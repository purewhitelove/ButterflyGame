// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Exordium.java

package com.megacrit.cardcrawl.dungeons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.exordium.BigFish;
import com.megacrit.cardcrawl.events.exordium.Cleric;
import com.megacrit.cardcrawl.events.exordium.DeadAdventurer;
import com.megacrit.cardcrawl.events.exordium.GoldenIdolEvent;
import com.megacrit.cardcrawl.events.exordium.GoldenWing;
import com.megacrit.cardcrawl.events.exordium.GoopPuddle;
import com.megacrit.cardcrawl.events.exordium.LivingWall;
import com.megacrit.cardcrawl.events.exordium.Mushrooms;
import com.megacrit.cardcrawl.events.exordium.ScrapOoze;
import com.megacrit.cardcrawl.events.exordium.ShiningLight;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;
import com.megacrit.cardcrawl.events.shrines.GoldShrine;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.events.shrines.GremlinWheelGame;
import com.megacrit.cardcrawl.events.shrines.PurificationShrine;
import com.megacrit.cardcrawl.events.shrines.Transmogrifier;
import com.megacrit.cardcrawl.events.shrines.UpgradeShrine;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.EmptyRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.dungeons:
//            AbstractDungeon

public class Exordium extends AbstractDungeon
{

    public Exordium(AbstractPlayer p, ArrayList emptyList)
    {
        super(NAME, "Exordium", p, emptyList);
        initializeRelicList();
        if(Settings.isEndless)
        {
            if(floorNum <= 1)
            {
                blightPool.clear();
                blightPool = new ArrayList();
            }
        } else
        {
            blightPool.clear();
        }
        if(scene != null)
            scene.dispose();
        scene = new TheBottomScene();
        scene.randomizeScene();
        fadeColor = Color.valueOf("1e0f0aff");
        sourceFadeColor = Color.valueOf("1e0f0aff");
        initializeSpecialOneTimeEventList();
        initializeLevelSpecificChances();
        mapRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)AbstractDungeon.actNum));
        generateMap();
        CardCrawlGame.music.changeBGM(id);
        AbstractDungeon.currMapNode = new MapRoomNode(0, -1);
        if(Settings.isShowBuild || !((Boolean)TipTracker.tips.get("NEOW_SKIP")).booleanValue())
        {
            AbstractDungeon.currMapNode.room = new EmptyRoom();
        } else
        {
            AbstractDungeon.currMapNode.room = new NeowRoom(false);
            if(AbstractDungeon.floorNum > 1)
                SaveHelper.saveIfAppropriate(com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType.ENDLESS_NEOW);
            else
                SaveHelper.saveIfAppropriate(com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType.ENTER_ROOM);
        }
    }

    public Exordium(AbstractPlayer p, SaveFile saveFile)
    {
        super(NAME, p, saveFile);
        CardCrawlGame.dungeon = this;
        if(scene != null)
            scene.dispose();
        scene = new TheBottomScene();
        fadeColor = Color.valueOf("1e0f0aff");
        sourceFadeColor = Color.valueOf("1e0f0aff");
        initializeLevelSpecificChances();
        miscRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)saveFile.floor_num));
        CardCrawlGame.music.changeBGM(id);
        mapRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)saveFile.act_num));
        generateMap();
        firstRoomChosen = true;
        populatePathTaken(saveFile);
        if(isLoadingIntoNeow(saveFile))
            AbstractDungeon.firstRoomChosen = false;
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
        cardUpgradedChance = 0.0F;
    }

    protected void generateMonsters()
    {
        generateWeakEnemies(3);
        generateStrongEnemies(12);
        generateElites(10);
    }

    protected void generateWeakEnemies(int count)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(new MonsterInfo("Cultist", 2.0F));
        monsters.add(new MonsterInfo("Jaw Worm", 2.0F));
        monsters.add(new MonsterInfo("2 Louse", 2.0F));
        monsters.add(new MonsterInfo("Small Slimes", 2.0F));
        MonsterInfo.normalizeWeights(monsters);
        populateMonsterList(monsters, count, false);
    }

    protected void generateStrongEnemies(int count)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(new MonsterInfo("Blue Slaver", 2.0F));
        monsters.add(new MonsterInfo("Gremlin Gang", 1.0F));
        monsters.add(new MonsterInfo("Looter", 2.0F));
        monsters.add(new MonsterInfo("Large Slime", 2.0F));
        monsters.add(new MonsterInfo("Lots of Slimes", 1.0F));
        monsters.add(new MonsterInfo("Exordium Thugs", 1.5F));
        monsters.add(new MonsterInfo("Exordium Wildlife", 1.5F));
        monsters.add(new MonsterInfo("Red Slaver", 1.0F));
        monsters.add(new MonsterInfo("3 Louse", 2.0F));
        monsters.add(new MonsterInfo("2 Fungi Beasts", 2.0F));
        MonsterInfo.normalizeWeights(monsters);
        populateFirstStrongEnemy(monsters, generateExclusions());
        populateMonsterList(monsters, count, false);
    }

    protected void generateElites(int count)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(new MonsterInfo("Gremlin Nob", 1.0F));
        monsters.add(new MonsterInfo("Lagavulin", 1.0F));
        monsters.add(new MonsterInfo("3 Sentries", 1.0F));
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
        case -2013219467: 
            if(s.equals("Looter"))
                byte0 = 0;
            break;

        case -548386477: 
            if(s.equals("Jaw Worm"))
                byte0 = 1;
            break;

        case -1508851536: 
            if(s.equals("Cultist"))
                byte0 = 2;
            break;

        case 1637395457: 
            if(s.equals("Blue Slaver"))
                byte0 = 3;
            break;

        case -1879712874: 
            if(s.equals("2 Louse"))
                byte0 = 4;
            break;

        case 70731812: 
            if(s.equals("Small Slimes"))
                byte0 = 5;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            retVal.add("Exordium Thugs");
            break;

        case 3: // '\003'
            retVal.add("Red Slaver");
            retVal.add("Exordium Thugs");
            break;

        case 4: // '\004'
            retVal.add("3 Louse");
            break;

        case 5: // '\005'
            retVal.add("Large Slime");
            retVal.add("Lots of Slimes");
            break;
        }
        return retVal;
    }

    protected void initializeBoss()
    {
        bossList.clear();
        if(Settings.isDailyRun)
        {
            bossList.add("The Guardian");
            bossList.add("Hexaghost");
            bossList.add("Slime Boss");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        } else
        if(!UnlockTracker.isBossSeen("GUARDIAN"))
            bossList.add("The Guardian");
        else
        if(!UnlockTracker.isBossSeen("GHOST"))
            bossList.add("Hexaghost");
        else
        if(!UnlockTracker.isBossSeen("SLIME"))
        {
            bossList.add("Slime Boss");
        } else
        {
            bossList.add("The Guardian");
            bossList.add("Hexaghost");
            bossList.add("Slime Boss");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        }
        if(bossList.size() == 1)
            bossList.add(bossList.get(0));
        else
        if(bossList.isEmpty())
        {
            logger.warn("Boss list was empty. How?");
            bossList.add("The Guardian");
            bossList.add("Hexaghost");
            bossList.add("Slime Boss");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        }
        if(Settings.isDemo)
        {
            bossList.clear();
            bossList.add("Hexaghost");
        }
    }

    protected void initializeEventList()
    {
        eventList.add("Big Fish");
        eventList.add("The Cleric");
        eventList.add("Dead Adventurer");
        eventList.add("Golden Idol");
        eventList.add("Golden Wing");
        eventList.add("World of Goop");
        eventList.add("Liars Game");
        eventList.add("Living Wall");
        eventList.add("Mushrooms");
        eventList.add("Scrap Ooze");
        eventList.add("Shining Light");
    }

    protected void initializeShrineList()
    {
        shrineList.add("Match and Keep!");
        shrineList.add("Golden Shrine");
        shrineList.add("Transmorgrifier");
        shrineList.add("Purifier");
        shrineList.add("Upgrade Shrine");
        shrineList.add("Wheel of Change");
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

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public static final String NAME;
    public static final String ID = "Exordium";

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Exordium");
        TEXT = uiStrings.TEXT;
        NAME = TEXT[0];
    }
}
