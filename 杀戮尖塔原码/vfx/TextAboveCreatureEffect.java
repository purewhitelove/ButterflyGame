// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextAboveCreatureEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class TextAboveCreatureEffect extends AbstractGameEffect
{

    public TextAboveCreatureEffect(float x, float y, String msg, Color targetColor)
    {
        duration = 2.2F;
        startingDuration = 2.2F;
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.targetColor = targetColor;
        color = Color.WHITE.cpy();
        offsetY = STARTING_OFFSET_Y;
    }

    public void update()
    {
        super.update();
        color.r = Interpolation.exp5In.apply(targetColor.r, 1.0F, duration / startingDuration);
        color.g = Interpolation.exp5In.apply(targetColor.g, 1.0F, duration / startingDuration);
        color.b = Interpolation.exp5In.apply(targetColor.b, 1.0F, duration / startingDuration);
        offsetY = MathUtils.lerp(offsetY, TARGET_OFFSET_Y, Gdx.graphics.getDeltaTime() * 5F);
        y += Gdx.graphics.getDeltaTime() * 12F * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
            FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, msg, x, y + offsetY, color, 1.2F);
    }

    public void dispose()
    {
    }

    private static final float TEXT_DURATION = 2.2F;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private static final float LERP_RATE = 5F;
    private float x;
    private float y;
    private float offsetY;
    private String msg;
    private Color targetColor;

    static 
    {
        STARTING_OFFSET_Y = 80F * Settings.scale;
        TARGET_OFFSET_Y = 120F * Settings.scale;
    }
}
