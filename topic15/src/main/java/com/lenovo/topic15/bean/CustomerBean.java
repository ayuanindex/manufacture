package com.lenovo.topic15.bean;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic15.bean
 * @ClassName: CustomerBean
 * @CreateDate: 2020/2/16 15:31
 */
public class CustomerBean {
    private String stepName;
    private String icon;

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "CustomerBean{" +
                "stepName='" + stepName + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
