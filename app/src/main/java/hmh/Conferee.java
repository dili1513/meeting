package hmh;

/**
 * Created by lenovo on 2019/11/3.
 */

public class Conferee {
    private String ConfereeID;
    private String ConfereeName;
    public Conferee(String ID,String Name){
        this.ConfereeID=ID;
        this.ConfereeName=Name;
    }
    public String getName(){
        return this.ConfereeName;
    }
    public String getId(){
        return this.ConfereeID;
    }

}
