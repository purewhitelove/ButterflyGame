// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Label.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            AbstractDrawable, FontHelper

public class Label extends AbstractDrawable
{

    public Label(BitmapFont font, String msg, float x, float y, int z, float scale, Color color)
    {
        this.font = font;
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.z = z;
        this.scale = scale;
        this.color = color;
    }

    public void render(SpriteBatch sb)
    {
        font.getData().setScale(scale);
        FontHelper.renderFontCentered(sb, font, msg, x, y, color);
    }

    private BitmapFont font;
    private String msg;
    private Color color;
    private float x;
    private float y;
    private float scale;
}
