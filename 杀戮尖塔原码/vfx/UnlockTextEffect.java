// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnlockTextEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class UnlockTextEffect extends AbstractGameEffect
{

    public UnlockTextEffect()
    {
        CardCrawlGame.sound.play("UNLOCK_PING");
        duration = 3F;
        startingDuration = 3F;
        y = START_Y;
        color = Settings.BLUE_TEXT_COLOR.cpy();
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            duration = 0.0F;
        }
        if(duration > 2.5F)
        {
            y = Interpolation.elasticIn.apply(TARGET_Y, START_Y, (duration - 2.5F) * 2.0F);
            color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (duration - 2.5F) * 2.0F);
        } else
        if(duration < 0.5F)
            color.a = Interpolation.pow2In.apply(0.0F, 1.0F, duration * 2.0F);
    }

    public void render(SpriteBatch sb)
    {
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, LABEL, X, y, color);
    }

    public void dispose()
    {
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final float TEXT_DURATION = 3F;
    private static final float X;
    private static final float START_Y;
    private static final float TARGET_Y;
    private float y;
    private static final String LABEL;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("UnlockTextEffect");
        TEXT = uiStrings.TEXT;
        X = 1350F * Settings.scale;
        START_Y = (float)Settings.HEIGHT / 2.0F - 440F * Settings.scale;
        TARGET_Y = (float)Settings.HEIGHT / 2.0F - 270F * Settings.scale;
        LABEL = TEXT[0];
    }
}
