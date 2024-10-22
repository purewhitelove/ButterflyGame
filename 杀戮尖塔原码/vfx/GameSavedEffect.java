// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameSavedEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.DialogWord;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, SpeechTextEffect

public class GameSavedEffect extends AbstractGameEffect
{

    public GameSavedEffect()
    {
    }

    public void update()
    {
        if(ModHelper.enabledMods.size() > 0)
        {
            if(ModHelper.enabledMods.size() > 3)
                AbstractDungeon.topLevelEffects.add(new SpeechTextEffect(1600F * Settings.scale, (float)Settings.HEIGHT - 74F * Settings.scale, 2.0F, TEXT[0], com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.FADE_IN));
            else
                AbstractDungeon.topLevelEffects.add(new SpeechTextEffect(1600F * Settings.scale, (float)Settings.HEIGHT - 26F * Settings.scale, 2.0F, TEXT[0], com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.FADE_IN));
        } else
        {
            AbstractDungeon.topLevelEffects.add(new SpeechTextEffect(1450F * Settings.scale, (float)Settings.HEIGHT - 26F * Settings.scale, 2.0F, TEXT[0], com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.FADE_IN));
        }
        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("GameSavedEffect");
        TEXT = uiStrings.TEXT;
    }
}
