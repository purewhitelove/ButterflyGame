// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CalmParticleEffect.java

package com.megacrit.cardcrawl.vfx.stance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CalmParticleEffect extends AbstractGameEffect
{

    public CalmParticleEffect()
    {
        duration = MathUtils.random(0.6F, 1.0F);
        scale = MathUtils.random(0.6F, 1.2F) * Settings.scale;
        dur_div2 = duration / 2.0F;
        color = new Color(MathUtils.random(0.2F, 0.3F), MathUtils.random(0.65F, 0.8F), 1.0F, 0.0F);
        vX = MathUtils.random(-300F, -50F) * Settings.scale;
        vY = MathUtils.random(-200F, -100F) * Settings.scale;
        x = (AbstractDungeon.player.hb.cX + MathUtils.random(100F, 160F) * Settings.scale) - 32F;
        y = (AbstractDungeon.player.hb.cY + MathUtils.random(-50F, 220F) * Settings.scale) - 32F;
        renderBehind = MathUtils.randomBoolean(0.2F + (scale - 0.5F));
        dvx = 400F * Settings.scale * scale;
        dvy = 100F * Settings.scale;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vY += Gdx.graphics.getDeltaTime() * dvy;
        vX -= Gdx.graphics.getDeltaTime() * dvx;
        rotation = -(57.29578F * MathUtils.atan2(vX, vY)) - 0.0F;
        if(duration > dur_div2)
            color.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - dur_div2) / dur_div2);
        else
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration / dur_div2);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.FROST_ACTIVATE_VFX_1, x, y, 32F, 32F, 25F, 128F, scale, scale + (dur_div2 * 0.4F - duration) * Settings.scale, rotation, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private float dur_div2;
    private float dvy;
    private float dvx;
}
