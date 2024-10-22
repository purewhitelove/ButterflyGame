// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamageImpactCurvyEffect.java

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

public class DamageImpactCurvyEffect extends AbstractGameEffect
{

    public DamageImpactCurvyEffect(float x, float y, Color color, boolean renderBehind)
    {
        pos = new Vector2();
        positions = new ArrayList();
        img = ImageMaster.STRIKE_LINE_2;
        duration = MathUtils.random(0.8F, 1.1F);
        startingDuration = duration;
        pos.x = x - (float)img.packedWidth / 2.0F;
        pos.y = y - (float)img.packedHeight / 2.0F;
        speed = MathUtils.random(400F, 900F) * Settings.scale;
        speedStart = speed;
        speedTarget = MathUtils.random(200F, 300F) * Settings.scale;
        this.color = color;
        this.renderBehind = renderBehind;
        rotation = MathUtils.random(360F);
        waveIntensity = MathUtils.random(5F, 30F);
        waveSpeed = MathUtils.random(-20F, 20F);
        speedTarget = MathUtils.random(0.1F, 0.5F);
    }

    public DamageImpactCurvyEffect(float x, float y)
    {
        this(x, y, Color.GOLDENROD, true);
    }

    public void update()
    {
        positions.add(pos);
        Vector2 tmp = new Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation));
        tmp.x *= speed * Gdx.graphics.getDeltaTime();
        tmp.y *= speed * Gdx.graphics.getDeltaTime();
        speed = Interpolation.pow2OutInverse.apply(speedStart, speedTarget, 1.0F - duration / startingDuration);
        pos.x += tmp.x;
        pos.y += tmp.y;
        rotation += MathUtils.cos(duration * waveSpeed) * waveIntensity * Gdx.graphics.getDeltaTime() * 60F;
        scale = ((Settings.scale * duration) / startingDuration) * 0.75F;
        super.update();
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        Color tmp = color.cpy();
        tmp.a = 0.25F;
        for(int i = positions.size() - 1; i > 0; i--)
        {
            sb.setColor(tmp);
            tmp.a *= 0.95F;
            if(tmp.a > 0.05F)
                sb.draw(img, ((Vector2)positions.get(i)).x, ((Vector2)positions.get(i)).y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 2.0F, scale * 2.0F, rotation);
        }

        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private Vector2 pos;
    private float speed;
    private float speedStart;
    private float speedTarget;
    private float waveIntensity;
    private float waveSpeed;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private ArrayList positions;
}
