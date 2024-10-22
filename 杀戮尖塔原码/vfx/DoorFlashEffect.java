// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DoorFlashEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class DoorFlashEffect extends AbstractGameEffect
{

    public DoorFlashEffect(Texture img, boolean eventVersion)
    {
        yOffset = 0.0F;
        this.img = img;
        startingDuration = 1.3F;
        duration = startingDuration;
        color = Color.WHITE.cpy();
        scale = Settings.scale * 2.0F;
        if(eventVersion)
            yOffset = -48F * Settings.scale;
        else
            yOffset = 0.0F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            duration = 0.0F;
            isDone = true;
        }
        color.a = Interpolation.fade.apply(0.0F, 1.0F, duration / startingDuration);
        scale = Interpolation.swingIn.apply(0.95F, 1.3F, duration / startingDuration) * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, (float)Settings.WIDTH / 2.0F - 960F, ((float)Settings.HEIGHT / 2.0F - 600F) + yOffset, 960F, 600F, 1920F, 1200F, scale, scale, 0.0F, 0, 0, 1920, 1200, false, false);
        sb.draw(img, (float)Settings.WIDTH / 2.0F - 960F, ((float)Settings.HEIGHT / 2.0F - 600F) + yOffset, 960F, 600F, 1920F, 1200F, scale * 1.1F, scale * 1.1F, 0.0F, 0, 0, 1920, 1200, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
        img.dispose();
    }

    private Texture img;
    private float yOffset;
}
