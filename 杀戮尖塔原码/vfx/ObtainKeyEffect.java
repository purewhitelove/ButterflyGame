// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ObtainKeyEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class ObtainKeyEffect extends AbstractGameEffect
{
    public static final class KeyColor extends Enum
    {

        public static KeyColor[] values()
        {
            return (KeyColor[])$VALUES.clone();
        }

        public static KeyColor valueOf(String name)
        {
            return (KeyColor)Enum.valueOf(com/megacrit/cardcrawl/vfx/ObtainKeyEffect$KeyColor, name);
        }

        public static final KeyColor RED;
        public static final KeyColor GREEN;
        public static final KeyColor BLUE;
        private static final KeyColor $VALUES[];

        static 
        {
            RED = new KeyColor("RED", 0);
            GREEN = new KeyColor("GREEN", 1);
            BLUE = new KeyColor("BLUE", 2);
            $VALUES = (new KeyColor[] {
                RED, GREEN, BLUE
            });
        }

        private KeyColor(String s, int i)
        {
            super(s, i);
        }
    }


    public ObtainKeyEffect(KeyColor keyColor)
    {
        this.keyColor = null;
        this.keyColor = keyColor;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$vfx$ObtainKeyEffect$KeyColor[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$vfx$ObtainKeyEffect$KeyColor = new int[KeyColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$ObtainKeyEffect$KeyColor[KeyColor.RED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$ObtainKeyEffect$KeyColor[KeyColor.GREEN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$ObtainKeyEffect$KeyColor[KeyColor.BLUE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.vfx.ObtainKeyEffect.KeyColor[keyColor.ordinal()])
        {
        case 1: // '\001'
            img = ImageMaster.RUBY_KEY;
            break;

        case 2: // '\002'
            img = ImageMaster.EMERALD_KEY;
            break;

        case 3: // '\003'
            img = ImageMaster.SAPPHIRE_KEY;
            break;
        }
        duration = 0.33F;
        startingDuration = 0.33F;
        x = -32F + 46F * Settings.scale;
        y = (float)(Settings.HEIGHT - 32) - 35F * Settings.scale;
        color = Color.WHITE.cpy();
        color.a = 0.0F;
        rotation = 180F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            duration = 0.0F;
            color.a = 0.0F;
            CardCrawlGame.sound.playA("KEY_OBTAIN", -0.2F);
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.vfx.ObtainKeyEffect.KeyColor[keyColor.ordinal()])
            {
            case 1: // '\001'
                img = ImageMaster.RUBY_KEY;
                Settings.hasRubyKey = true;
                break;

            case 2: // '\002'
                img = ImageMaster.EMERALD_KEY;
                Settings.hasEmeraldKey = true;
                break;

            case 3: // '\003'
                img = ImageMaster.SAPPHIRE_KEY;
                Settings.hasSapphireKey = true;
                break;
            }
        } else
        {
            color.a = Interpolation.fade.apply(1.0F, 0.0F, duration * 3F);
            scale = Interpolation.pow4In.apply(1.1F, 5F, duration * 3F) * Settings.scale;
            rotation = Interpolation.pow4In.apply(0.0F, 180F, duration * 3F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, duration * 3F));
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private Texture img;
    private float x;
    private float y;
    private KeyColor keyColor;
}
