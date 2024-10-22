// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpgradeShrine.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;

public class UpgradeShrine extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/UpgradeShrine$CUR_SCREEN, name);
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


    public UpgradeShrine()
    {
        super(NAME, DIALOG_1, "images/events/shrine2.jpg");
        screen = CUR_SCREEN.INTRO;
        if(AbstractDungeon.player.masterDeck.hasUpgradableCards().booleanValue())
            imageEventText.setDialogOption(OPTIONS[0]);
        else
            imageEventText.setDialogOption(OPTIONS[3], true);
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom()
    {
        CardCrawlGame.music.playTempBGM("SHRINE");
    }

    public void update()
    {
        super.update();
        if(!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            c.upgrade();
            logMetricCardUpgrade("Upgrade Shrine", "Upgraded", c);
            AbstractDungeon.player.bottledCardUpgradeCheck(c);
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$UpgradeShrine$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$shrines$UpgradeShrine$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$UpgradeShrine$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$UpgradeShrine$CUR_SCREEN[CUR_SCREEN.COMPLETE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.UpgradeShrine.CUR_SCREEN[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                screen = CUR_SCREEN.COMPLETE;
                AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
                imageEventText.updateBodyText(DIALOG_2);
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[2], true, false, false, false);
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                imageEventText.clearRemainingOptions();
                break;

            case 1: // '\001'
                screen = CUR_SCREEN.COMPLETE;
                AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
                logMetricIgnored("Upgrade Shrine");
                imageEventText.updateBodyText(IGNORE);
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                imageEventText.clearRemainingOptions();
                break;
            }
            break;

        case 2: // '\002'
            openMap();
            break;
        }
    }

    public static final String ID = "Upgrade Shrine";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String IGNORE;
    private CUR_SCREEN screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Upgrade Shrine");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        IGNORE = DESCRIPTIONS[2];
    }
}
