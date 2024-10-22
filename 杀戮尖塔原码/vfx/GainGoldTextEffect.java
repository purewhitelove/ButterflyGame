// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GainGoldTextEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class GainGoldTextEffect extends AbstractGameEffect
{

    public GainGoldTextEffect(int startingAmount)
    {
        gold = 0;
        reachedCenter = false;
        waitTimer = 1.0F;
        fadeTimer = 1.0F;
        x = AbstractDungeon.player.hb.cX;
        y = AbstractDungeon.player.hb.cY;
        destinationY = y + 150F * Settings.scale;
        duration = 3F;
        startingDuration = 3F;
        reachedCenter = false;
        gold = startingAmount;
        totalGold = startingAmount;
        color = Color.GOLD.cpy();
    }

    public void update()
    {
        if(waitTimer > 0.0F)
        {
            gold = totalGold;
            if(!reachedCenter && y != destinationY)
            {
                y = MathUtils.lerp(y, destinationY, Gdx.graphics.getDeltaTime() * 9F);
                if(Math.abs(y - destinationY) < Settings.UI_SNAP_THRESHOLD)
                {
                    y = destinationY;
                    reachedCenter = true;
                }
            } else
            {
                waitTimer -= Gdx.graphics.getDeltaTime();
                if(waitTimer < 0.0F)
                    gold = totalGold;
                else
                    gold = totalGold;
            }
        } else
        {
            y += Gdx.graphics.getDeltaTime() * FADE_Y_SPEED;
            fadeTimer -= Gdx.graphics.getDeltaTime();
            color.a = fadeTimer;
            if(fadeTimer < 0.0F)
                isDone = true;
        }
    }

    public boolean ping(int amount)
    {
        if(waitTimer > 0.0F)
        {
            waitTimer = 1.0F;
            totalGold += amount;
            return true;
        } else
        {
            return false;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, (new StringBuilder()).append("+ ").append(Integer.toString(gold)).append(TEXT[0]).toString(), x, y, color);
    }

    public void dispose()
    {
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static int totalGold = 0;
    private int gold;
    private boolean reachedCenter;
    private float x;
    private float y;
    private float destinationY;
    private static final float WAIT_TIME = 1F;
    private float waitTimer;
    private float fadeTimer;
    private static final float FADE_Y_SPEED;
    private static final float TEXT_DURATION = 3F;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("GainGoldTextEffect");
        TEXT = uiStrings.TEXT;
        FADE_Y_SPEED = 100F * Settings.scale;
    }
}
