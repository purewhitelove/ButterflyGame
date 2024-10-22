// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdrenalineEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import java.util.ArrayList;

public class AdrenalineEffect extends AbstractGameEffect
{

    public AdrenalineEffect()
    {
        img = null;
        prevPositions = new ArrayList();
        img = ImageMaster.GLOW_SPARK_2;
        duration = 1.5F;
        if(flipper)
            position = new Vector2(-100F * Settings.scale - (float)img.packedWidth / 2.0F, (float)Settings.HEIGHT / 2.0F - (float)img.packedHeight / 2.0F);
        else
            position = new Vector2(-50F * Settings.scale - (float)img.packedWidth / 2.0F, (float)Settings.HEIGHT / 2.0F - (float)img.packedHeight / 2.0F);
        flipper = !flipper;
        velocity = new Vector2(3000F * Settings.scale, 0.0F);
        color = new Color(1.0F, 1.0F, 0.2F, 1.0F);
        scale = 3F * Settings.scale;
    }

    public void update()
    {
        if(duration == 1.5F)
        {
            CardCrawlGame.sound.playA("ATTACK_WHIFF_1", -0.6F);
            CardCrawlGame.sound.playA("ORB_LIGHTNING_CHANNEL", 0.6F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.BLUE.cpy(), true));
        }
        if(position.x > (float)Settings.WIDTH * 0.55F && position.y > (float)Settings.HEIGHT / 2.0F - (float)img.packedHeight / 2.0F)
        {
            velocity.y = 0.0F;
            position.y = (float)Settings.HEIGHT / 2.0F - (float)img.packedHeight / 2.0F;
            velocity.x = 3000F * Settings.scale;
        } else
        if(position.x > (float)Settings.WIDTH * 0.5F)
            velocity.y = 6000F * Settings.scale;
        else
        if(position.x > (float)Settings.WIDTH * 0.4F)
            velocity.y = -6000F * Settings.scale;
        else
        if(position.x > (float)Settings.WIDTH * 0.35F)
        {
            velocity.y = 6000F * Settings.scale;
            velocity.x = 2000F * Settings.scale;
        }
        prevPositions.add(position.cpy());
        position.mulAdd(velocity, Gdx.graphics.getDeltaTime());
        if(prevPositions.size() > 30)
            prevPositions.remove(0);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        for(int i = 0; i < prevPositions.size(); i++)
        {
            sb.setColor(new Color(1.0F, 0.9F, 0.3F, 1.0F));
            sb.draw(img, ((Vector2)prevPositions.get(i)).x, ((Vector2)prevPositions.get(i)).y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, (scale / 8F) * ((float)i * 0.05F + 1.0F) * MathUtils.random(1.5F, 3F), (scale / 8F) * ((float)i * 0.05F + 1.0F) * MathUtils.random(0.5F, 2.0F), 0.0F);
        }

        sb.setColor(Color.RED);
        sb.draw(img, position.x, position.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 2.5F, scale * 2.5F, 0.0F);
        sb.setBlendFunction(770, 771);
        sb.setColor(Color.YELLOW);
        sb.draw(img, position.x, position.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, 0.0F);
    }

    public void dispose()
    {
    }

    private Vector2 position;
    private Vector2 velocity;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private ArrayList prevPositions;
    private static boolean flipper = true;

}
