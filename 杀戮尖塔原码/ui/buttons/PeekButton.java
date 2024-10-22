// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PeekButton.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui.buttons:
//            DynamicBanner

public class PeekButton
{

    public PeekButton()
    {
        current_x = HIDE_X;
        target_x = current_x;
        isHidden = true;
        particleTimer = 0.0F;
        hb = new Hitbox(170F * Settings.scale, 170F * Settings.scale);
        hb.move(SHOW_X, DRAW_Y);
    }

    public void update()
    {
        if(!isHidden)
        {
            hb.update();
            if(InputHelper.justClickedLeft && hb.hovered)
            {
                hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
            if(hb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            if(InputActionSet.peek.isJustPressed() || CInputActionSet.peek.isJustPressed())
            {
                CInputActionSet.peek.unpress();
                hb.clicked = true;
            }
        }
        if(isPeeking)
        {
            particleTimer -= Gdx.graphics.getDeltaTime();
            if(particleTimer < 0.0F)
            {
                particleTimer = 0.2F;
                AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(hb.cX, hb.cY, Color.SKY));
                AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(hb.cX, hb.cY, Color.WHITE));
            }
        }
        if(current_x != target_x)
        {
            current_x = MathUtils.lerp(current_x, target_x, Gdx.graphics.getDeltaTime() * 9F);
            if(Math.abs(current_x - target_x) < Settings.UI_SNAP_THRESHOLD)
                current_x = target_x;
        }
    }

    public void hideInstantly()
    {
        current_x = HIDE_X;
        target_x = HIDE_X;
        isHidden = true;
    }

    public void hide()
    {
        if(!isHidden)
        {
            target_x = HIDE_X;
            isHidden = true;
        }
    }

    public void show()
    {
        if(isHidden)
        {
            isPeeking = false;
            target_x = SHOW_X;
            isHidden = false;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        renderButton(sb);
        if(isPeeking)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(0.6F, 1.0F, 1.0F, 1.0F));
            float derp = Interpolation.swingOut.apply(1.0F, 1.1F, MathUtils.cosDeg((System.currentTimeMillis() / 4L) % 360L) / 12F);
            sb.draw(ImageMaster.PEEK_BUTTON, current_x - 64F, (float)Settings.HEIGHT / 2.0F - 64F, 64F, 64F, 128F, 128F, Settings.scale * derp, Settings.scale * derp, 0.0F, 0, 0, 128, 128, false, false);
            sb.setBlendFunction(770, 771);
        }
        if(hb.hovered && !hb.clickStarted)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            renderButton(sb);
            sb.setBlendFunction(770, 771);
        }
        if(hb.clicked)
        {
            hb.clicked = false;
            isPeeking = !isPeeking;
            if(isPeeking)
            {
                AbstractDungeon.overlayMenu.hideBlackScreen();
                AbstractDungeon.dynamicBanner.hide();
            } else
            {
                AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
                if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.CARD_REWARD)
                    AbstractDungeon.dynamicBanner.appear();
            }
        }
        renderControllerUi(sb);
        if(!isHidden)
            hb.render(sb);
    }

    private void renderButton(SpriteBatch sb)
    {
        sb.draw(ImageMaster.PEEK_BUTTON, current_x - 64F, (float)Settings.HEIGHT / 2.0F - 64F, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
    }

    private void renderControllerUi(SpriteBatch sb)
    {
        if(Settings.isControllerMode && !isHidden)
        {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.peek.getKeyImg(), 20F * Settings.scale, hb.cY - 56F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
    }

    private static final Color HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.4F);
    private static final float SHOW_X;
    private static final float DRAW_Y;
    private static final float HIDE_X;
    private float current_x;
    private float target_x;
    private boolean isHidden;
    public static boolean isPeeking = false;
    private float particleTimer;
    public Hitbox hb;

    static 
    {
        SHOW_X = 140F * Settings.scale;
        DRAW_Y = (float)Settings.HEIGHT / 2.0F;
        HIDE_X = SHOW_X - 400F * Settings.scale;
    }
}
