package hmh;

/**
 * Created by lenovo on 2019/10/19.
 */

public class Meeting {
    private String ID;
    private String meetingName;
    private String meetingsponsor;
    private String meetingDate;
    private String meetingTime;
    private String meetinglength;
    private String projector;
    private String microphone;
    private String num;
    private String shenqingzhuangtai;
    public Meeting(String Name,String Date,String Time,String shenqingzhuangtai,String id){
        this.meetingName=Name;
        this.meetingDate=Date;
        this.meetingTime=Time;
        this.shenqingzhuangtai=shenqingzhuangtai;
        this.ID = id;
    }
    public Meeting(String Name,String Sponsor,String Date,String Time,String Length,String Projector,String Microphone,String Num,String shenqingzhuangtai ){
        this.meetingName=Name;
        this.meetingsponsor=Sponsor;
        this.meetingDate=Date;
        this.meetingTime=Time;
        this.meetinglength=Length;
        this.projector=Projector;
        this.microphone=Microphone;
        this.num=Num;
        this.shenqingzhuangtai=shenqingzhuangtai;
    }
    public Meeting(String Name,String Sponsor,String Date,String Time,String Length,String Projector,String Microphone,String Num ){
        this.meetingName=Name;
        this.meetingsponsor=Sponsor;
        this.meetingDate=Date;
        this.meetingTime=Time;
        this.meetinglength=Length;
        this.projector=Projector;
        this.microphone=Microphone;
        this.num=Num;
    }
    public String getName(){
        return this.meetingName;
    }
    public String getDate(){
        return this.meetingDate;
    }
    public String getTime(){return this.meetingTime;}
    public String getShenqingzhuangtai(){return this.shenqingzhuangtai;}
    public String getMeetingsponsor(){
        return this.meetingsponsor;
    }
    public String getMeetinglength(){
        return this.meetinglength;
    }
    public String getProjector(){return this.projector;}
    public String getMicrophone(){return this.microphone;}
    public String getNum(){return this.num;}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

