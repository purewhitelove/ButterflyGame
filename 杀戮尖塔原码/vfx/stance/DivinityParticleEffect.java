// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DivinityParticleEffect.java

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
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DivinityParticleEffect extends AbstractGameEffect
{

    public DivinityParticleEffect()
    {
        scale = Settings.scale;
        img = ImageMaster.EYE_ANIM_0;
        scale = MathUtils.random(1.0F, 1.5F);
        startingDuration = scale + 0.8F;
        duration = startingDuration;
        scale *= Settings.scale;
        dur_div2 = duration / 2.0F;
        color = new Color(MathUtils.random(0.8F, 1.0F), MathUtils.random(0.5F, 0.7F), MathUtils.random(0.8F, 1.0F), 0.0F);
        x = AbstractDungeon.player.hb.cX + MathUtils.random(-AbstractDungeon.player.hb.width / 2.0F - 50F * Settings.scale, AbstractDungeon.player.hb.width / 2.0F + 50F * Settings.scale);
        y = AbstractDungeon.player.hb.cY + MathUtils.random(-AbstractDungeon.player.hb.height / 2.0F + 10F * Settings.scale, AbstractDungeon.player.hb.height / 2.0F - 20F * Settings.scale);
        renderBehind = MathUtils.randomBoolean();
        rotation = MathUtils.random(12F, 6F);
        if(x > AbstractDungeon.player.hb.cX)
            rotation = -rotation;
        x -= (float)img.packedWidth / 2.0F;
        y -= (float)img.packedHeight / 2.0F;
    }

    public void update()
    {
        if(duration > dur_div2)
            color.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - dur_div2) / dur_div2);
        else
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration / dur_div2);
        if(duration > startingDuration * 0.85F)
        {
            vY = 12F * Settings.scale;
            img = ImageMaster.EYE_ANIM_0;
        } else
        if(duration > startingDuration * 0.8F)
        {
            vY = 8F * Settings.scale;
            img = ImageMaster.EYE_ANIM_1;
        } else
        if(duration > startingDuration * 0.75F)
        {
            vY = 4F * Settings.scale;
            img = ImageMaster.EYE_ANIM_2;
        } else
        if(duration > startingDuration * 0.7F)
        {
            vY = 3F * Settings.scale;
            img = ImageMaster.EYE_ANIM_3;
        } else
        if(duration > startingDuration * 0.65F)
            img = ImageMaster.EYE_ANIM_4;
        else
        if(duration > startingDuration * 0.6F)
            img = ImageMaster.EYE_ANIM_5;
        else
        if(duration > startingDuration * 0.55F)
            img = ImageMaster.EYE_ANIM_6;
        else
        if(duration > startingDuration * 0.38F)
            img = ImageMaster.EYE_ANIM_5;
        else
        if(duration > startingDuration * 0.3F)
            img = ImageMaster.EYE_ANIM_4;
        else
        if(duration > startingDuration * 0.25F)
        {
            vY = 3F * Settings.scale;
            img = ImageMaster.EYE_ANIM_3;
        } else
        if(duration > startingDuration * 0.2F)
        {
            vY = 4F * Settings.scale;
            img = ImageMaster.EYE_ANIM_2;
        } else
        if(duration > startingDuration * 0.15F)
        {
            vY = 8F * Settings.scale;
            img = ImageMaster.EYE_ANIM_1;
        } else
        {
            vY = 12F * Settings.scale;
            img = ImageMaster.EYE_ANIM_0;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y + vY, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vY;
    private float dur_div2;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
