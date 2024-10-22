// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FadeWipeParticle.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class FadeWipeParticle extends AbstractGameEffect
{

    public FadeWipeParticle()
    {
        img = ImageMaster.SCENE_TRANSITION_FADER;
        flatImg = ImageMaster.WHITE_SQUARE_IMG;
        color = AbstractDungeon.fadeColor.cpy();
        color.a = 0.0F;
        duration = 1.0F;
        startingDuration = 1.0F;
        y = Settings.HEIGHT + img.packedHeight;
        delayTimer = 0.1F;
    }

    public void update()
    {
        if(delayTimer > 0.0F)
        {
            delayTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            duration = 0.0F;
            lerpTimer += Gdx.graphics.getDeltaTime();
            if(lerpTimer > 0.5F)
            {
                color.a = MathHelper.slowColorLerpSnap(color.a, 0.0F);
                if(color.a == 0.0F)
                    isDone = true;
            }
        } else
        {
            color.a = Interpolation.pow5In.apply(1.0F, 0.0F, duration / 1.0F);
            y = Interpolation.pow3In.apply(0.0F - (float)img.packedHeight, Settings.HEIGHT + img.packedHeight, duration / 1.0F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, 0.0F, y, Settings.WIDTH, img.packedHeight);
        sb.draw(flatImg, 0.0F, (y + (float)img.packedHeight) - 1.0F * Settings.scale, Settings.WIDTH, Settings.HEIGHT);
    }

    public void dispose()
    {
    }

    private static final float DUR = 1F;
    private float y;
    private float lerpTimer;
    private float delayTimer;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private Texture flatImg;
}
