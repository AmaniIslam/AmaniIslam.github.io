import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Lambda {
    static Dictionary vars = new Hashtable<String, Expressions>();
    static String pastVar = "";

    public static void main(String[] args) throws IOException {
        String carrot = ">";
        String name = "";
        while (!name.equals("exit")) {
            System.out.print(carrot);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            name = reader.readLine();
            if (!name.equals("")) {
                char[] namearray = name.toCharArray();
                int commentsplit = -1;
                for (int i = namearray.length - 1; i >= 0; i--) {
                    if (namearray[i] == (';') && commentsplit == -1) {
                        commentsplit = i;
                    }
                }
                if (commentsplit == -1)
                    commentsplit = name.length();
                name = name.substring(0, commentsplit);
                // name = name.trim();
                if (!name.equals("exit")) {
                    ArrayList<String> chararray = new ArrayList<>();
                    namearray = name.toCharArray();
                    int temp = -1;
                    for (int i = 0; i < namearray.length; i++) {
                        if (!(namearray[i] == '(' || namearray[i] == '\\' || namearray[i] == '.' || namearray[i] == ')'
                                || namearray[i] == ' ' || namearray[i] == '=' || (int) namearray[i] == 0)) {
                            if (temp == -1) {
                                temp = i;
                            }
                        } else {
                            if (temp != -1) {
                                chararray.add(name.substring(temp, i));
                                if ((int) namearray[i] == 0) {
                                    chararray.add("\\");
                                } else
                                    chararray.add(name.substring(i, i + 1));
                                temp = i + 1;
                            } else {
                                if ((int) namearray[i] == 0) {
                                    chararray.add("\\");
                                } else
                                    chararray.add(name.substring(i, i + 1));
                                temp = i + 1;
                            }
                        }
                    }
                    if (temp != -1)
                        chararray.add(name.substring(temp));
                    Applications z = new Applications();
                    Expressions express = createExpression(chararray, z);
                    if (express != null)
                        System.out.println(express);
                } else {
                    System.out.println("Goodbye!");
                }
            }
        }
    }

    public static boolean canRun = true;

    public static Expressions runner(Expressions expression) {
        while (canRun(expression)) {
            expression = betaReduce(expression);
        }
        return expression;
        
    }

    public static boolean canRun(Expressions expression) {
        canRun = false;
        canItRun(expression);
        return canRun;
    }

    public static void canItRun(Expressions expression) {
        if (expression instanceof Applications) {
            if (((Applications) expression).left instanceof Function) {
                canRun = true;
            } else {
                canItRun(((Applications) expression).left);
                canItRun(((Applications) expression).right);
            }
        } else if (expression instanceof Function) {
            canItRun(((Function) expression).expression);
        }
    }

    public static Expressions copy(Expressions express) { // creates a deep copy of the expression
        if (express instanceof Applications) {
            Applications temp = new Applications();
            temp.left = copy(((Applications) express).left);
            temp.right = copy(((Applications) express).right);
            return temp;
        } else if (express instanceof Function) {
            Function temp = new Function();
            temp.var.name = ((Function) express).var.name;
            temp.expression = copy(((Function) express).expression);
            return temp;
        } else {
            Variables temp = new Variables();
            temp.name = ((Variables) express).name;
            return temp;
        }
    }

    public static ArrayList<String> findVarsToReplace(Expressions expression, ArrayList<String> varsToReplace) {
        if (expression instanceof Applications) {
            varsToReplace = findVarsToReplace((Expressions) ((Applications) expression).left, varsToReplace);
            return findVarsToReplace((Expressions) ((Applications) expression).right, varsToReplace);
        } else if (expression instanceof Variables) {
            if (!(varsToReplace.contains(((Variables) expression).name))) {
                varsToReplace.add(((Variables) expression).name);
            }
            return varsToReplace;
        } else {
            if (!(varsToReplace.contains(((Function) expression).var.name))) {
                varsToReplace.add(((Function) expression).var.name);
            }
            return findVarsToReplace((Expressions) ((Function) expression).expression, varsToReplace);
        }
    }

    public static Expressions replaceVarNames(Expressions expression,  ArrayList<String> varsToReplace, Dictionary varsToReplaceDictionary) {
        if (expression instanceof Applications) {
            Applications temp = new Applications();
            temp.left = replaceVarNames(((Applications) expression).left, varsToReplace, varsToReplaceDictionary);
            temp.right =  replaceVarNames(((Applications) expression).right, varsToReplace, varsToReplaceDictionary);
            return temp;
        } else if (expression instanceof Variables) {
            Enumeration enm = varsToReplaceDictionary.keys();
            List<String> ll = Collections.list(enm);
            if ((ll.contains(((Variables) expression).name))) {
                ((Variables) expression).name = (String) varsToReplaceDictionary.get(((Variables) expression).name);
            }
            return expression;
        } else {
            if ((varsToReplace.contains(((Function) expression).var.name))) {
                Enumeration enm = varsToReplaceDictionary.keys();
                List<String> ll = Collections.list(enm);
                if (ll.contains(((Function) expression).var.name)) {
                    ((Function) expression).var.name = (String) varsToReplaceDictionary.get(((Function) expression).var.name);
                } else {
                    String newName = (((Function) expression).var.name) + "1";
                    while ((varsToReplace.contains(newName))) {
                        newName = newName + "1";
                    }
                    varsToReplaceDictionary.put(((Function) expression).var.name, newName);
                    ((Function) expression).var.name = newName;
                }
            }
            ((Function) expression).expression = replaceVarNames(((Function) expression).expression, varsToReplace, varsToReplaceDictionary);
            return expression;
        }
    }
   
    public static Expressions alphaReduce(Expressions expression) {
        if (expression instanceof Variables)
            return expression;
        else if (expression instanceof Applications) {
            if (!(((Applications) expression).left instanceof Function)) {
                Applications temp = new Applications();
                temp.left = alphaReduce(((Applications) expression).left);
                temp.right = alphaReduce(((Applications) expression).right);
                return temp;
            } else {
                ArrayList<String> varsToReplace = new ArrayList<>();
                varsToReplace = findVarsToReplace(((Applications) expression).right, varsToReplace);
                Dictionary varsToReplaceDictionary = new Hashtable<>();
                ((Applications) expression).left = replaceVarNames(copy(((Applications) expression).left), varsToReplace, varsToReplaceDictionary);
                return expression;
            }
        } else {
            Function func = new Function();
            func.var = ((Function) expression).var;
            func.expression = alphaReduce(((Function) expression).expression);
            return func;
        }
    }
    public static Expressions betaReduce(Expressions expression) {
        if (expression instanceof Variables)
            return expression;
        else if (expression instanceof Applications) {
            if (!(((Applications) expression).left instanceof Function)) {
                Applications temp = new Applications();
                if (canRun(((Applications) expression).left)) { // if left can run, dont run right
                    temp.left = betaReduce(((Applications) expression).left);
                    temp.right = ((Applications) expression).right;
                } else {// if left cant run, run right
                    temp.left = betaReduce(((Applications) expression).left);
                    temp.right = betaReduce(((Applications) expression).right);
                }
                return temp;
            } else {
                expression = alphaReduce(expression);
                return replaceAllVars(((Function) ((Applications) expression).left).expression,
                        ((Function) ((Applications) expression).left).var, ((Applications) expression).right);
            }
        } else if (expression instanceof Function) {
            Function func = new Function();
            func.var = ((Function) expression).var;
            func.expression = betaReduce(((Function) expression).expression);
            return func;
        } else
            return null;
    }

    public static Expressions replaceAllVars(Expressions expression, Variables variable, Expressions subVar) {
        if (expression instanceof Applications) {
            Applications temp = new Applications();
            temp.left = replaceAllVars(((Applications) expression).left, variable, subVar);
            temp.right = replaceAllVars(((Applications) expression).right, variable, subVar);
            return temp;
        } else if (expression instanceof Variables) {
            if (variable.name.equals(((Variables) expression).name)) {
                return copy(subVar);
            } else {
                return expression;
            }
        } else {
            Function func = new Function();
            func.var = ((Function) expression).var;
            func.expression = ((Function) expression).expression;
            if (!(((Function) expression).var.name.equals(variable.name))){
                func.expression = replaceAllVars(((Function) expression).expression, variable, subVar);
            }
            return func;
        }
    }

    public static Expressions createExpression(ArrayList<String> chararray, Applications root) {
        if (chararray.size() == 0) {
            if (root.left == null)
                return null;
            if (root.right == null)
                return root.left;
            return root;
        }
        if (chararray.get(0).equals("run")) {
            chararray.remove(0);
            Applications z = new Applications();
            return runner(createExpression(chararray, z));
        } else if (chararray.get(0).equals("=")) {
            if (!(root.left instanceof Variables)) {
                System.out.println(pastVar + " is already defined.");
                pastVar = "";
            } else {
                Applications z = new Applications();
                chararray.remove(0);
                Expressions express = createExpression(chararray, z);
                vars.put((((Variables) root.left).name), express);
                System.out.println("Added " + express + " as " +(((Variables) root.left).name));
            }
            return null;
        } else if (chararray.get(0).equals("\\")) {
            Function func = new Function();
            Variables var = new Variables();
            chararray.remove(0);
            while (chararray.get(0).equals(" ") || chararray.get(0).equals("")) {
                chararray.remove(0);
            }
            var.name = chararray.get(0);
            func.var = var;
            chararray.remove(0);
            while (!chararray.get(0).equals(".")) {
                chararray.remove(0);
            }
            chararray.remove(0);
            Applications z = new Applications();
            func.expression = createExpression(chararray, z);
            return createExpression(chararray, buildTree(root, func));

        } else if (chararray.get(0).equals("(")) {
            int x = findClosedParenIndex(chararray, 0);
            ArrayList<String> temp = new ArrayList<>();
            chararray.remove(0);
            for (int i = 0; i < x - 1; i++) {
                temp.add(chararray.get(0));
                chararray.remove(0);
            }
            chararray.remove(0);
            Applications z = new Applications();
            root = buildTree(root, createExpression(temp, z));
            return createExpression(chararray, root);
        } else if (!((chararray.get(0).equals(" ")) || (chararray.get(0).equals("")))) {
            Variables var = new Variables();
            Enumeration enm = vars.keys();
            List<Expressions> ll = Collections.list(enm);
            if (ll.contains(chararray.get(0))) {
                pastVar = chararray.get(0);
                Expressions express = (Expressions) vars.get(chararray.get(0));
                root = buildTree(root, express);
                chararray.remove(0);
            } else {
                var.name = chararray.get(0);
                root = buildTree(root, var);// make the variables bound/free
                chararray.remove(0);
            }
            return createExpression(chararray, root);
        } else {
            chararray.remove(0);
            return createExpression(chararray, root);
        }
    }

    public static Applications buildTree(Applications root, Expressions express) {
        if (root.left == null) {
            root.left = express;
        } else if (root.right == null) {
            root.right = express;
        } else {
            Applications temp = new Applications();
            temp.left = root;
            temp.right = express;
            root = temp;
        }
        return root;
    }

    public static int findClosedParenIndex(ArrayList<String> chararray, int currindex) {
        int parenCount = 1;
        for (int i = currindex + 1; i < chararray.size(); i++) {
            if (chararray.get(i).equals("(")) {
                parenCount++;
            }
            if (chararray.get(i).equals(")")) {
                parenCount--;
            }
            if (parenCount == 0) {
                return i;
            }
        }
        return chararray.size() - 1;
    }
}
