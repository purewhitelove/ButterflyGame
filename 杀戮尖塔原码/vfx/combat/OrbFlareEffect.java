// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrbFlareEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class OrbFlareEffect extends AbstractGameEffect
{
    public static final class OrbFlareColor extends Enum
    {

        public static OrbFlareColor[] values()
        {
            return (OrbFlareColor[])$VALUES.clone();
        }

        public static OrbFlareColor valueOf(String name)
        {
            return (OrbFlareColor)Enum.valueOf(com/megacrit/cardcrawl/vfx/combat/OrbFlareEffect$OrbFlareColor, name);
        }

        public static final OrbFlareColor LIGHTNING;
        public static final OrbFlareColor DARK;
        public static final OrbFlareColor PLASMA;
        public static final OrbFlareColor FROST;
        private static final OrbFlareColor $VALUES[];

        static 
        {
            LIGHTNING = new OrbFlareColor("LIGHTNING", 0);
            DARK = new OrbFlareColor("DARK", 1);
            PLASMA = new OrbFlareColor("PLASMA", 2);
            FROST = new OrbFlareColor("FROST", 3);
            $VALUES = (new OrbFlareColor[] {
                LIGHTNING, DARK, PLASMA, FROST
            });
        }

        private OrbFlareColor(String s, int i)
        {
            super(s, i);
        }
    }


    public OrbFlareEffect(AbstractOrb orb, OrbFlareColor setColor)
    {
        if(outer == null)
        {
            outer = ImageMaster.vfxAtlas.findRegion("combat/orbFlareOuter");
            inner = ImageMaster.vfxAtlas.findRegion("combat/orbFlareInner");
        }
        this.orb = orb;
        renderBehind = true;
        duration = 0.5F;
        startingDuration = 0.5F;
        flareColor = setColor;
        setColor();
        scale = 2.0F * Settings.scale;
        scaleY = 0.0F;
    }

    private void setColor()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$vfx$combat$OrbFlareEffect$OrbFlareColor[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$vfx$combat$OrbFlareEffect$OrbFlareColor = new int[OrbFlareColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$combat$OrbFlareEffect$OrbFlareColor[OrbFlareColor.DARK.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$combat$OrbFlareEffect$OrbFlareColor[OrbFlareColor.FROST.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$combat$OrbFlareEffect$OrbFlareColor[OrbFlareColor.LIGHTNING.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$combat$OrbFlareEffect$OrbFlareColor[OrbFlareColor.PLASMA.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor[flareColor.ordinal()])
        {
        case 1: // '\001'
            color = Color.VIOLET.cpy();
            color2 = Color.BLACK.cpy();
            break;

        case 2: // '\002'
            color = Settings.BLUE_TEXT_COLOR.cpy();
            color2 = Color.LIGHT_GRAY.cpy();
            break;

        case 3: // '\003'
            color = Color.CHARTREUSE.cpy();
            color2 = Color.WHITE.cpy();
            break;

        case 4: // '\004'
            color = Color.CORAL.cpy();
            color2 = Color.CYAN.cpy();
            break;
        }
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            duration = 0.0F;
            isDone = true;
        }
        scaleY = Interpolation.elasticIn.apply(2.2F, 0.8F, duration * 2.0F);
        scale = Interpolation.elasticIn.apply(2.1F, 1.9F, duration * 2.0F);
        color.a = Interpolation.pow2Out.apply(0.0F, 0.6F, duration * 2.0F);
        color2.a = color.a;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color2);
        sb.draw(inner, orb.cX - (float)inner.packedWidth / 2.0F, orb.cY - (float)inner.packedHeight / 2.0F, (float)inner.packedWidth / 2.0F, (float)inner.packedHeight / 2.0F, inner.packedWidth, inner.packedHeight, scale * Settings.scale * 1.1F, scaleY * Settings.scale, MathUtils.random(-1F, 1.0F));
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(outer, orb.cX - (float)outer.packedWidth / 2.0F, orb.cY - (float)outer.packedHeight / 2.0F, (float)outer.packedWidth / 2.0F, (float)outer.packedHeight / 2.0F, outer.packedWidth, outer.packedHeight, scale, scaleY * Settings.scale, MathUtils.random(-2F, 2.0F));
        sb.draw(outer, orb.cX - (float)outer.packedWidth / 2.0F, orb.cY - (float)outer.packedHeight / 2.0F, (float)outer.packedWidth / 2.0F, (float)outer.packedHeight / 2.0F, outer.packedWidth, outer.packedHeight, scale, scaleY * Settings.scale, MathUtils.random(-2F, 2.0F));
        sb.setBlendFunction(770, 771);
        sb.setColor(color2);
        sb.draw(inner, orb.cX - (float)inner.packedWidth / 2.0F, orb.cY - (float)inner.packedHeight / 2.0F, (float)inner.packedWidth / 2.0F, (float)inner.packedHeight / 2.0F, inner.packedWidth, inner.packedHeight, scale * Settings.scale * 1.1F, scaleY * Settings.scale, MathUtils.random(-1F, 1.0F));
    }

    public void dispose()
    {
    }

    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion outer;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion inner;
    private float scaleY;
    private static final float DUR = 0.5F;
    private AbstractOrb orb;
    private OrbFlareColor flareColor;
    private Color color2;
}
