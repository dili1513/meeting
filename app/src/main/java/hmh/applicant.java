package hmh;

/**
 * Created by lenovo on 2019/10/17.
 */

public class applicant {
    private String email;
    private String ID;
    private String Password;
    public applicant(String s1,String s2){
        email=s1;
        Password=s2;
    }
    public boolean userlogin(){

        if(this.email.equals("user@163.com")&&this.Password.equals("password"))
            return true;
        return false;
    }
    public boolean adminlogin(){

        if(this.email.equals("admin@163.com")&&this.Password.equals("password"))
            return true;
        return false;
    }
}
