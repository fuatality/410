import java.util.ArrayList;

public class State {
    private ArrayList<String> states;
    public State (ArrayList<String> nameOfState){this.states =nameOfState;}

    public State() {

    }

    public ArrayList<String> getState(){
        return this.states;
    }
    public int getStateCount(){
        return this.states.size();
    }
    public void setState(ArrayList<String> N){
        this.states =N;
    }
}
