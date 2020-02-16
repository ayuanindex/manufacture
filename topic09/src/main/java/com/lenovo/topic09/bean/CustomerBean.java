package com.lenovo.topic09.bean;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic09.bean
 * @ClassName: CustomerBean
 * @CreateDate: 2020/2/12 17:10
 */
public class CustomerBean {
    private String name;
    private int hp;
    private int progress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "CustomerBean{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", progress=" + progress +
                '}';
    }
}