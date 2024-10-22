// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SyncMessage.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

public class SyncMessage
{

    public SyncMessage()
    {
        color = Settings.CREAM_COLOR.cpy();
        x = HIDE_X;
        isShowing = false;
    }

    public void show()
    {
        isShowing = true;
    }

    public void hide()
    {
        isShowing = false;
    }

    public void update()
    {
        if(Settings.isDebug && InputHelper.justClickedRight)
            toggle();
        if(isShowing)
        {
            x = MathHelper.uiLerpSnap(x, SHOW_X);
            color.a = (MathUtils.cosDeg((System.currentTimeMillis() / 3L) % 360L) + 1.25F) / 2.3F;
        } else
        {
            x = MathHelper.uiLerpSnap(x, HIDE_X);
            color.a = MathHelper.slowColorLerpSnap(color.a, 0.0F);
        }
    }

    public void toggle()
    {
        if(isShowing)
            hide();
        else
            show();
    }

    public void render(SpriteBatch sb)
    {
        if(color.a == 0.0F)
        {
            return;
        } else
        {
            FontHelper.renderFontRightTopAligned(sb, FontHelper.damageNumberFont, message, x, y, color);
            return;
        }
    }

    private static final String message;
    private Color color;
    private static final float HIDE_X;
    private static final float SHOW_X;
    private float x;
    private static final float y;
    public boolean isShowing;

    static 
    {
        message = CardCrawlGame.languagePack.getUIString("SyncMessage").TEXT[0];
        HIDE_X = (float)Settings.WIDTH + 720F * Settings.scale;
        SHOW_X = (float)Settings.WIDTH - 80F * Settings.scale;
        y = (float)Settings.HEIGHT - 80F * Settings.scale;
    }
}
