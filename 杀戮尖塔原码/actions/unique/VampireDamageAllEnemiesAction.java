// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VampireDamageAllEnemiesAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class VampireDamageAllEnemiesAction extends AbstractGameAction
{

    public VampireDamageAllEnemiesAction(AbstractCreature source, int amount[], com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        setValues(null, source, amount[0]);
        damage = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        damageType = type;
        attackEffect = effect;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            boolean playedMusic = false;
            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            for(int i = 0; i < temp; i++)
            {
                if(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDying || ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).currentHealth <= 0 || ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isEscaping)
                    continue;
                if(playedMusic)
                {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, attackEffect, true));
                } else
                {
                    playedMusic = true;
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, attackEffect));
                }
            }

        }
        tickDuration();
        if(isDone)
        {
            AbstractPower p;
            for(Iterator iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext(); p.onDamageAllEnemies(damage))
                p = (AbstractPower)iterator.next();

            int healAmount = 0;
            for(int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++)
            {
                AbstractMonster target = (AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if(target.isDying || target.currentHealth <= 0 || target.isEscaping)
                    continue;
                target.damage(new DamageInfo(source, damage[i], damageType));
                if(target.lastDamageTaken <= 0)
                    continue;
                healAmount += target.lastDamageTaken;
                for(int j = 0; j < target.lastDamageTaken / 2 && j < 10; j++)
                    addToBot(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY)));

            }

            if(healAmount > 0)
            {
                if(!Settings.FAST_MODE)
                    addToBot(new WaitAction(0.3F));
                addToBot(new HealAction(source, source, healAmount));
            }
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            addToTop(new WaitAction(0.1F));
        }
    }

    public int damage[];
}
