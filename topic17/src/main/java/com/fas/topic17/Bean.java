package com.fas.topic17;

public class Bean {
    private int id;
    private String carName;
    private String content;
    private int gold;

    public Bean(int id, String carName, String content, int gold) {
        this.id = id;
        this.carName = carName;
        this.content = content;
        this.gold = gold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
