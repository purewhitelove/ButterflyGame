// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextCenteredEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class TextCenteredEffect extends AbstractGameEffect
{

    public TextCenteredEffect(String msg)
    {
        duration = 1.8F;
        startingDuration = 1.8F;
        this.msg = msg;
        color = Color.WHITE.cpy();
        offsetY = STARTING_OFFSET_Y;
    }

    public void update()
    {
        super.update();
        offsetY = MathUtils.lerp(offsetY, TARGET_OFFSET_Y, Gdx.graphics.getDeltaTime() * 5F);
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, msg, DRAW_X, DRAW_Y + offsetY, color);
    }

    public void dispose()
    {
    }

    private static final float TEXT_DURATION = 1.8F;
    private static final float DRAW_X;
    private static final float DRAW_Y;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private static final float LERP_RATE = 5F;
    private float offsetY;
    private String msg;

    static 
    {
        DRAW_X = (float)Settings.WIDTH / 2.0F;
        DRAW_Y = (float)Settings.HEIGHT * 0.6F;
        STARTING_OFFSET_Y = 120F * Settings.scale;
        TARGET_OFFSET_Y = 160F * Settings.scale;
    }
}
