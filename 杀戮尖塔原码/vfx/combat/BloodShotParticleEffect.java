// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BloodShotParticleEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            AdditiveSlashImpactEffect

public class BloodShotParticleEffect extends AbstractGameEffect
{

    public BloodShotParticleEffect(float sX, float sY, float tX, float tY)
    {
        activated = false;
        img = ImageMaster.GLOW_SPARK_2;
        this.sX = sX + MathUtils.random(-90F, 90F) * Settings.scale;
        this.sY = sY + MathUtils.random(-90F, 90F) * Settings.scale;
        this.tX = tX + MathUtils.random(-50F, 50F) * Settings.scale;
        this.tY = tY + MathUtils.random(-50F, 50F) * Settings.scale;
        vX = this.sX + MathUtils.random(-200F, 200F) * Settings.scale;
        vY = this.sY + MathUtils.random(-200F, 200F) * Settings.scale;
        x = this.sX;
        y = this.sY;
        scale = 0.01F;
        startingDuration = 0.8F;
        duration = startingDuration;
        renderBehind = MathUtils.randomBoolean(0.2F);
        color = new Color(1.0F, 0.1F, MathUtils.random(0.2F, 0.5F), 1.0F);
    }

    public void update()
    {
        if(duration > startingDuration / 2.0F)
        {
            scale = Interpolation.pow3In.apply(2.5F, startingDuration / 2.0F, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F)) * Settings.scale;
            x = Interpolation.swingIn.apply(sX, vX, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
            y = Interpolation.swingIn.apply(sY, vY, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
        } else
        {
            scale = Interpolation.pow3Out.apply(2.0F, 2.5F, duration / (startingDuration / 2.0F)) * Settings.scale;
            x = Interpolation.swingOut.apply(tX, vX, duration / (startingDuration / 2.0F));
            y = Interpolation.swingOut.apply(tY, vY, duration / (startingDuration / 2.0F));
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < startingDuration / 2.0F && !activated)
        {
            activated = true;
            sX = x;
            sY = y;
        }
        if(duration < 0.0F)
        {
            AbstractDungeon.effectsQueue.add(new AdditiveSlashImpactEffect(tX, tY, color.cpy()));
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, MathUtils.randomBoolean());
            isDone = true;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.BLACK);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 2.0F, scale * 2.0F, rotation);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float x;
    private float y;
    private float vY;
    private float vX;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean activated;
}
