// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SensoryStone.java

package com.megacrit.cardcrawl.events.beyond;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SensoryStone extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/beyond/SensoryStone$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen INTRO_2;
        public static final CurScreen ACCEPT;
        public static final CurScreen LEAVE;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            INTRO_2 = new CurScreen("INTRO_2", 1);
            ACCEPT = new CurScreen("ACCEPT", 2);
            LEAVE = new CurScreen("LEAVE", 3);
            $VALUES = (new CurScreen[] {
                INTRO, INTRO_2, ACCEPT, LEAVE
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public SensoryStone()
    {
        super(NAME, INTRO_TEXT, "images/events/sensoryStone.jpg");
        screen = CurScreen.INTRO;
        noCardsInRewards = true;
        imageEventText.setDialogOption(OPTIONS[5]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_SENSORY");
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$beyond$SensoryStone$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$beyond$SensoryStone$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$SensoryStone$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$SensoryStone$CurScreen[CurScreen.INTRO_2.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$SensoryStone$CurScreen[CurScreen.ACCEPT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.beyond.SensoryStone.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            imageEventText.updateBodyText(INTRO_TEXT_2);
            imageEventText.updateDialogOption(0, OPTIONS[0]);
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[1]).append(5).append(OPTIONS[3]).toString());
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(10).append(OPTIONS[3]).toString());
            screen = CurScreen.INTRO_2;
            break;

        case 2: // '\002'
            getRandomMemory();
            switch(buttonPressed)
            {
            case 0: // '\0'
                screen = CurScreen.ACCEPT;
                logMetric("SensoryStone", "Memory 1");
                choice = 1;
                reward(choice);
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                break;

            case 1: // '\001'
                screen = CurScreen.ACCEPT;
                logMetricTakeDamage("SensoryStone", "Memory 2", 5);
                choice = 2;
                reward(choice);
                AbstractDungeon.player.damage(new DamageInfo(null, 5, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                break;

            case 2: // '\002'
                screen = CurScreen.ACCEPT;
                logMetricTakeDamage("SensoryStone", "Memory 3", 10);
                choice = 3;
                reward(choice);
                AbstractDungeon.player.damage(new DamageInfo(null, 10, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                break;
            }
            imageEventText.clearRemainingOptions();
            break;

        case 3: // '\003'
            reward(choice);
            // fall through

        default:
            openMap();
            break;
        }
    }

    private void getRandomMemory()
    {
        ArrayList memories = new ArrayList();
        memories.add(MEMORY_1_TEXT);
        memories.add(MEMORY_2_TEXT);
        memories.add(MEMORY_3_TEXT);
        memories.add(MEMORY_4_TEXT);
        Collections.shuffle(memories, new Random(AbstractDungeon.miscRng.randomLong()));
        imageEventText.updateBodyText((String)memories.get(0));
    }

    private void reward(int num)
    {
        AbstractDungeon.getCurrRoom().rewards.clear();
        for(int i = 0; i < num; i++)
            AbstractDungeon.getCurrRoom().addCardReward(new RewardItem(com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS));

        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
        screen = CurScreen.LEAVE;
    }

    public static final String ID = "SensoryStone";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String INTRO_TEXT;
    private static final String INTRO_TEXT_2;
    private static final String MEMORY_1_TEXT;
    private static final String MEMORY_2_TEXT;
    private static final String MEMORY_3_TEXT;
    private static final String MEMORY_4_TEXT;
    private CurScreen screen;
    private int choice;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("SensoryStone");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_TEXT = DESCRIPTIONS[0];
        INTRO_TEXT_2 = DESCRIPTIONS[1];
        MEMORY_1_TEXT = DESCRIPTIONS[2];
        MEMORY_2_TEXT = DESCRIPTIONS[3];
        MEMORY_3_TEXT = DESCRIPTIONS[4];
        MEMORY_4_TEXT = DESCRIPTIONS[5];
    }
}
