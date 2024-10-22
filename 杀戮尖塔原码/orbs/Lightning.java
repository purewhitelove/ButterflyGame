// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Lightning.java

package com.megacrit.cardcrawl.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.ElectroPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.orbs:
//            AbstractOrb

public class Lightning extends AbstractOrb
{

    public Lightning()
    {
        vfxTimer = 1.0F;
        ID = "Lightning";
        img = ImageMaster.ORB_LIGHTNING;
        name = orbString.NAME;
        baseEvokeAmount = 8;
        evokeAmount = baseEvokeAmount;
        basePassiveAmount = 3;
        passiveAmount = basePassiveAmount;
        updateDescription();
        angle = MathUtils.random(360F);
        channelAnimTimer = 0.5F;
    }

    public void updateDescription()
    {
        applyFocus();
        description = (new StringBuilder()).append(orbString.DESCRIPTION[0]).append(passiveAmount).append(orbString.DESCRIPTION[1]).append(evokeAmount).append(orbString.DESCRIPTION[2]).toString();
    }

    public void onEvoke()
    {
        if(AbstractDungeon.player.hasPower("Electro"))
            AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, evokeAmount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), true));
        else
            AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, evokeAmount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), false));
    }

    public void onEndOfTurn()
    {
        if(AbstractDungeon.player.hasPower("Electro"))
        {
            float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
            if(Settings.FAST_MODE)
                speedTime = 0.0F;
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
            AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, passiveAmount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), true));
        } else
        {
            AbstractDungeon.actionManager.addToBottom(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, passiveAmount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), this, false));
        }
    }

    private void triggerPassiveEffect(DamageInfo info, boolean hitAll)
    {
        if(!hitAll)
        {
            AbstractCreature m = AbstractDungeon.getRandomMonster();
            if(m != null)
            {
                float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
                if(Settings.FAST_MODE)
                    speedTime = 0.0F;
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE, true));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.drawX, m.drawY), speedTime));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
            }
        } else
        {
            float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
            if(Settings.FAST_MODE)
                speedTime = 0.0F;
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(info.base, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m3 = (AbstractMonster)iterator.next();
                if(!m3.isDeadOrEscaped() && !m3.halfDead)
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m3.drawX, m3.drawY), speedTime));
            } while(true);
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
        }
    }

    public void triggerEvokeAnimation()
    {
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(cX, cY));
    }

    public void updateAnimation()
    {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 180F;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if(vfxTimer < 0.0F)
        {
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(cX, cY));
            if(MathUtils.randomBoolean())
                AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(cX, cY));
            vfxTimer = MathUtils.random(0.15F, 0.8F);
        }
    }

    public void render(SpriteBatch sb)
    {
        shineColor.a = c.a / 2.0F;
        sb.setColor(shineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 48F, (cY - 48F) + bobEffect.y, 48F, 48F, 96F, 96F, scale + MathUtils.sin(angle / 12.56637F) * 0.05F + 0.1963495F, scale * 1.2F, angle, 0, 0, 96, 96, false, false);
        sb.draw(img, cX - 48F, (cY - 48F) + bobEffect.y, 48F, 48F, 96F, 96F, scale * 1.2F, scale + MathUtils.sin(angle / 12.56637F) * 0.05F + 0.1963495F, -angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        sb.setColor(c);
        sb.draw(img, cX - 48F, (cY - 48F) + bobEffect.y, 48F, 48F, 96F, 96F, scale, scale, angle / 12F, 0, 0, 96, 96, false, false);
        renderText(sb);
        hb.render(sb);
    }

    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }

    public AbstractOrb makeCopy()
    {
        return new Lightning();
    }

    public static final String ORB_ID = "Lightning";
    private static final OrbStrings orbString;
    private float vfxTimer;
    private static final float PI_DIV_16 = 0.1963495F;
    private static final float ORB_WAVY_DIST = 0.05F;
    private static final float PI_4 = 12.56637F;
    private static final float ORB_BORDER_SCALE = 1.2F;

    static 
    {
        orbString = CardCrawlGame.languagePack.getOrbString("Lightning");
    }
}
