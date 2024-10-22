// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlyingOrbEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

public class FlyingOrbEffect extends AbstractGameEffect
{

    public FlyingOrbEffect(float x, float y)
    {
        crs = new CatmullRomSpline();
        controlPoints = new ArrayList();
        points = new Vector2[60];
        currentSpeed = 0.0F;
        rotateClockwise = true;
        stopRotating = false;
        img = ImageMaster.GLOW_SPARK_2;
        pos = new Vector2(x, y);
        target = new Vector2(AbstractDungeon.player.hb.cX - DST_THRESHOLD / 3F - 100F * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50F, 50F) * Settings.scale);
        crs.controlPoints = new Vector2[1];
        rotateClockwise = MathUtils.randomBoolean();
        rotation = MathUtils.random(0, 359);
        controlPoints.clear();
        rotationRate = MathUtils.random(300F, 350F) * Settings.scale;
        currentSpeed = START_VELOCITY * MathUtils.random(0.2F, 1.0F);
        color = new Color(1.0F, 0.15F, 0.2F, 0.4F);
        duration = 1.3F;
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
        rotationRate += Gdx.graphics.getDeltaTime() * 700F;
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
            if(!stopRotating)
                if(target.dst(pos) < HOME_IN_THRESHOLD)
                {
                    rotation = targetAngle;
                    stopRotating = true;
                } else
                if(Math.abs(rotation - targetAngle) < Gdx.graphics.getDeltaTime() * rotationRate)
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
        if(target.x < (float)Settings.WIDTH / 2.0F && pos.x < 0.0F || target.x > (float)Settings.WIDTH / 2.0F && pos.x > (float)Settings.WIDTH || target.dst(pos) < DST_THRESHOLD)
        {
            isDone = true;
            return;
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
            sb.setBlendFunction(770, 1);
            sb.setColor(color);
            float scale = Settings.scale * 1.5F;
            for(int i = points.length - 1; i > 0; i--)
                if(points[i] != null)
                {
                    sb.draw(img, points[i].x - (float)(img.packedWidth / 2), points[i].y - (float)(img.packedHeight / 2), (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
                    scale *= 0.975F;
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
    private static final float START_VELOCITY;
    private static final float MAX_VELOCITY;
    private static final float VELOCITY_RAMP_RATE;
    private static final float DST_THRESHOLD;
    private static final float HOME_IN_THRESHOLD;
    private float rotation;
    private boolean rotateClockwise;
    private boolean stopRotating;
    private float rotationRate;

    static 
    {
        START_VELOCITY = 100F * Settings.scale;
        MAX_VELOCITY = 5000F * Settings.scale;
        VELOCITY_RAMP_RATE = 2000F * Settings.scale;
        DST_THRESHOLD = 36F * Settings.scale;
        HOME_IN_THRESHOLD = 36F * Settings.scale;
    }
}
