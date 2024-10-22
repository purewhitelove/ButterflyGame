// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BorderLongFlashEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class BorderLongFlashEffect extends AbstractGameEffect
{

    public BorderLongFlashEffect(Color color)
    {
        this(color, true);
    }

    public BorderLongFlashEffect(Color color, boolean additive)
    {
        this.additive = true;
        duration = 2.0F;
        startAlpha = color.a;
        this.color = color.cpy();
        this.color.a = 0.0F;
        this.additive = additive;
    }

    public void update()
    {
        if(2.0F - duration < 0.2F)
            color.a = Interpolation.fade.apply(0.0F, startAlpha, (2.0F - duration) * 10F);
        else
            color.a = Interpolation.pow2Out.apply(0.0F, startAlpha, duration / 2.0F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        if(additive)
            sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(ImageMaster.BORDER_GLOW_2, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        if(additive)
            sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static final float DUR = 2F;
    private float startAlpha;
    private boolean additive;
}
