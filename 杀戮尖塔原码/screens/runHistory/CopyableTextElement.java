// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CopyableTextElement.java

package com.megacrit.cardcrawl.screens.runHistory;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Clipboard;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class CopyableTextElement
{

    public CopyableTextElement(BitmapFont font)
    {
        text = "";
        copyText = "";
        this.font = font;
        float height = font.getLineHeight() * Settings.scale;
        hb = new Hitbox(0.0F, height + 2.0F * HITBOX_PADDING);
    }

    public void update()
    {
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        else
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            hb.clickStarted = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        } else
        if(hb.clicked)
        {
            hb.clicked = false;
            copySeedToSystem();
        }
    }

    private void copySeedToSystem()
    {
        Clipboard clipBoard = Gdx.app.getClipboard();
        clipBoard.setContents(copyText);
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        Color textColor = hb.hovered ? Settings.GREEN_TEXT_COLOR : Settings.GOLD_COLOR;
        renderSmallText(sb, getText(), x, y, textColor);
        hb.translate(x - HITBOX_PADDING, (y - hb.height) + HITBOX_PADDING);
        hb.render(sb);
    }

    public float approximateWidth()
    {
        return FontHelper.getSmartWidth(font, getText(), 3.402823E+038F, 36F * Settings.scale);
    }

    private void renderSmallText(SpriteBatch sb, String text, float x, float y, Color color)
    {
        FontHelper.renderSmartText(sb, font, text, x, y, 3.402823E+038F, 36F * Settings.scale, color);
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
        hb.width = approximateWidth() + 2.0F * HITBOX_PADDING;
    }

    public void setText(String text, String copyText)
    {
        setText(text);
        this.copyText = copyText;
    }

    private static final float HITBOX_PADDING;
    private String text;
    private String copyText;
    private Hitbox hb;
    private BitmapFont font;

    static 
    {
        HITBOX_PADDING = 4F * Settings.scale;
    }
}
