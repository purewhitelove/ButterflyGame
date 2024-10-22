// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NightmareAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.NightmarePower;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

public class NightmareAction extends AbstractGameAction
{

    public NightmareAction(AbstractCreature target, AbstractCreature source, int amount)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = DURATION;
        p = (AbstractPlayer)target;
    }

    public void update()
    {
        if(duration == DURATION)
        {
            if(p.hand.isEmpty())
            {
                isDone = true;
                return;
            }
            if(p.hand.size() == 1)
            {
                addToTop(new ApplyPowerAction(p, p, new NightmarePower(p, amount, p.hand.getBottomCard())));
                isDone = true;
                return;
            } else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                tickDuration();
                return;
            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            com.megacrit.cardcrawl.cards.AbstractCard tmpCard = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
            addToTop(new ApplyPowerAction(p, p, new NightmarePower(p, amount, tmpCard)));
            AbstractDungeon.player.hand.addToHand(tmpCard);
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private AbstractPlayer p;
    public static int numDiscarded;
    private static final float DURATION;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CopyAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}
