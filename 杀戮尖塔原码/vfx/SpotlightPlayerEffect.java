// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpotlightPlayerEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class SpotlightPlayerEffect extends AbstractGameEffect
{

    public SpotlightPlayerEffect()
    {
        duration = 3F;
        color = new Color(1.0F, 1.0F, 0.8F, 0.5F);
    }

    public void update()
    {
        if(duration == 3F)
            CardCrawlGame.sound.playA("INTIMIDATE", -0.6F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > 1.5F)
            color.a = Interpolation.pow5In.apply(0.5F, 0.0F, (duration - 1.5F) / 1.5F);
        else
            color.a = Interpolation.exp10In.apply(0.0F, 0.5F, duration / 1.5F);
        if(duration < 0.0F)
        {
            color.a = 0.0F;
            isDone = true;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.SPOTLIGHT_VFX, 0.0F, 0.0F, AbstractDungeon.player.drawX + AbstractDungeon.player.hb_w * 2.0F, Settings.HEIGHT);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }
}
