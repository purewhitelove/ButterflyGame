// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamageAllEnemiesAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
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
import com.megacrit.cardcrawl.vfx.TintEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class DamageAllEnemiesAction extends AbstractGameAction
{

    public DamageAllEnemiesAction(AbstractCreature source, int amount[], com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect, boolean isFast)
    {
        firstFrame = true;
        utilizeBaseDamage = false;
        this.source = source;
        damage = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        damageType = type;
        attackEffect = effect;
        if(isFast)
            duration = Settings.ACTION_DUR_XFAST;
        else
            duration = Settings.ACTION_DUR_FAST;
    }

    public DamageAllEnemiesAction(AbstractCreature source, int amount[], com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        this(source, amount, type, effect, false);
    }

    public DamageAllEnemiesAction(AbstractPlayer player, int baseDamage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        this(((AbstractCreature) (player)), null, type, effect, false);
        this.baseDamage = baseDamage;
        utilizeBaseDamage = true;
    }

    public void update()
    {
        if(firstFrame)
        {
            boolean playedMusic = false;
            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            if(utilizeBaseDamage)
                damage = DamageInfo.createDamageMatrix(baseDamage);
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

            firstFrame = false;
        }
        tickDuration();
        if(isDone)
        {
            AbstractPower p;
            for(Iterator iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext(); p.onDamageAllEnemies(damage))
                p = (AbstractPower)iterator.next();

            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            for(int i = 0; i < temp; i++)
            {
                if(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDeadOrEscaped())
                    continue;
                if(attackEffect == com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON)
                {
                    ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.color.set(Color.CHARTREUSE);
                    ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                } else
                if(attackEffect == com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE)
                {
                    ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.color.set(Color.RED);
                    ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                }
                ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).damage(new DamageInfo(source, damage[i], damageType));
            }

            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            if(!Settings.FAST_MODE)
                addToTop(new WaitAction(0.1F));
        }
    }

    public int damage[];
    private int baseDamage;
    private boolean firstFrame;
    private boolean utilizeBaseDamage;
}
