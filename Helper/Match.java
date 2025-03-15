package Helper;

import java.io.Serializable;

public class Match implements Serializable {
    private String team1;
    private String team2;
    private String venue;
    private String date;
    private String time;

    Match(String team1, String team2, String venue, String date, String time) {
        this.team1=team1;
        this.team2=team2;
        this.time=time;
        this.venue=venue;
        this.date=date;
    } 
    public String getTeam1(){
        return team1;
    }
    public String getTeam2(){
        return team2;
    }
    public String getVenue(){
        return this.venue;
    }
    public String getDate(){
        return this.date;
    }
    public String getTime(){
        return this.time;
    }
    public void setTeam1(String t){
         this.team1=t;
    }
    public void setTeam2(String t){
        this.team2=t;
    }
    public void setVenue(String v){
        this.venue=v;
    }
    public void setDate(String d){
        this.date=d;
    }
    public void setTime(String ti){
        this.time=ti;
    }
}
