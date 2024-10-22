// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpeechWord.java

package com.megacrit.cardcrawl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.MathHelper;

// Referenced classes of package com.megacrit.cardcrawl.ui:
//            DialogWord

public class SpeechWord
{

    public SpeechWord(BitmapFont font, String word, DialogWord.AppearEffect a_effect, DialogWord.WordEffect effect, DialogWord.WordColor wColor, float x, float y, 
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
        if(effect == DialogWord.WordEffect.WAVY)
            timer = MathUtils.random(1.570796F);
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$AppearEffect[];
            static final int $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[];
            static final int $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordEffect[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordEffect = new int[DialogWord.WordEffect.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordEffect[DialogWord.WordEffect.SHAKY.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordEffect[DialogWord.WordEffect.WAVY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordEffect[DialogWord.WordEffect.SLOW_WAVY.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor = new int[DialogWord.WordColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[DialogWord.WordColor.RED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[DialogWord.WordColor.GREEN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[DialogWord.WordColor.BLUE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[DialogWord.WordColor.GOLD.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$WordColor[DialogWord.WordColor.WHITE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$AppearEffect = new int[DialogWord.AppearEffect.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$AppearEffect[DialogWord.AppearEffect.FADE_IN.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$AppearEffect[DialogWord.AppearEffect.GROW_IN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$DialogWord$AppearEffect[DialogWord.AppearEffect.BUMP_IN.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
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
            return new Color(1.0F, 0.2F, 0.3F, 1.0F);

        case 2: // '\002'
            return new Color(0.3F, 1.0F, 0.1F, 1.0F);

        case 3: // '\003'
            return Settings.BLUE_TEXT_COLOR.cpy();

        case 4: // '\004'
            return Settings.GOLD_COLOR.cpy();

        case 5: // '\005'
            return Settings.CREAM_COLOR.cpy();
        }
        return Color.DARK_GRAY.cpy();
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
            timer += Gdx.graphics.getDeltaTime() * 5F;
            break;

        case 3: // '\003'
            timer += Gdx.graphics.getDeltaTime() * 2.5F;
            break;
        }
    }

    public void fadeOut()
    {
        targetColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
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

    public static DialogWord.WordEffect identifyWordEffect(String word)
    {
        if(word.length() > 2)
        {
            if(word.charAt(0) == '@' && word.charAt(word.length() - 1) == '@')
                return DialogWord.WordEffect.SHAKY;
            if(word.charAt(0) == '~' && word.charAt(word.length() - 1) == '~')
                return DialogWord.WordEffect.WAVY;
        }
        return DialogWord.WordEffect.NONE;
    }

    public static DialogWord.WordColor identifyWordColor(String word)
    {
        if(word.charAt(0) == '#')
            switch(word.charAt(1))
            {
            case 114: // 'r'
                return DialogWord.WordColor.RED;

            case 103: // 'g'
                return DialogWord.WordColor.GREEN;

            case 98: // 'b'
                return DialogWord.WordColor.BLUE;

            case 121: // 'y'
                return DialogWord.WordColor.GOLD;
            }
        return DialogWord.WordColor.DEFAULT;
    }

    public void render(SpriteBatch sb)
    {
        font.setColor(color);
        font.getData().setScale(scale);
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.ui.DialogWord.WordEffect[effect.ordinal()])
        {
        case 2: // '\002'
            float charOffset = 0.0F;
            int j = 0;
            char ac[] = word.toCharArray();
            int k = ac.length;
            for(int l = 0; l < k; l++)
            {
                char c = ac[l];
                String i = Character.toString(c);
                gl.setText(font, i);
                font.draw(sb, i, x + offset_x + charOffset, y + MathUtils.cosDeg(((System.currentTimeMillis() + (long)(j * 70)) / 4L) % 360L) * WAVY_DIST);
                charOffset += gl.width;
                j++;
            }

            break;

        case 3: // '\003'
            float charOffset3 = 0.0F;
            int j3 = 0;
            char ac1[] = word.toCharArray();
            int i1 = ac1.length;
            for(int j1 = 0; j1 < i1; j1++)
            {
                char c = ac1[j1];
                String i = Character.toString(c);
                gl.setText(font, i);
                font.draw(sb, i, x + offset_x + charOffset3, y + MathUtils.cosDeg(((System.currentTimeMillis() + (long)(j3 * 70)) / 4L) % 360L) * (WAVY_DIST / 2.0F));
                charOffset3 += gl.width;
                j3++;
            }

            break;

        case 1: // '\001'
            float charOffset2 = 0.0F;
            char ac2[] = word.toCharArray();
            int k1 = ac2.length;
            for(int l1 = 0; l1 < k1; l1++)
            {
                char c = ac2[l1];
                String i = Character.toString(c);
                gl.setText(font, i);
                font.draw(sb, i, x + MathUtils.random(-2F, 2.0F) * Settings.scale + charOffset2, y + MathUtils.random(-2F, 2.0F) * Settings.scale);
                charOffset2 += gl.width;
            }

            break;

        default:
            font.draw(sb, word, x + offset_x, y + offset_y);
            break;
        }
        font.getData().setScale(1.0F);
    }

    public void render(SpriteBatch sb, float y2)
    {
        font.setColor(color);
        font.getData().setScale(scale);
        font.draw(sb, word, x + offset_x, y + offset_y + y2);
        font.getData().setScale(1.0F);
    }

    private BitmapFont font;
    private DialogWord.WordEffect effect;
    private DialogWord.WordColor wColor;
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
    private static final float WAVY_DIST;
    private static final float SHAKE_INTERVAL = 0.02F;

    static 
    {
        BUMP_OFFSET = 20F * Settings.scale;
        SHAKE_AMT = 2.0F * Settings.scale;
        DIALOG_FADE_Y = 50F * Settings.scale;
        WAVY_DIST = 3F * Settings.scale;
    }
}
