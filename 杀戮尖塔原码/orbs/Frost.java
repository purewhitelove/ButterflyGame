// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Frost.java

package com.megacrit.cardcrawl.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.orbs:
//            AbstractOrb

public class Frost extends AbstractOrb
{

    public Frost()
    {
        vfxTimer = 1.0F;
        vfxIntervalMin = 0.15F;
        vfxIntervalMax = 0.8F;
        hFlip1 = MathUtils.randomBoolean();
        hFlip2 = MathUtils.randomBoolean();
        ID = "Frost";
        name = orbString.NAME;
        baseEvokeAmount = 5;
        evokeAmount = baseEvokeAmount;
        basePassiveAmount = 2;
        passiveAmount = basePassiveAmount;
        updateDescription();
        channelAnimTimer = 0.5F;
    }

    public void updateDescription()
    {
        applyFocus();
        description = (new StringBuilder()).append(orbString.DESCRIPTION[0]).append(passiveAmount).append(orbString.DESCRIPTION[1]).append(evokeAmount).append(orbString.DESCRIPTION[2]).toString();
    }

    public void onEvoke()
    {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, evokeAmount));
    }

    public void updateAnimation()
    {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 180F;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if(vfxTimer < 0.0F)
        {
            AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(cX, cY));
            if(MathUtils.randomBoolean())
                AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(cX, cY));
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    public void onEndOfTurn()
    {
        float speedTime = 0.6F / (float)AbstractDungeon.player.orbs.size();
        if(Settings.FAST_MODE)
            speedTime = 0.0F;
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor.FROST), speedTime));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, passiveAmount, true));
    }

    public void triggerEvokeAnimation()
    {
        CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateEffect(cX, cY));
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(c);
        sb.draw(ImageMaster.FROST_ORB_RIGHT, (cX - 48F) + bobEffect.y / 4F, (cY - 48F) + bobEffect.y / 4F, 48F, 48F, 96F, 96F, scale, scale, 0.0F, 0, 0, 96, 96, hFlip1, false);
        sb.draw(ImageMaster.FROST_ORB_LEFT, (cX - 48F) + bobEffect.y / 4F, cY - 48F - bobEffect.y / 4F, 48F, 48F, 96F, 96F, scale, scale, 0.0F, 0, 0, 96, 96, hFlip1, false);
        sb.draw(ImageMaster.FROST_ORB_MIDDLE, cX - 48F - bobEffect.y / 4F, (cY - 48F) + bobEffect.y / 2.0F, 48F, 48F, 96F, 96F, scale, scale, 0.0F, 0, 0, 96, 96, hFlip2, false);
        renderText(sb);
        hb.render(sb);
    }

    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1F);
    }

    public AbstractOrb makeCopy()
    {
        return new Frost();
    }

    public static final String ORB_ID = "Frost";
    private static final OrbStrings orbString;
    private boolean hFlip1;
    private boolean hFlip2;
    private float vfxTimer;
    private float vfxIntervalMin;
    private float vfxIntervalMax;

    static 
    {
        orbString = CardCrawlGame.languagePack.getOrbString("Frost");
    }
}
