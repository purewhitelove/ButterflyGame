// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SortHeaderButton.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.compendium.CardLibSortHeader;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            SortHeaderButtonListener

public class SortHeaderButton
{

    public SortHeaderButton(String text, float cx, float cy)
    {
        isAscending = false;
        isActive = false;
        hb = new Hitbox(210F * Settings.xScale, 48F * Settings.scale);
        hb.move(cx, cy);
        this.text = text;
        textWidth = FontHelper.getSmartWidth(FontHelper.topPanelInfoFont, text, 3.402823E+038F, 0.0F);
    }

    public SortHeaderButton(String text, float cx, float cy, SortHeaderButtonListener delegate)
    {
        this(text, cx, cy);
        _flddelegate = delegate;
    }

    public void update()
    {
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
        if(hb.hovered && InputHelper.justClickedLeft)
            hb.clickStarted = true;
        if(hb.clicked || hb.hovered && CInputActionSet.select.isJustPressed())
        {
            hb.clicked = false;
            isAscending = !isAscending;
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.2F);
            if(_flddelegate instanceof CardLibSortHeader)
                ((CardLibSortHeader)_flddelegate).clearActiveButtons();
            _flddelegate.didChangeOrder(this, isAscending);
        }
    }

    public void updateScrollPosition(float newY)
    {
        hb.move(hb.cX, newY);
    }

    public void render(SpriteBatch sb)
    {
        Color color = !hb.hovered && !isActive ? Settings.CREAM_COLOR : Settings.GOLD_COLOR;
        FontHelper.renderFontCentered(sb, FontHelper.topPanelInfoFont, text, hb.cX, hb.cY, color);
        sb.setColor(color);
        float arrowCenterOffset = 16F;
        sb.draw(ImageMaster.FILTER_ARROW, (hb.cX - 16F) + (textWidth / 2.0F + 16F * Settings.xScale), hb.cY - 16F, 16F, 16F, 32F, 32F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, !isAscending);
        hb.render(sb);
    }

    public void reset()
    {
        isAscending = false;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public Hitbox hb;
    private boolean isAscending;
    private boolean isActive;
    private String text;
    public SortHeaderButtonListener _flddelegate;
    private final float ARROW_SIZE = 32F;
    public float textWidth;
}
