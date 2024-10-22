// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApplyPoisonOnRandomMonsterAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.*;

/**
 * @deprecated Class ApplyPoisonOnRandomMonsterAction is deprecated
 */

public class ApplyPoisonOnRandomMonsterAction extends AbstractGameAction
{

    public ApplyPoisonOnRandomMonsterAction(AbstractCreature source, int stackAmount, boolean isFast, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        if(Settings.FAST_MODE)
            startingDuration = 0.1F;
        else
        if(isFast)
            startingDuration = Settings.ACTION_DUR_FASTER;
        else
            startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
        target = null;
        this.source = source;
        amount = stackAmount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.POWER;
        attackEffect = effect;
        if(AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            duration = 0.0F;
            startingDuration = 0.0F;
            isDone = true;
        }
    }

    public void update()
    {
        if(duration == startingDuration)
        {
            target = AbstractDungeon.getRandomMonster();
            if(target == null)
            {
                isDone = true;
                return;
            }
            powerToApply = new PoisonPower(target, source, amount);
            if(source != null)
            {
                AbstractPower pow;
                for(Iterator iterator = source.powers.iterator(); iterator.hasNext(); pow.onApplyPower(powerToApply, target, source))
                    pow = (AbstractPower)iterator.next();

            }
            if(target.hasPower("Artifact"))
            {
                addToTop(new TextAboveCreatureAction(target, TEXT[0]));
                duration -= Gdx.graphics.getDeltaTime();
                CardCrawlGame.sound.play("NULLIFY_SFX");
                target.getPower("Artifact").flashWithoutSound();
                target.getPower("Artifact").onSpecificTrigger();
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
            boolean hasBuffAlready = false;
            Iterator iterator1 = target.powers.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractPower p = (AbstractPower)iterator1.next();
                if(p.ID.equals(powerToApply.ID) && !p.ID.equals("Night Terror"))
                {
                    p.stackPower(amount);
                    p.flash();
                    if(((p instanceof StrengthPower) || (p instanceof DexterityPower)) && amount <= 0)
                        AbstractDungeon.effectList.add(new PowerDebuffEffect(target.hb.cX - target.animX, target.hb.cY + target.hb.height / 2.0F, (new StringBuilder()).append(powerToApply.name).append(TEXT[3]).toString()));
                    else
                    if(amount > 0)
                    {
                        if(p.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF || (p instanceof StrengthPower) || (p instanceof DexterityPower))
                            AbstractDungeon.effectList.add(new PowerBuffEffect(target.hb.cX - target.animX, target.hb.cY + target.hb.height / 2.0F, (new StringBuilder()).append("+").append(Integer.toString(amount)).append(" ").append(powerToApply.name).toString()));
                        else
                            AbstractDungeon.effectList.add(new PowerDebuffEffect(target.hb.cX - target.animX, target.hb.cY + target.hb.height / 2.0F, (new StringBuilder()).append("+").append(Integer.toString(amount)).append(" ").append(powerToApply.name).toString()));
                    } else
                    if(p.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF)
                        AbstractDungeon.effectList.add(new PowerBuffEffect(target.hb.cX - target.animX, target.hb.cY + target.hb.height / 2.0F, (new StringBuilder()).append(powerToApply.name).append(TEXT[3]).toString()));
                    else
                        AbstractDungeon.effectList.add(new PowerDebuffEffect(target.hb.cX - target.animX, target.hb.cY + target.hb.height / 2.0F, (new StringBuilder()).append(powerToApply.name).append(TEXT[3]).toString()));
                    p.updateDescription();
                    hasBuffAlready = true;
                    AbstractDungeon.onModifyPower();
                }
            } while(true);
            if(powerToApply.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF)
                target.useFastShakeAnimation(0.5F);
            if(!hasBuffAlready)
            {
                target.powers.add(powerToApply);
                Collections.sort(target.powers);
                powerToApply.onInitialApplication();
                powerToApply.flash();
                if(amount < 0 && (powerToApply.ID.equals("Strength") || powerToApply.ID.equals("Dexterity") || powerToApply.ID.equals("Focus")))
                    AbstractDungeon.effectList.add(new PowerDebuffEffect(target.hb.cX - target.animX, target.hb.cY + target.hb.height / 2.0F, (new StringBuilder()).append(powerToApply.name).append(TEXT[3]).toString()));
                else
                if(powerToApply.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF)
                    AbstractDungeon.effectList.add(new PowerBuffEffect(target.hb.cX - target.animX, target.hb.cY + target.hb.height / 2.0F, powerToApply.name));
                else
                    AbstractDungeon.effectList.add(new PowerDebuffEffect(target.hb.cX - target.animX, target.hb.cY + target.hb.height / 2.0F, powerToApply.name));
                AbstractDungeon.onModifyPower();
                if(target.isPlayer)
                {
                    int buffCount = 0;
                    Iterator iterator2 = target.powers.iterator();
                    do
                    {
                        if(!iterator2.hasNext())
                            break;
                        AbstractPower p = (AbstractPower)iterator2.next();
                        if(p.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF)
                            buffCount++;
                    } while(true);
                    if(buffCount >= 10)
                        UnlockTracker.unlockAchievement("POWERFUL");
                }
            }
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private float startingDuration;
    private AbstractPower powerToApply;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ApplyPowerAction");
        TEXT = uiStrings.TEXT;
    }
}
