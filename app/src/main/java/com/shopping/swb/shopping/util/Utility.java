package com.shopping.swb.shopping.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-04-29
 * Time: 21:23
 */
public class Utility {

    public static void saveData(Context context, String name, List<String> list) {
        String data = new Gson().toJson(list);
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(name, data).apply();
    }

    public static List<String> getData(Context context, String name) {
        String data = PreferenceManager.getDefaultSharedPreferences(context).getString(name, "");
        return new Gson().fromJson(data, new TypeToken<List<String>>() {
        }.getType());

    }

    public static <T> List<T> getGoodsList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }

    public static <T> T getGoods(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }

    public static PackageInfo getPackageInfo(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
            return info;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
