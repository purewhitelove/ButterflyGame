// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Necronomicurse.java

package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.vfx.NecronomicurseEffect;
import java.util.ArrayList;

public class Necronomicurse extends AbstractCard
{

    public Necronomicurse()
    {
        super("Necronomicurse", cardStrings.NAME, "curse/necronomicurse", -2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
    }

    public void use(AbstractPlayer abstractplayer, AbstractMonster abstractmonster)
    {
    }

    public void onRemoveFromMasterDeck()
    {
        if(AbstractDungeon.player.hasRelic("Necronomicon"))
            AbstractDungeon.player.getRelic("Necronomicon").flash();
        AbstractDungeon.effectsQueue.add(new NecronomicurseEffect(new Necronomicurse(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
    }

    public void triggerOnExhaust()
    {
        if(AbstractDungeon.player.hasRelic("Necronomicon"))
            AbstractDungeon.player.getRelic("Necronomicon").flash();
        addToBot(new MakeTempCardInHandAction(makeCopy()));
    }

    public void upgrade()
    {
    }

    public AbstractCard makeCopy()
    {
        return new Necronomicurse();
    }

    public static final String ID = "Necronomicurse";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Necronomicurse");
    }
}
