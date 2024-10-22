// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlashIntentParticle.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FlashIntentParticle extends AbstractGameEffect
{

    public FlashIntentParticle(Texture img, AbstractMonster m)
    {
        scale = 0.01F;
        shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        duration = 1.0F;
        this.img = img;
        W = img.getWidth();
        x = m.intentHb.cX - (float)W / 2.0F;
        y = m.intentHb.cY - (float)W / 2.0F;
        renderBehind = true;
    }

    public void update()
    {
        scale = Interpolation.fade.apply(START_SCALE, 0.01F, duration);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch spritebatch, float f, float f1)
    {
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        shineColor.a = duration / 2.0F;
        sb.setColor(shineColor);
        sb.draw(img, x, y, (float)W / 2.0F, (float)W / 2.0F, W, W, scale, scale, 0.0F, 0, 0, W, W, false, false);
        sb.setBlendFunction(770, 771);
    }

    private static final float DURATION = 1F;
    private static final float START_SCALE;
    private float scale;
    private static int W;
    private Texture img;
    private float x;
    private float y;
    private Color shineColor;

    static 
    {
        START_SCALE = 5F * Settings.scale;
    }
}
