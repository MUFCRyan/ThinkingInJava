package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page586 Chapter 18.14 Preferences
 */

public class PreferencesDemo {
    public static void main(String[] args) throws BackingStoreException {
        Preferences preferences = Preferences.userNodeForPackage(PreferencesDemo.class);
        preferences.put("Location", "Beijing");
        preferences.put("Footwear", "SportShoes");
        preferences.putInt("Companions", 4);
        preferences.putBoolean("Are there witches", true);
        int usageCount = preferences.getInt("usageCount", 0);
        usageCount++;
        preferences.putInt("usageCount", usageCount);
        for (String key : preferences.keys()) {
            Util.println(key + ": " + preferences.get(key, null));
        }
        Util.println("How many companions does Dorothy have? " + preferences.getInt("Companions", 0));
    }
}
