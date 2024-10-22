// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FadingPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class FadingPower extends AbstractPower
{

    public FadingPower(AbstractCreature owner, int turns)
    {
        name = NAME;
        ID = "Fading";
        this.owner = owner;
        amount = turns;
        updateDescription();
        loadRegion("fading");
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = DESCRIPTIONS[2];
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void duringTurn()
    {
        if(amount == 1 && !owner.isDying)
        {
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.hb.cX, owner.hb.cY), 0.1F));
            addToBot(new SuicideAction((AbstractMonster)owner));
        } else
        {
            addToBot(new ReducePowerAction(owner, owner, "Fading", 1));
            updateDescription();
        }
    }

    public static final String POWER_ID = "Fading";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Fading");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
