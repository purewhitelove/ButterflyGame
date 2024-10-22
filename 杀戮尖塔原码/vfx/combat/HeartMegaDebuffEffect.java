// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HeartMegaDebuffEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HeartMegaDebuffEffect extends AbstractGameEffect
{

    public HeartMegaDebuffEffect()
    {
        startingDuration = 4F;
        duration = startingDuration;
        color = new Color(0.9F, 0.7F, 1.0F, 0.0F);
        renderBehind = true;
    }

    public void update()
    {
        if(duration == startingDuration)
            CardCrawlGame.sound.playA("GHOST_ORB_IGNITE_1", -0.6F);
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.bounceIn.apply(1.0F, 0.0F, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
        else
            color.a = Interpolation.bounceOut.apply(duration * (startingDuration / 2.0F));
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, color.a * 0.8F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(color);
        sb.draw(ImageMaster.BORDER_GLOW_2, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    public void dispose()
    {
    }
}
