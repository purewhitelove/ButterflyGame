// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StanceAuraEffect.java

package com.megacrit.cardcrawl.vfx.stance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class StanceAuraEffect extends AbstractGameEffect
{

    public StanceAuraEffect(String stanceId)
    {
        img = ImageMaster.EXHAUST_L;
        duration = 2.0F;
        scale = MathUtils.random(2.7F, 2.5F) * Settings.scale;
        if(stanceId.equals("Wrath"))
            color = new Color(MathUtils.random(0.6F, 0.7F), MathUtils.random(0.0F, 0.1F), MathUtils.random(0.1F, 0.2F), 0.0F);
        else
        if(stanceId.equals("Calm"))
            color = new Color(MathUtils.random(0.5F, 0.55F), MathUtils.random(0.6F, 0.7F), 1.0F, 0.0F);
        else
            color = new Color(MathUtils.random(0.6F, 0.7F), MathUtils.random(0.0F, 0.1F), MathUtils.random(0.6F, 0.7F), 0.0F);
        x = AbstractDungeon.player.hb.cX + MathUtils.random(-AbstractDungeon.player.hb.width / 16F, AbstractDungeon.player.hb.width / 16F);
        y = AbstractDungeon.player.hb.cY + MathUtils.random(-AbstractDungeon.player.hb.height / 16F, AbstractDungeon.player.hb.height / 12F);
        x -= (float)img.packedWidth / 2.0F;
        y -= (float)img.packedHeight / 2.0F;
        switcher = !switcher;
        renderBehind = true;
        rotation = MathUtils.random(360F);
        if(switcher)
        {
            renderBehind = true;
            vY = MathUtils.random(0.0F, 40F);
        } else
        {
            renderBehind = false;
            vY = MathUtils.random(0.0F, -40F);
        }
    }

    public void update()
    {
        if(duration > 1.0F)
            color.a = Interpolation.fade.apply(0.3F, 0.0F, duration - 1.0F);
        else
            color.a = Interpolation.fade.apply(0.0F, 0.3F, duration);
        rotation += Gdx.graphics.getDeltaTime() * vY;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    public static boolean switcher = true;

}
