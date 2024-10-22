// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SwordBoomerangAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class SwordBoomerangAction extends AbstractGameAction
{

    public SwordBoomerangAction(AbstractCreature target, DamageInfo info, int numTimes)
    {
        this.info = info;
        this.target = target;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
        duration = 0.01F;
        this.numTimes = numTimes;
    }

    public SwordBoomerangAction(DamageInfo info, int numTimes)
    {
        this.info = info;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
        duration = 0.01F;
        this.numTimes = numTimes;
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            addToTop(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info, numTimes));
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
        if(target.currentHealth > 0)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
            info.applyPowers(info.owner, target);
            target.damage(info);
            if(numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            {
                numTimes--;
                addToTop(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info, numTimes));
            }
            addToTop(new WaitAction(0.2F));
        } else
        {
            addToTop(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info, numTimes));
        }
        isDone = true;
    }

    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int numTimes;
}
