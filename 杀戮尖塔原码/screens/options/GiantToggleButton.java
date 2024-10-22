// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GiantToggleButton.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GiantToggleButton
{
    public static final class ToggleType extends Enum
    {

        public static ToggleType[] values()
        {
            return (ToggleType[])$VALUES.clone();
        }

        public static ToggleType valueOf(String name)
        {
            return (ToggleType)Enum.valueOf(com/megacrit/cardcrawl/screens/options/GiantToggleButton$ToggleType, name);
        }

        public static final ToggleType CONTROLLER_ENABLED;
        public static final ToggleType TOUCHSCREEN_ENABLED;
        private static final ToggleType $VALUES[];

        static 
        {
            CONTROLLER_ENABLED = new ToggleType("CONTROLLER_ENABLED", 0);
            TOUCHSCREEN_ENABLED = new ToggleType("TOUCHSCREEN_ENABLED", 1);
            $VALUES = (new ToggleType[] {
                CONTROLLER_ENABLED, TOUCHSCREEN_ENABLED
            });
        }

        private ToggleType(String s, int i)
        {
            super(s, i);
        }
    }


    public GiantToggleButton(ToggleType type, float x, float y, String label)
    {
        ticked = false;
        hb = new Hitbox(320F * Settings.scale, 72F * Settings.scale);
        scale = Settings.scale;
        this.type = type;
        this.x = x;
        this.y = y;
        this.label = label;
        hb.move(x + 110F * Settings.scale, y);
        initialize();
    }

    private void initialize()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$options$GiantToggleButton$ToggleType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$options$GiantToggleButton$ToggleType = new int[ToggleType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$GiantToggleButton$ToggleType[ToggleType.CONTROLLER_ENABLED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$GiantToggleButton$ToggleType[ToggleType.TOUCHSCREEN_ENABLED.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.options.GiantToggleButton.ToggleType[type.ordinal()])
        {
        case 1: // '\001'
            ticked = Settings.gamePref.getBoolean("Controller Enabled", true);
            break;

        case 2: // '\002'
            ticked = Settings.gamePref.getBoolean("Touchscreen Enabled", false);
            break;

        default:
            logger.info((new StringBuilder()).append(type.name()).append(" not found (initialize())").toString());
            break;
        }
    }

    public void update()
    {
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            hb.clickStarted = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        } else
        if(hb.clicked || hb.hovered && CInputActionSet.select.isJustPressed())
        {
            CInputActionSet.select.unpress();
            hb.clicked = false;
            ticked = !ticked;
            useEffect();
        }
        if(hb.hovered)
            scale = Settings.scale * 1.125F;
        else
            scale = Settings.scale;
    }

    private void useEffect()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.options.GiantToggleButton.ToggleType[type.ordinal()])
        {
        case 1: // '\001'
            Settings.gamePref.putBoolean("Controller Enabled", ticked);
            Settings.gamePref.flush();
            Settings.CONTROLLER_ENABLED = ticked;
            break;

        case 2: // '\002'
            Settings.gamePref.putBoolean("Touchscreen Enabled", ticked);
            Settings.gamePref.flush();
            Settings.TOUCHSCREEN_ENABLED = ticked;
            Settings.isTouchScreen = ticked;
            break;

        default:
            logger.info((new StringBuilder()).append(type.name()).append(" not found (useEffect())").toString());
            break;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.CHECKBOX, x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        if(ticked)
            sb.draw(ImageMaster.TICK, x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        if(hb.hovered)
            FontHelper.renderFontLeft(sb, FontHelper.panelEndTurnFont, label, x + 40F * Settings.scale, y, Settings.GREEN_TEXT_COLOR);
        else
            FontHelper.renderFontLeft(sb, FontHelper.panelEndTurnFont, label, x + 40F * Settings.scale, y, Settings.CREAM_COLOR);
        hb.render(sb);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/options/GiantToggleButton.getName());
    public boolean ticked;
    public ToggleType type;
    private String label;
    Hitbox hb;
    private float x;
    private float y;
    private float scale;

}
