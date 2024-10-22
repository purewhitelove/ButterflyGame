// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExhaustEmberEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class ExhaustEmberEffect extends AbstractGameEffect
{

    public ExhaustEmberEffect(float x, float y)
    {
        rotateSpeed = 0.0F;
        switch(MathUtils.random(2))
        {
        case 0: // '\0'
            color = Color.CORAL.cpy();
            break;

        case 1: // '\001'
            color = Color.ORANGE.cpy();
            break;

        case 2: // '\002'
            color = Color.SCARLET.cpy();
            break;
        }
        duration = MathUtils.random(0.6F, 1.4F);
        duration *= duration;
        targetScale = MathUtils.random(0.4F, 0.8F);
        startDur = duration;
        vX = MathUtils.random(-150F * Settings.scale, 150F * Settings.scale);
        vY = MathUtils.random(-100F * Settings.scale, 300F * Settings.scale);
        this.x = x + MathUtils.random(-170F * Settings.scale, 170F * Settings.scale);
        this.y = y + MathUtils.random(-220F * Settings.scale, 150F * Settings.scale);
        scale = 0.01F;
        img = setImg();
        rotateSpeed = MathUtils.random(-700F, 700F);
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion setImg()
    {
        switch(MathUtils.random(0, 5))
        {
        case 0: // '\0'
            return ImageMaster.DUST_1;

        case 1: // '\001'
            return ImageMaster.DUST_2;

        case 2: // '\002'
            return ImageMaster.DUST_3;

        case 3: // '\003'
            return ImageMaster.DUST_4;

        case 4: // '\004'
            return ImageMaster.DUST_5;
        }
        return ImageMaster.DUST_6;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        rotation += rotateSpeed * Gdx.graphics.getDeltaTime();
        scale = Interpolation.swing.apply(0.01F, targetScale, 1.0F - duration / startDur);
        if(duration < 0.5F)
            color.a = duration * 2.0F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, img.offsetX, img.offsetY, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setColor(new Color(color.r, color.g, color.b, color.a / 3F));
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, img.offsetX, img.offsetY, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private float startDur;
    private float targetScale;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float rotateSpeed;
}
