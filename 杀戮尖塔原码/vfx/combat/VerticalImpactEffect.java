// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VerticalImpactEffect.java

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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.GenericSmokeEffect;
import java.util.ArrayList;

public class VerticalImpactEffect extends AbstractGameEffect
{

    public VerticalImpactEffect(float x, float y)
    {
        playedSound = false;
        img = ImageMaster.VERTICAL_IMPACT;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight * 0.01F;
        startingDuration = 0.6F;
        duration = 0.6F;
        scale = Settings.scale;
        rotation = MathUtils.random(40F, 50F);
        color = Color.SCARLET.cpy();
        renderBehind = false;
        for(int i = 0; i < 50; i++)
            AbstractDungeon.effectsQueue.add(new GenericSmokeEffect(x + MathUtils.random(-280F, 250F) * Settings.scale, y - 80F * Settings.scale));

    }

    private void playRandomSfX()
    {
        CardCrawlGame.sound.playA("BLUNT_HEAVY", -0.3F);
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        if(duration < 0.5F && !playedSound)
        {
            playRandomSfX();
            playedSound = true;
        }
        if(duration > 0.2F)
            color.a = Interpolation.fade.apply(0.5F, 0.0F, (duration - 0.34F) * 5F);
        else
            color.a = Interpolation.fade.apply(0.0F, 0.5F, duration * 5F);
        scale = Interpolation.fade.apply(Settings.scale * 1.1F, Settings.scale * 1.05F, duration / 0.6F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x + MathUtils.random(-10F, 10F) * Settings.scale, y, (float)img.packedWidth / 2.0F, 0.0F, img.packedWidth, img.packedHeight, scale * 0.3F, scale * 0.8F, rotation - 18F);
        sb.draw(img, x + MathUtils.random(-10F, 10F) * Settings.scale, y, (float)img.packedWidth / 2.0F, 0.0F, img.packedWidth, img.packedHeight, scale * 0.3F, scale * 0.8F, rotation + MathUtils.random(12F, 18F));
        sb.draw(img, x + MathUtils.random(-10F, 10F) * Settings.scale, y, (float)img.packedWidth / 2.0F, 0.0F, img.packedWidth, img.packedHeight, scale * 0.4F, scale * 0.5F, rotation - MathUtils.random(-10F, 14F));
        sb.draw(img, x + MathUtils.random(-10F, 10F) * Settings.scale, y, (float)img.packedWidth / 2.0F, 0.0F, img.packedWidth, img.packedHeight, scale * 0.7F, scale * 0.9F, rotation + MathUtils.random(20F, 28F));
        sb.draw(img, x + MathUtils.random(-10F, 10F) * Settings.scale, y, (float)img.packedWidth / 2.0F, 0.0F, img.packedWidth, img.packedHeight, scale * 1.5F, scale * MathUtils.random(1.4F, 1.6F), rotation);
        Color c = Color.GOLD.cpy();
        c.a = color.a;
        sb.setColor(c);
        sb.draw(img, x + MathUtils.random(-10F, 10F) * Settings.scale, y, (float)img.packedWidth / 2.0F, 0.0F, img.packedWidth, img.packedHeight, scale, scale * MathUtils.random(0.8F, 1.2F), rotation);
        sb.draw(img, x + MathUtils.random(-10F, 10F) * Settings.scale, y, (float)img.packedWidth / 2.0F, 0.0F, img.packedWidth, img.packedHeight, scale, scale * MathUtils.random(0.4F, 0.6F), rotation);
        sb.draw(img, x + MathUtils.random(-10F, 10F) * Settings.scale, y, (float)img.packedWidth / 2.0F, 0.0F, img.packedWidth, img.packedHeight, scale * 0.5F, scale * 0.7F, rotation + MathUtils.random(20F, 28F));
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private static final float DUR = 0.6F;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean playedSound;
}
