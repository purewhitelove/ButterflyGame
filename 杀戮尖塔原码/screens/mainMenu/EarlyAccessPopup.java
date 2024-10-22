// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EarlyAccessPopup.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.scenes.TitleBackground;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            MainMenuScreen

public class EarlyAccessPopup
{

    public EarlyAccessPopup()
    {
        darken = false;
        if(img == null)
            img = ImageMaster.loadImage("images/ui/eapopup.png");
    }

    public void update()
    {
        if(!darken)
        {
            darken = true;
            CardCrawlGame.mainMenuScreen.darken();
        }
        if((InputHelper.justClickedLeft || InputHelper.pressedEscape || CInputActionSet.select.isJustPressed()) && CardCrawlGame.mainMenuScreen.screenColor.a == 0.8F)
        {
            CardCrawlGame.mainMenuScreen.bg.activated = true;
            isUp = false;
            CardCrawlGame.mainMenuScreen.lighten();
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        if(!Settings.isBeta)
        {
            FontHelper.renderFontCenteredTopAligned(sb, FontHelper.damageNumberFont, TEXT[0], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 150F * Settings.scale, Settings.GOLD_COLOR);
            FontHelper.renderSmartText(sb, FontHelper.topPanelInfoFont, TEXT[2], 600F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 50F * Settings.scale, 800F * Settings.scale, 32F * Settings.scale, Settings.CREAM_COLOR);
        } else
        {
            FontHelper.renderFontCenteredTopAligned(sb, FontHelper.damageNumberFont, TEXT[1], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 150F * Settings.scale, Settings.GOLD_COLOR);
            FontHelper.renderSmartText(sb, FontHelper.topPanelInfoFont, TEXT[3], 600F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 50F * Settings.scale, 800F * Settings.scale, 32F * Settings.scale, Settings.CREAM_COLOR);
        }
        if(!Settings.isControllerMode)
        {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[4], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.2F, new Color(1.0F, 0.9F, 0.4F, 0.5F + MathUtils.cosDeg((System.currentTimeMillis() / 4L) % 360L) / 5F));
        } else
        {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[5], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.2F, new Color(1.0F, 0.9F, 0.4F, 0.5F + MathUtils.cosDeg((System.currentTimeMillis() / 4L) % 360L) / 5F));
            sb.draw(CInputActionSet.select.getKeyImg(), (float)Settings.WIDTH / 2.0F - 32F - 110F * Settings.scale, (float)Settings.HEIGHT * 0.2F - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public static boolean isUp = false;
    private boolean darken;
    private static Texture img = null;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("EarlyAccessPopup");
        TEXT = uiStrings.TEXT;
    }
}
