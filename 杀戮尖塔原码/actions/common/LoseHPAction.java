// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoseHPAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
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

public class LoseHPAction extends AbstractGameAction
{

    public LoseHPAction(AbstractCreature target, AbstractCreature source, int amount)
    {
        this(target, source, amount, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE);
    }

    public LoseHPAction(AbstractCreature target, AbstractCreature source, int amount, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = effect;
        duration = 0.33F;
    }

    public void update()
    {
        if(duration == 0.33F && target.currentHealth > 0)
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
        tickDuration();
        if(isDone)
        {
            target.damage(new DamageInfo(source, amount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            if(!Settings.FAST_MODE)
                addToTop(new WaitAction(0.1F));
        }
    }

    private static final float DURATION = 0.33F;
}
