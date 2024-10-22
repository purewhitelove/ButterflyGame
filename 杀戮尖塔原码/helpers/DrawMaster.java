// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DrawMaster.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            AbstractDrawable, Sprite, Label

public class DrawMaster
{

    public DrawMaster()
    {
    }

    public static void draw(SpriteBatch sb)
    {
        Collections.sort(drawList);
        sb.setColor(Color.WHITE);
        AbstractDrawable o;
        for(Iterator iterator = drawList.iterator(); iterator.hasNext(); o.render(sb))
            o = (AbstractDrawable)iterator.next();

        drawList.clear();
    }

    public static void queue(Texture img, float x, float y, int z, Color color)
    {
        drawList.add(new Sprite(img, x, y, z, color));
    }

    public static void queue(Texture img, float x, float y, int z, float scale, Color color)
    {
        drawList.add(new Sprite(img, x, y, z, scale, color));
    }

    public static void queue(Texture img, float x, float y, int z, float scale, float rotation, Color color)
    {
        drawList.add(new Sprite(img, x, y, z, scale, rotation, color));
    }

    public static void queue(BitmapFont font, String label, float x, float y, int z, float scale, Color color)
    {
        drawList.add(new Label(font, label, x, y, z, scale, color));
    }

    public static List drawList = new ArrayList();

}
