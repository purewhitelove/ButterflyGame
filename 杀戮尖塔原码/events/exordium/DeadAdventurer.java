// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeadAdventurer.java

package com.megacrit.cardcrawl.events.exordium;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.EffectHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeadAdventurer extends AbstractEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/exordium/DeadAdventurer$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN FAIL;
        public static final CUR_SCREEN SUCCESS;
        public static final CUR_SCREEN ESCAPE;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            FAIL = new CUR_SCREEN("FAIL", 1);
            SUCCESS = new CUR_SCREEN("SUCCESS", 2);
            ESCAPE = new CUR_SCREEN("ESCAPE", 3);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, FAIL, SUCCESS, ESCAPE
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public DeadAdventurer()
    {
        numRewards = 0;
        encounterChance = 0;
        rewards = new ArrayList();
        x = 800F * Settings.xScale;
        y = AbstractDungeon.floorY;
        enemy = 0;
        screen = CUR_SCREEN.INTRO;
        goldRewardMetric = 0;
        relicRewardMetric = null;
        rewards.add("GOLD");
        rewards.add("NOTHING");
        rewards.add("RELIC");
        Collections.shuffle(rewards, new Random(AbstractDungeon.miscRng.randomLong()));
        if(AbstractDungeon.ascensionLevel >= 15)
            encounterChance = 35;
        else
            encounterChance = 25;
        enemy = AbstractDungeon.miscRng.random(0, 2);
        adventurerImg = ImageMaster.loadImage("images/npcs/nopants.png");
        body = DESCRIPTIONS[2];
        enemy;
        JVM INSTR lookupswitch 2: default 260
    //                   0: 196
    //                   1: 228;
           goto _L1 _L2 _L3
_L2:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        body;
        append();
        DESCRIPTIONS[3];
        append();
        toString();
        body;
          goto _L4
_L3:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        body;
        append();
        DESCRIPTIONS[4];
        append();
        toString();
        body;
          goto _L4
_L1:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        body;
        append();
        DESCRIPTIONS[5];
        append();
        toString();
        body;
_L4:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        body;
        append();
        DESCRIPTIONS[6];
        append();
        toString();
        body;
        roomEventText.addDialogOption((new StringBuilder()).append(OPTIONS[0]).append(encounterChance).append(OPTIONS[4]).toString());
        roomEventText.addDialogOption(OPTIONS[1]);
        hasDialog = true;
        hasFocus = true;
        return;
    }

    public void update()
    {
        super.update();
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.EVENT || AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE)
            imgColor = Color.WHITE.cpy();
        else
            imgColor = DARKEN_COLOR;
        if(!RoomEventDialog.waitForInput)
            buttonEffect(roomEventText.getSelectedOption());
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$exordium$DeadAdventurer$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$exordium$DeadAdventurer$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$DeadAdventurer$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$DeadAdventurer$CUR_SCREEN[CUR_SCREEN.SUCCESS.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$DeadAdventurer$CUR_SCREEN[CUR_SCREEN.FAIL.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$DeadAdventurer$CUR_SCREEN[CUR_SCREEN.ESCAPE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.exordium.DeadAdventurer.CUR_SCREEN[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            default:
                break;

            case 0: // '\0'
                if(AbstractDungeon.miscRng.random(0, 99) < encounterChance)
                {
                    screen = CUR_SCREEN.FAIL;
                    roomEventText.updateBodyText(FIGHT_MSG);
                    roomEventText.updateDialogOption(0, OPTIONS[2]);
                    roomEventText.removeDialogOption(1);
                    if(Settings.isDailyRun)
                        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(30));
                    else
                        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25, 35));
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(getMonster());
                    AbstractDungeon.getCurrRoom().eliteTrigger = true;
                } else
                {
                    randomReward();
                }
                break;

            case 1: // '\001'
                screen = CUR_SCREEN.ESCAPE;
                roomEventText.updateBodyText(ESCAPE_MSG);
                roomEventText.updateDialogOption(0, OPTIONS[1]);
                roomEventText.removeDialogOption(1);
                break;
            }
            break;

        case 2: // '\002'
            openMap();
            break;

        case 3: // '\003'
            Iterator iterator = rewards.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                String s = (String)iterator.next();
                if(s.equals("GOLD"))
                    AbstractDungeon.getCurrRoom().addGoldToRewards(30);
                else
                if(s.equals("RELIC"))
                    AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomRelicTier());
            } while(true);
            enterCombat();
            AbstractDungeon.lastCombatMetricKey = getMonster();
            numRewards++;
            logMetric(numRewards);
            break;

        case 4: // '\004'
            logMetric(numRewards);
            openMap();
            break;

        default:
            logger.info("WHY YOU CALLED?");
            break;
        }
    }

    private String getMonster()
    {
        switch(enemy)
        {
        case 0: // '\0'
            return "3 Sentries";

        case 1: // '\001'
            return "Gremlin Nob";
        }
        return "Lagavulin Event";
    }

    private void randomReward()
    {
        numRewards++;
        encounterChance += 25;
        String s = (String)rewards.remove(0);
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 2193504: 
            if(s.equals("GOLD"))
                byte0 = 0;
            break;

        case -1447660627: 
            if(s.equals("NOTHING"))
                byte0 = 1;
            break;

        case 77859667: 
            if(s.equals("RELIC"))
                byte0 = 2;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            roomEventText.updateBodyText(DESCRIPTIONS[7]);
            EffectHelper.gainGold(AbstractDungeon.player, x, y, 30);
            AbstractDungeon.player.gainGold(30);
            goldRewardMetric = 30;
            break;

        case 1: // '\001'
            roomEventText.updateBodyText(DESCRIPTIONS[8]);
            break;

        case 2: // '\002'
            roomEventText.updateBodyText(DESCRIPTIONS[9]);
            AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
            relicRewardMetric = r;
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(x, y, r);
            break;

        default:
            logger.info("HOW IS THIS POSSSIBLLEEEE");
            break;
        }
        if(numRewards == 3)
        {
            roomEventText.updateBodyText(DESCRIPTIONS[10]);
            roomEventText.updateDialogOption(0, OPTIONS[1]);
            roomEventText.removeDialogOption(1);
            screen = CUR_SCREEN.SUCCESS;
            logMetric(numRewards);
        } else
        {
            logger.info("SHOULD NOT DISMISS");
            roomEventText.updateDialogOption(0, (new StringBuilder()).append(OPTIONS[3]).append(encounterChance).append(OPTIONS[4]).toString());
            roomEventText.updateDialogOption(1, OPTIONS[1]);
            screen = CUR_SCREEN.INTRO;
        }
    }

    public void logMetric(int numAttempts)
    {
        if(relicRewardMetric != null)
            AbstractEvent.logMetricGainGoldAndRelic("Dead Adventurer", (new StringBuilder()).append("Searched '").append(numAttempts).append("' times").toString(), relicRewardMetric, goldRewardMetric);
        else
            AbstractEvent.logMetricGainGold("Dead Adventurer", (new StringBuilder()).append("Searched '").append(numAttempts).append("' times").toString(), goldRewardMetric);
    }

    public void render(SpriteBatch sb)
    {
        super.render(sb);
        sb.setColor(Color.WHITE);
        sb.draw(adventurerImg, x - 146F, y - 86.5F, 146F, 86.5F, 292F, 173F, Settings.scale, Settings.scale, 0.0F, 0, 0, 292, 173, false, false);
    }

    public void dispose()
    {
        super.dispose();
        if(adventurerImg != null)
        {
            adventurerImg.dispose();
            adventurerImg = null;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/events/exordium/DeadAdventurer.getName());
    public static final String ID = "Dead Adventurer";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final int GOLD_REWARD = 30;
    private static final int ENCOUNTER_CHANCE_START = 25;
    private static final int A_2_CHANCE_START = 35;
    private static final int ENCOUNTER_CHANCE_RAMP = 25;
    private static final String FIGHT_MSG;
    private static final String ESCAPE_MSG;
    private int numRewards;
    private int encounterChance;
    private ArrayList rewards;
    private float x;
    private float y;
    private int enemy;
    private CUR_SCREEN screen;
    private static final Color DARKEN_COLOR = new Color(0.5F, 0.5F, 0.5F, 1.0F);
    public static final String LAGAVULIN_FIGHT = "Lagavulin Dead Adventurers Fight";
    private Texture adventurerImg;
    private int goldRewardMetric;
    private AbstractRelic relicRewardMetric;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Dead Adventurer");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        FIGHT_MSG = DESCRIPTIONS[0];
        ESCAPE_MSG = DESCRIPTIONS[1];
    }
}
