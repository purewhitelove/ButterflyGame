// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Regret.java

package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

public class Regret extends AbstractCard
{

    public Regret()
    {
        super("Regret", cardStrings.NAME, "curse/regret", -2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(dontTriggerOnUseCard)
            addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
    }

    public void triggerOnEndOfTurnForPlayingCard()
    {
        dontTriggerOnUseCard = true;
        magicNumber = baseMagicNumber = AbstractDungeon.player.hand.size();
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public void upgrade()
    {
    }

    public AbstractCard makeCopy()
    {
        return new Regret();
    }

    public static final String ID = "Regret";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Regret");
    }
}
