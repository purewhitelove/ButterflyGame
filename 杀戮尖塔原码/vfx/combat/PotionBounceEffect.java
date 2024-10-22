// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PotionBounceEffect.java

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
import java.util.ArrayList;

public class PotionBounceEffect extends AbstractGameEffect
{

    public PotionBounceEffect(float srcX, float srcY, float destX, float destY)
    {
        playedSfx = false;
        previousPos = new ArrayList();
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/flask");
        sX = srcX;
        sY = srcY;
        cX = sX;
        cY = sY;
        dX = destX;
        dY = destY;
        rotation = 0.0F;
        duration = 0.6F;
        color = new Color(0.4F, 1.0F, 0.0F, 0.0F);
        if(sY > dY)
            bounceHeight = 400F * Settings.scale;
        else
            bounceHeight = (dY - sY) + 400F * Settings.scale;
    }

    public void update()
    {
        if(!playedSfx)
        {
            playedSfx = true;
            if(MathUtils.randomBoolean())
                CardCrawlGame.sound.playA("POTION_DROP_1", MathUtils.random(-0.3F, -0.2F));
            else
                CardCrawlGame.sound.playA("POTION_DROP_2", MathUtils.random(-0.3F, -0.2F));
            if(MathUtils.randomBoolean())
                CardCrawlGame.sound.play("POTION_1");
            else
                CardCrawlGame.sound.play("POTION_2");
        }
        cX = Interpolation.linear.apply(dX, sX, duration / 0.6F);
        cY = Interpolation.linear.apply(dY, sY, duration / 0.6F);
        previousPos.add(new Vector2(cX + MathUtils.random(-30F, 30F) * Settings.scale, cY + yOffset + MathUtils.random(-30F, 30F) * Settings.scale));
        if(previousPos.size() > 20)
            previousPos.remove(previousPos.get(0));
        if(dX > sX)
            rotation -= Gdx.graphics.getDeltaTime() * 1000F;
        else
            rotation += Gdx.graphics.getDeltaTime() * 1000F;
        if(duration > 0.3F)
        {
            color.a = Interpolation.exp5In.apply(1.0F, 0.0F, (duration - 0.3F) / 0.3F) * Settings.scale;
            yOffset = Interpolation.circleIn.apply(bounceHeight, 0.0F, (duration - 0.3F) / 0.3F) * Settings.scale;
        } else
        {
            yOffset = Interpolation.circleOut.apply(0.0F, bounceHeight, duration / 0.3F) * Settings.scale;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(0.4F, 1.0F, 1.0F, color.a / 3F));
        for(int i = 5; i < previousPos.size(); i++)
            sb.draw(ImageMaster.POWER_UP_2, ((Vector2)previousPos.get(i)).x - (float)(img.packedWidth / 2), ((Vector2)previousPos.get(i)).y - (float)(img.packedHeight / 2), (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale / (40F / (float)i), scale / (40F / (float)i), rotation);

        sb.setColor(color);
        sb.draw(img, cX - (float)(img.packedWidth / 2), (cY - (float)(img.packedHeight / 2)) + yOffset, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.draw(img, cX - (float)(img.packedWidth / 2), (cY - (float)(img.packedHeight / 2)) + yOffset, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float sX;
    private float sY;
    private float cX;
    private float cY;
    private float dX;
    private float dY;
    private float yOffset;
    private float bounceHeight;
    private static final float DUR = 0.6F;
    private boolean playedSfx;
    private ArrayList previousPos;
}
