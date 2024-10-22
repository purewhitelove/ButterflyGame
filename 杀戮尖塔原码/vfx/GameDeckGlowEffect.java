// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameDeckGlowEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class GameDeckGlowEffect extends AbstractGameEffect
{

    public GameDeckGlowEffect(boolean isAbove)
    {
        shadowColor = Color.BLACK.cpy();
        effectDuration = MathUtils.random(2.0F, 5F);
        duration = effectDuration;
        startingDuration = effectDuration;
        vY = MathUtils.random(10F * Settings.scale, 20F * Settings.scale);
        vX = MathUtils.random(10F * Settings.scale, 20F * Settings.scale);
        flipY = MathUtils.randomBoolean();
        flipX = MathUtils.randomBoolean();
        color = Settings.CREAM_COLOR.cpy();
        float darkness = MathUtils.random(0.1F, 0.4F);
        color.r -= darkness;
        color.g -= darkness;
        color.b -= darkness;
        img = getImg();
        x = MathUtils.random(35F, 85F) * Settings.scale - (float)(img.packedWidth / 2);
        y = MathUtils.random(35F, 85F) * Settings.scale - (float)(img.packedHeight / 2);
        scale = Settings.scale * 0.75F;
        rotator = MathUtils.random(-120F, 120F);
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getImg()
    {
        int roll = MathUtils.random(0, 5);
        switch(roll)
        {
        case 0: // '\0'
            return ImageMaster.DECK_GLOW_1;

        case 1: // '\001'
            return ImageMaster.DECK_GLOW_2;

        case 2: // '\002'
            return ImageMaster.DECK_GLOW_3;

        case 3: // '\003'
            return ImageMaster.DECK_GLOW_4;

        case 4: // '\004'
            return ImageMaster.DECK_GLOW_5;
        }
        return ImageMaster.DECK_GLOW_6;
    }

    public void update()
    {
        rotation += rotator * Gdx.graphics.getDeltaTime();
        if(vY != 0.0F)
        {
            if(flipY)
                y += vY * Gdx.graphics.getDeltaTime();
            else
                y -= vY * Gdx.graphics.getDeltaTime();
            vY = MathUtils.lerp(vY, 0.0F, Gdx.graphics.getDeltaTime() / 4F);
            if(vY < 0.5F)
                vY = 0.0F;
        }
        if(vX != 0.0F)
        {
            if(flipX)
                x += vX * Gdx.graphics.getDeltaTime();
            else
                x -= vX * Gdx.graphics.getDeltaTime();
            vX = MathUtils.lerp(vX, 0.0F, Gdx.graphics.getDeltaTime() / 4F);
            if(vX < 0.5F)
                vX = 0.0F;
        }
        duration -= Gdx.graphics.getDeltaTime();
        color.a = duration / effectDuration;
        if(duration < 0.0F)
            isDone = true;
        shadowColor.a = color.a / 2.0F;
    }

    public void render(SpriteBatch sb, float x2, float y2)
    {
        if(img != null)
        {
            sb.setColor(color);
            sb.draw(img, x + x2, y + y2, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        }
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    private float effectDuration;
    private float x;
    private float y;
    private float vY;
    private float vX;
    private float rotator;
    private boolean flipY;
    private boolean flipX;
    private Color shadowColor;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
