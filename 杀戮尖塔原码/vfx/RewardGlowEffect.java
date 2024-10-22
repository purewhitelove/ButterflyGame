// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RewardGlowEffect.java

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

public class RewardGlowEffect extends AbstractGameEffect
{

    public RewardGlowEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
        color = Color.WHITE.cpy();
        duration = 1.1F;
        scale = Settings.scale;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        scale += (Settings.scale * Gdx.graphics.getDeltaTime()) / 20F;
        color.a = Interpolation.fade.apply(duration / 1.1F) / 12F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.REWARD_SCREEN_ITEM, x - 232F, y - 49F, 232F, 49F, 464F, 98F, Settings.xScale, scale + Settings.scale * 0.05F, 0.0F, 0, 0, 464, 98, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch sb, Color tint)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, x - 32F, y - 32F, 32F, 32F, 64F, 64F, (scale * Settings.scale) / 2.0F, (scale * Settings.scale) / 2.0F, angle, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
    }

    private static final int W = 64;
    private static final float DURATION = 1.1F;
    private float scale;
    private float x;
    private float y;
    private float angle;
}
