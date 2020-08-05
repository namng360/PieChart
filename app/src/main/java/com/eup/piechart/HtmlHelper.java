package com.eup.piechart;

import android.content.Context;

import java.io.IOException;

public class HtmlHelper {

    private static final String dateColor = "rgba(0,0,0,.71)";
    private static final String rubyColorNight = "#F9F9F9";
    private static final String linkColorNight = "#F7A033";
    private static final String titleColorNight = "rgba(255,255,255,.91)";

    private String newsTitleHtml = "";

    public HtmlHelper(Context context) {

        getHtmlString(context);
    }

    public String getRubyColor() {
        return rubyColorNight;
    }

    public String getLinkColor() {
        return  linkColorNight ;
    }

    public String getTitleColor() {
        return  titleColorNight ;
    }

    public String getDateColor() {
        return dateColor;
    }

    private void getHtmlString(Context context) {
        try {
            newsTitleHtml = StringHelper.getStringFromAsset(context, "sentence.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String string2Title(String title, int fontSize) {
        return newsTitleHtml.replaceAll("<nguyenthelinh>", title)
                .replaceAll("<myFontSize>", String.valueOf(fontSize + 2))
                .replaceAll("<bodyColor>", getTitleColor())
                .replaceAll("<rubyColor>", getRubyColor());
    }

    public String string2Title(String title, int fontSize, String colorTitle) {
        return newsTitleHtml.replaceAll("<nguyenthelinh>", title)
                .replaceAll("<myFontSize>", String.valueOf(fontSize + 2))
                .replaceAll("<bodyColor>", colorTitle.isEmpty() ? getTitleColor() : colorTitle)
                .replaceAll("<rubyColor>", getRubyColor());
    }

    public String string2TitleSpan(String title, int fontSize) {
        return newsTitleHtml.replaceAll("<nguyenthelinh>", title)
                .replaceAll("<myFontSize>", String.valueOf(fontSize + 2))
                .replaceAll("<bodyColor>", getTitleColor())
                .replaceAll("<rubyColor>", getRubyColor())
                .replaceAll("28,134,238", "241,216,99");
    }

}
