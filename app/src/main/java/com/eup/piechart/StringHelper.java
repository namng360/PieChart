package com.eup.piechart;

import android.content.Context;
import android.text.Html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringHelper {
    private static final String tagRuby = "<ruby>%s<rt>%s</rt></ruby>";
    public static final String tagSpan = "<span>%s</span>";
    private static final int HIRAGANA_START = 0x3040;
    private static final int HIRAGANA_END = 0x309F;
    private static final int KATAKANA_START = 0x30A0;
    private static final int KATAKANA_END = 0x30FF;
    private static final int KATAKANA_PHONETIC_START = 0x31F0;
    private static final int KATAKANA_PHONETIC_END = 0x31FF;
    private static final int KANJI_START = 0x4e00;
    private static final int KANJI_END = 0x9faf;
    private static final int RARE_KANJI_START = 0x3400;
    private static final int RARE_KANJI_END = 0x4dbf;
    private static final int JAPANESE_STYLE_START = 0x3000;
    private static final int JAPANESE_STYLE_END = 0x303f;
//    private static final char SPACIAL[] = {'\\', '^', '$', '{', '}', '[', ']', '(', ')', '.', '*', '+', '?', '|', '<', '>', '&'};

    /*
    merge to enable furigana
*/
    public static String htlm2Furigana(String html) {
        html = html.replaceAll("<ruby>", "{");
        html = html.replaceAll("<rt>", ";");

        html = html.replaceAll("</rt></ruby>", "}");
        return Html.fromHtml(html).toString();
    }

    /*
        merge to disable furigana
     */
    public static String html2String(String html) {
        html = html.replaceAll("/</?span[^>]*>/g", "");
        html = html.replaceAll("<ruby>", "");
        html = html.replaceAll("</ruby>", "");
        html = html.replaceAll("<rt>[\\w-]+</rt>", "");
        return Html.fromHtml(html).toString();
    }

    public static String extractYoutubeId(String url) {
        String id = "undefined";
        try {
            String query = new URL(url).getQuery();
            if (query != null) {
                String[] param = query.split("&");
                for (String row : param) {
                    String[] param1 = row.split("=");
                    if (param1[0].equals("v")) {
                        id = param1[1];
                    }
                }
            } else {
                if (url.contains("embed") && url.contains("/")) {
                    id = url.substring(url.lastIndexOf("/") + 1);
                } else if (url.contains("youtu.be") && url.contains("/")) {
                    if (url.lastIndexOf("/") < url.length())
                        id = url.substring(url.lastIndexOf("/") + 1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

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

    private static String appendTagRuby(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = value.length();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(String.format(tagRuby, value.charAt(i) + "", ""));
        }
        return stringBuilder.toString();
    }

    public static boolean isHira(char ch) {
        return HIRAGANA_START <= ch && ch <= HIRAGANA_END;
    }

    private static boolean isKanji(char ch) {
        return ch != '、' && ch != '「' && ch != '」' && ch != '。' && ch != '　'
                && (KANJI_START <= ch && ch <= KANJI_END || RARE_KANJI_START <= ch && ch <= RARE_KANJI_END || JAPANESE_STYLE_START <= ch && ch <= JAPANESE_STYLE_END);
//        || KATAKANA_START <= ch && ch <= KATAKANA_END || KATAKANA_PHONETIC_START <= ch && ch <= KATAKANA_PHONETIC_END);
    }

    public static boolean isKanjiWithKata(char ch) {
        return ch != '・' && ch != '、' && ch != '「' && ch != '」' && ch != '。' && ch != '　'
                && (KANJI_START <= ch && ch <= KANJI_END || RARE_KANJI_START <= ch && ch <= RARE_KANJI_END || JAPANESE_STYLE_START <= ch && ch <= JAPANESE_STYLE_END || KATAKANA_START <= ch && ch <= KATAKANA_END || KATAKANA_PHONETIC_START <= ch && ch <= KATAKANA_PHONETIC_END);
    }

    public static boolean isNotCharJapanese(char ch) {
        return !isHira(ch) && !isKanjiWithKata(ch);
    }

    public static int ConvertStringToMillis(String time) {
        if (time != null && time.length() > 0 && time.contains(":")) {
            List<String> times = Arrays.asList(time.split(":"));
            int timeMillis = -1;
            if (times.size() == 3) { //string  "00:00:00"
                try {
                    timeMillis = Integer.parseInt(times.get(0)) * 60 * 60 * 1000;
                } catch (NumberFormatException e) {
                    timeMillis = 0;
                }

                try {
                    timeMillis += Integer.parseInt(times.get(1)) * 60 * 1000;
                } catch (NumberFormatException e) {
                    timeMillis += 0;
                }
                String tmp = times.get(2).replace(",", ".");

                try {
                    timeMillis += Double.parseDouble(tmp.contains(" ") ? tmp.substring(0, tmp.indexOf(" ")) : tmp) * 1000;
                } catch (NumberFormatException e) {
                    timeMillis += 0;
                }

            } else if (times.size() == 2) { //string  "00:00"
                try {
                    timeMillis = Integer.parseInt(times.get(0)) * 60 * 1000;
                } catch (NumberFormatException e) {
                    timeMillis = 0;
                }
                String tmp = times.get(1).replace(",", ".");
                try {
                    timeMillis += Double.parseDouble(tmp.contains(" ") ? tmp.substring(0, tmp.indexOf(" ")) : tmp) * 1000;
                } catch (NumberFormatException e) {
                    timeMillis += 0;
                }
            }
            return timeMillis;
        }
        return -1;
    }

    /*
        Example
     */
    public static String enableFurigana(String jap) {
        StringBuilder html = new StringBuilder();
        String[] _ref = jap.split("\t");
        for (String j : _ref) {
            String[] _ref1 = j.split(" ");
            String writting;
            String reading;
            if (_ref1.length <= 2) {
                writting = _ref1[0];
                html.append(writting);
            } else if (_ref1.length == 3) {
                writting = _ref1[0];
                reading = _ref1[2];
                html.append("{").append(writting).append(";").append(reading).append("}");
            }
        }
        return html.toString();
    }

    public static String disableFurigana(String jap) {
        StringBuilder html = new StringBuilder();
        String[] _ref = jap.split("\t");
        for (String j : _ref) {
            String[] _ref1 = j.split(" ");
            String writting;
            writting = _ref1[0];
            html.append(writting);
        }
        return html.toString();
    }

    /*
       get string from asset file
    */
    public static String getStringFromAsset(Context context, String path) throws IOException {
        StringBuilder buf = new StringBuilder();
        InputStream json = context.getAssets().open(path);
        BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
        String str;

        while ((str = in.readLine()) != null) {
            buf.append(str);
        }

        in.close();

        return buf.toString();
    }

    public static String stringToHex(String str) {
        int unicode = 0;
        for (int i = 0; i < str.length(); i++) {
            unicode = unicode * 10 + (int) str.charAt(i);
        }
        return Integer.toHexString(unicode);
    }

}
