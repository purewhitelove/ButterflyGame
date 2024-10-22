// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GotItButton.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class GotItButton
{

    public GotItButton(float x, float y)
    {
        hb = new Hitbox(220F * Settings.scale, 60F * Settings.scale);
        x += 120F * Settings.scale;
        y -= 160F * Settings.scale;
        this.x = x;
        this.y = y;
        hb.move(x, y);
    }

    public void update()
    {
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(hb.hovered && InputHelper.justClickedLeft)
            hb.clickStarted = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.draw(ImageMaster.FTUE_BTN, x - 105F, y - 26F, 105F, 26F, 210F, 52F, Settings.scale, Settings.scale, 0.0F, 0, 0, 210, 52, false, false);
        if(hb.hovered)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
            sb.draw(ImageMaster.FTUE_BTN, x - 105F, y - 26F, 105F, 26F, 210F, 52F, Settings.scale, Settings.scale, 0.0F, 0, 0, 210, 52, false, false);
            sb.setBlendFunction(770, 771);
        }
        hb.render(sb);
    }

    public Hitbox hb;
    private static final int W = 210;
    private static final int H = 52;
    float x;
    float y;
}
