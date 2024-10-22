// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SumDamageEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class SumDamageEffect extends AbstractGameEffect
{

    public SumDamageEffect(AbstractCreature target, float x, float y, int amt)
    {
        scale = 3F * Settings.scale;
        duration = 2.5F;
        startingDuration = 2.5F;
        this.x = x;
        this.y = y + OFFSET_Y;
        vY = 90F * Settings.scale;
        this.target = target;
        this.amt = amt;
        color = Settings.GOLD_COLOR.cpy();
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        if(vY > 0.0F)
            vY -= 50F * Settings.scale * Gdx.graphics.getDeltaTime();
        scale = (Settings.scale * duration) / 2.5F + 1.3F;
        if(duration < 1.0F)
            color.a = duration;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        FontHelper.damageNumberFont.getData().setScale(scale);
        FontHelper.renderFontCentered(sb, FontHelper.damageNumberFont, Integer.toString(amt), x, y, color);
    }

    public void dispose()
    {
    }

    public void refresh(int amt)
    {
        this.amt += amt;
        duration = 2.5F;
        color.a = 1.0F;
    }

    private static final float EFFECT_DUR = 2.5F;
    private float x;
    private float y;
    private float vY;
    private static final float OFFSET_Y;
    private int amt;
    private float scale;
    public AbstractCreature target;

    static 
    {
        OFFSET_Y = 200F * Settings.scale;
    }
}
