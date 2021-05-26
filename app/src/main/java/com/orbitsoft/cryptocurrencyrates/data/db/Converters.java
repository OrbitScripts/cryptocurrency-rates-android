package com.orbitsoft.cryptocurrencyrates.data.db;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class Converters {

    private static final String LIST_SEPARATOR = ";";

    @TypeConverter
    public static String fromListOfFloatsToString(List<Float> list) {
        StringBuilder str = new StringBuilder();
        for (Float value : list) {
            if (value == null) continue;
            str.append((str.length() == 0) ? "" : LIST_SEPARATOR);
            str.append(value.toString());
        }
        return str.toString();
    }

    @TypeConverter
    public static List<Float> fromStringToListOfFloats(String str) {
        ArrayList<Float> list = new ArrayList<>();
        String[] listStr = str.split(LIST_SEPARATOR);
        for (String value : listStr) {
            list.add(Float.parseFloat(value));
        }
        return list;
    }
}
