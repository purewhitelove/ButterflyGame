// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BottomBgPanel.java

package com.megacrit.cardcrawl.ui.panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

public class BottomBgPanel
{
    public static final class Mode extends Enum
    {

        public static Mode[] values()
        {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String name)
        {
            return (Mode)Enum.valueOf(com/megacrit/cardcrawl/ui/panels/BottomBgPanel$Mode, name);
        }

        public static final Mode NORMAL;
        public static final Mode OVERLAY;
        public static final Mode HIDDEN;
        private static final Mode $VALUES[];

        static 
        {
            NORMAL = new Mode("NORMAL", 0);
            OVERLAY = new Mode("OVERLAY", 1);
            HIDDEN = new Mode("HIDDEN", 2);
            $VALUES = (new Mode[] {
                NORMAL, OVERLAY, HIDDEN
            });
        }

        private Mode(String s, int i)
        {
            super(s, i);
        }
    }


    public BottomBgPanel()
    {
        normal_y = 72F * Settings.scale;
        hide_y = 0.0F;
        overlay_y = (float)Settings.HEIGHT * 0.5F;
        doneAnimating = true;
        current_y = normal_y;
        target_y = current_y;
    }

    public void changeMode(Mode mode)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$ui$panels$BottomBgPanel$Mode[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$ui$panels$BottomBgPanel$Mode = new int[Mode.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$panels$BottomBgPanel$Mode[Mode.NORMAL.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$panels$BottomBgPanel$Mode[Mode.OVERLAY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$panels$BottomBgPanel$Mode[Mode.HIDDEN.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.ui.panels.BottomBgPanel.Mode[mode.ordinal()])
        {
        case 1: // '\001'
            target_y = normal_y;
            doneAnimating = false;
            break;

        case 2: // '\002'
            target_y = overlay_y;
            doneAnimating = false;
            break;

        case 3: // '\003'
            target_y = hide_y;
            doneAnimating = false;
            break;
        }
    }

    public void updatePositions()
    {
        if(current_y != target_y)
        {
            current_y = MathUtils.lerp(current_y, target_y, Gdx.graphics.getDeltaTime() * 7F);
            if(Math.abs(current_y - target_y) < 0.3F)
            {
                current_y = target_y;
                doneAnimating = true;
            } else
            {
                doneAnimating = false;
            }
        }
    }

    private static final float SNAP_THRESHOLD = 0.3F;
    private static final float LERP_SPEED = 7F;
    private float current_y;
    private float target_y;
    private float normal_y;
    private float hide_y;
    private float overlay_y;
    public boolean doneAnimating;
}
