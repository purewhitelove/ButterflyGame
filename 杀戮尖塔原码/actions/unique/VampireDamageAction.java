// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VampireDamageAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class VampireDamageAction extends AbstractGameAction
{

    public VampireDamageAction(AbstractCreature target, DamageInfo info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        this.info = info;
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = effect;
    }

    public void update()
    {
        if(duration == 0.5F)
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
        tickDuration();
        if(isDone)
        {
            target.damage(info);
            if(target.lastDamageTaken > 0)
            {
                addToTop(new HealAction(source, source, target.lastDamageTaken));
                addToTop(new WaitAction(0.1F));
            }
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
    }

    private DamageInfo info;
}
