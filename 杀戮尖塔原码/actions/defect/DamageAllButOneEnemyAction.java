// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamageAllButOneEnemyAction.java

package com.megacrit.cardcrawl.actions.defect;

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

public class DamageAllButOneEnemyAction extends AbstractGameAction
{

    public DamageAllButOneEnemyAction(AbstractCreature source, AbstractCreature target, int amount[], com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect, boolean isFast)
    {
        firstFrame = true;
        setValues(target, source, amount[0]);
        damage = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        damageType = type;
        attackEffect = effect;
        if(isFast)
            duration = Settings.ACTION_DUR_XFAST;
        else
            duration = Settings.ACTION_DUR_FAST;
    }

    public DamageAllButOneEnemyAction(AbstractCreature source, AbstractCreature target, int amount[], com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        this(source, target, amount, type, effect, false);
    }

    public void update()
    {
        if(firstFrame)
        {
            boolean playedMusic = false;
            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            for(int i = 0; i < temp; i++)
            {
                if(AbstractDungeon.getCurrRoom().monsters.monsters.get(i) == target || ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDying || ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).currentHealth <= 0 || ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isEscaping)
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
                if(AbstractDungeon.getCurrRoom().monsters.monsters.get(i) != target && !((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDeadOrEscaped())
                {
                    if(attackEffect == com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON)
                    {
                        ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.color = Color.CHARTREUSE.cpy();
                        ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                    } else
                    if(attackEffect == com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE)
                    {
                        ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.color = Color.RED.cpy();
                        ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                    }
                    DamageInfo info = new DamageInfo(source, damage[i], damageType);
                    info.applyPowers(source, (AbstractCreature)AbstractDungeon.getCurrRoom().monsters.monsters.get(i));
                    ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).damage(info);
                }
                if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                    AbstractDungeon.actionManager.clearPostCombatActions();
                addToTop(new WaitAction(0.1F));
            }

        }
    }

    public int damage[];
    private boolean firstFrame;
}
