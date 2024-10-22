// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColorTabBar.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            TabBarListener

public class ColorTabBar
{
    public static final class CurrentTab extends Enum
    {

        public static CurrentTab[] values()
        {
            return (CurrentTab[])$VALUES.clone();
        }

        public static CurrentTab valueOf(String name)
        {
            return (CurrentTab)Enum.valueOf(com/megacrit/cardcrawl/screens/mainMenu/ColorTabBar$CurrentTab, name);
        }

        public static final CurrentTab RED;
        public static final CurrentTab GREEN;
        public static final CurrentTab BLUE;
        public static final CurrentTab PURPLE;
        public static final CurrentTab COLORLESS;
        public static final CurrentTab CURSE;
        private static final CurrentTab $VALUES[];

        static 
        {
            RED = new CurrentTab("RED", 0);
            GREEN = new CurrentTab("GREEN", 1);
            BLUE = new CurrentTab("BLUE", 2);
            PURPLE = new CurrentTab("PURPLE", 3);
            COLORLESS = new CurrentTab("COLORLESS", 4);
            CURSE = new CurrentTab("CURSE", 5);
            $VALUES = (new CurrentTab[] {
                RED, GREEN, BLUE, PURPLE, COLORLESS, CURSE
            });
        }

        private CurrentTab(String s, int i)
        {
            super(s, i);
        }
    }


    public ColorTabBar(TabBarListener delegate)
    {
        curTab = CurrentTab.RED;
        float w = 200F * Settings.scale;
        float h = 50F * Settings.scale;
        redHb = new Hitbox(w, h);
        greenHb = new Hitbox(w, h);
        blueHb = new Hitbox(w, h);
        purpleHb = new Hitbox(w, h);
        colorlessHb = new Hitbox(w, h);
        curseHb = new Hitbox(w, h);
        _flddelegate = delegate;
        viewUpgradeHb = new Hitbox(360F * Settings.scale, 48F * Settings.scale);
    }

    public void update(float y)
    {
        float x = 470F * Settings.xScale;
        redHb.move(x, y + 50F * Settings.scale);
        greenHb.move(x += TAB_SPACING, y + 50F * Settings.scale);
        blueHb.move(x += TAB_SPACING, y + 50F * Settings.scale);
        purpleHb.move(x += TAB_SPACING, y + 50F * Settings.scale);
        colorlessHb.move(x += TAB_SPACING, y + 50F * Settings.scale);
        curseHb.move(x += TAB_SPACING, y + 50F * Settings.scale);
        viewUpgradeHb.move(1410F * Settings.xScale, y);
        redHb.update();
        greenHb.update();
        blueHb.update();
        purpleHb.update();
        colorlessHb.update();
        curseHb.update();
        viewUpgradeHb.update();
        if(redHb.justHovered || greenHb.justHovered || blueHb.justHovered || colorlessHb.justHovered || curseHb.justHovered || purpleHb.justHovered)
            CardCrawlGame.sound.playA("UI_HOVER", -0.4F);
        if(Settings.isControllerMode)
            if(CInputActionSet.pageRightViewExhaust.isJustPressed())
            {
                if(curTab == CurrentTab.RED)
                    curTab = CurrentTab.GREEN;
                else
                if(curTab == CurrentTab.GREEN)
                    curTab = CurrentTab.BLUE;
                else
                if(curTab == CurrentTab.BLUE)
                    curTab = CurrentTab.PURPLE;
                else
                if(curTab == CurrentTab.PURPLE)
                    curTab = CurrentTab.COLORLESS;
                else
                if(curTab == CurrentTab.COLORLESS)
                    curTab = CurrentTab.CURSE;
                else
                if(curTab == CurrentTab.CURSE)
                    curTab = CurrentTab.RED;
                _flddelegate.didChangeTab(this, curTab);
            } else
            if(CInputActionSet.pageLeftViewDeck.isJustPressed())
            {
                if(curTab == CurrentTab.RED)
                    curTab = CurrentTab.CURSE;
                else
                if(curTab == CurrentTab.GREEN)
                    curTab = CurrentTab.RED;
                else
                if(curTab == CurrentTab.BLUE)
                    curTab = CurrentTab.GREEN;
                else
                if(curTab == CurrentTab.PURPLE)
                    curTab = CurrentTab.BLUE;
                else
                if(curTab == CurrentTab.COLORLESS)
                    curTab = CurrentTab.PURPLE;
                else
                if(curTab == CurrentTab.CURSE)
                    curTab = CurrentTab.COLORLESS;
                _flddelegate.didChangeTab(this, curTab);
            }
        if(InputHelper.justClickedLeft)
        {
            CurrentTab oldTab = curTab;
            if(redHb.hovered)
                curTab = CurrentTab.RED;
            else
            if(greenHb.hovered)
                curTab = CurrentTab.GREEN;
            else
            if(blueHb.hovered)
                curTab = CurrentTab.BLUE;
            else
            if(purpleHb.hovered)
                curTab = CurrentTab.PURPLE;
            else
            if(colorlessHb.hovered)
                curTab = CurrentTab.COLORLESS;
            else
            if(curseHb.hovered)
                curTab = CurrentTab.CURSE;
            if(oldTab != curTab)
                _flddelegate.didChangeTab(this, curTab);
        }
        if(viewUpgradeHb.justHovered)
            CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
        if(viewUpgradeHb.hovered && InputHelper.justClickedLeft)
            viewUpgradeHb.clickStarted = true;
        if(viewUpgradeHb.clicked || viewUpgradeHb.hovered && CInputActionSet.select.isJustPressed())
        {
            viewUpgradeHb.clicked = false;
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.2F);
            SingleCardViewPopup.isViewingUpgrade = !SingleCardViewPopup.isViewingUpgrade;
        }
    }

    public Hitbox hoveredHb()
    {
        if(redHb.hovered)
            return redHb;
        if(greenHb.hovered)
            return greenHb;
        if(blueHb.hovered)
            return blueHb;
        if(purpleHb.hovered)
            return purpleHb;
        if(colorlessHb.hovered)
            return colorlessHb;
        if(curseHb.hovered)
            return curseHb;
        if(viewUpgradeHb.hovered)
            return viewUpgradeHb;
        else
            return null;
    }

    public Color getBarColor()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab = new int[CurrentTab.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[CurrentTab.RED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[CurrentTab.GREEN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[CurrentTab.BLUE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[CurrentTab.PURPLE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[CurrentTab.COLORLESS.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[CurrentTab.CURSE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab[curTab.ordinal()])
        {
        case 1: // '\001'
            return new Color(0.5F, 0.1F, 0.1F, 1.0F);

        case 2: // '\002'
            return new Color(0.25F, 0.55F, 0.0F, 1.0F);

        case 3: // '\003'
            return new Color(0.01F, 0.34F, 0.52F, 1.0F);

        case 4: // '\004'
            return new Color(0.37F, 0.22F, 0.49F, 1.0F);

        case 5: // '\005'
            return new Color(0.4F, 0.4F, 0.4F, 1.0F);

        case 6: // '\006'
            return new Color(0.18F, 0.18F, 0.16F, 1.0F);
        }
        return Color.WHITE;
    }

    public void render(SpriteBatch sb, float y)
    {
        sb.setColor(Color.GRAY);
        if(curTab != CurrentTab.CURSE)
            renderTab(sb, ImageMaster.COLOR_TAB_CURSE, curseHb.cX, y, CardLibraryScreen.TEXT[5], true);
        if(curTab != CurrentTab.COLORLESS)
            renderTab(sb, ImageMaster.COLOR_TAB_COLORLESS, colorlessHb.cX, y, CardLibraryScreen.TEXT[4], true);
        if(curTab != CurrentTab.BLUE)
            renderTab(sb, ImageMaster.COLOR_TAB_BLUE, blueHb.cX, y, CardLibraryScreen.TEXT[3], true);
        if(curTab != CurrentTab.PURPLE)
            renderTab(sb, ImageMaster.COLOR_TAB_PURPLE, purpleHb.cX, y, CardLibraryScreen.TEXT[8], true);
        if(curTab != CurrentTab.GREEN)
            renderTab(sb, ImageMaster.COLOR_TAB_GREEN, greenHb.cX, y, CardLibraryScreen.TEXT[2], true);
        if(curTab != CurrentTab.RED)
            renderTab(sb, ImageMaster.COLOR_TAB_RED, redHb.cX, y, CardLibraryScreen.TEXT[1], true);
        sb.setColor(getBarColor());
        sb.draw(ImageMaster.COLOR_TAB_BAR, (float)Settings.WIDTH / 2.0F - 667F, y - 51F, 667F, 51F, 1334F, 102F, Settings.xScale, Settings.scale, 0.0F, 0, 0, 1334, 102, false, false);
        sb.setColor(Color.WHITE);
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab[curTab.ordinal()])
        {
        case 1: // '\001'
            renderTab(sb, ImageMaster.COLOR_TAB_RED, redHb.cX, y, CardLibraryScreen.TEXT[1], false);
            break;

        case 2: // '\002'
            renderTab(sb, ImageMaster.COLOR_TAB_GREEN, greenHb.cX, y, CardLibraryScreen.TEXT[2], false);
            break;

        case 3: // '\003'
            renderTab(sb, ImageMaster.COLOR_TAB_BLUE, blueHb.cX, y, CardLibraryScreen.TEXT[3], false);
            break;

        case 4: // '\004'
            renderTab(sb, ImageMaster.COLOR_TAB_PURPLE, purpleHb.cX, y, CardLibraryScreen.TEXT[8], false);
            break;

        case 5: // '\005'
            renderTab(sb, ImageMaster.COLOR_TAB_COLORLESS, colorlessHb.cX, y, CardLibraryScreen.TEXT[4], false);
            break;

        case 6: // '\006'
            renderTab(sb, ImageMaster.COLOR_TAB_CURSE, curseHb.cX, y, CardLibraryScreen.TEXT[5], false);
            break;
        }
        renderViewUpgrade(sb, y);
        redHb.render(sb);
        greenHb.render(sb);
        blueHb.render(sb);
        purpleHb.render(sb);
        colorlessHb.render(sb);
        curseHb.render(sb);
        viewUpgradeHb.render(sb);
    }

    private void renderTab(SpriteBatch sb, Texture img, float x, float y, String label, boolean selected)
    {
        sb.draw(img, x - 137F, (y - 34F) + 53F * Settings.scale, 137F, 34F, 274F, 68F, Settings.xScale, Settings.scale, 0.0F, 0, 0, 274, 68, false, false);
        Color c = Settings.GOLD_COLOR;
        if(selected)
            c = Color.GRAY;
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, label, x, y + 50F * Settings.scale, c, 0.9F);
    }

    private void renderViewUpgrade(SpriteBatch sb, float y)
    {
        Color c = Settings.CREAM_COLOR;
        if(viewUpgradeHb.hovered)
            c = Settings.GOLD_COLOR;
        FontHelper.renderFontRightAligned(sb, FontHelper.topPanelInfoFont, CardLibraryScreen.TEXT[7], 1546F * Settings.xScale, y, c);
        Texture img = SingleCardViewPopup.isViewingUpgrade ? ImageMaster.COLOR_TAB_BOX_TICKED : ImageMaster.COLOR_TAB_BOX_UNTICKED;
        sb.setColor(c);
        sb.draw(img, 1532F * Settings.xScale - FontHelper.getSmartWidth(FontHelper.topPanelInfoFont, CardLibraryScreen.TEXT[7], 9999F, 0.0F) - 24F, y - 24F, 24F, 24F, 48F, 48F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
    }

    private static final float TAB_SPACING;
    private static final int BAR_W = 1334;
    private static final int BAR_H = 102;
    private static final int TAB_W = 274;
    private static final int TAB_H = 68;
    private static final int TICKBOX_W = 48;
    public Hitbox redHb;
    public Hitbox greenHb;
    public Hitbox blueHb;
    public Hitbox purpleHb;
    public Hitbox colorlessHb;
    public Hitbox curseHb;
    public Hitbox viewUpgradeHb;
    public CurrentTab curTab;
    private TabBarListener _flddelegate;

    static 
    {
        TAB_SPACING = 198F * Settings.xScale;
    }
}
