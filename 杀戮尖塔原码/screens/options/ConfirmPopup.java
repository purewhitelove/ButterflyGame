// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfirmPopup.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfirmPopup
{
    public static final class ConfirmType extends Enum
    {

        public static ConfirmType[] values()
        {
            return (ConfirmType[])$VALUES.clone();
        }

        public static ConfirmType valueOf(String name)
        {
            return (ConfirmType)Enum.valueOf(com/megacrit/cardcrawl/screens/options/ConfirmPopup$ConfirmType, name);
        }

        public static final ConfirmType EXIT;
        public static final ConfirmType ABANDON_MID_RUN;
        public static final ConfirmType DELETE_SAVE;
        public static final ConfirmType SKIP_FTUE;
        public static final ConfirmType ABANDON_MAIN_MENU;
        private static final ConfirmType $VALUES[];

        static 
        {
            EXIT = new ConfirmType("EXIT", 0);
            ABANDON_MID_RUN = new ConfirmType("ABANDON_MID_RUN", 1);
            DELETE_SAVE = new ConfirmType("DELETE_SAVE", 2);
            SKIP_FTUE = new ConfirmType("SKIP_FTUE", 3);
            ABANDON_MAIN_MENU = new ConfirmType("ABANDON_MAIN_MENU", 4);
            $VALUES = (new ConfirmType[] {
                EXIT, ABANDON_MID_RUN, DELETE_SAVE, SKIP_FTUE, ABANDON_MAIN_MENU
            });
        }

        private ConfirmType(String s, int i)
        {
            super(s, i);
        }
    }


    public ConfirmPopup(ConfirmType type)
    {
        shown = false;
        slot = -1;
        screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        uiColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        headerColor = Settings.GOLD_COLOR.cpy();
        descriptionColor = Settings.CREAM_COLOR.cpy();
        targetAlpha = 0.0F;
        targetAlpha2 = 0.0F;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType = new int[ConfirmType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType[ConfirmType.ABANDON_MAIN_MENU.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType[ConfirmType.DELETE_SAVE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType[ConfirmType.SKIP_FTUE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType[ConfirmType.EXIT.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ConfirmPopup$ConfirmType[ConfirmType.ABANDON_MID_RUN.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.options.ConfirmPopup.ConfirmType[type.ordinal()])
        {
        case 1: // '\001'
            String TMP[] = CardCrawlGame.languagePack.getUIString("SettingsScreen").TEXT;
            this.type = type;
            title = TMP[0];
            desc = TMP[2];
            initializeButtons();
            break;
        }
    }

    private void initializeButtons()
    {
        if(Settings.isMobile)
        {
            yesHb = new Hitbox(240F * Settings.scale, 110F * Settings.scale);
            noHb = new Hitbox(240F * Settings.scale, 110F * Settings.scale);
            yesHb.move(810F * Settings.xScale, Settings.OPTION_Y - 162F * Settings.scale);
            noHb.move(1112F * Settings.xScale, Settings.OPTION_Y - 162F * Settings.scale);
        } else
        {
            yesHb = new Hitbox(160F * Settings.scale, 70F * Settings.scale);
            noHb = new Hitbox(160F * Settings.scale, 70F * Settings.scale);
            yesHb.move(860F * Settings.xScale, Settings.OPTION_Y - 118F * Settings.scale);
            noHb.move(1062F * Settings.xScale, Settings.OPTION_Y - 118F * Settings.scale);
        }
    }

    public ConfirmPopup()
    {
        shown = false;
        slot = -1;
        screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        uiColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        headerColor = Settings.GOLD_COLOR.cpy();
        descriptionColor = Settings.CREAM_COLOR.cpy();
        targetAlpha = 0.0F;
        targetAlpha2 = 0.0F;
        initializeButtons();
    }

    public ConfirmPopup(String title, String desc, ConfirmType type)
    {
        shown = false;
        slot = -1;
        screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        uiColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        headerColor = Settings.GOLD_COLOR.cpy();
        descriptionColor = Settings.CREAM_COLOR.cpy();
        targetAlpha = 0.0F;
        targetAlpha2 = 0.0F;
        this.type = type;
        this.title = title;
        this.desc = desc;
        initializeButtons();
    }

    public void show()
    {
        if(!shown)
            shown = true;
    }

    public void hide()
    {
        if(shown)
        {
            shown = false;
            if(type == ConfirmType.ABANDON_MID_RUN || type == ConfirmType.ABANDON_MAIN_MENU)
            {
                CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.MAIN_MENU;
                if(AbstractDungeon.overlayMenu != null)
                    AbstractDungeon.overlayMenu.cancelButton.show(TEXT[0]);
            } else
            if(AbstractDungeon.overlayMenu != null)
                AbstractDungeon.overlayMenu.cancelButton.show(TEXT[0]);
        }
    }

    protected void updateTransparency()
    {
        if(shown)
        {
            screenColor.a = MathHelper.fadeLerpSnap(screenColor.a, 0.75F);
            uiColor.a = MathHelper.fadeLerpSnap(uiColor.a, 1.0F);
        } else
        {
            screenColor.a = MathHelper.fadeLerpSnap(screenColor.a, 0.0F);
            uiColor.a = MathHelper.fadeLerpSnap(uiColor.a, 0.0F);
        }
    }

    public void update()
    {
        updateTransparency();
        if(shown)
        {
            updateYes();
            updateNo();
        }
    }

    protected void updateYes()
    {
        yesHb.update();
        if(yesHb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        else
        if(InputHelper.justClickedLeft && yesHb.hovered)
        {
            CardCrawlGame.sound.play("UI_CLICK_1");
            yesHb.clickStarted = true;
        } else
        if(yesHb.clicked)
        {
            yesHb.clicked = false;
            yesButtonEffect();
        }
        if(CInputActionSet.proceed.isJustPressed())
        {
            CInputActionSet.proceed.unpress();
            yesHb.clicked = true;
        }
    }

    protected void updateNo()
    {
        noHb.update();
        if(noHb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        else
        if(noHb.hovered && InputHelper.justClickedLeft)
        {
            CardCrawlGame.sound.play("UI_CLICK_1");
            noHb.clickStarted = true;
        } else
        if(noHb.clicked)
        {
            noHb.clicked = false;
            noButtonEffect();
            hide();
        }
        if(CInputActionSet.cancel.isJustPressed() || InputActionSet.cancel.isJustPressed())
        {
            CInputActionSet.cancel.unpress();
            noButtonEffect();
        }
    }

    protected void noButtonEffect()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.options.ConfirmPopup.ConfirmType[type.ordinal()])
        {
        case 2: // '\002'
            CardCrawlGame.mainMenuScreen.saveSlotScreen.curPop = com.megacrit.cardcrawl.screens.mainMenu.SaveSlotScreen.CurrentPopup.NONE;
            shown = false;
            break;

        case 3: // '\003'
            TipTracker.disableAllFtues();
            hide();
            break;

        case 1: // '\001'
            CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.MAIN_MENU;
            shown = false;
            targetAlpha = 0.0F;
            targetAlpha2 = 0.0F;
            if(AbstractDungeon.overlayMenu != null)
                AbstractDungeon.overlayMenu.cancelButton.show(CardCrawlGame.languagePack.getUIString("SettingsScreen").TEXT[0]);
            break;

        default:
            hide();
            break;
        }
    }

    private void abandonRunFromMainMenu(AbstractPlayer player)
    {
        com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass pClass = player.chosenClass;
        logger.info((new StringBuilder()).append("Abandoning run with ").append(pClass.name()).toString());
        SaveFile file = SaveAndContinue.loadSaveFile(pClass);
        if(Settings.isStandardRun())
            if(file.floor_num >= 16)
            {
                CardCrawlGame.playerPref.putInteger((new StringBuilder()).append(pClass.name()).append("_SPIRITS").toString(), 1);
                CardCrawlGame.playerPref.flush();
            } else
            {
                CardCrawlGame.playerPref.putInteger((new StringBuilder()).append(pClass.name()).append("_SPIRITS").toString(), 0);
                CardCrawlGame.playerPref.flush();
            }
        SaveAndContinue.deleteSave(player);
        if(!file.is_ascension_mode)
            StatsScreen.incrementDeath(player.getCharStat());
    }

    protected void yesButtonEffect()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.options.ConfirmPopup.ConfirmType[type.ordinal()])
        {
        default:
            break;

        case 4: // '\004'
            CardCrawlGame.music.fadeAll();
            hide();
            AbstractDungeon.getCurrRoom().clearEvent();
            AbstractDungeon.closeCurrentScreen();
            CardCrawlGame.startOver();
            if(RestRoom.lastFireSoundId != 0L)
                CardCrawlGame.sound.fadeOut("REST_FIRE_WET", RestRoom.lastFireSoundId);
            if(!AbstractDungeon.player.stance.ID.equals("Neutral") && AbstractDungeon.player.stance != null)
                AbstractDungeon.player.stance.stopIdleSfx();
            break;

        case 1: // '\001'
            AbstractPlayer playerWithSave = CardCrawlGame.characterManager.loadChosenCharacter();
            if(playerWithSave != null)
            {
                String statId;
                if(Settings.isBeta)
                    statId = (new StringBuilder()).append(playerWithSave.getWinStreakKey()).append("_BETA").toString();
                else
                    statId = playerWithSave.getWinStreakKey();
                CardCrawlGame.publisherIntegration.setStat(statId, 0);
                logger.info((new StringBuilder()).append("WIN STREAK  ").append(CardCrawlGame.publisherIntegration.getStat(statId)).toString());
                abandonRunFromMainMenu(playerWithSave);
            }
            CardCrawlGame.mainMenuScreen.abandonedRun = true;
            hide();
            break;

        case 5: // '\005'
            hide();
            AbstractDungeon.closeCurrentScreen();
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
            break;

        case 2: // '\002'
            SaveHelper.deletePrefs(slot);
            CardCrawlGame.mainMenuScreen.saveSlotScreen.deleteSlot(slot);
            CardCrawlGame.mainMenuScreen.saveSlotScreen.curPop = com.megacrit.cardcrawl.screens.mainMenu.SaveSlotScreen.CurrentPopup.NONE;
            CardCrawlGame.playerName = "";
            shown = false;
            boolean allSlotsEmpty = true;
            Iterator iterator = CardCrawlGame.mainMenuScreen.saveSlotScreen.slots.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                SaveSlot s = (SaveSlot)iterator.next();
                if(s.emptySlot)
                    continue;
                allSlotsEmpty = false;
                break;
            } while(true);
            if(allSlotsEmpty)
                CardCrawlGame.mainMenuScreen.saveSlotScreen.cancelButton.hide();
            break;

        case 3: // '\003'
            TipTracker.neverShowAgain("NO_FTUE");
            hide();
            break;
        }
    }

    public void render(SpriteBatch sb)
    {
        renderPopupBg(sb);
        renderText(sb);
        if(shown)
            renderButtons(sb);
        renderControllerUi(sb);
    }

    protected void renderPopupBg(SpriteBatch sb)
    {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(uiColor);
        if(!Settings.isMobile)
            sb.draw(ImageMaster.OPTION_CONFIRM, (float)Settings.WIDTH / 2.0F - 180F, Settings.OPTION_Y - 207F, 180F, 207F, 360F, 414F, Settings.scale, Settings.scale, 0.0F, 0, 0, 360, 414, false, false);
        else
            sb.draw(ImageMaster.OPTION_CONFIRM, (float)Settings.WIDTH / 2.0F - 180F, Settings.OPTION_Y - 207F, 180F, 207F, 360F, 414F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 360, 414, false, false);
    }

    private void renderButtons(SpriteBatch sb)
    {
        if(Settings.isMobile)
            sb.draw(ImageMaster.OPTION_YES, (float)Settings.WIDTH / 2.0F - 86.5F - 150F * Settings.scale, Settings.OPTION_Y - 37F - 170F * Settings.scale, 86.5F, 37F, 173F, 74F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 173, 74, false, false);
        else
            sb.draw(ImageMaster.OPTION_YES, (float)Settings.WIDTH / 2.0F - 86.5F - 100F * Settings.scale, Settings.OPTION_Y - 37F - 120F * Settings.scale, 86.5F, 37F, 173F, 74F, Settings.scale, Settings.scale, 0.0F, 0, 0, 173, 74, false, false);
        if(yesHb.hovered)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, uiColor.a * 0.25F));
            sb.setBlendFunction(770, 1);
            if(Settings.isMobile)
                sb.draw(ImageMaster.OPTION_YES, (float)Settings.WIDTH / 2.0F - 86.5F - 150F * Settings.scale, Settings.OPTION_Y - 37F - 170F * Settings.scale, 86.5F, 37F, 173F, 74F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 173, 74, false, false);
            else
                sb.draw(ImageMaster.OPTION_YES, (float)Settings.WIDTH / 2.0F - 86.5F - 100F * Settings.scale, Settings.OPTION_Y - 37F - 120F * Settings.scale, 86.5F, 37F, 173F, 74F, Settings.scale, Settings.scale, 0.0F, 0, 0, 173, 74, false, false);
            sb.setBlendFunction(770, 771);
            sb.setColor(uiColor);
            if(Settings.isMobile)
                FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[2], (float)Settings.WIDTH / 2.0F - 165F * Settings.scale, Settings.OPTION_Y - 162F * Settings.scale, uiColor, 1.0F);
            else
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[2], (float)Settings.WIDTH / 2.0F - 110F * Settings.scale, Settings.OPTION_Y - 118F * Settings.scale, uiColor, 1.0F);
        } else
        if(Settings.isMobile)
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[2], (float)Settings.WIDTH / 2.0F - 165F * Settings.scale, Settings.OPTION_Y - 162F * Settings.scale, headerColor, 1.0F);
        else
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[2], (float)Settings.WIDTH / 2.0F - 110F * Settings.scale, Settings.OPTION_Y - 118F * Settings.scale, headerColor, 1.0F);
        if(Settings.isMobile)
            sb.draw(ImageMaster.OPTION_NO, ((float)Settings.WIDTH / 2.0F - 80.5F) + 160F * Settings.scale, Settings.OPTION_Y - 37F - 170F * Settings.scale, 80.5F, 37F, 161F, 74F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 161, 74, false, false);
        else
            sb.draw(ImageMaster.OPTION_NO, ((float)Settings.WIDTH / 2.0F - 80.5F) + 106F * Settings.scale, Settings.OPTION_Y - 37F - 120F * Settings.scale, 80.5F, 37F, 161F, 74F, Settings.scale, Settings.scale, 0.0F, 0, 0, 161, 74, false, false);
        if(noHb.hovered)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, uiColor.a * 0.25F));
            sb.setBlendFunction(770, 1);
            if(Settings.isMobile)
                sb.draw(ImageMaster.OPTION_NO, ((float)Settings.WIDTH / 2.0F - 80.5F) + 160F * Settings.scale, Settings.OPTION_Y - 37F - 170F * Settings.scale, 80.5F, 37F, 161F, 74F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 161, 74, false, false);
            else
                sb.draw(ImageMaster.OPTION_NO, ((float)Settings.WIDTH / 2.0F - 80.5F) + 106F * Settings.scale, Settings.OPTION_Y - 37F - 120F * Settings.scale, 80.5F, 37F, 161F, 74F, Settings.scale, Settings.scale, 0.0F, 0, 0, 161, 74, false, false);
            sb.setBlendFunction(770, 771);
            sb.setColor(uiColor);
            if(Settings.isMobile)
                FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[3], (float)Settings.WIDTH / 2.0F + 165F * Settings.scale, Settings.OPTION_Y - 162F * Settings.scale, uiColor, 1.0F);
            else
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[3], (float)Settings.WIDTH / 2.0F + 110F * Settings.scale, Settings.OPTION_Y - 118F * Settings.scale, uiColor, 1.0F);
        } else
        if(Settings.isMobile)
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[3], (float)Settings.WIDTH / 2.0F + 165F * Settings.scale, Settings.OPTION_Y - 162F * Settings.scale, headerColor, 1.0F);
        else
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[3], (float)Settings.WIDTH / 2.0F + 110F * Settings.scale, Settings.OPTION_Y - 118F * Settings.scale, headerColor, 1.0F);
        yesHb.render(sb);
        noHb.render(sb);
    }

    private void renderText(SpriteBatch sb)
    {
        headerColor.a = uiColor.a;
        if(Settings.isMobile)
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, title, (float)Settings.WIDTH / 2.0F, Settings.OPTION_Y + 200F * Settings.scale, headerColor, 1.2F);
        else
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, title, (float)Settings.WIDTH / 2.0F, Settings.OPTION_Y + 126F * Settings.scale, headerColor);
        descriptionColor.a = uiColor.a;
        if(Settings.isMobile)
            FontHelper.renderWrappedText(sb, FontHelper.panelNameFont, desc, (float)Settings.WIDTH / 2.0F, Settings.OPTION_Y + 30F * Settings.scale, 380F * Settings.scale, descriptionColor, 1.0F);
        else
            FontHelper.renderWrappedText(sb, FontHelper.tipBodyFont, desc, (float)Settings.WIDTH / 2.0F, Settings.OPTION_Y + 20F * Settings.scale, 240F * Settings.scale, descriptionColor, 1.0F);
    }

    private void renderControllerUi(SpriteBatch sb)
    {
        if(Settings.isControllerMode)
        {
            sb.draw(CInputActionSet.proceed.getKeyImg(), 770F * Settings.xScale - 32F, Settings.OPTION_Y - 32F - 140F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            sb.draw(CInputActionSet.cancel.getKeyImg(), 1150F * Settings.xScale - 32F, Settings.OPTION_Y - 32F - 140F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
    }

    protected static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/options/ConfirmPopup.getName());
    protected static final String TEXT[];
    public String title;
    public String desc;
    public ConfirmType type;
    public Hitbox yesHb;
    public Hitbox noHb;
    public boolean shown;
    protected int slot;
    protected Color screenColor;
    protected Color uiColor;
    protected Color headerColor;
    protected Color descriptionColor;
    protected float targetAlpha;
    protected float targetAlpha2;
    protected static final int CONFIRM_W = 360;
    protected static final int CONFIRM_H = 414;
    protected static final int YES_W = 173;
    protected static final int NO_W = 161;
    protected static final int BUTTON_H = 74;
    protected static final float SCREEN_DARKNESS = 0.75F;

    static 
    {
        TEXT = CardCrawlGame.languagePack.getUIString("ConfirmPopup").TEXT;
    }
}
