package io.agora.custom;

/**
 * Created by sonnguyen on 5/14/17.
 */

public class User {
    private String UID;
    private String UserName;
    private String UserPass;
    private int Level;
    private String Name;
    private String Rank;

    public String getStatus() {
        return Status;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String Status;

    public User() {
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
