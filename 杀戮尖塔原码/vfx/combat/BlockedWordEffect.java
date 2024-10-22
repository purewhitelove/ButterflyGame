// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlockedWordEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BlockedWordEffect extends AbstractGameEffect
{

    public BlockedWordEffect(AbstractCreature target, float x, float y, String msg)
    {
        scale = 1.0F;
        duration = 2.3F;
        startingDuration = 2.3F;
        this.x = x;
        this.y = y + OFFSET_Y;
        this.target = target;
        this.msg = msg;
        color = Color.WHITE.cpy();
    }

    public void update()
    {
        y += Gdx.graphics.getDeltaTime() * duration * 100F * Settings.scale;
        super.update();
        scale = (Settings.scale * duration) / 2.3F + 1.0F;
    }

    public void render(SpriteBatch sb)
    {
        FontHelper.damageNumberFont.getData().setScale(scale);
        FontHelper.renderFontCentered(sb, FontHelper.damageNumberFont, msg, x, y, color);
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 2.3F;
    private float x;
    private float y;
    private static final float OFFSET_Y;
    private String msg;
    private float scale;
    public AbstractCreature target;

    static 
    {
        OFFSET_Y = 150F * Settings.scale;
    }
}
