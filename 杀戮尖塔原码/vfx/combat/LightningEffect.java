// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LightningEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
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
//            ImpactSparkEffect

public class LightningEffect extends AbstractGameEffect
{

    public LightningEffect(float x, float y)
    {
        img = null;
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/lightning");
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y;
        color = Color.WHITE.cpy();
        duration = 0.5F;
        startingDuration = 0.5F;
    }

    public void update()
    {
        if(duration == startingDuration)
        {
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.LOW, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, false);
            for(int i = 0; i < 15; i++)
                AbstractDungeon.topLevelEffectsQueue.add(new ImpactSparkEffect(x + MathUtils.random(-20F, 20F) * Settings.scale + 150F * Settings.scale, y + MathUtils.random(-20F, 20F) * Settings.scale));

        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        color.a = Interpolation.bounceIn.apply(duration * 2.0F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, 0.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
