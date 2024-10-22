// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NextTurnBlockPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class NextTurnBlockPower extends AbstractPower
{

    public NextTurnBlockPower(AbstractCreature owner, int armorAmt, String newName)
    {
        name = newName;
        ID = "Next Turn Block";
        this.owner = owner;
        amount = armorAmt;
        updateDescription();
        loadRegion("defenseNext");
    }

    public NextTurnBlockPower(AbstractCreature owner, int armorAmt)
    {
        this(owner, armorAmt, NAME);
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void atStartOfTurn()
    {
        flash();
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(owner.hb.cX, owner.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SHIELD));
        addToBot(new GainBlockAction(owner, owner, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, "Next Turn Block"));
    }

    public static final String POWER_ID = "Next Turn Block";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Next Turn Block");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
