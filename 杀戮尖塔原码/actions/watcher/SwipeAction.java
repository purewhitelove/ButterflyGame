// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SwipeAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.badlogic.gdx.graphics.Color;
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
import com.megacrit.cardcrawl.vfx.TintEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class SwipeAction extends AbstractGameAction
{

    public SwipeAction(AbstractCreature target, DamageInfo info)
    {
        skipWait = false;
        this.info = info;
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_VERTICAL;
        duration = Settings.ACTION_DUR_XFAST;
    }

    public void update()
    {
        if(shouldCancelAction() && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS)
        {
            isDone = true;
            return;
        }
        if(duration == Settings.ACTION_DUR_XFAST)
        {
            if(info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && (info.owner.isDying || info.owner.halfDead))
            {
                isDone = true;
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect, false));
        }
        tickDuration();
        if(isDone)
        {
            if(attackEffect == com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON)
            {
                target.tint.color.set(Color.CHARTREUSE.cpy());
                target.tint.changeColor(Color.WHITE.cpy());
            } else
            if(attackEffect == com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE)
            {
                target.tint.color.set(Color.RED);
                target.tint.changeColor(Color.WHITE.cpy());
            }
            target.damage(info);
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            if(!skipWait && !Settings.FAST_MODE)
                addToTop(new WaitAction(0.1F));
        }
    }

    private DamageInfo info;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private boolean skipWait;
}
