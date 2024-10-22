// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnlockConfirmButton.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.*;
import java.util.ArrayList;
import java.util.Iterator;

public class UnlockConfirmButton
{

    public UnlockConfirmButton()
    {
        hoverColor = Color.WHITE.cpy();
        textColor = Color.WHITE.cpy();
        btnColor = Color.WHITE.cpy();
        target_a = 0.0F;
        done = false;
        animTimer = 0.0F;
        scale = 0.8F;
        buttonText = "NOT_SET";
        hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);
        buttonText = TEXT[0];
        hb.move((float)Settings.WIDTH / 2.0F, TAKE_Y);
    }

    public void update()
    {
        animateIn();
        if(!done && animTimer < 0.2F)
            hb.update();
        if(hb.hovered && !done)
            hoverColor.a = 0.33F;
        else
            hoverColor.a = MathHelper.fadeLerpSnap(hoverColor.a, 0.0F);
        if(hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            hb.clickStarted = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        }
        if(hb.clicked || CInputActionSet.select.isJustPressed())
        {
            CInputActionSet.select.unpress();
            hb.clicked = false;
            hb.hovered = false;
            if(AbstractDungeon.unlockScreen.unlock != null)
            {
                UnlockTracker.hardUnlock(AbstractDungeon.unlockScreen.unlock.key);
                CardCrawlGame.sound.stop("UNLOCK_SCREEN", AbstractDungeon.unlockScreen.id);
            } else
            if(AbstractDungeon.unlocks != null)
            {
                AbstractUnlock u;
                for(Iterator iterator = AbstractDungeon.unlocks.iterator(); iterator.hasNext(); UnlockTracker.hardUnlock(u.key))
                    u = (AbstractUnlock)iterator.next();

            }
            InputHelper.justClickedLeft = false;
            hide();
            if(!AbstractDungeon.is_victory)
                AbstractDungeon.previousScreen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DEATH;
            else
                AbstractDungeon.previousScreen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.VICTORY;
            AbstractDungeon.closeCurrentScreen();
        }
        textColor.a = MathHelper.fadeLerpSnap(textColor.a, target_a);
        btnColor.a = textColor.a;
    }

    private void animateIn()
    {
        if(animTimer != 0.0F)
        {
            animTimer -= Gdx.graphics.getDeltaTime();
            if(animTimer < 0.0F)
                animTimer = 0.0F;
            scale = Interpolation.elasticIn.apply(1.0F, 0.6F, animTimer / 0.4F);
        }
    }

    public void hide()
    {
        textColor = Color.LIGHT_GRAY.cpy();
        done = true;
    }

    public void show()
    {
        textColor = Color.WHITE.cpy();
        animTimer = 0.4F;
        hoverColor.a = 0.0F;
        textColor.a = 0.0F;
        target_a = 1.0F;
        scale = 0.6F;
        done = false;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(btnColor);
        renderButton(sb);
        if(!hb.clickStarted && !done)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(hoverColor);
            renderButton(sb);
            sb.setBlendFunction(770, 771);
        }
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, buttonText, (float)Settings.WIDTH / 2.0F, TAKE_Y, textColor, scale);
    }

    private void renderButton(SpriteBatch sb)
    {
        if(hb.clickStarted)
            sb.setColor(Color.LIGHT_GRAY);
        if(!done)
            sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, X - 256F, TAKE_Y - 128F, 256F, 128F, 512F, 256F, scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        else
            sb.draw(ImageMaster.REWARD_SCREEN_TAKE_USED_BUTTON, X - 256F, TAKE_Y - 128F, 256F, 128F, 512F, 256F, scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.select.getKeyImg(), X - 32F - 130F * Settings.scale, TAKE_Y - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        hb.render(sb);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final int W = 512;
    private static final int H = 256;
    private static final float TAKE_Y;
    private static final float X;
    private Color hoverColor;
    private Color textColor;
    private Color btnColor;
    private float target_a;
    private boolean done;
    private float animTimer;
    private static final float ANIM_TIME = 0.4F;
    private float scale;
    private static final float HOVER_BRIGHTNESS = 0.33F;
    private static final float SCALE_START = 0.6F;
    private String buttonText;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Unlock Confirm Button");
        TEXT = uiStrings.TEXT;
        TAKE_Y = (float)Settings.HEIGHT / 2.0F - 410F * Settings.scale;
        X = (float)Settings.WIDTH / 2.0F;
        HITBOX_W = 260F * Settings.scale;
        HITBOX_H = 80F * Settings.scale;
    }
}
