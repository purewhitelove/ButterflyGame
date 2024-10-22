// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EndTurnGlowEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class EndTurnGlowEffect extends AbstractGameEffect
{

    public EndTurnGlowEffect()
    {
        scale = 0.0F;
        duration = 2.0F;
        color = Color.WHITE.cpy();
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        scale = Interpolation.fade.apply(Settings.scale, 2.0F * Settings.scale, 1.0F - duration / 2.0F);
        color.a = Interpolation.fade.apply(0.4F, 0.0F, 1.0F - duration / 2.0F) / 2.0F;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(ImageMaster.END_TURN_BUTTON_GLOW, x - 128F, y - 128F, 128F, 128F, 256F, 256F, scale, scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    private static final float DURATION = 2F;
    private float scale;
    private static final int IMG_W = 256;
}
