package hmh;

public class MeetingRoomBean {

    /**
     * address : 北京市海淀区西土城路10号教三
     * name : 310
     * id : 1
     * capacity : 200
     * projector : 1
     * username : null
     * admname : hanning
     * microphone : 1
     */

    private String address;
    private String name;
    private int id;
    private int capacity;
    private int projector;
    private String username;
    private String admname;
    private int microphone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getProjector() {
        return projector;
    }

    public void setProjector(int projector) {
        this.projector = projector;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdmname() {
        return admname;
    }

    public void setAdmname(String admname) {
        this.admname = admname;
    }

    public int getMicrophone() {
        return microphone;
    }

    public void setMicrophone(int microphone) {
        this.microphone = microphone;
    }
}
