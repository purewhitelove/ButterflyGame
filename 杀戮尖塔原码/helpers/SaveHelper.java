// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaveHelper.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.integrations.DistributorFactory;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.mainMenu.SaveSlotScreen;
import com.megacrit.cardcrawl.vfx.GameSavedEffect;
import java.io.File;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            Prefs, AsyncSaver

public class SaveHelper
{

    public SaveHelper()
    {
    }

    public static void initialize()
    {
    }

    private static Boolean isGog()
    {
        return Boolean.valueOf(CardCrawlGame.publisherIntegration.getType() == com.megacrit.cardcrawl.integrations.DistributorFactory.Distributor.GOG);
    }

    private static String getSaveDir()
    {
        if(Settings.isBeta || isGog().booleanValue())
            return "betaPreferences";
        else
            return "preferences";
    }

    public static boolean doesPrefExist(String name)
    {
        return Gdx.files.local((new StringBuilder()).append(getSaveDir()).append(File.separator).append(name).toString()).exists();
    }

    public static void deletePrefs(int slot)
    {
        String dir = (new StringBuilder()).append(getSaveDir()).append(File.separator).toString();
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSDataVagabond", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSDataTheSilent", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSDataDefect", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSDataWatcher", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSAchievements", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSDaily", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSSeenBosses", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSSeenCards", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSBetaCardPreference", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSSeenRelics", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSUnlockProgress", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSUnlocks", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSGameplaySettings", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSInputSettings", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSInputSettings_Controller", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSSound", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSPlayer", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("STSTips", slot)).toString());
        dir = (new StringBuilder()).append("runs").append(File.separator).toString();
        deleteFolder((new StringBuilder()).append(dir).append(slotName("IRONCLAD", slot)).toString());
        deleteFolder((new StringBuilder()).append(dir).append(slotName("THE_SILENT", slot)).toString());
        deleteFolder((new StringBuilder()).append(dir).append(slotName("DEFECT", slot)).toString());
        deleteFolder((new StringBuilder()).append(dir).append(slotName("WATCHER", slot)).toString());
        deleteFolder((new StringBuilder()).append(dir).append(slotName("DAILY", slot)).toString());
        dir = (new StringBuilder()).append("saves").append(File.separator).toString();
        deleteFile((new StringBuilder()).append(dir).append(slotName("IRONCLAD.autosave", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("DEFECT.autosave", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("THE_SILENT.autosave", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("WATCHER.autosave", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("IRONCLAD.autosave.backUp", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("DEFECT.autosave.backUp", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("THE_SILENT.autosave.backUp", slot)).toString());
        deleteFile((new StringBuilder()).append(dir).append(slotName("WATCHER.autosave.backUp", slot)).toString());
        if(Settings.isBeta || isGog().booleanValue())
        {
            deleteFile((new StringBuilder()).append(dir).append(slotName("IRONCLAD.autosaveBETA", slot)).toString());
            deleteFile((new StringBuilder()).append(dir).append(slotName("DEFECT.autosaveBETA", slot)).toString());
            deleteFile((new StringBuilder()).append(dir).append(slotName("THE_SILENT.autosaveBETA", slot)).toString());
            deleteFile((new StringBuilder()).append(dir).append(slotName("WATCHER.autosaveBETA", slot)).toString());
            deleteFile((new StringBuilder()).append(dir).append(slotName("IRONCLAD.autosaveBETA.backUp", slot)).toString());
            deleteFile((new StringBuilder()).append(dir).append(slotName("DEFECT.autosaveBETA.backUp", slot)).toString());
            deleteFile((new StringBuilder()).append(dir).append(slotName("THE_SILENT.autosaveBETA.backUp", slot)).toString());
            deleteFile((new StringBuilder()).append(dir).append(slotName("WATCHER.autosaveBETA.backUp", slot)).toString());
        }
        CardCrawlGame.saveSlotPref.putString(slotName("PROFILE_NAME", slot), "");
        CardCrawlGame.saveSlotPref.putFloat(slotName("COMPLETION", slot), 0.0F);
        CardCrawlGame.saveSlotPref.putLong(slotName("PLAYTIME", slot), 0L);
        CardCrawlGame.saveSlotPref.flush();
        if(slot == CardCrawlGame.saveSlot || CardCrawlGame.saveSlot == -1)
        {
            String name = "";
            boolean newDefaultSet = false;
            int i = 0;
            do
            {
                if(i >= 3)
                    break;
                name = CardCrawlGame.saveSlotPref.getString(slotName("PROFILE_NAME", i), "");
                if(!name.equals(""))
                {
                    logger.info((new StringBuilder()).append("Current slot deleted, DEFAULT_SLOT is now ").append(i).toString());
                    CardCrawlGame.saveSlotPref.putInteger("DEFAULT_SLOT", i);
                    newDefaultSet = true;
                    SaveSlotScreen.slotDeleted = true;
                    break;
                }
                i++;
            } while(true);
            if(!newDefaultSet)
            {
                logger.info("All slots deleted, DEFAULT_SLOT is now -1");
                CardCrawlGame.saveSlotPref.putInteger("DEFAULT_SLOT", -1);
            }
            CardCrawlGame.saveSlotPref.flush();
        }
    }

    private static void deleteFile(String fileName)
    {
        logger.info((new StringBuilder()).append("Deleting ").append(fileName).toString());
        if(Gdx.files.local(fileName).delete())
            logger.info((new StringBuilder()).append(fileName).append(" deleted.").toString());
        if(Gdx.files.local((new StringBuilder()).append(fileName).append(".backUp").toString()).delete())
            logger.info((new StringBuilder()).append(fileName).append(".backUp deleted.").toString());
    }

    private static void deleteFolder(String dirName)
    {
        logger.info((new StringBuilder()).append("Deleting ").append(dirName).toString());
        if(Gdx.files.local(dirName).deleteDirectory())
            logger.info((new StringBuilder()).append(dirName).append(" deleted.").toString());
    }

    public static String slotName(String name, int slot)
    {
        switch(slot)
        {
        case 1: // '\001'
        case 2: // '\002'
        default:
            name = (new StringBuilder()).append(slot).append("_").append(name).toString();
            // fall through

        case 0: // '\0'
            return name;
        }
    }

    public static Prefs getPrefs(String name)
    {
        Gson gson;
        switch(CardCrawlGame.saveSlot)
        {
        case 1: // '\001'
        case 2: // '\002'
        default:
            name = (new StringBuilder()).append(CardCrawlGame.saveSlot).append("_").append(name).toString();
            // fall through

        case 0: // '\0'
            gson = new Gson();
            break;
        }
        Prefs retVal = new Prefs();
        Type type = (new TypeToken() {

        }
).getType();
        String filepath = (new StringBuilder()).append(getSaveDir()).append(File.separator).append(name).toString();
        String jsonStr = null;
        try
        {
            jsonStr = loadJson(filepath);
            if(jsonStr.isEmpty())
            {
                logger.error((new StringBuilder()).append("Empty Pref file: name=").append(name).append(", filepath=").append(filepath).toString());
                handleCorruption(jsonStr, filepath, name);
                retVal = getPrefs(name);
            } else
            {
                retVal.data = (Map)gson.fromJson(jsonStr, type);
            }
        }
        catch(JsonSyntaxException e)
        {
            logger.error("Corrupt Pref file", e);
            handleCorruption(jsonStr, filepath, name);
            retVal = getPrefs(name);
        }
        retVal.filepath = filepath;
        return retVal;
    }

    private static void handleCorruption(String jsonStr, String filepath, String name)
    {
        preserveCorruptFile(filepath);
        FileHandle backup = Gdx.files.local((new StringBuilder()).append(filepath).append(".backUp").toString());
        if(backup.exists())
        {
            backup.moveTo(Gdx.files.local(filepath));
            logger.info((new StringBuilder()).append("Original corrupted, backup loaded for ").append(filepath).toString());
        }
    }

    public static void preserveCorruptFile(String filePath)
    {
        FileHandle file = Gdx.files.local(filePath);
        FileHandle corruptFile = Gdx.files.local((new StringBuilder()).append("sendToDevs").append(File.separator).append(filePath).append(".corrupt").toString());
        file.moveTo(corruptFile);
    }

    private static String loadJson(String filepath)
    {
        if(Gdx.files.local(filepath).exists())
        {
            return Gdx.files.local(filepath).readString(String.valueOf(StandardCharsets.UTF_8));
        } else
        {
            Map map = new HashMap();
            Gson gson = new Gson();
            AsyncSaver.save(filepath, gson.toJson(map));
            return "{}";
        }
    }

    public static boolean saveExists()
    {
        StringBuilder retVal = new StringBuilder();
        retVal.append(getSaveDir()).append(File.separator);
        switch(CardCrawlGame.saveSlot)
        {
        case 0: // '\0'
            retVal.append("STSPlayer");
            break;

        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
            retVal.append(CardCrawlGame.saveSlot).append("_STSPlayer");
            break;

        default:
            retVal.append("STSPlayer");
            break;
        }
        return Gdx.files.local(retVal.toString()).exists();
    }

    public static void saveIfAppropriate(com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType saveType)
    {
        if(!shouldSave())
        {
            return;
        } else
        {
            SaveFile saveFile = new SaveFile(saveType);
            SaveAndContinue.save(saveFile);
            AbstractDungeon.effectList.add(new GameSavedEffect());
            return;
        }
    }

    public static boolean shouldSave()
    {
        if(AbstractDungeon.nextRoom != null && (AbstractDungeon.nextRoom.getRoom() instanceof TrueVictoryRoom))
            return false;
        else
            return !Settings.isDemo;
    }

    public static boolean shouldDeleteSave()
    {
        return !Settings.isDemo;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/SaveHelper.getName());

}
