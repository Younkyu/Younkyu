package com.younkyu.android.remindapplication;

import java.util.ArrayList;

/**
 * Created by USER on 2017-01-28.
 */

public class calcul {

    public static String ys (String ddap) {


        ArrayList<String> wow = new ArrayList<>();

        for (int i = 0; i < ddap.length(); i++) {
            if (ddap.substring(i, i + 1).equals("*")) {
                wow.add(ddap.substring(0, i));
                ddap = ddap.substring(i, ddap.length());
                i = 0;
            } else if (ddap.substring(i, i + 1).equals("/")) {
                wow.add(ddap.substring(0, i));
                ddap = ddap.substring(i, ddap.length());
                i = 0;
            } else if (ddap.substring(i, i + 1).equals("-")) {
                wow.add(ddap.substring(0, i));
                ddap = ddap.substring(i, ddap.length());
                i = 0;
            } else if (ddap.substring(i, i + 1).equals("+")) {
                wow.add(ddap.substring(0, i));
                ddap = ddap.substring(i, ddap.length());
                i = 0;
            } else if (ddap.substring(i, i + 1).equals("=")) {
                wow.add(ddap.substring(0, i));
                ddap = ddap.substring(i, ddap.length());
                wow.add("=");
                break;
            }
        }
        int i = 1;

        wow.set(0, "+" + wow.get(0));
        Double hoo = 0.0;
        while (i < wow.size()) {
            String how = wow.get(i);
            String hew = how.substring(0, 1);
            String her = how.substring(1, wow.get(i).length());
            if (hew.equals("*")) {
                hoo = (Double.parseDouble(wow.get(i - 1).substring(1, wow.get(i - 1).length()))) * (Double.parseDouble(her));
                wow.set(i - 1, wow.get(i - 1).substring(0, 1) + hoo);
                wow.remove(i);
                i--;

            } else if (hew.equals("/")) {
                hoo = (Double.parseDouble(wow.get(i - 1).substring(1, wow.get(i - 1).length()))) / (Double.parseDouble(her));
                wow.set(i - 1, wow.get(i - 1).substring(0, 1) + hoo);
                wow.remove(i);
                i--;
            }

            i++;
        }

        i = 1;
        hoo = 0.0;
        while (i < wow.size()) {
            String how = wow.get(i);
            String hew = how.substring(0, 1);
            String her = how.substring(1, wow.get(i).length());
            if (hew.equals("+")) {
                hoo = (Double.parseDouble(wow.get(i - 1).substring(1, wow.get(i - 1).length()))) + (Double.parseDouble(her));
                wow.set(i - 1, wow.get(i - 1).substring(0, 1) + hoo);
                wow.remove(i);
                i--;

            } else if (hew.equals("-")) {
                hoo = (Double.parseDouble(wow.get(i - 1).substring(1, wow.get(i - 1).length()))) - (Double.parseDouble(her));
                wow.set(i - 1, wow.get(i - 1).substring(0, 1) + hoo);
                wow.remove(i);
                i--;
            }

            i++;
        }

        wow.set(0, wow.get(0).substring(1, wow.get(0).length()));

    return wow.get(0);
    }

}
