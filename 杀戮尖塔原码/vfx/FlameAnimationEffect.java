// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlameAnimationEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class FlameAnimationEffect extends AbstractGameEffect
{

    public FlameAnimationEffect(Hitbox hb)
    {
        img = null;
        flipped = false;
        nodeHb = hb;
        startingDuration = 0.5F;
        duration = 0.5F;
        scale = MathUtils.random(0.9F, 1.3F) * Settings.scale;
        rotation = MathUtils.random(-30F, 30F);
        offsetX = MathUtils.random(0.0F, 8F) * Settings.scale;
        offsetY = MathUtils.random(-3F, 14F) * Settings.scale;
        alternator = !alternator;
        flipped = alternator;
        if(!alternator)
            offsetX = -offsetX;
        color = new Color(0.34F, 0.34F, 0.34F, 1.0F);
        color = color.cpy();
        img = ImageMaster.FLAME_ANIM_1;
    }

    public void update()
    {
        color.a = duration / 0.5F;
        if(duration < 0.1F)
            img = null;
        else
        if(duration < 0.0F)
            img = ImageMaster.FLAME_ANIM_6;
        else
        if(duration < 0.1F)
            img = ImageMaster.FLAME_ANIM_5;
        else
        if(duration < 0.2F)
            img = ImageMaster.FLAME_ANIM_4;
        else
        if(duration < 0.3F)
            img = ImageMaster.FLAME_ANIM_3;
        else
        if(duration < 0.4F)
            img = ImageMaster.FLAME_ANIM_2;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb, float s)
    {
        sb.setColor(color);
        if(img != null)
            sb.draw(img, (nodeHb.cX - 128F) + offsetX, (nodeHb.cY - 128F) + offsetY, 128F, 128F, 256F, 256F, s * scale, s * scale, rotation, 0, 0, 256, 256, flipped, false);
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    public Texture img;
    private static final int W = 256;
    private static final float DUR = 0.5F;
    private static boolean alternator = true;
    private boolean flipped;
    private Hitbox nodeHb;
    private float offsetX;
    private float offsetY;

}
