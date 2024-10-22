// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FountainOfCurseRemoval.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import java.util.ArrayList;
import java.util.List;

public class FountainOfCurseRemoval extends AbstractImageEvent
{

    public FountainOfCurseRemoval()
    {
        super(NAME, DIALOG_1, "images/events/fountain.jpg");
        screenNum = 0;
        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom()
    {
        CardCrawlGame.music.playTempBGM("SHRINE");
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_FOUNTAIN");
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(DIALOG_2);
                List curses = new ArrayList();
                screenNum = 1;
                for(int i = AbstractDungeon.player.masterDeck.group.size() - 1; i >= 0; i--)
                    if(((AbstractCard)AbstractDungeon.player.masterDeck.group.get(i)).type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && !((AbstractCard)AbstractDungeon.player.masterDeck.group.get(i)).inBottleFlame && !((AbstractCard)AbstractDungeon.player.masterDeck.group.get(i)).inBottleLightning && ((AbstractCard)AbstractDungeon.player.masterDeck.group.get(i)).cardID != "AscendersBane" && ((AbstractCard)AbstractDungeon.player.masterDeck.group.get(i)).cardID != "CurseOfTheBell" && ((AbstractCard)AbstractDungeon.player.masterDeck.group.get(i)).cardID != "Necronomicurse")
                    {
                        AbstractDungeon.effectList.add(new PurgeCardEffect((AbstractCard)AbstractDungeon.player.masterDeck.group.get(i)));
                        curses.add(((AbstractCard)AbstractDungeon.player.masterDeck.group.get(i)).cardID);
                        AbstractDungeon.player.masterDeck.removeCard((AbstractCard)AbstractDungeon.player.masterDeck.group.get(i));
                    }

                logMetricRemoveCards("Fountain of Cleansing", "Removed Curses", curses);
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                imageEventText.clearRemainingOptions();
                break;

            default:
                logMetricIgnored("Fountain of Cleansing");
                imageEventText.updateBodyText(DIALOG_3);
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                imageEventText.clearRemainingOptions();
                screenNum = 1;
                break;
            }
            break;

        case 1: // '\001'
            openMap();
            break;

        default:
            openMap();
            break;
        }
    }

    public void logMetric(String cardGiven)
    {
        AbstractEvent.logMetric("Fountain of Cleansing", cardGiven);
    }

    public static final String ID = "Fountain of Cleansing";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private int screenNum;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Fountain of Cleansing");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
    }
}
