// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApplyPowerToRandomEnemyAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;

// Referenced classes of package com.megacrit.cardcrawl.actions.common:
//            ApplyPowerAction

public class ApplyPowerToRandomEnemyAction extends AbstractGameAction
{

    public ApplyPowerToRandomEnemyAction(AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        setValues(null, source, stackAmount);
        this.powerToApply = powerToApply;
        this.isFast = isFast;
        this.effect = effect;
    }

    public ApplyPowerToRandomEnemyAction(AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast)
    {
        this(source, powerToApply, stackAmount, isFast, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE);
    }

    public ApplyPowerToRandomEnemyAction(AbstractCreature source, AbstractPower powerToApply, int stackAmount)
    {
        this(source, powerToApply, stackAmount, false);
    }

    public ApplyPowerToRandomEnemyAction(AbstractCreature source, AbstractPower powerToApply)
    {
        this(source, powerToApply, -1);
    }

    public void update()
    {
        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        powerToApply.owner = target;
        if(target != null)
            addToTop(new ApplyPowerAction(target, source, powerToApply, amount, isFast, effect));
        isDone = true;
    }

    private AbstractPower powerToApply;
    private boolean isFast;
    private com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect;
}
