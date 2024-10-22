// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LightningOrbPassiveEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

public class LightningOrbPassiveEffect extends AbstractGameEffect
{

    public LightningOrbPassiveEffect(float x, float y)
    {
        img = null;
        index = 0;
        renderBehind = MathUtils.randomBoolean();
        this.x = x;
        this.y = y;
        color = Settings.LIGHT_YELLOW_COLOR.cpy();
        img = (Texture)ImageMaster.LIGHTNING_PASSIVE_VFX.get(index);
        scale = MathUtils.random(0.6F, 1.0F) * Settings.scale;
        rotation = MathUtils.random(360F);
        if(rotation < 120F)
            renderBehind = true;
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        intervalDuration = MathUtils.random(0.03F, 0.06F);
        duration = intervalDuration;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            index++;
            if(index > ImageMaster.LIGHTNING_PASSIVE_VFX.size() - 1)
            {
                isDone = true;
                return;
            }
            img = (Texture)ImageMaster.LIGHTNING_PASSIVE_VFX.get(index);
            duration = intervalDuration;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x - 61F, y - 61F, 61F, 61F, 122F, 122F, scale, scale, rotation, 0, 0, 122, 122, flipX, flipY);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private Texture img;
    private int index;
    private float x;
    private float y;
    private boolean flipX;
    private boolean flipY;
    private float intervalDuration;
}
