// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PowerBuffEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            FlyingSpikeEffect

public class PowerBuffEffect extends AbstractGameEffect
{

    public PowerBuffEffect(float x, float y, String msg)
    {
        duration = 2.0F;
        startingDuration = 2.0F;
        this.msg = msg;
        this.x = x;
        this.y = y;
        targetColor = Settings.GREEN_TEXT_COLOR;
        color = Color.WHITE.cpy();
        offsetY = STARTING_OFFSET_Y;
    }

    public void update()
    {
        if(duration == startingDuration && !Settings.DISABLE_EFFECTS)
        {
            for(int i = 0; i < 10; i++)
                AbstractDungeon.effectsQueue.add(new FlyingSpikeEffect(x - MathUtils.random(-120F, 120F) * Settings.scale, y + MathUtils.random(90F, 110F) * Settings.scale, -90F, 0.0F, MathUtils.random(-200F, -50F) * Settings.scale, Settings.GREEN_TEXT_COLOR));

            for(int i = 0; i < 10; i++)
                AbstractDungeon.effectsQueue.add(new FlyingSpikeEffect(x - MathUtils.random(-120F, 120F) * Settings.scale, y + MathUtils.random(90F, 110F) * Settings.scale, 90F, 0.0F, MathUtils.random(200F, 50F) * Settings.scale, Settings.GREEN_TEXT_COLOR));

        }
        offsetY = Interpolation.exp10In.apply(TARGET_OFFSET_Y, STARTING_OFFSET_Y, duration / 2.0F);
        color.r = Interpolation.pow2In.apply(targetColor.r, 1.0F, duration / startingDuration);
        color.g = Interpolation.pow2In.apply(targetColor.g, 1.0F, duration / startingDuration);
        color.b = Interpolation.pow2In.apply(targetColor.b, 1.0F, duration / startingDuration);
        color.a = Interpolation.exp10Out.apply(0.0F, 1.0F, duration / 2.0F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            duration = 0.0F;
        }
    }

    public void render(SpriteBatch sb)
    {
        FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, msg, x, y + offsetY, color, 1.25F);
    }

    public void dispose()
    {
    }

    private static final float TEXT_DURATION = 2F;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private float x;
    private float y;
    private float offsetY;
    private String msg;
    private Color targetColor;

    static 
    {
        STARTING_OFFSET_Y = 60F * Settings.scale;
        TARGET_OFFSET_Y = 100F * Settings.scale;
    }
}
