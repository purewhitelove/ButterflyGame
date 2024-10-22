// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheBombPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class TheBombPower extends AbstractPower
{

    public TheBombPower(AbstractCreature owner, int turns, int damage)
    {
        name = NAME;
        ID = (new StringBuilder()).append("TheBomb").append(bombIdOffset).toString();
        bombIdOffset++;
        this.owner = owner;
        amount = turns;
        this.damage = damage;
        updateDescription();
        loadRegion("the_bomb");
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            addToBot(new ReducePowerAction(owner, owner, this, 1));
            if(amount == 1)
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = String.format(DESCRIPTIONS[1], new Object[] {
                Integer.valueOf(damage)
            });
        else
            description = String.format(DESCRIPTIONS[0], new Object[] {
                Integer.valueOf(amount), Integer.valueOf(damage)
            });
    }

    public static final String POWER_ID = "TheBomb";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private int damage;
    private static int bombIdOffset;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("TheBomb");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
