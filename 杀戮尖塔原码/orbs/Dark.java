// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Dark.java

package com.megacrit.cardcrawl.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DarkOrbEvokeAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.orbs:
//            AbstractOrb

public class Dark extends AbstractOrb
{

    public Dark()
    {
        vfxTimer = 0.5F;
        ID = "Dark";
        img = ImageMaster.ORB_DARK;
        name = orbString.NAME;
        baseEvokeAmount = 6;
        evokeAmount = baseEvokeAmount;
        basePassiveAmount = 6;
        passiveAmount = basePassiveAmount;
        updateDescription();
        channelAnimTimer = 0.5F;
    }

    public void updateDescription()
    {
        applyFocus();
        description = (new StringBuilder()).append(DESC[0]).append(passiveAmount).append(DESC[1]).append(evokeAmount).append(DESC[2]).toString();
    }

    public void onEvoke()
    {
        AbstractDungeon.actionManager.addToTop(new DarkOrbEvokeAction(new DamageInfo(AbstractDungeon.player, evokeAmount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
    }

    public void onEndOfTurn()
    {
        float speedTime = 0.6F / (float)AbstractDungeon.player.orbs.size();
        if(Settings.FAST_MODE)
            speedTime = 0.0F;
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor.DARK), speedTime));
        evokeAmount += passiveAmount;
        updateDescription();
    }

    public void triggerEvokeAnimation()
    {
        CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(cX, cY));
    }

    public void applyFocus()
    {
        AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if(power != null)
            passiveAmount = Math.max(0, basePassiveAmount + power.amount);
        else
            passiveAmount = basePassiveAmount;
    }

    public void updateAnimation()
    {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 120F;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if(vfxTimer < 0.0F)
        {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(cX, cY));
            vfxTimer = 0.25F;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(c);
        sb.draw(img, cX - 48F, (cY - 48F) + bobEffect.y, 48F, 48F, 96F, 96F, scale, scale, angle, 0, 0, 96, 96, false, false);
        shineColor.a = c.a / 3F;
        sb.setColor(shineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 48F, (cY - 48F) + bobEffect.y, 48F, 48F, 96F, 96F, scale * 1.2F, scale * 1.2F, angle / 1.2F, 0, 0, 96, 96, false, false);
        sb.draw(img, cX - 48F, (cY - 48F) + bobEffect.y, 48F, 48F, 96F, 96F, scale * 1.5F, scale * 1.5F, angle / 1.4F, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);
    }

    protected void renderText(SpriteBatch sb)
    {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(evokeAmount), cX + NUM_X_OFFSET, (cY + bobEffect.y / 2.0F + NUM_Y_OFFSET) - 4F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, c.a), fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(passiveAmount), cX + NUM_X_OFFSET, cY + bobEffect.y / 2.0F + NUM_Y_OFFSET + 20F * Settings.scale, c, fontScale);
    }

    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("ORB_DARK_CHANNEL", 0.1F);
    }

    public AbstractOrb makeCopy()
    {
        return new Dark();
    }

    public static final String ORB_ID = "Dark";
    private static final OrbStrings orbString;
    public static final String DESC[];
    private static final float ORB_BORDER_SCALE = 1.2F;
    private float vfxTimer;
    private static final float VFX_INTERVAL_TIME = 0.25F;

    static 
    {
        orbString = CardCrawlGame.languagePack.getOrbString("Dark");
        DESC = orbString.DESCRIPTION;
    }
}
