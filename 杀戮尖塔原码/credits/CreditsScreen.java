// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CreditsScreen.java

package com.megacrit.cardcrawl.credits;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cutscenes.NeowNarrationScreen;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.screens.DoorUnlockScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.credits:
//            CreditLine

public class CreditsScreen
{

    public CreditsScreen()
    {
        screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        fadeInTimer = 2.0F;
        lines = new ArrayList();
        thankYouTimer = 3F;
        thankYouColor = Settings.CREAM_COLOR.cpy();
        THANKS_MSG = CardCrawlGame.languagePack.getCreditString("THANKS_MSG").HEADER;
        showNeowAfter = false;
        tmpY = -400F;
        currentY = SCROLL_START_Y;
        targetY = currentY;
        creditLineHelper("DEV");
        creditLineHelper("OPS");
        creditLineHelper("SOUND");
        creditLineHelper("VOICE");
        creditLineHelper("PORTRAITS");
        creditLineHelper("ILLUSTRATION");
        creditLineHelper("ANIMATION");
        if(Settings.isConsoleBuild)
        {
            creditLineHelper("PORTING");
            creditLineHelper("PUBLISHING");
            creditLineHelper("KAKEHASHI");
            END_OF_CREDITS_Y += 1600F * Settings.scale;
        }
        creditLineHelper("LOC_ZHS");
        creditLineHelper("LOC_ZHT");
        creditLineHelper("LOC_DEU");
        creditLineHelper("LOC_DUT");
        creditLineHelper("LOC_EPO");
        creditLineHelper("LOC_FIN");
        creditLineHelper("LOC_FRA");
        creditLineHelper("LOC_GRE");
        creditLineHelper("LOC_IND");
        creditLineHelper("LOC_ITA");
        creditLineHelper("LOC_JPN");
        creditLineHelper("LOC_KOR");
        creditLineHelper("LOC_NOR");
        creditLineHelper("LOC_POL");
        creditLineHelper("LOC_PTB");
        creditLineHelper("LOC_RUS");
        creditLineHelper("LOC_SPA");
        creditLineHelper("LOC_SRB");
        creditLineHelper("LOC_THA");
        creditLineHelper("LOC_TUR");
        creditLineHelper("LOC_UKR");
        creditLineHelper("LOC_VIE");
        creditLineHelper("LOC_ADDITIONAL");
        creditLineHelper("TEST");
        creditLineHelper("SPECIAL");
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[] = new int[com.megacrit.cardcrawl.core.Settings.GameLanguage.values().length];

        }

        if(logoImg == null)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.core.Settings.GameLanguage[Settings.language.ordinal()])
            {
            default:
                logoImg = ImageMaster.loadImage("images/ui/credits_logo/eng.png");
                break;
            }
    }

    private void creditLineHelper(String id)
    {
        CreditStrings str = CardCrawlGame.languagePack.getCreditString(id);
        lines.add(new CreditLine(str.HEADER, tmpY -= 150F, true));
        for(int i = 0; i < str.NAMES.length; i++)
            lines.add(new CreditLine(str.NAMES[i], tmpY -= 45F, false));

    }

    public void open(boolean playCreditsBgm)
    {
        if(playCreditsBgm)
        {
            showNeowAfter = true;
            CardCrawlGame.playCreditsBgm = false;
            CardCrawlGame.music.playTempBgmInstantly("CREDITS", true);
        } else
        {
            showNeowAfter = false;
        }
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.CREDITS;
        skipTimer = 0.0F;
        isSkippable = false;
        closingSkipMenu = false;
        skipX = SKIP_START_X;
        GameCursor.hidden = true;
        thankYouColor.a = 0.0F;
        screenColor.a = 0.0F;
        thankYouTimer = 3F;
        CardCrawlGame.mainMenuScreen.darken();
        fadeInTimer = 2.0F;
        currentY = SCROLL_START_Y;
        targetY = currentY;
    }

    public void update()
    {
        if(InputHelper.pressedEscape)
        {
            InputHelper.pressedEscape = false;
            close();
        }
        if(InputHelper.isMouseDown_R)
        {
            targetY -= Gdx.graphics.getDeltaTime() * scrollSpeed * 4F;
        } else
        {
            targetY += Gdx.graphics.getDeltaTime() * scrollSpeed;
            if(currentY > END_OF_CREDITS_Y)
            {
                thankYouTimer -= Gdx.graphics.getDeltaTime();
                if(thankYouTimer < 0.0F)
                    thankYouTimer = 0.0F;
                thankYouColor.a = Interpolation.fade.apply(1.0F, 0.0F, thankYouTimer / 3F);
            }
        }
        if(thankYouColor.a == 0.0F)
        {
            if(Gdx.input.isKeyJustPressed(62))
                targetY = SCROLL_START_Y;
            if(InputHelper.scrolledUp)
                targetY -= 100F * Settings.scale;
            else
            if(InputHelper.scrolledDown)
                targetY += 100F * Settings.scale;
            if(CInputActionSet.up.isJustPressed())
                targetY -= 300F * Settings.scale;
            else
            if(CInputActionSet.down.isJustPressed())
                targetY += 300F * Settings.scale;
            else
            if(CInputActionSet.inspectDown.isJustPressed())
                targetY -= 1000F * Settings.scale;
            else
            if(CInputActionSet.inspectUp.isJustPressed())
                targetY += 1000F * Settings.scale;
        }
        currentY = MathHelper.scrollSnapLerpSpeed(currentY, targetY);
        updateFade();
        skipLogic();
    }

    private void skipLogic()
    {
        if(InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())
            if(isSkippable)
                close();
            else
            if(!isSkippable && skipTimer == 0.0F)
            {
                skipTimer = 0.5F;
                skipX = SKIP_END_X;
            }
        if(skipTimer != 0.0F)
        {
            skipTimer -= Gdx.graphics.getDeltaTime();
            if(!isSkippable && !closingSkipMenu)
                skipX = Interpolation.swingIn.apply(SKIP_END_X, SKIP_START_X, skipTimer * 2.0F);
            else
            if(closingSkipMenu)
                skipX = Interpolation.fade.apply(SKIP_START_X, SKIP_END_X, skipTimer * 2.0F);
            else
                skipX = SKIP_END_X;
            if(skipTimer < 0.0F)
                if(!isSkippable && !closingSkipMenu)
                {
                    isSkippable = true;
                    skipTimer = 2.0F;
                } else
                if(!closingSkipMenu)
                {
                    closingSkipMenu = true;
                    isSkippable = false;
                    skipTimer = 0.5F;
                } else
                {
                    isSkippable = false;
                    closingSkipMenu = false;
                    skipTimer = 0.0F;
                }
        }
    }

    public void close()
    {
        if(showNeowAfter)
        {
            CardCrawlGame.music.justFadeOutTempBGM();
            CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.NEOW_SCREEN;
            CardCrawlGame.mainMenuScreen.neowNarrateScreen.open();
        } else
        if(DoorUnlockScreen.show)
        {
            CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.DOOR_UNLOCK;
            CardCrawlGame.mainMenuScreen.doorUnlockScreen.open(false);
        } else
        if(CardCrawlGame.mainMenuScreen.screen == com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.CREDITS)
        {
            CardCrawlGame.mainMenuScreen.lighten();
            CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.MAIN_MENU;
            CardCrawlGame.music.fadeOutTempBGM();
            GameCursor.hidden = false;
        }
    }

    private void updateFade()
    {
        fadeInTimer -= Gdx.graphics.getDeltaTime();
        if(fadeInTimer < 0.0F)
            fadeInTimer = 0.0F;
        screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, fadeInTimer / 2.0F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, screenColor.a));
        sb.draw(logoImg, (float)Settings.WIDTH / 2.0F - 360F, currentY - 360F, 360F, 360F, 720F, 720F, Settings.scale, Settings.scale, 0.0F, 0, 0, 720, 720, false, false);
        CreditLine c;
        for(Iterator iterator = lines.iterator(); iterator.hasNext(); c.render(sb, currentY))
            c = (CreditLine)iterator.next();

        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, THANKS_MSG, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, thankYouColor);
        if(Settings.isTouchScreen)
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, uiStrings.TEXT[2], skipX, 50F * Settings.scale, Settings.BLUE_TEXT_COLOR);
        else
        if(!Settings.isControllerMode)
        {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, uiStrings.TEXT[0], skipX, 50F * Settings.scale, Settings.BLUE_TEXT_COLOR);
        } else
        {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, uiStrings.TEXT[1], skipX + 46F * Settings.scale, 50F * Settings.scale, Settings.BLUE_TEXT_COLOR);
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.select.getKeyImg(), (skipX - 32F) + 10F * Settings.scale, -32F + 44F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);
        }
    }

    private static final UIStrings uiStrings;
    private Color screenColor;
    private float fadeInTimer;
    private float targetY;
    private float currentY;
    private static final float scrollSpeed;
    private static final int W = 720;
    private ArrayList lines;
    private static final float LINE_SPACING = 45F;
    private static final float SECTION_SPACING = 150F;
    private static final float SCROLL_START_Y;
    private static final float THANK_YOU_TIME = 3F;
    private float thankYouTimer;
    private Color thankYouColor;
    private static float END_OF_CREDITS_Y;
    private String THANKS_MSG;
    private static Texture logoImg = null;
    private float skipTimer;
    private static final float SKIP_MENU_UP_DUR = 2F;
    private static final float SKIP_APPEAR_TIME = 0.5F;
    private boolean isSkippable;
    private boolean closingSkipMenu;
    private boolean showNeowAfter;
    private static final float SKIP_START_X;
    private static final float SKIP_END_X;
    private float skipX;
    private float tmpY;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CreditsScreen");
        scrollSpeed = 50F * Settings.scale;
        SCROLL_START_Y = 400F * Settings.scale;
        END_OF_CREDITS_Y = 15000F * Settings.scale;
        SKIP_START_X = -300F * Settings.scale;
        SKIP_END_X = 50F * Settings.scale;
    }
}
