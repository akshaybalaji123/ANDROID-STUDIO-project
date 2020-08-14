package net.simplifiedlearning.firebaseauth;

public class Chore  {
    String choreName, chorePoints, email,status,id,username;

    public Chore() {
    }
public Chore (String choreName, String chorePoints, String email, String status,String id,String username){
        this.choreName = choreName;
        this.chorePoints = chorePoints;
        this.email = email;
        this.status = status;
    this.id = id;
    this.username = username;

    }
    public String getChoreName(){
        return choreName;
    }
    public String getUsername(){
        return username;
    }
    public void setChoreName(String choreName){
        this.choreName = choreName;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getChorePoints(){
        return chorePoints;
    }
    public void setChorePoints(String chorePoints){
        this.chorePoints = chorePoints;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
