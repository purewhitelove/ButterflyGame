// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Button.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class Button
{

    public Button(float x, float y, Texture img)
    {
        activeColor = Color.WHITE;
        inactiveColor = new Color(0.6F, 0.6F, 0.6F, 1.0F);
        pressed = false;
        this.x = x;
        this.y = y;
        this.img = img;
        hb = new Hitbox(x, y, img.getWidth(), img.getHeight());
        height = img.getHeight();
        width = img.getWidth();
    }

    public void update()
    {
        hb.update(x, y);
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            pressed = true;
            InputHelper.justClickedLeft = false;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(hb.hovered)
            sb.setColor(activeColor);
        else
            sb.setColor(inactiveColor);
        sb.draw(img, x, y);
        sb.setColor(Color.WHITE);
        hb.render(sb);
    }

    public float x;
    public float y;
    private Texture img;
    protected Hitbox hb;
    protected Color activeColor;
    protected Color inactiveColor;
    public boolean pressed;
    public int height;
    public int width;
}
