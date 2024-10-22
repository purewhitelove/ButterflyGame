// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Duplicator.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class Duplicator extends AbstractImageEvent
{

    public Duplicator()
    {
        super(NAME, DIALOG_1, "images/events/shrine4.jpg");
        screenNum = 0;
        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom()
    {
        CardCrawlGame.music.playTempBGM("SHRINE");
    }

    public void update()
    {
        super.update();
        if(!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c = ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeStatEquivalentCopy();
            logMetricObtainCard("Duplicator", "Copied", c);
            c.inBottleFlame = false;
            c.inBottleLightning = false;
            c.inBottleTornado = false;
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        default:
            break;

        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(DIALOG_2);
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                imageEventText.clearRemainingOptions();
                use();
                screenNum = 2;
                break;

            case 1: // '\001'
                screenNum = 2;
                imageEventText.updateBodyText(IGNORE);
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                imageEventText.clearRemainingOptions();
                logMetricIgnored("Duplicator");
                break;
            }
            break;

        case 1: // '\001'
            screenNum = 2;
            break;

        case 2: // '\002'
            openMap();
            break;
        }
    }

    public void use()
    {
        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, 1, OPTIONS[2], false, false, false, false);
    }

    public void logMetric(String result)
    {
        AbstractEvent.logMetric("Duplicator", result);
    }

    public static final String ID = "Duplicator";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private int screenNum;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String IGNORE;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Duplicator");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        IGNORE = DESCRIPTIONS[2];
    }
}
