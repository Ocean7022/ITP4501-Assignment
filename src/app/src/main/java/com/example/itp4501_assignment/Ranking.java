package com.example.itp4501_assignment;

import org.w3c.dom.CDATASection;

public class Ranking {
    private String name, dateTime, style;
    private int moves, numOfRanking;

    //online game records
    public Ranking(String name, int moves, int numOfRanking) {
        this.name = name;
        this.moves = moves;
        this.numOfRanking = numOfRanking;
        this.dateTime = "";
    }

    //local game records
    public Ranking(String name, int moves, int numOfRanking, String dateTime, String style) {
        this.name = name;
        this.moves = moves;
        this.numOfRanking = numOfRanking;
        this.dateTime = dateTime;
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        if (dateTime != "")
            return "Moves : " + moves + " Style : " + style + "\nDateTime : " + dateTime;
        return "Moves : " + moves;
    }

    public int getNumOfRanking() {
        return numOfRanking;
    }
}
