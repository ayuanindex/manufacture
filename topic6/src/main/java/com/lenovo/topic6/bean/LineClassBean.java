package com.lenovo.topic6.bean;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic6.bean
 * @ClassName: LineClassBean
 * @Author: AYuan
 * @CreateDate: 2020/1/19 10:50
 */
public class LineClassBean {
    private boolean mpvIsSelect = false;
    private boolean carIsSelect = false;
    private boolean suvIsSelect = false;
    private int position;

    public LineClassBean(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean isMpvIsSelect() {
        return mpvIsSelect;
    }

    public void setMpvIsSelect(boolean mpvIsSelect) {
        this.mpvIsSelect = mpvIsSelect;
    }

    public boolean isCarIsSelect() {
        return carIsSelect;
    }

    public void setCarIsSelect(boolean carIsSelect) {
        this.carIsSelect = carIsSelect;
    }

    public boolean isSuvIsSelect() {
        return suvIsSelect;
    }

    public void setSuvIsSelect(boolean suvIsSelect) {
        this.suvIsSelect = suvIsSelect;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void update(AllProductionLineBean.LineBean lineBean) {
        switch (lineBean.getProductionLineId()) {
            case 1:
                setCarIsSelect(true);
                break;
            case 2:
                setMpvIsSelect(true);
                break;
            case 3:
                setSuvIsSelect(true);
                break;
        }
    }

    @Override
    public String toString() {
        return "LineClassBean{" +
                "mpvIsSelect=" + mpvIsSelect +
                ", carIsSelect=" + carIsSelect +
                ", suvIsSelect=" + suvIsSelect +
                ", position=" + position +
                '}';
    }
}
