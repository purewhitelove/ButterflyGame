// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MonsterInfo.java

package com.megacrit.cardcrawl.monsters;

import com.megacrit.cardcrawl.core.Settings;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MonsterInfo
    implements Comparable
{

    public MonsterInfo(String name, float weight)
    {
        this.name = name;
        this.weight = weight;
    }

    public static void normalizeWeights(ArrayList list)
    {
        Collections.sort(list);
        float total = 0.0F;
        for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            MonsterInfo i = (MonsterInfo)iterator.next();
            total += i.weight;
        }

        Iterator iterator1 = list.iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            MonsterInfo i = (MonsterInfo)iterator1.next();
            i.weight /= total;
            if(Settings.isInfo)
                logger.info((new StringBuilder()).append(i.name).append(": ").append(i.weight).append("%").toString());
        } while(true);
    }

    public static String roll(ArrayList list, float roll)
    {
        float currentWeight = 0.0F;
        for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            MonsterInfo i = (MonsterInfo)iterator.next();
            currentWeight += i.weight;
            if(roll < currentWeight)
                return i.name;
        }

        return "ERROR";
    }

    public int compareTo(MonsterInfo other)
    {
        return Float.compare(weight, other.weight);
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((MonsterInfo)obj);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/monsters/MonsterInfo.getName());
    public String name;
    public float weight;

}
