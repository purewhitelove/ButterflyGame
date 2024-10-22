// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheBeyond.java

package com.megacrit.cardcrawl.dungeons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.beyond.Falling;
import com.megacrit.cardcrawl.events.beyond.MindBloom;
import com.megacrit.cardcrawl.events.beyond.MoaiHead;
import com.megacrit.cardcrawl.events.beyond.MysteriousSphere;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;
import com.megacrit.cardcrawl.events.beyond.TombRedMask;
import com.megacrit.cardcrawl.events.beyond.WindingHalls;
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
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.dungeons:
//            AbstractDungeon

public class TheBeyond extends AbstractDungeon
{

    public TheBeyond(AbstractPlayer p, ArrayList theList)
    {
        super(NAME, "TheBeyond", p, theList);
        if(scene != null)
            scene.dispose();
        scene = new TheBeyondScene();
        fadeColor = Color.valueOf("140a1eff");
        sourceFadeColor = Color.valueOf("140a1eff");
        initializeLevelSpecificChances();
        mapRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)(AbstractDungeon.actNum * 200)));
        generateMap();
        CardCrawlGame.music.changeBGM(id);
    }

    public TheBeyond(AbstractPlayer p, SaveFile saveFile)
    {
        super(NAME, p, saveFile);
        CardCrawlGame.dungeon = this;
        if(scene != null)
            scene.dispose();
        scene = new TheBeyondScene();
        fadeColor = Color.valueOf("140a1eff");
        sourceFadeColor = Color.valueOf("140a1eff");
        initializeLevelSpecificChances();
        miscRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)saveFile.floor_num));
        CardCrawlGame.music.changeBGM(id);
        mapRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)(saveFile.act_num * 200)));
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
            cardUpgradedChance = 0.25F;
        else
            cardUpgradedChance = 0.5F;
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
        monsters.add(new MonsterInfo("3 Darklings", 2.0F));
        monsters.add(new MonsterInfo("Orb Walker", 2.0F));
        monsters.add(new MonsterInfo("3 Shapes", 2.0F));
        MonsterInfo.normalizeWeights(monsters);
        populateMonsterList(monsters, count, false);
    }

    protected void generateStrongEnemies(int count)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(new MonsterInfo("Spire Growth", 1.0F));
        monsters.add(new MonsterInfo("Transient", 1.0F));
        monsters.add(new MonsterInfo("4 Shapes", 1.0F));
        monsters.add(new MonsterInfo("Maw", 1.0F));
        monsters.add(new MonsterInfo("Sphere and 2 Shapes", 1.0F));
        monsters.add(new MonsterInfo("Jaw Worm Horde", 1.0F));
        monsters.add(new MonsterInfo("3 Darklings", 1.0F));
        monsters.add(new MonsterInfo("Writhing Mass", 1.0F));
        MonsterInfo.normalizeWeights(monsters);
        populateFirstStrongEnemy(monsters, generateExclusions());
        populateMonsterList(monsters, count, false);
    }

    protected void generateElites(int count)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(new MonsterInfo("Giant Head", 2.0F));
        monsters.add(new MonsterInfo("Nemesis", 2.0F));
        monsters.add(new MonsterInfo("Reptomancer", 2.0F));
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
        case 1014856122: 
            if(s.equals("3 Darklings"))
                byte0 = 0;
            break;

        case 1679632599: 
            if(s.equals("Orb Walker"))
                byte0 = 1;
            break;

        case -500373089: 
            if(s.equals("3 Shapes"))
                byte0 = 2;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            retVal.add("3 Darklings");
            break;

        case 1: // '\001'
            retVal.add("Orb Walker");
            break;

        case 2: // '\002'
            retVal.add("4 Shapes");
            break;
        }
        return retVal;
    }

    protected void initializeBoss()
    {
        bossList.clear();
        if(Settings.isDailyRun)
        {
            bossList.add("Awakened One");
            bossList.add("Time Eater");
            bossList.add("Donu and Deca");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        } else
        if(!UnlockTracker.isBossSeen("CROW"))
            bossList.add("Awakened One");
        else
        if(!UnlockTracker.isBossSeen("DONUT"))
            bossList.add("Donu and Deca");
        else
        if(!UnlockTracker.isBossSeen("WIZARD"))
        {
            bossList.add("Time Eater");
        } else
        {
            bossList.add("Awakened One");
            bossList.add("Time Eater");
            bossList.add("Donu and Deca");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        }
        if(bossList.size() == 1)
            bossList.add(bossList.get(0));
        else
        if(bossList.isEmpty())
        {
            logger.warn("Boss list was empty. How?");
            bossList.add("Awakened One");
            bossList.add("Time Eater");
            bossList.add("Donu and Deca");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        }
    }

    protected void initializeEventList()
    {
        eventList.add("Falling");
        eventList.add("MindBloom");
        eventList.add("The Moai Head");
        eventList.add("Mysterious Sphere");
        eventList.add("SensoryStone");
        eventList.add("Tomb of Lord Red Mask");
        eventList.add("Winding Halls");
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

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/dungeons/TheBeyond.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public static final String NAME;
    public static final String ID = "TheBeyond";

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("TheBeyond");
        TEXT = uiStrings.TEXT;
        NAME = TEXT[0];
    }
}
