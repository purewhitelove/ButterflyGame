// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EndTurnLongPressBarFlashEffect.java

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

public class EndTurnLongPressBarFlashEffect extends AbstractGameEffect
{

    public EndTurnLongPressBarFlashEffect()
    {
        bgColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        duration = 1.0F;
        color = new Color(1.0F, 1.0F, 0.6F, 0.0F);
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        color.a = Interpolation.exp5Out.apply(0.0F, 1.0F, duration);
    }

    public void render(SpriteBatch sb)
    {
        bgColor.a = color.a * 0.25F;
        sb.setColor(bgColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 1533F * Settings.xScale, 256F * Settings.scale, 214F * Settings.scale, 20F * Settings.scale);
        sb.setBlendFunction(770, 1);
        color.r = 1.0F;
        color.g = 1.0F;
        color.b = 0.6F;
        sb.setColor(color);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 1540F * Settings.xScale, 263F * Settings.scale, 200F * Settings.scale, 6F * Settings.scale);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private Color bgColor;
}
