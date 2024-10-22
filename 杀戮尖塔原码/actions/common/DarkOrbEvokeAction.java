// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DarkOrbEvokeAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.TintEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class DarkOrbEvokeAction extends AbstractGameAction
{

    public DarkOrbEvokeAction(DamageInfo info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        muteSfx = false;
        AbstractMonster weakestMonster = null;
        Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDeadOrEscaped())
                if(weakestMonster == null)
                    weakestMonster = m;
                else
                if(m.currentHealth < weakestMonster.currentHealth)
                    weakestMonster = m;
        } while(true);
        this.info = info;
        setValues(weakestMonster, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = effect;
        duration = 0.1F;
    }

    public void update()
    {
        if(shouldCancelAction() && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS || target == null)
        {
            isDone = true;
            return;
        }
        if(duration == 0.1F)
        {
            info.output = AbstractOrb.applyLockOn(target, info.base);
            if(info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && (info.owner.isDying || info.owner.halfDead))
            {
                isDone = true;
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect, muteSfx));
        }
        tickDuration();
        if(isDone)
        {
            if(attackEffect == com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON)
            {
                target.tint.color = Color.CHARTREUSE.cpy();
                target.tint.changeColor(Color.WHITE.cpy());
            } else
            if(attackEffect == com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE)
            {
                target.tint.color = Color.RED.cpy();
                target.tint.changeColor(Color.WHITE.cpy());
            }
            target.damage(info);
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            if(!Settings.FAST_MODE)
                addToTop(new WaitAction(0.1F));
        }
    }

    private DamageInfo info;
    private static final float DURATION = 0.1F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private boolean muteSfx;
}
