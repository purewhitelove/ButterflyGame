// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CorpseExplosionPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class CorpseExplosionPower extends AbstractPower
{

    public CorpseExplosionPower(AbstractCreature owner)
    {
        name = NAME;
        ID = "CorpseExplosionPower";
        this.owner = owner;
        amount = 1;
        type = AbstractPower.PowerType.DEBUFF;
        updateDescription();
        loadRegion("cExplosion");
    }

    public void onDeath()
    {
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && owner.currentHealth <= 0)
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(owner.maxHealth * amount, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public static final String POWER_ID = "CorpseExplosionPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("CorpseExplosionPower");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
