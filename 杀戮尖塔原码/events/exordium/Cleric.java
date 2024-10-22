// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Cleric.java

package com.megacrit.cardcrawl.events.exordium;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import java.util.ArrayList;

public class Cleric extends AbstractImageEvent
{

    public Cleric()
    {
        super(NAME, DIALOG_1, "images/events/cleric.jpg");
        purifyCost = 0;
        if(AbstractDungeon.ascensionLevel >= 15)
            purifyCost = 75;
        else
            purifyCost = 50;
        int gold = AbstractDungeon.player.gold;
        if(gold >= 35)
        {
            healAmt = (int)((float)AbstractDungeon.player.maxHealth * 0.25F);
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(healAmt).append(OPTIONS[8]).toString(), gold < 35);
        } else
        {
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[1]).append(35).append(OPTIONS[2]).toString(), gold < 35);
        }
        if(gold >= 50)
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[3]).append(purifyCost).append(OPTIONS[4]).toString(), gold < purifyCost);
        else
            imageEventText.setDialogOption(OPTIONS[5], gold < purifyCost);
        imageEventText.setDialogOption(OPTIONS[6]);
    }

    public void update()
    {
        super.update();
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, Settings.WIDTH / 2, Settings.HEIGHT / 2));
            AbstractEvent.logMetricCardRemovalAtCost("The Cleric", "Card Removal", c, purifyCost);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
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
                AbstractDungeon.player.loseGold(35);
                AbstractDungeon.player.heal(healAmt);
                showProceedScreen(DIALOG_2);
                AbstractEvent.logMetricHealAtCost("The Cleric", "Healed", 35, healAmt);
                break;

            case 1: // '\001'
                if(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0)
                {
                    AbstractDungeon.player.loseGold(purifyCost);
                    AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[7], false, false, false, true);
                }
                showProceedScreen(DIALOG_3);
                break;

            default:
                showProceedScreen(DIALOG_4);
                AbstractEvent.logMetric("The Cleric", "Leave");
                break;
            }
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "The Cleric";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    public static final int HEAL_COST = 35;
    private static final int PURIFY_COST = 50;
    private static final int A_2_PURIFY_COST = 75;
    private int purifyCost;
    private static final float HEAL_AMT = 0.25F;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private static final String DIALOG_4;
    private int healAmt;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("The Cleric");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
        DIALOG_4 = DESCRIPTIONS[3];
    }
}
