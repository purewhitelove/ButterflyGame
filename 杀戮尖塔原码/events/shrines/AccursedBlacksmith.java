// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccursedBlacksmith.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.WarpedTongs;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;

public class AccursedBlacksmith extends AbstractImageEvent
{

    public AccursedBlacksmith()
    {
        super(NAME, DIALOG_1, "images/events/blacksmith.jpg");
        screenNum = 0;
        pickCard = false;
        if(AbstractDungeon.player.masterDeck.hasUpgradableCards().booleanValue())
            imageEventText.setDialogOption(OPTIONS[0]);
        else
            imageEventText.setDialogOption(OPTIONS[4], true);
        imageEventText.setDialogOption(OPTIONS[1], CardLibrary.getCopy("Pain"), new WarpedTongs());
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_FORGE");
    }

    public void update()
    {
        super.update();
        if(pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            c.upgrade();
            logMetricCardUpgrade("Accursed Blacksmith", "Forge", c);
            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            pickCard = false;
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                pickCard = true;
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[3], true, false, false, false);
                imageEventText.updateBodyText(FORGE_RESULT);
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                break;

            case 1: // '\001'
                screenNum = 2;
                imageEventText.updateBodyText((new StringBuilder()).append(RUMMAGE_RESULT).append(CURSE_RESULT2).toString());
                AbstractCard curse = new Pain();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, Settings.WIDTH / 2, Settings.HEIGHT / 2));
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new WarpedTongs());
                logMetricObtainCardAndRelic("Accursed Blacksmith", "Rummage", curse, new WarpedTongs());
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                break;

            case 2: // '\002'
                screenNum = 2;
                logMetricIgnored("Accursed Blacksmith");
                imageEventText.updateBodyText(LEAVE_RESULT);
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                break;
            }
            imageEventText.clearRemainingOptions();
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "Accursed Blacksmith";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String FORGE_RESULT;
    private static final String RUMMAGE_RESULT;
    private static final String CURSE_RESULT2;
    private static final String LEAVE_RESULT;
    private int screenNum;
    private boolean pickCard;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Accursed Blacksmith");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        FORGE_RESULT = DESCRIPTIONS[1];
        RUMMAGE_RESULT = DESCRIPTIONS[2];
        CURSE_RESULT2 = DESCRIPTIONS[4];
        LEAVE_RESULT = DESCRIPTIONS[5];
    }
}
