// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TouchPickupGold.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, ShineLinesEffect

public class TouchPickupGold extends AbstractGameEffect
{

    public TouchPickupGold()
    {
        isPickupable = false;
        pickedup = false;
        hasBounced = true;
        vY = -0.2F;
        vX = 0.0F;
        gravity = -0.3F;
        if(MathUtils.randomBoolean())
            img = ImageMaster.COPPER_COIN_1;
        else
            img = ImageMaster.COPPER_COIN_2;
        willBounce = MathUtils.random(3) != 0;
        if(willBounce)
        {
            hasBounced = false;
            bounceY = MathUtils.random(1.0F, 4F);
            bounceX = MathUtils.random(-3F, 3F);
        }
        y = (float)Settings.HEIGHT * MathUtils.random(1.1F, 1.3F) - (float)img.packedHeight / 2.0F;
        x = MathUtils.random((float)Settings.WIDTH * 0.3F, (float)Settings.WIDTH * 0.95F) - (float)img.packedWidth / 2.0F;
        landingY = MathUtils.random(AbstractDungeon.floorY - (float)Settings.HEIGHT * 0.05F, AbstractDungeon.floorY + (float)Settings.HEIGHT * 0.08F);
        rotation = MathUtils.random(360F);
        scale = Settings.scale;
    }

    public TouchPickupGold(boolean centerOnPlayer)
    {
        this();
        if(centerOnPlayer)
        {
            x = MathUtils.random(AbstractDungeon.player.drawX - AbstractDungeon.player.hb_w, AbstractDungeon.player.drawX + AbstractDungeon.player.hb_w);
            gravity = -0.7F;
        }
    }

    public void update()
    {
        if(!isPickupable)
        {
            x += vX * Gdx.graphics.getDeltaTime() * 60F;
            y += vY * Gdx.graphics.getDeltaTime() * 60F;
            vY += gravity;
            if(y < landingY)
                if(hasBounced)
                {
                    y = landingY;
                    isPickupable = true;
                    hitbox = new Hitbox(x - IMG_WIDTH * 2.0F, y - IMG_WIDTH * 2.0F, IMG_WIDTH * 4F, IMG_WIDTH * 4F);
                } else
                {
                    if(MathUtils.random(1) == 0)
                        hasBounced = true;
                    y = landingY;
                    vY = bounceY;
                    vX = bounceX;
                    bounceY *= 0.5F;
                    bounceX *= 0.3F;
                }
        } else
        if(!pickedup)
        {
            pickedup = true;
            isDone = true;
            playGainGoldSFX();
            AbstractDungeon.effectsQueue.add(new ShineLinesEffect(x, y));
        }
    }

    private void playGainGoldSFX()
    {
        int roll = MathUtils.random(2);
        switch(roll)
        {
        case 0: // '\0'
            CardCrawlGame.sound.play("GOLD_GAIN", 0.1F);
            break;

        case 1: // '\001'
            CardCrawlGame.sound.play("GOLD_GAIN_3", 0.1F);
            break;

        default:
            CardCrawlGame.sound.play("GOLD_GAIN_5", 0.1F);
            break;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        if(hitbox != null)
            hitbox.render(sb);
    }

    public void dispose()
    {
    }

    private static final float RAW_IMG_WIDTH = 32F;
    private static final float IMG_WIDTH;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean isPickupable;
    public boolean pickedup;
    private float x;
    private float y;
    private float landingY;
    private boolean willBounce;
    private boolean hasBounced;
    private float bounceY;
    private float bounceX;
    private float vY;
    private float vX;
    private float gravity;
    private Hitbox hitbox;

    static 
    {
        IMG_WIDTH = 32F * Settings.scale;
    }
}
