package main;

public class UserType {
    public boolean isAdmin = false, isEmployee = false;

    public UserType(String user){
        if(user.contentEquals("admin"))
            this.isAdmin = true;
        else if(user.contentEquals("employ"))
            this.isEmployee = true;
    }
}
