// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RelicAboveCreatureEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class RelicAboveCreatureEffect extends AbstractGameEffect
{

    public RelicAboveCreatureEffect(float x, float y, AbstractRelic relic)
    {
        outlineColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        duration = 1.5F;
        startingDuration = 1.5F;
        this.relic = relic;
        this.x = x;
        this.y = y;
        color = Color.WHITE.cpy();
        offsetY = STARTING_OFFSET_Y;
        scale = Settings.scale;
    }

    public void update()
    {
        if(duration > 1.0F)
            color.a = Interpolation.exp5In.apply(1.0F, 0.0F, (duration - 1.0F) * 2.0F);
        super.update();
        if(AbstractDungeon.player.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT)
            offsetY = MathUtils.lerp(offsetY, TARGET_OFFSET_Y + 80F * Settings.scale, Gdx.graphics.getDeltaTime() * 5F);
        else
            offsetY = MathUtils.lerp(offsetY, TARGET_OFFSET_Y, Gdx.graphics.getDeltaTime() * 5F);
        y += Gdx.graphics.getDeltaTime() * 12F * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        outlineColor.a = color.a / 2.0F;
        sb.setColor(outlineColor);
        sb.draw(relic.outlineImg, x - 64F, (y - 64F) + offsetY, 64F, 64F, 128F, 128F, scale * (2.5F - duration), scale * (2.5F - duration), rotation, 0, 0, 128, 128, false, false);
        sb.setColor(color);
        sb.draw(relic.img, x - 64F, (y - 64F) + offsetY, 64F, 64F, 128F, 128F, scale * (2.5F - duration), scale * (2.5F - duration), rotation, 0, 0, 128, 128, false, false);
        sb.setBlendFunction(770, 1);
        shineColor.a = color.a / 4F;
        sb.setColor(shineColor);
        sb.draw(relic.img, x - 64F, (y - 64F) + offsetY, 64F, 64F, 128F, 128F, scale * (2.7F - duration), scale * (2.7F - duration), rotation, 0, 0, 128, 128, false, false);
        sb.draw(relic.img, x - 64F, (y - 64F) + offsetY, 64F, 64F, 128F, 128F, scale * (3F - duration), scale * (3F - duration), rotation, 0, 0, 128, 128, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static final float TEXT_DURATION = 1.5F;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private static final float LERP_RATE = 5F;
    private static final int W = 128;
    private float x;
    private float y;
    private float offsetY;
    private AbstractRelic relic;
    private Color outlineColor;
    private Color shineColor;

    static 
    {
        STARTING_OFFSET_Y = 0.0F * Settings.scale;
        TARGET_OFFSET_Y = 60F * Settings.scale;
    }
}
