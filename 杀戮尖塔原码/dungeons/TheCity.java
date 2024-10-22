// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheCity.java

package com.megacrit.cardcrawl.dungeons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Addict;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.city.Beggar;
import com.megacrit.cardcrawl.events.city.Colosseum;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.events.city.DrugDealer;
import com.megacrit.cardcrawl.events.city.ForgottenAltar;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.events.city.MaskedBandits;
import com.megacrit.cardcrawl.events.city.Nest;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import com.megacrit.cardcrawl.events.city.TheMausoleum;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.events.shrines.GoldShrine;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.events.shrines.GremlinWheelGame;
import com.megacrit.cardcrawl.events.shrines.PurificationShrine;
import com.megacrit.cardcrawl.events.shrines.Transmogrifier;
import com.megacrit.cardcrawl.events.shrines.UpgradeShrine;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.EmptyRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.scenes.TheCityScene;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.dungeons:
//            AbstractDungeon

public class TheCity extends AbstractDungeon
{

    public TheCity(AbstractPlayer p, ArrayList theList)
    {
        super(NAME, "TheCity", p, theList);
        if(scene != null)
            scene.dispose();
        scene = new TheCityScene();
        fadeColor = Color.valueOf("0a1e1eff");
        sourceFadeColor = Color.valueOf("0a1e1eff");
        initializeLevelSpecificChances();
        mapRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)(AbstractDungeon.actNum * 100)));
        generateMap();
        CardCrawlGame.music.changeBGM(id);
        AbstractDungeon.currMapNode = new MapRoomNode(0, -1);
        AbstractDungeon.currMapNode.room = new EmptyRoom();
    }

    public TheCity(AbstractPlayer p, SaveFile saveFile)
    {
        super(NAME, p, saveFile);
        if(scene != null)
            scene.dispose();
        scene = new TheCityScene();
        fadeColor = Color.valueOf("0a1e1eff");
        sourceFadeColor = Color.valueOf("0a1e1eff");
        initializeLevelSpecificChances();
        miscRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)saveFile.floor_num));
        CardCrawlGame.music.changeBGM(id);
        mapRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)(saveFile.act_num * 100)));
        generateMap();
        firstRoomChosen = true;
        populatePathTaken(saveFile);
    }

    protected void initializeLevelSpecificChances()
    {
        shopRoomChance = 0.05F;
        restRoomChance = 0.12F;
        treasureRoomChance = 0.0F;
        eventRoomChance = 0.22F;
        eliteRoomChance = 0.08F;
        smallChestChance = 50;
        mediumChestChance = 33;
        largeChestChance = 17;
        commonRelicChance = 50;
        uncommonRelicChance = 33;
        rareRelicChance = 17;
        colorlessRareChance = 0.3F;
        if(AbstractDungeon.ascensionLevel >= 12)
            cardUpgradedChance = 0.125F;
        else
            cardUpgradedChance = 0.25F;
    }

    protected void generateMonsters()
    {
        generateWeakEnemies(2);
        generateStrongEnemies(12);
        generateElites(10);
    }

    protected void generateWeakEnemies(int count)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(new MonsterInfo("Spheric Guardian", 2.0F));
        monsters.add(new MonsterInfo("Chosen", 2.0F));
        monsters.add(new MonsterInfo("Shell Parasite", 2.0F));
        monsters.add(new MonsterInfo("3 Byrds", 2.0F));
        monsters.add(new MonsterInfo("2 Thieves", 2.0F));
        MonsterInfo.normalizeWeights(monsters);
        populateMonsterList(monsters, count, false);
    }

    protected void generateStrongEnemies(int count)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(new MonsterInfo("Chosen and Byrds", 2.0F));
        monsters.add(new MonsterInfo("Sentry and Sphere", 2.0F));
        monsters.add(new MonsterInfo("Snake Plant", 6F));
        monsters.add(new MonsterInfo("Snecko", 4F));
        monsters.add(new MonsterInfo("Centurion and Healer", 6F));
        monsters.add(new MonsterInfo("Cultist and Chosen", 3F));
        monsters.add(new MonsterInfo("3 Cultists", 3F));
        monsters.add(new MonsterInfo("Shelled Parasite and Fungi", 3F));
        MonsterInfo.normalizeWeights(monsters);
        populateFirstStrongEnemy(monsters, generateExclusions());
        populateMonsterList(monsters, count, false);
    }

    protected void generateElites(int count)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(new MonsterInfo("Gremlin Leader", 1.0F));
        monsters.add(new MonsterInfo("Slavers", 1.0F));
        monsters.add(new MonsterInfo("Book of Stabbing", 1.0F));
        MonsterInfo.normalizeWeights(monsters);
        populateMonsterList(monsters, count, true);
    }

    protected ArrayList generateExclusions()
    {
        ArrayList retVal = new ArrayList();
        String s = (String)monsterList.get(monsterList.size() - 1);
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 1989842815: 
            if(s.equals("Spheric Guardian"))
                byte0 = 0;
            break;

        case -1001149827: 
            if(s.equals("3 Byrds"))
                byte0 = 1;
            break;

        case 2017619858: 
            if(s.equals("Chosen"))
                byte0 = 2;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            retVal.add("Sentry and Sphere");
            break;

        case 1: // '\001'
            retVal.add("Chosen and Byrds");
            break;

        case 2: // '\002'
            retVal.add("Chosen and Byrds");
            retVal.add("Cultist and Chosen");
            break;
        }
        return retVal;
    }

    protected void initializeBoss()
    {
        bossList.clear();
        if(Settings.isDailyRun)
        {
            bossList.add("Automaton");
            bossList.add("Collector");
            bossList.add("Champ");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        } else
        if(!UnlockTracker.isBossSeen("CHAMP"))
            bossList.add("Champ");
        else
        if(!UnlockTracker.isBossSeen("AUTOMATON"))
            bossList.add("Automaton");
        else
        if(!UnlockTracker.isBossSeen("COLLECTOR"))
        {
            bossList.add("Collector");
        } else
        {
            bossList.add("Automaton");
            bossList.add("Collector");
            bossList.add("Champ");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        }
        if(bossList.size() == 1)
            bossList.add(bossList.get(0));
        else
        if(bossList.isEmpty())
        {
            logger.warn("Boss list was empty. How?");
            bossList.add("Automaton");
            bossList.add("Collector");
            bossList.add("Champ");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        }
    }

    protected void initializeEventList()
    {
        eventList.add("Addict");
        eventList.add("Back to Basics");
        eventList.add("Beggar");
        eventList.add("Colosseum");
        eventList.add("Cursed Tome");
        eventList.add("Drug Dealer");
        eventList.add("Forgotten Altar");
        eventList.add("Ghosts");
        eventList.add("Masked Bandits");
        eventList.add("Nest");
        eventList.add("The Library");
        eventList.add("The Mausoleum");
        eventList.add("Vampires");
    }

    protected void initializeEventImg()
    {
        if(eventBackgroundImg != null)
        {
            eventBackgroundImg.dispose();
            eventBackgroundImg = null;
        }
        eventBackgroundImg = ImageMaster.loadImage("images/ui/event/panel.png");
    }

    protected void initializeShrineList()
    {
        shrineList.add("Match and Keep!");
        shrineList.add("Wheel of Change");
        shrineList.add("Golden Shrine");
        shrineList.add("Transmorgrifier");
        shrineList.add("Purifier");
        shrineList.add("Upgrade Shrine");
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/dungeons/TheCity.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public static final String NAME;
    public static final String ID = "TheCity";

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("TheCity");
        TEXT = uiStrings.TEXT;
        NAME = TEXT[0];
    }
}
