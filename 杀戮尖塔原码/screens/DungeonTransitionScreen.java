// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DungeonTransitionScreen.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import java.util.HashMap;

public class DungeonTransitionScreen
{

    public DungeonTransitionScreen(String key)
    {
        isComplete = false;
        msgCreated = false;
        isFading = false;
        playSFX = false;
        popup = null;
        color = Settings.CREAM_COLOR.cpy();
        lvlColor = Settings.BLUE_TEXT_COLOR.cpy();
        animTimer = 0.0F;
        continueColor = Settings.GOLD_COLOR.cpy();
        if(!((Boolean)TipTracker.tips.get("NO_FTUE")).booleanValue())
        {
            popup = new ConfirmPopup(TEXT[0], TEXT[1], com.megacrit.cardcrawl.screens.options.ConfirmPopup.ConfirmType.SKIP_FTUE);
            popup.show();
        }
        source = "";
        name = "";
        timer = 2.0F;
        continueFader = 0.0F;
        oscillateTimer = 0.0F;
        continueColor.a = 0.0F;
        lvlColor.a = 0.0F;
        color.a = 0.0F;
        setAreaName(key);
        isComplete = true;
    }

    private void setAreaName(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -1887678253: 
            if(s.equals("Exordium"))
                byte0 = 0;
            break;

        case 313705820: 
            if(s.equals("TheCity"))
                byte0 = 1;
            break;

        case 791401920: 
            if(s.equals("TheBeyond"))
                byte0 = 2;
            break;

        case 884969688: 
            if(s.equals("TheEnding"))
                byte0 = 3;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            levelNum = TEXT[2];
            levelName = TEXT[3];
            break;

        case 1: // '\001'
            levelNum = TEXT[4];
            levelName = TEXT[5];
            break;

        case 2: // '\002'
            levelNum = TEXT[6];
            levelName = TEXT[7];
            break;

        case 3: // '\003'
            levelNum = TEXT[8];
            levelName = TEXT[9];
            break;

        default:
            levelNum = TEXT[8];
            levelName = TEXT[9];
            break;
        }
        AbstractDungeon.name = levelName;
        AbstractDungeon.levelNum = levelNum;
    }

    private void oscillateColor()
    {
        oscillateTimer += Gdx.graphics.getDeltaTime() * 5F;
        continueColor.a = 0.33F + (MathUtils.cos(oscillateTimer) + 1.0F) / 3F;
        if(!isFading)
        {
            if(continueFader != 1.0F)
            {
                continueFader += Gdx.graphics.getDeltaTime() / 2.0F;
                if(continueFader > 1.0F)
                    continueFader = 1.0F;
            }
        } else
        if(continueFader != 0.0F)
        {
            continueFader -= Gdx.graphics.getDeltaTime();
            if(continueFader < 0.0F)
                continueFader = 0.0F;
        }
        continueColor.a = continueColor.a * continueFader;
    }

    public void update()
    {
        if(popup != null && popup.shown)
        {
            popup.update();
            return;
        }
        if(msgCreated)
            oscillateColor();
        if(Settings.isDebug || InputHelper.justClickedLeft)
        {
            InputHelper.justClickedLeft = false;
            isComplete = true;
        }
        if(isFading)
        {
            timer -= Gdx.graphics.getDeltaTime();
            if(timer < 0.0F)
            {
                isComplete = true;
            } else
            {
                color.a = timer;
                return;
            }
        }
        if(animTimer > 0.5F && !playSFX)
        {
            playSFX = true;
            CardCrawlGame.sound.play("DUNGEON_TRANSITION");
        }
        if(!msgCreated)
        {
            animTimer += Gdx.graphics.getDeltaTime();
            if(animTimer > 4F)
            {
                msgCreated = true;
                animTimer = 4F;
            }
            if(animTimer > 2.0F)
                color.a = 1.0F;
            else
                color.a = animTimer / 2.0F;
        }
    }

    public void render(SpriteBatch sb)
    {
        lvlColor.a = color.a;
        FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, levelNum, (float)Settings.WIDTH / 2.0F - 44F * Settings.scale, (float)Settings.HEIGHT * 0.54F, lvlColor);
        FontHelper.renderFontCentered(sb, FontHelper.dungeonTitleFont, levelName, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, color);
        FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, (new StringBuilder()).append("\"").append(source).append("\"").toString(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.44F, color);
        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, TEXT[10], (float)Settings.WIDTH / 2.0F, 100F * Settings.scale, continueColor);
        if(popup != null && popup.shown)
            popup.render(sb);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public boolean isComplete;
    public boolean msgCreated;
    public boolean isFading;
    public float timer;
    public String name;
    public String levelNum;
    public String levelName;
    private String source;
    private boolean playSFX;
    private ConfirmPopup popup;
    private Color color;
    private Color lvlColor;
    private float oscillateTimer;
    private float continueFader;
    private float animTimer;
    private Color continueColor;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DungeonTransitionScreen");
        TEXT = uiStrings.TEXT;
    }
}
