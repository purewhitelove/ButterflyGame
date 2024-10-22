// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BiteEffect.java

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
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BiteEffect extends AbstractGameEffect
{

    public BiteEffect(float x, float y, Color c)
    {
        playedSfx = false;
        if(top == null)
        {
            top = ImageMaster.vfxAtlas.findRegion("combat/biteTop");
            bot = ImageMaster.vfxAtlas.findRegion("combat/biteBot");
        }
        this.x = x - (float)top.packedWidth / 2.0F;
        sY = (y - (float)top.packedHeight / 2.0F) + 150F * Settings.scale;
        dY = y - 0.0F * Settings.scale;
        this.y = sY;
        sY2 = y - (float)(top.packedHeight / 2) - 100F * Settings.scale;
        dY2 = y - 90F * Settings.scale;
        y2 = sY2;
        duration = 1.0F;
        startingDuration = 1.0F;
        color = c;
        scale = 1.0F * Settings.scale;
    }

    public BiteEffect(float x, float y)
    {
        this(x, y, new Color(0.7F, 0.9F, 1.0F, 0.0F));
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < startingDuration - 0.3F && !playedSfx)
        {
            playedSfx = true;
            CardCrawlGame.sound.play("EVENT_VAMP_BITE", 0.05F);
        }
        if(duration > startingDuration / 2.0F)
        {
            color.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - 0.5F) * 2.0F);
            y = Interpolation.bounceIn.apply(dY, sY, (duration - 0.5F) * 2.0F);
            y2 = Interpolation.bounceIn.apply(dY2, sY2, (duration - 0.5F) * 2.0F);
        } else
        {
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration * 2.0F);
            y = Interpolation.fade.apply(sY, dY, duration * 2.0F);
            y2 = Interpolation.fade.apply(sY2, dY2, duration * 2.0F);
        }
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(top, x, y, (float)top.packedWidth / 2.0F, (float)top.packedHeight / 2.0F, top.packedWidth, top.packedHeight, scale + MathUtils.random(-0.05F, 0.05F), scale + MathUtils.random(-0.05F, 0.05F), 0.0F);
        sb.draw(bot, x, y2, (float)top.packedWidth / 2.0F, (float)top.packedHeight / 2.0F, top.packedWidth, top.packedHeight, scale + MathUtils.random(-0.05F, 0.05F), scale + MathUtils.random(-0.05F, 0.05F), 0.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion top;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion bot;
    private float x;
    private float y;
    private float sY;
    private float dY;
    private float y2;
    private float sY2;
    private float dY2;
    private static final float DUR = 1F;
    private boolean playedSfx;
}
