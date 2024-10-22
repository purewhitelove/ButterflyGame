// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThrowShivEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ThrowShivEffect extends AbstractGameEffect
{

    public ThrowShivEffect(float x, float y)
    {
        playedSound = false;
        img = ImageMaster.DAGGER_STREAK;
        this.x = x - MathUtils.random(320F, 360F) - (float)img.packedWidth / 2.0F;
        destY = y;
        this.y = (destY + MathUtils.random(-25F, 25F) * Settings.scale) - (float)img.packedHeight / 2.0F;
        startingDuration = 0.4F;
        duration = 0.4F;
        scale = Settings.scale * MathUtils.random(0.5F, 2.0F);
        rotation = MathUtils.random(-30F, 30F);
        float darkness = MathUtils.random(0.5F, 0.8F);
        color = new Color(darkness, darkness, darkness, 1.0F);
    }

    private void playRandomSfX()
    {
        int roll = MathUtils.random(5);
        switch(roll)
        {
        case 0: // '\0'
            CardCrawlGame.sound.play("ATTACK_DAGGER_1");
            break;

        case 1: // '\001'
            CardCrawlGame.sound.play("ATTACK_DAGGER_2");
            break;

        case 2: // '\002'
            CardCrawlGame.sound.play("ATTACK_DAGGER_3");
            break;

        case 3: // '\003'
            CardCrawlGame.sound.play("ATTACK_DAGGER_4");
            break;

        case 4: // '\004'
            CardCrawlGame.sound.play("ATTACK_DAGGER_5");
            break;

        default:
            CardCrawlGame.sound.play("ATTACK_DAGGER_6");
            break;
        }
    }

    public void update()
    {
        if(!playedSound)
        {
            playRandomSfX();
            playedSound = true;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        if(duration > 0.2F)
            color.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - 0.2F) * 5F);
        else
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration * 5F);
        scale = Interpolation.bounceIn.apply(Settings.scale * 0.5F, Settings.scale * 1.5F, duration / 0.4F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth * 0.85F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale * 1.5F, rotation);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth * 0.85F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 0.75F, scale * 0.75F, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float destY;
    private static final float DUR = 0.4F;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean playedSound;
}
