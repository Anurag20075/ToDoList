import java.util.ArrayList;
import java.util.List;

public class Processdata {
     private List<Getdata> Item;

    public Processdata(){
        Item= new ArrayList<>();
    }
    public void addList(String description){
        Item.add(new Getdata(description));
    }
    public void removeList(int index){
        if(index>=0&&index<Item.size()){
        Item.remove(index);
        }
        else{
            System.out.println("INVALID INDEX");
        }
    }
    public void isCompleted(int index){
        Item.get(index).complete();
    }
    public void display(){
        for(int i=1;i<=Item.size();i++){
            System.out.println(i+":-"+Item.get(i-1).isToComplete());
        }
    }
}
