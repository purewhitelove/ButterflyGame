// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WeightyImpactEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            DamageImpactCurvyEffect

public class WeightyImpactEffect extends AbstractGameEffect
{

    public WeightyImpactEffect(float x, float y)
    {
        this(x, y, new Color(1.0F, 1.0F, 0.1F, 0.0F));
    }

    public WeightyImpactEffect(float x, float y, Color newColor)
    {
        impactHook = false;
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/weightyImpact");
        scale = Settings.scale;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = (float)Settings.HEIGHT - (float)img.packedHeight / 2.0F;
        duration = 1.0F;
        targetY = y - 180F * Settings.scale;
        rotation = MathUtils.random(-1F, 1.0F);
        color = newColor;
    }

    public void update()
    {
        y = Interpolation.fade.apply(Settings.HEIGHT, targetY, 1.0F - duration / 1.0F);
        scale += Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            CardCrawlGame.sound.playA("ATTACK_IRON_2", -0.5F);
        } else
        if(duration < 0.2F)
        {
            if(!impactHook)
            {
                impactHook = true;
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.ORANGE));
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, true);
                for(int i = 0; i < 5; i++)
                    AbstractDungeon.effectsQueue.add(new DamageImpactCurvyEffect(x + (float)img.packedWidth / 2.0F, y + (float)img.packedWidth / 2.0F));

                for(int i = 0; i < 30; i++)
                    AbstractDungeon.effectsQueue.add(new UpgradeShineParticleEffect(x + MathUtils.random(-100F, 100F) * Settings.scale + (float)img.packedWidth / 2.0F, y + MathUtils.random(-50F, 120F) * Settings.scale + (float)img.packedHeight / 2.0F));

            }
            color.a = Interpolation.fade.apply(0.0F, 0.5F, 0.2F / duration);
        } else
        {
            color.a = Interpolation.pow2Out.apply(0.6F, 0.0F, duration / 1.0F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        color.g = 1.0F;
        sb.setColor(color);
        sb.draw(img, x, y + 140F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, (float)img.packedHeight * (duration + 0.2F) * 5F, scale * MathUtils.random(0.99F, 1.01F) * 0.3F, scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (duration + 0.8F), rotation);
        color.g = 0.6F;
        sb.setColor(color);
        sb.draw(img, x, y + 40F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, (float)img.packedHeight * (duration + 0.2F) * 5F, scale * MathUtils.random(0.99F, 1.01F) * 0.7F, scale * MathUtils.random(0.99F, 1.01F) * 1.3F * (duration + 0.8F), rotation);
        color.g = 0.2F;
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, (float)img.packedHeight * (duration + 0.2F) * 5F, scale * MathUtils.random(0.99F, 1.01F), scale * MathUtils.random(0.99F, 1.01F) * (duration + 0.8F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 1F;
    private float x;
    private float y;
    private float targetY;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean impactHook;
}
