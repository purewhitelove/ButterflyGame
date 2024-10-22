// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PowerIconShowEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PowerIconShowEffect extends AbstractGameEffect
{

    public PowerIconShowEffect(AbstractPower power)
    {
        shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        if(!power.owner.isDeadOrEscaped())
        {
            x = power.owner.hb.cX;
            y = power.owner.hb.cY + power.owner.hb.height / 2.0F;
        }
        img = power.img;
        duration = 2.2F;
        startingDuration = 2.2F;
        offsetY = STARTING_OFFSET_Y;
        color = Color.WHITE.cpy();
        renderBehind = true;
    }

    public void update()
    {
        super.update();
        offsetY = MathUtils.lerp(offsetY, TARGET_OFFSET_Y, Gdx.graphics.getDeltaTime() * 5F);
        y += Gdx.graphics.getDeltaTime() * 12F * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        if(img != null)
        {
            shineColor.a = color.a / 2.0F;
            sb.setColor(shineColor);
            sb.setBlendFunction(770, 1);
            sb.draw(img, x - 16F, (y - 16F) + offsetY, 16F, 16F, 32F, 32F, Settings.scale * 2.5F, Settings.scale * 2.5F, 0.0F, 0, 0, 32, 32, false, false);
            sb.setBlendFunction(770, 771);
            sb.setColor(color);
            sb.draw(img, x - 16F, (y - 16F) + offsetY, 16F, 16F, 32F, 32F, Settings.scale * 2.0F, Settings.scale * 2.0F, 0.0F, 0, 0, 32, 32, false, false);
        }
    }

    public void dispose()
    {
    }

    private static final float DUR = 2.2F;
    private float x;
    private float y;
    private float offsetY;
    private Texture img;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private static final float LERP_RATE = 5F;
    private static final int W = 32;
    private Color shineColor;

    static 
    {
        STARTING_OFFSET_Y = 130F * Settings.scale;
        TARGET_OFFSET_Y = 170F * Settings.scale;
    }
}
