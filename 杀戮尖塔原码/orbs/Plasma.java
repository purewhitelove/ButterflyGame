// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Plasma.java

package com.megacrit.cardcrawl.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.orbs:
//            AbstractOrb

public class Plasma extends AbstractOrb
{

    public Plasma()
    {
        vfxTimer = 1.0F;
        vfxIntervalMin = 0.1F;
        vfxIntervalMax = 0.4F;
        ID = "Plasma";
        img = ImageMaster.ORB_PLASMA;
        name = orbString.NAME;
        baseEvokeAmount = 2;
        evokeAmount = baseEvokeAmount;
        basePassiveAmount = 1;
        passiveAmount = basePassiveAmount;
        updateDescription();
        angle = MathUtils.random(360F);
        channelAnimTimer = 0.5F;
    }

    public void updateDescription()
    {
        applyFocus();
        description = (new StringBuilder()).append(DESC[0]).append(evokeAmount).append(DESC[1]).toString();
    }

    public void onEvoke()
    {
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(evokeAmount));
    }

    public void onStartOfTurn()
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor.PLASMA), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(passiveAmount));
    }

    public void triggerEvokeAnimation()
    {
        CardCrawlGame.sound.play("ORB_PLASMA_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(cX, cY));
    }

    public void updateAnimation()
    {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45F;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if(vfxTimer < 0.0F)
        {
            AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(cX, cY));
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    public void render(SpriteBatch sb)
    {
        shineColor.a = c.a / 2.0F;
        sb.setColor(shineColor);
        sb.draw(img, cX - 48F, (cY - 48F) + bobEffect.y, 48F, 48F, 96F, 96F, scale + MathUtils.sin(angle / 12.56637F) * 0.04F * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 48F, (cY - 48F) + bobEffect.y, 48F, 48F, 96F, 96F, scale, scale + MathUtils.sin(angle / 12.56637F) * 0.04F * Settings.scale, -angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);
    }

    protected void renderText(SpriteBatch sb)
    {
        if(showEvokeValue)
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(evokeAmount), cX + NUM_X_OFFSET, (cY + bobEffect.y / 2.0F + NUM_Y_OFFSET) - 4F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, c.a), fontScale);
    }

    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("ORB_PLASMA_CHANNEL", 0.1F);
    }

    public AbstractOrb makeCopy()
    {
        return new Plasma();
    }

    public static final String ORB_ID = "Plasma";
    private static final OrbStrings orbString;
    public static final String DESC[];
    private float vfxTimer;
    private float vfxIntervalMin;
    private float vfxIntervalMax;
    private static final float ORB_WAVY_DIST = 0.04F;
    private static final float PI_4 = 12.56637F;

    static 
    {
        orbString = CardCrawlGame.languagePack.getOrbString("Plasma");
        DESC = orbString.DESCRIPTION;
    }
}
