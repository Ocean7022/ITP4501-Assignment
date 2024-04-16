package com.example.itp4501_assignment;

public class Card {
    private int cardNum; // 0 - 3
    private int photo;
    private boolean open;

    public Card(int cardNum, int photo) {
        this.cardNum = cardNum;
        this.photo = photo;
        this.open = false;
    }

    public int getCardNum() {
        return cardNum;
    }

    public int getPhoto() {
        return photo;
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}