// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Parasite.java

package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Parasite extends AbstractCard
{

    public Parasite()
    {
        super("Parasite", cardStrings.NAME, "curse/parasite", -2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
    }

    public void use(AbstractPlayer abstractplayer, AbstractMonster abstractmonster)
    {
    }

    public void onRemoveFromMasterDeck()
    {
        AbstractDungeon.player.decreaseMaxHealth(3);
        CardCrawlGame.sound.play("BLOOD_SWISH");
    }

    public void upgrade()
    {
    }

    public AbstractCard makeCopy()
    {
        return new Parasite();
    }

    public static final String ID = "Parasite";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Parasite");
    }
}
