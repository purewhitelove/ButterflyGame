// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbandonRunButton.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

// Referenced classes of package com.megacrit.cardcrawl.screens.options:
//            SettingsScreen, ConfirmPopup

public class AbandonRunButton
{

    public AbandonRunButton()
    {
        W = 440;
        H = 100;
        hb = new Hitbox(340F * Settings.scale, 70F * Settings.scale);
        x = 1430F * Settings.xScale;
        y = Settings.OPTION_Y + 340F * Settings.scale;
        hb.move(x, y);
    }

    public void update()
    {
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(InputHelper.justClickedLeft && hb.hovered)
        {
            CardCrawlGame.sound.play("UI_CLICK_1");
            hb.clickStarted = true;
        }
        if(hb.clicked || CInputActionSet.proceed.isJustPressed())
        {
            CInputActionSet.proceed.unpress();
            hb.clicked = false;
            AbstractDungeon.settingsScreen.popup(ConfirmPopup.ConfirmType.ABANDON_MID_RUN);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.OPTION_ABANDON, x - (float)W / 2.0F, y - (float)H / 2.0F, (float)W / 2.0F, (float)H / 2.0F, W, H, Settings.scale, Settings.scale, 0.0F, 0, 0, W, H, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[0], x + 15F * Settings.scale, y + 5F * Settings.scale, Settings.GOLD_COLOR);
        if(hb.hovered)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));
            sb.draw(ImageMaster.OPTION_ABANDON, x - (float)W / 2.0F, y - (float)H / 2.0F, (float)W / 2.0F, (float)H / 2.0F, W, H, Settings.scale, Settings.scale, 0.0F, 0, 0, W, H, false, false);
            sb.setBlendFunction(770, 771);
        }
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.proceed.getKeyImg(), x - 32F - 32F * Settings.scale - FontHelper.getSmartWidth(FontHelper.buttonLabelFont, TEXT[0], 99999F, 0.0F) / 2.0F, (y - 32F) + 5F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        hb.render(sb);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private int W;
    private int H;
    private Hitbox hb;
    private float x;
    private float y;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("AbandonRunButton");
        TEXT = uiStrings.TEXT;
    }
}
