// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Sprite.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            AbstractDrawable, FontHelper

public class Sprite extends AbstractDrawable
{

    public Sprite(Texture img, float x, float y, int z, float scale, float rotation, Color color)
    {
        this.img = img;
        this.x = x;
        this.y = y;
        this.z = z;
        this.scale = scale;
        this.rotation = rotation;
        this.color = color;
        text = false;
    }

    public Sprite(Texture img, float x, float y, int z, float scale, Color color)
    {
        this(img, x, y, z, Settings.scale, 0.0F, color);
    }

    public Sprite(Texture img, float x, float y, int z)
    {
        this(img, x, y, z, Settings.scale, null);
    }

    public Sprite(Texture img, float x, float y, int z, Color color)
    {
        this(img, x, y, z, Settings.scale, color);
    }

    public Sprite(String label, float x, float y, int z, Color color)
    {
        this.label = label;
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
        text = true;
    }

    public void render(SpriteBatch sb)
    {
        if(!text)
        {
            int w = img.getWidth();
            int h = img.getHeight();
            if(color != null)
                sb.setColor(color);
            if(isVisible())
                sb.draw(img, x - (float)w / 2.0F, y - (float)h / 2.0F, (float)w / 2.0F, (float)h / 2.0F, w, h, scale, scale, rotation, 0, 0, w, h, false, false);
        } else
        {
            FontHelper.renderFontCentered(sb, FontHelper.panelEndTurnFont, label, x, y, color);
        }
    }

    private boolean isVisible()
    {
        return true;
    }

    private Texture img;
    private String label;
    private Color color;
    private float x;
    private float y;
    private float scale;
    private float rotation;
    private boolean text;
}
