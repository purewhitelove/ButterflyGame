// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SettingsScreen.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.options:
//            OptionsPanel, ConfirmPopup

public class SettingsScreen
{

    public SettingsScreen()
    {
        panel = new OptionsPanel();
        exitPopup = new ConfirmPopup(TEXT[0], TEXT[1], ConfirmPopup.ConfirmType.EXIT);
        abandonPopup = new ConfirmPopup(TEXT[0], TEXT[2], ConfirmPopup.ConfirmType.ABANDON_MID_RUN);
    }

    public void update()
    {
        if(!exitPopup.shown && !abandonPopup.shown)
            panel.update();
        exitPopup.update();
        abandonPopup.update();
    }

    public void open()
    {
        open(true);
    }

    public void open(boolean animated)
    {
        AbstractDungeon.player.releaseCard();
        panel.refresh();
        if(animated)
            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[4]);
        else
            AbstractDungeon.overlayMenu.cancelButton.showInstantly(TEXT[4]);
        CardCrawlGame.sound.play("UI_CLICK_1");
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.SETTINGS;
    }

    public void render(SpriteBatch sb)
    {
        panel.render(sb);
        exitPopup.render(sb);
        abandonPopup.render(sb);
    }

    public void popup(ConfirmPopup.ConfirmType type)
    {
        if(AbstractDungeon.overlayMenu != null)
            AbstractDungeon.overlayMenu.cancelButton.hide();
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType = new int[ConfirmPopup.ConfirmType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType[ConfirmPopup.ConfirmType.ABANDON_MID_RUN.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType[ConfirmPopup.ConfirmType.EXIT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.options.ConfirmPopup.ConfirmType[type.ordinal()])
        {
        case 1: // '\001'
            abandonPopup.show();
            break;

        case 2: // '\002'
            exitPopup.desc = TEXT[1];
            if(AbstractDungeon.player != null && !AbstractDungeon.player.saveFileExists())
                exitPopup.desc = NOT_SAVED_MSG;
            exitPopup.show();
            break;

        default:
            logger.info((new StringBuilder()).append("Unspecified case: ").append(type.name()).toString());
            break;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/options/SettingsScreen.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public OptionsPanel panel;
    public ConfirmPopup exitPopup;
    public ConfirmPopup abandonPopup;
    private static final String NOT_SAVED_MSG;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("SettingsScreen");
        TEXT = uiStrings.TEXT;
        NOT_SAVED_MSG = TEXT[3];
    }
}
