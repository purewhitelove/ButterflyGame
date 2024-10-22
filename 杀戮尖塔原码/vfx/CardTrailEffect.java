// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardTrailEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Pool;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class CardTrailEffect extends AbstractGameEffect
    implements com.badlogic.gdx.utils.Pool.Poolable
{

    public CardTrailEffect()
    {
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/blurDot2");
        renderBehind = false;
    }

    public void init(float x, float y)
    {
        duration = 0.5F;
        startingDuration = 0.5F;
        this.x = x - 6F;
        this.y = y - 6F;
        color = AbstractDungeon.player.getCardTrailColor();
        scale = 0.01F;
        isDone = false;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.25F)
            scale = duration * SCALE_MULTI;
        else
            scale = (duration - 0.25F) * SCALE_MULTI;
        if(duration < 0.0F)
        {
            isDone = true;
            Soul.trailEffectPool.free(this);
        } else
        {
            color.a = Interpolation.fade.apply(0.0F, 0.18F, duration / 0.5F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, 6F, 6F, 12F, 12F, scale, scale, 0.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    public void reset()
    {
    }

    private static final float EFFECT_DUR = 0.5F;
    private static final float DUR_DIV_2 = 0.25F;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img = null;
    private static final int W = 12;
    private static final int W_DIV_2 = 6;
    private static final float SCALE_MULTI;
    private float x;
    private float y;

    static 
    {
        SCALE_MULTI = Settings.scale * 22F;
    }
}
