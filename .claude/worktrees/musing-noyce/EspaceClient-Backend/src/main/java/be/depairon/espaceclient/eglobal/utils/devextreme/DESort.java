package be.depairon.espaceclient.eglobal.utils.devextreme;

public class DESort {

    private String selector;
    private boolean desc;

    public DESort(String key, boolean isDescOrder) {
        this.selector = key;
        this.desc = isDescOrder;
    }

    public DESort() {
        this.selector = "";
        this.desc = false;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public boolean getDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}
