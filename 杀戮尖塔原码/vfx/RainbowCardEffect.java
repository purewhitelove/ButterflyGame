// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RainbowCardEffect.java

package com.megacrit.cardcrawl.vfx;

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

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class RainbowCardEffect extends AbstractGameEffect
{

    public RainbowCardEffect()
    {
        if(img == null)
            img = ImageMaster.CRYSTAL_IMPACT;
        x = AbstractDungeon.player.hb.cX - (float)img.packedWidth / 2.0F;
        y = AbstractDungeon.player.hb.cY - (float)img.packedHeight / 2.0F;
        startingDuration = 1.5F;
        duration = startingDuration;
        scale = Settings.scale;
        color = Color.CYAN.cpy();
        color.a = 0.0F;
        renderBehind = true;
    }

    public void update()
    {
        if(duration == startingDuration)
            CardCrawlGame.sound.playA("HEAL_3", 0.5F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.fade.apply(1.0F, 0.01F, duration - startingDuration / 2.0F) * Settings.scale;
        else
            color.a = Interpolation.fade.apply(0.01F, 1.0F, duration / (startingDuration / 2.0F)) * Settings.scale;
        scale = Interpolation.elasticIn.apply(4F, 0.01F, duration / startingDuration) * Settings.scale;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(new Color(1.0F, 0.2F, 0.2F, color.a));
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 1.15F, scale * 1.15F, 0.0F);
        sb.setColor(new Color(1.0F, 1.0F, 0.2F, color.a));
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, 0.0F);
        sb.setColor(new Color(0.2F, 1.0F, 0.2F, color.a));
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 0.85F, scale * 0.85F, 0.0F);
        sb.setColor(new Color(0.2F, 0.7F, 1.0F, color.a));
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 0.7F, scale * 0.7F, 0.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    float x;
    float y;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
