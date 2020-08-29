public class Function implements Expressions{
    public Variables var = new Variables();
    public Expressions expression = null;
    public void setVar(String string){
        var.name = string;
    }
    public String toString(){
        return ("(λ"+var.toString()+ "."+expression.toString()+")");
    }
    
}