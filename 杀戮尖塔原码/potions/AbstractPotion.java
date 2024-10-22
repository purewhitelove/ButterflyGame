// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractPotion.java

package com.megacrit.cardcrawl.potions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            PotionSlot

public abstract class AbstractPotion
{
    public static final class PotionEffect extends Enum
    {

        public static PotionEffect[] values()
        {
            return (PotionEffect[])$VALUES.clone();
        }

        public static PotionEffect valueOf(String name)
        {
            return (PotionEffect)Enum.valueOf(com/megacrit/cardcrawl/potions/AbstractPotion$PotionEffect, name);
        }

        public static final PotionEffect NONE;
        public static final PotionEffect RAINBOW;
        public static final PotionEffect OSCILLATE;
        private static final PotionEffect $VALUES[];

        static 
        {
            NONE = new PotionEffect("NONE", 0);
            RAINBOW = new PotionEffect("RAINBOW", 1);
            OSCILLATE = new PotionEffect("OSCILLATE", 2);
            $VALUES = (new PotionEffect[] {
                NONE, RAINBOW, OSCILLATE
            });
        }

        private PotionEffect(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class PotionColor extends Enum
    {

        public static PotionColor[] values()
        {
            return (PotionColor[])$VALUES.clone();
        }

        public static PotionColor valueOf(String name)
        {
            return (PotionColor)Enum.valueOf(com/megacrit/cardcrawl/potions/AbstractPotion$PotionColor, name);
        }

        public static final PotionColor POISON;
        public static final PotionColor BLUE;
        public static final PotionColor FIRE;
        public static final PotionColor GREEN;
        public static final PotionColor EXPLOSIVE;
        public static final PotionColor WEAK;
        public static final PotionColor FEAR;
        public static final PotionColor STRENGTH;
        public static final PotionColor WHITE;
        public static final PotionColor FAIRY;
        public static final PotionColor ANCIENT;
        public static final PotionColor ELIXIR;
        public static final PotionColor NONE;
        public static final PotionColor ENERGY;
        public static final PotionColor SWIFT;
        public static final PotionColor FRUIT;
        public static final PotionColor SNECKO;
        public static final PotionColor SMOKE;
        public static final PotionColor STEROID;
        public static final PotionColor SKILL;
        public static final PotionColor ATTACK;
        public static final PotionColor POWER;
        private static final PotionColor $VALUES[];

        static 
        {
            POISON = new PotionColor("POISON", 0);
            BLUE = new PotionColor("BLUE", 1);
            FIRE = new PotionColor("FIRE", 2);
            GREEN = new PotionColor("GREEN", 3);
            EXPLOSIVE = new PotionColor("EXPLOSIVE", 4);
            WEAK = new PotionColor("WEAK", 5);
            FEAR = new PotionColor("FEAR", 6);
            STRENGTH = new PotionColor("STRENGTH", 7);
            WHITE = new PotionColor("WHITE", 8);
            FAIRY = new PotionColor("FAIRY", 9);
            ANCIENT = new PotionColor("ANCIENT", 10);
            ELIXIR = new PotionColor("ELIXIR", 11);
            NONE = new PotionColor("NONE", 12);
            ENERGY = new PotionColor("ENERGY", 13);
            SWIFT = new PotionColor("SWIFT", 14);
            FRUIT = new PotionColor("FRUIT", 15);
            SNECKO = new PotionColor("SNECKO", 16);
            SMOKE = new PotionColor("SMOKE", 17);
            STEROID = new PotionColor("STEROID", 18);
            SKILL = new PotionColor("SKILL", 19);
            ATTACK = new PotionColor("ATTACK", 20);
            POWER = new PotionColor("POWER", 21);
            $VALUES = (new PotionColor[] {
                POISON, BLUE, FIRE, GREEN, EXPLOSIVE, WEAK, FEAR, STRENGTH, WHITE, FAIRY, 
                ANCIENT, ELIXIR, NONE, ENERGY, SWIFT, FRUIT, SNECKO, SMOKE, STEROID, SKILL, 
                ATTACK, POWER
            });
        }

        private PotionColor(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class PotionRarity extends Enum
    {

        public static PotionRarity[] values()
        {
            return (PotionRarity[])$VALUES.clone();
        }

        public static PotionRarity valueOf(String name)
        {
            return (PotionRarity)Enum.valueOf(com/megacrit/cardcrawl/potions/AbstractPotion$PotionRarity, name);
        }

        public static final PotionRarity PLACEHOLDER;
        public static final PotionRarity COMMON;
        public static final PotionRarity UNCOMMON;
        public static final PotionRarity RARE;
        private static final PotionRarity $VALUES[];

        static 
        {
            PLACEHOLDER = new PotionRarity("PLACEHOLDER", 0);
            COMMON = new PotionRarity("COMMON", 1);
            UNCOMMON = new PotionRarity("UNCOMMON", 2);
            RARE = new PotionRarity("RARE", 3);
            $VALUES = (new PotionRarity[] {
                PLACEHOLDER, COMMON, UNCOMMON, RARE
            });
        }

        private PotionRarity(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class PotionSize extends Enum
    {

        public static PotionSize[] values()
        {
            return (PotionSize[])$VALUES.clone();
        }

        public static PotionSize valueOf(String name)
        {
            return (PotionSize)Enum.valueOf(com/megacrit/cardcrawl/potions/AbstractPotion$PotionSize, name);
        }

        public static final PotionSize T;
        public static final PotionSize S;
        public static final PotionSize M;
        public static final PotionSize SPHERE;
        public static final PotionSize H;
        public static final PotionSize BOTTLE;
        public static final PotionSize HEART;
        public static final PotionSize SNECKO;
        public static final PotionSize FAIRY;
        public static final PotionSize GHOST;
        public static final PotionSize JAR;
        public static final PotionSize BOLT;
        public static final PotionSize CARD;
        public static final PotionSize MOON;
        public static final PotionSize SPIKY;
        public static final PotionSize EYE;
        public static final PotionSize ANVIL;
        private static final PotionSize $VALUES[];

        static 
        {
            T = new PotionSize("T", 0);
            S = new PotionSize("S", 1);
            M = new PotionSize("M", 2);
            SPHERE = new PotionSize("SPHERE", 3);
            H = new PotionSize("H", 4);
            BOTTLE = new PotionSize("BOTTLE", 5);
            HEART = new PotionSize("HEART", 6);
            SNECKO = new PotionSize("SNECKO", 7);
            FAIRY = new PotionSize("FAIRY", 8);
            GHOST = new PotionSize("GHOST", 9);
            JAR = new PotionSize("JAR", 10);
            BOLT = new PotionSize("BOLT", 11);
            CARD = new PotionSize("CARD", 12);
            MOON = new PotionSize("MOON", 13);
            SPIKY = new PotionSize("SPIKY", 14);
            EYE = new PotionSize("EYE", 15);
            ANVIL = new PotionSize("ANVIL", 16);
            $VALUES = (new PotionSize[] {
                T, S, M, SPHERE, H, BOTTLE, HEART, SNECKO, FAIRY, GHOST, 
                JAR, BOLT, CARD, MOON, SPIKY, EYE, ANVIL
            });
        }

        private PotionSize(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect effect, Color liquidColor, Color hybridColor, 
            Color spotsColor)
    {
        slot = -1;
        tips = new ArrayList();
        labOutlineColor = Settings.HALF_TRANSPARENT_BLACK_COLOR;
        this.effect = new ArrayList();
        scale = Settings.scale;
        isObtained = false;
        sparkleTimer = 0.0F;
        flashCount = 0;
        flashTimer = 0.0F;
        this.hybridColor = null;
        this.spotsColor = null;
        potency = 0;
        hb = new Hitbox(64F * Settings.scale, 64F * Settings.scale);
        angle = 0.0F;
        canUse = false;
        discarded = false;
        isThrown = false;
        targetRequired = false;
        ID = id;
        this.name = name;
        this.rarity = rarity;
        color = null;
        this.liquidColor = liquidColor.cpy();
        this.hybridColor = hybridColor;
        this.spotsColor = spotsColor;
        p_effect = effect;
        this.size = size;
        initializeImage();
        initializeData();
    }

    public AbstractPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color)
    {
        slot = -1;
        tips = new ArrayList();
        labOutlineColor = Settings.HALF_TRANSPARENT_BLACK_COLOR;
        effect = new ArrayList();
        scale = Settings.scale;
        isObtained = false;
        sparkleTimer = 0.0F;
        flashCount = 0;
        flashTimer = 0.0F;
        hybridColor = null;
        spotsColor = null;
        potency = 0;
        hb = new Hitbox(64F * Settings.scale, 64F * Settings.scale);
        angle = 0.0F;
        canUse = false;
        discarded = false;
        isThrown = false;
        targetRequired = false;
        this.color = color;
        this.size = size;
        ID = id;
        this.name = name;
        this.rarity = rarity;
        p_effect = PotionEffect.NONE;
        initializeImage();
        initializeColor();
        initializeData();
    }

    private void initializeImage()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[];
            static final int $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[];
            static final int $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionRarity[];
            static final int $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionEffect[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionEffect = new int[PotionEffect.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionEffect[PotionEffect.NONE.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionEffect[PotionEffect.OSCILLATE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionEffect[PotionEffect.RAINBOW.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionRarity = new int[PotionRarity.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionRarity[PotionRarity.COMMON.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionRarity[PotionRarity.UNCOMMON.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionRarity[PotionRarity.RARE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionRarity[PotionRarity.PLACEHOLDER.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor = new int[PotionColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.BLUE.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.WHITE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.FAIRY.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.ENERGY.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.EXPLOSIVE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.FIRE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.GREEN.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror13) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.POISON.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror14) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.STRENGTH.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror15) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.STEROID.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror16) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.SWIFT.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror17) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.WEAK.ordinal()] = 12;
                }
                catch(NoSuchFieldError nosuchfielderror18) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.FEAR.ordinal()] = 13;
                }
                catch(NoSuchFieldError nosuchfielderror19) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.ELIXIR.ordinal()] = 14;
                }
                catch(NoSuchFieldError nosuchfielderror20) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.ANCIENT.ordinal()] = 15;
                }
                catch(NoSuchFieldError nosuchfielderror21) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.FRUIT.ordinal()] = 16;
                }
                catch(NoSuchFieldError nosuchfielderror22) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.SNECKO.ordinal()] = 17;
                }
                catch(NoSuchFieldError nosuchfielderror23) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.SMOKE.ordinal()] = 18;
                }
                catch(NoSuchFieldError nosuchfielderror24) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.ATTACK.ordinal()] = 19;
                }
                catch(NoSuchFieldError nosuchfielderror25) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.SKILL.ordinal()] = 20;
                }
                catch(NoSuchFieldError nosuchfielderror26) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionColor[PotionColor.POWER.ordinal()] = 21;
                }
                catch(NoSuchFieldError nosuchfielderror27) { }
                $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize = new int[PotionSize.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.T.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror28) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.S.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror29) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.M.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror30) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.SPHERE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror31) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.H.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror32) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.BOTTLE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror33) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.HEART.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror34) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.SNECKO.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror35) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.FAIRY.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror36) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.GHOST.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror37) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.JAR.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror38) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.BOLT.ordinal()] = 12;
                }
                catch(NoSuchFieldError nosuchfielderror39) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.CARD.ordinal()] = 13;
                }
                catch(NoSuchFieldError nosuchfielderror40) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.MOON.ordinal()] = 14;
                }
                catch(NoSuchFieldError nosuchfielderror41) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.SPIKY.ordinal()] = 15;
                }
                catch(NoSuchFieldError nosuchfielderror42) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.EYE.ordinal()] = 16;
                }
                catch(NoSuchFieldError nosuchfielderror43) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$potions$AbstractPotion$PotionSize[PotionSize.ANVIL.ordinal()] = 17;
                }
                catch(NoSuchFieldError nosuchfielderror44) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize[size.ordinal()])
        {
        case 1: // '\001'
            containerImg = ImageMaster.POTION_T_CONTAINER;
            liquidImg = ImageMaster.POTION_T_LIQUID;
            hybridImg = ImageMaster.POTION_T_HYBRID;
            spotsImg = ImageMaster.POTION_T_SPOTS;
            outlineImg = ImageMaster.POTION_T_OUTLINE;
            break;

        case 2: // '\002'
            containerImg = ImageMaster.POTION_S_CONTAINER;
            liquidImg = ImageMaster.POTION_S_LIQUID;
            hybridImg = ImageMaster.POTION_S_HYBRID;
            spotsImg = ImageMaster.POTION_S_SPOTS;
            outlineImg = ImageMaster.POTION_S_OUTLINE;
            break;

        case 3: // '\003'
            containerImg = ImageMaster.POTION_M_CONTAINER;
            liquidImg = ImageMaster.POTION_M_LIQUID;
            hybridImg = ImageMaster.POTION_M_HYBRID;
            spotsImg = ImageMaster.POTION_M_SPOTS;
            outlineImg = ImageMaster.POTION_M_OUTLINE;
            break;

        case 4: // '\004'
            containerImg = ImageMaster.POTION_SPHERE_CONTAINER;
            liquidImg = ImageMaster.POTION_SPHERE_LIQUID;
            hybridImg = ImageMaster.POTION_SPHERE_HYBRID;
            spotsImg = ImageMaster.POTION_SPHERE_SPOTS;
            outlineImg = ImageMaster.POTION_SPHERE_OUTLINE;
            break;

        case 5: // '\005'
            containerImg = ImageMaster.POTION_H_CONTAINER;
            liquidImg = ImageMaster.POTION_H_LIQUID;
            hybridImg = ImageMaster.POTION_H_HYBRID;
            spotsImg = ImageMaster.POTION_H_SPOTS;
            outlineImg = ImageMaster.POTION_H_OUTLINE;
            break;

        case 6: // '\006'
            containerImg = ImageMaster.POTION_BOTTLE_CONTAINER;
            liquidImg = ImageMaster.POTION_BOTTLE_LIQUID;
            hybridImg = ImageMaster.POTION_BOTTLE_HYBRID;
            spotsImg = ImageMaster.POTION_BOTTLE_SPOTS;
            outlineImg = ImageMaster.POTION_BOTTLE_OUTLINE;
            break;

        case 7: // '\007'
            containerImg = ImageMaster.POTION_HEART_CONTAINER;
            liquidImg = ImageMaster.POTION_HEART_LIQUID;
            hybridImg = ImageMaster.POTION_HEART_HYBRID;
            spotsImg = ImageMaster.POTION_HEART_SPOTS;
            outlineImg = ImageMaster.POTION_HEART_OUTLINE;
            break;

        case 8: // '\b'
            containerImg = ImageMaster.POTION_SNECKO_CONTAINER;
            liquidImg = ImageMaster.POTION_SNECKO_LIQUID;
            hybridImg = ImageMaster.POTION_SNECKO_HYBRID;
            spotsImg = ImageMaster.POTION_SNECKO_SPOTS;
            outlineImg = ImageMaster.POTION_SNECKO_OUTLINE;
            break;

        case 9: // '\t'
            containerImg = ImageMaster.POTION_FAIRY_CONTAINER;
            liquidImg = ImageMaster.POTION_FAIRY_LIQUID;
            hybridImg = ImageMaster.POTION_FAIRY_HYBRID;
            spotsImg = ImageMaster.POTION_FAIRY_SPOTS;
            outlineImg = ImageMaster.POTION_FAIRY_OUTLINE;
            break;

        case 10: // '\n'
            containerImg = ImageMaster.POTION_GHOST_CONTAINER;
            liquidImg = ImageMaster.POTION_GHOST_LIQUID;
            hybridImg = ImageMaster.POTION_GHOST_HYBRID;
            spotsImg = ImageMaster.POTION_GHOST_SPOTS;
            outlineImg = ImageMaster.POTION_GHOST_OUTLINE;
            break;

        case 11: // '\013'
            containerImg = ImageMaster.POTION_JAR_CONTAINER;
            liquidImg = ImageMaster.POTION_JAR_LIQUID;
            hybridImg = ImageMaster.POTION_JAR_HYBRID;
            spotsImg = ImageMaster.POTION_JAR_SPOTS;
            outlineImg = ImageMaster.POTION_JAR_OUTLINE;
            break;

        case 12: // '\f'
            containerImg = ImageMaster.POTION_BOLT_CONTAINER;
            liquidImg = ImageMaster.POTION_BOLT_LIQUID;
            hybridImg = ImageMaster.POTION_BOLT_HYBRID;
            spotsImg = ImageMaster.POTION_BOLT_SPOTS;
            outlineImg = ImageMaster.POTION_BOLT_OUTLINE;
            break;

        case 13: // '\r'
            containerImg = ImageMaster.POTION_CARD_CONTAINER;
            liquidImg = ImageMaster.POTION_CARD_LIQUID;
            hybridImg = ImageMaster.POTION_CARD_HYBRID;
            spotsImg = ImageMaster.POTION_CARD_SPOTS;
            outlineImg = ImageMaster.POTION_CARD_OUTLINE;
            break;

        case 14: // '\016'
            containerImg = ImageMaster.POTION_MOON_CONTAINER;
            liquidImg = ImageMaster.POTION_MOON_LIQUID;
            hybridImg = ImageMaster.POTION_MOON_HYBRID;
            outlineImg = ImageMaster.POTION_MOON_OUTLINE;
            break;

        case 15: // '\017'
            containerImg = ImageMaster.POTION_SPIKY_CONTAINER;
            liquidImg = ImageMaster.POTION_SPIKY_LIQUID;
            hybridImg = ImageMaster.POTION_SPIKY_HYBRID;
            outlineImg = ImageMaster.POTION_SPIKY_OUTLINE;
            break;

        case 16: // '\020'
            containerImg = ImageMaster.POTION_EYE_CONTAINER;
            liquidImg = ImageMaster.POTION_EYE_LIQUID;
            hybridImg = ImageMaster.POTION_EYE_HYBRID;
            outlineImg = ImageMaster.POTION_EYE_OUTLINE;
            break;

        case 17: // '\021'
            containerImg = ImageMaster.POTION_ANVIL_CONTAINER;
            liquidImg = ImageMaster.POTION_ANVIL_LIQUID;
            hybridImg = ImageMaster.POTION_ANVIL_HYBRID;
            outlineImg = ImageMaster.POTION_ANVIL_OUTLINE;
            break;

        default:
            containerImg = null;
            liquidImg = null;
            hybridImg = null;
            spotsImg = null;
            break;
        }
    }

    public void flash()
    {
        flashCount = 1;
    }

    private void initializeColor()
    {
        if(color == null)
            return;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.potions.AbstractPotion.PotionColor[color.ordinal()])
        {
        case 1: // '\001'
            liquidColor = Color.SKY.cpy();
            break;

        case 2: // '\002'
            liquidColor = Color.WHITE.cpy();
            hybridColor = Color.LIGHT_GRAY.cpy();
            break;

        case 3: // '\003'
            liquidColor = Color.CLEAR.cpy();
            spotsColor = Color.WHITE.cpy();
            break;

        case 4: // '\004'
            liquidColor = Color.GOLD.cpy();
            break;

        case 5: // '\005'
            liquidColor = Color.ORANGE.cpy();
            break;

        case 6: // '\006'
            liquidColor = Color.RED.cpy();
            hybridColor = Color.ORANGE.cpy();
            break;

        case 7: // '\007'
            liquidColor = Color.CHARTREUSE.cpy();
            break;

        case 8: // '\b'
            liquidColor = Color.LIME.cpy();
            spotsColor = Color.FOREST.cpy();
            break;

        case 9: // '\t'
            liquidColor = Color.DARK_GRAY.cpy();
            spotsColor = Color.CORAL.cpy();
            break;

        case 10: // '\n'
            liquidColor = Color.DARK_GRAY.cpy();
            hybridColor = Color.CORAL.cpy();
            break;

        case 11: // '\013'
            liquidColor = Color.valueOf("0d429dff");
            spotsColor = Color.CYAN.cpy();
            break;

        case 12: // '\f'
            liquidColor = Color.VIOLET.cpy();
            hybridColor = Color.MAROON.cpy();
            break;

        case 13: // '\r'
            liquidColor = Color.BLACK.cpy();
            hybridColor = Color.SCARLET.cpy();
            break;

        case 14: // '\016'
            liquidColor = Color.GOLD.cpy();
            spotsColor = Color.DARK_GRAY.cpy();
            break;

        case 15: // '\017'
            liquidColor = Color.GOLD.cpy();
            hybridColor = Color.CYAN.cpy();
            break;

        case 16: // '\020'
            liquidColor = Color.ORANGE.cpy();
            hybridColor = Color.LIME.cpy();
            break;

        case 17: // '\021'
            liquidColor = Settings.GREEN_TEXT_COLOR.cpy();
            hybridColor = Settings.GOLD_COLOR.cpy();
            break;

        case 18: // '\022'
            liquidColor = Color.GRAY.cpy();
            hybridColor = Color.DARK_GRAY.cpy();
            break;

        case 19: // '\023'
            liquidColor = Settings.RED_TEXT_COLOR.cpy();
            hybridColor = Color.FIREBRICK.cpy();
            break;

        case 20: // '\024'
            liquidColor = Color.FOREST.cpy();
            hybridColor = Color.CHARTREUSE.cpy();
            break;

        case 21: // '\025'
            liquidColor = Color.NAVY.cpy();
            hybridColor = Color.SKY.cpy();
            break;

        default:
            liquidColor = Color.RED.cpy();
            spotsColor = Color.RED.cpy();
            break;
        }
    }

    public void move(float setX, float setY)
    {
        posX = setX;
        posY = setY;
    }

    public void adjustPosition(int slot)
    {
        posX = TopPanel.potionX + (float)slot * Settings.POTION_W;
        posY = Settings.POTION_Y;
        hb.move(posX, posY);
    }

    public int getPrice()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity[rarity.ordinal()])
        {
        case 1: // '\001'
            return 50;

        case 2: // '\002'
            return 75;

        case 3: // '\003'
            return 100;
        }
        return 999;
    }

    public abstract void use(AbstractCreature abstractcreature);

    public boolean canDiscard()
    {
        return AbstractDungeon.getCurrRoom().event == null || !(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain);
    }

    public void initializeData()
    {
    }

    public boolean canUse()
    {
        if(AbstractDungeon.getCurrRoom().event != null && (AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain))
            return false;
        return AbstractDungeon.getCurrRoom().monsters != null && !AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead() && !AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT;
    }

    public void update()
    {
        if(isObtained)
        {
            hb.update();
            updateFlash();
        }
    }

    private void updateFlash()
    {
        if(flashCount != 0)
        {
            flashTimer -= Gdx.graphics.getDeltaTime();
            if(flashTimer < 0.0F)
            {
                flashTimer = 0.33F;
                flashCount--;
                effect.add(new FlashPotionEffect(this));
            }
        }
        Iterator i = effect.iterator();
        do
        {
            if(!i.hasNext())
                break;
            FlashPotionEffect e = (FlashPotionEffect)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
    }

    public void setAsObtained(int potionSlot)
    {
        slot = potionSlot;
        isObtained = true;
        adjustPosition(potionSlot);
    }

    public static void playPotionSound()
    {
        int tmp = MathUtils.random(2);
        if(tmp == 0)
            CardCrawlGame.sound.play("POTION_1");
        else
        if(tmp == 1)
            CardCrawlGame.sound.play("POTION_2");
        else
            CardCrawlGame.sound.play("POTION_3");
    }

    public void renderLightOutline(SpriteBatch sb)
    {
        if(!(this instanceof PotionSlot))
        {
            sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
            sb.draw(outlineImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        }
    }

    public void renderOutline(SpriteBatch sb)
    {
        if(!(this instanceof PotionSlot))
        {
            sb.setColor(Settings.HALF_TRANSPARENT_BLACK_COLOR);
            sb.draw(outlineImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        }
    }

    public void renderOutline(SpriteBatch sb, Color c)
    {
        if(!(this instanceof PotionSlot))
        {
            sb.setColor(c);
            sb.draw(outlineImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        }
    }

    public void renderShiny(SpriteBatch sb)
    {
        if(!(this instanceof PotionSlot))
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
            sb.draw(containerImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
            sb.setBlendFunction(770, 771);
        }
    }

    public void render(SpriteBatch sb)
    {
        updateFlash();
        updateEffect();
        if(this instanceof PotionSlot)
        {
            sb.setColor(PLACEHOLDER_COLOR);
            sb.draw(ImageMaster.POTION_PLACEHOLDER, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        } else
        {
            sb.setColor(liquidColor);
            sb.draw(liquidImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
            if(hybridColor != null)
            {
                sb.setColor(hybridColor);
                sb.draw(hybridImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
            }
            if(spotsColor != null)
            {
                sb.setColor(spotsColor);
                sb.draw(spotsImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
            }
            sb.setColor(Color.WHITE);
            sb.draw(containerImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        }
        FlashPotionEffect e;
        for(Iterator iterator = effect.iterator(); iterator.hasNext(); e.render(sb, posX, posY))
            e = (FlashPotionEffect)iterator.next();

        if(hb != null)
            hb.render(sb);
    }

    private void updateEffect()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.potions.AbstractPotion.PotionEffect[p_effect.ordinal()])
        {
        case 3: // '\003'
            liquidColor.r = (MathUtils.cosDeg((System.currentTimeMillis() / 10L) % 360L) + 1.25F) / 2.3F;
            liquidColor.g = (MathUtils.cosDeg(((System.currentTimeMillis() + 1000L) / 10L) % 360L) + 1.25F) / 2.3F;
            liquidColor.b = (MathUtils.cosDeg(((System.currentTimeMillis() + 2000L) / 10L) % 360L) + 1.25F) / 2.3F;
            liquidColor.a = 1.0F;
            break;
        }
    }

    public void shopRender(SpriteBatch sb)
    {
        generateSparkles(0.0F, 0.0F, false);
        updateFlash();
        updateEffect();
        if(hb.hovered)
        {
            TipHelper.queuePowerTips((float)InputHelper.mX + 50F * Settings.scale, (float)InputHelper.mY + 50F * Settings.scale, tips);
            scale = 1.5F * Settings.scale;
        } else
        {
            scale = MathHelper.scaleLerpSnap(scale, 1.2F * Settings.scale);
        }
        renderOutline(sb);
        sb.setColor(liquidColor);
        sb.draw(liquidImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        if(hybridColor != null)
        {
            sb.setColor(hybridColor);
            sb.draw(hybridImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        }
        if(spotsColor != null)
        {
            sb.setColor(spotsColor);
            sb.draw(spotsImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        }
        sb.setColor(Color.WHITE);
        sb.draw(containerImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        FlashPotionEffect e;
        for(Iterator iterator = effect.iterator(); iterator.hasNext(); e.render(sb, posX, posY))
            e = (FlashPotionEffect)iterator.next();

        if(hb != null)
            hb.render(sb);
    }

    public void labRender(SpriteBatch sb)
    {
        updateFlash();
        updateEffect();
        if(hb.hovered)
        {
            TipHelper.queuePowerTips(150F * Settings.scale, 800F * Settings.scale, tips);
            scale = 1.5F * Settings.scale;
        } else
        {
            scale = MathHelper.scaleLerpSnap(scale, 1.2F * Settings.scale);
        }
        renderOutline(sb, labOutlineColor);
        sb.setColor(liquidColor);
        sb.draw(liquidImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        if(hybridColor != null)
        {
            sb.setColor(hybridColor);
            sb.draw(hybridImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        }
        if(spotsColor != null)
        {
            sb.setColor(spotsColor);
            sb.draw(spotsImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        }
        sb.setColor(Color.WHITE);
        sb.draw(containerImg, posX - 32F, posY - 32F, 32F, 32F, 64F, 64F, scale, scale, angle, 0, 0, 64, 64, false, false);
        FlashPotionEffect e;
        for(Iterator iterator = effect.iterator(); iterator.hasNext(); e.render(sb, posX, posY))
            e = (FlashPotionEffect)iterator.next();

        if(hb != null)
            hb.render(sb);
    }

    public void generateSparkles(float x, float y, boolean usePositions)
    {
        if(Settings.DISABLE_EFFECTS)
            return;
        if(usePositions)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity[rarity.ordinal()])
            {
            case 3: // '\003'
                sparkleTimer -= Gdx.graphics.getDeltaTime();
                if(sparkleTimer < 0.0F)
                {
                    AbstractDungeon.topLevelEffects.add(new RarePotionParticleEffect(x, y));
                    sparkleTimer = MathUtils.random(0.35F, 0.5F);
                }
                break;

            case 2: // '\002'
                sparkleTimer -= Gdx.graphics.getDeltaTime();
                if(sparkleTimer < 0.0F)
                {
                    AbstractDungeon.topLevelEffects.add(new UncommonPotionParticleEffect(x, y));
                    sparkleTimer = MathUtils.random(0.25F, 0.3F);
                }
                break;
            }
        else
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity[rarity.ordinal()])
            {
            case 1: // '\001'
            case 4: // '\004'
            default:
                break;

            case 3: // '\003'
                sparkleTimer -= Gdx.graphics.getDeltaTime();
                if(sparkleTimer < 0.0F)
                {
                    AbstractDungeon.topLevelEffects.add(new RarePotionParticleEffect(hb));
                    sparkleTimer = MathUtils.random(0.35F, 0.5F);
                }
                break;

            case 2: // '\002'
                sparkleTimer -= Gdx.graphics.getDeltaTime();
                if(sparkleTimer < 0.0F)
                {
                    AbstractDungeon.topLevelEffects.add(new UncommonPotionParticleEffect(hb));
                    sparkleTimer = MathUtils.random(0.25F, 0.3F);
                }
                break;
            }
    }

    public abstract int getPotency(int i);

    public int getPotency()
    {
        int potency = getPotency(AbstractDungeon.ascensionLevel);
        if(AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark"))
            potency *= 2;
        return potency;
    }

    public boolean onPlayerDeath()
    {
        return false;
    }

    protected void addToBot(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action)
    {
        addToTop(action);
    }

    public static String gameDataUploadHeader()
    {
        GameDataStringBuilder sb = new GameDataStringBuilder();
        sb.addFieldData("name");
        sb.addFieldData("rarity");
        sb.addFieldData("color");
        sb.addFieldData("text");
        return sb.toString();
    }

    public String getUploadData(String color)
    {
        GameDataStringBuilder sb = new GameDataStringBuilder();
        sb.addFieldData(name);
        sb.addFieldData(rarity.toString());
        sb.addFieldData(color);
        String originalValue = String.valueOf(getPotency(0));
        String comboDesc = description;
        if(getPotency(0) != getPotency(15))
            comboDesc = description.replace(originalValue, String.format("%s(%s)", new Object[] {
                originalValue, Integer.valueOf(getPotency(15))
            }));
        sb.addFieldData(comboDesc);
        return sb.toString();
    }

    public abstract AbstractPotion makeCopy();

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public String ID;
    public String name;
    public String description;
    public int slot;
    public ArrayList tips;
    private Texture containerImg;
    private Texture liquidImg;
    private Texture hybridImg;
    private Texture spotsImg;
    private Texture outlineImg;
    public float posX;
    public float posY;
    private static final int RAW_W = 64;
    protected Color labOutlineColor;
    private ArrayList effect;
    public float scale;
    public boolean isObtained;
    private float sparkleTimer;
    private static final int FLASH_COUNT = 1;
    private static final float FLASH_INTERVAL = 0.33F;
    private int flashCount;
    private float flashTimer;
    public final PotionEffect p_effect;
    public final PotionColor color;
    public Color liquidColor;
    public Color hybridColor;
    public Color spotsColor;
    public PotionRarity rarity;
    public PotionSize size;
    protected int potency;
    public Hitbox hb;
    private float angle;
    protected boolean canUse;
    public boolean discarded;
    public boolean isThrown;
    public boolean targetRequired;
    private static final Color PLACEHOLDER_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.75F);

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("AbstractPotion");
        TEXT = uiStrings.TEXT;
    }
}
