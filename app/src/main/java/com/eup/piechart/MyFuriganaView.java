package com.eup.piechart;

import android.text.Html;
import android.util.Log;

import java.util.ArrayList;

public class MyFuriganaView {
    private static final String tagRuby = "<ruby>%s<rt>%s</rt></ruby>";
    private static final int KANJI_START = 0x4e00;
    private static final int KANJI_END = 0x9faf;
    private static final int RARE_KANJI_START = 0x3400;
    private static final int RARE_KANJI_END = 0x4dbf;
    private static final int JAPANESE_STYLE_START = 0x3000;
    private static final int JAPANESE_STYLE_END = 0x303f;

    public static String htlm2Furigana(String html) {
        html = html.replaceAll("<ruby>", "{");
        html = html.replaceAll("<rt>", ";");
        html = html.replaceAll("</rt></ruby>", "}");
        Log.d("html", html);
        return Html.fromHtml(html).toString();
    }

//    public static String htlm2Furigana(String html) {
//        html = html.replaceAll("<ruby>", "{");
//        html = html.replaceAll("<rt>", ";");
//        html = html.replaceAll("</rt></ruby>", "}");
//        return Html.fromHtml(html).toString();
//    }

    public static String stringToFurigana(String value, String hira) {
        if (hira.isEmpty()) return value;

        boolean checkReplace = false;
        String txtOrigin = "", txtReplace = "";
        if (value.contains("6") && !hira.contains("6") && hira.contains("ろく")) {
            value = value.replace("6", "(ろく)");
            hira = hira.replace("ろく", "(ろく)");
            checkReplace = true;
            txtOrigin = "6";
            txtReplace = "(ろく)";
        }

        ArrayList<Integer> listCheck = new ArrayList<>();
        StringBuilder html = new StringBuilder(), valueBulder, hiraBulder;

        int first_kanji = -1;
        for (int i = 0; i < value.length(); i++) {
            if (isKanji(value.charAt(i))) {
                first_kanji = i;
                break;
            }
        }

        if (first_kanji == -1)
            return value;

        try {
            if (first_kanji > 0) {
                String hira_first = value.substring(0, first_kanji);
                html.append(appendTagRuby(hira_first.trim()));
                valueBulder = new StringBuilder(value.substring(first_kanji));
                hiraBulder = new StringBuilder(hira.replace(" ", "").substring(hira_first.replace(" ", "").length()));
            } else {
                valueBulder = new StringBuilder(value);
                hiraBulder = new StringBuilder(hira.replace(" ", ""));
            }

            boolean isKanji = true;

            if (value.endsWith("。") && !hira.endsWith("。"))
                hiraBulder.append("。");

            for (int i = 0; i < valueBulder.length() - 2; i++) {
                if (isKanji(valueBulder.charAt(i)) && valueBulder.charAt(i + 1) == ' ' &&
                        (isKanji(valueBulder.charAt(i + 2)) || valueBulder.charAt(i + 2) == ' '))
                    valueBulder = new StringBuilder(valueBulder.substring(0, i + 1) + valueBulder.substring(i + 2));
            }

            for (int i = 0; i < valueBulder.length(); i++) {
                if (isKanji(valueBulder.charAt(i)) == isKanji) {
                    listCheck.add(i);
                    isKanji = !isKanji;
                }
            }

            if (listCheck.size() == 1) {
                html.append(valueBulder.toString().trim().equals(hiraBulder.toString()) ? appendTagRuby(valueBulder.toString())
                        : String.format(tagRuby, valueBulder.toString(), hiraBulder.toString()));
            } else {

                for (int i = 0; i < listCheck.size(); i += 2) {
                    if (i + 1 == listCheck.size() - 1) {
                        String hira_last = valueBulder.toString().substring(listCheck.get(i + 1));
                        String hira_bulder = hiraBulder.toString();
                        int pos = hira_bulder.indexOf(hira_last.replace(" ", ""));
                        String hira_string = "";
                        if (pos > 0)
                            hira_string = hira_bulder.substring(0, pos);
                        else {
                            int pos_2 = hira_bulder.replace("わ", "は").indexOf(hira_last.replace(" ", ""));
                            if (pos_2 > 0)
                                hira_string = hira_bulder.substring(0, pos_2);
                        }
                        String valueBuild = valueBulder.toString().substring(listCheck.get(i), listCheck.get(i + 1));
                        html.append(valueBuild.trim().equals(hira_string.trim()) ? appendTagRuby(valueBuild) :
                                String.format(tagRuby, valueBuild, hira_string));
                        html.append(appendTagRuby(hira_last));
                    } else if (i == listCheck.size() - 1) {
                        String valueBuild = valueBulder.toString().substring(listCheck.get(i));
                        html.append(valueBuild.trim().equals(hiraBulder.toString()) ? appendTagRuby(valueBuild) :
                                String.format(tagRuby, valueBuild, hiraBulder.toString()));
                    } else {
                        String value_mid = valueBulder.toString().substring(listCheck.get(i), listCheck.get(i + 1));
                        String hira_mid = valueBulder.toString().substring(listCheck.get(i + 1), listCheck.get(i + 2));
                        String hira_bulder = hiraBulder.toString();
                        int pos = hira_bulder.indexOf(hira_mid.replace(" ", ""), value_mid.length());

                        String hira_string = "";
                        if (pos > 0) {
                            hira_string = hira_bulder.substring(0, pos);
                        } else {
                            pos = hira_bulder.replace("わ", "は").indexOf(hira_mid.replace(" ", ""));
                            if (pos > 0)
                                hira_string = hira_bulder.substring(0, pos);
                            else
                                pos = value_mid.length();
                        }
                        html.append(value_mid.trim().equals(hira_string.trim()) ? appendTagRuby(value_mid) :
                                String.format(tagRuby, value_mid, hira_string));
                        html.append(appendTagRuby(hira_mid));

                        hiraBulder = new StringBuilder(hira_bulder.substring(pos + hira_mid.replace(" ", "").trim().length()));
                    }
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            return value;
        }
        if (checkReplace)
            return html.toString().replace(txtReplace, txtOrigin);

        return html.toString();
    }

    private static boolean isKanji(char ch) {
        return ch != '、' && ch != '「' && ch != '」' && ch != '。' && ch != '　'
                && (KANJI_START <= ch && ch <= KANJI_END || RARE_KANJI_START <= ch && ch <= RARE_KANJI_END || JAPANESE_STYLE_START <= ch && ch <= JAPANESE_STYLE_END);
//        || KATAKANA_START <= ch && ch <= KATAKANA_END || KATAKANA_PHONETIC_START <= ch && ch <= KATAKANA_PHONETIC_END);
    }

    private static String appendTagRuby(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = value.length();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(String.format(tagRuby, value.charAt(i) + "", ""));
        }
        return stringBuilder.toString();
    }

//    public static String toFurigana(String s1,String s2){
//        String result = "";
//        List<String> lg1 = new ArrayList<>(), lg2 = new ArrayList<>();
//        Collections.addAll(lg1, s1.split(""));
//        Collections.addAll(lg2, s2.split(""));
//        while (lg1.size() > 0 && lg2.size()>0) {
//            Log.e("Result", lg1.size()+"");
//            String sup = "", sub = "";
//            if (lg1.size()==1){
//                if (lg1.get(0).equals(lg2.get(0))){
//                    result+=lg1.get(0);
//                    lg1.remove(0);
//                    lg2.remove(0);
//                } else {
//                    while (lg2.size()>0){
//                        sub+=lg2.get(0);
//                        lg2.remove(0);
//                    }
//                    result+="<ruby>"+lg1.get(0)+"<rt>"+sub+"</rt>"+"</ruby>";
//                    lg1.remove(0);
//                }
//                break;
//            } else if (lg1.get(0).equals(lg2.get(0))) {
//                result += lg1.get(0);
//                lg1.remove(0);
//                lg2.remove(0);
//                continue;
//            } else {
//                int f1 = -1, f2 = -1;
//                for (int i = 0; i < lg1.size(); i++) {
//                    boolean isStop = false;
//                    for (int j = 0; j < lg2.size(); j++) {
//                        if (lg1.get(i).equals(lg2.get(j))) {
//                            f1 = i;
//                            f2 = j;
//                            isStop = true;
//                            break;
//                        }
//                    }
//                    if (isStop) {
//                        break;
//                    }
//                }
//                if (f1 == -1){
//                    f1=lg1.size()-1;
//                    f2=lg2.size()-1;
//                }
//                while (f1>0){
//                    sup += lg1.get(0);
//                    lg1.remove(0);
//                    f1--;
//                }
//
//                while(f2>0) {
//                    sub += lg2.get(0);
//                    lg2.remove(0);
//                    f2--;
//                }
//                sub = "<rt>" + sub + "</rt>";
//                sup = "<ruby>" + sup.trim() + sub.trim() + "</ruby>";
//                result += sup;
//            }
//
//        }
//        return result;
//    }
}
