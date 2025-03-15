package Helper;

import java.io.Serializable;

public class Player implements Serializable{
    int id;
    String name;
    String position;

    public Player(int id , String name, String position){
        this.id = id;
        this.name = name;
        this.position = position;
    }
}
