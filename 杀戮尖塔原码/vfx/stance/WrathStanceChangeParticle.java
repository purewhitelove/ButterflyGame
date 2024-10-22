// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WrathStanceChangeParticle.java

package com.megacrit.cardcrawl.vfx.stance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WrathStanceChangeParticle extends AbstractGameEffect
{

    public WrathStanceChangeParticle(float playerX)
    {
        img = ImageMaster.STRIKE_LINE;
        startingDuration = 1.0F;
        duration = startingDuration;
        color = new Color(1.0F, MathUtils.random(0.1F, 0.3F), 0.1F, 0.0F);
        x = MathUtils.random(-30F, 30F) * Settings.scale - (float)img.packedWidth / 2.0F;
        y = ((float)Settings.HEIGHT / 2.0F + MathUtils.random(-150F, 150F) * Settings.scale) - (float)img.packedHeight / 2.0F;
        scale = MathUtils.random(2.2F, 2.5F) * Settings.scale;
        delayTimer = MathUtils.random(0.5F);
        rotation = MathUtils.random(89F, 91F);
        renderBehind = MathUtils.randomBoolean(0.9F);
    }

    public void update()
    {
        if(delayTimer > 0.0F)
        {
            delayTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            return;
        }
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.pow3In.apply(0.6F, 0.0F, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
        else
            color.a = Interpolation.fade.apply(0.0F, 0.6F, duration / (startingDuration / 2.0F));
    }

    public void render(SpriteBatch sb)
    {
        if(delayTimer > 0.0F)
        {
            return;
        } else
        {
            sb.setColor(color);
            sb.setBlendFunction(770, 1);
            sb.draw(img, AbstractDungeon.player.hb.cX + x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(2.9F, 3.1F), scale * MathUtils.random(0.95F, 1.05F), rotation);
            sb.setBlendFunction(770, 771);
            return;
        }
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float delayTimer;
}
