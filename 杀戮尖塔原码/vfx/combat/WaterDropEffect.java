// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WaterDropEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            WaterSplashParticleEffect

public class WaterDropEffect extends AbstractGameEffect
{

    public WaterDropEffect(float x, float y)
    {
        frame = 0;
        animTimer = 0.1F;
        this.x = x;
        this.y = y;
        frame = 0;
        scale = MathUtils.random(2.5F, 3F) * Settings.scale;
        rotation = 0.0F;
        scale *= Settings.scale;
        color = new Color(1.0F, 0.05F, 0.05F, 0.0F);
    }

    public void update()
    {
        color.a = MathHelper.fadeLerpSnap(color.a, 1.0F);
        animTimer -= Gdx.graphics.getDeltaTime();
        if(animTimer < 0.0F)
        {
            animTimer += 0.1F;
            frame++;
            if(frame == 3)
            {
                for(int i = 0; i < 3; i++)
                    AbstractDungeon.effectsQueue.add(new WaterSplashParticleEffect(x, y));

            }
            if(frame > 5)
            {
                frame = 5;
                isDone = true;
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        switch(frame)
        {
        case 0: // '\0'
            sb.draw(ImageMaster.WATER_DROP_VFX[0], x - 32F, (y - 32F) + 40F * Settings.scale, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
            break;

        case 1: // '\001'
            sb.draw(ImageMaster.WATER_DROP_VFX[1], x - 32F, (y - 32F) + 20F * Settings.scale, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
            break;

        case 2: // '\002'
            sb.draw(ImageMaster.WATER_DROP_VFX[2], x - 32F, (y - 32F) + 10F * Settings.scale, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
            break;

        case 3: // '\003'
            sb.draw(ImageMaster.WATER_DROP_VFX[3], x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
            break;

        case 4: // '\004'
            sb.draw(ImageMaster.WATER_DROP_VFX[4], x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
            break;

        case 5: // '\005'
            sb.draw(ImageMaster.WATER_DROP_VFX[5], x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
            break;
        }
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private int frame;
    private float animTimer;
    private static final int W = 64;
}
