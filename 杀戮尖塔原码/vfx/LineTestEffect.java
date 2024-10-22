// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LineTestEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, MapDot

public class LineTestEffect extends AbstractGameEffect
{

    public LineTestEffect()
    {
        dots = new ArrayList();
        x = InputHelper.mX;
        y = InputHelper.mY;
        x2 = (float)Settings.WIDTH / 2.0F;
        y2 = (float)Settings.HEIGHT / 2.0F;
        Vector2 vec2 = (new Vector2(x2, y2)).sub(new Vector2(x, y));
        float length = vec2.len();
        float START = SPACING * MathUtils.random();
        for(float i = START; i < length; i += SPACING)
        {
            vec2.clamp(length - i, length - i);
            dots.add(new MapDot(x + vec2.x, y + vec2.y, (new Vector2(x - x2, y - y2)).nor().angle() + 90F, true));
        }

        duration = 3F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        MapDot d;
        for(Iterator iterator = dots.iterator(); iterator.hasNext(); d.render(sb))
            d = (MapDot)iterator.next();

    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float x2;
    private float y2;
    private static final float SPACING;
    private ArrayList dots;

    static 
    {
        SPACING = 30F * Settings.scale;
    }
}
