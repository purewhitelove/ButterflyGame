// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Legend.java

package com.megacrit.cardcrawl.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.map:
//            LegendItem, MapRoomNode

public class Legend
{

    public Legend()
    {
        c = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        items = new ArrayList();
        isLegendHighlighted = false;
        items.add(new LegendItem(TEXT[0], ImageMaster.MAP_NODE_EVENT, TEXT[1], TEXT[2], 0));
        items.add(new LegendItem(TEXT[3], ImageMaster.MAP_NODE_MERCHANT, TEXT[4], TEXT[5], 1));
        items.add(new LegendItem(TEXT[6], ImageMaster.MAP_NODE_TREASURE, TEXT[7], TEXT[8], 2));
        items.add(new LegendItem(TEXT[9], ImageMaster.MAP_NODE_REST, TEXT[10], TEXT[11], 3));
        items.add(new LegendItem(TEXT[12], ImageMaster.MAP_NODE_ENEMY, TEXT[13], TEXT[14], 4));
        items.add(new LegendItem(TEXT[15], ImageMaster.MAP_NODE_ELITE, TEXT[16], TEXT[17], 5));
        if(img == null)
            img = ImageMaster.loadImage("images/ui/map/selectBox.png");
    }

    public boolean isIconHovered(String nodeHovered)
    {
        String s = nodeHovered;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 63: // '?'
            if(s.equals("?"))
                byte0 = 0;
            break;

        case 36: // '$'
            if(s.equals("$"))
                byte0 = 1;
            break;

        case 84: // 'T'
            if(s.equals("T"))
                byte0 = 2;
            break;

        case 82: // 'R'
            if(s.equals("R"))
                byte0 = 3;
            break;

        case 77: // 'M'
            if(s.equals("M"))
                byte0 = 4;
            break;

        case 69: // 'E'
            if(s.equals("E"))
                byte0 = 5;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            if(((LegendItem)items.get(0)).hb.hovered)
                return true;
            break;

        case 1: // '\001'
            if(((LegendItem)items.get(1)).hb.hovered)
                return true;
            break;

        case 2: // '\002'
            if(((LegendItem)items.get(2)).hb.hovered)
                return true;
            break;

        case 3: // '\003'
            if(((LegendItem)items.get(3)).hb.hovered)
                return true;
            break;

        case 4: // '\004'
            if(((LegendItem)items.get(4)).hb.hovered)
                return true;
            break;

        case 5: // '\005'
            if(((LegendItem)items.get(5)).hb.hovered)
                return true;
            break;

        default:
            return false;
        }
        return false;
    }

    public void update(float mapAlpha, boolean isMapScreen)
    {
        if(mapAlpha >= 0.8F && isMapScreen)
        {
            updateControllerInput();
            c.a = MathHelper.fadeLerpSnap(c.a, 1.0F);
            LegendItem i;
            for(Iterator iterator = items.iterator(); iterator.hasNext(); i.update())
                i = (LegendItem)iterator.next();

        } else
        {
            c.a = MathHelper.fadeLerpSnap(c.a, 0.0F);
        }
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        if(isLegendHighlighted)
        {
            if(CInputActionSet.proceed.isJustPressed() || CInputActionSet.cancel.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputActionSet.cancel.unpress();
                isLegendHighlighted = false;
                return;
            }
        } else
        if(CInputActionSet.proceed.isJustPressed())
        {
            isLegendHighlighted = true;
            return;
        }
        if(!isLegendHighlighted)
            return;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = items.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            LegendItem i = (LegendItem)iterator.next();
            if(i.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
            Gdx.input.setCursorPosition((int)((LegendItem)items.get(0)).hb.cX, Settings.HEIGHT - (int)((LegendItem)items.get(0)).hb.cY);
        else
        if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
        {
            if(++index > items.size() - 1)
                index = 0;
            Gdx.input.setCursorPosition((int)((LegendItem)items.get(index)).hb.cX, Settings.HEIGHT - (int)((LegendItem)items.get(index)).hb.cY);
        } else
        if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
        {
            if(--index < 0)
                index = items.size() - 1;
            Gdx.input.setCursorPosition((int)((LegendItem)items.get(index)).hb.cX, Settings.HEIGHT - (int)((LegendItem)items.get(index)).hb.cY);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(c);
        if(!Settings.isMobile)
            sb.draw(ImageMaster.MAP_LEGEND, X - 256F, Y - 400F, 256F, 400F, 512F, 800F, Settings.scale, Settings.yScale, 0.0F, 0, 0, 512, 800, false, false);
        else
            sb.draw(ImageMaster.MAP_LEGEND, X - 256F, Y - 400F, 256F, 400F, 512F, 800F, Settings.scale * 1.1F, Settings.yScale * 1.1F, 0.0F, 0, 0, 512, 800, false, false);
        Color c2 = new Color(MapRoomNode.AVAILABLE_COLOR.r, MapRoomNode.AVAILABLE_COLOR.g, MapRoomNode.AVAILABLE_COLOR.b, c.a);
        if(Settings.isMobile)
            FontHelper.renderFontCentered(sb, FontHelper.menuBannerFont, TEXT[18], X, Y + 190F * Settings.yScale, c2, 1.4F);
        else
            FontHelper.renderFontCentered(sb, FontHelper.menuBannerFont, TEXT[18], X, Y + 170F * Settings.yScale, c2);
        sb.setColor(c2);
        LegendItem i;
        for(Iterator iterator = items.iterator(); iterator.hasNext(); i.render(sb, c2))
            i = (LegendItem)iterator.next();

        if(Settings.isControllerMode)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, c2.a));
            sb.draw(CInputActionSet.proceed.getKeyImg(), 1570F * Settings.xScale - 32F, (Y + 170F * Settings.yScale) - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            if(isLegendHighlighted)
            {
                sb.setColor(new Color(1.0F, 0.9F, 0.5F, 0.6F + MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L) / 5F));
                float doop = 1.0F + (1.0F + MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L)) / 50F;
                sb.draw(img, 1670F * Settings.scale - 160F, ((float)(Settings.HEIGHT - Gdx.input.getY()) - 52F) + 4F * Settings.scale, 160F, 52F, 320F, 104F, Settings.scale * doop, Settings.scale * doop, 0.0F, 0, 0, 320, 104, false, false);
            }
        }
    }

    public static final float X;
    public static final float Y;
    private static final int LW = 512;
    private static final int LH = 800;
    public Color c;
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public ArrayList items;
    public boolean isLegendHighlighted;
    private static Texture img = null;

    static 
    {
        X = 1670F * Settings.xScale;
        Y = 600F * Settings.yScale;
        uiStrings = CardCrawlGame.languagePack.getUIString("Legend");
        TEXT = uiStrings.TEXT;
    }
}
