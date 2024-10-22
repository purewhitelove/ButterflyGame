// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractCampfireOption.java

package com.megacrit.cardcrawl.ui.campfire;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;

public abstract class AbstractCampfireOption
{

    public AbstractCampfireOption()
    {
        color = Color.WHITE.cpy();
        descriptionColor = Settings.CREAM_COLOR.cpy();
        scale = NORM_SCALE;
        hb = new Hitbox(216F * Settings.scale, 140F * Settings.scale);
        usable = true;
    }

    public void setPosition(float x, float y)
    {
        hb.move(x, y);
    }

    public void update()
    {
        hb.update();
        boolean canClick = !((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI.somethingSelected && usable;
        if(hb.hovered && canClick)
        {
            if(hb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            if(InputHelper.justClickedLeft)
            {
                CardCrawlGame.sound.play("UI_CLICK_1");
                hb.clickStarted = true;
            }
            if(!hb.clickStarted)
            {
                scale = MathHelper.scaleLerpSnap(scale, HOVER_SCALE);
                scale = MathHelper.scaleLerpSnap(scale, HOVER_SCALE);
            } else
            {
                scale = MathHelper.scaleLerpSnap(scale, NORM_SCALE);
            }
        } else
        {
            scale = MathHelper.scaleLerpSnap(scale, NORM_SCALE);
        }
        if(hb.clicked || CInputActionSet.select.isJustPressed() && canClick && hb.hovered)
        {
            hb.clicked = false;
            if(!Settings.isTouchScreen)
            {
                useOption();
                ((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI.somethingSelected = true;
            } else
            {
                CampfireUI campfire = ((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI;
                if(campfire.touchOption != this)
                {
                    campfire.touchOption = this;
                    campfire.confirmButton.hideInstantly();
                    campfire.confirmButton.isDisabled = false;
                    campfire.confirmButton.show();
                }
            }
        }
    }

    public abstract void useOption();

    public void render(SpriteBatch sb)
    {
        float scaler = ((scale - NORM_SCALE) * 10F) / Settings.scale;
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, color.a / 5F));
        sb.draw(img, (hb.cX - 128F) + SHDW_X, (hb.cY - 128F) + SHDW_Y, 128F, 128F, 256F, 256F, scale, scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.setColor(new Color(1.0F, 0.93F, 0.45F, scaler));
        sb.draw(ImageMaster.CAMPFIRE_HOVER_BUTTON, hb.cX - 128F, hb.cY - 128F, 128F, 128F, 256F, 256F, scale * 1.075F, scale * 1.075F, 0.0F, 0, 0, 256, 256, false, false);
        sb.setColor(color);
        if(!usable)
            ShaderHelper.setShader(sb, com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.GRAYSCALE);
        sb.draw(img, hb.cX - 128F, hb.cY - 128F, 128F, 128F, 256F, 256F, scale, scale, 0.0F, 0, 0, 256, 256, false, false);
        if(!usable)
            ShaderHelper.setShader(sb, com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.DEFAULT);
        if(!usable)
            FontHelper.renderFontCenteredTopAligned(sb, FontHelper.topPanelInfoFont, label, hb.cX, hb.cY - 60F * Settings.scale - 50F * Settings.scale * (scale / Settings.scale), Color.LIGHT_GRAY);
        else
            FontHelper.renderFontCenteredTopAligned(sb, FontHelper.topPanelInfoFont, label, hb.cX, hb.cY - 60F * Settings.scale - 50F * Settings.scale * (scale / Settings.scale), Settings.GOLD_COLOR);
        descriptionColor.a = scaler;
        FontHelper.renderFontCenteredTopAligned(sb, FontHelper.topPanelInfoFont, description, 950F * Settings.xScale, (float)Settings.HEIGHT / 2.0F + 20F * Settings.scale, descriptionColor);
        hb.render(sb);
    }

    protected String label;
    protected String description;
    protected Texture img;
    private static final int W = 256;
    private static final float SHDW_X;
    private static final float SHDW_Y;
    private Color color;
    private Color descriptionColor;
    private static final float NORM_SCALE;
    private static final float HOVER_SCALE;
    private float scale;
    public Hitbox hb;
    public boolean usable;

    static 
    {
        SHDW_X = 11F * Settings.scale;
        SHDW_Y = -8F * Settings.scale;
        NORM_SCALE = 0.9F * Settings.scale;
        HOVER_SCALE = Settings.scale;
    }
}
