// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnlockTracker.java

package com.megacrit.cardcrawl.unlock;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.stats.*;
import com.megacrit.cardcrawl.unlock.cards.defect.EchoFormUnlock;
import com.megacrit.cardcrawl.unlock.cards.defect.HyperbeamUnlock;
import com.megacrit.cardcrawl.unlock.cards.defect.MeteorStrikeUnlock;
import com.megacrit.cardcrawl.unlock.cards.defect.NovaUnlock;
import com.megacrit.cardcrawl.unlock.cards.defect.ReboundUnlock;
import com.megacrit.cardcrawl.unlock.cards.defect.RecycleUnlock;
import com.megacrit.cardcrawl.unlock.cards.defect.SunderUnlock;
import com.megacrit.cardcrawl.unlock.cards.defect.TurboUnlock;
import com.megacrit.cardcrawl.unlock.cards.defect.UndoUnlock;
import com.megacrit.cardcrawl.unlock.cards.ironclad.EvolveUnlock;
import com.megacrit.cardcrawl.unlock.cards.ironclad.ExhumeUnlock;
import com.megacrit.cardcrawl.unlock.cards.ironclad.HavocUnlock;
import com.megacrit.cardcrawl.unlock.cards.ironclad.HeavyBladeUnlock;
import com.megacrit.cardcrawl.unlock.cards.ironclad.ImmolateUnlock;
import com.megacrit.cardcrawl.unlock.cards.ironclad.LimitBreakUnlock;
import com.megacrit.cardcrawl.unlock.cards.ironclad.SentinelUnlock;
import com.megacrit.cardcrawl.unlock.cards.ironclad.SpotWeaknessUnlock;
import com.megacrit.cardcrawl.unlock.cards.ironclad.WildStrikeUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.AccuracyUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.BaneUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CatalystUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CloakAndDaggerUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.ConcentrateUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CorpseExplosionUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.GrandFinaleUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.SetupUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.StormOfSteelUnlock;
import com.megacrit.cardcrawl.unlock.cards.watcher.AlphaUnlock;
import com.megacrit.cardcrawl.unlock.cards.watcher.BlasphemyUnlock;
import com.megacrit.cardcrawl.unlock.cards.watcher.ClarityUnlock;
import com.megacrit.cardcrawl.unlock.cards.watcher.DevotionUnlock;
import com.megacrit.cardcrawl.unlock.cards.watcher.ForeignInfluenceUnlock;
import com.megacrit.cardcrawl.unlock.cards.watcher.ForesightUnlock;
import com.megacrit.cardcrawl.unlock.cards.watcher.MentalFortressUnlock;
import com.megacrit.cardcrawl.unlock.cards.watcher.ProstrateUnlock;
import com.megacrit.cardcrawl.unlock.cards.watcher.WishUnlock;
import com.megacrit.cardcrawl.unlock.misc.DefectUnlock;
import com.megacrit.cardcrawl.unlock.misc.TheSilentUnlock;
import com.megacrit.cardcrawl.unlock.misc.WatcherUnlock;
import com.megacrit.cardcrawl.unlock.relics.defect.CablesUnlock;
import com.megacrit.cardcrawl.unlock.relics.defect.DataDiskUnlock;
import com.megacrit.cardcrawl.unlock.relics.defect.EmotionChipUnlock;
import com.megacrit.cardcrawl.unlock.relics.defect.RunicCapacitorUnlock;
import com.megacrit.cardcrawl.unlock.relics.defect.TurnipUnlock;
import com.megacrit.cardcrawl.unlock.relics.defect.VirusUnlock;
import com.megacrit.cardcrawl.unlock.relics.ironclad.BlueCandleUnlock;
import com.megacrit.cardcrawl.unlock.relics.ironclad.DeadBranchUnlock;
import com.megacrit.cardcrawl.unlock.relics.ironclad.OmamoriUnlock;
import com.megacrit.cardcrawl.unlock.relics.ironclad.PrayerWheelUnlock;
import com.megacrit.cardcrawl.unlock.relics.ironclad.ShovelUnlock;
import com.megacrit.cardcrawl.unlock.relics.ironclad.SingingBowlUnlock;
import com.megacrit.cardcrawl.unlock.relics.silent.ArtOfWarUnlock;
import com.megacrit.cardcrawl.unlock.relics.silent.CourierUnlock;
import com.megacrit.cardcrawl.unlock.relics.silent.DuvuDollUnlock;
import com.megacrit.cardcrawl.unlock.relics.silent.PandorasBoxUnlock;
import com.megacrit.cardcrawl.unlock.relics.silent.SmilingMaskUnlock;
import com.megacrit.cardcrawl.unlock.relics.silent.TinyChestUnlock;
import com.megacrit.cardcrawl.unlock.relics.watcher.AkabekoUnlock;
import com.megacrit.cardcrawl.unlock.relics.watcher.CeramicFishUnlock;
import com.megacrit.cardcrawl.unlock.relics.watcher.CloakClaspUnlock;
import com.megacrit.cardcrawl.unlock.relics.watcher.StrikeDummyUnlock;
import com.megacrit.cardcrawl.unlock.relics.watcher.TeardropUnlock;
import com.megacrit.cardcrawl.unlock.relics.watcher.YangUnlock;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.unlock:
//            AbstractUnlock

public class UnlockTracker
{

    public UnlockTracker()
    {
    }

    public static void initialize()
    {
        achievementPref = SaveHelper.getPrefs("STSAchievements");
        unlockPref = SaveHelper.getPrefs("STSUnlocks");
        unlockProgress = SaveHelper.getPrefs("STSUnlockProgress");
        seenPref = SaveHelper.getPrefs("STSSeenCards");
        betaCardPref = SaveHelper.getPrefs("STSBetaCardPreference");
        bossSeenPref = SaveHelper.getPrefs("STSSeenBosses");
        relicSeenPref = SaveHelper.getPrefs("STSSeenRelics");
        refresh();
    }

    public static void retroactiveUnlock()
    {
        ArrayList cardKeys = new ArrayList();
        ArrayList relicKeys = new ArrayList();
        ArrayList bundle = new ArrayList();
        appendRetroactiveUnlockList(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD, unlockProgress.getInteger((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.toString()).append("UnlockLevel").toString(), -1), bundle, cardKeys, relicKeys);
        appendRetroactiveUnlockList(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT, unlockProgress.getInteger((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.toString()).append("UnlockLevel").toString(), -1), bundle, cardKeys, relicKeys);
        appendRetroactiveUnlockList(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT, unlockProgress.getInteger((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.toString()).append("UnlockLevel").toString(), -1), bundle, cardKeys, relicKeys);
        appendRetroactiveUnlockList(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER, unlockProgress.getInteger((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER.toString()).append("UnlockLevel").toString(), -1), bundle, cardKeys, relicKeys);
        boolean changed = false;
        Iterator iterator = cardKeys.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String k = (String)iterator.next();
            if(unlockPref.getInteger(k) != 2)
            {
                unlockPref.putInteger(k, 2);
                changed = true;
                logger.info((new StringBuilder()).append("RETROACTIVE CARD UNLOCK:  ").append(k).toString());
            }
        } while(true);
        iterator = relicKeys.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String k = (String)iterator.next();
            if(unlockPref.getInteger(k) != 2)
            {
                unlockPref.putInteger(k, 2);
                changed = true;
                logger.info((new StringBuilder()).append("RETROACTIVE RELIC UNLOCK: ").append(k).toString());
            }
        } while(true);
        if(isCharacterLocked("Watcher") && !isCharacterLocked("Defect") && (isAchievementUnlocked("RUBY") || isAchievementUnlocked("EMERALD") || isAchievementUnlocked("SAPPHIRE")))
        {
            unlockPref.putInteger("Watcher", 2);
            lockedCharacters.remove("Watcher");
            changed = true;
        }
        if(changed)
        {
            logger.info("RETRO UNLOCKED, SAVING");
            unlockPref.flush();
        }
    }

    private static void appendRetroactiveUnlockList(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c, int lvl, ArrayList bundle, ArrayList cardKeys, ArrayList relicKeys)
    {
label0:
        for(; lvl > 0; lvl--)
        {
            bundle = getUnlockBundle(c, lvl - 1);
            Iterator iterator = bundle.iterator();
            do
            {
                if(!iterator.hasNext())
                    continue label0;
                AbstractUnlock u = (AbstractUnlock)iterator.next();
                if(u.type == AbstractUnlock.UnlockType.RELIC)
                {
                    logger.info((new StringBuilder()).append(u.key).append(" should be unlocked.").toString());
                    relicKeys.add(u.key);
                } else
                if(u.type == AbstractUnlock.UnlockType.CARD)
                {
                    logger.info((new StringBuilder()).append(u.key).append(" should be unlocked.").toString());
                    cardKeys.add(u.key);
                }
            } while(true);
        }

    }

    public static void refresh()
    {
        lockedCards.clear();
        lockedCharacters.clear();
        lockedLoadouts.clear();
        lockedRelics.clear();
        addCard("Havoc");
        addCard("Sentinel");
        addCard("Exhume");
        addCard("Wild Strike");
        addCard("Evolve");
        addCard("Immolate");
        addCard("Heavy Blade");
        addCard("Spot Weakness");
        addCard("Limit Break");
        addCard("Concentrate");
        addCard("Setup");
        addCard("Grand Finale");
        addCard("Cloak And Dagger");
        addCard("Accuracy");
        addCard("Storm of Steel");
        addCard("Bane");
        addCard("Catalyst");
        addCard("Corpse Explosion");
        addCard("Rebound");
        addCard("Undo");
        addCard("Echo Form");
        addCard("Turbo");
        addCard("Sunder");
        addCard("Meteor Strike");
        addCard("Hyperbeam");
        addCard("Recycle");
        addCard("Core Surge");
        addCard("Prostrate");
        addCard("Blasphemy");
        addCard("Devotion");
        addCard("ForeignInfluence");
        addCard("Alpha");
        addCard("MentalFortress");
        addCard("SpiritShield");
        addCard("Wish");
        addCard("Wireheading");
        addCharacter("The Silent");
        addCharacter("Defect");
        addCharacter("Watcher");
        addRelic("Omamori");
        addRelic("Prayer Wheel");
        addRelic("Shovel");
        addRelic("Art of War");
        addRelic("The Courier");
        addRelic("Pandora's Box");
        addRelic("Blue Candle");
        addRelic("Dead Branch");
        addRelic("Singing Bowl");
        addRelic("Du-Vu Doll");
        addRelic("Smiling Mask");
        addRelic("Tiny Chest");
        addRelic("Cables");
        addRelic("DataDisk");
        addRelic("Emotion Chip");
        addRelic("Runic Capacitor");
        addRelic("Turnip");
        addRelic("Symbiotic Virus");
        addRelic("Akabeko");
        addRelic("Yang");
        addRelic("CeramicFish");
        addRelic("StrikeDummy");
        addRelic("TeardropLocket");
        addRelic("CloakClasp");
        countUnlockedCards();
    }

    public static int incrementUnlockRamp(int currentCost)
    {
        switch(currentCost)
        {
        case 300: 
            return 750;

        case 500: 
            return 1000;

        case 750: 
            return 1000;

        case 1000: 
            return 1500;

        case 1500: 
            return 2000;

        case 2000: 
            return 2500;

        case 2500: 
            return 3000;

        case 3000: 
            return 3000;

        case 4000: 
            return 4000;
        }
        return currentCost + 250;
    }

    public static void resetUnlockProgress(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        unlockProgress.putInteger((new StringBuilder()).append(c.toString()).append("UnlockLevel").toString(), 0);
        unlockProgress.putInteger((new StringBuilder()).append(c.toString()).append("Progress").toString(), 0);
        unlockProgress.putInteger((new StringBuilder()).append(c.toString()).append("CurrentCost").toString(), 300);
        unlockProgress.putInteger((new StringBuilder()).append(c.toString()).append("TotalScore").toString(), 0);
        unlockProgress.putInteger((new StringBuilder()).append(c.toString()).append("HighScore").toString(), 0);
    }

    public static int getUnlockLevel(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        return unlockProgress.getInteger((new StringBuilder()).append(c.toString()).append("UnlockLevel").toString(), 0);
    }

    public static int getCurrentProgress(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        return unlockProgress.getInteger((new StringBuilder()).append(c.toString()).append("Progress").toString(), 0);
    }

    public static int getCurrentScoreCost(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        return unlockProgress.getInteger((new StringBuilder()).append(c.toString()).append("CurrentCost").toString(), 300);
    }

    public static void addScore(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c, int scoreGained)
    {
        String key_unlock_level = (new StringBuilder()).append(c.toString()).append("UnlockLevel").toString();
        String key_progress = (new StringBuilder()).append(c.toString()).append("Progress").toString();
        String key_current_cost = (new StringBuilder()).append(c.toString()).append("CurrentCost").toString();
        String key_total_score = (new StringBuilder()).append(c.toString()).append("TotalScore").toString();
        String key_high_score = (new StringBuilder()).append(c.toString()).append("HighScore").toString();
        logger.info("Keys");
        logger.info(key_unlock_level);
        logger.info(key_progress);
        logger.info(key_current_cost);
        logger.info(key_total_score);
        logger.info(key_high_score);
        int p = unlockProgress.getInteger(key_progress, 0);
        p += scoreGained;
        if(p >= unlockProgress.getInteger(key_current_cost, 300))
        {
            logger.info("[DEBUG] Level up!");
            int lvl = unlockProgress.getInteger(key_unlock_level, 0);
            lvl++;
            unlockProgress.putInteger(key_unlock_level, lvl);
            p -= unlockProgress.getInteger(key_current_cost, 300);
            unlockProgress.putInteger(key_progress, p);
            logger.info((new StringBuilder()).append("[DEBUG] Score Progress: ").append(key_progress).toString());
            int current_cost = unlockProgress.getInteger(key_current_cost, 300);
            unlockProgress.putInteger(key_current_cost, incrementUnlockRamp(current_cost));
            if(p > unlockProgress.getInteger(key_current_cost, 300))
            {
                unlockProgress.putInteger(key_progress, unlockProgress.getInteger(key_current_cost, 300) - 1);
                logger.info("Overfloat maxes out next level");
            }
        } else
        {
            unlockProgress.putInteger(key_progress, p);
        }
        int total = unlockProgress.getInteger(key_total_score, 0);
        total += scoreGained;
        unlockProgress.putInteger(key_total_score, total);
        logger.info((new StringBuilder()).append("[DEBUG] Total score: ").append(total).toString());
        int highscore = unlockProgress.getInteger(key_high_score, 0);
        if(scoreGained > highscore)
        {
            unlockProgress.putInteger(key_high_score, scoreGained);
            logger.info((new StringBuilder()).append("[DEBUG] New high score: ").append(scoreGained).toString());
        }
        unlockProgress.flush();
    }

    public static void countUnlockedCards()
    {
        ArrayList tmp = new ArrayList();
        int count = 0;
        tmp.add("Havoc");
        tmp.add("Sentinel");
        tmp.add("Exhume");
        tmp.add("Wild Strike");
        tmp.add("Evolve");
        tmp.add("Immolate");
        tmp.add("Heavy Blade");
        tmp.add("Spot Weakness");
        tmp.add("Limit Break");
        Iterator iterator = tmp.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            if(!isCardLocked(s))
                count++;
        } while(true);
        lockedRedCardCount = tmp.size();
        unlockedRedCardCount = count;
        tmp.clear();
        count = 0;
        tmp.add("Concentrate");
        tmp.add("Setup");
        tmp.add("Grand Finale");
        tmp.add("Cloak And Dagger");
        tmp.add("Accuracy");
        tmp.add("Storm of Steel");
        tmp.add("Bane");
        tmp.add("Catalyst");
        tmp.add("Corpse Explosion");
        iterator = tmp.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            if(!isCardLocked(s))
                count++;
        } while(true);
        lockedGreenCardCount = tmp.size();
        unlockedGreenCardCount = count;
        tmp.clear();
        count = 0;
        tmp.add("Rebound");
        tmp.add("Undo");
        tmp.add("Echo Form");
        tmp.add("Turbo");
        tmp.add("Sunder");
        tmp.add("Meteor Strike");
        tmp.add("Hyperbeam");
        tmp.add("Recycle");
        tmp.add("Core Surge");
        iterator = tmp.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            if(!isCardLocked(s))
                count++;
        } while(true);
        lockedBlueCardCount = tmp.size();
        unlockedBlueCardCount = count;
        tmp.clear();
        count = 0;
        tmp.add("Prostrate");
        tmp.add("Blasphemy");
        tmp.add("Devotion");
        tmp.add("ForeignInfluence");
        tmp.add("Alpha");
        tmp.add("MentalFortress");
        tmp.add("SpiritShield");
        tmp.add("Wish");
        tmp.add("Wireheading");
        iterator = tmp.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            if(!isCardLocked(s))
                count++;
        } while(true);
        lockedPurpleCardCount = tmp.size();
        unlockedPurpleCardCount = count;
        tmp.clear();
        count = 0;
        tmp.add("Omamori");
        tmp.add("Prayer Wheel");
        tmp.add("Shovel");
        tmp.add("Art of War");
        tmp.add("The Courier");
        tmp.add("Pandora's Box");
        tmp.add("Blue Candle");
        tmp.add("Dead Branch");
        tmp.add("Singing Bowl");
        tmp.add("Du-Vu Doll");
        tmp.add("Smiling Mask");
        tmp.add("Tiny Chest");
        tmp.add("Cables");
        tmp.add("DataDisk");
        tmp.add("Emotion Chip");
        tmp.add("Runic Capacitor");
        tmp.add("Turnip");
        tmp.add("Symbiotic Virus");
        tmp.add("Akabeko");
        tmp.add("Yang");
        tmp.add("CeramicFish");
        tmp.add("StrikeDummy");
        tmp.add("TeardropLocket");
        tmp.add("CloakClasp");
        iterator = tmp.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            if(!isRelicLocked(s))
                count++;
        } while(true);
        lockedRelicCount = tmp.size();
        unlockedRelicCount = count;
        logger.info((new StringBuilder()).append("RED UNLOCKS:   ").append(unlockedRedCardCount).append("/").append(lockedRedCardCount).toString());
        logger.info((new StringBuilder()).append("GREEN UNLOCKS: ").append(unlockedGreenCardCount).append("/").append(lockedGreenCardCount).toString());
        logger.info((new StringBuilder()).append("BLUE UNLOCKS: ").append(unlockedBlueCardCount).append("/").append(lockedBlueCardCount).toString());
        logger.info((new StringBuilder()).append("PURPLE UNLOCKS: ").append(unlockedPurpleCardCount).append("/").append(lockedPurpleCardCount).toString());
        logger.info((new StringBuilder()).append("RELIC UNLOCKS: ").append(unlockedRelicCount).append("/").append(lockedRelicCount).toString());
        logger.info((new StringBuilder()).append("CARDS SEEN:    ").append(seenPref.get().keySet().size()).append("/").append(CardLibrary.totalCardCount).toString());
        logger.info((new StringBuilder()).append("RELICS SEEN:   ").append(relicSeenPref.get().keySet().size()).append("/").append(RelicLibrary.totalRelicCount).toString());
    }

    public static String getCardsSeenString()
    {
        return (new StringBuilder()).append(CardLibrary.seenRedCards + CardLibrary.seenGreenCards + CardLibrary.seenBlueCards + CardLibrary.seenPurpleCards + CardLibrary.seenColorlessCards + CardLibrary.seenCurseCards).append("/").append(CardLibrary.totalCardCount).toString();
    }

    public static String getRelicsSeenString()
    {
        return (new StringBuilder()).append(RelicLibrary.seenRelics).append("/").append(RelicLibrary.totalRelicCount).toString();
    }

    public static void addCard(String key)
    {
        if(unlockPref.getString(key).equals("true"))
        {
            unlockPref.putInteger(key, 2);
            logger.info((new StringBuilder()).append("Converting ").append(key).append(" from bool to int").toString());
            unlockPref.flush();
        } else
        if(unlockPref.getString(key).equals("false"))
        {
            unlockPref.putInteger(key, 0);
            logger.info((new StringBuilder()).append("Converting ").append(key).append(" from bool to int").toString());
            unlockPref.flush();
        }
        if(unlockPref.getInteger(key, 0) != 2)
            lockedCards.add(key);
    }

    public static void addCharacter(String key)
    {
        if(unlockPref.getString(key).equals("true"))
        {
            unlockPref.putInteger(key, 2);
            logger.info((new StringBuilder()).append("Converting ").append(key).append(" from bool to int").toString());
            unlockPref.flush();
        } else
        if(unlockPref.getString(key).equals("false"))
        {
            unlockPref.putInteger(key, 0);
            logger.info((new StringBuilder()).append("Converting ").append(key).append(" from bool to int").toString());
            unlockPref.flush();
        }
        if(unlockPref.getInteger(key, 0) != 2)
            lockedCharacters.add(key);
    }

    public static void addRelic(String key)
    {
        if(unlockPref.getInteger(key, 0) != 2)
            lockedRelics.add(key);
    }

    public static void unlockAchievement(String key)
    {
        if(Settings.isModded || Settings.isShowBuild || !Settings.isStandardRun())
            return;
        CardCrawlGame.publisherIntegration.unlockAchievement(key);
        if(!achievementPref.getBoolean(key, false))
        {
            achievementPref.putBoolean(key, true);
            logger.info((new StringBuilder()).append("Achievement Unlocked: ").append(key).toString());
        }
        if(allAchievementsExceptPlatinumUnlocked() && !isAchievementUnlocked("ETERNAL_ONE"))
        {
            CardCrawlGame.publisherIntegration.unlockAchievement("ETERNAL_ONE");
            achievementPref.putBoolean("ETERNAL_ONE", true);
            logger.info("Achievement Unlocked: ETERNAL_ONE");
        }
        achievementPref.flush();
    }

    public static boolean allAchievementsExceptPlatinumUnlocked()
    {
        return achievementPref.data.entrySet().size() >= 45;
    }

    public static boolean isAchievementUnlocked(String key)
    {
        return achievementPref.getBoolean(key, false);
    }

    public static void unlockLuckyDay()
    {
        if(Settings.isModded)
            return;
        String key = "LUCKY_DAY";
        CardCrawlGame.publisherIntegration.unlockAchievement(key);
        if(!achievementPref.getBoolean(key, false))
        {
            achievementPref.putBoolean(key, true);
            achievementPref.flush();
            logger.info((new StringBuilder()).append("Achievement Unlocked: ").append(key).toString());
        }
    }

    public static void hardUnlock(String key)
    {
        if(Settings.isShowBuild)
            return;
        if(unlockPref.getInteger(key, 0) == 1)
        {
            unlockPref.putInteger(key, 2);
            unlockPref.flush();
            logger.info((new StringBuilder()).append("Hard Unlock: ").append(key).toString());
        }
    }

    public static void hardUnlockOverride(String key)
    {
        if(Settings.isShowBuild)
        {
            return;
        } else
        {
            unlockPref.putInteger(key, 2);
            unlockPref.flush();
            logger.info((new StringBuilder()).append("Hard Unlock: ").append(key).toString());
            return;
        }
    }

    public static boolean isCardLocked(String key)
    {
        return lockedCards.contains(key);
    }

    public static void unlockCard(String key)
    {
        seenPref.putInteger(key, 1);
        seenPref.flush();
        unlockPref.putInteger(key, 2);
        unlockPref.flush();
        lockedCards.remove(key);
        if(CardLibrary.getCard(key) != null)
        {
            CardLibrary.getCard(key).isSeen = true;
            CardLibrary.getCard(key).unlock();
        }
    }

    public static boolean isCharacterLocked(String key)
    {
        if(key.equals("The Silent") && Settings.isDemo)
            return false;
        if(Settings.isAlpha)
            return false;
        else
            return lockedCharacters.contains(key);
    }

    public static boolean isAscensionUnlocked(AbstractPlayer p)
    {
        int victories = StatsScreen.getVictory(p.getCharStat());
        if(victories > 0)
        {
            if(!achievementPref.getBoolean("ASCEND_0", false))
                unlockAchievement("ASCEND_0");
            if(!achievementPref.getBoolean("ASCEND_10", false))
                StatsScreen.retroactiveAscend10Unlock(p.getPrefs());
            if(!achievementPref.getBoolean("ASCEND_20", false))
                StatsScreen.retroactiveAscend20Unlock(p.getPrefs());
            return true;
        } else
        {
            return false;
        }
    }

    public static boolean isRelicLocked(String key)
    {
        return lockedRelics.contains(key);
    }

    public static void markCardAsSeen(String key)
    {
        if(CardLibrary.getCard(key) != null && !CardLibrary.getCard(key).isSeen)
        {
            CardLibrary.getCard(key).isSeen = true;
            seenPref.putInteger(key, 1);
            seenPref.flush();
        } else
        {
            logger.info((new StringBuilder()).append("Already seen: ").append(key).toString());
        }
    }

    public static boolean isCardSeen(String key)
    {
        return seenPref.getInteger(key, 0) != 0;
    }

    public static void markRelicAsSeen(String key)
    {
        if(RelicLibrary.getRelic(key) != null && !RelicLibrary.getRelic(key).isSeen)
        {
            RelicLibrary.getRelic(key).isSeen = true;
            relicSeenPref.putInteger(key, 1);
            relicSeenPref.flush();
        } else
        if(Settings.isDebug)
            logger.info((new StringBuilder()).append("Already seen: ").append(key).toString());
    }

    public static boolean isRelicSeen(String key)
    {
        return relicSeenPref.getInteger(key, 0) == 1;
    }

    public static void markBossAsSeen(String originalName)
    {
        if(bossSeenPref.getInteger(originalName) != 1)
        {
            bossSeenPref.putInteger(originalName, 1);
            bossSeenPref.flush();
        }
    }

    public static boolean isBossSeen(String key)
    {
        return bossSeenPref.getInteger(key, 0) == 1;
    }

    public static ArrayList getUnlockBundle(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c, int unlockLevel)
    {
        ArrayList tmpBundle = new ArrayList();
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass = new int[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[c.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            switch(unlockLevel)
            {
            case 0: // '\0'
                tmpBundle.add(new HeavyBladeUnlock());
                tmpBundle.add(new SpotWeaknessUnlock());
                tmpBundle.add(new LimitBreakUnlock());
                break;

            case 1: // '\001'
                tmpBundle.add(new OmamoriUnlock());
                tmpBundle.add(new PrayerWheelUnlock());
                tmpBundle.add(new ShovelUnlock());
                break;

            case 2: // '\002'
                tmpBundle.add(new WildStrikeUnlock());
                tmpBundle.add(new EvolveUnlock());
                tmpBundle.add(new ImmolateUnlock());
                break;

            case 3: // '\003'
                tmpBundle.add(new HavocUnlock());
                tmpBundle.add(new SentinelUnlock());
                tmpBundle.add(new ExhumeUnlock());
                break;

            case 4: // '\004'
                tmpBundle.add(new BlueCandleUnlock());
                tmpBundle.add(new DeadBranchUnlock());
                tmpBundle.add(new SingingBowlUnlock());
                break;
            }
            break;

        case 2: // '\002'
            switch(unlockLevel)
            {
            case 0: // '\0'
                tmpBundle.add(new BaneUnlock());
                tmpBundle.add(new CatalystUnlock());
                tmpBundle.add(new CorpseExplosionUnlock());
                break;

            case 1: // '\001'
                tmpBundle.add(new DuvuDollUnlock());
                tmpBundle.add(new SmilingMaskUnlock());
                tmpBundle.add(new TinyChestUnlock());
                break;

            case 2: // '\002'
                tmpBundle.add(new CloakAndDaggerUnlock());
                tmpBundle.add(new AccuracyUnlock());
                tmpBundle.add(new StormOfSteelUnlock());
                break;

            case 3: // '\003'
                tmpBundle.add(new ArtOfWarUnlock());
                tmpBundle.add(new CourierUnlock());
                tmpBundle.add(new PandorasBoxUnlock());
                break;

            case 4: // '\004'
                tmpBundle.add(new ConcentrateUnlock());
                tmpBundle.add(new SetupUnlock());
                tmpBundle.add(new GrandFinaleUnlock());
                break;
            }
            break;

        case 3: // '\003'
            switch(unlockLevel)
            {
            case 0: // '\0'
                tmpBundle.add(new ReboundUnlock());
                tmpBundle.add(new UndoUnlock());
                tmpBundle.add(new EchoFormUnlock());
                break;

            case 1: // '\001'
                tmpBundle.add(new TurboUnlock());
                tmpBundle.add(new SunderUnlock());
                tmpBundle.add(new MeteorStrikeUnlock());
                break;

            case 2: // '\002'
                tmpBundle.add(new HyperbeamUnlock());
                tmpBundle.add(new RecycleUnlock());
                tmpBundle.add(new NovaUnlock());
                break;

            case 3: // '\003'
                tmpBundle.add(new CablesUnlock());
                tmpBundle.add(new TurnipUnlock());
                tmpBundle.add(new RunicCapacitorUnlock());
                break;

            case 4: // '\004'
                tmpBundle.add(new EmotionChipUnlock());
                tmpBundle.add(new VirusUnlock());
                tmpBundle.add(new DataDiskUnlock());
                break;
            }
            break;

        case 4: // '\004'
            switch(unlockLevel)
            {
            case 0: // '\0'
                tmpBundle.add(new ProstrateUnlock());
                tmpBundle.add(new BlasphemyUnlock());
                tmpBundle.add(new DevotionUnlock());
                break;

            case 1: // '\001'
                tmpBundle.add(new ForeignInfluenceUnlock());
                tmpBundle.add(new AlphaUnlock());
                tmpBundle.add(new MentalFortressUnlock());
                break;

            case 2: // '\002'
                tmpBundle.add(new ClarityUnlock());
                tmpBundle.add(new WishUnlock());
                tmpBundle.add(new ForesightUnlock());
                break;

            case 3: // '\003'
                tmpBundle.add(new AkabekoUnlock());
                tmpBundle.add(new YangUnlock());
                tmpBundle.add(new CeramicFishUnlock());
                break;

            case 4: // '\004'
                tmpBundle.add(new StrikeDummyUnlock());
                tmpBundle.add(new TeardropUnlock());
                tmpBundle.add(new CloakClaspUnlock());
                break;
            }
            break;
        }
        return tmpBundle;
    }

    public static void addCardUnlockToList(HashMap map, String key, AbstractUnlock unlock)
    {
        if(isCardLocked(key))
            map.put(key, unlock);
    }

    public static void addRelicUnlockToList(HashMap map, String key, AbstractUnlock unlock)
    {
        if(isRelicLocked(key))
            map.put(key, unlock);
    }

    public static float getCompletionPercentage()
    {
        float totalPercent = 0.0F;
        totalPercent += getAscensionProgress() * 0.3F;
        totalPercent += getUnlockProgress() * 0.25F;
        totalPercent += getAchievementProgress() * 0.35F;
        totalPercent += getSeenCardsProgress() * 0.05F;
        totalPercent += getSeenRelicsProgress() * 0.05F;
        return totalPercent * 100F;
    }

    private static float getAscensionProgress()
    {
        ArrayList allCharacterPrefs = CardCrawlGame.characterManager.getAllPrefs();
        int sum = 0;
        for(Iterator iterator = allCharacterPrefs.iterator(); iterator.hasNext();)
        {
            Prefs p = (Prefs)iterator.next();
            sum += p.getInteger("ASCENSION_LEVEL", 0);
        }

        float retVal = (float)sum / 60F;
        logger.info((new StringBuilder()).append("Ascension Progress: ").append(retVal).toString());
        if(retVal > 1.0F)
            retVal = 1.0F;
        return retVal;
    }

    private static float getUnlockProgress()
    {
        int sum = Math.min(getUnlockLevel(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD), 5);
        sum += Math.min(getUnlockLevel(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT), 5);
        sum += Math.min(getUnlockLevel(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT), 5);
        sum += Math.min(getUnlockLevel(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER), 5);
        float retVal = (float)sum / 15F;
        logger.info((new StringBuilder()).append("Unlock IC: ").append(getUnlockLevel(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD)).toString());
        logger.info((new StringBuilder()).append("Unlock Silent: ").append(getUnlockLevel(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT)).toString());
        logger.info((new StringBuilder()).append("Unlock Defect: ").append(getUnlockLevel(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT)).toString());
        logger.info((new StringBuilder()).append("Unlock Watcher: ").append(getUnlockLevel(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER)).toString());
        logger.info((new StringBuilder()).append("Unlock Progress: ").append(retVal).toString());
        if(retVal > 1.0F)
            retVal = 1.0F;
        return retVal;
    }

    private static float getAchievementProgress()
    {
        int sum = 0;
        Iterator iterator = StatsScreen.achievements.items.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AchievementItem item = (AchievementItem)iterator.next();
            if(item.isUnlocked)
                sum++;
        } while(true);
        float retVal = (float)sum / (float)StatsScreen.achievements.items.size();
        logger.info((new StringBuilder()).append("Achievement Progress: ").append(retVal).toString());
        if(retVal > 1.0F)
            retVal = 1.0F;
        return retVal;
    }

    private static float getSeenCardsProgress()
    {
        int sum = 0;
        Iterator iterator = CardLibrary.cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            if(((AbstractCard)c.getValue()).isSeen)
                sum++;
        } while(true);
        float retVal = (float)sum / (float)CardLibrary.cards.size();
        logger.info((new StringBuilder()).append("Seen Cards Progress: ").append(retVal).toString());
        if(retVal > 1.0F)
            retVal = 1.0F;
        return retVal;
    }

    private static float getSeenRelicsProgress()
    {
        float retVal = (float)RelicLibrary.seenRelics / (float)RelicLibrary.totalRelicCount;
        logger.info((new StringBuilder()).append("Seen Relics Progress: ").append(retVal).toString());
        if(retVal > 1.0F)
            retVal = 1.0F;
        return retVal;
    }

    public static long getTotalPlaytime()
    {
        return Settings.totalPlayTime;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/unlock/UnlockTracker.getName());
    public static Prefs unlockPref;
    public static Prefs seenPref;
    public static Prefs betaCardPref;
    public static Prefs bossSeenPref;
    public static Prefs relicSeenPref;
    public static Prefs achievementPref;
    public static Prefs unlockProgress;
    public static HashMap unlockReqs = new HashMap();
    public static ArrayList lockedCards = new ArrayList();
    public static ArrayList lockedCharacters = new ArrayList();
    public static ArrayList lockedLoadouts = new ArrayList();
    public static ArrayList lockedRelics = new ArrayList();
    public static int lockedRedCardCount;
    public static int unlockedRedCardCount;
    public static int lockedGreenCardCount;
    public static int unlockedGreenCardCount;
    public static int lockedBlueCardCount;
    public static int unlockedBlueCardCount;
    public static int lockedPurpleCardCount;
    public static int unlockedPurpleCardCount;
    public static int lockedRelicCount;
    public static int unlockedRelicCount;
    private static final int STARTING_UNLOCK_COST = 300;

}
