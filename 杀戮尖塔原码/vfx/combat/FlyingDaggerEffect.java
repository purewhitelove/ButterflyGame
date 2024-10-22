// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlyingDaggerEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FlyingDaggerEffect extends AbstractGameEffect
{

    public FlyingDaggerEffect(float x, float y, float fAngle, boolean shouldFlip)
    {
        playedSound = false;
        img = ImageMaster.DAGGER_STREAK;
        this.x = x - (float)img.packedWidth / 2.0F;
        destY = y;
        this.y = destY - (float)img.packedHeight / 2.0F;
        startingDuration = 0.5F;
        duration = 0.5F;
        scaleMultiplier = MathUtils.random(1.2F, 1.5F);
        scale = 0.25F * Settings.scale;
        if(shouldFlip)
            rotation = fAngle - 180F;
        else
            rotation = fAngle;
        color = Color.CHARTREUSE.cpy();
        color.a = 0.0F;
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
        Vector2 derp = new Vector2(MathUtils.cos(0.01745329F * rotation), MathUtils.sin(0.01745329F * rotation));
        x += derp.x * Gdx.graphics.getDeltaTime() * (3500F * scaleMultiplier) * Settings.scale;
        y += derp.y * Gdx.graphics.getDeltaTime() * (3500F * scaleMultiplier) * Settings.scale;
        if(duration < 0.0F)
            isDone = true;
        if(duration > 0.25F)
            color.a = Interpolation.pow5In.apply(1.0F, 0.0F, (duration - 0.25F) * 4F);
        else
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration * 4F);
        scale += Gdx.graphics.getDeltaTime() * scaleMultiplier;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale * 1.5F, rotation);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 0.75F, scale * 0.75F, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float destY;
    private float scaleMultiplier;
    private static final float DUR = 0.5F;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean playedSound;
}
