// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CreditLine.java

package com.megacrit.cardcrawl.credits;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

public class CreditLine
{

    public CreditLine(String text, float offset, boolean isHeader)
    {
        x = (float)Settings.WIDTH / 2.0F;
        this.text = text;
        y = offset * Settings.scale;
        if(isHeader)
        {
            font = FontHelper.tipBodyFont;
            color = Settings.GOLD_COLOR.cpy();
        } else
        {
            font = FontHelper.panelNameFont;
            color = Settings.CREAM_COLOR.cpy();
        }
    }

    public CreditLine(String text, float offset, boolean isHeader, boolean left)
    {
        x = (float)Settings.WIDTH / 2.0F;
        this.text = text;
        y = offset * Settings.scale;
        if(isHeader)
        {
            font = FontHelper.tipBodyFont;
            color = Settings.GOLD_COLOR.cpy();
        } else
        {
            font = FontHelper.panelNameFont;
            color = Settings.CREAM_COLOR.cpy();
        }
        if(left)
            x = (float)Settings.WIDTH * 0.4F;
        else
            x = (float)Settings.WIDTH * 0.6F;
    }

    public void render(SpriteBatch sb, float scrollY)
    {
        FontHelper.renderFontCentered(sb, font, text, x, y + scrollY, color);
    }

    public String text;
    private float x;
    private float y;
    private BitmapFont font;
    private Color color;
}
