package net.simplifiedlearning.firebaseauth;

public class Chore  {
    String choreName, chorePoints, email,status,id;

    public Chore() {
    }
public Chore (String choreName, String chorePoints, String email, String status,String id){
        this.choreName = choreName;
        this.chorePoints = chorePoints;
        this.email = email;
        this.status = status;
    this.id = id;

    }
    public String getChoreName(){
        return choreName;
    }
    public void setChoreName(String choreName){
        this.choreName = choreName;
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
