// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlashPotionEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.potions.AbstractPotion;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class FlashPotionEffect extends AbstractGameEffect
{

    public FlashPotionEffect(AbstractPotion p)
    {
        renderHybrid = false;
        renderSpots = false;
        duration = 1.0F;
        liquidColor = p.liquidColor.cpy();
        if(p.hybridColor != null)
        {
            renderHybrid = true;
            hybridColor = p.hybridColor.cpy();
        }
        if(p.spotsColor != null)
        {
            renderSpots = true;
            spotsColor = p.spotsColor.cpy();
        }
        color = Color.WHITE.cpy();
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize = new int[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.T.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.S.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.M.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.SPHERE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.H.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.BOTTLE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.HEART.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.SNECKO.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.FAIRY.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.GHOST.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.JAR.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.BOLT.ordinal()] = 12;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.CARD.ordinal()] = 13;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.MOON.ordinal()] = 14;
                }
                catch(NoSuchFieldError nosuchfielderror13) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.SPIKY.ordinal()] = 15;
                }
                catch(NoSuchFieldError nosuchfielderror14) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.EYE.ordinal()] = 16;
                }
                catch(NoSuchFieldError nosuchfielderror15) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize.ANVIL.ordinal()] = 17;
                }
                catch(NoSuchFieldError nosuchfielderror16) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize[p.size.ordinal()])
        {
        case 1: // '\001'
            containerImg = ImageMaster.POTION_T_CONTAINER;
            liquidImg = ImageMaster.POTION_T_LIQUID;
            hybridImg = ImageMaster.POTION_T_HYBRID;
            spotsImg = ImageMaster.POTION_T_SPOTS;
            break;

        case 2: // '\002'
            containerImg = ImageMaster.POTION_S_CONTAINER;
            liquidImg = ImageMaster.POTION_S_LIQUID;
            hybridImg = ImageMaster.POTION_S_HYBRID;
            spotsImg = ImageMaster.POTION_S_SPOTS;
            break;

        case 3: // '\003'
            containerImg = ImageMaster.POTION_M_CONTAINER;
            liquidImg = ImageMaster.POTION_M_LIQUID;
            hybridImg = ImageMaster.POTION_M_HYBRID;
            spotsImg = ImageMaster.POTION_M_SPOTS;
            break;

        case 4: // '\004'
            containerImg = ImageMaster.POTION_SPHERE_CONTAINER;
            liquidImg = ImageMaster.POTION_SPHERE_LIQUID;
            hybridImg = ImageMaster.POTION_SPHERE_HYBRID;
            spotsImg = ImageMaster.POTION_SPHERE_SPOTS;
            break;

        case 5: // '\005'
            containerImg = ImageMaster.POTION_H_CONTAINER;
            liquidImg = ImageMaster.POTION_H_LIQUID;
            hybridImg = ImageMaster.POTION_H_HYBRID;
            spotsImg = ImageMaster.POTION_H_SPOTS;
            break;

        case 6: // '\006'
            containerImg = ImageMaster.POTION_BOTTLE_CONTAINER;
            liquidImg = ImageMaster.POTION_BOTTLE_LIQUID;
            hybridImg = ImageMaster.POTION_BOTTLE_HYBRID;
            spotsImg = ImageMaster.POTION_BOTTLE_SPOTS;
            break;

        case 7: // '\007'
            containerImg = ImageMaster.POTION_HEART_CONTAINER;
            liquidImg = ImageMaster.POTION_HEART_LIQUID;
            hybridImg = ImageMaster.POTION_HEART_HYBRID;
            spotsImg = ImageMaster.POTION_HEART_SPOTS;
            break;

        case 8: // '\b'
            containerImg = ImageMaster.POTION_SNECKO_CONTAINER;
            liquidImg = ImageMaster.POTION_SNECKO_LIQUID;
            hybridImg = ImageMaster.POTION_SNECKO_HYBRID;
            spotsImg = ImageMaster.POTION_SNECKO_SPOTS;
            break;

        case 9: // '\t'
            containerImg = ImageMaster.POTION_FAIRY_CONTAINER;
            liquidImg = ImageMaster.POTION_FAIRY_LIQUID;
            hybridImg = ImageMaster.POTION_FAIRY_HYBRID;
            spotsImg = ImageMaster.POTION_FAIRY_SPOTS;
            break;

        case 10: // '\n'
            containerImg = ImageMaster.POTION_GHOST_CONTAINER;
            liquidImg = ImageMaster.POTION_GHOST_LIQUID;
            hybridImg = ImageMaster.POTION_GHOST_HYBRID;
            spotsImg = ImageMaster.POTION_GHOST_SPOTS;
            break;

        case 11: // '\013'
            containerImg = ImageMaster.POTION_JAR_CONTAINER;
            liquidImg = ImageMaster.POTION_JAR_LIQUID;
            hybridImg = ImageMaster.POTION_JAR_HYBRID;
            spotsImg = ImageMaster.POTION_JAR_SPOTS;
            break;

        case 12: // '\f'
            containerImg = ImageMaster.POTION_BOLT_CONTAINER;
            liquidImg = ImageMaster.POTION_BOLT_LIQUID;
            hybridImg = ImageMaster.POTION_BOLT_HYBRID;
            spotsImg = ImageMaster.POTION_BOLT_SPOTS;
            break;

        case 13: // '\r'
            containerImg = ImageMaster.POTION_CARD_CONTAINER;
            liquidImg = ImageMaster.POTION_CARD_LIQUID;
            hybridImg = ImageMaster.POTION_CARD_HYBRID;
            spotsImg = ImageMaster.POTION_CARD_SPOTS;
            break;

        case 14: // '\016'
            containerImg = ImageMaster.POTION_MOON_CONTAINER;
            liquidImg = ImageMaster.POTION_MOON_LIQUID;
            hybridImg = ImageMaster.POTION_MOON_HYBRID;
            break;

        case 15: // '\017'
            containerImg = ImageMaster.POTION_SPIKY_CONTAINER;
            liquidImg = ImageMaster.POTION_SPIKY_LIQUID;
            hybridImg = ImageMaster.POTION_SPIKY_HYBRID;
            break;

        case 16: // '\020'
            containerImg = ImageMaster.POTION_EYE_CONTAINER;
            liquidImg = ImageMaster.POTION_EYE_LIQUID;
            hybridImg = ImageMaster.POTION_EYE_HYBRID;
            break;

        case 17: // '\021'
            containerImg = ImageMaster.POTION_ANVIL_CONTAINER;
            liquidImg = ImageMaster.POTION_ANVIL_LIQUID;
            hybridImg = ImageMaster.POTION_ANVIL_HYBRID;
            break;
        }
    }

    public void update()
    {
        scale = Interpolation.exp10In.apply(Settings.scale, END_SCALE * 2.0F, duration / 1.0F);
        if(duration > 0.3F)
            liquidColor.a = Interpolation.pow2.apply(0.4F, 0.05F, duration / 1.0F);
        else
            liquidColor.a = duration * 2.0F;
        duration -= Gdx.graphics.getDeltaTime();
        color.a = liquidColor.a;
        if(renderHybrid)
            hybridColor.a = liquidColor.a;
        if(renderSpots)
            spotsColor.a = liquidColor.a;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        sb.setColor(color);
        sb.draw(containerImg, x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        sb.setColor(liquidColor);
        sb.draw(liquidImg, x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(containerImg, x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        sb.setColor(liquidColor);
        sb.draw(liquidImg, x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        if(renderHybrid)
        {
            sb.setColor(hybridColor);
            sb.draw(hybridImg, x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        if(renderSpots)
        {
            sb.setColor(spotsColor);
            sb.draw(spotsImg, x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    private static final int W = 64;
    private static final float DURATION = 1F;
    private static final float END_SCALE;
    private Texture containerImg;
    private Texture liquidImg;
    private Texture hybridImg;
    private Texture spotsImg;
    private Color liquidColor;
    private Color color;
    private Color hybridColor;
    private Color spotsColor;
    private boolean renderHybrid;
    private boolean renderSpots;

    static 
    {
        END_SCALE = 8F * Settings.scale;
    }
}
