// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LaserBeamEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import java.util.ArrayList;

public class LaserBeamEffect extends AbstractGameEffect
{

    public LaserBeamEffect(float x, float y)
    {
        playedSfx = false;
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThick");
        this.x = x;
        this.y = y;
        color = Color.CYAN.cpy();
        duration = 2.0F;
        startingDuration = 2.0F;
    }

    public void update()
    {
        if(!playedSfx)
        {
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.SKY));
            playedSfx = true;
            CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM");
            CardCrawlGame.screenShake.rumble(2.0F);
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.pow2In.apply(1.0F, 0.0F, duration - 1.0F);
        else
            color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, duration);
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(0.5F, 0.7F, 1.0F, color.a));
        sb.draw(img, x, y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 2.0F + MathUtils.random(-0.05F, 0.05F), scale * 1.5F + MathUtils.random(-0.1F, 0.1F), MathUtils.random(186F, 189F));
        sb.draw(img, x, y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 2.0F + MathUtils.random(-0.05F, 0.05F), scale * 1.5F + MathUtils.random(-0.1F, 0.1F), MathUtils.random(186F, 189F));
        sb.setColor(color);
        sb.draw(img, x, y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 2.0F, scale / 2.0F, MathUtils.random(187F, 188F));
        sb.draw(img, x, y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 2.0F, scale / 2.0F, MathUtils.random(187F, 188F));
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private static final float DUR = 2F;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean playedSfx;
}
