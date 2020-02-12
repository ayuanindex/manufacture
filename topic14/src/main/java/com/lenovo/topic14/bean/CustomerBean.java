package com.lenovo.topic14.bean;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic14.bean
 * @ClassName: CustomerBean
 * @CreateDate: 2020/2/12 14:50
 */
public class CustomerBean {
    private String background;
    private String plName;
    private String hp;
    private double progress;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getPlName() {
        return plName;
    }

    public void setPlName(String plName) {
        this.plName = plName;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "CustomerBean{" +
                "background='" + background + '\'' +
                ", plName='" + plName + '\'' +
                ", hp='" + hp + '\'' +
                ", progress=" + progress +
                '}';
    }
}
