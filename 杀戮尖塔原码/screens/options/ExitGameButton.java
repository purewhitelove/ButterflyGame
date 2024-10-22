// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExitGameButton.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.options:
//            SettingsScreen, ConfirmPopup

public class ExitGameButton
{

    public ExitGameButton()
    {
        W = 635;
        H = 488;
        label = TEXT[0];
        hb = new Hitbox(280F * Settings.scale, 80F * Settings.scale);
        x = 1490F * Settings.xScale;
        y = Settings.OPTION_Y - 202F * Settings.scale;
        hb.move(x + 50F * Settings.xScale, y - 173F * Settings.scale);
    }

    public void update()
    {
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(hb.clicked || CInputActionSet.discardPile.isJustPressed())
        {
            hb.clicked = false;
            AbstractDungeon.settingsScreen.popup(ConfirmPopup.ConfirmType.EXIT);
        }
        if(hb.hovered && InputHelper.justClickedLeft || CInputActionSet.discardPile.isJustPressed())
        {
            if(CardCrawlGame.mode == com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.CHAR_SELECT)
            {
                logger.info("Exit Game button clicked!");
                Gdx.app.exit();
            }
            CardCrawlGame.sound.play("UI_CLICK_1");
            hb.clickStarted = true;
            return;
        } else
        {
            return;
        }
    }

    public void updateLabel(String newLabel)
    {
        label = newLabel;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.OPTION_EXIT, x - (float)W / 2.0F, y - (float)H / 2.0F, (float)W / 2.0F, (float)H / 2.0F, W, H, Settings.scale, Settings.scale, 0.0F, 0, 0, W, H, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, label, x + 50F * Settings.scale, y - 170F * Settings.scale, Settings.GOLD_COLOR, 1.0F);
        if(hb.hovered)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));
            sb.draw(ImageMaster.OPTION_EXIT, x - (float)W / 2.0F, y - (float)H / 2.0F, (float)W / 2.0F, (float)H / 2.0F, W, H, Settings.scale, Settings.scale, 0.0F, 0, 0, W, H, false, false);
            sb.setBlendFunction(770, 771);
        }
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.discardPile.getKeyImg(), x - 32F - 32F * Settings.scale - FontHelper.getSmartWidth(FontHelper.losePowerFont, label, 99999F, 0.0F) / 2.0F, y - 32F - 170F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        hb.render(sb);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/options/ExitGameButton.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private int W;
    private int H;
    private Hitbox hb;
    private float x;
    private float y;
    private String label;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExitGameButton");
        TEXT = uiStrings.TEXT;
    }
}
