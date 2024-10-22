// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScreenShake.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.megacrit.cardcrawl.core.Settings;

public class ScreenShake
{
    public static final class ShakeDur extends Enum
    {

        public static ShakeDur[] values()
        {
            return (ShakeDur[])$VALUES.clone();
        }

        public static ShakeDur valueOf(String name)
        {
            return (ShakeDur)Enum.valueOf(com/megacrit/cardcrawl/helpers/ScreenShake$ShakeDur, name);
        }

        public static final ShakeDur SHORT;
        public static final ShakeDur MED;
        public static final ShakeDur LONG;
        public static final ShakeDur XLONG;
        private static final ShakeDur $VALUES[];

        static 
        {
            SHORT = new ShakeDur("SHORT", 0);
            MED = new ShakeDur("MED", 1);
            LONG = new ShakeDur("LONG", 2);
            XLONG = new ShakeDur("XLONG", 3);
            $VALUES = (new ShakeDur[] {
                SHORT, MED, LONG, XLONG
            });
        }

        private ShakeDur(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class ShakeIntensity extends Enum
    {

        public static ShakeIntensity[] values()
        {
            return (ShakeIntensity[])$VALUES.clone();
        }

        public static ShakeIntensity valueOf(String name)
        {
            return (ShakeIntensity)Enum.valueOf(com/megacrit/cardcrawl/helpers/ScreenShake$ShakeIntensity, name);
        }

        public static final ShakeIntensity LOW;
        public static final ShakeIntensity MED;
        public static final ShakeIntensity HIGH;
        private static final ShakeIntensity $VALUES[];

        static 
        {
            LOW = new ShakeIntensity("LOW", 0);
            MED = new ShakeIntensity("MED", 1);
            HIGH = new ShakeIntensity("HIGH", 2);
            $VALUES = (new ShakeIntensity[] {
                LOW, MED, HIGH
            });
        }

        private ShakeIntensity(String s, int i)
        {
            super(s, i);
        }
    }


    public ScreenShake()
    {
        x = 0.0F;
        duration = 0.0F;
        startDuration = 0.0F;
    }

    public void shake(ShakeIntensity intensity, ShakeDur dur, boolean isVertical)
    {
        duration = getDuration(dur);
        startDuration = duration;
        intensityValue = getIntensity(intensity);
        vertical = isVertical;
        intervalSpeed = 0.3F;
    }

    public void rumble(float dur)
    {
        duration = dur;
        startDuration = dur;
        intensityValue = 10F;
        vertical = false;
        intervalSpeed = 0.7F;
    }

    public void mildRumble(float dur)
    {
        duration = dur;
        startDuration = dur;
        intensityValue = 3F;
        vertical = false;
        intervalSpeed = 0.7F;
    }

    public void update(FitViewport viewport)
    {
        if(Settings.HORIZ_LETTERBOX_AMT != 0 || Settings.VERT_LETTERBOX_AMT != 0)
            return;
        if(duration != 0.0F)
        {
            duration -= Gdx.graphics.getDeltaTime();
            if(duration < 0.0F)
            {
                duration = 0.0F;
                viewport.update(Settings.M_W, Settings.M_H);
                return;
            }
            float tmp = Interpolation.fade.apply(0.1F, intensityValue, duration / startDuration);
            x = MathUtils.cosDeg((float)(System.currentTimeMillis() % 360L) / intervalSpeed) * tmp;
            if(Settings.SCREEN_SHAKE)
                if(vertical)
                    viewport.update(Settings.M_W, (int)((float)Settings.M_H + Math.abs(x)));
                else
                    viewport.update((int)((float)Settings.M_W + x), Settings.M_H);
        }
    }

    private float getIntensity(ShakeIntensity intensity)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$helpers$ScreenShake$ShakeIntensity[];
            static final int $SwitchMap$com$megacrit$cardcrawl$helpers$ScreenShake$ShakeDur[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$helpers$ScreenShake$ShakeDur = new int[ShakeDur.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ScreenShake$ShakeDur[ShakeDur.SHORT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ScreenShake$ShakeDur[ShakeDur.MED.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ScreenShake$ShakeDur[ShakeDur.LONG.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ScreenShake$ShakeDur[ShakeDur.XLONG.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                $SwitchMap$com$megacrit$cardcrawl$helpers$ScreenShake$ShakeIntensity = new int[ShakeIntensity.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ScreenShake$ShakeIntensity[ShakeIntensity.LOW.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ScreenShake$ShakeIntensity[ShakeIntensity.MED.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity[intensity.ordinal()])
        {
        case 1: // '\001'
            return 20F * Settings.scale;

        case 2: // '\002'
            return 50F * Settings.scale;
        }
        return 100F * Settings.scale;
    }

    private float getDuration(ShakeDur dur)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur[dur.ordinal()])
        {
        case 1: // '\001'
            return 0.3F;

        case 2: // '\002'
            return 0.5F;

        case 3: // '\003'
            return 1.0F;

        case 4: // '\004'
            return 3F;
        }
        return 1.0F;
    }

    private float x;
    private float duration;
    private float startDuration;
    private float intensityValue;
    private float intervalSpeed;
    private boolean vertical;
}
