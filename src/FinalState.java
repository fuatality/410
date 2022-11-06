public class FinalState {
    private String finalState;
    public FinalState (String nameOfFinalState){
        this.finalState =nameOfFinalState;
    }
    public String getFinalState(){
        return this.finalState;
    }
    public void setFinalState(String nameOfFinalState){
        this.finalState =nameOfFinalState;
    }
    public void print(){
        System.out.println("Final state: "+finalState);
    }
}
