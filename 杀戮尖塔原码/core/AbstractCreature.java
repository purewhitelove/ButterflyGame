// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractCreature.java

package com.megacrit.cardcrawl.core;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.blights.Muzzle;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.daily.mods.Colossus;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PreservedInsect;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import com.megacrit.cardcrawl.vfx.TintEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.core:
//            CardCrawlGame, Settings

public abstract class AbstractCreature
{
    public static final class CreatureAnimation extends Enum
    {

        public static CreatureAnimation[] values()
        {
            return (CreatureAnimation[])$VALUES.clone();
        }

        public static CreatureAnimation valueOf(String name)
        {
            return (CreatureAnimation)Enum.valueOf(com/megacrit/cardcrawl/core/AbstractCreature$CreatureAnimation, name);
        }

        public static final CreatureAnimation FAST_SHAKE;
        public static final CreatureAnimation SHAKE;
        public static final CreatureAnimation ATTACK_FAST;
        public static final CreatureAnimation ATTACK_SLOW;
        public static final CreatureAnimation STAGGER;
        public static final CreatureAnimation HOP;
        public static final CreatureAnimation JUMP;
        private static final CreatureAnimation $VALUES[];

        static 
        {
            FAST_SHAKE = new CreatureAnimation("FAST_SHAKE", 0);
            SHAKE = new CreatureAnimation("SHAKE", 1);
            ATTACK_FAST = new CreatureAnimation("ATTACK_FAST", 2);
            ATTACK_SLOW = new CreatureAnimation("ATTACK_SLOW", 3);
            STAGGER = new CreatureAnimation("STAGGER", 4);
            HOP = new CreatureAnimation("HOP", 5);
            JUMP = new CreatureAnimation("JUMP", 6);
            $VALUES = (new CreatureAnimation[] {
                FAST_SHAKE, SHAKE, ATTACK_FAST, ATTACK_SLOW, STAGGER, HOP, JUMP
            });
        }

        private CreatureAnimation(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractCreature()
    {
        powers = new ArrayList();
        isDying = false;
        isDead = false;
        halfDead = false;
        flipHorizontal = false;
        flipVertical = false;
        escapeTimer = 0.0F;
        isEscaping = false;
        tips = new ArrayList();
        healthHideTimer = 0.0F;
        lastDamageTaken = 0;
        hbShowTimer = 0.0F;
        healthBarAnimTimer = 0.0F;
        blockAnimTimer = 0.0F;
        blockOffset = 0.0F;
        blockScale = 1.0F;
        hbAlpha = 0.0F;
        hbYOffset = HB_Y_OFFSET_DIST * 5F;
        hbBgColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        hbShadowColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        blockColor = new Color(0.6F, 0.93F, 0.98F, 0.0F);
        blockOutlineColor = new Color(0.6F, 0.93F, 0.98F, 0.0F);
        blockTextColor = new Color(0.9F, 0.9F, 0.9F, 0.0F);
        redHbBarColor = new Color(0.8F, 0.05F, 0.05F, 0.0F);
        greenHbBarColor = Color.valueOf("78c13c00");
        blueHbBarColor = Color.valueOf("31568c00");
        orangeHbBarColor = new Color(1.0F, 0.5F, 0.0F, 0.0F);
        hbTextColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        tint = new TintEffect();
        shakeToggle = true;
        animationTimer = 0.0F;
        atlas = null;
        reticleAlpha = 0.0F;
        reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        reticleShadowColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        reticleRendered = false;
        reticleOffset = 0.0F;
        reticleAnimTimer = 0.0F;
    }

    public abstract void damage(DamageInfo damageinfo);

    private void brokeBlock()
    {
        if(this instanceof AbstractMonster)
        {
            AbstractRelic r;
            for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onBlockBroken(this))
                r = (AbstractRelic)iterator.next();

        }
        AbstractDungeon.effectList.add(new HbBlockBrokenEffect((hb.cX - hb.width / 2.0F) + BLOCK_ICON_X, (hb.cY - hb.height / 2.0F) + BLOCK_ICON_Y));
        CardCrawlGame.sound.play("BLOCK_BREAK");
    }

    protected int decrementBlock(DamageInfo info, int damageAmount)
    {
        if(info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && currentBlock > 0)
        {
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
            if(damageAmount > currentBlock)
            {
                damageAmount -= currentBlock;
                if(Settings.SHOW_DMG_BLOCK)
                    AbstractDungeon.effectList.add(new BlockedNumberEffect(hb.cX, hb.cY + hb.height / 2.0F, Integer.toString(currentBlock)));
                loseBlock();
                brokeBlock();
            } else
            if(damageAmount == currentBlock)
            {
                damageAmount = 0;
                loseBlock();
                brokeBlock();
                AbstractDungeon.effectList.add(new BlockedWordEffect(this, hb.cX, hb.cY, TEXT[1]));
            } else
            {
                CardCrawlGame.sound.play("BLOCK_ATTACK");
                loseBlock(damageAmount);
                for(int i = 0; i < 18; i++)
                    AbstractDungeon.effectList.add(new BlockImpactLineEffect(hb.cX, hb.cY));

                if(Settings.SHOW_DMG_BLOCK)
                    AbstractDungeon.effectList.add(new BlockedNumberEffect(hb.cX, hb.cY + hb.height / 2.0F, Integer.toString(damageAmount)));
                damageAmount = 0;
            }
        }
        return damageAmount;
    }

    public void increaseMaxHp(int amount, boolean showEffect)
    {
        if(!Settings.isEndless || !AbstractDungeon.player.hasBlight("FullBelly"))
        {
            if(amount < 0)
                logger.info("Why are we decreasing health with increaseMaxHealth()?");
            maxHealth += amount;
            AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(hb.cX - animX, hb.cY, (new StringBuilder()).append(TEXT[2]).append(Integer.toString(amount)).toString(), Settings.GREEN_TEXT_COLOR));
            heal(amount, true);
            healthBarUpdatedEvent();
        }
    }

    public void decreaseMaxHealth(int amount)
    {
        if(amount < 0)
            logger.info("Why are we increasing health with decreaseMaxHealth()?");
        maxHealth -= amount;
        if(maxHealth <= 1)
            maxHealth = 1;
        if(currentHealth > maxHealth)
            currentHealth = maxHealth;
        healthBarUpdatedEvent();
    }

    protected void refreshHitboxLocation()
    {
        hb.move(drawX + hb_x + animX, drawY + hb_y + hb_h / 2.0F);
        healthHb.move(hb.cX, hb.cY - hb_h / 2.0F - healthHb.height / 2.0F);
    }

    public void updateAnimations()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$core$AbstractCreature$CreatureAnimation[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$core$AbstractCreature$CreatureAnimation = new int[CreatureAnimation.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$AbstractCreature$CreatureAnimation[CreatureAnimation.ATTACK_FAST.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$AbstractCreature$CreatureAnimation[CreatureAnimation.ATTACK_SLOW.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$AbstractCreature$CreatureAnimation[CreatureAnimation.FAST_SHAKE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$AbstractCreature$CreatureAnimation[CreatureAnimation.HOP.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$AbstractCreature$CreatureAnimation[CreatureAnimation.JUMP.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$AbstractCreature$CreatureAnimation[CreatureAnimation.SHAKE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$AbstractCreature$CreatureAnimation[CreatureAnimation.STAGGER.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
            }
        }

        if(animationTimer != 0.0F)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.core.AbstractCreature.CreatureAnimation[animation.ordinal()])
            {
            case 1: // '\001'
                updateFastAttackAnimation();
                break;

            case 2: // '\002'
                updateSlowAttackAnimation();
                break;

            case 3: // '\003'
                updateFastShakeAnimation();
                break;

            case 4: // '\004'
                updateHopAnimation();
                break;

            case 5: // '\005'
                updateJumpAnimation();
                break;

            case 6: // '\006'
                updateShakeAnimation();
                break;

            case 7: // '\007'
                updateStaggerAnimation();
                break;
            }
        refreshHitboxLocation();
        if(!isPlayer)
            ((AbstractMonster)this).refreshIntentHbLocation();
    }

    protected void updateFastAttackAnimation()
    {
        animationTimer -= Gdx.graphics.getDeltaTime();
        float targetPos = 90F * Settings.scale;
        if(!isPlayer)
            targetPos = -targetPos;
        if(animationTimer > 0.5F)
            animX = Interpolation.exp5In.apply(0.0F, targetPos, (1.0F - animationTimer / 1.0F) * 2.0F);
        else
        if(animationTimer < 0.0F)
        {
            animationTimer = 0.0F;
            animX = 0.0F;
        } else
        {
            animX = Interpolation.fade.apply(0.0F, targetPos, (animationTimer / 1.0F) * 2.0F);
        }
    }

    protected void updateSlowAttackAnimation()
    {
        animationTimer -= Gdx.graphics.getDeltaTime();
        float targetPos = 90F * Settings.scale;
        if(!isPlayer)
            targetPos = -targetPos;
        if(animationTimer > 0.5F)
            animX = Interpolation.exp10In.apply(0.0F, targetPos, (1.0F - animationTimer / 1.0F) * 2.0F);
        else
        if(animationTimer < 0.0F)
        {
            animationTimer = 0.0F;
            animX = 0.0F;
        } else
        {
            animX = Interpolation.fade.apply(0.0F, targetPos, (animationTimer / 1.0F) * 2.0F);
        }
    }

    protected void updateFastShakeAnimation()
    {
        animationTimer -= Gdx.graphics.getDeltaTime();
        if(animationTimer < 0.0F)
        {
            animationTimer = 0.0F;
            animX = 0.0F;
        } else
        if(shakeToggle)
        {
            animX += SHAKE_SPEED * Gdx.graphics.getDeltaTime();
            if(animX > SHAKE_THRESHOLD / 2.0F)
                shakeToggle = !shakeToggle;
        } else
        {
            animX -= SHAKE_SPEED * Gdx.graphics.getDeltaTime();
            if(animX < -SHAKE_THRESHOLD / 2.0F)
                shakeToggle = !shakeToggle;
        }
    }

    protected void updateHopAnimation()
    {
        vY -= 17F * Settings.scale;
        animY += vY * Gdx.graphics.getDeltaTime();
        if(animY < 0.0F)
        {
            animationTimer = 0.0F;
            animY = 0.0F;
        }
    }

    protected void updateJumpAnimation()
    {
        vY -= 17F * Settings.scale;
        animY += vY * Gdx.graphics.getDeltaTime();
        if(animY < 0.0F)
        {
            animationTimer = 0.0F;
            animY = 0.0F;
        }
    }

    protected void updateStaggerAnimation()
    {
        if(animationTimer != 0.0F)
        {
            animationTimer -= Gdx.graphics.getDeltaTime();
            if(!isPlayer)
                animX = Interpolation.pow2.apply(STAGGER_MOVE_SPEED, 0.0F, 1.0F - animationTimer / 0.3F);
            else
                animX = Interpolation.pow2.apply(-STAGGER_MOVE_SPEED, 0.0F, 1.0F - animationTimer / 0.3F);
            if(animationTimer < 0.0F)
            {
                animationTimer = 0.0F;
                animX = 0.0F;
                vX = STAGGER_MOVE_SPEED;
            }
        }
    }

    protected void updateShakeAnimation()
    {
        animationTimer -= Gdx.graphics.getDeltaTime();
        if(animationTimer < 0.0F)
        {
            animationTimer = 0.0F;
            animX = 0.0F;
        } else
        if(shakeToggle)
        {
            animX += SHAKE_SPEED * Gdx.graphics.getDeltaTime();
            if(animX > SHAKE_THRESHOLD)
                shakeToggle = !shakeToggle;
        } else
        {
            animX -= SHAKE_SPEED * Gdx.graphics.getDeltaTime();
            if(animX < -SHAKE_THRESHOLD)
                shakeToggle = !shakeToggle;
        }
    }

    protected void loadAnimation(String atlasUrl, String skeletonUrl, float scale)
    {
        atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(atlas);
        if(CardCrawlGame.dungeon != null && AbstractDungeon.player != null)
        {
            if(AbstractDungeon.player.hasRelic("PreservedInsect") && !isPlayer && AbstractDungeon.getCurrRoom().eliteTrigger)
                scale += 0.3F;
            if(ModHelper.isModEnabled("MonsterHunter") && !isPlayer)
                scale -= 0.3F;
        }
        json.setScale(Settings.renderScale / scale);
        com.esotericsoftware.spine.SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        skeleton = new Skeleton(skeletonData);
        skeleton.setColor(Color.WHITE);
        stateData = new AnimationStateData(skeletonData);
        state = new AnimationState(stateData);
    }

    public void heal(int healAmount, boolean showEffect)
    {
        if(Settings.isEndless && isPlayer && AbstractDungeon.player.hasBlight("FullBelly"))
        {
            healAmount /= 2;
            if(healAmount < 1)
                healAmount = 1;
        }
        if(isDying)
            return;
        Iterator iterator = AbstractDungeon.player.relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(isPlayer)
                healAmount = r.onPlayerHeal(healAmount);
        } while(true);
        for(Iterator iterator1 = powers.iterator(); iterator1.hasNext();)
        {
            AbstractPower p = (AbstractPower)iterator1.next();
            healAmount = p.onHeal(healAmount);
        }

        currentHealth += healAmount;
        if(currentHealth > maxHealth)
            currentHealth = maxHealth;
        if((float)currentHealth > (float)maxHealth / 2.0F && isBloodied)
        {
            isBloodied = false;
            AbstractRelic r2;
            for(Iterator iterator2 = AbstractDungeon.player.relics.iterator(); iterator2.hasNext(); r2.onNotBloodied())
                r2 = (AbstractRelic)iterator2.next();

        }
        if(healAmount > 0)
        {
            if(showEffect && isPlayer)
            {
                AbstractDungeon.topPanel.panelHealEffect();
                AbstractDungeon.effectsQueue.add(new HealEffect(hb.cX - animX, hb.cY, healAmount));
            }
            healthBarUpdatedEvent();
        }
    }

    public void heal(int amount)
    {
        heal(amount, true);
    }

    public void addBlock(int blockAmount)
    {
        float tmp = blockAmount;
        if(isPlayer)
        {
            for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext();)
            {
                AbstractRelic r = (AbstractRelic)iterator.next();
                tmp = r.onPlayerGainedBlock(tmp);
            }

            if(tmp > 0.0F)
            {
                AbstractPower p;
                for(Iterator iterator1 = powers.iterator(); iterator1.hasNext(); p.onGainedBlock(tmp))
                    p = (AbstractPower)iterator1.next();

            }
        }
        boolean effect = false;
        if(currentBlock == 0)
            effect = true;
        for(Iterator iterator2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); iterator2.hasNext();)
        {
            AbstractMonster m = (AbstractMonster)iterator2.next();
            Iterator iterator3 = m.powers.iterator();
            while(iterator3.hasNext()) 
            {
                AbstractPower p = (AbstractPower)iterator3.next();
                tmp = p.onPlayerGainedBlock(tmp);
            }
        }

        currentBlock += MathUtils.floor(tmp);
        if(currentBlock >= 99 && isPlayer)
            UnlockTracker.unlockAchievement("IMPERVIOUS");
        if(currentBlock > 999)
            currentBlock = 999;
        if(currentBlock == 999)
            UnlockTracker.unlockAchievement("BARRICADED");
        if(effect && currentBlock > 0)
            gainBlockAnimation();
        else
        if(blockAmount > 0 && blockAmount > 0)
        {
            Color tmpCol = Settings.GOLD_COLOR.cpy();
            tmpCol.a = blockTextColor.a;
            blockTextColor = tmpCol;
            blockScale = 5F;
        }
    }

    public void loseBlock(int amount, boolean noAnimation)
    {
        boolean effect = false;
        if(currentBlock != 0)
            effect = true;
        currentBlock -= amount;
        if(currentBlock < 0)
            currentBlock = 0;
        if(currentBlock == 0 && effect)
        {
            if(!noAnimation)
                AbstractDungeon.effectList.add(new HbBlockBrokenEffect((hb.cX - hb.width / 2.0F) + BLOCK_ICON_X, (hb.cY - hb.height / 2.0F) + BLOCK_ICON_Y));
        } else
        if(currentBlock > 0 && amount > 0)
        {
            Color tmp = Color.SCARLET.cpy();
            tmp.a = blockTextColor.a;
            blockTextColor = tmp;
            blockScale = 5F;
        }
    }

    public void loseBlock()
    {
        loseBlock(currentBlock);
    }

    public void loseBlock(boolean noAnimation)
    {
        loseBlock(currentBlock, noAnimation);
    }

    public void loseBlock(int amount)
    {
        loseBlock(amount, false);
    }

    public void showHealthBar()
    {
        hbShowTimer = 0.7F;
        hbAlpha = 0.0F;
    }

    public void hideHealthBar()
    {
        hbAlpha = 0.0F;
    }

    public void addPower(AbstractPower powerToApply)
    {
        boolean hasBuffAlready = false;
        Iterator iterator = powers.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractPower p = (AbstractPower)iterator.next();
            if(p.ID.equals(powerToApply.ID))
            {
                p.stackPower(powerToApply.amount);
                p.updateDescription();
                hasBuffAlready = true;
            }
        } while(true);
        if(!hasBuffAlready)
        {
            powers.add(powerToApply);
            if(isPlayer)
            {
                int buffCount = 0;
                Iterator iterator1 = powers.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    AbstractPower p = (AbstractPower)iterator1.next();
                    if(p.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF)
                        buffCount++;
                } while(true);
                if(buffCount >= 10)
                    UnlockTracker.unlockAchievement("POWERFUL");
            }
        }
    }

    public void applyStartOfTurnPowers()
    {
        AbstractPower p;
        for(Iterator iterator = powers.iterator(); iterator.hasNext(); p.atStartOfTurn())
            p = (AbstractPower)iterator.next();

    }

    public void applyTurnPowers()
    {
        AbstractPower p;
        for(Iterator iterator = powers.iterator(); iterator.hasNext(); p.duringTurn())
            p = (AbstractPower)iterator.next();

    }

    public void applyStartOfTurnPostDrawPowers()
    {
        AbstractPower p;
        for(Iterator iterator = powers.iterator(); iterator.hasNext(); p.atStartOfTurnPostDraw())
            p = (AbstractPower)iterator.next();

    }

    public void applyEndOfTurnTriggers()
    {
        AbstractPower p;
        for(Iterator iterator = powers.iterator(); iterator.hasNext(); p.atEndOfTurn(isPlayer))
        {
            p = (AbstractPower)iterator.next();
            if(!isPlayer)
                p.atEndOfTurnPreEndTurnCards(false);
        }

    }

    public void healthBarUpdatedEvent()
    {
        healthBarAnimTimer = 1.2F;
        targetHealthBarWidth = (hb.width * (float)currentHealth) / (float)maxHealth;
        if(maxHealth == currentHealth)
            healthBarWidth = targetHealthBarWidth;
        else
        if(currentHealth == 0)
        {
            healthBarWidth = 0.0F;
            targetHealthBarWidth = 0.0F;
        }
        if(targetHealthBarWidth > healthBarWidth)
            healthBarWidth = targetHealthBarWidth;
    }

    public void healthBarRevivedEvent()
    {
        healthBarAnimTimer = 1.2F;
        targetHealthBarWidth = (hb.width * (float)currentHealth) / (float)maxHealth;
        healthBarWidth = targetHealthBarWidth;
        hbBgColor.a = 0.75F;
        hbShadowColor.a = 0.5F;
        hbTextColor.a = 1.0F;
    }

    protected void updateHealthBar()
    {
        updateHbHoverFade();
        updateBlockAnimations();
        updateHbPopInAnimation();
        updateHbDamageAnimation();
        updateHbAlpha();
    }

    private void updateHbHoverFade()
    {
        if(healthHb.hovered)
        {
            healthHideTimer -= Gdx.graphics.getDeltaTime() * 4F;
            if(healthHideTimer < 0.2F)
                healthHideTimer = 0.2F;
        } else
        {
            healthHideTimer += Gdx.graphics.getDeltaTime() * 4F;
            if(healthHideTimer > 1.0F)
                healthHideTimer = 1.0F;
        }
    }

    private void updateHbAlpha()
    {
        if((this instanceof AbstractMonster) && ((AbstractMonster)this).isEscaping)
        {
            hbAlpha = MathHelper.fadeLerpSnap(hbAlpha, 0.0F);
            targetHealthBarWidth = 0.0F;
            hbBgColor.a = hbAlpha * 0.75F;
            hbShadowColor.a = hbAlpha * 0.5F;
            hbTextColor.a = hbAlpha;
            orangeHbBarColor.a = hbAlpha;
            redHbBarColor.a = hbAlpha;
            greenHbBarColor.a = hbAlpha;
            blueHbBarColor.a = hbAlpha;
            blockOutlineColor.a = hbAlpha;
        } else
        if(targetHealthBarWidth == 0.0F && healthBarAnimTimer <= 0.0F)
        {
            hbShadowColor.a = MathHelper.fadeLerpSnap(hbShadowColor.a, 0.0F);
            hbBgColor.a = MathHelper.fadeLerpSnap(hbBgColor.a, 0.0F);
            hbTextColor.a = MathHelper.fadeLerpSnap(hbTextColor.a, 0.0F);
            blockOutlineColor.a = MathHelper.fadeLerpSnap(blockOutlineColor.a, 0.0F);
        } else
        {
            hbBgColor.a = hbAlpha * 0.5F;
            hbShadowColor.a = hbAlpha * 0.2F;
            hbTextColor.a = hbAlpha;
            orangeHbBarColor.a = hbAlpha;
            redHbBarColor.a = hbAlpha;
            greenHbBarColor.a = hbAlpha;
            blueHbBarColor.a = hbAlpha;
            blockOutlineColor.a = hbAlpha;
        }
    }

    protected void gainBlockAnimation()
    {
        blockAnimTimer = 0.7F;
        blockTextColor.a = 0.0F;
        blockColor.a = 0.0F;
    }

    private void updateBlockAnimations()
    {
        if(currentBlock > 0)
        {
            if(blockAnimTimer > 0.0F)
            {
                blockAnimTimer -= Gdx.graphics.getDeltaTime();
                if(blockAnimTimer < 0.0F)
                    blockAnimTimer = 0.0F;
                blockOffset = Interpolation.swingOut.apply(BLOCK_OFFSET_DIST * 3F, 0.0F, 1.0F - blockAnimTimer / 0.7F);
                blockScale = Interpolation.pow3In.apply(3F, 1.0F, 1.0F - blockAnimTimer / 0.7F);
                blockColor.a = Interpolation.pow2Out.apply(0.0F, 1.0F, 1.0F - blockAnimTimer / 0.7F);
                blockTextColor.a = Interpolation.pow5In.apply(0.0F, 1.0F, 1.0F - blockAnimTimer / 0.7F);
            } else
            if(blockScale != 1.0F)
                blockScale = MathHelper.scaleLerpSnap(blockScale, 1.0F);
            if(blockTextColor.r != 1.0F)
                blockTextColor.r = MathHelper.slowColorLerpSnap(blockTextColor.r, 1.0F);
            if(blockTextColor.g != 1.0F)
                blockTextColor.g = MathHelper.slowColorLerpSnap(blockTextColor.g, 1.0F);
            if(blockTextColor.b != 1.0F)
                blockTextColor.b = MathHelper.slowColorLerpSnap(blockTextColor.b, 1.0F);
        }
    }

    private void updateHbPopInAnimation()
    {
        if(hbShowTimer > 0.0F)
        {
            hbShowTimer -= Gdx.graphics.getDeltaTime();
            if(hbShowTimer < 0.0F)
                hbShowTimer = 0.0F;
            hbAlpha = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - hbShowTimer / 0.7F);
            hbYOffset = Interpolation.exp10Out.apply(HB_Y_OFFSET_DIST * 5F, 0.0F, 1.0F - hbShowTimer / 0.7F);
        }
    }

    private void updateHbDamageAnimation()
    {
        if(healthBarAnimTimer > 0.0F)
            healthBarAnimTimer -= Gdx.graphics.getDeltaTime();
        if(healthBarWidth != targetHealthBarWidth && healthBarAnimTimer <= 0.0F && targetHealthBarWidth < healthBarWidth)
            healthBarWidth = MathHelper.uiLerpSnap(healthBarWidth, targetHealthBarWidth);
    }

    public void updatePowers()
    {
        for(int i = 0; i < powers.size(); i++)
            ((AbstractPower)powers.get(i)).update(i);

    }

    public static void initialize()
    {
        sr = new SkeletonMeshRenderer();
        sr.setPremultipliedAlpha(true);
    }

    public void renderPowerTips(SpriteBatch sb)
    {
        tips.clear();
        for(Iterator iterator = powers.iterator(); iterator.hasNext();)
        {
            AbstractPower p = (AbstractPower)iterator.next();
            if(p.region48 != null)
                tips.add(new PowerTip(p.name, p.description, p.region48));
            else
                tips.add(new PowerTip(p.name, p.description, p.img));
        }

        if(!tips.isEmpty())
            if(hb.cX + hb.width / 2.0F < TIP_X_THRESHOLD)
                TipHelper.queuePowerTips(hb.cX + hb.width / 2.0F + TIP_OFFSET_R_X, hb.cY + TipHelper.calculateAdditionalOffset(tips, hb.cY), tips);
            else
                TipHelper.queuePowerTips((hb.cX - hb.width / 2.0F) + TIP_OFFSET_L_X, hb.cY + TipHelper.calculateAdditionalOffset(tips, hb.cY), tips);
    }

    public void useFastAttackAnimation()
    {
        animX = 0.0F;
        animY = 0.0F;
        animationTimer = 0.4F;
        animation = CreatureAnimation.ATTACK_FAST;
    }

    public void useSlowAttackAnimation()
    {
        animX = 0.0F;
        animY = 0.0F;
        animationTimer = 1.0F;
        animation = CreatureAnimation.ATTACK_SLOW;
    }

    public void useHopAnimation()
    {
        animX = 0.0F;
        animY = 0.0F;
        vY = 300F * Settings.scale;
        animationTimer = 0.7F;
        animation = CreatureAnimation.HOP;
    }

    public void useJumpAnimation()
    {
        animX = 0.0F;
        animY = 0.0F;
        vY = 500F * Settings.scale;
        animationTimer = 0.7F;
        animation = CreatureAnimation.JUMP;
    }

    public void useStaggerAnimation()
    {
        if(animY == 0.0F)
        {
            animX = 0.0F;
            animationTimer = 0.3F;
            animation = CreatureAnimation.STAGGER;
        }
    }

    public void useFastShakeAnimation(float duration)
    {
        if(animY == 0.0F)
        {
            animX = 0.0F;
            animationTimer = duration;
            animation = CreatureAnimation.FAST_SHAKE;
        }
    }

    public void useShakeAnimation(float duration)
    {
        if(animY == 0.0F)
        {
            animX = 0.0F;
            animationTimer = duration;
            animation = CreatureAnimation.SHAKE;
        }
    }

    public AbstractPower getPower(String targetID)
    {
        for(Iterator iterator = powers.iterator(); iterator.hasNext();)
        {
            AbstractPower p = (AbstractPower)iterator.next();
            if(p.ID.equals(targetID))
                return p;
        }

        return null;
    }

    public boolean hasPower(String targetID)
    {
        for(Iterator iterator = powers.iterator(); iterator.hasNext();)
        {
            AbstractPower p = (AbstractPower)iterator.next();
            if(p.ID.equals(targetID))
                return true;
        }

        return false;
    }

    public boolean isDeadOrEscaped()
    {
        if(isDying || halfDead)
            return true;
        if(!isPlayer)
        {
            AbstractMonster m = (AbstractMonster)this;
            if(m.isEscaping)
                return true;
        }
        return false;
    }

    public void loseGold(int goldAmount)
    {
        if(goldAmount > 0)
        {
            gold -= goldAmount;
            if(gold < 0)
                gold = 0;
        } else
        {
            logger.info("NEGATIVE MONEY???");
        }
    }

    public void gainGold(int amount)
    {
        if(amount < 0)
            logger.info("NEGATIVE MONEY???");
        else
            gold += amount;
    }

    public void renderReticle(SpriteBatch sb)
    {
        reticleRendered = true;
        renderReticleCorner(sb, -hb.width / 2.0F + reticleOffset, hb.height / 2.0F - reticleOffset, false, false);
        renderReticleCorner(sb, hb.width / 2.0F - reticleOffset, hb.height / 2.0F - reticleOffset, true, false);
        renderReticleCorner(sb, -hb.width / 2.0F + reticleOffset, -hb.height / 2.0F + reticleOffset, false, true);
        renderReticleCorner(sb, hb.width / 2.0F - reticleOffset, -hb.height / 2.0F + reticleOffset, true, true);
    }

    public void renderReticle(SpriteBatch sb, Hitbox hb)
    {
        reticleRendered = true;
        renderReticleCorner(sb, -hb.width / 2.0F + reticleOffset, hb.height / 2.0F - reticleOffset, hb, false, false);
        renderReticleCorner(sb, hb.width / 2.0F - reticleOffset, hb.height / 2.0F - reticleOffset, hb, true, false);
        renderReticleCorner(sb, -hb.width / 2.0F + reticleOffset, -hb.height / 2.0F + reticleOffset, hb, false, true);
        renderReticleCorner(sb, hb.width / 2.0F - reticleOffset, -hb.height / 2.0F + reticleOffset, hb, true, true);
    }

    protected void updateReticle()
    {
        if(reticleRendered)
        {
            reticleRendered = false;
            reticleAlpha += Gdx.graphics.getDeltaTime() * 3F;
            if(reticleAlpha > 1.0F)
                reticleAlpha = 1.0F;
            reticleAnimTimer += Gdx.graphics.getDeltaTime();
            if(reticleAnimTimer > 1.0F)
                reticleAnimTimer = 1.0F;
            reticleOffset = Interpolation.elasticOut.apply(RETICLE_OFFSET_DIST, 0.0F, reticleAnimTimer);
        } else
        {
            reticleAlpha = 0.0F;
            reticleAnimTimer = 0.0F;
            reticleOffset = RETICLE_OFFSET_DIST;
        }
    }

    public void renderHealth(SpriteBatch sb)
    {
        if(Settings.hideCombatElements)
            return;
        float x = hb.cX - hb.width / 2.0F;
        float y = (hb.cY - hb.height / 2.0F) + hbYOffset;
        renderHealthBg(sb, x, y);
        if(targetHealthBarWidth != 0.0F)
        {
            renderOrangeHealthBar(sb, x, y);
            if(hasPower("Poison"))
                renderGreenHealthBar(sb, x, y);
            renderRedHealthBar(sb, x, y);
        }
        if(currentBlock != 0 && hbAlpha != 0.0F)
            renderBlockOutline(sb, x, y);
        renderHealthText(sb, y);
        if(currentBlock != 0 && hbAlpha != 0.0F)
            renderBlockIconAndValue(sb, x, y);
        renderPowerIcons(sb, x, y);
    }

    private void renderBlockOutline(SpriteBatch sb, float x, float y)
    {
        sb.setColor(blockOutlineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.BLOCK_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.BLOCK_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, hb.width, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.BLOCK_BAR_R, x + hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.setBlendFunction(770, 771);
    }

    private void renderBlockIconAndValue(SpriteBatch sb, float x, float y)
    {
        sb.setColor(blockColor);
        sb.draw(ImageMaster.BLOCK_ICON, (x + BLOCK_ICON_X) - 32F, ((y + BLOCK_ICON_Y) - 32F) + blockOffset, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(currentBlock), x + BLOCK_ICON_X, y - 16F * Settings.scale, blockTextColor, blockScale);
    }

    private void renderHealthBg(SpriteBatch sb, float x, float y)
    {
        sb.setColor(hbShadowColor);
        sb.draw(ImageMaster.HB_SHADOW_L, x - HEALTH_BAR_HEIGHT, (y - HEALTH_BG_OFFSET_X) + 3F * Settings.scale, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_B, x, (y - HEALTH_BG_OFFSET_X) + 3F * Settings.scale, hb.width, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_R, x + hb.width, (y - HEALTH_BG_OFFSET_X) + 3F * Settings.scale, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.setColor(hbBgColor);
        if(currentHealth != maxHealth)
        {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, hb.width, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        }
    }

    private void renderOrangeHealthBar(SpriteBatch sb, float x, float y)
    {
        sb.setColor(orangeHbBarColor);
        sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, healthBarWidth, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + healthBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
    }

    private void renderGreenHealthBar(SpriteBatch sb, float x, float y)
    {
        sb.setColor(greenHbBarColor);
        if(currentHealth > 0)
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, targetHealthBarWidth, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + targetHealthBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
    }

    private void renderRedHealthBar(SpriteBatch sb, float x, float y)
    {
        if(currentBlock > 0)
            sb.setColor(blueHbBarColor);
        else
            sb.setColor(redHbBarColor);
        if(!hasPower("Poison"))
        {
            if(currentHealth > 0)
                sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, targetHealthBarWidth, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + targetHealthBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        } else
        {
            int poisonAmt = getPower("Poison").amount;
            if(poisonAmt > 0 && hasPower("Intangible"))
                poisonAmt = 1;
            if(currentHealth > poisonAmt)
            {
                float w = 1.0F - (float)(currentHealth - poisonAmt) / (float)currentHealth;
                w *= targetHealthBarWidth;
                if(currentHealth > 0)
                    sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, targetHealthBarWidth - w, HEALTH_BAR_HEIGHT);
                sb.draw(ImageMaster.HEALTH_BAR_R, (x + targetHealthBarWidth) - w, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            }
        }
    }

    private void renderHealthText(SpriteBatch sb, float y)
    {
        if(targetHealthBarWidth != 0.0F)
        {
            float tmp = hbTextColor.a;
            hbTextColor.a *= healthHideTimer;
            FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, (new StringBuilder()).append(currentHealth).append("/").append(maxHealth).toString(), hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y + 5F * Settings.scale, hbTextColor);
            hbTextColor.a = tmp;
        } else
        {
            FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, TEXT[0], hb.cX, (y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y) - 1.0F * Settings.scale, hbTextColor);
        }
    }

    private void renderPowerIcons(SpriteBatch sb, float x, float y)
    {
        float offset = 10F * Settings.scale;
        for(Iterator iterator = powers.iterator(); iterator.hasNext();)
        {
            AbstractPower p = (AbstractPower)iterator.next();
            if(Settings.isMobile)
                p.renderIcons(sb, x + offset, y - 53F * Settings.scale, hbTextColor);
            else
                p.renderIcons(sb, x + offset, y - 48F * Settings.scale, hbTextColor);
            offset += POWER_ICON_PADDING_X;
        }

        offset = 0.0F * Settings.scale;
        for(Iterator iterator1 = powers.iterator(); iterator1.hasNext();)
        {
            AbstractPower p = (AbstractPower)iterator1.next();
            if(Settings.isMobile)
                p.renderAmount(sb, x + offset + 32F * Settings.scale, y - 75F * Settings.scale, hbTextColor);
            else
                p.renderAmount(sb, x + offset + 32F * Settings.scale, y - 66F * Settings.scale, hbTextColor);
            offset += POWER_ICON_PADDING_X;
        }

    }

    private void renderReticleCorner(SpriteBatch sb, float x, float y, Hitbox hb, boolean flipX, boolean flipY)
    {
        reticleShadowColor.a = reticleAlpha / 4F;
        sb.setColor(reticleShadowColor);
        sb.draw(ImageMaster.RETICLE_CORNER, ((hb.cX + x) - 18F) + 4F * Settings.scale, (hb.cY + y) - 18F - 4F * Settings.scale, 18F, 18F, 36F, 36F, Settings.scale, Settings.scale, 0.0F, 0, 0, 36, 36, flipX, flipY);
        reticleColor.a = reticleAlpha;
        sb.setColor(reticleColor);
        sb.draw(ImageMaster.RETICLE_CORNER, (hb.cX + x) - 18F, (hb.cY + y) - 18F, 18F, 18F, 36F, 36F, Settings.scale, Settings.scale, 0.0F, 0, 0, 36, 36, flipX, flipY);
    }

    private void renderReticleCorner(SpriteBatch sb, float x, float y, boolean flipX, boolean flipY)
    {
        reticleShadowColor.a = reticleAlpha / 4F;
        sb.setColor(reticleShadowColor);
        sb.draw(ImageMaster.RETICLE_CORNER, ((hb.cX + x) - 18F) + 4F * Settings.scale, (hb.cY + y) - 18F - 4F * Settings.scale, 18F, 18F, 36F, 36F, Settings.scale, Settings.scale, 0.0F, 0, 0, 36, 36, flipX, flipY);
        reticleColor.a = reticleAlpha;
        sb.setColor(reticleColor);
        sb.draw(ImageMaster.RETICLE_CORNER, (hb.cX + x) - 18F, (hb.cY + y) - 18F, 18F, 18F, 36F, 36F, Settings.scale, Settings.scale, 0.0F, 0, 0, 36, 36, flipX, flipY);
    }

    public abstract void render(SpriteBatch spritebatch);

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/core/AbstractCreature.getName());
    public String name;
    public String id;
    public ArrayList powers;
    public boolean isPlayer;
    public boolean isBloodied;
    public float drawX;
    public float drawY;
    public float dialogX;
    public float dialogY;
    public Hitbox hb;
    public int gold;
    public int displayGold;
    public boolean isDying;
    public boolean isDead;
    public boolean halfDead;
    public boolean flipHorizontal;
    public boolean flipVertical;
    public float escapeTimer;
    public boolean isEscaping;
    protected static final float TIP_X_THRESHOLD;
    protected static final float MULTI_TIP_Y_OFFSET;
    protected static final float TIP_OFFSET_R_X;
    protected static final float TIP_OFFSET_L_X;
    protected static final float TIP_OFFSET_Y;
    protected ArrayList tips;
    private static UIStrings uiStrings;
    public static final String TEXT[];
    public Hitbox healthHb;
    private float healthHideTimer;
    public int lastDamageTaken;
    public float hb_x;
    public float hb_y;
    public float hb_w;
    public float hb_h;
    public int currentHealth;
    public int maxHealth;
    public int currentBlock;
    private float healthBarWidth;
    private float targetHealthBarWidth;
    private float hbShowTimer;
    private float healthBarAnimTimer;
    private float blockAnimTimer;
    private float blockOffset;
    private float blockScale;
    public float hbAlpha;
    private float hbYOffset;
    private Color hbBgColor;
    private Color hbShadowColor;
    private Color blockColor;
    private Color blockOutlineColor;
    private Color blockTextColor;
    private Color redHbBarColor;
    private Color greenHbBarColor;
    private Color blueHbBarColor;
    private Color orangeHbBarColor;
    private Color hbTextColor;
    private static final float BLOCK_ANIM_TIME = 0.7F;
    private static final float BLOCK_OFFSET_DIST;
    private static final float SHOW_HB_TIME = 0.7F;
    private static final float HB_Y_OFFSET_DIST;
    protected static final float BLOCK_ICON_X;
    protected static final float BLOCK_ICON_Y;
    private static final int BLOCK_W = 64;
    private static final float HEALTH_BAR_PAUSE_DURATION = 1.2F;
    private static final float HEALTH_BAR_HEIGHT;
    private static final float HEALTH_BAR_OFFSET_Y;
    private static final float HEALTH_TEXT_OFFSET_Y;
    private static final float POWER_ICON_PADDING_X;
    private static final float HEALTH_BG_OFFSET_X;
    public TintEffect tint;
    public static SkeletonMeshRenderer sr;
    private boolean shakeToggle;
    private static final float SHAKE_THRESHOLD;
    private static final float SHAKE_SPEED;
    public float animX;
    public float animY;
    protected float vX;
    protected float vY;
    protected CreatureAnimation animation;
    protected float animationTimer;
    protected static final float SLOW_ATTACK_ANIM_DUR = 1F;
    protected static final float STAGGER_ANIM_DUR = 0.3F;
    protected static final float FAST_ATTACK_ANIM_DUR = 0.4F;
    protected static final float HOP_ANIM_DURATION = 0.7F;
    private static final float STAGGER_MOVE_SPEED;
    protected TextureAtlas atlas;
    protected Skeleton skeleton;
    public AnimationState state;
    protected AnimationStateData stateData;
    private static final int RETICLE_W = 36;
    public float reticleAlpha;
    private Color reticleColor;
    private Color reticleShadowColor;
    public boolean reticleRendered;
    private float reticleOffset;
    private float reticleAnimTimer;
    private static final float RETICLE_OFFSET_DIST;

    static 
    {
        TIP_X_THRESHOLD = 1544F * Settings.scale;
        MULTI_TIP_Y_OFFSET = 80F * Settings.scale;
        TIP_OFFSET_R_X = 20F * Settings.scale;
        TIP_OFFSET_L_X = -380F * Settings.scale;
        TIP_OFFSET_Y = 80F * Settings.scale;
        uiStrings = CardCrawlGame.languagePack.getUIString("AbstractCreature");
        TEXT = uiStrings.TEXT;
        BLOCK_OFFSET_DIST = 12F * Settings.scale;
        HB_Y_OFFSET_DIST = 12F * Settings.scale;
        BLOCK_ICON_X = -14F * Settings.scale;
        BLOCK_ICON_Y = -14F * Settings.scale;
        HEALTH_BAR_HEIGHT = 20F * Settings.scale;
        HEALTH_BAR_OFFSET_Y = -28F * Settings.scale;
        HEALTH_TEXT_OFFSET_Y = 6F * Settings.scale;
        POWER_ICON_PADDING_X = Settings.isMobile ? 55F * Settings.scale : 48F * Settings.scale;
        HEALTH_BG_OFFSET_X = 31F * Settings.scale;
        SHAKE_THRESHOLD = Settings.scale * 8F;
        SHAKE_SPEED = 150F * Settings.scale;
        STAGGER_MOVE_SPEED = 20F * Settings.scale;
        RETICLE_OFFSET_DIST = 15F * Settings.scale;
    }
}
