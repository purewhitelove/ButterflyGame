// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TipTracker.java

package com.megacrit.cardcrawl.helpers;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            SaveHelper, Prefs

public class TipTracker
{

    public TipTracker()
    {
    }

    public static void initialize()
    {
        pref = SaveHelper.getPrefs("STSTips");
        refresh();
    }

    public static void refresh()
    {
        tips.clear();
        tips.put("NEOW_SKIP", Boolean.valueOf(pref.getBoolean("NEOW_SKIP", false)));
        tips.put("NEOW_INTRO", Boolean.valueOf(pref.getBoolean("NEOW_INTRO", false)));
        tips.put("NO_FTUE", Boolean.valueOf(pref.getBoolean("NO_FTUE", false)));
        tips.put("COMBAT_TIP", Boolean.valueOf(pref.getBoolean("COMBAT_TIP", false)));
        tips.put("BLOCK TIP", Boolean.valueOf(pref.getBoolean("BLOCK TIP", false)));
        tips.put("POWER_TIP", Boolean.valueOf(pref.getBoolean("POWER_TIP", false)));
        tips.put("M_POWER_TIP", Boolean.valueOf(pref.getBoolean("M_POWER_TIP", false)));
        tips.put("ENERGY_USE_TIP", Boolean.valueOf(pref.getBoolean("ENERGY_USE_TIP", false)));
        if(((Boolean)tips.get("ENERGY_USE_TIP")).booleanValue())
            energyUseCounter = 9;
        else
            energyUseCounter = 0;
        tips.put("SHUFFLE_TIP", Boolean.valueOf(pref.getBoolean("SHUFFLE_TIP", false)));
        if(((Boolean)tips.get("SHUFFLE_TIP")).booleanValue())
            shuffleCounter = 99;
        else
            shuffleCounter = 0;
        shuffleCounter = 0;
        tips.put("POTION_TIP", Boolean.valueOf(pref.getBoolean("POTION_TIP", false)));
        tips.put("CARD_REWARD_TIP", Boolean.valueOf(pref.getBoolean("CARD_REWARD_TIP", false)));
        tips.put("INTENT_TIP", Boolean.valueOf(pref.getBoolean("INTENT_TIP", false)));
        blockCounter = 0;
        tips.put("RELIC_TIP", Boolean.valueOf(pref.getBoolean("RELIC_TIP", false)));
        if(((Boolean)tips.get("RELIC_TIP")).booleanValue())
            relicCounter = 99;
        else
            relicCounter = 0;
    }

    public static void neverShowAgain(String key)
    {
        logger.info((new StringBuilder()).append(key).append(" will never be shown again!").toString());
        pref.putBoolean(key, true);
        tips.put(key, Boolean.valueOf(true));
        pref.flush();
    }

    public static void showAgain(String key)
    {
        logger.info((new StringBuilder()).append(key).append(" is reactivated").toString());
        pref.putBoolean(key, false);
        tips.put(key, Boolean.valueOf(false));
        pref.flush();
    }

    public static void disableAllFtues()
    {
        neverShowAgain("BLOCK TIP");
        neverShowAgain("CARD_REWARD_TIP");
        neverShowAgain("COMBAT_TIP");
        neverShowAgain("ENERGY_USE_TIP");
        neverShowAgain("INTENT_TIP");
        neverShowAgain("M_POWER_TIP");
        neverShowAgain("NO_FTUE");
        neverShowAgain("POTION_TIP");
        neverShowAgain("POWER_TIP");
        neverShowAgain("RELIC_TIP");
        neverShowAgain("SHUFFLE_TIP");
    }

    public static void reset()
    {
        java.util.Map.Entry c;
        for(Iterator iterator = tips.entrySet().iterator(); iterator.hasNext(); showAgain((String)c.getKey()))
            c = (java.util.Map.Entry)iterator.next();

    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/TipTracker.getName());
    public static Prefs pref;
    public static HashMap tips = new HashMap();
    public static final String NEOW_SKIP = "NEOW_SKIP";
    public static final String NEOW_INTRO = "NEOW_INTRO";
    public static final String NO_FTUE_CHECK = "NO_FTUE";
    public static final String COMBAT_TIP = "COMBAT_TIP";
    public static final String BLOCK_TIP = "BLOCK TIP";
    public static final String POWER_TIP = "POWER_TIP";
    public static final String M_POWER_TIP = "M_POWER_TIP";
    public static final String ENERGY_USE_TIP = "ENERGY_USE_TIP";
    public static int energyUseCounter = 0;
    public static final String SHUFFLE_TIP = "SHUFFLE_TIP";
    public static int shuffleCounter = 0;
    public static final int SHUFFLE_THRESHOLD = 1;
    public static final String POTION_TIP = "POTION_TIP";
    public static final String CARD_REWARD_TIP = "CARD_REWARD_TIP";
    public static final String INTENT_TIP = "INTENT_TIP";
    public static int blockCounter = 0;
    public static final int BLOCK_THRESHOLD = 3;
    public static final String RELIC_TIP = "RELIC_TIP";
    public static int relicCounter = 0;

}
