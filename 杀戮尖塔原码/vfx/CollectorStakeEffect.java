// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CollectorStakeEffect.java

package com.megacrit.cardcrawl.vfx;

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
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class CollectorStakeEffect extends AbstractGameEffect
{

    public CollectorStakeEffect(float x, float y)
    {
        shownSlash = false;
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/stake");
        float randomAngle = 0.01745329F * MathUtils.random(-50F, 230F);
        this.x = MathUtils.cos(randomAngle) * MathUtils.random(200F, 600F) * Settings.scale + x;
        this.y = MathUtils.sin(randomAngle) * MathUtils.random(200F, 500F) * Settings.scale + y;
        duration = 1.0F;
        scale = 0.01F;
        targetScale = MathUtils.random(0.4F, 1.1F);
        targetAngle = MathUtils.atan2(y - this.y, x - this.x) * 57.29578F + 90F;
        startingAngle = MathUtils.random(0.0F, 360F);
        rotation = startingAngle;
        this.x -= img.packedWidth / 2;
        this.y -= img.packedHeight / 2;
        sX = this.x;
        sY = this.y;
        tX = x - (float)(img.packedWidth / 2);
        tY = y - (float)(img.packedHeight / 2);
        color = new Color(MathUtils.random(0.5F, 1.0F), MathUtils.random(0.0F, 0.4F), MathUtils.random(0.5F, 1.0F), 0.0F);
    }

    public void update()
    {
        rotation = Interpolation.elasticIn.apply(targetAngle, startingAngle, duration);
        if(duration > 0.5F)
        {
            scale = Interpolation.elasticIn.apply(targetScale, targetScale * 10F, (duration - 0.5F) * 2.0F) * Settings.scale;
            color.a = Interpolation.fade.apply(0.6F, 0.0F, (duration - 0.5F) * 2.0F);
        } else
        {
            x = Interpolation.exp10Out.apply(tX, sX, duration * 2.0F);
            y = Interpolation.exp10Out.apply(tY, sY, duration * 2.0F);
        }
        if(duration < 0.05F && !shownSlash)
        {
            AbstractDungeon.effectsQueue.add(new AdditiveSlashImpactEffect(tX + (float)img.packedWidth / 2.0F, tY + (float)img.packedHeight / 2.0F, color.cpy()));
            shownSlash = true;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, MathUtils.randomBoolean());
            CardCrawlGame.sound.play("ATTACK_FAST", 0.2F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(1.0F, 1.2F), scale * MathUtils.random(1.0F, 1.2F), rotation);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.9F, 1.1F), scale * MathUtils.random(0.9F, 1.1F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float targetAngle;
    private float startingAngle;
    private float targetScale;
    private boolean shownSlash;
}
