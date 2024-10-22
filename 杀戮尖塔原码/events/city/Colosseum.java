// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Colosseum.java

package com.megacrit.cardcrawl.events.city;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;

public class Colosseum extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/city/Colosseum$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen FIGHT;
        public static final CurScreen LEAVE;
        public static final CurScreen POST_COMBAT;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            FIGHT = new CurScreen("FIGHT", 1);
            LEAVE = new CurScreen("LEAVE", 2);
            POST_COMBAT = new CurScreen("POST_COMBAT", 3);
            $VALUES = (new CurScreen[] {
                INTRO, FIGHT, LEAVE, POST_COMBAT
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public Colosseum()
    {
        super(NAME, DESCRIPTIONS[0], "images/events/colosseum.jpg");
        screen = CurScreen.INTRO;
        imageEventText.setDialogOption(OPTIONS[0]);
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$city$Colosseum$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$city$Colosseum$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$Colosseum$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$Colosseum$CurScreen[CurScreen.FIGHT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$Colosseum$CurScreen[CurScreen.POST_COMBAT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$Colosseum$CurScreen[CurScreen.LEAVE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.city.Colosseum.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText((new StringBuilder()).append(DESCRIPTIONS[1]).append(DESCRIPTIONS[2]).append(4200).append(DESCRIPTIONS[3]).toString());
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                screen = CurScreen.FIGHT;
                break;
            }
            break;

        case 2: // '\002'
            switch(buttonPressed)
            {
            case 0: // '\0'
                screen = CurScreen.POST_COMBAT;
                logMetric("Fight");
                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Colosseum Slavers");
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractDungeon.getCurrRoom().rewardAllowed = false;
                enterCombatFromImage();
                AbstractDungeon.lastCombatMetricKey = "Colosseum Slavers";
                // fall through

            default:
                imageEventText.clearRemainingOptions();
                break;
            }
            break;

        case 3: // '\003'
            AbstractDungeon.getCurrRoom().rewardAllowed = true;
            switch(buttonPressed)
            {
            case 1: // '\001'
                screen = CurScreen.LEAVE;
                logMetric("Fought Nobs");
                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Colosseum Nobs");
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractDungeon.getCurrRoom().addRelicToRewards(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE);
                AbstractDungeon.getCurrRoom().addRelicToRewards(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON);
                AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                AbstractDungeon.getCurrRoom().eliteTrigger = true;
                enterCombatFromImage();
                AbstractDungeon.lastCombatMetricKey = "Colosseum Nobs";
                break;

            default:
                logMetric("Fled From Nobs");
                openMap();
                break;
            }
            break;

        case 4: // '\004'
            openMap();
            break;

        default:
            openMap();
            break;
        }
    }

    public void logMetric(String actionTaken)
    {
        AbstractEvent.logMetric("Colosseum", actionTaken);
    }

    public void reopen()
    {
        if(screen != CurScreen.LEAVE)
        {
            AbstractDungeon.resetPlayer();
            AbstractDungeon.player.drawX = (float)Settings.WIDTH * 0.25F;
            AbstractDungeon.player.preBattlePrep();
            enterImageFromCombat();
            imageEventText.updateBodyText(DESCRIPTIONS[4]);
            imageEventText.updateDialogOption(0, OPTIONS[2]);
            imageEventText.setDialogOption(OPTIONS[3]);
        }
    }

    public static final String ID = "Colosseum";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private CurScreen screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Colosseum");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
}
