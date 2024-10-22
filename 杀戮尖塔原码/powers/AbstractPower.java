// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractPower.java

package com.megacrit.cardcrawl.powers;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractPower
    implements Comparable
{
    public static final class PowerType extends Enum
    {

        public static PowerType[] values()
        {
            return (PowerType[])$VALUES.clone();
        }

        public static PowerType valueOf(String name)
        {
            return (PowerType)Enum.valueOf(com/megacrit/cardcrawl/powers/AbstractPower$PowerType, name);
        }

        public static final PowerType BUFF;
        public static final PowerType DEBUFF;
        private static final PowerType $VALUES[];

        static 
        {
            BUFF = new PowerType("BUFF", 0);
            DEBUFF = new PowerType("DEBUFF", 1);
            $VALUES = (new PowerType[] {
                BUFF, DEBUFF
            });
        }

        private PowerType(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractPower()
    {
        fontScale = 1.0F;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        redColor = new Color(1.0F, 0.0F, 0.0F, 1.0F);
        greenColor = new Color(0.0F, 1.0F, 0.0F, 1.0F);
        effect = new ArrayList();
        amount = -1;
        priority = 5;
        type = PowerType.BUFF;
        isTurnBased = false;
        isPostActionPower = false;
        canGoNegative = false;
    }

    public static void initialize()
    {
        atlas = new TextureAtlas(Gdx.files.internal("powers/powers.atlas"));
    }

    protected void loadRegion(String fileName)
    {
        region48 = atlas.findRegion((new StringBuilder()).append("48/").append(fileName).toString());
        region128 = atlas.findRegion((new StringBuilder()).append("128/").append(fileName).toString());
    }

    public String toString()
    {
        return (new StringBuilder()).append("[").append(name).append("]: ").append(description).toString();
    }

    public void playApplyPowerSfx()
    {
        if(type == PowerType.BUFF)
        {
            int roll = MathUtils.random(0, 2);
            if(roll == 0)
                CardCrawlGame.sound.play("BUFF_1");
            else
            if(roll == 1)
                CardCrawlGame.sound.play("BUFF_2");
            else
                CardCrawlGame.sound.play("BUFF_3");
        } else
        {
            int roll = MathUtils.random(0, 2);
            if(roll == 0)
                CardCrawlGame.sound.play("DEBUFF_1");
            else
            if(roll == 1)
                CardCrawlGame.sound.play("DEBUFF_2");
            else
                CardCrawlGame.sound.play("DEBUFF_3");
        }
    }

    public void updateParticles()
    {
    }

    public void update(int slot)
    {
        updateFlash();
        updateFontScale();
        updateColor();
    }

    protected void addToBot(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToTop(action);
    }

    private void updateFlash()
    {
        Iterator i = effect.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
    }

    private void updateColor()
    {
        if(color.a != 1.0F)
            color.a = MathHelper.fadeLerpSnap(color.a, 1.0F);
    }

    private void updateFontScale()
    {
        if(fontScale != 1.0F)
        {
            fontScale = MathUtils.lerp(fontScale, 1.0F, Gdx.graphics.getDeltaTime() * 10F);
            if(fontScale - 1.0F < 0.05F)
                fontScale = 1.0F;
        }
    }

    public void updateDescription()
    {
    }

    public void stackPower(int stackAmount)
    {
        if(amount == -1)
        {
            logger.info((new StringBuilder()).append(name).append(" does not stack").toString());
            return;
        } else
        {
            fontScale = 8F;
            amount += stackAmount;
            return;
        }
    }

    public void reducePower(int reduceAmount)
    {
        if(amount - reduceAmount <= 0)
        {
            fontScale = 8F;
            amount = 0;
        } else
        {
            fontScale = 8F;
            amount -= reduceAmount;
        }
    }

    public String getHoverMessage()
    {
        return (new StringBuilder()).append(name).append(":\n").append(description).toString();
    }

    public void renderIcons(SpriteBatch sb, float x, float y, Color c)
    {
        if(img != null)
        {
            sb.setColor(c);
            sb.draw(img, x - 12F, y - 12F, 16F, 16F, 32F, 32F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 32, 32, false, false);
        } else
        {
            sb.setColor(c);
            if(Settings.isMobile)
                sb.draw(region48, x - (float)region48.packedWidth / 2.0F, y - (float)region48.packedHeight / 2.0F, (float)region48.packedWidth / 2.0F, (float)region48.packedHeight / 2.0F, region48.packedWidth, region48.packedHeight, Settings.scale * 1.17F, Settings.scale * 1.17F, 0.0F);
            else
                sb.draw(region48, x - (float)region48.packedWidth / 2.0F, y - (float)region48.packedHeight / 2.0F, (float)region48.packedWidth / 2.0F, (float)region48.packedHeight / 2.0F, region48.packedWidth, region48.packedHeight, Settings.scale, Settings.scale, 0.0F);
        }
        AbstractGameEffect e;
        for(Iterator iterator = effect.iterator(); iterator.hasNext(); e.render(sb, x, y))
            e = (AbstractGameEffect)iterator.next();

    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c)
    {
        if(amount > 0)
        {
            if(!isTurnBased)
            {
                greenColor.a = c.a;
                c = greenColor;
            }
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount), x, y, fontScale, c);
        } else
        if(amount < 0 && canGoNegative)
        {
            redColor.a = c.a;
            c = redColor;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount), x, y, fontScale, c);
        }
    }

    public float atDamageGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        return damage;
    }

    public float atDamageFinalGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        return damage;
    }

    public float atDamageFinalReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        return damage;
    }

    public float atDamageReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType damageType)
    {
        return damage;
    }

    public float atDamageGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, AbstractCard card)
    {
        return atDamageGive(damage, type);
    }

    public float atDamageFinalGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, AbstractCard card)
    {
        return atDamageFinalGive(damage, type);
    }

    public float atDamageFinalReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, AbstractCard card)
    {
        return atDamageFinalReceive(damage, type);
    }

    public float atDamageReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType damageType, AbstractCard card)
    {
        return atDamageReceive(damage, damageType);
    }

    public void atStartOfTurn()
    {
    }

    public void duringTurn()
    {
    }

    public void atStartOfTurnPostDraw()
    {
    }

    public void atEndOfTurn(boolean flag)
    {
    }

    public void atEndOfTurnPreEndTurnCards(boolean flag)
    {
    }

    public void atEndOfRound()
    {
    }

    public void onScry()
    {
    }

    public void onDamageAllEnemies(int ai[])
    {
    }

    public int onHeal(int healAmount)
    {
        return healAmount;
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        return damageAmount;
    }

    public void onAttack(DamageInfo damageinfo, int i, AbstractCreature abstractcreature)
    {
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount)
    {
        return damageAmount;
    }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount)
    {
        return damageAmount;
    }

    public void onInflictDamage(DamageInfo damageinfo, int i, AbstractCreature abstractcreature)
    {
    }

    public void onEvokeOrb(AbstractOrb abstractorb)
    {
    }

    public void onCardDraw(AbstractCard abstractcard)
    {
    }

    public void onPlayCard(AbstractCard abstractcard, AbstractMonster abstractmonster)
    {
    }

    public void onUseCard(AbstractCard abstractcard, UseCardAction usecardaction)
    {
    }

    public void onAfterUseCard(AbstractCard abstractcard, UseCardAction usecardaction)
    {
    }

    public void wasHPLost(DamageInfo damageinfo, int i)
    {
    }

    public void onSpecificTrigger()
    {
    }

    public void triggerMarks(AbstractCard abstractcard)
    {
    }

    public void onDeath()
    {
    }

    public void onChannel(AbstractOrb abstractorb)
    {
    }

    public void atEnergyGain()
    {
    }

    public void onExhaust(AbstractCard abstractcard)
    {
    }

    public void onChangeStance(AbstractStance abstractstance, AbstractStance abstractstance1)
    {
    }

    public float modifyBlock(float blockAmount)
    {
        return blockAmount;
    }

    public float modifyBlock(float blockAmount, AbstractCard card)
    {
        return modifyBlock(blockAmount);
    }

    public float modifyBlockLast(float blockAmount)
    {
        return blockAmount;
    }

    public void onGainedBlock(float f)
    {
    }

    public int onPlayerGainedBlock(float blockAmount)
    {
        return MathUtils.floor(blockAmount);
    }

    public int onPlayerGainedBlock(int blockAmount)
    {
        return blockAmount;
    }

    public void onGainCharge(int i)
    {
    }

    public void onRemove()
    {
    }

    public void onEnergyRecharge()
    {
    }

    public void onDrawOrDiscard()
    {
    }

    public void onAfterCardPlayed(AbstractCard abstractcard)
    {
    }

    public void onInitialApplication()
    {
    }

    public int compareTo(AbstractPower other)
    {
        return priority - other.priority;
    }

    public void flash()
    {
        effect.add(new GainPowerEffect(this));
        AbstractDungeon.effectList.add(new FlashPowerEffect(this));
    }

    public void flashWithoutSound()
    {
        effect.add(new SilentGainPowerEffect(this));
        AbstractDungeon.effectList.add(new FlashPowerEffect(this));
    }

    public void onApplyPower(AbstractPower abstractpower, AbstractCreature abstractcreature, AbstractCreature abstractcreature1)
    {
    }

    public HashMap getLocStrings()
    {
        HashMap powerData = new HashMap();
        powerData.put("name", name);
        powerData.put("description", DESCRIPTIONS);
        return powerData;
    }

    public int onLoseHp(int damageAmount)
    {
        return damageAmount;
    }

    public void onVictory()
    {
    }

    public boolean canPlayCard(AbstractCard card)
    {
        return true;
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((AbstractPower)obj);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/powers/AbstractPower.getName());
    public static TextureAtlas atlas;
    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region48;
    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region128;
    private static final int RAW_W = 32;
    protected static final float POWER_STACK_FONT_SCALE = 8F;
    private static final float FONT_LERP = 10F;
    private static final float FONT_SNAP_THRESHOLD = 0.05F;
    protected float fontScale;
    private Color color;
    private Color redColor;
    private Color greenColor;
    private ArrayList effect;
    public AbstractCreature owner;
    public String name;
    public String description;
    public String ID;
    public Texture img;
    public int amount;
    public int priority;
    public PowerType type;
    protected boolean isTurnBased;
    public boolean isPostActionPower;
    public boolean canGoNegative;
    public static String DESCRIPTIONS[];

}
