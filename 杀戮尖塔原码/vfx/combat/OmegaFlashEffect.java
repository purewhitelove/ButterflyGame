// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OmegaFlashEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class OmegaFlashEffect extends AbstractGameEffect
{

    public OmegaFlashEffect(float x, float y)
    {
        playedSound = false;
        img = AbstractPower.atlas.findRegion("128/omega");
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        startingDuration = 0.5F;
        duration = startingDuration;
        color = Color.WHITE.cpy();
    }

    public void update()
    {
        if(!playedSound)
        {
            CardCrawlGame.sound.playA("BLOCK_ATTACK", -0.5F);
            playedSound = true;
        }
        super.update();
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(2.9F, 3.1F), scale * MathUtils.random(2.9F, 3.1F), rotation);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(2.9F, 3.1F), scale * MathUtils.random(2.9F, 3.1F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean playedSound;
}
