// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Prefs.java

package com.megacrit.cardcrawl.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            AsyncSaver

public class Prefs
{

    public Prefs()
    {
        data = new HashMap();
    }

    public String getString(String key)
    {
        return (String)data.getOrDefault(key, "");
    }

    public String getString(String key, String def)
    {
        return (String)data.getOrDefault(key, def);
    }

    public void putString(String key, String value)
    {
        data.put(key, value);
    }

    public int getInteger(String key)
    {
        if(data.containsKey(key))
            return Integer.valueOf(((String)data.get(key)).trim()).intValue();
        else
            return -999;
    }

    public int getInteger(String key, int def)
    {
        if(data.containsKey(key))
            return Integer.valueOf(((String)data.get(key)).trim()).intValue();
        else
            return def;
    }

    public void putInteger(String key, int value)
    {
        data.put(key, Integer.toString(value));
    }

    public float getFloat(String key, float def)
    {
        if(data.containsKey(key))
            return Float.valueOf(((String)data.get(key)).trim()).floatValue();
        else
            return def;
    }

    public void putFloat(String key, float value)
    {
        data.put(key, Float.toString(value));
    }

    public long getLong(String key, long def)
    {
        if(data.containsKey(key))
            return Long.valueOf(((String)data.get(key)).trim()).longValue();
        else
            return def;
    }

    public void putLong(String key, long value)
    {
        data.put(key, Long.toString(value));
    }

    public boolean getBoolean(String key, boolean def)
    {
        if(data.containsKey(key))
            return Boolean.valueOf(((String)data.get(key)).trim()).booleanValue();
        else
            return def;
    }

    public boolean getBoolean(String key)
    {
        return Boolean.valueOf(((String)data.get(key)).trim()).booleanValue();
    }

    public void putBoolean(String key, boolean value)
    {
        data.put(key, Boolean.toString(value));
    }

    public void flush()
    {
        Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
        AsyncSaver.save(filepath, gson.toJson(data));
    }

    public Map get()
    {
        return data;
    }

    public Map data;
    public String filepath;
}
