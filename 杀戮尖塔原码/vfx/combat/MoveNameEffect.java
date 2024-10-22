// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MoveNameEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class MoveNameEffect extends AbstractGameEffect
{

    public MoveNameEffect(float x, float y, String msg)
    {
        color2 = Color.BLACK.cpy();
        img = ImageMaster.MOVE_NAME_BG;
        duration = 2.5F;
        startingDuration = 2.5F;
        if(msg == null)
            isDone = true;
        else
            this.msg = msg;
        this.x = x;
        this.y = y;
        color = Settings.CREAM_COLOR.cpy();
    }

    public void update()
    {
        offsetY = Interpolation.exp10In.apply(TARGET_OFFSET_Y, STARTING_OFFSET_Y, duration / 2.5F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration > 2.0F)
        {
            color.a = Interpolation.fade.apply(1.0F - (duration - 2.0F) / 0.5F);
            color2.a = color.a;
        } else
        if(duration < 1.0F)
        {
            color.a = Interpolation.fade.apply(duration);
            color2.a = color.a;
        } else
        {
            color.a = 1.0F;
            color2.a = 1.0F;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color2);
        sb.draw(img, x - (float)img.packedWidth / 2.0F, (y - (float)img.packedHeight / 2.0F) + offsetY, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, msg, x, y + offsetY, color, 1.0F);
    }

    public void dispose()
    {
    }

    private static final float TEXT_DURATION = 2.5F;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private float x;
    private float y;
    private float offsetY;
    private String msg;
    private Color color2;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;

    static 
    {
        STARTING_OFFSET_Y = 100F * Settings.scale;
        TARGET_OFFSET_Y = 120F * Settings.scale;
    }
}
