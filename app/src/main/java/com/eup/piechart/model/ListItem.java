package com.eup.piechart.model;

public class ListItem {
    public String value;
    public boolean hasBackground = false;

    public ListItem(String value, boolean background) {
        this.value = value;
        this.hasBackground = background;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
