// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamageNumberEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SumDamageEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class DamageNumberEffect extends AbstractGameEffect
{

    public DamageNumberEffect(AbstractCreature target, float x, float y, int amt)
    {
        scale = 1.0F;
        duration = 1.2F;
        startingDuration = 1.2F;
        this.x = x;
        this.y = y + OFFSET_Y;
        this.target = target;
        vX = MathUtils.random(100F * Settings.scale, 150F * Settings.scale);
        if(MathUtils.randomBoolean())
            vX = -vX;
        vY = MathUtils.random(400F * Settings.scale, 500F * Settings.scale);
        this.amt = amt;
        color = Color.RED.cpy();
        if(!Settings.SHOW_DMG_SUM || amt <= 0)
            return;
        boolean isSumDamageAvailable = false;
        Iterator iterator = AbstractDungeon.effectList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)iterator.next();
            if((e instanceof SumDamageEffect) && ((SumDamageEffect)e).target == target)
            {
                isSumDamageAvailable = true;
                ((SumDamageEffect)e).refresh(amt);
            }
        } while(true);
        if(!isSumDamageAvailable)
        {
            Iterator iterator1 = AbstractDungeon.effectList.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractGameEffect e = (AbstractGameEffect)iterator1.next();
                if((e instanceof DamageNumberEffect) && e != this && ((DamageNumberEffect)e).target == target)
                    AbstractDungeon.effectsQueue.add(new SumDamageEffect(target, x, y, ((DamageNumberEffect)e).amt + amt));
            } while(true);
        }
    }

    public void update()
    {
        x += Gdx.graphics.getDeltaTime() * vX;
        y += Gdx.graphics.getDeltaTime() * vY;
        vY += Gdx.graphics.getDeltaTime() * GRAVITY_Y;
        super.update();
        if(color.g != 1.0F)
        {
            color.g += Gdx.graphics.getDeltaTime() * 4F;
            if(color.g > 1.0F)
                color.g = 1.0F;
        }
        if(color.b != 1.0F)
        {
            color.b += Gdx.graphics.getDeltaTime() * 4F;
            if(color.b > 1.0F)
                color.b = 1.0F;
        }
        scale = Interpolation.pow4Out.apply(6F, 1.2F, 1.0F - duration / 1.2F) * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        FontHelper.damageNumberFont.getData().setScale(scale);
        FontHelper.renderFontCentered(sb, FontHelper.damageNumberFont, Integer.toString(amt), x, y, color);
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 1.2F;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private static final float OFFSET_Y;
    private static final float GRAVITY_Y;
    private int amt;
    private float scale;
    public AbstractCreature target;

    static 
    {
        OFFSET_Y = 150F * Settings.scale;
        GRAVITY_Y = -2000F * Settings.scale;
    }
}
