// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SwirlyBloodEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

public class SwirlyBloodEffect extends AbstractGameEffect
{

    public SwirlyBloodEffect(float sX, float sY)
    {
        crs = new CatmullRomSpline();
        controlPoints = new ArrayList();
        points = new Vector2[60];
        currentSpeed = 0.0F;
        rotateClockwise = true;
        stopRotating = false;
        img = ImageMaster.GLOW_SPARK_2;
        pos = new Vector2(sX, sY);
        target = new Vector2(sX + 100F, sY + 100F);
        crs.controlPoints = new Vector2[1];
        rotateClockwise = MathUtils.randomBoolean();
        rotation = MathUtils.random(0, 359);
        controlPoints.clear();
        rotationRate = MathUtils.random(800F, 1000F) * Settings.scale;
        currentSpeed = rotationRate / 2.0F;
        color = new Color(MathUtils.random(0.3F, 1.0F), 0.3F, MathUtils.random(0.6F, 1.0F), 0.25F);
        duration = MathUtils.random(1.0F, 1.5F);
        renderBehind = MathUtils.randomBoolean();
        scale = 1E-005F;
    }

    public void update()
    {
        updateMovement();
    }

    private void updateMovement()
    {
        Vector2 tmp = new Vector2(pos.x - target.x, pos.y - target.y);
        tmp.nor();
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
        }
        tmp.setAngle(rotation);
        tmp.x *= Gdx.graphics.getDeltaTime() * currentSpeed;
        tmp.y *= Gdx.graphics.getDeltaTime() * currentSpeed;
        pos.sub(tmp);
        currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 1.5F;
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
        if(controlPoints.size() > 5)
            controlPoints.remove(0);
        duration -= Gdx.graphics.getDeltaTime();
        scale = Interpolation.pow2In.apply(2.0F, 0.01F, duration) * Settings.scale;
        if(duration < 0.5F)
            color.a = duration / 2.0F;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
        {
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, color.a / 2.0F));
            float tmpScale = scale * 2.0F;
            for(int i = points.length - 1; i > 0; i--)
                if(points[i] != null)
                {
                    sb.draw(img, points[i].x - (float)(img.packedWidth / 2), points[i].y - (float)(img.packedHeight / 2), (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, tmpScale, tmpScale, rotation);
                    tmpScale *= 0.975F;
                }

            sb.setBlendFunction(770, 1);
            sb.setColor(color);
            tmpScale = scale * 1.5F;
            for(int i = points.length - 1; i > 0; i--)
                if(points[i] != null)
                {
                    sb.draw(img, points[i].x - (float)(img.packedWidth / 2), points[i].y - (float)(img.packedHeight / 2), (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, tmpScale, tmpScale, rotation);
                    tmpScale *= 0.975F;
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
    private static final float VELOCITY_RAMP_RATE;
    private float rotation;
    private boolean rotateClockwise;
    private boolean stopRotating;
    private float rotationRate;

    static 
    {
        VELOCITY_RAMP_RATE = 3000F * Settings.scale;
    }
}
