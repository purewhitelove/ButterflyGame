// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HemokinesisParticle.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            DamageImpactLineEffect

public class HemokinesisParticle extends AbstractGameEffect
{

    public HemokinesisParticle(float sX, float sY, float tX, float tY, boolean facingLeft)
    {
        crs = new CatmullRomSpline();
        controlPoints = new ArrayList();
        points = new Vector2[60];
        currentSpeed = 0.0F;
        rotateClockwise = true;
        stopRotating = false;
        img = ImageMaster.GLOW_SPARK_2;
        pos = new Vector2(sX, sY);
        if(!facingLeft)
            target = new Vector2(tX + DST_THRESHOLD, tY);
        else
            target = new Vector2(tX - DST_THRESHOLD, tY);
        this.facingLeft = facingLeft;
        crs.controlPoints = new Vector2[1];
        rotateClockwise = MathUtils.randomBoolean();
        rotation = MathUtils.random(0, 359);
        controlPoints.clear();
        rotationRate = MathUtils.random(600F, 650F) * Settings.scale;
        currentSpeed = 1000F * Settings.scale;
        color = new Color(1.0F, 0.0F, 0.02F, 0.6F);
        duration = 0.7F;
        scale = 1.0F * Settings.scale;
        renderBehind = MathUtils.randomBoolean();
    }

    public void update()
    {
        updateMovement();
    }

    private void updateMovement()
    {
        Vector2 tmp = new Vector2(pos.x - target.x, pos.y - target.y);
        tmp.nor();
        float targetAngle = tmp.angle();
        rotationRate += Gdx.graphics.getDeltaTime() * 2000F;
        scale += Gdx.graphics.getDeltaTime() * 1.0F * Settings.scale;
        if(!stopRotating)
        {
            if(rotateClockwise)
            {
                rotation += Gdx.graphics.getDeltaTime() * rotationRate;
            } else
            {
                rotation -= Gdx.graphics.getDeltaTime() * rotationRate;
                if(rotation < 0.0F)
                    rotation += 360F;
            }
            rotation = rotation % 360F;
            if(!stopRotating && Math.abs(rotation - targetAngle) < Gdx.graphics.getDeltaTime() * rotationRate)
            {
                rotation = targetAngle;
                stopRotating = true;
            }
        }
        tmp.setAngle(rotation);
        tmp.x *= Gdx.graphics.getDeltaTime() * currentSpeed;
        tmp.y *= Gdx.graphics.getDeltaTime() * currentSpeed;
        pos.sub(tmp);
        if(stopRotating)
            currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 3F;
        else
            currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 1.5F;
        if(currentSpeed > MAX_VELOCITY)
            currentSpeed = MAX_VELOCITY;
        if(target.dst(pos) < DST_THRESHOLD)
        {
            for(int i = 0; i < 5; i++)
                if(facingLeft)
                    AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(target.x + DST_THRESHOLD, target.y));
                else
                    AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(target.x - DST_THRESHOLD, target.y));

            CardCrawlGame.sound.playAV("BLUNT_HEAVY", MathUtils.random(0.6F, 0.9F), 0.5F);
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
            isDone = true;
        }
        if(!controlPoints.isEmpty())
        {
            if(!((Vector2)controlPoints.get(0)).equals(pos))
                controlPoints.add(pos.cpy());
        } else
        {
            controlPoints.add(pos.cpy());
        }
        if(controlPoints.size() > 3)
        {
            Vector2 vec2Array[] = new Vector2[0];
            crs.set((com.badlogic.gdx.math.Vector[])controlPoints.toArray(vec2Array), false);
            for(int i = 0; i < 60; i++)
            {
                points[i] = new Vector2();
                crs.valueAt(points[i], (float)i / 59F);
            }

        }
        if(controlPoints.size() > 10)
            controlPoints.remove(0);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
        {
            sb.setColor(Color.BLACK);
            float scaleCpy = scale;
            for(int i = points.length - 1; i > 0; i--)
                if(points[i] != null)
                {
                    sb.draw(img, points[i].x - (float)(img.packedWidth / 2), points[i].y - (float)(img.packedHeight / 2), (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scaleCpy * 1.5F, scaleCpy * 1.5F, rotation);
                    scaleCpy *= 0.98F;
                }

            sb.setBlendFunction(770, 1);
            sb.setColor(color);
            scaleCpy = scale;
            for(int i = points.length - 1; i > 0; i--)
                if(points[i] != null)
                {
                    sb.draw(img, points[i].x - (float)(img.packedWidth / 2), points[i].y - (float)(img.packedHeight / 2), (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scaleCpy, scaleCpy, rotation);
                    scaleCpy *= 0.98F;
                }

            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private CatmullRomSpline crs;
    private ArrayList controlPoints;
    private static final int TRAIL_ACCURACY = 60;
    private Vector2 points[];
    private Vector2 pos;
    private Vector2 target;
    private float currentSpeed;
    private static final float MAX_VELOCITY;
    private static final float VELOCITY_RAMP_RATE;
    private static final float DST_THRESHOLD;
    private float rotation;
    private boolean rotateClockwise;
    private boolean stopRotating;
    private boolean facingLeft;
    private float rotationRate;

    static 
    {
        MAX_VELOCITY = 4000F * Settings.scale;
        VELOCITY_RAMP_RATE = 3000F * Settings.scale;
        DST_THRESHOLD = 42F * Settings.scale;
    }
}
