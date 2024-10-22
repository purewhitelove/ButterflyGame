// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CutscenePanel.java

package com.megacrit.cardcrawl.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;

public class CutscenePanel
{

    public CutscenePanel(String imgUrl, String sfx)
    {
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        activated = false;
        finished = false;
        fadeOut = false;
        this.sfx = null;
        img = ImageMaster.loadImage(imgUrl);
        this.sfx = sfx;
    }

    public CutscenePanel(String imgUrl)
    {
        this(imgUrl, null);
    }

    public void update()
    {
        if(fadeOut)
        {
            color.a -= Gdx.graphics.getDeltaTime();
            if(color.a < 0.0F)
            {
                color.a = 0.0F;
                finished = true;
            }
            return;
        }
        if(activated && !finished)
        {
            if(sfx != null)
                color.a += Gdx.graphics.getDeltaTime() * 10F;
            else
                color.a += Gdx.graphics.getDeltaTime();
            if(color.a > 1.0F)
            {
                color.a = 1.0F;
                finished = true;
            }
        }
    }

    public void activate()
    {
        if(sfx != null)
        {
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
            CardCrawlGame.sound.play(sfx);
            CardCrawlGame.sound.playA(sfx, -0.2F);
        }
        activated = true;
    }

    public void render(SpriteBatch sb)
    {
        if(img != null)
        {
            sb.setColor(color);
            if(Settings.isSixteenByTen)
                sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
            else
                sb.draw(img, 0.0F, -50F * Settings.scale, Settings.WIDTH, (float)Settings.HEIGHT + 110F * Settings.scale);
        }
    }

    public void fadeOut()
    {
        fadeOut = true;
        finished = false;
    }

    public void dispose()
    {
        if(img != null)
        {
            img.dispose();
            img = null;
        }
    }

    private Texture img;
    private Color color;
    public boolean activated;
    public boolean finished;
    public boolean fadeOut;
    private String sfx;
}
