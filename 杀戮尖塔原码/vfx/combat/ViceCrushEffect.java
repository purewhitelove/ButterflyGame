// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ViceCrushEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import java.util.ArrayList;

public class ViceCrushEffect extends AbstractGameEffect
{

    public ViceCrushEffect(float x, float y)
    {
        impactHook = false;
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/weightyImpact");
        startX = x - 300F * Settings.scale - (float)img.packedWidth / 2.0F;
        startX2 = (x + 300F * Settings.scale) - (float)img.packedWidth / 2.0F;
        targetX = x - 120F * Settings.scale - (float)img.packedWidth / 2.0F;
        targetX2 = (x + 120F * Settings.scale) - (float)img.packedWidth / 2.0F;
        this.x = startX;
        x2 = startX2;
        this.y = y - (float)img.packedHeight / 2.0F;
        scale = 1.1F;
        duration = 0.7F;
        startingDuration = 0.7F;
        rotation = 90F;
        color = Color.PURPLE.cpy();
        color.a = 0.0F;
    }

    public void update()
    {
        if(duration == startingDuration)
            CardCrawlGame.sound.playA("ATTACK_MAGIC_FAST_3", -0.4F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < 0.2F)
        {
            if(!impactHook)
            {
                impactHook = true;
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PURPLE));
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, true);
            }
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration * 5F);
        } else
        {
            color.a = Interpolation.fade.apply(1.0F, 0.0F, duration / startingDuration);
        }
        scale += 1.1F * Gdx.graphics.getDeltaTime();
        x = Interpolation.fade.apply(targetX, startX, duration / startingDuration);
        x2 = Interpolation.fade.apply(targetX2, startX2, duration / startingDuration);
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(0.5F, 0.5F, 0.9F, color.a));
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * scale * Settings.scale * 0.5F, scale * Settings.scale * (duration + 0.8F), rotation);
        sb.draw(img, x2, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * scale * Settings.scale * 0.5F, scale * Settings.scale * (duration + 0.8F), rotation - 180F);
        sb.setColor(new Color(0.7F, 0.5F, 0.9F, color.a));
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * scale * Settings.scale * 0.75F, scale * Settings.scale * (duration + 0.8F), rotation);
        sb.draw(img, x2, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * scale * Settings.scale * 0.75F, scale * Settings.scale * (duration + 0.8F), rotation - 180F);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * scale * Settings.scale, scale * Settings.scale * (duration + 1.0F), rotation);
        sb.draw(img, x2, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * scale * Settings.scale, scale * Settings.scale * (duration + 1.0F), rotation - 180F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean impactHook;
    private float x;
    private float x2;
    private float y;
    private float startX;
    private float startX2;
    private float targetX;
    private float targetX2;
}
