// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DialogWord.java

package com.megacrit.cardcrawl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.MathHelper;

public class DialogWord
{
    public static final class WordColor extends Enum
    {

        public static WordColor[] values()
        {
            return (WordColor[])$VALUES.clone();
        }

        public static WordColor valueOf(String name)
        {
            return (WordColor)Enum.valueOf(com/megacrit/cardcrawl/ui/DialogWord$WordColor, name);
        }

        public static final WordColor DEFAULT;
        public static final WordColor RED;
        public static final WordColor GREEN;
        public static final WordColor BLUE;
        public static final WordColor GOLD;
        public static final WordColor PURPLE;
        public static final WordColor WHITE;
        private static final WordColor $VALUES[];

        static 
        {
            DEFAULT = new WordColor("DEFAULT", 0);
            RED = new WordColor("RED", 1);
            GREEN = new WordColor("GREEN", 2);
            BLUE = new WordColor("BLUE", 3);
            GOLD = new WordColor("GOLD", 4);
            PURPLE = new WordColor("PURPLE", 5);
            WHITE = new WordColor("WHITE", 6);
            $VALUES = (new WordColor[] {
                DEFAULT, RED, GREEN, BLUE, GOLD, PURPLE, WHITE
            });
        }

        private WordColor(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class WordEffect extends Enum
    {

        public static WordEffect[] values()
        {
            return (WordEffect[])$VALUES.clone();
        }

        public static WordEffect valueOf(String name)
        {
            return (WordEffect)Enum.valueOf(com/megacrit/cardcrawl/ui/DialogWord$WordEffect, name);
        }

        public static final WordEffect NONE;
        public static final WordEffect WAVY;
        public static final WordEffect SLOW_WAVY;
        public static final WordEffect SHAKY;
        public static final WordEffect PULSE;
        private static final WordEffect $VALUES[];

        static 
        {
            NONE = new WordEffect("NONE", 0);
            WAVY = new WordEffect("WAVY", 1);
            SLOW_WAVY = new WordEffect("SLOW_WAVY", 2);
            SHAKY = new WordEffect("SHAKY", 3);
            PULSE = new WordEffect("PULSE", 4);
            $VALUES = (new WordEffect[] {
                NONE, WAVY, SLOW_WAVY, SHAKY, PULSE
            });
        }

        private WordEffect(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class AppearEffect extends Enum
    {

        public static AppearEffect[] values()
        {
            return (AppearEffect[])$VALUES.clone();
        }

        public static AppearEffect valueOf(String name)
        {
            return (AppearEffect)Enum.valueOf(com/megacrit/cardcrawl/ui/DialogWord$AppearEffect, name);
        }

        public static final AppearEffect NONE;
        public static final AppearEffect FADE_IN;
        public static final AppearEffect GROW_IN;
        public static final AppearEffect BUMP_IN;
        private static final AppearEffect $VALUES[];

        static 
        {
            NONE = new AppearEffect("NONE", 0);
            FADE_IN = new AppearEffect("FADE_IN", 1);
            GROW_IN = new AppearEffect("GROW_IN", 2);
            BUMP_IN = new AppearEffect("BUMP_IN", 3);
            $VALUES = (new AppearEffect[] {
                NONE, FADE_IN, GROW_IN, BUMP_IN
            });
        }

        private AppearEffect(String s, int i)
        {
            super(s, i);
        }
    }


    public DialogWord(BitmapFont font, String word, AppearEffect a_effect, WordEffect effect, WordColor wColor, float x, float y, 
            int line)
    {
        this.line = 0;
        timer = 0.0F;
        scale = 1.0F;
        targetScale = 1.0F;
        if(gl == null)
            gl = new GlyphLayout();
        this.font = font;
        this.effect = effect;
        this.wColor = wColor;
        this.word = word;
        this.x = x;
        this.y = y;
        target_x = x;
        target_y = y;
        targetColor = getColor();
        this.line = line;
        color = new Color(targetColor.r, targetColor.g, targetColor.b, 0.0F);
        if(effect == WordEffect.WAVY || effect == WordEffect.SLOW_WAVY)
            timer = MathUtils.random(1.570796F);
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$AppearEffect[];
            static final int $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[];
            static final int $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordEffect[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordEffect = new int[WordEffect.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordEffect[WordEffect.SHAKY.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordEffect[WordEffect.WAVY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordEffect[WordEffect.SLOW_WAVY.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor = new int[WordColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[WordColor.RED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[WordColor.GREEN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[WordColor.BLUE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[WordColor.GOLD.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[WordColor.PURPLE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[WordColor.WHITE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$AppearEffect = new int[AppearEffect.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$AppearEffect[AppearEffect.FADE_IN.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$AppearEffect[AppearEffect.GROW_IN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$AppearEffect[AppearEffect.BUMP_IN.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.ui.DialogWord.AppearEffect[a_effect.ordinal()])
        {
        case 2: // '\002'
            this.y -= BUMP_OFFSET;
            scale = 0.0F;
            break;

        case 3: // '\003'
            this.y -= BUMP_OFFSET;
            break;
        }
    }

    private Color getColor()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.ui.DialogWord.WordColor[wColor.ordinal()])
        {
        case 1: // '\001'
            return Settings.RED_TEXT_COLOR.cpy();

        case 2: // '\002'
            return Settings.GREEN_TEXT_COLOR.cpy();

        case 3: // '\003'
            return Settings.BLUE_TEXT_COLOR.cpy();

        case 4: // '\004'
            return Settings.GOLD_COLOR.cpy();

        case 5: // '\005'
            return Settings.PURPLE_COLOR.cpy();

        case 6: // '\006'
            return Settings.CREAM_COLOR.cpy();
        }
        return Settings.CREAM_COLOR.cpy();
    }

    public void update()
    {
        if(x != target_x)
            x = MathUtils.lerp(x, target_x, Gdx.graphics.getDeltaTime() * 12F);
        if(y != target_y)
            y = MathUtils.lerp(y, target_y, Gdx.graphics.getDeltaTime() * 12F);
        color = color.lerp(targetColor, Gdx.graphics.getDeltaTime() * 8F);
        if(scale != targetScale)
            scale = MathHelper.scaleLerpSnap(scale, targetScale);
        applyEffects();
    }

    private void applyEffects()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.ui.DialogWord.WordEffect[effect.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            timer -= Gdx.graphics.getDeltaTime();
            if(timer < 0.0F)
            {
                offset_x = MathUtils.random(-SHAKE_AMT, SHAKE_AMT);
                offset_y = MathUtils.random(-SHAKE_AMT, SHAKE_AMT);
                timer = 0.02F;
            }
            break;

        case 2: // '\002'
            timer += Gdx.graphics.getDeltaTime() * 6F;
            offset_y = (float)Math.cos(timer) * Settings.scale * 3F;
            break;

        case 3: // '\003'
            timer += Gdx.graphics.getDeltaTime() * 3F;
            offset_y = (float)Math.cos(timer) * Settings.scale * 1.5F;
            break;
        }
    }

    public void dialogFadeOut()
    {
        targetColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        target_y -= DIALOG_FADE_Y;
    }

    public void shiftY(float shiftAmount)
    {
        target_y += shiftAmount;
    }

    public void shiftX(float shiftAmount)
    {
        target_x += shiftAmount;
    }

    public void setX(float newX)
    {
        target_x = newX;
    }

    public void render(SpriteBatch sb)
    {
        font.setColor(color);
        font.getData().setScale(scale);
        font.draw(sb, word, x + offset_x, y + offset_y);
        font.getData().setScale(1.0F);
    }

    public void render(SpriteBatch sb, float y2)
    {
        font.setColor(color);
        font.getData().setScale(scale);
        font.draw(sb, word, x + offset_x, y + offset_y + y2);
        font.getData().setScale(1.0F);
    }

    public static WordEffect identifyWordEffect(String word)
    {
        if(word.length() > 2)
        {
            if(word.charAt(0) == '@' && word.charAt(word.length() - 1) == '@')
                return WordEffect.SHAKY;
            if(word.charAt(0) == '~' && word.charAt(word.length() - 1) == '~')
                return WordEffect.WAVY;
        }
        return WordEffect.NONE;
    }

    public static WordColor identifyWordColor(String word)
    {
        if(word.charAt(0) == '#')
            switch(word.charAt(1))
            {
            case 114: // 'r'
                return WordColor.RED;

            case 103: // 'g'
                return WordColor.GREEN;

            case 98: // 'b'
                return WordColor.BLUE;

            case 121: // 'y'
                return WordColor.GOLD;

            case 112: // 'p'
                return WordColor.PURPLE;
            }
        return WordColor.DEFAULT;
    }

    private BitmapFont font;
    private WordEffect effect;
    private WordColor wColor;
    public String word;
    public int line;
    private float x;
    private float y;
    private float target_x;
    private float target_y;
    private float offset_x;
    private float offset_y;
    private float timer;
    private Color color;
    private Color targetColor;
    private float scale;
    private float targetScale;
    private static final float BUMP_OFFSET;
    private static GlyphLayout gl;
    private static final float COLOR_LERP_SPEED = 8F;
    private static final float SHAKE_AMT;
    private static final float DIALOG_FADE_Y;
    private static final float WAVY_DIST = 3F;
    private static final float SHAKE_INTERVAL = 0.02F;

    static 
    {
        BUMP_OFFSET = 20F * Settings.scale;
        SHAKE_AMT = 2.0F * Settings.scale;
        DIALOG_FADE_Y = 50F * Settings.scale;
    }
}
