// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MiracleEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class MiracleEffect extends AbstractGameEffect
{

    public MiracleEffect(Color setColor, Color altColor, String setSfx)
    {
        sfxUrl = "HEAL_3";
        if(img == null)
            img = ImageMaster.CRYSTAL_IMPACT;
        x = AbstractDungeon.player.hb.cX - (float)img.packedWidth / 2.0F;
        y = AbstractDungeon.player.hb.cY - (float)img.packedHeight / 2.0F;
        startingDuration = 0.7F;
        duration = startingDuration;
        scale = Settings.scale;
        this.altColor = altColor;
        color = setColor.cpy();
        color.a = 0.0F;
        renderBehind = false;
        sfxUrl = setSfx;
    }

    public MiracleEffect()
    {
        sfxUrl = "HEAL_3";
        if(img == null)
            img = ImageMaster.CRYSTAL_IMPACT;
        x = AbstractDungeon.player.hb.cX - (float)img.packedWidth / 2.0F;
        y = AbstractDungeon.player.hb.cY - (float)img.packedHeight / 2.0F;
        startingDuration = 0.7F;
        duration = startingDuration;
        scale = Settings.scale;
        altColor = new Color(1.0F, 0.6F, 0.2F, 0.0F);
        color = Settings.GOLD_COLOR.cpy();
        color.a = 0.0F;
        renderBehind = false;
    }

    public void update()
    {
        if(duration == startingDuration)
            CardCrawlGame.sound.playA(sfxUrl, 0.5F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.fade.apply(1.0F, 0.01F, duration - startingDuration / 2.0F) * Settings.scale;
        else
            color.a = Interpolation.fade.apply(0.01F, 1.0F, duration / (startingDuration / 2.0F)) * Settings.scale;
        scale = Interpolation.pow5In.apply(2.4F, 0.3F, duration / startingDuration) * Settings.scale;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        altColor.a = color.a;
        sb.setColor(altColor);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 1.1F, scale * 1.1F, 0.0F);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 0.9F, scale * 0.9F, 0.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private Color altColor;
    private String sfxUrl;
}
