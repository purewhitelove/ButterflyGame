// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ModHelper.java

package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.daily.mods.*;
import com.megacrit.cardcrawl.metrics.BotDataUploader;
import java.util.*;

public class ModHelper
{

    public ModHelper()
    {
    }

    public static void initialize()
    {
        addStarterMod(new Shiny());
        addStarterMod(new Allstar());
        addStarterMod(new Draft());
        addStarterMod(new SealedDeck());
        addStarterMod(new Insanity());
        addStarterMod(new Heirloom());
        addStarterMod(new Specialized());
        addStarterMod(new Chimera());
        addStarterMod(new CursedRun());
        addGenericMod(new Diverse());
        addGenericMod(new RedCards());
        addGenericMod(new GreenCards());
        addGenericMod(new BlueCards());
        addGenericMod(new PurpleCards());
        addGenericMod(new ColorlessCards());
        addGenericMod(new TimeDilation());
        addGenericMod(new Vintage());
        addGenericMod(new Hoarder());
        addGenericMod(new Flight());
        addGenericMod(new CertainFuture());
        addGenericMod(new ControlledChaos());
        addDifficultyMod(new BigGameHunter());
        addDifficultyMod(new Lethality());
        addDifficultyMod(new NightTerrors());
        addDifficultyMod(new Binary());
        addDifficultyMod(new Midas());
        addDifficultyMod(new Terminal());
        addDifficultyMod(new DeadlyEvents());
        addLegacyMod(new Brewmaster());
        addLegacyMod(new Colossus());
    }

    private static void addStarterMod(AbstractDailyMod mod)
    {
        starterMods.put(mod.modID, mod);
    }

    private static void addGenericMod(AbstractDailyMod mod)
    {
        genericMods.put(mod.modID, mod);
    }

    private static void addDifficultyMod(AbstractDailyMod mod)
    {
        difficultyMods.put(mod.modID, mod);
    }

    private static void addLegacyMod(AbstractDailyMod mod)
    {
        legacyMods.put(mod.modID, mod);
    }

    public static void setMods(List modIDs)
    {
        setModsFalse();
        Iterator iterator = modIDs.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String m = (String)iterator.next();
            if(!m.equals("Endless"))
                enabledMods.add(getMod(m));
        } while(true);
    }

    public static AbstractDailyMod getMod(String key)
    {
        AbstractDailyMod mod = (AbstractDailyMod)starterMods.get(key);
        if(mod == null)
            mod = (AbstractDailyMod)genericMods.get(key);
        if(mod == null)
            mod = (AbstractDailyMod)difficultyMods.get(key);
        if(mod == null)
            mod = (AbstractDailyMod)legacyMods.get(key);
        return mod;
    }

    public static ArrayList getEnabledModIDs()
    {
        ArrayList enabled = new ArrayList();
        Iterator iterator = enabledMods.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractDailyMod m = (AbstractDailyMod)iterator.next();
            if(m != null)
                enabled.add(m.modID);
        } while(true);
        return enabled;
    }

    private static void setTheMods(HashMap modMap, long daysSince1970, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass characterClass)
    {
        ArrayList shuffledList = new ArrayList();
        Iterator iterator = modMap.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry m = (java.util.Map.Entry)iterator.next();
            if(((AbstractDailyMod)m.getValue()).classToExclude != characterClass)
                shuffledList.add(m.getValue());
        } while(true);
        int rotationConstant = 5;
        int modSelectionIndex = (int)(daysSince1970 % (long)rotationConstant);
        if(modSelectionIndex < 0)
            modSelectionIndex += rotationConstant;
        int shuffleInterval = (int)(daysSince1970 / (long)rotationConstant);
        Collections.shuffle(shuffledList, new Random(shuffleInterval));
        enabledMods.add(shuffledList.get(modSelectionIndex));
    }

    public static void setTodaysMods(long daysSince1970, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass chosenClass)
    {
        setModsFalse();
        setTheMods(starterMods, daysSince1970, chosenClass);
        setTheMods(genericMods, daysSince1970, chosenClass);
        setTheMods(difficultyMods, daysSince1970, chosenClass);
    }

    public static boolean isModEnabled(String modID)
    {
        for(Iterator iterator = enabledMods.iterator(); iterator.hasNext();)
        {
            AbstractDailyMod m = (AbstractDailyMod)iterator.next();
            if(m != null && m.modID != null && m.modID.equals(modID))
                return true;
        }

        return false;
    }

    public static void setModsFalse()
    {
        enabledMods.clear();
    }

    public static void uploadModData()
    {
        ArrayList data = new ArrayList();
        java.util.Map.Entry m;
        for(Iterator iterator = starterMods.entrySet().iterator(); iterator.hasNext(); data.add(((AbstractDailyMod)m.getValue()).gameDataUploadData()))
            m = (java.util.Map.Entry)iterator.next();

        java.util.Map.Entry m;
        for(Iterator iterator1 = genericMods.entrySet().iterator(); iterator1.hasNext(); data.add(((AbstractDailyMod)m.getValue()).gameDataUploadData()))
            m = (java.util.Map.Entry)iterator1.next();

        java.util.Map.Entry m;
        for(Iterator iterator2 = difficultyMods.entrySet().iterator(); iterator2.hasNext(); data.add(((AbstractDailyMod)m.getValue()).gameDataUploadData()))
            m = (java.util.Map.Entry)iterator2.next();

        BotDataUploader.uploadDataAsync(com.megacrit.cardcrawl.metrics.BotDataUploader.GameDataType.DAILY_MOD_DATA, AbstractDailyMod.gameDataUploadHeader(), data);
    }

    public static void clearNulls()
    {
        Iterator m = enabledMods.iterator();
        do
        {
            if(!m.hasNext())
                break;
            AbstractDailyMod derp = (AbstractDailyMod)m.next();
            if(derp == null)
                m.remove();
        } while(true);
    }

    private static HashMap starterMods = new HashMap();
    private static HashMap genericMods = new HashMap();
    private static HashMap difficultyMods = new HashMap();
    private static HashMap legacyMods = new HashMap();
    public static ArrayList enabledMods = new ArrayList();

}
