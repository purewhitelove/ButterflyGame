// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MenuPanelScreen.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.DistributorFactory;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            MenuCancelButton, MainMenuPanelButton, MainMenuScreen

public class MenuPanelScreen
{
    public static final class PanelScreen extends Enum
    {

        public static PanelScreen[] values()
        {
            return (PanelScreen[])$VALUES.clone();
        }

        public static PanelScreen valueOf(String name)
        {
            return (PanelScreen)Enum.valueOf(com/megacrit/cardcrawl/screens/mainMenu/MenuPanelScreen$PanelScreen, name);
        }

        public static final PanelScreen PLAY;
        public static final PanelScreen COMPENDIUM;
        public static final PanelScreen STATS;
        public static final PanelScreen SETTINGS;
        private static final PanelScreen $VALUES[];

        static 
        {
            PLAY = new PanelScreen("PLAY", 0);
            COMPENDIUM = new PanelScreen("COMPENDIUM", 1);
            STATS = new PanelScreen("STATS", 2);
            SETTINGS = new PanelScreen("SETTINGS", 3);
            $VALUES = (new PanelScreen[] {
                PLAY, COMPENDIUM, STATS, SETTINGS
            });
        }

        private PanelScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public MenuPanelScreen()
    {
        panels = new ArrayList();
        button = new MenuCancelButton();
    }

    public void open(PanelScreen screenType)
    {
        CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.PANEL_MENU;
        screen = screenType;
        initializePanels();
        button.show(CharacterSelectScreen.TEXT[5]);
        CardCrawlGame.mainMenuScreen.darken();
    }

    public void hide()
    {
    }

    private void initializePanels()
    {
        panels.clear();
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuPanelScreen$PanelScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuPanelScreen$PanelScreen = new int[PanelScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuPanelScreen$PanelScreen[PanelScreen.PLAY.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuPanelScreen$PanelScreen[PanelScreen.COMPENDIUM.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuPanelScreen$PanelScreen[PanelScreen.STATS.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuPanelScreen$PanelScreen[PanelScreen.SETTINGS.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.MenuPanelScreen.PanelScreen[screen.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.PLAY_NORMAL, MainMenuPanelButton.PanelColor.BLUE, (float)Settings.WIDTH / 2.0F - 450F * Settings.scale, PANEL_Y));
            if(CardCrawlGame.mainMenuScreen.statsScreen.statScreenUnlocked())
                panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.PLAY_DAILY, MainMenuPanelButton.PanelColor.BEIGE, (float)Settings.WIDTH / 2.0F, PANEL_Y));
            else
                panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.PLAY_DAILY, MainMenuPanelButton.PanelColor.GRAY, (float)Settings.WIDTH / 2.0F, PANEL_Y));
            if(StatsScreen.all.highestDaily > 0)
                panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.PLAY_CUSTOM, MainMenuPanelButton.PanelColor.RED, (float)Settings.WIDTH / 2.0F + 450F * Settings.scale, PANEL_Y));
            else
                panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.PLAY_CUSTOM, MainMenuPanelButton.PanelColor.GRAY, (float)Settings.WIDTH / 2.0F + 450F * Settings.scale, PANEL_Y));
            break;

        case 2: // '\002'
            panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.INFO_CARD, MainMenuPanelButton.PanelColor.BLUE, (float)Settings.WIDTH / 2.0F - 450F * Settings.scale, PANEL_Y));
            panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.INFO_RELIC, MainMenuPanelButton.PanelColor.BEIGE, (float)Settings.WIDTH / 2.0F, PANEL_Y));
            panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.INFO_POTION, MainMenuPanelButton.PanelColor.RED, (float)Settings.WIDTH / 2.0F + 450F * Settings.scale, PANEL_Y));
            break;

        case 3: // '\003'
            float offset = 225F;
            if(DistributorFactory.isLeaderboardEnabled())
                offset = 450F;
            panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.STAT_CHAR, MainMenuPanelButton.PanelColor.BLUE, (float)Settings.WIDTH / 2.0F - offset * Settings.scale, PANEL_Y));
            if(DistributorFactory.isLeaderboardEnabled())
                panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.STAT_LEADERBOARDS, MainMenuPanelButton.PanelColor.BEIGE, (float)Settings.WIDTH / 2.0F, PANEL_Y));
            panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.STAT_HISTORY, MainMenuPanelButton.PanelColor.RED, (float)Settings.WIDTH / 2.0F + offset * Settings.scale, PANEL_Y));
            break;

        case 4: // '\004'
            panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.SETTINGS_GAME, MainMenuPanelButton.PanelColor.BLUE, (float)Settings.WIDTH / 2.0F - 450F * Settings.scale, PANEL_Y));
            panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.SETTINGS_INPUT, MainMenuPanelButton.PanelColor.BLUE, (float)Settings.WIDTH / 2.0F, PANEL_Y));
            panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.SETTINGS_CREDITS, MainMenuPanelButton.PanelColor.BLUE, (float)Settings.WIDTH / 2.0F + 450F * Settings.scale, PANEL_Y));
            break;
        }
    }

    public void update()
    {
        button.update();
        if(button.hb.clicked || InputHelper.pressedEscape)
        {
            InputHelper.pressedEscape = false;
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
            button.hide();
            CardCrawlGame.mainMenuScreen.lighten();
        }
        MainMenuPanelButton panel;
        for(Iterator iterator = panels.iterator(); iterator.hasNext(); panel.update())
            panel = (MainMenuPanelButton)iterator.next();

    }

    public void render(SpriteBatch sb)
    {
        MainMenuPanelButton panel;
        for(Iterator iterator = panels.iterator(); iterator.hasNext(); panel.render(sb))
            panel = (MainMenuPanelButton)iterator.next();

        button.render(sb);
    }

    public void refresh()
    {
        button.hideInstantly();
        button.show(CharacterSelectScreen.TEXT[5]);
        CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.PANEL_MENU;
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public ArrayList panels;
    private PanelScreen screen;
    private static final float PANEL_Y;
    public MenuCancelButton button;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("MenuPanels");
        TEXT = uiStrings.TEXT;
        PANEL_Y = (float)Settings.HEIGHT / 2.0F;
    }
}
