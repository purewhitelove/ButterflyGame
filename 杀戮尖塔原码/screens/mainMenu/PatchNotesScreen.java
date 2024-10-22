// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PatchNotesScreen.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.io.*;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            MenuCancelButton, ScrollBar, ScrollBarListener, MainMenuScreen

public class PatchNotesScreen
    implements ScrollBarListener
{

    public PatchNotesScreen()
    {
        scrollY = START_Y;
        targetY = scrollY;
        scrollLowerBound = (float)Settings.HEIGHT - 300F * Settings.scale;
        scrollUpperBound = 2400F * Settings.scale;
        button = new MenuCancelButton();
        grabbedScreen = false;
        grabStartY = 0.0F;
        scrollBar = new ScrollBar(this);
    }

    public void open()
    {
        button.show(TEXT[0]);
        targetY = scrollLowerBound;
        scrollY = (float)Settings.HEIGHT - 400F * Settings.scale;
        CardCrawlGame.mainMenuScreen.darken();
        CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.PATCH_NOTES;
        if(text == null)
        {
            if(Settings.isBeta)
                log = Gdx.files.internal((new StringBuilder()).append("changelog").append(File.separator).append("notes.txt").toString());
            else
                log = Gdx.files.internal((new StringBuilder()).append("changelog").append(File.separator).append("notes_main.txt").toString());
            openLog();
            scrollUpperBound = calculateHeight() + 300F * Settings.scale;
            if(scrollUpperBound < scrollLowerBound)
                scrollUpperBound = scrollLowerBound + 100F * Settings.scale;
        } else
        {
            scrollY = START_Y;
            targetY = scrollY;
        }
    }

    private float calculateHeight()
    {
        return FontHelper.getHeight(FontHelper.tipBodyFont, text, 1.0F);
    }

    private void openLog()
    {
        BufferedReader br;
        Throwable throwable;
        br = new BufferedReader(log.reader());
        throwable = null;
        try
        {
            StringBuilder sb = new StringBuilder();
            String line = "";
            try
            {
                line = br.readLine();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            for(; line != null; line = br.readLine())
            {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            text = sb.toString();
            br.close();
        }
        catch(Throwable throwable2)
        {
            throwable = throwable2;
            throw throwable2;
        }
        if(br != null)
            if(throwable != null)
                try
                {
                    br.close();
                }
                catch(Throwable throwable1)
                {
                    throwable.addSuppressed(throwable1);
                }
            else
                br.close();
        break MISSING_BLOCK_LABEL_164;
        Exception exception;
        exception;
        if(br != null)
            if(throwable != null)
                try
                {
                    br.close();
                }
                catch(Throwable throwable3)
                {
                    throwable.addSuppressed(throwable3);
                }
            else
                br.close();
        throw exception;
        IOException e;
        e;
        e.printStackTrace();
    }

    public void update()
    {
        if(Settings.isControllerMode)
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                targetY += Settings.SCROLL_SPEED * 2.0F;
            else
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                targetY -= Settings.SCROLL_SPEED * 2.0F;
            else
            if(CInputActionSet.drawPile.isJustPressed())
                targetY -= Settings.SCROLL_SPEED * 10F;
            else
            if(CInputActionSet.discardPile.isJustPressed())
                targetY += Settings.SCROLL_SPEED * 10F;
        button.update();
        if(button.hb.clicked || InputHelper.pressedEscape)
        {
            InputHelper.pressedEscape = false;
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
            button.hide();
            CardCrawlGame.mainMenuScreen.lighten();
        }
        boolean isDraggingScrollBar = scrollBar.update();
        if(!isDraggingScrollBar)
            updateScrolling();
        InputHelper.justClickedLeft = false;
    }

    private void updateScrolling()
    {
        int y = InputHelper.mY;
        if(!grabbedScreen)
        {
            if(InputHelper.scrolledDown)
                targetY += Settings.SCROLL_SPEED;
            else
            if(InputHelper.scrolledUp)
                targetY -= Settings.SCROLL_SPEED;
            if(InputHelper.justClickedLeft)
            {
                grabbedScreen = true;
                grabStartY = (float)y - targetY;
            }
        } else
        if(InputHelper.isMouseDown)
            targetY = (float)y - grabStartY;
        else
            grabbedScreen = false;
        scrollY = MathHelper.scrollSnapLerpSpeed(scrollY, targetY);
        resetScrolling();
        updateBarPosition();
    }

    private void resetScrolling()
    {
        if(targetY < scrollLowerBound)
            targetY = MathHelper.scrollSnapLerpSpeed(targetY, scrollLowerBound);
        else
        if(targetY > scrollUpperBound)
            targetY = MathHelper.scrollSnapLerpSpeed(targetY, scrollUpperBound);
    }

    public void render(SpriteBatch sb)
    {
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, TEXT[1], 250F * Settings.scale, scrollY + 70F * Settings.scale, LINE_WIDTH, LINE_SPACING, Settings.CREAM_COLOR);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, text, 300F * Settings.scale, scrollY, Settings.CREAM_COLOR);
        button.render(sb);
        scrollBar.render(sb);
    }

    public void scrolledUsingBar(float newPercent)
    {
        scrollY = MathHelper.valueFromPercentBetween(scrollLowerBound, scrollUpperBound, newPercent);
        targetY = scrollY;
        updateBarPosition();
    }

    private void updateBarPosition()
    {
        float percent = MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, scrollY);
        scrollBar.parentScrolledToPercent(percent);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static String text = null;
    private FileHandle log;
    private static final float START_Y;
    private float scrollY;
    private float targetY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    public MenuCancelButton button;
    private static final float LINE_WIDTH;
    private static final float LINE_SPACING;
    private boolean grabbedScreen;
    private float grabStartY;
    private ScrollBar scrollBar;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("PatchNotesScreen");
        TEXT = uiStrings.TEXT;
        START_Y = (float)Settings.HEIGHT - 300F * Settings.scale;
        LINE_WIDTH = 1200F * Settings.scale;
        LINE_SPACING = 30F * Settings.scale;
    }
}
