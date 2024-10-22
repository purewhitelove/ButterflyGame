// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExplosivePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class ExplosivePower extends AbstractPower
{

    public ExplosivePower(AbstractCreature owner, int damage)
    {
        name = NAME;
        ID = "Explosive";
        this.owner = owner;
        amount = damage;
        updateDescription();
        loadRegion("explosive");
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[3]).append(30).append(DESCRIPTIONS[2]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).append(30).append(DESCRIPTIONS[2]).toString();
    }

    public void duringTurn()
    {
        if(amount == 1 && !owner.isDying)
        {
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.hb.cX, owner.hb.cY), 0.1F));
            addToBot(new SuicideAction((AbstractMonster)owner));
            DamageInfo damageInfo = new DamageInfo(owner, 30, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS);
            addToBot(new DamageAction(AbstractDungeon.player, damageInfo, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE, true));
        } else
        {
            addToBot(new ReducePowerAction(owner, owner, "Explosive", 1));
            updateDescription();
        }
    }

    public static final String POWER_ID = "Explosive";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private static final int DAMAGE_AMOUNT = 30;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Explosive");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
