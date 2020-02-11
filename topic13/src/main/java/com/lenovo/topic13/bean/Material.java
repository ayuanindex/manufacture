package com.lenovo.topic13.bean;

public class Material {
    private int icon;
    private String iconPath;
    private String name;
    private int sum;
    private int area;
    private UserPart.DataBean userPart;

    public Material() {
    }

    public Material(int icon, String iconPath, String name, int sum, int area, UserPart.DataBean userPart) {
        this.icon = icon;
        this.iconPath = iconPath;
        this.name = name;
        this.sum = sum;
        this.area = area;
        this.userPart = userPart;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public UserPart.DataBean getUserPart() {
        return userPart;
    }

    public void setUserPart(UserPart.DataBean userPart) {
        this.userPart = userPart;
    }

    @Override
    public String toString() {
        return "Material{" +
                "icon=" + icon +
                ", iconPath='" + iconPath + '\'' +
                ", name='" + name + '\'' +
                ", sum=" + sum +
                ", area=" + area +
                ", userPart=" + userPart +
                '}';
    }


}