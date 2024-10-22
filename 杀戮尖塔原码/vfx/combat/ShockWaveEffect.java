// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShockWaveEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            BlurWaveAdditiveEffect, BlurWaveNormalEffect, BlurWaveChaoticEffect

public class ShockWaveEffect extends AbstractGameEffect
{
    public static final class ShockWaveType extends Enum
    {

        public static ShockWaveType[] values()
        {
            return (ShockWaveType[])$VALUES.clone();
        }

        public static ShockWaveType valueOf(String name)
        {
            return (ShockWaveType)Enum.valueOf(com/megacrit/cardcrawl/vfx/combat/ShockWaveEffect$ShockWaveType, name);
        }

        public static final ShockWaveType ADDITIVE;
        public static final ShockWaveType NORMAL;
        public static final ShockWaveType CHAOTIC;
        private static final ShockWaveType $VALUES[];

        static 
        {
            ADDITIVE = new ShockWaveType("ADDITIVE", 0);
            NORMAL = new ShockWaveType("NORMAL", 1);
            CHAOTIC = new ShockWaveType("CHAOTIC", 2);
            $VALUES = (new ShockWaveType[] {
                ADDITIVE, NORMAL, CHAOTIC
            });
        }

        private ShockWaveType(String s, int i)
        {
            super(s, i);
        }
    }


    public ShockWaveEffect(float x, float y, Color color, ShockWaveType type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
        this.color = color;
    }

    public void update()
    {
        float speed = MathUtils.random(1000F, 1200F) * Settings.scale;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$vfx$combat$ShockWaveEffect$ShockWaveType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$vfx$combat$ShockWaveEffect$ShockWaveType = new int[ShockWaveType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$combat$ShockWaveEffect$ShockWaveType[ShockWaveType.ADDITIVE.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$combat$ShockWaveEffect$ShockWaveType[ShockWaveType.NORMAL.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$combat$ShockWaveEffect$ShockWaveType[ShockWaveType.CHAOTIC.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType[type.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            for(int i = 0; i < 40; i++)
                AbstractDungeon.effectsQueue.add(new BlurWaveAdditiveEffect(x, y, color.cpy(), speed));

            break;

        case 2: // '\002'
            for(int i = 0; i < 40; i++)
                AbstractDungeon.effectsQueue.add(new BlurWaveNormalEffect(x, y, color.cpy(), speed));

            break;

        case 3: // '\003'
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
            for(int i = 0; i < 40; i++)
                AbstractDungeon.effectsQueue.add(new BlurWaveChaoticEffect(x, y, color.cpy(), speed));

            break;
        }
        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private ShockWaveType type;
    private Color color;
}
