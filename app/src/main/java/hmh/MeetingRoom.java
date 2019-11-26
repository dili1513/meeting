package hmh;

/**
 * Created by lenovo on 2019/10/19.
 */

public class MeetingRoom {
    private String meetingRoomName;
    private String meetingRoomDate;
    private String meetingRoomTime;
    private String meetingAddress;
    private String num;
    private String projector;
    private String microphone;

    public MeetingRoom(String Name,String address,String Date,String num,String projector,String microphone){
        this.meetingRoomName=Name;
        this.meetingRoomDate=Date;
        this.meetingAddress=address;
        this.num=num;
        this.projector=projector;
        this.microphone=microphone;
    }
    public MeetingRoom(String Name,String Date,String Time){
        this.meetingRoomName=Name;
        this.meetingRoomDate=Date;
        this.meetingRoomTime=Time;
    }
    public String getName(){
        return this.meetingRoomName;
    }
    public String getDate(){
        return this.meetingRoomDate;
    }
    public String getTime(){return this.meetingRoomTime;}
    public String getAddress(){return this.meetingAddress;}
    public String getNum(){return this.num;}
    public String getMicrophone(){return this.microphone;}
    public String getProjector(){return this.projector;}

}
