package com.bebs.wardrobepicker;

import androidx.room.TypeConverter;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;

public class Converters {
    @TypeConverter
    public static String fromIntArray(ArrayList<Integer> values){
        StringBuilder res = new StringBuilder();
        if (values != null && values.size() > 0){
            for (int value:values) {
                res.append(";");
                res.append(value);
            }
        }
        return res.toString();
    }

    @TypeConverter
    public static ArrayList<Integer> stringToIntArray(String value){
        ArrayList<Integer> res = new ArrayList<>();
        if (!value.equals("")){
            String[] split = value.split(";");

            for (String string:split) {
                if (!string.equals("")){
                    res.add(Integer.parseInt(string));
                }
            }
        } else {
            res.add(0);
        }
        return res;
    }

    @TypeConverter
    public static String fromStringArray(ArrayList<String> values){
        StringBuilder res = new StringBuilder();
        if (values != null && values.size() > 0){
            for (String string:values) {
                res.append(";");
                res.append(string);
            }
        }
        return res.toString();
    }

    @TypeConverter
    public static ArrayList<String> stringToStringArray(String value){
        ArrayList<String> res = new ArrayList<>();
        if (!value.equals("")){
            String[] split = value.split(";");
            Collections.addAll(res, split);
        } else {
            res.add("");
        }
        return res;
    }

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
