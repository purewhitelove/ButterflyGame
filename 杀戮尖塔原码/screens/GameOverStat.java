// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameOverStat.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class GameOverStat
{

    public GameOverStat()
    {
        hidden = true;
        color = Settings.CREAM_COLOR.cpy();
        offsetX = -50F * Settings.scale;
        hb = null;
        label = null;
        description = null;
        value = null;
        hb = new Hitbox(250F * Settings.scale, 32F * Settings.scale);
        color.a = 0.0F;
    }

    public GameOverStat(String label, String description, String value)
    {
        hidden = true;
        color = Settings.CREAM_COLOR.cpy();
        offsetX = -50F * Settings.scale;
        hb = null;
        this.label = label;
        if(description != null && description.equals(""))
            this.description = null;
        else
            this.description = description;
        hb = new Hitbox(250F * Settings.scale, 32F * Settings.scale);
        this.value = value;
        color.a = 0.0F;
    }

    public void update()
    {
        if(!hidden)
        {
            color.a = MathHelper.slowColorLerpSnap(color.a, 1.0F);
            offsetX = MathHelper.uiLerpSnap(offsetX, 0.0F);
            if(hb != null)
            {
                hb.update();
                if(hb.hovered && description != null)
                    TipHelper.renderGenericTip((float)InputHelper.mX + 80F * Settings.scale, InputHelper.mY, label, description);
            }
        }
    }

    public void renderLine(SpriteBatch sb, boolean isWide, float y)
    {
        if(isWide)
        {
            sb.setColor(color);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, (float)Settings.WIDTH / 2.0F - 525F * Settings.scale, y - 10F * Settings.scale, 1050F * Settings.scale, 4F * Settings.scale);
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, color.a / 4F));
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, (float)Settings.WIDTH / 2.0F - 525F * Settings.scale, y - 10F * Settings.scale, 1050F * Settings.scale, 1.0F * Settings.scale);
        } else
        {
            sb.setColor(color);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, (float)Settings.WIDTH / 2.0F - 222F * Settings.scale, y - 10F * Settings.scale, 434F * Settings.scale, 4F * Settings.scale);
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, color.a / 4F));
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, (float)Settings.WIDTH / 2.0F - 222F * Settings.scale, y - 10F * Settings.scale, 434F * Settings.scale, 1.0F * Settings.scale);
        }
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        if(label == null || value == null)
            return;
        Color prevColor = null;
        if(hb != null && hb.hovered)
        {
            prevColor = color;
            color = new Color(0.0F, 0.9F, 0.0F, color.a);
        }
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, label, x + offsetX, y, color);
        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, value, x + VALUE_X + offsetX, y, color);
        if(hb != null)
        {
            hb.move(x + 120F * Settings.scale, y - 8F * Settings.scale);
            hb.render(sb);
            if(hb.hovered)
                color = prevColor;
        }
    }

    public final String label;
    public final String description;
    public final String value;
    private static final float VALUE_X;
    public boolean hidden;
    private Color color;
    private float offsetX;
    public Hitbox hb;

    static 
    {
        VALUE_X = 430F * Settings.scale;
    }
}
