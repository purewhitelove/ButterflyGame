// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamageAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.TintEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class DamageAction extends AbstractGameAction
{

    public DamageAction(AbstractCreature target, DamageInfo info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        goldAmount = 0;
        skipWait = false;
        muteSfx = false;
        this.info = info;
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = effect;
        duration = 0.1F;
    }

    public DamageAction(AbstractCreature target, DamageInfo info, int stealGoldAmount)
    {
        this(target, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        goldAmount = stealGoldAmount;
    }

    public DamageAction(AbstractCreature target, DamageInfo info)
    {
        this(target, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE);
    }

    public DamageAction(AbstractCreature target, DamageInfo info, boolean superFast)
    {
        this(target, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE);
        skipWait = superFast;
    }

    public DamageAction(AbstractCreature target, DamageInfo info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect, boolean superFast)
    {
        this(target, info, effect);
        skipWait = superFast;
    }

    public DamageAction(AbstractCreature target, DamageInfo info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect, boolean superFast, boolean muteSfx)
    {
        this(target, info, effect, superFast);
        this.muteSfx = muteSfx;
    }

    public void update()
    {
        if(shouldCancelAction() && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS)
        {
            isDone = true;
            return;
        }
        if(duration == 0.1F)
        {
            if(info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && (info.owner.isDying || info.owner.halfDead))
            {
                isDone = true;
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect, muteSfx));
            if(goldAmount != 0)
                stealGold();
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

    private void stealGold()
    {
        if(target.gold == 0)
            return;
        CardCrawlGame.sound.play("GOLD_JINGLE");
        if(target.gold < goldAmount)
            goldAmount = target.gold;
        target.gold -= goldAmount;
        for(int i = 0; i < goldAmount; i++)
            if(source.isPlayer)
                AbstractDungeon.effectList.add(new GainPennyEffect(target.hb.cX, target.hb.cY));
            else
                AbstractDungeon.effectList.add(new GainPennyEffect(source, target.hb.cX, target.hb.cY, source.hb.cX, source.hb.cY, false));

    }

    private DamageInfo info;
    private int goldAmount;
    private static final float DURATION = 0.1F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private boolean skipWait;
    private boolean muteSfx;
}
