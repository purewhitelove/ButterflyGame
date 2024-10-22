// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BouncingFlaskAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.PotionBounceEffect;

public class BouncingFlaskAction extends AbstractGameAction
{

    public BouncingFlaskAction(AbstractCreature target, int amount, int numTimes)
    {
        this.target = target;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DEBUFF;
        duration = 0.01F;
        this.numTimes = numTimes;
        this.amount = amount;
    }

    public void update()
    {
        if(target == null)
        {
            isDone = true;
            return;
        }
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
        {
            AbstractDungeon.actionManager.clearPostCombatActions();
            isDone = true;
            return;
        }
        if(numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            numTimes--;
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            addToTop(new BouncingFlaskAction(randomMonster, amount, numTimes));
            addToTop(new VFXAction(new PotionBounceEffect(target.hb.cX, target.hb.cY, randomMonster.hb.cX, randomMonster.hb.cY), 0.4F));
        }
        if(target.currentHealth > 0)
        {
            addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new PoisonPower(target, AbstractDungeon.player, amount), amount, true, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON));
            addToTop(new WaitAction(0.1F));
        }
        isDone = true;
    }

    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private int numTimes;
    private int amount;
}
