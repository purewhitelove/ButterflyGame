// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Slider.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import java.text.DecimalFormat;

public class Slider
{
    public static final class SliderType extends Enum
    {

        public static SliderType[] values()
        {
            return (SliderType[])$VALUES.clone();
        }

        public static SliderType valueOf(String name)
        {
            return (SliderType)Enum.valueOf(com/megacrit/cardcrawl/screens/options/Slider$SliderType, name);
        }

        public static final SliderType MASTER;
        public static final SliderType BGM;
        public static final SliderType SFX;
        private static final SliderType $VALUES[];

        static 
        {
            MASTER = new SliderType("MASTER", 0);
            BGM = new SliderType("BGM", 1);
            SFX = new SliderType("SFX", 2);
            $VALUES = (new SliderType[] {
                MASTER, BGM, SFX
            });
        }

        private SliderType(String s, int i)
        {
            super(s, i);
        }
    }


    public Slider(float y, float volume, SliderType type)
    {
        sliderGrabbed = false;
        this.type = type;
        this.y = y;
        this.volume = volume;
        hb = new Hitbox(42F * Settings.scale, 38F * Settings.scale);
        bgHb = new Hitbox(300F * Settings.scale, 38F * Settings.scale);
        bgHb.move(BG_X, y);
        x = L_X + SLIDE_W * volume;
    }

    public void update()
    {
        hb.update();
        bgHb.update();
        hb.move(x, y);
        if(sliderGrabbed)
        {
            if(InputHelper.isMouseDown)
            {
                x = MathHelper.fadeLerpSnap(x, InputHelper.mX);
                if(x < L_X)
                    x = L_X;
                else
                if(x > L_X + SLIDE_W)
                    x = L_X + SLIDE_W;
                volume = (x - L_X) / SLIDE_W;
                modifyVolume();
            } else
            {
                if(type == SliderType.SFX)
                {
                    int roll = MathUtils.random(2);
                    if(roll == 0)
                        CardCrawlGame.sound.play("ATTACK_DAGGER_1");
                    else
                    if(roll == 1)
                        CardCrawlGame.sound.play("ATTACK_DAGGER_2");
                    else
                    if(roll == 2)
                        CardCrawlGame.sound.play("ATTACK_DAGGER_3");
                }
                sliderGrabbed = false;
                Settings.soundPref.flush();
            }
        } else
        if(InputHelper.justClickedLeft)
            if(hb.hovered)
                sliderGrabbed = true;
            else
            if(bgHb.hovered)
                sliderGrabbed = true;
        if(Settings.isControllerMode && bgHb.hovered)
            if(CInputActionSet.inspectLeft.isJustPressed())
            {
                x -= 5F * Settings.scale;
                if(x < L_X)
                    x = L_X;
                volume = (x - L_X) / SLIDE_W;
                modifyVolume();
            } else
            if(CInputActionSet.inspectRight.isJustPressed())
            {
                x += 5F * Settings.scale;
                if(x > L_X + SLIDE_W)
                    x = L_X + SLIDE_W;
                volume = (x - L_X) / SLIDE_W;
                modifyVolume();
            }
    }

    private void modifyVolume()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$options$Slider$SliderType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$options$Slider$SliderType = new int[SliderType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$Slider$SliderType[SliderType.MASTER.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$Slider$SliderType[SliderType.BGM.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$Slider$SliderType[SliderType.SFX.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.options.Slider.SliderType[type.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            Settings.MASTER_VOLUME = volume;
            Settings.soundPref.putFloat("Master Volume", volume);
            CardCrawlGame.music.updateVolume();
            if(CardCrawlGame.mode == com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.CHAR_SELECT)
            {
                CardCrawlGame.mainMenuScreen.updateAmbienceVolume();
                break;
            }
            if(AbstractDungeon.scene != null)
                AbstractDungeon.scene.updateAmbienceVolume();
            break;

        case 2: // '\002'
            Settings.MUSIC_VOLUME = volume;
            CardCrawlGame.music.updateVolume();
            Settings.soundPref.putFloat("Music Volume", volume);
            break;

        case 3: // '\003'
            Settings.SOUND_VOLUME = volume;
            if(CardCrawlGame.mode == com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.CHAR_SELECT)
                CardCrawlGame.mainMenuScreen.updateAmbienceVolume();
            else
            if(AbstractDungeon.scene != null)
                AbstractDungeon.scene.updateAmbienceVolume();
            Settings.soundPref.putFloat("Sound Volume", volume);
            break;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        if(Settings.isControllerMode && bgHb.hovered)
            sb.draw(ImageMaster.CONTROLLER_RS, bgHb.cX + 195F * Settings.scale, bgHb.cY - 46F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        sb.draw(ImageMaster.OPTION_SLIDER_BG, BG_X - 125F, y - 12F, 125F, 12F, 250F, 24F, Settings.scale, Settings.scale, 0.0F, 0, 0, 250, 24, false, false);
        if(sliderGrabbed)
            FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, (new StringBuilder()).append(df.format(volume * 100F)).append('%').toString(), BG_X + 170F * Settings.scale, y, Settings.GREEN_TEXT_COLOR);
        else
            FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, (new StringBuilder()).append(df.format(volume * 100F)).append('%').toString(), BG_X + 170F * Settings.scale, y, Settings.BLUE_TEXT_COLOR);
        sb.draw(ImageMaster.OPTION_SLIDER, x - 22F, y - 22F, 22F, 22F, 44F, 44F, Settings.scale, Settings.scale, 0.0F, 0, 0, 44, 44, false, false);
        hb.render(sb);
        bgHb.render(sb);
    }

    private static final int BG_W = 250;
    private static final int BG_H = 24;
    private static final int S_W = 44;
    private static final float SLIDE_W;
    private static final float BG_X;
    private static final float L_X;
    private float x;
    private float y;
    private float volume;
    public Hitbox hb;
    public Hitbox bgHb;
    private boolean sliderGrabbed;
    private SliderType type;
    private static DecimalFormat df = new DecimalFormat("#");

    static 
    {
        SLIDE_W = 230F * Settings.xScale;
        BG_X = 1350F * Settings.xScale;
        L_X = 1235F * Settings.xScale;
    }
}
