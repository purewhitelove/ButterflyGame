// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefectVictoryNumberEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DefectVictoryNumberEffect extends AbstractGameEffect
{

    public DefectVictoryNumberEffect()
    {
        num = "";
        dontIncrement = false;
        renderBehind = true;
        x = MathUtils.random(0.0F, 1870F) * Settings.xScale;
        y = MathUtils.random(50F, 990F) * Settings.yScale;
        duration = MathUtils.random(2.0F, 4F);
        color = new Color(MathUtils.random(0.5F, 1.0F), MathUtils.random(0.5F, 1.0F), MathUtils.random(0.5F, 1.0F), 0.0F);
        scale = MathUtils.random(0.7F, 1.3F);
        incrementTimer = MathUtils.random(0.02F, 0.1F);
        switch(MathUtils.random(100))
        {
        case 0: // '\0'
            num = "H3110";
            dontIncrement = true;
            break;

        case 1: // '\001'
            num = "D00T D00T";
            dontIncrement = true;
            break;

        case 2: // '\002'
            num = "<ERR0R>";
            dontIncrement = true;
            break;
        }
    }

    public void update()
    {
        if(dontIncrement)
            break MISSING_BLOCK_LABEL_105;
        incrementTimer -= Gdx.graphics.getDeltaTime();
        if(incrementTimer >= 0.0F)
            break MISSING_BLOCK_LABEL_105;
        if(!MathUtils.randomBoolean()) goto _L2; else goto _L1
_L1:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        num;
        append();
        "0";
        append();
        toString();
        num;
          goto _L3
_L2:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        num;
        append();
        "1";
        append();
        toString();
        num;
_L3:
        incrementTimer = MathUtils.random(0.1F, 0.4F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            return;
        }
        if(duration < 1.0F)
            color.a = Interpolation.bounceOut.apply(0.0F, 0.5F, duration);
        else
            color.a = MathHelper.slowColorLerpSnap(color.a, 0.5F);
        return;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        FontHelper.energyNumFontBlue.getData().setScale(scale);
        FontHelper.renderFont(sb, FontHelper.energyNumFontBlue, num, x, y, color);
        FontHelper.energyNumFontBlue.getData().setScale(1.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float incrementTimer;
    private String num;
    private boolean dontIncrement;
}
