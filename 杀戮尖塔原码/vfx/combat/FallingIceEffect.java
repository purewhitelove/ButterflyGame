// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FallingIceEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            IceShatterEffect

public class FallingIceEffect extends AbstractGameEffect
{

    public FallingIceEffect(int frostCount, boolean flipped)
    {
        this.frostCount = 0;
        this.frostCount = frostCount;
        switch(MathUtils.random(2))
        {
        case 0: // '\0'
            img = ImageMaster.FROST_ORB_RIGHT;
            break;

        case 1: // '\001'
            img = ImageMaster.FROST_ORB_LEFT;
            break;

        default:
            img = ImageMaster.FROST_ORB_MIDDLE;
            break;
        }
        waitTimer = MathUtils.random(0.0F, 0.5F);
        if(flipped)
        {
            x = MathUtils.random(420F, 1420F) * Settings.scale - 48F;
            vX = MathUtils.random(-600F, -900F);
            vX += (float)frostCount * 5F;
        } else
        {
            x = MathUtils.random(500F, 1500F) * Settings.scale - 48F;
            vX = MathUtils.random(600F, 900F);
            vX -= (float)frostCount * 5F;
        }
        y = ((float)Settings.HEIGHT + MathUtils.random(100F, 300F)) - 48F;
        vY = MathUtils.random(2500F, 4000F);
        vY -= (float)frostCount * 10F;
        vY *= Settings.scale;
        vX *= Settings.scale;
        duration = 2.0F;
        scale = MathUtils.random(1.0F, 1.5F);
        scale += (float)frostCount * 0.04F;
        vX *= scale;
        scale *= Settings.scale;
        color = new Color(0.9F, 0.9F, 1.0F, MathUtils.random(0.9F, 1.0F));
        Vector2 derp = new Vector2(vX, vY);
        if(flipped)
            rotation = (derp.angle() + 225F) - (float)frostCount / 3F;
        else
            rotation = (derp.angle() - 45F) + (float)frostCount / 3F;
        renderBehind = MathUtils.randomBoolean();
        floorY = AbstractDungeon.floorY + MathUtils.random(-200F, 50F) * Settings.scale;
    }

    public void update()
    {
        waitTimer -= Gdx.graphics.getDeltaTime();
        if(waitTimer > 0.0F)
            return;
        x += vX * Gdx.graphics.getDeltaTime();
        y -= vY * Gdx.graphics.getDeltaTime();
        if(y < floorY)
        {
            float pitch = 0.8F;
            pitch -= (float)frostCount * 0.025F;
            pitch += MathUtils.random(-0.2F, 0.2F);
            CardCrawlGame.sound.playA("ORB_FROST_EVOKE", pitch);
            for(int i = 0; i < 4; i++)
                AbstractDungeon.effectsQueue.add(new IceShatterEffect(x, y));

            isDone = true;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(waitTimer < 0.0F)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(color);
            sb.draw(img, x, y, 48F, 48F, 96F, 96F, scale, scale, rotation, 0, 0, 96, 96, false, false);
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose()
    {
    }

    private float waitTimer;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float floorY;
    private Texture img;
    private int frostCount;
}
