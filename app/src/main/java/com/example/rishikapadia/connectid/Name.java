package com.example.rishikapadia.connectid;

/**
 * Created by Rishi Kapadia on 17/02/2017.
 */

public class Name {

    private String name;
    private int card_id;

    public Name (String name,int card_id){
        this.setName(name);
        this.setCard_id(card_id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }
}
