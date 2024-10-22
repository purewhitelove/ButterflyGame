// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SplashScreen.java

package com.megacrit.cardcrawl.screens.splash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class SplashScreen
{
    private static final class Phase extends Enum
    {

        public static Phase[] values()
        {
            return (Phase[])$VALUES.clone();
        }

        public static Phase valueOf(String name)
        {
            return (Phase)Enum.valueOf(com/megacrit/cardcrawl/screens/splash/SplashScreen$Phase, name);
        }

        public static final Phase INIT;
        public static final Phase BOUNCE;
        public static final Phase FADE;
        public static final Phase WAIT;
        public static final Phase FADE_OUT;
        private static final Phase $VALUES[];

        static 
        {
            INIT = new Phase("INIT", 0);
            BOUNCE = new Phase("BOUNCE", 1);
            FADE = new Phase("FADE", 2);
            WAIT = new Phase("WAIT", 3);
            FADE_OUT = new Phase("FADE_OUT", 4);
            $VALUES = (new Phase[] {
                INIT, BOUNCE, FADE, WAIT, FADE_OUT
            });
        }

        private Phase(String s, int i)
        {
            super(s, i);
        }
    }


    public SplashScreen()
    {
        timer = 0.0F;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        bgColor = new Color(0.0F, 0.0F, 0.0F, 1.0F);
        shadowColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        phase = Phase.INIT;
        isDone = false;
        x = (float)Settings.WIDTH / 2.0F;
        y = (float)Settings.HEIGHT / 2.0F - OFFSET_Y;
        sX = (float)Settings.WIDTH / 2.0F;
        sY = (float)Settings.HEIGHT / 2.0F;
        cream = Color.valueOf("ffffdbff");
        bgBlue = Color.valueOf("074254ff");
        playSfx = false;
        sfxId = -1L;
        sfxKey = null;
        img = ImageMaster.loadImage("images/megaCrit.png");
    }

    public void update()
    {
        if((InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) && phase != Phase.FADE_OUT)
        {
            phase = Phase.FADE_OUT;
            timer = 1.0F;
            if(sfxKey != null)
                CardCrawlGame.sound.fadeOut(sfxKey, sfxId);
        }
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$splash$SplashScreen$Phase[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$splash$SplashScreen$Phase = new int[Phase.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$splash$SplashScreen$Phase[Phase.INIT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$splash$SplashScreen$Phase[Phase.BOUNCE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$splash$SplashScreen$Phase[Phase.FADE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$splash$SplashScreen$Phase[Phase.WAIT.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$splash$SplashScreen$Phase[Phase.FADE_OUT.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.splash.SplashScreen.Phase[phase.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            timer -= Gdx.graphics.getDeltaTime();
            if(timer < 0.0F)
            {
                phase = Phase.BOUNCE;
                timer = 1.2F;
            }
            break;

        case 2: // '\002'
            timer -= Gdx.graphics.getDeltaTime();
            color.a = Interpolation.fade.apply(1.0F, 0.0F, timer / 1.2F);
            y = Interpolation.elasticIn.apply((float)Settings.HEIGHT / 2.0F, (float)Settings.HEIGHT / 2.0F - 200F * Settings.scale, timer / 1.2F);
            if(timer < 0.96F && !playSfx)
            {
                playSfx = true;
                sfxId = CardCrawlGame.sound.play("SPLASH");
            }
            if(timer < 0.0F)
            {
                phase = Phase.FADE;
                timer = 3F;
            }
            break;

        case 3: // '\003'
            timer -= Gdx.graphics.getDeltaTime();
            color.r = Interpolation.fade.apply(cream.r, 1.0F, timer / 3F);
            color.g = Interpolation.fade.apply(cream.g, 1.0F, timer / 3F);
            color.b = Interpolation.fade.apply(cream.b, 1.0F, timer / 3F);
            bgColor.r = Interpolation.fade.apply(bgBlue.r, 0.0F, timer / 3F);
            bgColor.g = Interpolation.fade.apply(bgBlue.g, 0.0F, timer / 3F);
            bgColor.b = Interpolation.fade.apply(bgBlue.b, 0.0F, timer / 3F);
            sX = Interpolation.exp5Out.apply((float)Settings.WIDTH / 2.0F + OFFSET_X, (float)Settings.WIDTH / 2.0F, timer / 3F);
            sY = Interpolation.exp5Out.apply((float)Settings.HEIGHT / 2.0F - OFFSET_Y, (float)Settings.HEIGHT / 2.0F, timer / 3F);
            if(timer < 0.0F)
            {
                phase = Phase.WAIT;
                timer = 1.5F;
            }
            break;

        case 4: // '\004'
            timer -= Gdx.graphics.getDeltaTime();
            if(timer < 0.0F)
            {
                phase = Phase.FADE_OUT;
                timer = 1.0F;
            }
            break;

        case 5: // '\005'
            bgColor.a = Interpolation.fade.apply(0.0F, 1.0F, timer / 1.0F);
            color.a = Interpolation.fade.apply(0.0F, 1.0F, timer / 1.0F);
            timer -= Gdx.graphics.getDeltaTime();
            if(timer < 0.0F)
            {
                img.dispose();
                isDone = true;
            }
            break;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(bgColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(shadowColor);
        sb.draw(img, sX - 256F, sY - 256F, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
        Color s = new Color(0.0F, 0.0F, 0.0F, color.a / 5F);
        sb.setColor(s);
        sb.draw(img, (x - 256F) + 14F * Settings.scale, y - 256F - 14F * Settings.scale, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
        sb.setColor(color);
        sb.draw(img, x - 256F, y - 256F, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
    }

    private Texture img;
    private float timer;
    private static final float BOUNCE_DUR = 1.2F;
    private static final float FADE_DUR = 3F;
    private static final float WAIT_DUR = 1.5F;
    private static final float FADE_OUT_DUR = 1F;
    private static final int W = 512;
    private static final int H = 512;
    private Color color;
    private Color bgColor;
    private Color shadowColor;
    private Phase phase;
    public boolean isDone;
    private static final float OFFSET_Y;
    private static final float OFFSET_X;
    private float x;
    private float y;
    private float sX;
    private float sY;
    private Color cream;
    private Color bgBlue;
    private boolean playSfx;
    private long sfxId;
    private String sfxKey;

    static 
    {
        OFFSET_Y = 8F * Settings.scale;
        OFFSET_X = 12F * Settings.scale;
    }
}
