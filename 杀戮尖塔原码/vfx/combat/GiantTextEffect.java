// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GiantTextEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.purple.Judgement;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GiantTextEffect extends AbstractGameEffect
{

    public GiantTextEffect(float x, float y)
    {
        sBuilder = new StringBuilder("");
        targetString = Judgement.cardStrings.EXTENDED_DESCRIPTION[0];
        index = 0;
        sBuilder.setLength(0);
        this.x = x;
        this.y = y + 100F * Settings.scale;
        color = Color.WHITE.cpy();
        duration = 1.0F;
    }

    public void update()
    {
        color.a = Interpolation.pow5Out.apply(0.0F, 0.8F, duration);
        color.a += MathUtils.random(-0.05F, 0.05F);
        color.a = MathUtils.clamp(color.a, 0.0F, 1.0F);
        duration -= Gdx.graphics.getDeltaTime();
        if(index < targetString.length())
        {
            sBuilder.append(targetString.charAt(index));
            index++;
        }
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, Judgement.cardStrings.EXTENDED_DESCRIPTION[0], x, y, color, (2.5F - duration / 4F) + MathUtils.random(0.05F));
        sb.setBlendFunction(770, 1);
        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, Judgement.cardStrings.EXTENDED_DESCRIPTION[0], x, y, color, 0.05F + (2.5F - duration / 4F) + MathUtils.random(0.05F));
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private StringBuilder sBuilder;
    private String targetString;
    private int index;
    private float x;
    private float y;
}
