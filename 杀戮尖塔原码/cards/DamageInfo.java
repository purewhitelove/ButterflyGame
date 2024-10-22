// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamageInfo.java

package com.megacrit.cardcrawl.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.blights.Spear;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import java.util.ArrayList;
import java.util.Iterator;

public class DamageInfo
{
    public static final class DamageType extends Enum
    {

        public static DamageType[] values()
        {
            return (DamageType[])$VALUES.clone();
        }

        public static DamageType valueOf(String name)
        {
            return (DamageType)Enum.valueOf(com/megacrit/cardcrawl/cards/DamageInfo$DamageType, name);
        }

        public static final DamageType NORMAL;
        public static final DamageType THORNS;
        public static final DamageType HP_LOSS;
        private static final DamageType $VALUES[];

        static 
        {
            NORMAL = new DamageType("NORMAL", 0);
            THORNS = new DamageType("THORNS", 1);
            HP_LOSS = new DamageType("HP_LOSS", 2);
            $VALUES = (new DamageType[] {
                NORMAL, THORNS, HP_LOSS
            });
        }

        private DamageType(String s, int i)
        {
            super(s, i);
        }
    }


    public DamageInfo(AbstractCreature damageSource, int base, DamageType type)
    {
        isModified = false;
        owner = damageSource;
        this.type = type;
        this.base = base;
        output = base;
    }

    public DamageInfo(AbstractCreature owner, int base)
    {
        this(owner, base, DamageType.NORMAL);
    }

    public void applyPowers(AbstractCreature owner, AbstractCreature target)
    {
        output = base;
        isModified = false;
        float tmp = output;
        if(!owner.isPlayer)
        {
            if(Settings.isEndless && AbstractDungeon.player.hasBlight("DeadlyEnemies"))
            {
                float mod = AbstractDungeon.player.getBlight("DeadlyEnemies").effectFloat();
                tmp *= mod;
                if(base != (int)tmp)
                    isModified = true;
            }
            Iterator iterator = owner.powers.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractPower p = (AbstractPower)iterator.next();
                tmp = p.atDamageGive(tmp, type);
                if(base != (int)tmp)
                    isModified = true;
            } while(true);
            iterator = target.powers.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractPower p = (AbstractPower)iterator.next();
                tmp = p.atDamageReceive(tmp, type);
                if(base != (int)tmp)
                    isModified = true;
            } while(true);
            tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, type);
            if(base != (int)tmp)
                isModified = true;
            iterator = owner.powers.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractPower p = (AbstractPower)iterator.next();
                tmp = p.atDamageFinalGive(tmp, type);
                if(base != (int)tmp)
                    isModified = true;
            } while(true);
            iterator = target.powers.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractPower p = (AbstractPower)iterator.next();
                tmp = p.atDamageFinalReceive(tmp, type);
                if(base != (int)tmp)
                    isModified = true;
            } while(true);
            output = MathUtils.floor(tmp);
            if(output < 0)
                output = 0;
        } else
        {
            Iterator iterator1 = owner.powers.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractPower p = (AbstractPower)iterator1.next();
                tmp = p.atDamageGive(tmp, type);
                if(base != (int)tmp)
                    isModified = true;
            } while(true);
            tmp = AbstractDungeon.player.stance.atDamageGive(tmp, type);
            if(base != (int)tmp)
                isModified = true;
            iterator1 = target.powers.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractPower p = (AbstractPower)iterator1.next();
                tmp = p.atDamageReceive(tmp, type);
                if(base != (int)tmp)
                    isModified = true;
            } while(true);
            iterator1 = owner.powers.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractPower p = (AbstractPower)iterator1.next();
                tmp = p.atDamageFinalGive(tmp, type);
                if(base != (int)tmp)
                    isModified = true;
            } while(true);
            iterator1 = target.powers.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractPower p = (AbstractPower)iterator1.next();
                tmp = p.atDamageFinalReceive(tmp, type);
                if(base != (int)tmp)
                    isModified = true;
            } while(true);
            output = MathUtils.floor(tmp);
            if(output < 0)
                output = 0;
        }
    }

    public void applyEnemyPowersOnly(AbstractCreature target)
    {
        output = base;
        isModified = false;
        float tmp = output;
        Iterator iterator = target.powers.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractPower p = (AbstractPower)iterator.next();
            tmp = p.atDamageReceive(output, type);
            if(base != output)
                isModified = true;
        } while(true);
        iterator = target.powers.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractPower p = (AbstractPower)iterator.next();
            tmp = p.atDamageFinalReceive(output, type);
            if(base != output)
                isModified = true;
        } while(true);
        if(tmp < 0.0F)
            tmp = 0.0F;
        output = MathUtils.floor(tmp);
    }

    public static int[] createDamageMatrix(int baseDamage)
    {
        return createDamageMatrix(baseDamage, false);
    }

    public static int[] createDamageMatrix(int baseDamage, boolean isPureDamage)
    {
        int retVal[] = new int[AbstractDungeon.getMonsters().monsters.size()];
        for(int i = 0; i < retVal.length; i++)
        {
            DamageInfo info = new DamageInfo(AbstractDungeon.player, baseDamage);
            if(!isPureDamage)
                info.applyPowers(AbstractDungeon.player, (AbstractCreature)AbstractDungeon.getMonsters().monsters.get(i));
            retVal[i] = info.output;
        }

        return retVal;
    }

    public static int[] createDamageMatrix(int baseDamage, boolean isPureDamage, boolean isOrbDamage)
    {
        int retVal[] = new int[AbstractDungeon.getMonsters().monsters.size()];
        for(int i = 0; i < retVal.length; i++)
        {
            DamageInfo info = new DamageInfo(AbstractDungeon.player, baseDamage);
            if(isOrbDamage && ((AbstractMonster)AbstractDungeon.getMonsters().monsters.get(i)).hasPower("Lockon"))
                info.output = (int)((float)info.base * 1.5F);
            retVal[i] = info.output;
        }

        return retVal;
    }

    public AbstractCreature owner;
    public String name;
    public DamageType type;
    public int base;
    public int output;
    public boolean isModified;
}
