// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractGameAction.java

package com.megacrit.cardcrawl.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// Referenced classes of package com.megacrit.cardcrawl.actions:
//            GameActionManager

public abstract class AbstractGameAction
{
    public static final class ActionType extends Enum
    {

        public static ActionType[] values()
        {
            return (ActionType[])$VALUES.clone();
        }

        public static ActionType valueOf(String name)
        {
            return (ActionType)Enum.valueOf(com/megacrit/cardcrawl/actions/AbstractGameAction$ActionType, name);
        }

        public static final ActionType BLOCK;
        public static final ActionType POWER;
        public static final ActionType CARD_MANIPULATION;
        public static final ActionType DAMAGE;
        public static final ActionType DEBUFF;
        public static final ActionType DISCARD;
        public static final ActionType DRAW;
        public static final ActionType EXHAUST;
        public static final ActionType HEAL;
        public static final ActionType ENERGY;
        public static final ActionType TEXT;
        public static final ActionType USE;
        public static final ActionType CLEAR_CARD_QUEUE;
        public static final ActionType DIALOG;
        public static final ActionType SPECIAL;
        public static final ActionType WAIT;
        public static final ActionType SHUFFLE;
        public static final ActionType REDUCE_POWER;
        private static final ActionType $VALUES[];

        static 
        {
            BLOCK = new ActionType("BLOCK", 0);
            POWER = new ActionType("POWER", 1);
            CARD_MANIPULATION = new ActionType("CARD_MANIPULATION", 2);
            DAMAGE = new ActionType("DAMAGE", 3);
            DEBUFF = new ActionType("DEBUFF", 4);
            DISCARD = new ActionType("DISCARD", 5);
            DRAW = new ActionType("DRAW", 6);
            EXHAUST = new ActionType("EXHAUST", 7);
            HEAL = new ActionType("HEAL", 8);
            ENERGY = new ActionType("ENERGY", 9);
            TEXT = new ActionType("TEXT", 10);
            USE = new ActionType("USE", 11);
            CLEAR_CARD_QUEUE = new ActionType("CLEAR_CARD_QUEUE", 12);
            DIALOG = new ActionType("DIALOG", 13);
            SPECIAL = new ActionType("SPECIAL", 14);
            WAIT = new ActionType("WAIT", 15);
            SHUFFLE = new ActionType("SHUFFLE", 16);
            REDUCE_POWER = new ActionType("REDUCE_POWER", 17);
            $VALUES = (new ActionType[] {
                BLOCK, POWER, CARD_MANIPULATION, DAMAGE, DEBUFF, DISCARD, DRAW, EXHAUST, HEAL, ENERGY, 
                TEXT, USE, CLEAR_CARD_QUEUE, DIALOG, SPECIAL, WAIT, SHUFFLE, REDUCE_POWER
            });
        }

        private ActionType(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class AttackEffect extends Enum
    {

        public static AttackEffect[] values()
        {
            return (AttackEffect[])$VALUES.clone();
        }

        public static AttackEffect valueOf(String name)
        {
            return (AttackEffect)Enum.valueOf(com/megacrit/cardcrawl/actions/AbstractGameAction$AttackEffect, name);
        }

        public static final AttackEffect BLUNT_LIGHT;
        public static final AttackEffect BLUNT_HEAVY;
        public static final AttackEffect SLASH_DIAGONAL;
        public static final AttackEffect SMASH;
        public static final AttackEffect SLASH_HEAVY;
        public static final AttackEffect SLASH_HORIZONTAL;
        public static final AttackEffect SLASH_VERTICAL;
        public static final AttackEffect NONE;
        public static final AttackEffect FIRE;
        public static final AttackEffect POISON;
        public static final AttackEffect SHIELD;
        public static final AttackEffect LIGHTNING;
        private static final AttackEffect $VALUES[];

        static 
        {
            BLUNT_LIGHT = new AttackEffect("BLUNT_LIGHT", 0);
            BLUNT_HEAVY = new AttackEffect("BLUNT_HEAVY", 1);
            SLASH_DIAGONAL = new AttackEffect("SLASH_DIAGONAL", 2);
            SMASH = new AttackEffect("SMASH", 3);
            SLASH_HEAVY = new AttackEffect("SLASH_HEAVY", 4);
            SLASH_HORIZONTAL = new AttackEffect("SLASH_HORIZONTAL", 5);
            SLASH_VERTICAL = new AttackEffect("SLASH_VERTICAL", 6);
            NONE = new AttackEffect("NONE", 7);
            FIRE = new AttackEffect("FIRE", 8);
            POISON = new AttackEffect("POISON", 9);
            SHIELD = new AttackEffect("SHIELD", 10);
            LIGHTNING = new AttackEffect("LIGHTNING", 11);
            $VALUES = (new AttackEffect[] {
                BLUNT_LIGHT, BLUNT_HEAVY, SLASH_DIAGONAL, SMASH, SLASH_HEAVY, SLASH_HORIZONTAL, SLASH_VERTICAL, NONE, FIRE, POISON, 
                SHIELD, LIGHTNING
            });
        }

        private AttackEffect(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractGameAction()
    {
        attackEffect = AttackEffect.NONE;
        isDone = false;
    }

    protected void setValues(AbstractCreature target, DamageInfo info)
    {
        this.target = target;
        source = info.owner;
        amount = info.output;
        duration = 0.5F;
    }

    protected void setValues(AbstractCreature target, AbstractCreature source, int amount)
    {
        this.target = target;
        this.source = source;
        this.amount = amount;
        duration = 0.5F;
    }

    protected void setValues(AbstractCreature target, AbstractCreature source)
    {
        this.target = target;
        this.source = source;
        amount = 0;
        duration = 0.5F;
    }

    protected boolean isDeadOrEscaped(AbstractCreature target)
    {
        if(target.isDying || target.halfDead)
            return true;
        if(!target.isPlayer)
        {
            AbstractMonster m = (AbstractMonster)target;
            if(m.isEscaping)
                return true;
        }
        return false;
    }

    protected void addToBot(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public abstract void update();

    protected void tickDuration()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    protected boolean shouldCancelAction()
    {
        return target == null || source != null && source.isDying || target.isDeadOrEscaped();
    }

    protected static final float DEFAULT_DURATION = 0.5F;
    protected float duration;
    protected float startDuration;
    public ActionType actionType;
    public AttackEffect attackEffect;
    public com.megacrit.cardcrawl.cards.DamageInfo.DamageType damageType;
    public boolean isDone;
    public int amount;
    public AbstractCreature target;
    public AbstractCreature source;
}
