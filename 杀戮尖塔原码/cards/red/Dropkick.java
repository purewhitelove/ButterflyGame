// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Dropkick.java

package com.megacrit.cardcrawl.cards.red;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.unique.DropkickAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

public class Dropkick extends AbstractCard
{

    public Dropkick()
    {
        super("Dropkick", cardStrings.NAME, "red/attack/drop_kick", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DropkickAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
    }

    public void triggerOnGlowCheck()
    {
        glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(m.isDeadOrEscaped() || !m.hasPower("Vulnerable"))
                continue;
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            break;
        } while(true);
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Dropkick();
    }

    public static final String ID = "Dropkick";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Dropkick");
    }
}
