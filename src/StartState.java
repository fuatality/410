public class StartState {
    private String startState;
    public StartState (String nameOfStartState){
        this.startState=nameOfStartState;
    }

    public StartState() {

    }

    public String getStartState(){
        return this.startState;
    }
    public void setStartState(String nameOfStartState){
        this.startState=nameOfStartState;
    }
    public void print(){
        System.out.println("Start state: "+startState);
    }
}
