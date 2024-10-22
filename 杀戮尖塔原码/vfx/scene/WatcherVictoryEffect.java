// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WatcherVictoryEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WatcherVictoryEffect extends AbstractGameEffect
{

    public WatcherVictoryEffect()
    {
        renderBehind = true;
        img = ImageMaster.EYE_ANIM_0;
        x = (float)Settings.WIDTH / 2.0F - (float)img.packedWidth / 2.0F;
        y = (float)Settings.HEIGHT / 2.0F - (float)img.packedHeight / 2.0F;
        scale = 1.5F * Settings.scale;
        color = new Color(0.0F, 0.0F, 0.0F, 0.0F);
    }

    public void update()
    {
        color.a = MathHelper.slowColorLerpSnap(color.a, 1.0F);
        duration += Gdx.graphics.getDeltaTime();
        rotation += 5F * Gdx.graphics.getDeltaTime();
    }

    private void renderHelper(SpriteBatch sb, float offsetX, float offsetY, float rotation, Color color, float scaleMod)
    {
        sb.setColor(color);
        offsetX *= Settings.scale;
        offsetY *= Settings.scale;
        sb.draw(getImg(this.rotation + rotation + offsetX / 100F), x, y, (float)img.packedWidth / 2.0F - offsetX, (float)img.packedHeight / 2.0F - offsetY, img.packedWidth, img.packedHeight, scale, scale * 2.0F, this.rotation + rotation);
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getImg(float input)
    {
        input %= 10F;
        if(input < 0.5F)
            return ImageMaster.EYE_ANIM_1;
        if(input < 1.2F)
            return ImageMaster.EYE_ANIM_2;
        if(input < 2.0F)
            return ImageMaster.EYE_ANIM_3;
        if(input < 3F)
            return ImageMaster.EYE_ANIM_4;
        if(input < 4.2F)
            return ImageMaster.EYE_ANIM_5;
        if(input < 6F)
            return ImageMaster.EYE_ANIM_6;
        if(input < 7.5F)
            return ImageMaster.EYE_ANIM_5;
        if(input < 8.5F)
            return ImageMaster.EYE_ANIM_4;
        if(input < 9.3F)
            return ImageMaster.EYE_ANIM_3;
        else
            return ImageMaster.EYE_ANIM_2;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        float angle = 0.0F;
        for(int i = 0; i < 24; i++)
        {
            color.r = 0.9F;
            color.g = 0.46F + (float)i * 0.01F;
            color.b = 0.3F + (float)(12 - i) * 0.05F;
            renderHelper(sb, -760F, -760F, angle, color, 1.0F);
            renderHelper(sb, -630F, -630F, angle, color, 1.0F);
            renderHelper(sb, -510F, -510F, angle, color, 1.0F);
            renderHelper(sb, -400F, -400F, angle, color, 1.0F);
            renderHelper(sb, -300F, -300F, angle, color, 1.0F);
            renderHelper(sb, -220F, -220F, angle, color, 1.0F);
            renderHelper(sb, -170F, -170F, angle, color, 1.0F);
            renderHelper(sb, -130F, -130F, angle, color, 1.0F);
            renderHelper(sb, -100F, -100F, angle, color, 1.0F);
            renderHelper(sb, 760F, -760F, angle, color, 1.0F);
            renderHelper(sb, 630F, -630F, angle, color, 1.0F);
            renderHelper(sb, 510F, -510F, angle, color, 1.0F);
            renderHelper(sb, 400F, -400F, angle, color, 1.0F);
            renderHelper(sb, 300F, -300F, angle, color, 1.0F);
            renderHelper(sb, 220F, -220F, angle, color, 1.0F);
            renderHelper(sb, 170F, -170F, angle, color, 1.0F);
            renderHelper(sb, 130F, -130F, angle, color, 1.0F);
            renderHelper(sb, 100F, -100F, angle, color, 1.0F);
            angle += 15F;
        }

        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
