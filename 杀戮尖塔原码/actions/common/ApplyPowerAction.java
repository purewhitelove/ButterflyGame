// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApplyPowerAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.*;

public class ApplyPowerAction extends AbstractGameAction
{

    public ApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        if(Settings.FAST_MODE)
            startingDuration = 0.1F;
        else
        if(isFast)
            startingDuration = Settings.ACTION_DUR_FASTER;
        else
            startingDuration = Settings.ACTION_DUR_FAST;
        setValues(target, source, stackAmount);
        duration = startingDuration;
        this.powerToApply = powerToApply;
        if(AbstractDungeon.player.hasRelic("Snake Skull") && source != null && source.isPlayer && target != source && powerToApply.ID.equals("Poison"))
        {
            AbstractDungeon.player.getRelic("Snake Skull").flash();
            this.powerToApply.amount++;
            amount++;
        }
        if(powerToApply.ID.equals("Corruption"))
        {
            Iterator iterator = AbstractDungeon.player.hand.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
                    c.modifyCostForCombat(-9);
            } while(true);
            iterator = AbstractDungeon.player.drawPile.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
                    c.modifyCostForCombat(-9);
            } while(true);
            iterator = AbstractDungeon.player.discardPile.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
                    c.modifyCostForCombat(-9);
            } while(true);
            iterator = AbstractDungeon.player.exhaustPile.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
                    c.modifyCostForCombat(-9);
            } while(true);
        }
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.POWER;
        attackEffect = effect;
        if(AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            duration = 0.0F;
            startingDuration = 0.0F;
            isDone = true;
        }
    }

    public ApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast)
    {
        this(target, source, powerToApply, stackAmount, isFast, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE);
    }

    public ApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply)
    {
        this(target, source, powerToApply, powerToApply.amount);
    }

    public ApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount)
    {
        this(target, source, powerToApply, stackAmount, false, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE);
    }

    public ApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        this(target, source, powerToApply, stackAmount, false, effect);
    }

    public void update()
    {
        if(target == null || target.isDeadOrEscaped())
        {
            isDone = true;
            return;
        }
        if(duration == startingDuration)
        {
            if((powerToApply instanceof NoDrawPower) && target.hasPower(powerToApply.ID))
            {
                isDone = true;
                return;
            }
            if(source != null)
            {
                AbstractPower pow;
                for(Iterator iterator = source.powers.iterator(); iterator.hasNext(); pow.onApplyPower(powerToApply, target, source))
                    pow = (AbstractPower)iterator.next();

            }
            if(AbstractDungeon.player.hasRelic("Champion Belt") && source != null && source.isPlayer && target != source && powerToApply.ID.equals("Vulnerable") && !target.hasPower("Artifact"))
                AbstractDungeon.player.getRelic("Champion Belt").onTrigger(target);
            if((target instanceof AbstractMonster) && target.isDeadOrEscaped())
            {
                duration = 0.0F;
                isDone = true;
                return;
            }
            if(AbstractDungeon.player.hasRelic("Ginger") && target.isPlayer && powerToApply.ID.equals("Weakened"))
            {
                AbstractDungeon.player.getRelic("Ginger").flash();
                addToTop(new TextAboveCreatureAction(target, TEXT[1]));
                duration -= Gdx.graphics.getDeltaTime();
                return;
            }
            if(AbstractDungeon.player.hasRelic("Turnip") && target.isPlayer && powerToApply.ID.equals("Frail"))
            {
                AbstractDungeon.player.getRelic("Turnip").flash();
                addToTop(new TextAboveCreatureAction(target, TEXT[1]));
                duration -= Gdx.graphics.getDeltaTime();
                return;
            }
            if(target.hasPower("Artifact") && powerToApply.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF)
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
    private AbstractPower powerToApply;
    private float startingDuration;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ApplyPowerAction");
        TEXT = uiStrings.TEXT;
    }
}
