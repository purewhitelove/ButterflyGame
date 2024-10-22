// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PetalEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class PetalEffect extends AbstractGameEffect
{

    public PetalEffect()
    {
        frame = 0;
        animTimer = 0.05F;
        x = MathUtils.random(100F * Settings.scale, 1820F * Settings.scale);
        y = (float)Settings.HEIGHT + MathUtils.random(20F, 300F) * Settings.scale;
        frame = MathUtils.random(8);
        rotation = MathUtils.random(-10F, 10F);
        scale = MathUtils.random(1.0F, 2.5F);
        scaleY = MathUtils.random(1.0F, 1.2F);
        if(scale < 1.5F)
            renderBehind = true;
        vY = MathUtils.random(200F, 300F) * scale * Settings.scale;
        vX = MathUtils.random(-100F, 100F) * scale * Settings.scale;
        scale *= Settings.scale;
        if(MathUtils.randomBoolean())
            rotation += 180F;
        color = new Color(1.0F, MathUtils.random(0.7F, 0.9F), MathUtils.random(0.7F, 0.9F), 1.0F);
        duration = 4F;
    }

    public void update()
    {
        y -= vY * Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime();
        animTimer -= Gdx.graphics.getDeltaTime() / scale;
        if(animTimer < 0.0F)
        {
            animTimer += 0.05F;
            frame++;
            if(frame > 11)
                frame = 0;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < 1.0F)
            color.a = duration;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        switch(frame)
        {
        case 0: // '\0'
            renderImg(sb, ImageMaster.PETAL_VFX[0], false, false);
            break;

        case 1: // '\001'
            renderImg(sb, ImageMaster.PETAL_VFX[1], false, false);
            break;

        case 2: // '\002'
            renderImg(sb, ImageMaster.PETAL_VFX[2], false, false);
            break;

        case 3: // '\003'
            renderImg(sb, ImageMaster.PETAL_VFX[3], false, false);
            break;

        case 4: // '\004'
            renderImg(sb, ImageMaster.PETAL_VFX[2], true, true);
            break;

        case 5: // '\005'
            renderImg(sb, ImageMaster.PETAL_VFX[1], true, true);
            break;

        case 6: // '\006'
            renderImg(sb, ImageMaster.PETAL_VFX[0], true, true);
            break;

        case 7: // '\007'
            renderImg(sb, ImageMaster.PETAL_VFX[1], true, true);
            break;

        case 8: // '\b'
            renderImg(sb, ImageMaster.PETAL_VFX[2], true, true);
            break;

        case 9: // '\t'
            renderImg(sb, ImageMaster.PETAL_VFX[3], true, true);
            break;

        case 10: // '\n'
            renderImg(sb, ImageMaster.PETAL_VFX[2], false, false);
            break;

        case 11: // '\013'
            renderImg(sb, ImageMaster.PETAL_VFX[1], false, false);
            break;
        }
    }

    public void dispose()
    {
    }

    private void renderImg(SpriteBatch sb, Texture img, boolean flipH, boolean flipV)
    {
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, 16F, 16F, 32F, 32F, scale, scale * scaleY, rotation, 0, 0, 32, 32, flipH, flipV);
        sb.setBlendFunction(770, 771);
    }

    private float x;
    private float y;
    private float vY;
    private float vX;
    private float scaleY;
    private int frame;
    private float animTimer;
    private static final int W = 32;
}
