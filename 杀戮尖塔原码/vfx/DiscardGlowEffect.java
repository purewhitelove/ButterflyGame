// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscardGlowEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class DiscardGlowEffect extends AbstractGameEffect
{

    public DiscardGlowEffect(boolean isAbove)
    {
        scaleJitter = 1.0F;
        shadowColor = Color.BLACK.cpy();
        img = getImg();
        setPosition(isAbove);
        x -= img.packedWidth / 2;
        y -= img.packedHeight / 2;
        effectDuration = MathUtils.random(0.4F, 0.9F);
        duration = effectDuration;
        startingDuration = effectDuration;
        scaleJitter = MathUtils.random(scaleJitter - 0.1F, scaleJitter + 0.1F);
        vY = MathUtils.random(30F * Settings.scale, 60F * Settings.scale);
        color = Settings.DISCARD_COLOR.cpy();
        color.r -= MathUtils.random(0.0F, 0.1F);
        color.g += MathUtils.random(0.0F, 0.1F);
        color.b += MathUtils.random(0.0F, 0.1F);
        isAdditive = MathUtils.randomBoolean();
        rotator = MathUtils.random(-180F, 180F);
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

    private void setPosition(boolean isAbove)
    {
        int roll = MathUtils.random(0, 9);
        if(isAbove)
        {
            switch(roll)
            {
            case 0: // '\0'
                x = 1886F * Settings.scale;
                y = 86F * Settings.scale;
                return;

            case 1: // '\001'
                x = 1883F * Settings.scale;
                y = 80F * Settings.scale;
                return;

            case 2: // '\002'
                x = 1881F * Settings.scale;
                y = 67F * Settings.scale;
                return;

            case 3: // '\003'
                x = 1876F * Settings.scale;
                y = 54F * Settings.scale;
                return;

            case 4: // '\004'
                x = 1873F * Settings.scale;
                y = 45F * Settings.scale;
                return;

            case 5: // '\005'
                x = 1865F * Settings.scale;
                y = 36F * Settings.scale;
                return;

            case 6: // '\006'
                x = 1849F * Settings.scale;
                y = 32F * Settings.scale;
                return;

            case 7: // '\007'
                x = 1841F * Settings.scale;
                y = 36F * Settings.scale;
                return;

            case 8: // '\b'
                x = 1830F * Settings.scale;
                y = 36F * Settings.scale;
                return;
            }
            x = 1819F * Settings.scale;
            y = 43F * Settings.scale;
            return;
        }
        switch(roll)
        {
        case 0: // '\0'
            x = 1810F * Settings.scale;
            y = 84F * Settings.scale;
            return;

        case 1: // '\001'
            x = 1820F * Settings.scale;
            y = 88F * Settings.scale;
            return;

        case 2: // '\002'
            x = 1830F * Settings.scale;
            y = 94F * Settings.scale;
            return;

        case 3: // '\003'
            x = 1834F * Settings.scale;
            y = 96F * Settings.scale;
            return;

        case 4: // '\004'
            x = 1837F * Settings.scale;
            y = 96F * Settings.scale;
            return;

        case 5: // '\005'
            x = 1841F * Settings.scale;
            y = 98F * Settings.scale;
            return;

        case 6: // '\006'
            x = 1854F * Settings.scale;
            y = 99F * Settings.scale;
            return;

        case 7: // '\007'
            x = 1859F * Settings.scale;
            y = 91F * Settings.scale;
            return;

        case 8: // '\b'
            x = 1871F * Settings.scale;
            y = 87F * Settings.scale;
            return;
        }
        x = 1877F * Settings.scale;
        y = 84F * Settings.scale;
    }

    public void update()
    {
        rotation += rotator * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.1F)
            scale = ((Settings.scale * duration) / effectDuration) * 2.0F + Settings.scale / 2.0F;
        if(duration < 0.25F)
            color.a = duration * 4F;
        if(duration < 0.0F)
            isDone = true;
        shadowColor.a = color.a / 2.0F;
    }

    public void render(SpriteBatch sb, float x2, float y2)
    {
        if(isAdditive)
            sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x + x2, y + y2, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * scaleJitter, scale * scaleJitter, rotation);
        if(isAdditive)
            sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    private static final float EFFECT_DUR = 0.2F;
    private float effectDuration;
    private float x;
    private float y;
    private float vY;
    private float rotator;
    private float scaleJitter;
    private static final float SCALE_JITTER_AMT = 0.1F;
    private Color shadowColor;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean isAdditive;
}
