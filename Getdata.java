public class Getdata {
    private String description;
    private boolean isComplete;
    
    public Getdata(String description){
        this.description=description;
        this.isComplete=false;
    }
    public String workList(){
        return description;
    }
    public void complete(){
        isComplete=true;
    }
    public String isToComplete(){
        return description;
    } 
}
