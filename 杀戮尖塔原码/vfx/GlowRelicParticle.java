// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GlowRelicParticle.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class GlowRelicParticle extends AbstractGameEffect
{

    public GlowRelicParticle(Texture img, float x, float y, float angle)
    {
        scale = 0.01F;
        duration = 3F;
        this.img = img;
        this.x = x;
        this.y = y;
        rotation = angle;
        color = Color.WHITE.cpy();
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        scale = Interpolation.fade.apply(Settings.scale, 2.0F * Settings.scale, 1.0F - duration / 3F);
        color.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - duration / 3F) / 2.0F;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x - 64F, y - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static final float DURATION = 3F;
    private float scale;
    private static final int IMG_W = 128;
    private Texture img;
    private float x;
    private float y;
}
