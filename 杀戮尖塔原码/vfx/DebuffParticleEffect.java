// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DebuffParticleEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class DebuffParticleEffect extends AbstractGameEffect
{

    public DebuffParticleEffect(float x, float y)
    {
        scale = 0.0F;
        this.x = x + MathUtils.random(-36F, 36F) * Settings.scale;
        this.y = y + MathUtils.random(-36F, 36F) * Settings.scale;
        duration = 4F;
        rotation = MathUtils.random(360F);
        spinClockwise = MathUtils.randomBoolean();
        if(IMG_NUM == 0)
        {
            renderBehind = true;
            img = ImageMaster.DEBUFF_VFX_1;
            IMG_NUM++;
        } else
        if(IMG_NUM == 1)
        {
            img = ImageMaster.DEBUFF_VFX_3;
            IMG_NUM++;
        } else
        {
            img = ImageMaster.DEBUFF_VFX_2;
            IMG_NUM = 0;
        }
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    }

    public void update()
    {
        if(spinClockwise)
            rotation += Gdx.graphics.getDeltaTime() * 120F;
        else
            rotation -= Gdx.graphics.getDeltaTime() * 120F;
        if(duration > 3F)
            color.a = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - (duration - 3F));
        else
        if(duration <= 1.0F)
        {
            color.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - duration);
            scale = Interpolation.fade.apply(Settings.scale, 0.0F, 1.0F - duration);
        }
        if(duration > 2.0F)
            scale = Interpolation.bounceOut.apply(0.0F, Settings.scale, 2.0F - (duration - 2.0F));
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale, scale, rotation, 0, 0, 32, 32, false, false);
    }

    public void dispose()
    {
    }

    private Texture img;
    private static int IMG_NUM = 0;
    private boolean spinClockwise;
    private float x;
    private float y;
    private float scale;

}
