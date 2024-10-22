// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultiPageFtue.java

package com.megacrit.cardcrawl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui:
//            FtueTip

public class MultiPageFtue extends FtueTip
{

    public MultiPageFtue()
    {
        screen = Color.valueOf("1c262a00");
        scrollTimer = 0.0F;
        currentSlot = 0;
        img1 = ImageMaster.loadImage("images/ui/tip/t1.png");
        img2 = ImageMaster.loadImage("images/ui/tip/t2.png");
        img3 = ImageMaster.loadImage("images/ui/tip/t3.png");
        AbstractDungeon.player.releaseCard();
        if(AbstractDungeon.isScreenUp)
        {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
        x = 0.0F;
        x1 = 567F * Settings.scale;
        x2 = x1 + (float)Settings.WIDTH;
        x3 = x2 + (float)Settings.WIDTH;
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
    }

    public void update()
    {
        if(screen.a != 0.8F)
        {
            screen.a += Gdx.graphics.getDeltaTime();
            if(screen.a > 0.8F)
                screen.a = 0.8F;
        }
        if(AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed())
        {
            CInputActionSet.proceed.unpress();
            if(currentSlot == -2)
            {
                CardCrawlGame.sound.play("DECK_CLOSE");
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.effectList.clear();
                AbstractDungeon.topLevelEffects.add(new BattleStartEffect(false));
                return;
            }
            AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
            AbstractDungeon.overlayMenu.proceedButton.show();
            CardCrawlGame.sound.play("DECK_CLOSE");
            currentSlot--;
            startX = x;
            targetX = currentSlot * Settings.WIDTH;
            scrollTimer = 0.3F;
            if(currentSlot == -2)
                AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
        }
        if(scrollTimer != 0.0F)
        {
            scrollTimer -= Gdx.graphics.getDeltaTime();
            if(scrollTimer < 0.0F)
                scrollTimer = 0.0F;
        }
        x = Interpolation.fade.apply(targetX, startX, scrollTimer / 0.3F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(screen);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(Color.WHITE);
        sb.draw(img1, (x + x1) - 380F, (float)Settings.HEIGHT / 2.0F - 290F, 380F, 290F, 760F, 580F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);
        sb.draw(img2, (x + x2) - 380F, (float)Settings.HEIGHT / 2.0F - 290F, 380F, 290F, 760F, 580F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);
        sb.draw(img3, (x + x3) - 380F, (float)Settings.HEIGHT / 2.0F - 290F, 380F, 290F, 760F, 580F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);
        float offsetY = 0.0F;
        if(Settings.BIG_TEXT_MODE)
            offsetY = 110F * Settings.scale;
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg1, x + x1 + 400F * Settings.scale, ((float)Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, msg1, 700F * Settings.scale, 40F * Settings.scale) / 2.0F) + offsetY, 700F * Settings.scale, 40F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg2, x + x2 + 400F * Settings.scale, ((float)Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, msg2, 700F * Settings.scale, 40F * Settings.scale) / 2.0F) + offsetY, 700F * Settings.scale, 40F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg3, x + x3 + 400F * Settings.scale, ((float)Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, msg3, 700F * Settings.scale, 40F * Settings.scale) / 2.0F) + offsetY, 700F * Settings.scale, 40F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, LABEL[2], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 360F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, (new StringBuilder()).append(LABEL[3]).append(Integer.toString(Math.abs(currentSlot - 1))).append(LABEL[4]).toString(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 400F * Settings.scale, Settings.CREAM_COLOR);
        AbstractDungeon.overlayMenu.proceedButton.render(sb);
    }

    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    private static final int W = 760;
    private static final int H = 580;
    private Texture img1;
    private Texture img2;
    private Texture img3;
    private Color screen;
    private float x;
    private float x1;
    private float x2;
    private float x3;
    private float targetX;
    private float startX;
    private float scrollTimer;
    private static final float SCROLL_TIME = 0.3F;
    private int currentSlot;
    private static final String msg1;
    private static final String msg2;
    private static final String msg3;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Main Tutorial");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        msg1 = MSG[0];
        msg2 = MSG[1];
        msg3 = MSG[2];
    }
}
