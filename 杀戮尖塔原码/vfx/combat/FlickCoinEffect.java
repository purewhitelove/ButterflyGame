// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlickCoinEffect.java

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
import com.megacrit.cardcrawl.vfx.ShineSparkleEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            AdditiveSlashImpactEffect

public class FlickCoinEffect extends AbstractGameEffect
{

    public FlickCoinEffect(float srcX, float srcY, float destX, float destY)
    {
        playedSfx = false;
        sparkleTimer = 0.0F;
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/empowerCircle1");
        sX = srcX;
        sY = srcY;
        cX = sX;
        cY = sY;
        dX = destX;
        dY = destY - 100F * Settings.scale;
        rotation = 0.0F;
        duration = 0.5F;
        color = new Color(1.0F, 1.0F, 0.0F, 0.0F);
        if(sY > dY)
            bounceHeight = 600F * Settings.scale;
        else
            bounceHeight = (dY - sY) + 600F * Settings.scale;
    }

    public void update()
    {
        if(!playedSfx)
        {
            playedSfx = true;
            CardCrawlGame.sound.playA("ATTACK_WHIFF_2", MathUtils.random(0.7F, 0.8F));
        }
        sparkleTimer -= Gdx.graphics.getDeltaTime();
        if(duration < 0.4F && sparkleTimer < 0.0F)
        {
            for(int i = 0; i < MathUtils.random(2, 5); i++)
                AbstractDungeon.effectsQueue.add(new ShineSparkleEffect(cX, cY + yOffset));

            sparkleTimer = MathUtils.random(0.05F, 0.1F);
        }
        cX = Interpolation.linear.apply(dX, sX, duration / 0.5F);
        cY = Interpolation.linear.apply(dY, sY, duration / 0.5F);
        if(dX > sX)
            rotation -= Gdx.graphics.getDeltaTime() * 1000F;
        else
            rotation += Gdx.graphics.getDeltaTime() * 1000F;
        if(duration > 0.25F)
        {
            color.a = Interpolation.exp5In.apply(1.0F, 0.0F, (duration - 0.25F) / 0.2F) * Settings.scale;
            yOffset = Interpolation.circleIn.apply(bounceHeight, 0.0F, (duration - 0.25F) / 0.25F) * Settings.scale;
        } else
        {
            yOffset = Interpolation.circleOut.apply(0.0F, bounceHeight, duration / 0.25F) * Settings.scale;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            CardCrawlGame.sound.playA("GOLD_GAIN", MathUtils.random(0.0F, 0.1F));
            AbstractDungeon.effectsQueue.add(new AdditiveSlashImpactEffect(dX, dY + 100F * Settings.scale, Color.GOLD.cpy()));
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(0.4F, 1.0F, 1.0F, color.a / 5F));
        sb.setColor(color);
        sb.draw(img, cX - (float)(img.packedWidth / 2), (cY - (float)(img.packedHeight / 2)) + yOffset, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 0.7F, scale * 0.4F, rotation);
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
    private static final float DUR = 0.5F;
    private boolean playedSfx;
    private float sparkleTimer;
}
