// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RipAndTearAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
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
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;
import java.util.ArrayList;

public class RipAndTearAction extends AbstractGameAction
{

    public RipAndTearAction(AbstractCreature target, DamageInfo info, int numTimes)
    {
        this.info = info;
        this.target = target;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        if(Settings.FAST_MODE)
            startDuration = 0.05F;
        else
            startDuration = 0.2F;
        duration = startDuration;
        this.numTimes = numTimes;
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
        if(duration == startDuration)
            AbstractDungeon.effectsQueue.add(new RipAndTearEffect(target.hb.cX, target.hb.cY, Color.RED, Color.GOLD));
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            if(target.currentHealth > 0)
            {
                info.applyPowers(info.owner, target);
                target.damage(info);
                if(numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                {
                    numTimes--;
                    addToTop(new RipAndTearAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info, numTimes));
                }
                if(Settings.FAST_MODE)
                    addToTop(new WaitAction(0.1F));
                else
                    addToTop(new WaitAction(0.2F));
            }
            isDone = true;
        }
    }

    private DamageInfo info;
    private int numTimes;
}
