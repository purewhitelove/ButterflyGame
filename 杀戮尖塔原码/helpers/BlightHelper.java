// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlightHelper.java

package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.blights.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.BotDataUploader;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlightHelper
{

    public BlightHelper()
    {
    }

    public static void initialize()
    {
        blights.clear();
        blights.add("DeadlyEnemies");
        blights.add("ToughEnemies");
        blights.add("TimeMaze");
        blights.add("MimicInfestation");
        blights.add("FullBelly");
        blights.add("GrotesqueTrophy");
        blights.add("Accursed");
        blights.add("Scatterbrain");
        blights.add("TwistingMind");
        blights.add("BlightedDurian");
        blights.add("VoidEssence");
        blights.add("GraspOfShadows");
        blights.add("MetallicRebirth");
        chestBlights.clear();
        chestBlights.add("Accursed");
        chestBlights.add("Scatterbrain");
        chestBlights.add("TwistingMind");
        chestBlights.add("BlightedDurian");
        chestBlights.add("VoidEssence");
        chestBlights.add("GraspOfShadows");
        chestBlights.add("MetallicRebirth");
    }

    public static AbstractBlight getBlight(String id)
    {
        String s = id;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -452041771: 
            if(s.equals("DeadlyEnemies"))
                byte0 = 0;
            break;

        case -1718824437: 
            if(s.equals("ToughEnemies"))
                byte0 = 1;
            break;

        case -2012937140: 
            if(s.equals("TimeMaze"))
                byte0 = 2;
            break;

        case -1215673339: 
            if(s.equals("MimicInfestation"))
                byte0 = 3;
            break;

        case 299752039: 
            if(s.equals("FullBelly"))
                byte0 = 4;
            break;

        case 1139469619: 
            if(s.equals("GrotesqueTrophy"))
                byte0 = 5;
            break;

        case 259154541: 
            if(s.equals("TwistingMind"))
                byte0 = 6;
            break;

        case -1965007080: 
            if(s.equals("Scatterbrain"))
                byte0 = 7;
            break;

        case -2067046188: 
            if(s.equals("Accursed"))
                byte0 = 8;
            break;

        case 2007188552: 
            if(s.equals("BlightedDurian"))
                byte0 = 9;
            break;

        case 1635946876: 
            if(s.equals("VoidEssence"))
                byte0 = 10;
            break;

        case -1971148503: 
            if(s.equals("GraspOfShadows"))
                byte0 = 11;
            break;

        case -1602615507: 
            if(s.equals("MetallicRebirth"))
                byte0 = 12;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new Spear();

        case 1: // '\001'
            return new Shield();

        case 2: // '\002'
            return new TimeMaze();

        case 3: // '\003'
            return new MimicInfestation();

        case 4: // '\004'
            return new Muzzle();

        case 5: // '\005'
            return new GrotesqueTrophy();

        case 6: // '\006'
            return new TwistingMind();

        case 7: // '\007'
            return new Scatterbrain();

        case 8: // '\b'
            return new Accursed();

        case 9: // '\t'
            return new Durian();

        case 10: // '\n'
            return new VoidEssence();

        case 11: // '\013'
            return new Hauntings();

        case 12: // '\f'
            return new AncientAugmentation();
        }
        logger.info((new StringBuilder()).append("MISSING KEY: ").append(id).toString());
        return null;
    }

    public static AbstractBlight getRandomChestBlight(ArrayList exclusion)
    {
        ArrayList blightTmp = new ArrayList();
        String randomKey = chestBlights.iterator();
        do
        {
            if(!randomKey.hasNext())
                break;
            String s = (String)randomKey.next();
            boolean exclude = false;
            Iterator iterator = exclusion.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                String s2 = (String)iterator.next();
                if(s.equals(s2))
                {
                    logger.info((new StringBuilder()).append(s).append(" EXCLUDED").toString());
                    exclude = true;
                }
            } while(true);
            if(!exclude)
                blightTmp.add(s);
        } while(true);
        randomKey = (String)blightTmp.get(AbstractDungeon.relicRng.random(blightTmp.size() - 1));
        return getBlight(randomKey);
    }

    public static AbstractBlight getRandomBlight(Random rng)
    {
        String randomKey = (String)blights.get(rng.random(blights.size() - 1));
        return getBlight(randomKey);
    }

    public static AbstractBlight getRandomBlight()
    {
        String randomKey = (String)chestBlights.get(AbstractDungeon.relicRng.random(chestBlights.size() - 1));
        if(AbstractDungeon.player.maxHealth <= 20)
            for(; randomKey.equals("BlightedDurian"); randomKey = (String)chestBlights.get(AbstractDungeon.relicRng.random(chestBlights.size() - 1)));
        return getBlight(randomKey);
    }

    public static void uploadBlightData()
    {
        ArrayList data = new ArrayList();
        if(blights.isEmpty())
            initialize();
        ArrayList allBlights = new ArrayList(blights);
        allBlights.addAll(chestBlights);
        Iterator iterator = allBlights.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String b = (String)iterator.next();
            AbstractBlight blight = getBlight(b);
            if(blight != null)
                data.add(blight.gameDataUploadData());
        } while(true);
        BotDataUploader.uploadDataAsync(com.megacrit.cardcrawl.metrics.BotDataUploader.GameDataType.BLIGHT_DATA, AbstractBlight.gameDataUploadHeader(), data);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/BlightHelper.getName());
    public static ArrayList blights = new ArrayList();
    public static ArrayList chestBlights = new ArrayList();

}
