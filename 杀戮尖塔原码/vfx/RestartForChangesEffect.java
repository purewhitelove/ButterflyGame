// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RestartForChangesEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class RestartForChangesEffect extends AbstractGameEffect
{

    public RestartForChangesEffect()
    {
        color = Settings.RED_TEXT_COLOR.cpy();
        duration = 2.0F;
        color.a = 0.0F;
        x = (float)Settings.WIDTH / 2.0F;
        y = Settings.OPTION_Y + 460F * Settings.scale;
        scale = 1.3F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        scale = MathHelper.popLerpSnap(scale, 1.0F);
        if(duration < 0.0F)
        {
            duration = 0.0F;
            isDone = true;
        }
        if(duration < 1.0F)
            color.a = duration;
        else
            color.a = 1.0F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, duration / 2.0F));
        float w = FontHelper.getWidth(FontHelper.panelNameFont, TEXT[0], scale);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, x - w / 2.0F - 50F * Settings.scale, y - 25F * Settings.scale, w + 100F * Settings.scale, 50F * Settings.scale);
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[0], x, y, color, scale);
    }

    public void dispose()
    {
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private float x;
    private float y;
    private Color color;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("RestartForChangesEffect");
        TEXT = uiStrings.TEXT;
    }
}
