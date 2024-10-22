// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CloudBubble.java

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
//            FloatyEffect

public class CloudBubble
{

    public CloudBubble(float x, float y, float targetScale)
    {
        scale = 0.01F;
        fadingIn = true;
        killed = false;
        this.x = x;
        this.y = y;
        this.targetScale = targetScale * Settings.scale;
        fadeInTime = MathUtils.random(0.7F, 2.5F);
        fadeInTimer = fadeInTime;
        f_effect = new FloatyEffect(this.targetScale * 3F, 1.0F);
        float darkness = MathUtils.random(0.8F, 0.9F);
        color = new Color(darkness, darkness - 0.04F, darkness - 0.05F, 1.0F);
        if(targetScale > 0.5F)
        {
            img = ImageMaster.LARGE_CLOUD;
        } else
        {
            img = ImageMaster.SMALL_CLOUD;
            this.targetScale *= 3F;
        }
        killSpeed = MathUtils.random(8F, 24F);
    }

    public void update()
    {
        f_effect.update();
        if(fadingIn)
        {
            fadeInTimer -= Gdx.graphics.getDeltaTime();
            if(fadeInTimer < 0.0F)
            {
                fadeInTimer = 0.0F;
                fadingIn = false;
            }
            scale = Interpolation.swingIn.apply(targetScale, 0.0F, fadeInTimer / fadeInTime);
        }
        if(killed)
            color.a = MathUtils.lerp(color.a, 0.0F, Gdx.graphics.getDeltaTime() * killSpeed);
    }

    public void kill()
    {
        killed = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, (x - 64F) + f_effect.x, (y - 64F) + f_effect.y, 64F, 64F, 128F, 128F, scale, scale, 0.0F, 0, 0, 128, 128, false, false);
    }

    private static final int RAW_W = 128;
    private float x;
    private float y;
    private float fadeInTime;
    private float fadeInTimer;
    private float scale;
    private float targetScale;
    private boolean fadingIn;
    private FloatyEffect f_effect;
    private Color color;
    private Texture img;
    private boolean killed;
    private float killSpeed;
}
