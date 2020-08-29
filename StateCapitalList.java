package u10.capitals;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class StateCapitalList {
    private ArrayList<StateCapital> states = new ArrayList<>();
    public StateCapitalList(java.lang.String fileName) throws java.io.FileNotFoundException {
        Scanner in = new Scanner(new FileReader(fileName));
        while (in.hasNextLine()){
            String [] str =in.nextLine().split("\t");
            states.add(new StateCapital(str [0],str[1]));
        }
    }

    public StateCapital getRandomState(){
        Random random = new Random();
        return states.get(random.nextInt(states.size()));
    }

    public int statesRemaining() {
        return states.size();
    }

    public void remove(StateCapital sc){
        states.remove(sc);
    }
}
