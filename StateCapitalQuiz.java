package u10.capitals;

import javax.swing.*;
import java.io.FileNotFoundException;

public class StateCapitalQuiz {
    public static void main(String[] args) throws FileNotFoundException {
        int right = 0;
        int inp = 0;
        String[] files = {"states_central.txt", "states_east.txt", "states_small_test.txt",
                "states_south.txt", "states_west.txt"};
        String filec = (String) JOptionPane.showInputDialog(null, "Which quiz would you like to take?",
                "File Choice:",JOptionPane.QUESTION_MESSAGE, null, files, files[0]);
        if (filec == null){
            System.exit(0);
        }
        StateCapitalList quiz = new StateCapitalList(filec);
        while (quiz.statesRemaining() > 0){
            StateCapital state = quiz.getRandomState();
            String resp = JOptionPane.showInputDialog("What is the capital of "+state.getState()
            + "?");
            if (resp ==null){
                break;
            }
            else if (resp.equalsIgnoreCase(state.getCapital())){
                JOptionPane.showMessageDialog(null,"Correct!");
                quiz.remove(state);
                right++;
            }
            else{
                JOptionPane.showMessageDialog(null, "Incorrect, the capital is "
                + state.getCapital());
        }
            inp++;
        }

        JOptionPane.showMessageDialog(null, "You guessed " +
                right + " capitals in " + inp + "guesses.");
    }
}
