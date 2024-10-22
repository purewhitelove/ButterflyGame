// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LegendItem.java

package com.megacrit.cardcrawl.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;

// Referenced classes of package com.megacrit.cardcrawl.map:
//            Legend

public class LegendItem
{

    public LegendItem(String label, Texture img, String tipHeader, String tipBody, int index)
    {
        hb = new Hitbox(230F * Settings.xScale, SPACE_Y - 2.0F);
        this.label = label;
        this.img = img;
        header = tipHeader;
        body = tipBody;
        this.index = index;
    }

    public void update()
    {
        hb.update();
        if(hb.hovered)
            TipHelper.renderGenericTip(1500F * Settings.xScale, 270F * Settings.scale, header, body);
    }

    public void render(SpriteBatch sb, Color c)
    {
        sb.setColor(c);
        if(!Settings.isMobile)
        {
            if(hb.hovered)
                sb.draw(img, ICON_X - 64F, ((Legend.Y - SPACE_Y * (float)index) + OFFSET_Y) - 64F, 64F, 64F, 128F, 128F, Settings.scale / 1.2F, Settings.scale / 1.2F, 0.0F, 0, 0, 128, 128, false, false);
            else
                sb.draw(img, ICON_X - 64F, ((Legend.Y - SPACE_Y * (float)index) + OFFSET_Y) - 64F, 64F, 64F, 128F, 128F, Settings.scale / 1.65F, Settings.scale / 1.65F, 0.0F, 0, 0, 128, 128, false, false);
        } else
        if(hb.hovered)
            sb.draw(img, ICON_X - 64F, ((Legend.Y - SPACE_Y * (float)index) + OFFSET_Y) - 64F, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        else
            sb.draw(img, ICON_X - 64F, ((Legend.Y - SPACE_Y * (float)index) + OFFSET_Y) - 64F, 64F, 64F, 128F, 128F, Settings.scale / 1.3F, Settings.scale / 1.3F, 0.0F, 0, 0, 128, 128, false, false);
        if(Settings.isMobile)
            FontHelper.panelNameFont.getData().setScale(1.2F);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, label, TEXT_X - 50F * Settings.scale, (Legend.Y - SPACE_Y * (float)index) + OFFSET_Y + 13F * Settings.yScale, c);
        if(Settings.isMobile)
            FontHelper.panelNameFont.getData().setScale(1.0F);
        hb.move(TEXT_X, (Legend.Y - SPACE_Y * (float)index) + OFFSET_Y);
        if(c.a != 0.0F)
            hb.render(sb);
    }

    private static final float ICON_X;
    private static final float TEXT_X;
    private static final float SPACE_Y;
    private static final float OFFSET_Y;
    private Texture img;
    private static final int W = 128;
    private int index;
    private String label;
    private String header;
    private String body;
    public Hitbox hb;

    static 
    {
        ICON_X = 1575F * Settings.xScale;
        TEXT_X = 1670F * Settings.xScale;
        SPACE_Y = Settings.isMobile ? 64F * Settings.yScale : 58F * Settings.yScale;
        OFFSET_Y = Settings.isMobile ? 110F * Settings.yScale : 100F * Settings.yScale;
    }
}
