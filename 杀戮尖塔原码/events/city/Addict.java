// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Addict.java

package com.megacrit.cardcrawl.events.city;

import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class Addict extends AbstractImageEvent
{

    public Addict()
    {
        super(NAME, DESCRIPTIONS[0], "images/events/addict.jpg");
        screenNum = 0;
        if(AbstractDungeon.player.gold >= 85)
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(85).append(OPTIONS[1]).toString(), AbstractDungeon.player.gold < 85);
        else
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(85).append(OPTIONS[3]).toString(), AbstractDungeon.player.gold < 85);
        imageEventText.setDialogOption(OPTIONS[4], CardLibrary.getCopy("Shame"));
        imageEventText.setDialogOption(OPTIONS[5]);
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(DESCRIPTIONS[1]);
                if(AbstractDungeon.player.gold >= 85)
                {
                    AbstractRelic relic = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                    AbstractEvent.logMetricObtainRelicAtCost("Addict", "Obtained Relic", relic, 85);
                    AbstractDungeon.player.loseGold(85);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(drawX, drawY, relic);
                    imageEventText.updateDialogOption(0, OPTIONS[5]);
                    imageEventText.clearRemainingOptions();
                }
                break;

            case 1: // '\001'
                imageEventText.updateBodyText(DESCRIPTIONS[2]);
                com.megacrit.cardcrawl.cards.AbstractCard card = new Shame();
                AbstractRelic relic = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                AbstractEvent.logMetricObtainCardAndRelic("Addict", "Stole Relic", card, relic);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, Settings.WIDTH / 2, Settings.HEIGHT / 2));
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(drawX, drawY, relic);
                imageEventText.updateDialogOption(0, OPTIONS[5]);
                imageEventText.clearRemainingOptions();
                break;

            default:
                imageEventText.updateDialogOption(0, OPTIONS[5]);
                imageEventText.clearRemainingOptions();
                openMap();
                break;
            }
            screenNum = 1;
            break;

        case 1: // '\001'
            openMap();
            break;
        }
    }

    public static final String ID = "Addict";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final int GOLD_COST = 85;
    private int screenNum;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Addict");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
}
