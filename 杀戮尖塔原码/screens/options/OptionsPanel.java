// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OptionsPanel.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.screens.DisplayOption;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.RestartForChangesEffect;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.screens.options:
//            ToggleButton, DropdownMenu, Slider, ExitGameButton, 
//            AbandonRunButton, DropdownMenuListener, InputSettingsScreen

public class OptionsPanel
    implements DropdownMenuListener, HitboxListener
{

    public OptionsPanel()
    {
        playtesterToggle = null;
        effects = new ArrayList();
        currentHb = null;
        fsToggle = new ToggleButton(TOGGLE_X_LEFT, 98F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.FULL_SCREEN, true);
        wfsToggle = new ToggleButton(TOGGLE_X_LEFT, 64F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.W_FULL_SCREEN, true);
        ssToggle = new ToggleButton(TOGGLE_X_LEFT, 30F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.SCREEN_SHAKE);
        vSyncToggle = new ToggleButton(TOGGLE_X_LEFT_2, 30F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.V_SYNC);
        resoDropdown = new DropdownMenu(this, getResolutionLabels(), FontHelper.tipBodyFont, Settings.CREAM_COLOR);
        resetResolutionDropdownSelection();
        fpsDropdown = new DropdownMenu(this, FRAMERATE_LABELS, FontHelper.tipBodyFont, Settings.CREAM_COLOR);
        resetFpsDropdownSelection();
        sumToggle = new ToggleButton(TOGGLE_X_LEFT, -122F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.SUM_DMG, true);
        blockToggle = new ToggleButton(TOGGLE_X_LEFT, -156F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.BLOCK_DMG, true);
        confirmToggle = new ToggleButton(TOGGLE_X_LEFT, -190F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.HAND_CONF, true);
        effectsToggle = new ToggleButton(TOGGLE_X_LEFT, -224F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.EFFECTS, true);
        fastToggle = new ToggleButton(TOGGLE_X_LEFT, -258F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.FAST_MODE, true);
        cardKeyOverlayToggle = new ToggleButton(TOGGLE_X_LEFT, -292F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.SHOW_CARD_HOTKEYS, true);
        ambienceToggle = new ToggleButton(TOGGLE_X_RIGHT, 58F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.AMBIENCE_ON, true);
        muteBgToggle = new ToggleButton(TOGGLE_X_RIGHT, 24F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.MUTE_IF_BG, true);
        masterSlider = new Slider(SCREEN_CENTER_Y + 186F * Settings.scale, Settings.MASTER_VOLUME, Slider.SliderType.MASTER);
        bgmSlider = new Slider(SCREEN_CENTER_Y + 142F * Settings.scale, Settings.MUSIC_VOLUME, Slider.SliderType.BGM);
        sfxSlider = new Slider(SCREEN_CENTER_Y + 98F * Settings.scale, Settings.SOUND_VOLUME, Slider.SliderType.SFX);
        if(canTogglePlaytesterArt())
            playtesterToggle = new ToggleButton(TOGGLE_X_RIGHT, -190F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.PLAYTESTER_ART, true);
        uploadToggle = new ToggleButton(TOGGLE_X_RIGHT, -224F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.UPLOAD_DATA, true);
        longPressToggle = new ToggleButton(TOGGLE_X_RIGHT, -258F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.LONG_PRESS, true);
        bigTextToggle = new ToggleButton(TOGGLE_X_RIGHT, -292F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.BIG_TEXT, true);
        languageLabels = languageLabels();
        languageDropdown = new DropdownMenu(this, languageLabels, FontHelper.tipBodyFont, Settings.CREAM_COLOR, 9);
        resetLanguageDropdownSelection();
        exitBtn = new ExitGameButton();
        abandonBtn = new AbandonRunButton();
        inputSettingsHb = new Hitbox(360F * Settings.scale, 70F * Settings.scale);
        inputSettingsHb.move(918F * Settings.xScale, Settings.OPTION_Y + 382F * Settings.scale);
    }

    public void update()
    {
        updateControllerInput();
        if(CardCrawlGame.isInARun())
            abandonBtn.update();
        exitBtn.update();
        if(Settings.isControllerMode && CInputActionSet.pageRightViewExhaust.isJustPressed())
            clicked(inputSettingsHb);
        inputSettingsHb.encapsulatedUpdate(this);
        if(fpsDropdown.isOpen)
            fpsDropdown.update();
        else
        if(resoDropdown.isOpen)
            resoDropdown.update();
        else
        if(languageDropdown.isOpen)
        {
            languageDropdown.update();
        } else
        {
            updateEffects();
            updateGraphics();
            updateSound();
            updatePreferences();
            updateMiscellaneous();
        }
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || resoDropdown.isOpen || fpsDropdown.isOpen || languageDropdown.isOpen)
            return;
        if(AbstractDungeon.player != null && AbstractDungeon.player.viewingRelics)
            return;
        if(resoDropdown.getHitbox().hovered)
        {
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(masterSlider.bgHb);
                currentHb = masterSlider.bgHb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(fpsDropdown.getHitbox());
                currentHb = fpsDropdown.getHitbox();
            }
        } else
        if(fpsDropdown.getHitbox().hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(resoDropdown.getHitbox());
                currentHb = resoDropdown.getHitbox();
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(fsToggle.hb);
                currentHb = fsToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(bgmSlider.bgHb);
                currentHb = bgmSlider.bgHb;
            }
        } else
        if(fsToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(resoDropdown.getHitbox());
                currentHb = resoDropdown.getHitbox();
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(wfsToggle.hb);
                currentHb = wfsToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(sfxSlider.bgHb);
                currentHb = sfxSlider.bgHb;
            }
        } else
        if(wfsToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(fsToggle.hb);
                currentHb = fsToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(ssToggle.hb);
                currentHb = ssToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(ambienceToggle.hb);
                currentHb = ambienceToggle.hb;
            }
        } else
        if(ssToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(wfsToggle.hb);
                currentHb = wfsToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(sumToggle.hb);
                currentHb = sumToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            {
                CInputHelper.setCursor(vSyncToggle.hb);
                currentHb = vSyncToggle.hb;
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(muteBgToggle.hb);
                currentHb = muteBgToggle.hb;
            }
        } else
        if(vSyncToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(wfsToggle.hb);
                currentHb = wfsToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(sumToggle.hb);
                currentHb = sumToggle.hb;
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(ssToggle.hb);
                currentHb = ssToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            {
                CInputHelper.setCursor(muteBgToggle.hb);
                currentHb = muteBgToggle.hb;
            }
        } else
        if(sumToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(ssToggle.hb);
                currentHb = ssToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(blockToggle.hb);
                currentHb = blockToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(languageDropdown.getHitbox());
                currentHb = languageDropdown.getHitbox();
            }
        } else
        if(blockToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(sumToggle.hb);
                currentHb = sumToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(confirmToggle.hb);
                currentHb = confirmToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(languageDropdown.getHitbox());
                currentHb = languageDropdown.getHitbox();
            }
        } else
        if(confirmToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(blockToggle.hb);
                currentHb = blockToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(effectsToggle.hb);
                currentHb = effectsToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                if(playtesterToggle != null)
                {
                    CInputHelper.setCursor(playtesterToggle.hb);
                    currentHb = playtesterToggle.hb;
                } else
                {
                    CInputHelper.setCursor(uploadToggle.hb);
                    currentHb = uploadToggle.hb;
                }
        } else
        if(effectsToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(confirmToggle.hb);
                currentHb = confirmToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(fastToggle.hb);
                currentHb = fastToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(uploadToggle.hb);
                currentHb = uploadToggle.hb;
            }
        } else
        if(fastToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(effectsToggle.hb);
                currentHb = effectsToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(cardKeyOverlayToggle.hb);
                currentHb = cardKeyOverlayToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(longPressToggle.hb);
                currentHb = longPressToggle.hb;
            }
        } else
        if(cardKeyOverlayToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(fastToggle.hb);
                currentHb = fastToggle.hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(bigTextToggle.hb);
                currentHb = bigTextToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(masterSlider.bgHb);
                currentHb = masterSlider.bgHb;
            }
        } else
        if(masterSlider.bgHb.hovered)
        {
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(bgmSlider.bgHb);
                currentHb = bgmSlider.bgHb;
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(resoDropdown.getHitbox());
                currentHb = resoDropdown.getHitbox();
            }
        } else
        if(bgmSlider.bgHb.hovered)
        {
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(sfxSlider.bgHb);
                currentHb = sfxSlider.bgHb;
            } else
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(masterSlider.bgHb);
                currentHb = masterSlider.bgHb;
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(fpsDropdown.getHitbox());
                currentHb = fpsDropdown.getHitbox();
            }
        } else
        if(sfxSlider.bgHb.hovered)
        {
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(ambienceToggle.hb);
                currentHb = ambienceToggle.hb;
            } else
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(bgmSlider.bgHb);
                currentHb = bgmSlider.bgHb;
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(fsToggle.hb);
                currentHb = fsToggle.hb;
            }
        } else
        if(ambienceToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(sfxSlider.bgHb);
                currentHb = sfxSlider.bgHb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(muteBgToggle.hb);
                currentHb = muteBgToggle.hb;
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(wfsToggle.hb);
                currentHb = wfsToggle.hb;
            }
        } else
        if(muteBgToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(ambienceToggle.hb);
                currentHb = ambienceToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(languageDropdown.getHitbox());
                currentHb = languageDropdown.getHitbox();
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(vSyncToggle.hb);
                currentHb = vSyncToggle.hb;
            }
        } else
        if(languageDropdown.getHitbox().hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(muteBgToggle.hb);
                currentHb = muteBgToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                if(canTogglePlaytesterArt())
                {
                    CInputHelper.setCursor(playtesterToggle.hb);
                    currentHb = playtesterToggle.hb;
                } else
                {
                    CInputHelper.setCursor(uploadToggle.hb);
                    currentHb = uploadToggle.hb;
                }
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(sumToggle.hb);
                currentHb = sumToggle.hb;
            }
        } else
        if(playtesterToggle != null && playtesterToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(languageDropdown.getHitbox());
                currentHb = languageDropdown.getHitbox();
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(uploadToggle.hb);
                currentHb = uploadToggle.hb;
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(confirmToggle.hb);
                currentHb = confirmToggle.hb;
            }
        } else
        if(uploadToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                if(playtesterToggle != null)
                {
                    CInputHelper.setCursor(playtesterToggle.hb);
                    currentHb = playtesterToggle.hb;
                } else
                {
                    CInputHelper.setCursor(languageDropdown.getHitbox());
                    currentHb = languageDropdown.getHitbox();
                }
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(effectsToggle.hb);
                currentHb = effectsToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(longPressToggle.hb);
                currentHb = longPressToggle.hb;
            }
        } else
        if(longPressToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(uploadToggle.hb);
                currentHb = uploadToggle.hb;
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(bigTextToggle.hb);
                currentHb = bigTextToggle.hb;
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(fastToggle.hb);
                currentHb = fastToggle.hb;
            }
        } else
        if(bigTextToggle.hb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(longPressToggle.hb);
                currentHb = longPressToggle.hb;
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                CInputHelper.setCursor(cardKeyOverlayToggle.hb);
                currentHb = cardKeyOverlayToggle.hb;
            }
        } else
        {
            CInputHelper.setCursor(resoDropdown.getHitbox());
            currentHb = resoDropdown.getHitbox();
        }
    }

    private void updateEffects()
    {
        Iterator c = effects.iterator();
        do
        {
            if(!c.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)c.next();
            e.update();
            if(e.isDone)
                c.remove();
        } while(true);
    }

    private void updateGraphics()
    {
        fsToggle.update();
        wfsToggle.update();
        ssToggle.update();
        vSyncToggle.update();
        resoDropdown.update();
        fpsDropdown.update();
        if(fsToggle.hb.hovered)
            TipHelper.renderGenericTip((float)Settings.WIDTH * 0.03F, fsToggle.hb.cY + 50F * Settings.scale, LABEL[1], MSG[1]);
        else
        if(wfsToggle.hb.hovered)
            TipHelper.renderGenericTip((float)Settings.WIDTH * 0.03F, wfsToggle.hb.cY + 50F * Settings.scale, LABEL[2], MSG[2]);
        else
        if(fpsDropdown.getHitbox().hovered)
            TipHelper.renderGenericTip((float)Settings.WIDTH * 0.03F, fpsDropdown.getHitbox().cY + 30F * Settings.scale, LABEL[3], MSG[3]);
    }

    private void updateSound()
    {
        ambienceToggle.update();
        muteBgToggle.update();
        masterSlider.update();
        bgmSlider.update();
        sfxSlider.update();
    }

    private void updatePreferences()
    {
        sumToggle.update();
        blockToggle.update();
        confirmToggle.update();
        effectsToggle.update();
        fastToggle.update();
        cardKeyOverlayToggle.update();
    }

    private void updateMiscellaneous()
    {
        uploadToggle.update();
        if(playtesterToggle != null)
            playtesterToggle.update();
        if(uploadToggle.hb.hovered)
            TipHelper.renderGenericTip((float)Settings.WIDTH * 0.03F, (float)Settings.HEIGHT / 2.0F, LABEL[0], MSG[0]);
        longPressToggle.update();
        bigTextToggle.update();
        languageDropdown.update();
    }

    public void render(SpriteBatch sb)
    {
        renderBg(sb);
        renderBanner(sb);
        renderGraphics(sb);
        renderSound(sb);
        renderPreferences(sb);
        renderMiscellaneous(sb);
        if(CardCrawlGame.isInARun())
            abandonBtn.render(sb);
        exitBtn.render(sb);
        if(!Settings.isConsoleBuild && !Settings.isMobile)
            languageDropdown.render(sb, 1150F * Settings.xScale, SCREEN_CENTER_Y - 115F * Settings.scale);
        else
            languageDropdown.render(sb, 1180F * Settings.xScale, SCREEN_CENTER_Y - 100F * Settings.scale);
        if(resoDropdown.isOpen)
        {
            fpsDropdown.render(sb, LEFT_TOGGLE_X, SCREEN_CENTER_Y + 160F * Settings.scale);
            resoDropdown.render(sb, LEFT_TOGGLE_X, SCREEN_CENTER_Y + 206F * Settings.scale);
        } else
        {
            resoDropdown.render(sb, LEFT_TOGGLE_X, SCREEN_CENTER_Y + 206F * Settings.scale);
            fpsDropdown.render(sb, LEFT_TOGGLE_X, SCREEN_CENTER_Y + 160F * Settings.scale);
        }
        AbstractGameEffect e;
        for(Iterator iterator = effects.iterator(); iterator.hasNext(); e.render(sb))
            e = (AbstractGameEffect)iterator.next();

        renderControllerUi(sb, currentHb);
    }

    private void renderControllerUi(SpriteBatch sb, Hitbox hb)
    {
        if(!Settings.isControllerMode)
            return;
        if(hb != null)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(0.7F, 0.9F, 1.0F, 0.25F));
            sb.draw(ImageMaster.CONTROLLER_HB_HIGHLIGHT, hb.cX - hb.width / 2.0F, hb.cY - hb.height / 2.0F, hb.width, hb.height);
            sb.setBlendFunction(770, 771);
        }
    }

    private void renderBg(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.INPUT_SETTINGS_EDGES, (float)Settings.WIDTH / 2.0F - 960F, Settings.OPTION_Y - 540F, 960F, 540F, 1920F, 1080F, Settings.xScale, Settings.scale, 0.0F, 0, 0, 1920, 1080, false, false);
        sb.draw(ImageMaster.SETTINGS_BACKGROUND, (float)Settings.WIDTH / 2.0F - 960F, Settings.OPTION_Y - 540F, 960F, 540F, 1920F, 1080F, Settings.xScale, Settings.scale, 0.0F, 0, 0, 1920, 1080, false, false);
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), (inputSettingsHb.cX - 32F) + FontHelper.getSmartWidth(FontHelper.panelNameFont, TEXT[20], 99999F, 0.0F) / 2.0F + 42F * Settings.scale, (Settings.OPTION_Y - 32F) + 379F * Settings.scale, 32F, 32F, 64F, 64F, Settings.xScale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
    }

    private void renderBanner(SpriteBatch sb)
    {
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, HEADER_TEXT, inputSettingsHb.cX - 396F * Settings.xScale, inputSettingsHb.cY, Settings.GOLD_COLOR);
        Color textColor = inputSettingsHb.hovered ? Settings.GOLD_COLOR : Color.LIGHT_GRAY;
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[20], inputSettingsHb.cX, inputSettingsHb.cY, textColor);
        inputSettingsHb.render(sb);
    }

    private void renderGraphics(SpriteBatch sb)
    {
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, GRAPHICS_PANEL_HEADER_TEXT, 636F * Settings.xScale, SCREEN_CENTER_Y + 256F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, RESOLUTION_TEXTS, LEFT_TEXT_X, SCREEN_CENTER_Y + 196F * Settings.scale, 10000F, 40F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, FULLSCREEN_TEXTS, LEFT_TOGGLE_TEXT_X, SCREEN_CENTER_Y + 106F * Settings.scale, 10000F, 34F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, VSYNC_TEXT, 686F * Settings.xScale, SCREEN_CENTER_Y + 106F * Settings.scale, 10000F, 34F * Settings.scale, Settings.CREAM_COLOR);
        fsToggle.render(sb);
        wfsToggle.render(sb);
        ssToggle.render(sb);
        vSyncToggle.render(sb);
    }

    private void renderSound(SpriteBatch sb)
    {
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, SOUND_PANEL_HEADER_TEXT, 1264F * Settings.xScale, SCREEN_CENTER_Y + 256F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, VOLUME_TEXTS, 1020F * Settings.xScale, SCREEN_CENTER_Y + 196F * Settings.scale, 10000F, 44F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, OTHER_SOUND_TEXTS, 1056F * Settings.xScale, SCREEN_CENTER_Y + 66F * Settings.scale, 10000F, 34.8F * Settings.scale, Settings.CREAM_COLOR);
        masterSlider.render(sb);
        bgmSlider.render(sb);
        sfxSlider.render(sb);
        ambienceToggle.render(sb);
        muteBgToggle.render(sb);
    }

    private void renderPreferences(SpriteBatch sb)
    {
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, PREF_PANEL_HEADER_TEXT, 636F * Settings.xScale, SCREEN_CENTER_Y - 60F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, (new StringBuilder()).append(PREF_TEXTS).append(" NL ").append(DISABLE_EFFECTS_TEXT).append(" NL ").append(FAST_MODE_TEXT).append(" NL ").append(SHOW_CARD_QUICK_SELECT_TEXT).toString(), 456F * Settings.xScale, SCREEN_CENTER_Y - 112F * Settings.scale, 10000F, 34.8F * Settings.scale, Settings.CREAM_COLOR);
        sumToggle.render(sb);
        blockToggle.render(sb);
        confirmToggle.render(sb);
        effectsToggle.render(sb);
        fastToggle.render(sb);
        cardKeyOverlayToggle.render(sb);
    }

    private void renderMiscellaneous(SpriteBatch sb)
    {
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, MISC_PANEL_HEADER_TEXT, 1264F * Settings.xScale, SCREEN_CENTER_Y - 60F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, LANGUAGE_TEXT, 1020F * Settings.xScale, SCREEN_CENTER_Y - 114F * Settings.scale, 10000F, 44F * Settings.scale, Settings.CREAM_COLOR);
        if(playtesterToggle != null)
            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, PLAYTESTER_ART_TEXT, 1056F * Settings.xScale, SCREEN_CENTER_Y - 182F * Settings.scale, 10000F, 34F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, UPLOAD_TEXT, 1056F * Settings.xScale, SCREEN_CENTER_Y - 216F * Settings.scale, 10000F, 34F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, LONGPRESS_TEXT, 1056F * Settings.xScale, SCREEN_CENTER_Y - 250F * Settings.scale, 10000F, 34F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, TEXT[26], 1056F * Settings.xScale, SCREEN_CENTER_Y - 284F * Settings.scale, 10000F, 34F * Settings.scale, Settings.CREAM_COLOR);
        uploadToggle.render(sb);
        if(playtesterToggle != null)
            playtesterToggle.render(sb);
        longPressToggle.render(sb);
        bigTextToggle.render(sb);
    }

    private boolean canTogglePlaytesterArt()
    {
        return UnlockTracker.isAchievementUnlocked("THE_ENDING") || Settings.isBeta;
    }

    public void refresh()
    {
        currentHb = null;
        fsToggle = new ToggleButton(TOGGLE_X_LEFT, 98F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.FULL_SCREEN);
        wfsToggle = new ToggleButton(TOGGLE_X_LEFT, 64F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.W_FULL_SCREEN);
        ssToggle = new ToggleButton(TOGGLE_X_LEFT, 30F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.SCREEN_SHAKE);
        vSyncToggle = new ToggleButton(TOGGLE_X_LEFT_2, 30F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.V_SYNC);
        sumToggle = new ToggleButton(TOGGLE_X_LEFT, -122F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.SUM_DMG);
        blockToggle = new ToggleButton(TOGGLE_X_LEFT, -156F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.BLOCK_DMG);
        confirmToggle = new ToggleButton(TOGGLE_X_LEFT, -190F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.HAND_CONF);
        effectsToggle = new ToggleButton(TOGGLE_X_LEFT, -224F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.EFFECTS);
        fastToggle = new ToggleButton(TOGGLE_X_LEFT, -258F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.FAST_MODE);
        cardKeyOverlayToggle = new ToggleButton(TOGGLE_X_LEFT, -292F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.SHOW_CARD_HOTKEYS);
        ambienceToggle = new ToggleButton(TOGGLE_X_RIGHT, 58F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.AMBIENCE_ON);
        muteBgToggle = new ToggleButton(TOGGLE_X_RIGHT, 24F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.MUTE_IF_BG);
        if(canTogglePlaytesterArt())
            playtesterToggle = new ToggleButton(TOGGLE_X_RIGHT, -190F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.PLAYTESTER_ART);
        uploadToggle = new ToggleButton(TOGGLE_X_RIGHT, -224F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.UPLOAD_DATA);
        longPressToggle = new ToggleButton(TOGGLE_X_RIGHT, -258F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.LONG_PRESS);
        bigTextToggle = new ToggleButton(TOGGLE_X_RIGHT, -292F, SCREEN_CENTER_Y, ToggleButton.ToggleBtnType.BIG_TEXT);
        masterSlider = new Slider(SCREEN_CENTER_Y + 186F * Settings.scale, Settings.MASTER_VOLUME, Slider.SliderType.MASTER);
        bgmSlider = new Slider(SCREEN_CENTER_Y + 142F * Settings.scale, Settings.MUSIC_VOLUME, Slider.SliderType.BGM);
        sfxSlider = new Slider(SCREEN_CENTER_Y + 98F * Settings.scale, Settings.SOUND_VOLUME, Slider.SliderType.SFX);
        resoDropdown = new DropdownMenu(this, getResolutionLabels(), FontHelper.tipBodyFont, Settings.CREAM_COLOR);
        resetResolutionDropdownSelection();
        fpsDropdown = new DropdownMenu(this, FRAMERATE_LABELS, FontHelper.tipBodyFont, Settings.CREAM_COLOR);
        resetFpsDropdownSelection();
        languageDropdown = new DropdownMenu(this, languageLabels, FontHelper.tipBodyFont, Settings.CREAM_COLOR);
        resetLanguageDropdownSelection();
        exitBtn.updateLabel(SAVE_TEXT);
        if(!Gdx.files.local(AbstractDungeon.player.getSaveFilePath()).exists())
            exitBtn.updateLabel(EXIT_TEXT);
    }

    public void displayRestartRequiredText()
    {
        if(CardCrawlGame.mode == com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.CHAR_SELECT)
        {
            if(CardCrawlGame.mainMenuScreen != null)
            {
                CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
                CardCrawlGame.mainMenuScreen.optionPanel.effects.add(new RestartForChangesEffect());
            }
        } else
        {
            AbstractDungeon.topLevelEffects.add(new RestartForChangesEffect());
        }
    }

    public void changedSelectionTo(DropdownMenu dropdownMenu, int index, String optionText)
    {
        if(dropdownMenu == languageDropdown)
        {
            changeLanguageToIndex(index);
            if(Settings.isControllerMode)
            {
                Gdx.input.setCursorPosition((int)languageDropdown.getHitbox().cX, Settings.HEIGHT - (int)languageDropdown.getHitbox().cY);
                currentHb = languageDropdown.getHitbox();
            }
        } else
        if(dropdownMenu == resoDropdown)
        {
            changeResolutionToIndex(index);
            if(Settings.isControllerMode)
            {
                Gdx.input.setCursorPosition((int)resoDropdown.getHitbox().cX, Settings.HEIGHT - (int)resoDropdown.getHitbox().cY);
                currentHb = resoDropdown.getHitbox();
            }
        } else
        if(dropdownMenu == fpsDropdown)
        {
            changeFrameRateToIndex(index);
            if(Settings.isControllerMode)
            {
                Gdx.input.setCursorPosition((int)fpsDropdown.getHitbox().cX, Settings.HEIGHT - (int)fpsDropdown.getHitbox().cY);
                currentHb = fpsDropdown.getHitbox();
            }
        }
    }

    private void resetLanguageDropdownSelection()
    {
        com.megacrit.cardcrawl.core.Settings.GameLanguage languageOptions[] = LanguageOptions();
        for(int i = 0; i < languageOptions.length; i++)
            if(Settings.language == languageOptions[i])
            {
                languageDropdown.setSelectedIndex(i);
                return;
            }

    }

    private void changeLanguageToIndex(int index)
    {
        if(index >= LOCALIZED_LANGUAGE_LABELS.length)
            return;
        com.megacrit.cardcrawl.core.Settings.GameLanguage languageOptions[] = LanguageOptions();
        for(int i = 0; i < languageOptions.length; i++)
            if(Settings.language == languageOptions[i] && i == index)
                return;

        Settings.setLanguage(LanguageOptions()[index], false);
        Settings.gamePref.flush();
        displayRestartRequiredText();
    }

    public String[] languageLabels()
    {
        if(Settings.isConsoleBuild)
            return (new String[] {
                LOCALIZED_LANGUAGE_LABELS[3], LOCALIZED_LANGUAGE_LABELS[0], LOCALIZED_LANGUAGE_LABELS[1], LOCALIZED_LANGUAGE_LABELS[2], LOCALIZED_LANGUAGE_LABELS[26], LOCALIZED_LANGUAGE_LABELS[4], LOCALIZED_LANGUAGE_LABELS[5], LOCALIZED_LANGUAGE_LABELS[24], LOCALIZED_LANGUAGE_LABELS[6], LOCALIZED_LANGUAGE_LABELS[7], 
                LOCALIZED_LANGUAGE_LABELS[8], LOCALIZED_LANGUAGE_LABELS[9], LOCALIZED_LANGUAGE_LABELS[10], LOCALIZED_LANGUAGE_LABELS[11], LOCALIZED_LANGUAGE_LABELS[16], LOCALIZED_LANGUAGE_LABELS[13], LOCALIZED_LANGUAGE_LABELS[18], LOCALIZED_LANGUAGE_LABELS[19]
            });
        if(!Settings.isBeta)
            return (new String[] {
                LOCALIZED_LANGUAGE_LABELS[3], LOCALIZED_LANGUAGE_LABELS[0], LOCALIZED_LANGUAGE_LABELS[1], LOCALIZED_LANGUAGE_LABELS[2], LOCALIZED_LANGUAGE_LABELS[26], LOCALIZED_LANGUAGE_LABELS[25], LOCALIZED_LANGUAGE_LABELS[27], LOCALIZED_LANGUAGE_LABELS[4], LOCALIZED_LANGUAGE_LABELS[5], LOCALIZED_LANGUAGE_LABELS[24], 
                LOCALIZED_LANGUAGE_LABELS[6], LOCALIZED_LANGUAGE_LABELS[7], LOCALIZED_LANGUAGE_LABELS[8], LOCALIZED_LANGUAGE_LABELS[9], LOCALIZED_LANGUAGE_LABELS[10], LOCALIZED_LANGUAGE_LABELS[17], LOCALIZED_LANGUAGE_LABELS[21], LOCALIZED_LANGUAGE_LABELS[11], LOCALIZED_LANGUAGE_LABELS[16], LOCALIZED_LANGUAGE_LABELS[13], 
                LOCALIZED_LANGUAGE_LABELS[18], LOCALIZED_LANGUAGE_LABELS[19]
            });
        else
            return (new String[] {
                LOCALIZED_LANGUAGE_LABELS[3], LOCALIZED_LANGUAGE_LABELS[0], LOCALIZED_LANGUAGE_LABELS[1], LOCALIZED_LANGUAGE_LABELS[2], LOCALIZED_LANGUAGE_LABELS[26], LOCALIZED_LANGUAGE_LABELS[25], LOCALIZED_LANGUAGE_LABELS[27], LOCALIZED_LANGUAGE_LABELS[4], LOCALIZED_LANGUAGE_LABELS[5], LOCALIZED_LANGUAGE_LABELS[23], 
                LOCALIZED_LANGUAGE_LABELS[24], LOCALIZED_LANGUAGE_LABELS[6], LOCALIZED_LANGUAGE_LABELS[7], LOCALIZED_LANGUAGE_LABELS[8], LOCALIZED_LANGUAGE_LABELS[15], LOCALIZED_LANGUAGE_LABELS[9], LOCALIZED_LANGUAGE_LABELS[10], LOCALIZED_LANGUAGE_LABELS[17], LOCALIZED_LANGUAGE_LABELS[21], LOCALIZED_LANGUAGE_LABELS[11], 
                LOCALIZED_LANGUAGE_LABELS[16], LOCALIZED_LANGUAGE_LABELS[13], LOCALIZED_LANGUAGE_LABELS[18], LOCALIZED_LANGUAGE_LABELS[19]
            });
    }

    public com.megacrit.cardcrawl.core.Settings.GameLanguage[] LanguageOptions()
    {
        if(Settings.isConsoleBuild)
            return (new com.megacrit.cardcrawl.core.Settings.GameLanguage[] {
                com.megacrit.cardcrawl.core.Settings.GameLanguage.ENG, com.megacrit.cardcrawl.core.Settings.GameLanguage.PTB, com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS, com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHT, com.megacrit.cardcrawl.core.Settings.GameLanguage.DUT, com.megacrit.cardcrawl.core.Settings.GameLanguage.FRA, com.megacrit.cardcrawl.core.Settings.GameLanguage.DEU, com.megacrit.cardcrawl.core.Settings.GameLanguage.ITA, com.megacrit.cardcrawl.core.Settings.GameLanguage.ITA, com.megacrit.cardcrawl.core.Settings.GameLanguage.JPN, 
                com.megacrit.cardcrawl.core.Settings.GameLanguage.KOR, com.megacrit.cardcrawl.core.Settings.GameLanguage.POL, com.megacrit.cardcrawl.core.Settings.GameLanguage.RUS, com.megacrit.cardcrawl.core.Settings.GameLanguage.SPA, com.megacrit.cardcrawl.core.Settings.GameLanguage.THA, com.megacrit.cardcrawl.core.Settings.GameLanguage.TUR, com.megacrit.cardcrawl.core.Settings.GameLanguage.UKR, com.megacrit.cardcrawl.core.Settings.GameLanguage.VIE
            });
        if(!Settings.isBeta)
            return (new com.megacrit.cardcrawl.core.Settings.GameLanguage[] {
                com.megacrit.cardcrawl.core.Settings.GameLanguage.ENG, com.megacrit.cardcrawl.core.Settings.GameLanguage.PTB, com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS, com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHT, com.megacrit.cardcrawl.core.Settings.GameLanguage.DUT, com.megacrit.cardcrawl.core.Settings.GameLanguage.EPO, com.megacrit.cardcrawl.core.Settings.GameLanguage.FIN, com.megacrit.cardcrawl.core.Settings.GameLanguage.FRA, com.megacrit.cardcrawl.core.Settings.GameLanguage.DEU, com.megacrit.cardcrawl.core.Settings.GameLanguage.IND, 
                com.megacrit.cardcrawl.core.Settings.GameLanguage.ITA, com.megacrit.cardcrawl.core.Settings.GameLanguage.JPN, com.megacrit.cardcrawl.core.Settings.GameLanguage.KOR, com.megacrit.cardcrawl.core.Settings.GameLanguage.POL, com.megacrit.cardcrawl.core.Settings.GameLanguage.RUS, com.megacrit.cardcrawl.core.Settings.GameLanguage.SRP, com.megacrit.cardcrawl.core.Settings.GameLanguage.SRB, com.megacrit.cardcrawl.core.Settings.GameLanguage.SPA, com.megacrit.cardcrawl.core.Settings.GameLanguage.THA, com.megacrit.cardcrawl.core.Settings.GameLanguage.TUR, 
                com.megacrit.cardcrawl.core.Settings.GameLanguage.UKR, com.megacrit.cardcrawl.core.Settings.GameLanguage.VIE
            });
        else
            return (new com.megacrit.cardcrawl.core.Settings.GameLanguage[] {
                com.megacrit.cardcrawl.core.Settings.GameLanguage.ENG, com.megacrit.cardcrawl.core.Settings.GameLanguage.PTB, com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS, com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHT, com.megacrit.cardcrawl.core.Settings.GameLanguage.DUT, com.megacrit.cardcrawl.core.Settings.GameLanguage.EPO, com.megacrit.cardcrawl.core.Settings.GameLanguage.FIN, com.megacrit.cardcrawl.core.Settings.GameLanguage.FRA, com.megacrit.cardcrawl.core.Settings.GameLanguage.DEU, com.megacrit.cardcrawl.core.Settings.GameLanguage.GRE, 
                com.megacrit.cardcrawl.core.Settings.GameLanguage.IND, com.megacrit.cardcrawl.core.Settings.GameLanguage.ITA, com.megacrit.cardcrawl.core.Settings.GameLanguage.JPN, com.megacrit.cardcrawl.core.Settings.GameLanguage.KOR, com.megacrit.cardcrawl.core.Settings.GameLanguage.NOR, com.megacrit.cardcrawl.core.Settings.GameLanguage.POL, com.megacrit.cardcrawl.core.Settings.GameLanguage.RUS, com.megacrit.cardcrawl.core.Settings.GameLanguage.SRP, com.megacrit.cardcrawl.core.Settings.GameLanguage.SRB, com.megacrit.cardcrawl.core.Settings.GameLanguage.SPA, 
                com.megacrit.cardcrawl.core.Settings.GameLanguage.THA, com.megacrit.cardcrawl.core.Settings.GameLanguage.TUR, com.megacrit.cardcrawl.core.Settings.GameLanguage.UKR, com.megacrit.cardcrawl.core.Settings.GameLanguage.VIE
            });
    }

    private void changeFrameRateToIndex(int index)
    {
        if(Settings.MAX_FPS != FRAMERATE_OPTIONS[index])
        {
            fpsDropdown.setSelectedIndex(index);
            Settings.MAX_FPS = FRAMERATE_OPTIONS[index];
            DisplayConfig.writeDisplayConfigFile(Settings.SAVED_WIDTH, Settings.SAVED_HEIGHT, Settings.MAX_FPS, Settings.IS_FULLSCREEN, Settings.IS_W_FULLSCREEN, Settings.IS_V_SYNC);
            displayRestartRequiredText();
        }
    }

    private void resetFpsDropdownSelection()
    {
        boolean found = false;
        for(int i = 0; i < FRAMERATE_OPTIONS.length; i++)
            if(Settings.MAX_FPS == FRAMERATE_OPTIONS[i])
            {
                found = true;
                changeFrameRateToIndex(i);
                fpsDropdown.setSelectedIndex(i);
            }

        if(!found)
        {
            Settings.MAX_FPS = 60;
            changeFrameRateToIndex(2);
            fpsDropdown.setSelectedIndex(2);
        }
    }

    private void changeResolutionToIndex(int index)
    {
        if(Settings.displayIndex == index)
            return;
        resoDropdown.setSelectedIndex(index);
        Settings.displayIndex = index;
        displayRestartRequiredText();
        if(index > Settings.displayOptions.size() - 1)
            index = 0;
        int TMP_WIDTH = ((DisplayOption)Settings.displayOptions.get(index)).width;
        int TMP_HEIGHT = ((DisplayOption)Settings.displayOptions.get(index)).height;
        DisplayConfig.writeDisplayConfigFile(TMP_WIDTH, TMP_HEIGHT, Settings.MAX_FPS, Settings.IS_FULLSCREEN, Settings.IS_W_FULLSCREEN, Settings.IS_V_SYNC);
        Settings.SAVED_WIDTH = TMP_WIDTH;
        Settings.SAVED_HEIGHT = TMP_HEIGHT;
    }

    public void resetResolutionDropdownSelection()
    {
        DisplayConfig dConfig = DisplayConfig.readConfig();
        for(int i = 0; i < Settings.displayOptions.size(); i++)
            if(dConfig.getWidth() == ((DisplayOption)Settings.displayOptions.get(i)).width && dConfig.getHeight() == ((DisplayOption)Settings.displayOptions.get(i)).height)
            {
                Settings.displayIndex = i;
                resoDropdown.setSelectedIndex(i);
                resoDropdown.topVisibleRowIndex = 0;
                return;
            }

        resoDropdown.setSelectedIndex(Settings.displayIndex);
    }

    private ArrayList getResolutionLabels()
    {
        initalizeDisplayOptionsIfNull();
        ArrayList labels = new ArrayList();
        DisplayOption option;
        for(Iterator iterator = Settings.displayOptions.iterator(); iterator.hasNext(); labels.add(option.uiString()))
            option = (DisplayOption)iterator.next();

        return labels;
    }

    public ArrayList getResolutionLabels(int mode)
    {
        switch(mode)
        {
        case 0: // '\0'
            setDisplayOptionsToFullscreen();
            break;

        case 1: // '\001'
            setDisplayOptionsToBfs();
            break;

        default:
            setDisplayOptionsToAllResolutions();
            break;
        }
        ArrayList labels = new ArrayList();
        DisplayOption option;
        for(Iterator iterator = Settings.displayOptions.iterator(); iterator.hasNext(); labels.add(option.uiString()))
            option = (DisplayOption)iterator.next();

        return labels;
    }

    private void initalizeDisplayOptionsIfNull()
    {
        if(Settings.displayOptions == null)
            if(Settings.IS_FULLSCREEN)
                Settings.displayOptions = getFullScreenOnlyResolutions();
            else
            if(Settings.IS_W_FULLSCREEN)
                Settings.displayOptions = getBfsOnlyResolutions();
            else
                Settings.displayOptions = getWindowedAndFullscreenResolutions();
    }

    public void setDisplayOptionsToBfs()
    {
        Settings.displayOptions.clear();
        Settings.displayOptions = null;
        Settings.displayOptions = getBfsOnlyResolutions();
    }

    public void setDisplayOptionsToFullscreen()
    {
        Settings.displayOptions.clear();
        Settings.displayOptions = null;
        Settings.displayOptions = getFullScreenOnlyResolutions();
    }

    public void setDisplayOptionsToAllResolutions()
    {
        Settings.displayOptions.clear();
        Settings.displayOptions = null;
        Settings.displayOptions = getWindowedAndFullscreenResolutions();
    }

    private ArrayList getBfsOnlyResolutions()
    {
        ArrayList retVal = new ArrayList();
        com.badlogic.gdx.Graphics.DisplayMode modes[] = Gdx.graphics.getDisplayModes(Gdx.graphics.getPrimaryMonitor());
        List modesList = new ArrayList();
        for(int i = 0; i < modes.length; i++)
            modesList.add(new DisplayOption(modes[i].width, modes[i].height));

        Collections.sort(modesList);
        retVal.add(modesList.get(modesList.size() - 1));
        return retVal;
    }

    private ArrayList getFullScreenOnlyResolutions()
    {
        ArrayList retVal = new ArrayList();
        ArrayList allowedResolutions = getAllowedResolutions();
        com.badlogic.gdx.Graphics.DisplayMode modes[] = Gdx.graphics.getDisplayModes(Gdx.graphics.getPrimaryMonitor());
        com.badlogic.gdx.Graphics.DisplayMode adisplaymode[] = modes;
        int i = adisplaymode.length;
        for(int j = 0; j < i; j++)
        {
            com.badlogic.gdx.Graphics.DisplayMode m = adisplaymode[j];
            DisplayOption o = new DisplayOption(m.width, m.height, true);
            if(!retVal.contains(o) && allowedResolutions.contains(o))
                retVal.add(o);
        }

        Collections.sort(retVal);
        return retVal;
    }

    private ArrayList getAllowedResolutions()
    {
        ArrayList retVal = new ArrayList();
        retVal.add(new DisplayOption(1680, 720));
        retVal.add(new DisplayOption(2560, 1080));
        retVal.add(new DisplayOption(3440, 1440));
        retVal.add(new DisplayOption(1024, 576));
        retVal.add(new DisplayOption(1280, 720));
        retVal.add(new DisplayOption(1366, 768));
        retVal.add(new DisplayOption(1536, 864));
        retVal.add(new DisplayOption(1600, 900));
        retVal.add(new DisplayOption(1920, 1080));
        retVal.add(new DisplayOption(2560, 1440));
        retVal.add(new DisplayOption(3840, 2160));
        retVal.add(new DisplayOption(1024, 640));
        retVal.add(new DisplayOption(1280, 800));
        retVal.add(new DisplayOption(1680, 1050));
        retVal.add(new DisplayOption(1920, 1200));
        retVal.add(new DisplayOption(2560, 1600));
        retVal.add(new DisplayOption(1024, 768));
        retVal.add(new DisplayOption(1280, 960));
        retVal.add(new DisplayOption(1400, 1050));
        retVal.add(new DisplayOption(1440, 1080));
        retVal.add(new DisplayOption(1600, 1200));
        retVal.add(new DisplayOption(2048, 1536));
        retVal.add(new DisplayOption(2224, 1668));
        retVal.add(new DisplayOption(2732, 2048));
        return retVal;
    }

    private ArrayList getWindowedAndFullscreenResolutions()
    {
        ArrayList retVal = new ArrayList();
        ArrayList availableResos = getAllowedResolutions();
        DisplayOption screenRes = new DisplayOption(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
        if(!retVal.contains(screenRes))
            availableResos.add(screenRes);
        Iterator iterator = availableResos.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            DisplayOption o = (DisplayOption)iterator.next();
            if(o.width <= Gdx.graphics.getDisplayMode().width && o.height <= Gdx.graphics.getDisplayMode().height && !retVal.contains(o))
                retVal.add(o);
        } while(true);
        com.badlogic.gdx.Graphics.DisplayMode modes[] = Gdx.graphics.getDisplayModes(Gdx.graphics.getPrimaryMonitor());
        com.badlogic.gdx.Graphics.DisplayMode adisplaymode[] = modes;
        int i = adisplaymode.length;
        for(int j = 0; j < i; j++)
        {
            com.badlogic.gdx.Graphics.DisplayMode m = adisplaymode[j];
            DisplayOption o = new DisplayOption(m.width, m.height);
            if(!retVal.contains(o) && o.width >= 1024 && o.height >= 576)
                retVal.add(o);
        }

        Collections.sort(retVal);
        return retVal;
    }

    public void setFullscreen(boolean borderless)
    {
        int TMP_WIDTH = Gdx.graphics.getDisplayMode().width;
        int TMP_HEIGHT = Gdx.graphics.getDisplayMode().height;
        DisplayConfig.writeDisplayConfigFile(TMP_WIDTH, TMP_HEIGHT, Settings.MAX_FPS, !borderless, borderless, Settings.IS_V_SYNC);
        Settings.SAVED_WIDTH = TMP_WIDTH;
        Settings.SAVED_HEIGHT = TMP_HEIGHT;
        for(int i = 0; i < Settings.displayOptions.size(); i++)
            if(((DisplayOption)Settings.displayOptions.get(i)).equals(new DisplayOption(TMP_WIDTH, TMP_HEIGHT)))
                changeResolutionToIndex(i);

    }

    public void hoverStarted(Hitbox hitbox)
    {
        CardCrawlGame.sound.play("UI_HOVER");
    }

    public void startClicking(Hitbox hitbox)
    {
        CardCrawlGame.sound.play("UI_CLICK_1");
    }

    public void clicked(Hitbox hitbox)
    {
        if(hitbox == inputSettingsHb)
            if(CardCrawlGame.isInARun())
            {
                AbstractDungeon.inputSettingsScreen.open(false);
            } else
            {
                CardCrawlGame.cancelButton.hideInstantly();
                CardCrawlGame.mainMenuScreen.inputSettingsScreen.open(false);
                CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.INPUT_SETTINGS;
                CardCrawlGame.mainMenuScreen.isSettingsUp = false;
            }
    }

    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final int RAW_W = 1920;
    private static final int RAW_H = 1080;
    private static final float SCREEN_CENTER_Y;
    private AbandonRunButton abandonBtn;
    private ExitGameButton exitBtn;
    private Hitbox inputSettingsHb;
    public DropdownMenu fpsDropdown;
    public DropdownMenu resoDropdown;
    private Slider masterSlider;
    private Slider bgmSlider;
    private Slider sfxSlider;
    private static final float TOGGLE_X_LEFT;
    private static final float TOGGLE_X_LEFT_2;
    private static final float TOGGLE_X_RIGHT;
    public ToggleButton fsToggle;
    public ToggleButton wfsToggle;
    public ToggleButton vSyncToggle;
    private ToggleButton ssToggle;
    private ToggleButton ambienceToggle;
    private ToggleButton muteBgToggle;
    private ToggleButton sumToggle;
    private ToggleButton blockToggle;
    private ToggleButton confirmToggle;
    private ToggleButton effectsToggle;
    private ToggleButton fastToggle;
    private ToggleButton cardKeyOverlayToggle;
    private ToggleButton uploadToggle;
    private ToggleButton longPressToggle;
    private ToggleButton bigTextToggle;
    private ToggleButton playtesterToggle;
    private DropdownMenu languageDropdown;
    private String languageLabels[];
    public ArrayList effects;
    private Hitbox currentHb;
    public static final String LOCALIZED_LANGUAGE_LABELS[];
    private String FRAMERATE_LABELS[] = {
        "24", "30", "60", "120", "240"
    };
    private int FRAMERATE_OPTIONS[] = {
        24, 30, 60, 120, 240
    };
    private static final float LEFT_TOGGLE_X;
    private static final float LEFT_TEXT_X;
    private static final float LEFT_TOGGLE_TEXT_X;
    private static final String HEADER_TEXT;
    private static final String GRAPHICS_PANEL_HEADER_TEXT;
    private static final String RESOLUTION_TEXTS;
    private static final String FULLSCREEN_TEXTS;
    private static final String SOUND_PANEL_HEADER_TEXT;
    private static final String VOLUME_TEXTS;
    private static final String OTHER_SOUND_TEXTS;
    private static final String PREF_PANEL_HEADER_TEXT;
    private static final String PREF_TEXTS;
    private static final String FAST_MODE_TEXT;
    private static final String MISC_PANEL_HEADER_TEXT;
    private static final String LANGUAGE_TEXT;
    private static final String UPLOAD_TEXT;
    private static final String EXIT_TEXT;
    private static final String SAVE_TEXT;
    private static final String VSYNC_TEXT;
    private static final String PLAYTESTER_ART_TEXT;
    private static final String SHOW_CARD_QUICK_SELECT_TEXT;
    private static final String DISABLE_EFFECTS_TEXT;
    private static final String LONGPRESS_TEXT;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Options Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        uiStrings = CardCrawlGame.languagePack.getUIString("OptionsPanel");
        TEXT = uiStrings.TEXT;
        SCREEN_CENTER_Y = (float)Settings.HEIGHT / 2.0F - 64F * Settings.scale;
        TOGGLE_X_LEFT = 430F * Settings.xScale;
        TOGGLE_X_LEFT_2 = 660F * Settings.xScale;
        TOGGLE_X_RIGHT = 1030F * Settings.xScale;
        LOCALIZED_LANGUAGE_LABELS = CardCrawlGame.languagePack.getUIString("LanguageDropdown").TEXT;
        LEFT_TOGGLE_X = 670F * Settings.xScale;
        LEFT_TEXT_X = 410F * Settings.xScale;
        LEFT_TOGGLE_TEXT_X = 456F * Settings.xScale;
        HEADER_TEXT = TEXT[1];
        GRAPHICS_PANEL_HEADER_TEXT = TEXT[2];
        RESOLUTION_TEXTS = TEXT[3];
        FULLSCREEN_TEXTS = TEXT[4];
        SOUND_PANEL_HEADER_TEXT = TEXT[5];
        VOLUME_TEXTS = TEXT[6];
        OTHER_SOUND_TEXTS = TEXT[7];
        PREF_PANEL_HEADER_TEXT = TEXT[8];
        PREF_TEXTS = TEXT[9];
        FAST_MODE_TEXT = TEXT[10];
        MISC_PANEL_HEADER_TEXT = TEXT[12];
        LANGUAGE_TEXT = TEXT[13];
        UPLOAD_TEXT = TEXT[14];
        EXIT_TEXT = TEXT[15];
        SAVE_TEXT = TEXT[16];
        VSYNC_TEXT = TEXT[17];
        PLAYTESTER_ART_TEXT = TEXT[18];
        SHOW_CARD_QUICK_SELECT_TEXT = TEXT[19];
        DISABLE_EFFECTS_TEXT = TEXT[21];
        LONGPRESS_TEXT = TEXT[25];
    }
}
