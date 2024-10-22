// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WallopAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class WallopAction extends AbstractGameAction
{

    public WallopAction(AbstractCreature target, DamageInfo info)
    {
        this.info = info;
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
    }

    public void update()
    {
        if(shouldCancelAction())
        {
            isDone = true;
            return;
        }
        tickDuration();
        if(isDone)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
            target.damage(info);
            if(target.lastDamageTaken > 0)
            {
                addToTop(new GainBlockAction(source, target.lastDamageTaken));
                if(target.hb != null)
                    addToTop(new VFXAction(new WallopEffect(target.lastDamageTaken, target.hb.cX, target.hb.cY)));
            }
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            else
                addToTop(new WaitAction(0.1F));
        }
    }

    private DamageInfo info;
}
