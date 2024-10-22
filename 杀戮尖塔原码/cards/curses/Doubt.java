// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Doubt.java

package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import java.util.ArrayList;

public class Doubt extends AbstractCard
{

    public Doubt()
    {
        super("Doubt", cardStrings.NAME, "curse/doubt", -2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(dontTriggerOnUseCard)
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 1, true), 1));
    }

    public void triggerWhenDrawn()
    {
        addToBot(new SetDontTriggerAction(this, false));
    }

    public void triggerOnEndOfTurnForPlayingCard()
    {
        dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public void upgrade()
    {
    }

    public AbstractCard makeCopy()
    {
        return new Doubt();
    }

    public static final String ID = "Doubt";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Doubt");
    }
}
