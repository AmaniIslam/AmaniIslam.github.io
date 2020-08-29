package u10.capitals;

public class StateCapital {
    private String state;
    private String capital;
    public StateCapital(java.lang.String state, java.lang.String capital){
        this.state = state;
        this.capital = capital;
    }

    public boolean equals(java.lang.Object o) {
        StateCapital str = (StateCapital) o;
        if (this.state.equals((str.state))){
            return true;
        }
        else {
            return false;
        }
    }

    public java.lang.String getState(){
        return state;
    }

    public java.lang.String getCapital(){
        return capital;
    }


}