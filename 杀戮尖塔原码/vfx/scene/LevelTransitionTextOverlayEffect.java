// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LevelTransitionTextOverlayEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LevelTransitionTextOverlayEffect extends AbstractGameEffect
{

    public LevelTransitionTextOverlayEffect(String name, String levelNum, boolean higher)
    {
        alpha = 0.0F;
        c1 = Settings.GOLD_COLOR.cpy();
        c2 = Settings.BLUE_TEXT_COLOR.cpy();
        this.higher = false;
        this.name = name;
        this.levelNum = levelNum;
        duration = 5F;
        startingDuration = 5F;
        color = Settings.GOLD_COLOR.cpy();
        color.a = 0.0F;
        this.higher = higher;
    }

    public LevelTransitionTextOverlayEffect(String name, String levelNum)
    {
        alpha = 0.0F;
        c1 = Settings.GOLD_COLOR.cpy();
        c2 = Settings.BLUE_TEXT_COLOR.cpy();
        higher = false;
        this.name = name;
        this.levelNum = levelNum;
        duration = 5F;
        startingDuration = 5F;
        color = Settings.GOLD_COLOR.cpy();
        color.a = 0.0F;
    }

    public void update()
    {
        if(duration > 4F)
            alpha = Interpolation.pow5Out.apply(1.0F, 0.0F, (duration - 4F) / 4F);
        else
            alpha = Interpolation.fade.apply(0.0F, 1.0F, duration / 2.5F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        c1.a = alpha;
        c2.a = alpha;
    }

    public void render(SpriteBatch sb)
    {
        if(higher)
        {
            FontHelper.renderFontCentered(sb, FontHelper.SCP_cardDescFont, levelNum, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 290F * Settings.scale, c2, 1.0F);
            FontHelper.renderFontCentered(sb, FontHelper.dungeonTitleFont, name, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 190F * Settings.scale, c1);
        } else
        {
            FontHelper.renderFontCentered(sb, FontHelper.SCP_cardDescFont, levelNum, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 90F * Settings.scale, c2, 1.0F);
            FontHelper.renderFontCentered(sb, FontHelper.dungeonTitleFont, name, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 10F * Settings.scale, c1);
        }
    }

    public void dispose()
    {
    }

    private String name;
    private String levelNum;
    private static final float DUR = 5F;
    private float alpha;
    private Color c1;
    private Color c2;
    private boolean higher;
}
