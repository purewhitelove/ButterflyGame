// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThunderClap.java

package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
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
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class ThunderClap extends AbstractCard
{

    public ThunderClap()
    {
        super("Thunderclap", cardStrings.NAME, "red/attack/thunder_clap", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY);
        isMultiDamage = true;
        baseDamage = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new SFXAction("THUNDERCLAP", 0.05F));
        Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster mo = (AbstractMonster)iterator.next();
            if(!mo.isDeadOrEscaped())
                addToBot(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.05F));
        } while(true);
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        AbstractMonster mo;
        for(Iterator iterator1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); iterator1.hasNext(); addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, 1, false), 1, true, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE)))
            mo = (AbstractMonster)iterator1.next();

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
        return new ThunderClap();
    }

    public static final String ID = "Thunderclap";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Thunderclap");
    }
}
