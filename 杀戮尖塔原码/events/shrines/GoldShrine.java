// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GoldShrine.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class GoldShrine extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/GoldShrine$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN COMPLETE;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            COMPLETE = new CUR_SCREEN("COMPLETE", 1);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, COMPLETE
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public GoldShrine()
    {
        super(NAME, DIALOG_1, "images/events/goldShrine.jpg");
        screen = CUR_SCREEN.INTRO;
        if(AbstractDungeon.ascensionLevel >= 15)
            goldAmt = 50;
        else
            goldAmt = 100;
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(goldAmt).append(OPTIONS[1]).toString());
        imageEventText.setDialogOption(OPTIONS[2], CardLibrary.getCopy("Regret"));
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    public void onEnterRoom()
    {
        CardCrawlGame.music.playTempBGM("SHRINE");
    }

    public void update()
    {
        super.update();
        if(!RoomEventDialog.waitForInput)
            buttonEffect(roomEventText.getSelectedOption());
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$GoldShrine$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$shrines$GoldShrine$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$GoldShrine$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$GoldShrine$CUR_SCREEN[CUR_SCREEN.COMPLETE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.GoldShrine.CUR_SCREEN[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                screen = CUR_SCREEN.COMPLETE;
                logMetricGainGold("Golden Shrine", "Pray", goldAmt);
                imageEventText.updateBodyText(DIALOG_2);
                imageEventText.updateDialogOption(0, OPTIONS[3]);
                AbstractDungeon.effectList.add(new RainingGoldEffect(goldAmt));
                AbstractDungeon.player.gainGold(goldAmt);
                imageEventText.clearRemainingOptions();
                break;

            case 1: // '\001'
                screen = CUR_SCREEN.COMPLETE;
                com.megacrit.cardcrawl.cards.AbstractCard curse = new Regret();
                logMetricGainGoldAndCard("Golden Shrine", "Desecrate", curse, 275);
                AbstractDungeon.effectList.add(new RainingGoldEffect(275));
                AbstractDungeon.player.gainGold(275);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                imageEventText.updateBodyText(DIALOG_3);
                imageEventText.updateDialogOption(0, OPTIONS[3]);
                imageEventText.clearRemainingOptions();
                break;

            case 2: // '\002'
                screen = CUR_SCREEN.COMPLETE;
                logMetricIgnored("Golden Shrine");
                imageEventText.updateBodyText(IGNORE);
                imageEventText.updateDialogOption(0, OPTIONS[3]);
                imageEventText.clearRemainingOptions();
                break;
            }
            break;

        case 2: // '\002'
            openMap();
            break;
        }
    }

    public static final String ID = "Golden Shrine";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final int GOLD_AMT = 100;
    private static final int CURSE_GOLD_AMT = 275;
    private static final int A_2_GOLD_AMT = 50;
    private int goldAmt;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private static final String IGNORE;
    private CUR_SCREEN screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Golden Shrine");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
        IGNORE = DESCRIPTIONS[3];
    }
}
