package melon.im.util;


import java.util.ArrayList;
import java.util.List;

import melon.im.UrlConstantV2;

/**
 * Created by melon on 2017/3/22.
 */
public class V2CovertUtils {

    /**
     * 文理
     */

    private static final String wenStr = "wen";
    private static final String liStr = "li";

    private static final String wenC = "文科";
    private static final String liC = "理科";

    private static final int wenInt = 1;
    private static final int liInt = 2;

    public static int convertWenli(String wenli){


        if (wenli.equals("1")||wenli.equals("2")){
            return Integer.parseInt(wenli);
        }

        switch (wenli){
            case wenStr:{
                return wenInt;
            }
            case liStr:{
                return liInt;
            }
        }
        return liInt;
    }

    public static String convertWenli(int wenli){
        switch (wenli){
            case wenInt:{
                return wenStr;
            }
            case liInt:{
                return liStr;
            }
        }
        return liStr;
    }

    public static String convertWenliStr(int course){
        return convertWenliStr(convertWenli(course));
    }

    public static String convertWenliStr(String course){
        if (course == null){
            return "";
        }
        return course.equals(liStr)?liC:wenC;
    }

}
