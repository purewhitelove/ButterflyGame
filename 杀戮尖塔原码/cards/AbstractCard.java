// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractCard.java

package com.megacrit.cardcrawl.cards;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.curses.Pride;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import java.io.PrintStream;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.cards:
//            DescriptionLine, CardGroup, DamageInfo

public abstract class AbstractCard
    implements Comparable
{
    public static final class CardTags extends Enum
    {

        public static CardTags[] values()
        {
            return (CardTags[])$VALUES.clone();
        }

        public static CardTags valueOf(String name)
        {
            return (CardTags)Enum.valueOf(com/megacrit/cardcrawl/cards/AbstractCard$CardTags, name);
        }

        public static final CardTags HEALING;
        public static final CardTags STRIKE;
        public static final CardTags EMPTY;
        public static final CardTags STARTER_DEFEND;
        public static final CardTags STARTER_STRIKE;
        private static final CardTags $VALUES[];

        static 
        {
            HEALING = new CardTags("HEALING", 0);
            STRIKE = new CardTags("STRIKE", 1);
            EMPTY = new CardTags("EMPTY", 2);
            STARTER_DEFEND = new CardTags("STARTER_DEFEND", 3);
            STARTER_STRIKE = new CardTags("STARTER_STRIKE", 4);
            $VALUES = (new CardTags[] {
                HEALING, STRIKE, EMPTY, STARTER_DEFEND, STARTER_STRIKE
            });
        }

        private CardTags(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class CardType extends Enum
    {

        public static CardType[] values()
        {
            return (CardType[])$VALUES.clone();
        }

        public static CardType valueOf(String name)
        {
            return (CardType)Enum.valueOf(com/megacrit/cardcrawl/cards/AbstractCard$CardType, name);
        }

        public static final CardType ATTACK;
        public static final CardType SKILL;
        public static final CardType POWER;
        public static final CardType STATUS;
        public static final CardType CURSE;
        private static final CardType $VALUES[];

        static 
        {
            ATTACK = new CardType("ATTACK", 0);
            SKILL = new CardType("SKILL", 1);
            POWER = new CardType("POWER", 2);
            STATUS = new CardType("STATUS", 3);
            CURSE = new CardType("CURSE", 4);
            $VALUES = (new CardType[] {
                ATTACK, SKILL, POWER, STATUS, CURSE
            });
        }

        private CardType(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class CardRarity extends Enum
    {

        public static CardRarity[] values()
        {
            return (CardRarity[])$VALUES.clone();
        }

        public static CardRarity valueOf(String name)
        {
            return (CardRarity)Enum.valueOf(com/megacrit/cardcrawl/cards/AbstractCard$CardRarity, name);
        }

        public static final CardRarity BASIC;
        public static final CardRarity SPECIAL;
        public static final CardRarity COMMON;
        public static final CardRarity UNCOMMON;
        public static final CardRarity RARE;
        public static final CardRarity CURSE;
        private static final CardRarity $VALUES[];

        static 
        {
            BASIC = new CardRarity("BASIC", 0);
            SPECIAL = new CardRarity("SPECIAL", 1);
            COMMON = new CardRarity("COMMON", 2);
            UNCOMMON = new CardRarity("UNCOMMON", 3);
            RARE = new CardRarity("RARE", 4);
            CURSE = new CardRarity("CURSE", 5);
            $VALUES = (new CardRarity[] {
                BASIC, SPECIAL, COMMON, UNCOMMON, RARE, CURSE
            });
        }

        private CardRarity(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class CardColor extends Enum
    {

        public static CardColor[] values()
        {
            return (CardColor[])$VALUES.clone();
        }

        public static CardColor valueOf(String name)
        {
            return (CardColor)Enum.valueOf(com/megacrit/cardcrawl/cards/AbstractCard$CardColor, name);
        }

        public static final CardColor RED;
        public static final CardColor GREEN;
        public static final CardColor BLUE;
        public static final CardColor PURPLE;
        public static final CardColor COLORLESS;
        public static final CardColor CURSE;
        private static final CardColor $VALUES[];

        static 
        {
            RED = new CardColor("RED", 0);
            GREEN = new CardColor("GREEN", 1);
            BLUE = new CardColor("BLUE", 2);
            PURPLE = new CardColor("PURPLE", 3);
            COLORLESS = new CardColor("COLORLESS", 4);
            CURSE = new CardColor("CURSE", 5);
            $VALUES = (new CardColor[] {
                RED, GREEN, BLUE, PURPLE, COLORLESS, CURSE
            });
        }

        private CardColor(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class CardTarget extends Enum
    {

        public static CardTarget[] values()
        {
            return (CardTarget[])$VALUES.clone();
        }

        public static CardTarget valueOf(String name)
        {
            return (CardTarget)Enum.valueOf(com/megacrit/cardcrawl/cards/AbstractCard$CardTarget, name);
        }

        public static final CardTarget ENEMY;
        public static final CardTarget ALL_ENEMY;
        public static final CardTarget SELF;
        public static final CardTarget NONE;
        public static final CardTarget SELF_AND_ENEMY;
        public static final CardTarget ALL;
        private static final CardTarget $VALUES[];

        static 
        {
            ENEMY = new CardTarget("ENEMY", 0);
            ALL_ENEMY = new CardTarget("ALL_ENEMY", 1);
            SELF = new CardTarget("SELF", 2);
            NONE = new CardTarget("NONE", 3);
            SELF_AND_ENEMY = new CardTarget("SELF_AND_ENEMY", 4);
            ALL = new CardTarget("ALL", 5);
            $VALUES = (new CardTarget[] {
                ENEMY, ALL_ENEMY, SELF, NONE, SELF_AND_ENEMY, ALL
            });
        }

        private CardTarget(String s, int i)
        {
            super(s, i);
        }
    }


    public boolean isStarterStrike()
    {
        return hasTag(CardTags.STRIKE) && rarity.equals(CardRarity.BASIC);
    }

    public boolean isStarterDefend()
    {
        return hasTag(CardTags.STARTER_DEFEND) && rarity.equals(CardRarity.BASIC);
    }

    public AbstractCard(String id, String name, String imgUrl, int cost, String rawDescription, CardType type, CardColor color, 
            CardRarity rarity, CardTarget target)
    {
        this(id, name, imgUrl, cost, rawDescription, type, color, rarity, target, DamageInfo.DamageType.NORMAL);
    }

    public AbstractCard(String id, String name, String deprecatedJokeUrl, String imgUrl, int cost, String rawDescription, CardType type, 
            CardColor color, CardRarity rarity, CardTarget target)
    {
        this(id, name, imgUrl, cost, rawDescription, type, color, rarity, target, DamageInfo.DamageType.NORMAL);
    }

    public AbstractCard(String id, String name, String deprecatedJokeUrl, String imgUrl, int cost, String rawDescription, CardType type, 
            CardColor color, CardRarity rarity, CardTarget target, DamageInfo.DamageType dType)
    {
        this(id, name, imgUrl, cost, rawDescription, type, color, rarity, target, dType);
    }

    public AbstractCard(String id, String name, String imgUrl, int cost, String rawDescription, CardType type, CardColor color, 
            CardRarity rarity, CardTarget target, DamageInfo.DamageType dType)
    {
        chargeCost = -1;
        isCostModified = false;
        isCostModifiedForTurn = false;
        retain = false;
        selfRetain = false;
        dontTriggerOnUseCard = false;
        isInnate = false;
        isLocked = false;
        showEvokeValue = false;
        showEvokeOrbCount = 0;
        keywords = new ArrayList();
        isUsed = false;
        upgraded = false;
        timesUpgraded = 0;
        misc = 0;
        ignoreEnergyOnUse = false;
        isSeen = true;
        upgradedCost = false;
        upgradedDamage = false;
        upgradedBlock = false;
        upgradedMagicNumber = false;
        isSelected = false;
        exhaust = false;
        returnToHand = false;
        shuffleBackIntoDrawPile = false;
        isEthereal = false;
        tags = new ArrayList();
        isMultiDamage = false;
        baseDamage = -1;
        baseBlock = -1;
        baseMagicNumber = -1;
        baseHeal = -1;
        baseDraw = -1;
        baseDiscard = -1;
        damage = -1;
        block = -1;
        magicNumber = -1;
        heal = -1;
        draw = -1;
        discard = -1;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        this.target = CardTarget.ENEMY;
        purgeOnUse = false;
        exhaustOnUseOnce = false;
        exhaustOnFire = false;
        freeToPlayOnce = false;
        isInAutoplay = false;
        fadingOut = false;
        transparency = 1.0F;
        targetTransparency = 1.0F;
        goldColor = Settings.GOLD_COLOR.cpy();
        renderColor = Color.WHITE.cpy();
        textColor = Settings.CREAM_COLOR.cpy();
        typeColor = new Color(0.35F, 0.35F, 0.35F, 0.0F);
        targetAngle = 0.0F;
        angle = 0.0F;
        glowList = new ArrayList();
        glowTimer = 0.0F;
        isGlowing = false;
        portraitImg = null;
        useSmallTitleFont = false;
        drawScale = 0.7F;
        targetDrawScale = 0.7F;
        isFlipped = false;
        darken = false;
        darkTimer = 0.0F;
        hb = new Hitbox(IMG_WIDTH_S, IMG_HEIGHT_S);
        hoverTimer = 0.0F;
        renderTip = false;
        hovered = false;
        hoverDuration = 0.0F;
        cardsToPreview = null;
        newGlowTimer = 0.0F;
        description = new ArrayList();
        inBottleFlame = false;
        inBottleLightning = false;
        inBottleTornado = false;
        glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        originalName = name;
        this.name = name;
        cardID = id;
        assetUrl = imgUrl;
        portrait = cardAtlas.findRegion(imgUrl);
        jokePortrait = oldCardAtlas.findRegion(imgUrl);
        if(portrait == null)
            if(jokePortrait != null)
                portrait = jokePortrait;
            else
                portrait = cardAtlas.findRegion("status/beta");
        this.cost = cost;
        costForTurn = cost;
        this.rawDescription = rawDescription;
        this.type = type;
        this.color = color;
        this.rarity = rarity;
        this.target = target;
        damageType = dType;
        damageTypeForTurn = dType;
        createCardImage();
        if(name == null || rawDescription == null)
            logger.info("Card initialized incorrecty");
        initializeTitle();
        initializeDescription();
        updateTransparency();
        uuid = UUID.randomUUID();
    }

    public static void initialize()
    {
        long startTime = System.currentTimeMillis();
        cardAtlas = new TextureAtlas(Gdx.files.internal("cards/cards.atlas"));
        oldCardAtlas = new TextureAtlas(Gdx.files.internal("oldCards/cards.atlas"));
        orbAtlas = new TextureAtlas(Gdx.files.internal("orbs/orb.atlas"));
        orb_red = orbAtlas.findRegion("red");
        orb_green = orbAtlas.findRegion("green");
        orb_blue = orbAtlas.findRegion("blue");
        orb_purple = orbAtlas.findRegion("purple");
        orb_card = orbAtlas.findRegion("card");
        orb_potion = orbAtlas.findRegion("potion");
        orb_relic = orbAtlas.findRegion("relic");
        orb_special = orbAtlas.findRegion("special");
        logger.info((new StringBuilder()).append("Card Image load time: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
    }

    public static void initializeDynamicFrameWidths()
    {
        float d = 48F * Settings.scale;
        gl.setText(FontHelper.cardTypeFont, uiStrings.TEXT[0]);
        typeOffsetAttack = (gl.width - 48F * Settings.scale) / 2.0F;
        typeWidthAttack = (gl.width / d - 1.0F) * 2.0F + 1.0F;
        gl.setText(FontHelper.cardTypeFont, uiStrings.TEXT[1]);
        typeOffsetSkill = (gl.width - 48F * Settings.scale) / 2.0F;
        typeWidthSkill = (gl.width / d - 1.0F) * 2.0F + 1.0F;
        gl.setText(FontHelper.cardTypeFont, uiStrings.TEXT[2]);
        typeOffsetPower = (gl.width - 48F * Settings.scale) / 2.0F;
        typeWidthPower = (gl.width / d - 1.0F) * 2.0F + 1.0F;
        gl.setText(FontHelper.cardTypeFont, uiStrings.TEXT[3]);
        typeOffsetCurse = (gl.width - 48F * Settings.scale) / 2.0F;
        typeWidthCurse = (gl.width / d - 1.0F) * 2.0F + 1.0F;
        gl.setText(FontHelper.cardTypeFont, uiStrings.TEXT[7]);
        typeOffsetStatus = (gl.width - 48F * Settings.scale) / 2.0F;
        typeWidthStatus = (gl.width / d - 1.0F) * 2.0F + 1.0F;
    }

    protected void initializeTitle()
    {
        FontHelper.cardTitleFont.getData().setScale(1.0F);
        gl.setText(FontHelper.cardTitleFont, name, Color.WHITE, 0.0F, 1, false);
        if(cost > 0 || cost == -1)
        {
            if(gl.width > TITLE_BOX_WIDTH)
                useSmallTitleFont = true;
        } else
        if(gl.width > TITLE_BOX_WIDTH_NO_COST)
            useSmallTitleFont = true;
        gl.reset();
    }

    public void initializeDescription()
    {
        keywords.clear();
        if(Settings.lineBreakViaCharacter)
        {
            initializeDescriptionCN();
            return;
        }
        description.clear();
        int numLines = 1;
        sbuilder.setLength(0);
        float currentWidth = 0.0F;
        String as[] = rawDescription.split(" ");
        int i = as.length;
        for(int j = 0; j < i; j++)
        {
            String word = as[j];
            boolean isKeyword = false;
            sbuilder2.setLength(0);
            sbuilder2.append(" ");
            if(word.length() > 0 && word.charAt(word.length() - 1) != ']' && !Character.isLetterOrDigit(word.charAt(word.length() - 1)))
            {
                sbuilder2.insert(0, word.charAt(word.length() - 1));
                word = word.substring(0, word.length() - 1);
            }
            String keywordTmp = word.toLowerCase();
            keywordTmp = dedupeKeyword(keywordTmp);
            if(GameDictionary.keywords.containsKey(keywordTmp))
            {
                if(!keywords.contains(keywordTmp))
                    keywords.add(keywordTmp);
                gl.reset();
                gl.setText(FontHelper.cardDescFont_N, sbuilder2);
                float tmp = gl.width;
                gl.setText(FontHelper.cardDescFont_N, word);
                gl.width += tmp;
                isKeyword = true;
            } else
            if(!word.isEmpty() && word.charAt(0) == '[')
            {
                gl.reset();
                gl.setText(FontHelper.cardDescFont_N, sbuilder2);
                gl.width += CARD_ENERGY_IMG_WIDTH;
                static class _cls1
                {

                    static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[];
                    static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[];
                    static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[];

                    static 
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity = new int[CardRarity.values().length];
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[CardRarity.BASIC.ordinal()] = 1;
                        }
                        catch(NoSuchFieldError nosuchfielderror) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[CardRarity.CURSE.ordinal()] = 2;
                        }
                        catch(NoSuchFieldError nosuchfielderror1) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[CardRarity.SPECIAL.ordinal()] = 3;
                        }
                        catch(NoSuchFieldError nosuchfielderror2) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[CardRarity.COMMON.ordinal()] = 4;
                        }
                        catch(NoSuchFieldError nosuchfielderror3) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[CardRarity.UNCOMMON.ordinal()] = 5;
                        }
                        catch(NoSuchFieldError nosuchfielderror4) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[CardRarity.RARE.ordinal()] = 6;
                        }
                        catch(NoSuchFieldError nosuchfielderror5) { }
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType = new int[CardType.values().length];
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[CardType.ATTACK.ordinal()] = 1;
                        }
                        catch(NoSuchFieldError nosuchfielderror6) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[CardType.CURSE.ordinal()] = 2;
                        }
                        catch(NoSuchFieldError nosuchfielderror7) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[CardType.STATUS.ordinal()] = 3;
                        }
                        catch(NoSuchFieldError nosuchfielderror8) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[CardType.SKILL.ordinal()] = 4;
                        }
                        catch(NoSuchFieldError nosuchfielderror9) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[CardType.POWER.ordinal()] = 5;
                        }
                        catch(NoSuchFieldError nosuchfielderror10) { }
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor = new int[CardColor.values().length];
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[CardColor.RED.ordinal()] = 1;
                        }
                        catch(NoSuchFieldError nosuchfielderror11) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[CardColor.GREEN.ordinal()] = 2;
                        }
                        catch(NoSuchFieldError nosuchfielderror12) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[CardColor.BLUE.ordinal()] = 3;
                        }
                        catch(NoSuchFieldError nosuchfielderror13) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[CardColor.PURPLE.ordinal()] = 4;
                        }
                        catch(NoSuchFieldError nosuchfielderror14) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[CardColor.COLORLESS.ordinal()] = 5;
                        }
                        catch(NoSuchFieldError nosuchfielderror15) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[CardColor.CURSE.ordinal()] = 6;
                        }
                        catch(NoSuchFieldError nosuchfielderror16) { }
                    }
                }

                switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[color.ordinal()])
                {
                case 1: // '\001'
                    if(!keywords.contains("[R]"))
                        keywords.add("[R]");
                    break;

                case 2: // '\002'
                    if(!keywords.contains("[G]"))
                        keywords.add("[G]");
                    break;

                case 3: // '\003'
                    if(!keywords.contains("[B]"))
                        keywords.add("[B]");
                    break;

                case 4: // '\004'
                    if(!keywords.contains("[W]"))
                        keywords.add("[W]");
                    break;

                case 5: // '\005'
                    if(word.equals("[W]") && !keywords.contains("[W]"))
                        keywords.add("[W]");
                    break;

                default:
                    logger.info((new StringBuilder()).append("ERROR: Tried to display an invalid energy type: ").append(color.name()).toString());
                    break;
                }
            } else
            if(word.equals("!D") || word.equals("!B") || word.equals("!M"))
                gl.setText(FontHelper.cardDescFont_N, word);
            else
            if(word.equals("NL"))
            {
                gl.width = 0.0F;
                word = "";
                description.add(new DescriptionLine(sbuilder.toString().trim(), currentWidth));
                currentWidth = 0.0F;
                numLines++;
                sbuilder.setLength(0);
            } else
            {
                gl.setText(FontHelper.cardDescFont_N, (new StringBuilder()).append(word).append(sbuilder2).toString());
            }
            if(currentWidth + gl.width > DESC_BOX_WIDTH)
            {
                description.add(new DescriptionLine(sbuilder.toString().trim(), currentWidth));
                numLines++;
                sbuilder.setLength(0);
                currentWidth = gl.width;
            } else
            {
                currentWidth += gl.width;
            }
            if(isKeyword)
                sbuilder.append('*');
            sbuilder.append(word).append(sbuilder2);
        }

        if(!sbuilder.toString().trim().isEmpty())
            description.add(new DescriptionLine(sbuilder.toString().trim(), currentWidth));
        if(Settings.isDev && numLines > 5)
            logger.info((new StringBuilder()).append("WARNING: Card ").append(name).append(" has lots of text").toString());
    }

    public void initializeDescriptionCN()
    {
        int numLines;
        int removeLine;
        int i;
        description.clear();
        numLines = 1;
        sbuilder.setLength(0);
        float currentWidth = 0.0F;
        String as[] = rawDescription.split(" ");
        i = as.length;
        for(int j = 0; j < i; j++)
        {
            String word = as[j];
            word = word.trim();
            if(!Settings.manualLineBreak && !Settings.manualAndAutoLineBreak && word.contains("NL"))
                continue;
            if(word.equals("NL") && sbuilder.length() == 0 || word.isEmpty())
            {
                currentWidth = 0.0F;
                continue;
            }
            String keywordTmp = word.toLowerCase();
            keywordTmp = dedupeKeyword(keywordTmp);
            if(GameDictionary.keywords.containsKey(keywordTmp))
            {
                if(!keywords.contains(keywordTmp))
                    keywords.add(keywordTmp);
                gl.setText(FontHelper.cardDescFont_N, word);
                if(currentWidth + gl.width > CN_DESC_BOX_WIDTH)
                {
                    numLines++;
                    description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                    sbuilder.setLength(0);
                    currentWidth = gl.width;
                    sbuilder.append(" *").append(word).append(" ");
                } else
                {
                    sbuilder.append(" *").append(word).append(" ");
                    currentWidth += gl.width;
                }
                continue;
            }
            if(!word.isEmpty() && word.charAt(0) == '[')
            {
                switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[color.ordinal()])
                {
                case 1: // '\001'
                    if(!keywords.contains("[R]"))
                        keywords.add("[R]");
                    break;

                case 2: // '\002'
                    if(!keywords.contains("[G]"))
                        keywords.add("[G]");
                    break;

                case 3: // '\003'
                    if(!keywords.contains("[B]"))
                        keywords.add("[B]");
                    break;

                case 4: // '\004'
                    if(!keywords.contains("[W]"))
                        keywords.add("[W]");
                    break;

                case 5: // '\005'
                    if(!keywords.contains("[W]"))
                        keywords.add("[W]");
                    break;

                default:
                    logger.info((new StringBuilder()).append("ERROR: Tried to display an invalid energy type: ").append(color.name()).toString());
                    break;
                }
                if(currentWidth + CARD_ENERGY_IMG_WIDTH > CN_DESC_BOX_WIDTH)
                {
                    numLines++;
                    description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                    sbuilder.setLength(0);
                    currentWidth = CARD_ENERGY_IMG_WIDTH;
                    sbuilder.append(" ").append(word).append(" ");
                } else
                {
                    sbuilder.append(" ").append(word).append(" ");
                    currentWidth += CARD_ENERGY_IMG_WIDTH;
                }
                continue;
            }
            if(word.equals("!D!"))
            {
                if(currentWidth + MAGIC_NUM_W > CN_DESC_BOX_WIDTH)
                {
                    numLines++;
                    description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                    sbuilder.setLength(0);
                    currentWidth = MAGIC_NUM_W;
                    sbuilder.append(" D ");
                } else
                {
                    sbuilder.append(" D ");
                    currentWidth += MAGIC_NUM_W;
                }
                continue;
            }
            if(word.equals("!B!"))
            {
                if(currentWidth + MAGIC_NUM_W > CN_DESC_BOX_WIDTH)
                {
                    numLines++;
                    description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                    sbuilder.setLength(0);
                    currentWidth = MAGIC_NUM_W;
                    sbuilder.append(" ").append(word).append("! ");
                } else
                {
                    sbuilder.append(" ").append(word).append("! ");
                    currentWidth += MAGIC_NUM_W;
                }
                continue;
            }
            if(word.equals("!M!"))
            {
                if(currentWidth + MAGIC_NUM_W > CN_DESC_BOX_WIDTH)
                {
                    numLines++;
                    description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                    sbuilder.setLength(0);
                    currentWidth = MAGIC_NUM_W;
                    sbuilder.append(" ").append(word).append("! ");
                } else
                {
                    sbuilder.append(" ").append(word).append("! ");
                    currentWidth += MAGIC_NUM_W;
                }
                continue;
            }
            if((Settings.manualLineBreak || Settings.manualAndAutoLineBreak) && word.equals("NL") && sbuilder.length() != 0)
            {
                gl.width = 0.0F;
                word = "";
                description.add(new DescriptionLine(sbuilder.toString().trim(), currentWidth));
                currentWidth = 0.0F;
                numLines++;
                sbuilder.setLength(0);
                continue;
            }
            if(word.charAt(0) == '*')
            {
                gl.setText(FontHelper.cardDescFont_N, word.substring(1));
                if(currentWidth + gl.width > CN_DESC_BOX_WIDTH)
                {
                    numLines++;
                    description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                    sbuilder.setLength(0);
                    currentWidth = gl.width;
                    sbuilder.append(" ").append(word).append(" ");
                } else
                {
                    sbuilder.append(" ").append(word).append(" ");
                    currentWidth += gl.width;
                }
                continue;
            }
            word = word.trim();
            char ac[] = word.toCharArray();
            int k = ac.length;
            for(int l = 0; l < k; l++)
            {
                char c = ac[l];
                gl.setText(FontHelper.cardDescFont_N, String.valueOf(c));
                sbuilder.append(c);
                if(Settings.manualLineBreak)
                    continue;
                if(currentWidth + gl.width > CN_DESC_BOX_WIDTH)
                {
                    numLines++;
                    description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                    sbuilder.setLength(0);
                    currentWidth = gl.width;
                } else
                {
                    currentWidth += gl.width;
                }
            }

        }

        if(sbuilder.length() != 0)
            description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
        removeLine = -1;
        i = 0;
_L3:
        if(i >= description.size()) goto _L2; else goto _L1
_L1:
        if(!((DescriptionLine)description.get(i)).text.equals(LocalizedStrings.PERIOD))
            continue; /* Loop/switch isn't completed */
        new StringBuilder();
        (DescriptionLine)description.get(i - 1);
        JVM INSTR dup_x1 ;
        text;
        append();
        LocalizedStrings.PERIOD;
        append();
        toString();
        text;
        removeLine = i;
        i++;
          goto _L3
_L2:
        if(removeLine != -1)
            description.remove(removeLine);
        if(Settings.isDev && numLines > 5)
            logger.info((new StringBuilder()).append("WARNING: Card ").append(name).append(" has lots of text").toString());
        return;
    }

    public boolean hasTag(CardTags tagToCheck)
    {
        return tags.contains(tagToCheck);
    }

    public boolean canUpgrade()
    {
        if(type == CardType.CURSE)
            return false;
        if(type == CardType.STATUS)
            return false;
        return !upgraded;
    }

    public abstract void upgrade();

    public void displayUpgrades()
    {
        if(upgradedCost)
            isCostModified = true;
        if(upgradedDamage)
        {
            damage = baseDamage;
            isDamageModified = true;
        }
        if(upgradedBlock)
        {
            block = baseBlock;
            isBlockModified = true;
        }
        if(upgradedMagicNumber)
        {
            magicNumber = baseMagicNumber;
            isMagicNumberModified = true;
        }
    }

    protected void upgradeDamage(int amount)
    {
        baseDamage += amount;
        upgradedDamage = true;
    }

    protected void upgradeBlock(int amount)
    {
        baseBlock += amount;
        upgradedBlock = true;
    }

    protected void upgradeMagicNumber(int amount)
    {
        baseMagicNumber += amount;
        magicNumber = baseMagicNumber;
        upgradedMagicNumber = true;
    }

    protected void upgradeName()
    {
        timesUpgraded++;
        upgraded = true;
        name = (new StringBuilder()).append(name).append("+").toString();
        initializeTitle();
    }

    protected void upgradeBaseCost(int newBaseCost)
    {
        int diff = costForTurn - cost;
        cost = newBaseCost;
        if(costForTurn > 0)
            costForTurn = cost + diff;
        if(costForTurn < 0)
            costForTurn = 0;
        upgradedCost = true;
    }

    private String dedupeKeyword(String keyword)
    {
        String retVal = (String)GameDictionary.parentWord.get(keyword);
        if(retVal != null)
            return retVal;
        else
            return keyword;
    }

    public AbstractCard(AbstractCard c)
    {
        chargeCost = -1;
        isCostModified = false;
        isCostModifiedForTurn = false;
        retain = false;
        selfRetain = false;
        dontTriggerOnUseCard = false;
        isInnate = false;
        isLocked = false;
        showEvokeValue = false;
        showEvokeOrbCount = 0;
        keywords = new ArrayList();
        isUsed = false;
        upgraded = false;
        timesUpgraded = 0;
        misc = 0;
        ignoreEnergyOnUse = false;
        isSeen = true;
        upgradedCost = false;
        upgradedDamage = false;
        upgradedBlock = false;
        upgradedMagicNumber = false;
        isSelected = false;
        exhaust = false;
        returnToHand = false;
        shuffleBackIntoDrawPile = false;
        isEthereal = false;
        tags = new ArrayList();
        isMultiDamage = false;
        baseDamage = -1;
        baseBlock = -1;
        baseMagicNumber = -1;
        baseHeal = -1;
        baseDraw = -1;
        baseDiscard = -1;
        damage = -1;
        block = -1;
        magicNumber = -1;
        heal = -1;
        draw = -1;
        discard = -1;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        target = CardTarget.ENEMY;
        purgeOnUse = false;
        exhaustOnUseOnce = false;
        exhaustOnFire = false;
        freeToPlayOnce = false;
        isInAutoplay = false;
        fadingOut = false;
        transparency = 1.0F;
        targetTransparency = 1.0F;
        goldColor = Settings.GOLD_COLOR.cpy();
        renderColor = Color.WHITE.cpy();
        textColor = Settings.CREAM_COLOR.cpy();
        typeColor = new Color(0.35F, 0.35F, 0.35F, 0.0F);
        targetAngle = 0.0F;
        angle = 0.0F;
        glowList = new ArrayList();
        glowTimer = 0.0F;
        isGlowing = false;
        portraitImg = null;
        useSmallTitleFont = false;
        drawScale = 0.7F;
        targetDrawScale = 0.7F;
        isFlipped = false;
        darken = false;
        darkTimer = 0.0F;
        hb = new Hitbox(IMG_WIDTH_S, IMG_HEIGHT_S);
        hoverTimer = 0.0F;
        renderTip = false;
        hovered = false;
        hoverDuration = 0.0F;
        cardsToPreview = null;
        newGlowTimer = 0.0F;
        description = new ArrayList();
        inBottleFlame = false;
        inBottleLightning = false;
        inBottleTornado = false;
        glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        name = c.name;
        rawDescription = c.rawDescription;
        cost = c.cost;
        type = c.type;
    }

    private void createCardImage()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[color.ordinal()])
        {
        case 6: // '\006'
            bgColor = CURSE_BG_COLOR.cpy();
            backColor = CURSE_TYPE_BACK_COLOR.cpy();
            frameColor = CURSE_FRAME_COLOR.cpy();
            descBoxColor = CURSE_DESC_BOX_COLOR.cpy();
            break;

        case 5: // '\005'
            bgColor = COLORLESS_BG_COLOR.cpy();
            backColor = COLORLESS_TYPE_BACK_COLOR.cpy();
            frameColor = COLORLESS_FRAME_COLOR.cpy();
            frameOutlineColor = Color.WHITE.cpy();
            descBoxColor = COLORLESS_DESC_BOX_COLOR.cpy();
            break;

        case 1: // '\001'
            bgColor = RED_BG_COLOR.cpy();
            backColor = RED_TYPE_BACK_COLOR.cpy();
            frameColor = RED_FRAME_COLOR.cpy();
            frameOutlineColor = RED_RARE_OUTLINE_COLOR.cpy();
            descBoxColor = RED_DESC_BOX_COLOR.cpy();
            break;

        case 2: // '\002'
            bgColor = GREEN_BG_COLOR.cpy();
            backColor = GREEN_TYPE_BACK_COLOR.cpy();
            frameColor = GREEN_FRAME_COLOR.cpy();
            frameOutlineColor = GREEN_RARE_OUTLINE_COLOR.cpy();
            descBoxColor = GREEN_DESC_BOX_COLOR.cpy();
            break;

        case 3: // '\003'
            bgColor = BLUE_BG_COLOR.cpy();
            backColor = BLUE_TYPE_BACK_COLOR.cpy();
            frameColor = BLUE_FRAME_COLOR.cpy();
            frameOutlineColor = BLUE_RARE_OUTLINE_COLOR.cpy();
            descBoxColor = BLUE_DESC_BOX_COLOR.cpy();
            // fall through

        case 4: // '\004'
            bgColor = BLUE_BG_COLOR.cpy();
            backColor = BLUE_TYPE_BACK_COLOR.cpy();
            frameColor = BLUE_FRAME_COLOR.cpy();
            frameOutlineColor = BLUE_RARE_OUTLINE_COLOR.cpy();
            descBoxColor = BLUE_DESC_BOX_COLOR.cpy();
            break;

        default:
            logger.info((new StringBuilder()).append("ERROR: Card color was NOT set for ").append(name).toString());
            break;
        }
        if(rarity == CardRarity.COMMON || rarity == CardRarity.BASIC || rarity == CardRarity.CURSE)
        {
            bannerColor = BANNER_COLOR_COMMON.cpy();
            imgFrameColor = IMG_FRAME_COLOR_COMMON.cpy();
        } else
        if(rarity == CardRarity.UNCOMMON)
        {
            bannerColor = BANNER_COLOR_UNCOMMON.cpy();
            imgFrameColor = IMG_FRAME_COLOR_UNCOMMON.cpy();
        } else
        {
            bannerColor = BANNER_COLOR_RARE.cpy();
            imgFrameColor = IMG_FRAME_COLOR_RARE.cpy();
        }
        tintColor = CardHelper.getColor(43, 37, 65);
        tintColor.a = 0.0F;
        frameShadowColor = FRAME_SHADOW_COLOR.cpy();
    }

    public AbstractCard makeSameInstanceOf()
    {
        AbstractCard card = makeStatEquivalentCopy();
        card.uuid = uuid;
        return card;
    }

    public AbstractCard makeStatEquivalentCopy()
    {
        AbstractCard card = makeCopy();
        for(int i = 0; i < timesUpgraded; i++)
            card.upgrade();

        card.name = name;
        card.target = target;
        card.upgraded = upgraded;
        card.timesUpgraded = timesUpgraded;
        card.baseDamage = baseDamage;
        card.baseBlock = baseBlock;
        card.baseMagicNumber = baseMagicNumber;
        card.cost = cost;
        card.costForTurn = costForTurn;
        card.isCostModified = isCostModified;
        card.isCostModifiedForTurn = isCostModifiedForTurn;
        card.inBottleLightning = inBottleLightning;
        card.inBottleFlame = inBottleFlame;
        card.inBottleTornado = inBottleTornado;
        card.isSeen = isSeen;
        card.isLocked = isLocked;
        card.misc = misc;
        card.freeToPlayOnce = freeToPlayOnce;
        return card;
    }

    public void onRemoveFromMasterDeck()
    {
    }

    public boolean cardPlayable(AbstractMonster m)
    {
        if((target == CardTarget.ENEMY || target == CardTarget.SELF_AND_ENEMY) && m != null && m.isDying || AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            cantUseMessage = null;
            return false;
        } else
        {
            return true;
        }
    }

    public boolean hasEnoughEnergy()
    {
        if(AbstractDungeon.actionManager.turnHasEnded)
        {
            cantUseMessage = TEXT[9];
            return false;
        }
        for(Iterator iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext();)
        {
            AbstractPower p = (AbstractPower)iterator.next();
            if(!p.canPlayCard(this))
            {
                cantUseMessage = TEXT[13];
                return false;
            }
        }

        if(AbstractDungeon.player.hasPower("Entangled") && type == CardType.ATTACK)
        {
            cantUseMessage = TEXT[10];
            return false;
        }
        for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext();)
        {
            AbstractRelic r = (AbstractRelic)iterator1.next();
            if(!r.canPlay(this))
                return false;
        }

        for(Iterator iterator2 = AbstractDungeon.player.blights.iterator(); iterator2.hasNext();)
        {
            AbstractBlight b = (AbstractBlight)iterator2.next();
            if(!b.canPlay(this))
                return false;
        }

        for(Iterator iterator3 = AbstractDungeon.player.hand.group.iterator(); iterator3.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator3.next();
            if(!c.canPlay(this))
                return false;
        }

        if(EnergyPanel.totalCount >= costForTurn || freeToPlay() || isInAutoplay)
        {
            return true;
        } else
        {
            cantUseMessage = TEXT[11];
            return false;
        }
    }

    public void tookDamage()
    {
    }

    public void didDiscard()
    {
    }

    public void switchedStance()
    {
    }

    /**
     * @deprecated Method useBlueCandle is deprecated
     */

    protected void useBlueCandle(AbstractPlayer abstractplayer)
    {
    }

    /**
     * @deprecated Method useMedicalKit is deprecated
     */

    protected void useMedicalKit(AbstractPlayer abstractplayer)
    {
    }

    public boolean canPlay(AbstractCard card)
    {
        return true;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        if(type == CardType.STATUS && costForTurn < -1 && !AbstractDungeon.player.hasRelic("Medical Kit"))
            return false;
        if(type == CardType.CURSE && costForTurn < -1 && !AbstractDungeon.player.hasRelic("Blue Candle"))
            return false;
        return cardPlayable(m) && hasEnoughEnergy();
    }

    public abstract void use(AbstractPlayer abstractplayer, AbstractMonster abstractmonster);

    public void update()
    {
        updateFlashVfx();
        if(hoverTimer != 0.0F)
        {
            hoverTimer -= Gdx.graphics.getDeltaTime();
            if(hoverTimer < 0.0F)
                hoverTimer = 0.0F;
        }
        if(AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard && this == AbstractDungeon.player.hoveredCard)
        {
            current_x = MathHelper.cardLerpSnap(current_x, target_x);
            current_y = MathHelper.cardLerpSnap(current_y, target_y);
            if(AbstractDungeon.player.hasRelic("Necronomicon"))
                if(cost >= 2 && type == CardType.ATTACK && AbstractDungeon.player.getRelic("Necronomicon").checkTrigger())
                    AbstractDungeon.player.getRelic("Necronomicon").beginLongPulse();
                else
                    AbstractDungeon.player.getRelic("Necronomicon").stopPulse();
        }
        if(Settings.FAST_MODE)
        {
            current_x = MathHelper.cardLerpSnap(current_x, target_x);
            current_y = MathHelper.cardLerpSnap(current_y, target_y);
        }
        current_x = MathHelper.cardLerpSnap(current_x, target_x);
        current_y = MathHelper.cardLerpSnap(current_y, target_y);
        hb.move(current_x, current_y);
        hb.resize(HB_W * drawScale, HB_H * drawScale);
        if(hb.clickStarted && hb.hovered)
        {
            drawScale = MathHelper.cardScaleLerpSnap(drawScale, targetDrawScale * 0.9F);
            drawScale = MathHelper.cardScaleLerpSnap(drawScale, targetDrawScale * 0.9F);
        } else
        {
            drawScale = MathHelper.cardScaleLerpSnap(drawScale, targetDrawScale);
        }
        if(angle != targetAngle)
            angle = MathHelper.angleLerpSnap(angle, targetAngle);
        updateTransparency();
        updateColor();
    }

    private void updateFlashVfx()
    {
        if(flashVfx != null)
        {
            flashVfx.update();
            if(flashVfx.isDone)
                flashVfx = null;
        }
    }

    private void updateGlow()
    {
        if(isGlowing)
        {
            glowTimer -= Gdx.graphics.getDeltaTime();
            if(glowTimer < 0.0F)
            {
                glowList.add(new CardGlowBorder(this, glowColor));
                glowTimer = 0.3F;
            }
        }
        Iterator i = glowList.iterator();
        do
        {
            if(!i.hasNext())
                break;
            CardGlowBorder e = (CardGlowBorder)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
    }

    public boolean isHoveredInHand(float scale)
    {
        if(hoverTimer > 0.0F)
        {
            return false;
        } else
        {
            int x = InputHelper.mX;
            int y = InputHelper.mY;
            return (float)x > current_x - (IMG_WIDTH * scale) / 2.0F && (float)x < current_x + (IMG_WIDTH * scale) / 2.0F && (float)y > current_y - (IMG_HEIGHT * scale) / 2.0F && (float)y < current_y + (IMG_HEIGHT * scale) / 2.0F;
        }
    }

    private boolean isOnScreen()
    {
        return current_y >= -200F * Settings.scale && current_y <= (float)Settings.HEIGHT + 200F * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        if(!Settings.hideCards)
            render(sb, false);
    }

    public void renderHoverShadow(SpriteBatch sb)
    {
        if(!Settings.hideCards)
            renderHelper(sb, Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR, ImageMaster.CARD_SUPER_SHADOW, current_x, current_y, 1.15F);
    }

    public void renderInLibrary(SpriteBatch sb)
    {
        if(!isOnScreen())
            return;
        if(SingleCardViewPopup.isViewingUpgrade && isSeen && !isLocked)
        {
            AbstractCard copy = makeCopy();
            copy.current_x = current_x;
            copy.current_y = current_y;
            copy.drawScale = drawScale;
            copy.upgrade();
            copy.displayUpgrades();
            copy.render(sb);
        } else
        {
            updateGlow();
            renderGlow(sb);
            renderImage(sb, hovered, false);
            renderType(sb);
            renderTitle(sb);
            if(Settings.lineBreakViaCharacter)
                renderDescriptionCN(sb);
            else
                renderDescription(sb);
            renderTint(sb);
            renderEnergy(sb);
            hb.render(sb);
        }
    }

    public void render(SpriteBatch sb, boolean selected)
    {
        if(!Settings.hideCards)
        {
            if(flashVfx != null)
                flashVfx.render(sb);
            renderCard(sb, hovered, selected);
            hb.render(sb);
        }
    }

    public void renderUpgradePreview(SpriteBatch sb)
    {
        upgraded = true;
        name = (new StringBuilder()).append(originalName).append("+").toString();
        initializeTitle();
        renderCard(sb, hovered, false);
        name = originalName;
        initializeTitle();
        upgraded = false;
        resetAttributes();
    }

    public void renderWithSelections(SpriteBatch sb)
    {
        renderCard(sb, false, true);
    }

    private void renderCard(SpriteBatch sb, boolean hovered, boolean selected)
    {
        if(!Settings.hideCards)
        {
            if(!isOnScreen())
                return;
            if(!isFlipped)
            {
                updateGlow();
                renderGlow(sb);
                renderImage(sb, hovered, selected);
                renderTitle(sb);
                renderType(sb);
                if(Settings.lineBreakViaCharacter)
                    renderDescriptionCN(sb);
                else
                    renderDescription(sb);
                renderTint(sb);
                renderEnergy(sb);
            } else
            {
                renderBack(sb, hovered, selected);
                hb.render(sb);
            }
        }
    }

    private void renderTint(SpriteBatch sb)
    {
        if(!Settings.hideCards)
        {
            com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion cardBgImg = getCardBgAtlas();
            if(cardBgImg != null)
                renderHelper(sb, tintColor, cardBgImg, current_x, current_y);
            else
                renderHelper(sb, tintColor, getCardBg(), current_x - 256F, current_y - 256F);
        }
    }

    public void renderOuterGlow(SpriteBatch sb)
    {
        if(AbstractDungeon.player == null || !Settings.hideCards)
        {
            return;
        } else
        {
            renderHelper(sb, AbstractDungeon.player.getCardRenderColor(), getCardBgAtlas(), current_x, current_y, 1.0F + tintColor.a / 5F);
            return;
        }
    }

    public Texture getCardBg()
    {
        int i = 10;
        if(i < 5)
            System.out.println("Add special logic here");
        return null;
    }

    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getCardBgAtlas()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[type.ordinal()])
        {
        case 1: // '\001'
            return ImageMaster.CARD_ATTACK_BG_SILHOUETTE;

        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
            return ImageMaster.CARD_SKILL_BG_SILHOUETTE;

        case 5: // '\005'
            return ImageMaster.CARD_POWER_BG_SILHOUETTE;
        }
        return null;
    }

    private void renderGlow(SpriteBatch sb)
    {
        if(!Settings.hideCards)
        {
            renderMainBorder(sb);
            AbstractGameEffect e;
            for(Iterator iterator = glowList.iterator(); iterator.hasNext(); e.render(sb))
                e = (AbstractGameEffect)iterator.next();

            sb.setBlendFunction(770, 771);
        }
    }

    public void beginGlowing()
    {
        isGlowing = true;
    }

    public void stopGlowing()
    {
        isGlowing = false;
        for(Iterator iterator = glowList.iterator(); iterator.hasNext();)
        {
            CardGlowBorder e = (CardGlowBorder)iterator.next();
            e.duration /= 5F;
        }

    }

    private void renderMainBorder(SpriteBatch sb)
    {
        if(isGlowing)
        {
            sb.setBlendFunction(770, 1);
            com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[type.ordinal()])
            {
            case 5: // '\005'
                img = ImageMaster.CARD_POWER_BG_SILHOUETTE;
                break;

            case 1: // '\001'
                img = ImageMaster.CARD_ATTACK_BG_SILHOUETTE;
                break;

            default:
                img = ImageMaster.CARD_SKILL_BG_SILHOUETTE;
                break;
            }
            if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
                sb.setColor(glowColor);
            else
                sb.setColor(GREEN_BORDER_GLOW_COLOR);
            sb.draw(img, (current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (current_y + img.offsetY) - (float)img.originalWidth / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalWidth / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, drawScale * Settings.scale * 1.04F, drawScale * Settings.scale * 1.03F, angle);
        }
    }

    private void renderHelper(SpriteBatch sb, Color color, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img, float drawX, float drawY)
    {
        sb.setColor(color);
        sb.draw(img, (drawX + img.offsetX) - (float)img.originalWidth / 2.0F, (drawY + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, drawScale * Settings.scale, drawScale * Settings.scale, angle);
    }

    private void renderHelper(SpriteBatch sb, Color color, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img, float drawX, float drawY, float scale)
    {
        sb.setColor(color);
        sb.draw(img, (drawX + img.offsetX) - (float)img.originalWidth / 2.0F, (drawY + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, drawScale * Settings.scale * scale, drawScale * Settings.scale * scale, angle);
    }

    private void renderHelper(SpriteBatch sb, Color color, Texture img, float drawX, float drawY)
    {
        sb.setColor(color);
        sb.draw(img, drawX + 256F, drawY + 256F, 256F, 256F, 512F, 512F, drawScale * Settings.scale, drawScale * Settings.scale, angle, 0, 0, 512, 512, false, false);
    }

    private void renderHelper(SpriteBatch sb, Color color, Texture img, float drawX, float drawY, float scale)
    {
        sb.setColor(color);
        sb.draw(img, drawX, drawY, 256F, 256F, 512F, 512F, drawScale * Settings.scale * scale, drawScale * Settings.scale * scale, angle, 0, 0, 512, 512, false, false);
    }

    public void renderSmallEnergy(SpriteBatch sb, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region, float x, float y)
    {
        sb.setColor(renderColor);
        sb.draw(region.getTexture(), current_x + x * Settings.scale * drawScale + region.offsetX * Settings.scale, current_y + y * Settings.scale * drawScale + region.offsetY * Settings.scale, 0.0F, 0.0F, region.packedWidth, region.packedHeight, drawScale * Settings.scale, drawScale * Settings.scale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

    private void renderImage(SpriteBatch sb, boolean hovered, boolean selected)
    {
        if(AbstractDungeon.player != null)
        {
            if(selected)
                renderHelper(sb, Color.SKY, getCardBgAtlas(), current_x, current_y, 1.03F);
            renderHelper(sb, frameShadowColor, getCardBgAtlas(), current_x + SHADOW_OFFSET_X * drawScale, current_y - SHADOW_OFFSET_Y * drawScale);
            if(AbstractDungeon.player.hoveredCard == this && (AbstractDungeon.player.isDraggingCard && AbstractDungeon.player.isHoveringDropZone || AbstractDungeon.player.inSingleTargetMode))
                renderHelper(sb, HOVER_IMG_COLOR, getCardBgAtlas(), current_x, current_y);
            else
            if(selected)
                renderHelper(sb, SELECTED_CARD_COLOR, getCardBgAtlas(), current_x, current_y);
        }
        renderCardBg(sb, current_x, current_y);
        if(UnlockTracker.betaCardPref.getBoolean(cardID, false) || Settings.PLAYTESTER_ART_MODE)
            renderJokePortrait(sb);
        else
            renderPortrait(sb);
        renderPortraitFrame(sb, current_x, current_y);
        renderBannerImage(sb, current_x, current_y);
    }

    private void renderCardBg(SpriteBatch sb, float x, float y)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[type.ordinal()])
        {
        case 1: // '\001'
            renderAttackBg(sb, x, y);
            break;

        case 4: // '\004'
            renderSkillBg(sb, x, y);
            break;

        case 5: // '\005'
            renderPowerBg(sb, x, y);
            break;

        case 2: // '\002'
            renderSkillBg(sb, x, y);
            break;

        case 3: // '\003'
            renderSkillBg(sb, x, y);
            break;
        }
    }

    private void renderAttackBg(SpriteBatch sb, float x, float y)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[color.ordinal()])
        {
        case 1: // '\001'
            renderHelper(sb, renderColor, ImageMaster.CARD_ATTACK_BG_RED, x, y);
            break;

        case 2: // '\002'
            renderHelper(sb, renderColor, ImageMaster.CARD_ATTACK_BG_GREEN, x, y);
            break;

        case 3: // '\003'
            renderHelper(sb, renderColor, ImageMaster.CARD_ATTACK_BG_BLUE, x, y);
            break;

        case 4: // '\004'
            renderHelper(sb, renderColor, ImageMaster.CARD_ATTACK_BG_PURPLE, x, y);
            break;

        case 6: // '\006'
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_BLACK, x, y);
            break;

        case 5: // '\005'
            renderHelper(sb, renderColor, ImageMaster.CARD_ATTACK_BG_GRAY, x, y);
            break;

        default:
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_BLACK, x, y);
            break;
        }
    }

    private void renderSkillBg(SpriteBatch sb, float x, float y)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[color.ordinal()])
        {
        case 1: // '\001'
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_RED, x, y);
            break;

        case 2: // '\002'
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_GREEN, x, y);
            break;

        case 3: // '\003'
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_BLUE, x, y);
            break;

        case 4: // '\004'
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_PURPLE, x, y);
            break;

        case 6: // '\006'
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_BLACK, x, y);
            break;

        case 5: // '\005'
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_GRAY, x, y);
            break;

        default:
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_BLACK, x, y);
            break;
        }
    }

    private void renderPowerBg(SpriteBatch sb, float x, float y)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[color.ordinal()])
        {
        case 1: // '\001'
            renderHelper(sb, renderColor, ImageMaster.CARD_POWER_BG_RED, x, y);
            break;

        case 2: // '\002'
            renderHelper(sb, renderColor, ImageMaster.CARD_POWER_BG_GREEN, x, y);
            break;

        case 3: // '\003'
            renderHelper(sb, renderColor, ImageMaster.CARD_POWER_BG_BLUE, x, y);
            break;

        case 4: // '\004'
            renderHelper(sb, renderColor, ImageMaster.CARD_POWER_BG_PURPLE, x, y);
            break;

        case 6: // '\006'
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_BLACK, x, y);
            break;

        case 5: // '\005'
            renderHelper(sb, renderColor, ImageMaster.CARD_POWER_BG_GRAY, x, y);
            break;

        default:
            renderHelper(sb, renderColor, ImageMaster.CARD_SKILL_BG_BLACK, x, y);
            break;
        }
    }

    private void renderPortraitFrame(SpriteBatch sb, float x, float y)
    {
        float tWidth = 0.0F;
        float tOffset = 0.0F;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[type.ordinal()])
        {
        case 1: // '\001'
            renderAttackPortrait(sb, x, y);
            tWidth = typeWidthAttack;
            tOffset = typeOffsetAttack;
            break;

        case 2: // '\002'
            renderSkillPortrait(sb, x, y);
            tWidth = typeWidthCurse;
            tOffset = typeOffsetCurse;
            break;

        case 3: // '\003'
            renderSkillPortrait(sb, x, y);
            tWidth = typeWidthStatus;
            tOffset = typeOffsetStatus;
            break;

        case 4: // '\004'
            renderSkillPortrait(sb, x, y);
            tWidth = typeWidthSkill;
            tOffset = typeOffsetSkill;
            break;

        case 5: // '\005'
            renderPowerPortrait(sb, x, y);
            tWidth = typeWidthPower;
            tOffset = typeOffsetPower;
            break;
        }
        renderDynamicFrame(sb, x, y, tOffset, tWidth);
    }

    private void renderAttackPortrait(SpriteBatch sb, float x, float y)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_ATTACK_COMMON, x, y);
            return;

        case 5: // '\005'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_ATTACK_UNCOMMON, x, y);
            return;

        case 6: // '\006'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_ATTACK_RARE, x, y);
            return;
        }
    }

    private void renderDynamicFrame(SpriteBatch sb, float x, float y, float typeOffset, float typeWidth)
    {
        if(typeWidth <= 1.1F)
            return;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_COMMON_FRAME_MID, x, y, 0.0F, typeWidth);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_COMMON_FRAME_LEFT, x, y, -typeOffset, 1.0F);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_COMMON_FRAME_RIGHT, x, y, typeOffset, 1.0F);
            break;

        case 5: // '\005'
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_UNCOMMON_FRAME_MID, x, y, 0.0F, typeWidth);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_UNCOMMON_FRAME_LEFT, x, y, -typeOffset, 1.0F);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_UNCOMMON_FRAME_RIGHT, x, y, typeOffset, 1.0F);
            break;

        case 6: // '\006'
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_RARE_FRAME_MID, x, y, 0.0F, typeWidth);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_RARE_FRAME_LEFT, x, y, -typeOffset, 1.0F);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_RARE_FRAME_RIGHT, x, y, typeOffset, 1.0F);
            break;
        }
    }

    private void dynamicFrameRenderHelper(SpriteBatch sb, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img, float x, float y, float xOffset, float xScale)
    {
        sb.draw(img, ((x + img.offsetX) - (float)img.originalWidth / 2.0F) + xOffset * drawScale, (y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, drawScale * Settings.scale * xScale, drawScale * Settings.scale, angle);
    }

    private void dynamicFrameRenderHelper(SpriteBatch sb, Texture img, float x, float y, float xOffset, float xScale)
    {
        sb.draw(img, x + xOffset * drawScale, y, 256F, 256F, 512F, 512F, drawScale * Settings.scale * xScale, drawScale * Settings.scale, angle, 0, 0, 512, 512, false, false);
    }

    private void renderSkillPortrait(SpriteBatch sb, float x, float y)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 1: // '\001'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_SKILL_COMMON, x, y);
            return;

        case 4: // '\004'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_SKILL_COMMON, x, y);
            return;

        case 5: // '\005'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_SKILL_UNCOMMON, x, y);
            return;

        case 6: // '\006'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_SKILL_RARE, x, y);
            return;

        case 2: // '\002'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_SKILL_COMMON, x, y);
            return;

        case 3: // '\003'
        default:
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_SKILL_COMMON, x, y);
            return;
        }
    }

    private void renderPowerPortrait(SpriteBatch sb, float x, float y)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_POWER_COMMON, x, y);
            break;

        case 5: // '\005'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_POWER_UNCOMMON, x, y);
            break;

        case 6: // '\006'
            renderHelper(sb, renderColor, ImageMaster.CARD_FRAME_POWER_RARE, x, y);
            break;
        }
    }

    private void renderBannerImage(SpriteBatch sb, float drawX, float drawY)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 1: // '\001'
            renderHelper(sb, renderColor, ImageMaster.CARD_BANNER_COMMON, drawX, drawY);
            return;

        case 4: // '\004'
            renderHelper(sb, renderColor, ImageMaster.CARD_BANNER_COMMON, drawX, drawY);
            return;

        case 5: // '\005'
            renderHelper(sb, renderColor, ImageMaster.CARD_BANNER_UNCOMMON, drawX, drawY);
            return;

        case 6: // '\006'
            renderHelper(sb, renderColor, ImageMaster.CARD_BANNER_RARE, drawX, drawY);
            return;

        case 2: // '\002'
            renderHelper(sb, renderColor, ImageMaster.CARD_BANNER_COMMON, drawX, drawY);
            return;

        case 3: // '\003'
        default:
            renderHelper(sb, renderColor, ImageMaster.CARD_BANNER_COMMON, drawX, drawY);
            return;
        }
    }

    private void renderBack(SpriteBatch sb, boolean hovered, boolean selected)
    {
        renderHelper(sb, renderColor, ImageMaster.CARD_BACK, current_x, current_y);
    }

    private void renderPortrait(SpriteBatch sb)
    {
        float drawX = current_x - 125F;
        float drawY = current_y - 95F;
        Texture img = null;
        if(portraitImg != null)
            img = portraitImg;
        if(!isLocked)
        {
            if(portrait != null)
            {
                drawX = current_x - (float)portrait.packedWidth / 2.0F;
                drawY = current_y - (float)portrait.packedHeight / 2.0F;
                sb.setColor(renderColor);
                sb.draw(portrait, drawX, drawY + 72F, (float)portrait.packedWidth / 2.0F, (float)portrait.packedHeight / 2.0F - 72F, portrait.packedWidth, portrait.packedHeight, drawScale * Settings.scale, drawScale * Settings.scale, angle);
            } else
            if(img != null)
            {
                sb.setColor(renderColor);
                sb.draw(img, drawX, drawY + 72F, 125F, 23F, 250F, 190F, drawScale * Settings.scale, drawScale * Settings.scale, angle, 0, 0, 250, 190, false, false);
            }
        } else
        {
            sb.draw(portraitImg, drawX, drawY + 72F, 125F, 23F, 250F, 190F, drawScale * Settings.scale, drawScale * Settings.scale, angle, 0, 0, 250, 190, false, false);
        }
    }

    private void renderJokePortrait(SpriteBatch sb)
    {
        float drawX = current_x - 125F;
        float drawY = current_y - 95F;
        Texture img = null;
        if(portraitImg != null)
            img = portraitImg;
        if(!isLocked)
        {
            if(jokePortrait != null)
            {
                drawX = current_x - (float)portrait.packedWidth / 2.0F;
                drawY = current_y - (float)portrait.packedHeight / 2.0F;
                sb.setColor(renderColor);
                sb.draw(jokePortrait, drawX, drawY + 72F, (float)jokePortrait.packedWidth / 2.0F, (float)jokePortrait.packedHeight / 2.0F - 72F, jokePortrait.packedWidth, jokePortrait.packedHeight, drawScale * Settings.scale, drawScale * Settings.scale, angle);
            } else
            if(img != null)
            {
                sb.setColor(renderColor);
                sb.draw(img, drawX, drawY + 72F, 125F, 23F, 250F, 190F, drawScale * Settings.scale, drawScale * Settings.scale, angle, 0, 0, 250, 190, false, false);
            }
        } else
        {
            sb.draw(portraitImg, drawX, drawY + 72F, 125F, 23F, 250F, 190F, drawScale * Settings.scale, drawScale * Settings.scale, angle, 0, 0, 250, 190, false, false);
        }
    }

    private void renderDescription(SpriteBatch sb)
    {
        if(!isSeen || isLocked)
        {
            FontHelper.menuBannerFont.getData().setScale(drawScale * 1.25F);
            FontHelper.renderRotatedText(sb, FontHelper.menuBannerFont, "? ? ?", current_x, current_y, 0.0F, (-200F * Settings.scale * drawScale) / 2.0F, angle, true, textColor);
            FontHelper.menuBannerFont.getData().setScale(1.0F);
            return;
        }
        BitmapFont font = getDescFont();
        float draw_y = (current_y - (IMG_HEIGHT * drawScale) / 2.0F) + DESC_OFFSET_Y * drawScale;
        draw_y += (float)description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
        float spacing = (1.45F * -font.getCapHeight()) / Settings.scale / drawScale;
        for(int i = 0; i < description.size(); i++)
        {
            float start_x = current_x - (((DescriptionLine)description.get(i)).width * drawScale) / 2.0F;
            String as[] = ((DescriptionLine)description.get(i)).getCachedTokenizedText();
            int j = as.length;
            for(int k = 0; k < j; k++)
            {
                String tmp = as[k];
                if(tmp.length() > 0 && tmp.charAt(0) == '*')
                {
                    tmp = tmp.substring(1);
                    String punctuation = "";
                    if(tmp.length() > 1 && tmp.charAt(tmp.length() - 2) != '+' && !Character.isLetter(tmp.charAt(tmp.length() - 2)))
                    {
                        punctuation = (new StringBuilder()).append(punctuation).append(tmp.charAt(tmp.length() - 2)).toString();
                        tmp = tmp.substring(0, tmp.length() - 2);
                        punctuation = (new StringBuilder()).append(punctuation).append(' ').toString();
                    }
                    gl.setText(font, tmp);
                    FontHelper.renderRotatedText(sb, font, tmp, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, goldColor);
                    start_x = Math.round(start_x + gl.width);
                    gl.setText(font, punctuation);
                    FontHelper.renderRotatedText(sb, font, punctuation, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, textColor);
                    gl.setText(font, punctuation);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.length() > 0 && tmp.charAt(0) == '!')
                {
                    if(tmp.length() == 4)
                    {
                        start_x += renderDynamicVariable(tmp.charAt(1), start_x, draw_y, i, font, sb, null);
                        continue;
                    }
                    if(tmp.length() == 5)
                        start_x += renderDynamicVariable(tmp.charAt(1), start_x, draw_y, i, font, sb, Character.valueOf(tmp.charAt(3)));
                    continue;
                }
                if(tmp.equals("[R] "))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_red, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[R]. "))
                {
                    gl.width = (CARD_ENERGY_IMG_WIDTH * drawScale) / Settings.scale;
                    renderSmallEnergy(sb, orb_red, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    FontHelper.renderRotatedText(sb, font, LocalizedStrings.PERIOD, current_x, current_y, (start_x - current_x) + CARD_ENERGY_IMG_WIDTH * drawScale, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, textColor);
                    start_x += gl.width;
                    gl.setText(font, LocalizedStrings.PERIOD);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[G] "))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_green, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[G]. "))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_green, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    FontHelper.renderRotatedText(sb, font, LocalizedStrings.PERIOD, current_x, current_y, (start_x - current_x) + CARD_ENERGY_IMG_WIDTH * drawScale, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, textColor);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[B] "))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_blue, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[B]. "))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_blue, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    FontHelper.renderRotatedText(sb, font, LocalizedStrings.PERIOD, current_x, current_y, (start_x - current_x) + CARD_ENERGY_IMG_WIDTH * drawScale, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, textColor);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[W] "))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_purple, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[W]. "))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_purple, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    FontHelper.renderRotatedText(sb, font, LocalizedStrings.PERIOD, current_x, current_y, (start_x - current_x) + CARD_ENERGY_IMG_WIDTH * drawScale, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, textColor);
                    start_x += gl.width;
                } else
                {
                    gl.setText(font, tmp);
                    FontHelper.renderRotatedText(sb, font, tmp, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, textColor);
                    start_x += gl.width;
                }
            }

        }

        font.getData().setScale(1.0F);
    }

    private String getDynamicValue(char key)
    {
        switch(key)
        {
        case 66: // 'B'
            if(isBlockModified)
            {
                if(block >= baseBlock)
                    return (new StringBuilder()).append("[#7fff00]").append(Integer.toString(block)).append("[]").toString();
                else
                    return (new StringBuilder()).append("[#ff6563]").append(Integer.toString(block)).append("[]").toString();
            } else
            {
                return Integer.toString(baseBlock);
            }

        case 68: // 'D'
            if(isDamageModified)
            {
                if(damage >= baseDamage)
                    return (new StringBuilder()).append("[#7fff00]").append(Integer.toString(damage)).append("[]").toString();
                else
                    return (new StringBuilder()).append("[#ff6563]").append(Integer.toString(damage)).append("[]").toString();
            } else
            {
                return Integer.toString(baseDamage);
            }

        case 77: // 'M'
            if(isMagicNumberModified)
            {
                if(magicNumber >= baseMagicNumber)
                    return (new StringBuilder()).append("[#7fff00]").append(Integer.toString(magicNumber)).append("[]").toString();
                else
                    return (new StringBuilder()).append("[#ff6563]").append(Integer.toString(magicNumber)).append("[]").toString();
            } else
            {
                return Integer.toString(baseMagicNumber);
            }
        }
        logger.info((new StringBuilder()).append("KEY: ").append(key).toString());
        return Integer.toString(-99);
    }

    private void renderDescriptionCN(SpriteBatch sb)
    {
        if(!isSeen || isLocked)
        {
            FontHelper.menuBannerFont.getData().setScale(drawScale * 1.25F);
            FontHelper.renderRotatedText(sb, FontHelper.menuBannerFont, "? ? ?", current_x, current_y, 0.0F, (-200F * Settings.scale * drawScale) / 2.0F, angle, true, textColor);
            FontHelper.menuBannerFont.getData().setScale(1.0F);
            return;
        }
        BitmapFont font = getDescFont();
        float draw_y = (current_y - (IMG_HEIGHT * drawScale) / 2.0F) + DESC_OFFSET_Y * drawScale;
        draw_y += (float)description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
        float spacing = (1.45F * -font.getCapHeight()) / Settings.scale / drawScale;
        for(int i = 0; i < description.size(); i++)
        {
            float start_x = 0.0F;
            if(Settings.leftAlignCards)
                start_x = (current_x - (DESC_BOX_WIDTH * drawScale) / 2.0F) + 2.0F * Settings.scale;
            else
                start_x = current_x - (((DescriptionLine)description.get(i)).width * drawScale) / 2.0F - 14F * Settings.scale;
            String as[] = ((DescriptionLine)description.get(i)).getCachedTokenizedTextCN();
            int k = as.length;
            for(int l = 0; l < k; l++)
            {
                String tmp = as[l];
                String updateTmp = null;
                int j = 0;
                do
                {
                    if(j >= tmp.length())
                        break;
                    if(tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M')
                    {
                        updateTmp = tmp.substring(0, j);
                        updateTmp = (new StringBuilder()).append(updateTmp).append(getDynamicValue(tmp.charAt(j))).toString();
                        updateTmp = (new StringBuilder()).append(updateTmp).append(tmp.substring(j + 1)).toString();
                        break;
                    }
                    j++;
                } while(true);
                if(updateTmp != null)
                    tmp = updateTmp;
                j = 0;
                do
                {
                    if(j >= tmp.length())
                        break;
                    if(tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M')
                    {
                        updateTmp = tmp.substring(0, j);
                        updateTmp = (new StringBuilder()).append(updateTmp).append(getDynamicValue(tmp.charAt(j))).toString();
                        updateTmp = (new StringBuilder()).append(updateTmp).append(tmp.substring(j + 1)).toString();
                        break;
                    }
                    j++;
                } while(true);
                if(updateTmp != null)
                    tmp = updateTmp;
                if(tmp.length() > 0 && tmp.charAt(0) == '*')
                {
                    tmp = tmp.substring(1);
                    String punctuation = "";
                    if(tmp.length() > 1 && tmp.charAt(tmp.length() - 2) != '+' && !Character.isLetter(tmp.charAt(tmp.length() - 2)))
                    {
                        punctuation = (new StringBuilder()).append(punctuation).append(tmp.charAt(tmp.length() - 2)).toString();
                        tmp = tmp.substring(0, tmp.length() - 2);
                        punctuation = (new StringBuilder()).append(punctuation).append(' ').toString();
                    }
                    gl.setText(font, tmp);
                    FontHelper.renderRotatedText(sb, font, tmp, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, goldColor);
                    start_x = Math.round(start_x + gl.width);
                    gl.setText(font, punctuation);
                    FontHelper.renderRotatedText(sb, font, punctuation, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, textColor);
                    gl.setText(font, punctuation);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[R]"))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_red, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[G]"))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_green, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[B]"))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_blue, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[W]"))
                {
                    gl.width = CARD_ENERGY_IMG_WIDTH * drawScale;
                    renderSmallEnergy(sb, orb_purple, (start_x - current_x) / Settings.scale / drawScale, -100F - ((((float)description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                } else
                {
                    gl.setText(font, tmp);
                    FontHelper.renderRotatedText(sb, font, tmp, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, textColor);
                    start_x += gl.width;
                }
            }

        }

        font.getData().setScale(1.0F);
    }

    private float renderDynamicVariable(char key, float start_x, float draw_y, int i, BitmapFont font, SpriteBatch sb, Character end)
    {
        sbuilder.setLength(0);
        Color c = null;
        int num = 0;
        switch(key)
        {
        default:
            break;

        case 68: // 'D'
            if(isDamageModified)
            {
                num = damage;
                if(damage >= baseDamage)
                    c = Settings.GREEN_TEXT_COLOR;
                else
                    c = Settings.RED_TEXT_COLOR;
            } else
            {
                c = textColor;
                num = baseDamage;
            }
            break;

        case 66: // 'B'
            if(isBlockModified)
            {
                num = block;
                if(block >= baseBlock)
                    c = Settings.GREEN_TEXT_COLOR;
                else
                    c = Settings.RED_TEXT_COLOR;
            } else
            {
                c = textColor;
                num = baseBlock;
            }
            break;

        case 77: // 'M'
            if(isMagicNumberModified)
            {
                num = magicNumber;
                if(magicNumber >= baseMagicNumber)
                    c = Settings.GREEN_TEXT_COLOR;
                else
                    c = Settings.RED_TEXT_COLOR;
            } else
            {
                c = textColor;
                num = baseMagicNumber;
            }
            break;
        }
        sbuilder.append(Integer.toString(num));
        gl.setText(font, sbuilder);
        FontHelper.renderRotatedText(sb, font, sbuilder.toString(), current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, angle, true, c);
        if(end != null)
        {
            FontHelper.renderRotatedText(sb, font, Character.toString(end.charValue()), current_x, current_y, (start_x - current_x) + gl.width + 4F * Settings.scale, (((float)i * 1.45F * -font.getCapHeight() + draw_y) - current_y) + -6F, 0.0F, true, Settings.CREAM_COLOR);
            sbuilder.append(end);
        }
        sbuilder.append(' ');
        gl.setText(font, sbuilder);
        return gl.width;
    }

    private BitmapFont getDescFont()
    {
        BitmapFont font = null;
        if(angle == 0.0F && drawScale == 1.0F)
            font = FontHelper.cardDescFont_N;
        else
            font = FontHelper.cardDescFont_L;
        font.getData().setScale(drawScale);
        return font;
    }

    private void renderTitle(SpriteBatch sb)
    {
        if(isLocked)
        {
            FontHelper.cardTitleFont.getData().setScale(drawScale);
            FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, LOCKED_STRING, current_x, current_y, 0.0F, 175F * drawScale * Settings.scale, angle, false, renderColor);
            return;
        }
        if(!isSeen)
        {
            FontHelper.cardTitleFont.getData().setScale(drawScale);
            FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, UNKNOWN_STRING, current_x, current_y, 0.0F, 175F * drawScale * Settings.scale, angle, false, renderColor);
            return;
        }
        if(!useSmallTitleFont)
            FontHelper.cardTitleFont.getData().setScale(drawScale);
        else
            FontHelper.cardTitleFont.getData().setScale(drawScale * 0.85F);
        if(upgraded)
        {
            Color color = Settings.GREEN_TEXT_COLOR.cpy();
            color.a = renderColor.a;
            FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, name, current_x, current_y, 0.0F, 175F * drawScale * Settings.scale, angle, false, color);
        } else
        {
            FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, name, current_x, current_y, 0.0F, 175F * drawScale * Settings.scale, angle, false, renderColor);
        }
    }

    private void renderType(SpriteBatch sb)
    {
        String text;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[type.ordinal()])
        {
        case 1: // '\001'
            text = TEXT[0];
            break;

        case 4: // '\004'
            text = TEXT[1];
            break;

        case 5: // '\005'
            text = TEXT[2];
            break;

        case 3: // '\003'
            text = TEXT[7];
            break;

        case 2: // '\002'
            text = TEXT[3];
            break;

        default:
            text = TEXT[5];
            break;
        }
        BitmapFont font = FontHelper.cardTypeFont;
        font.getData().setScale(drawScale);
        typeColor.a = renderColor.a;
        FontHelper.renderRotatedText(sb, font, text, current_x, current_y - 22F * drawScale * Settings.scale, 0.0F, -1F * drawScale * Settings.scale, angle, false, typeColor);
    }

    public static int getPrice(CardRarity rarity)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 1: // '\001'
            logger.info("ERROR: WHY WE SELLIN' BASIC");
            return 9999;

        case 4: // '\004'
            return 50;

        case 5: // '\005'
            return 75;

        case 6: // '\006'
            return 150;

        case 3: // '\003'
            logger.info("ERROR: WHY WE SELLIN' SPECIAL");
            return 9999;

        case 2: // '\002'
        default:
            logger.info("No rarity on this card?");
            return 0;
        }
    }

    private void renderEnergy(SpriteBatch sb)
    {
        if(cost <= -2 || darken || isLocked || !isSeen)
            return;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[color.ordinal()])
        {
        case 1: // '\001'
            renderHelper(sb, renderColor, ImageMaster.CARD_RED_ORB, current_x, current_y);
            break;

        case 2: // '\002'
            renderHelper(sb, renderColor, ImageMaster.CARD_GREEN_ORB, current_x, current_y);
            break;

        case 3: // '\003'
            renderHelper(sb, renderColor, ImageMaster.CARD_BLUE_ORB, current_x, current_y);
            break;

        case 4: // '\004'
            renderHelper(sb, renderColor, ImageMaster.CARD_PURPLE_ORB, current_x, current_y);
            break;

        case 5: // '\005'
            renderHelper(sb, renderColor, ImageMaster.CARD_COLORLESS_ORB, current_x, current_y);
            // fall through

        default:
            renderHelper(sb, renderColor, ImageMaster.CARD_COLORLESS_ORB, current_x, current_y);
            break;
        }
        Color costColor = Color.WHITE.cpy();
        if(AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this) && !hasEnoughEnergy())
            costColor = ENERGY_COST_RESTRICTED_COLOR;
        else
        if(isCostModified || isCostModifiedForTurn || freeToPlay())
            costColor = ENERGY_COST_MODIFIED_COLOR;
        costColor.a = transparency;
        String text = getCost();
        BitmapFont font = getEnergyFont();
        if((type != CardType.STATUS || cardID.equals("Slimed")) && (color != CardColor.CURSE || cardID.equals("Pride")))
            FontHelper.renderRotatedText(sb, font, text, current_x, current_y, -132F * drawScale * Settings.scale, 192F * drawScale * Settings.scale, angle, false, costColor);
    }

    public void updateCost(int amt)
    {
        if((color != CardColor.CURSE || cardID.equals("Pride")) && (type != CardType.STATUS || cardID.equals("Slimed")))
        {
            int tmpCost = cost;
            int diff = cost - costForTurn;
            tmpCost += amt;
            if(tmpCost < 0)
                tmpCost = 0;
            if(tmpCost != cost)
            {
                isCostModified = true;
                cost = tmpCost;
                costForTurn = cost - diff;
                if(costForTurn < 0)
                    costForTurn = 0;
            }
        } else
        {
            logger.info("Curses/Statuses cannot have their costs modified");
        }
    }

    public void setCostForTurn(int amt)
    {
        if(costForTurn >= 0)
        {
            costForTurn = amt;
            if(costForTurn < 0)
                costForTurn = 0;
            if(costForTurn != cost)
                isCostModifiedForTurn = true;
        }
    }

    public void modifyCostForCombat(int amt)
    {
        if(costForTurn > 0)
        {
            costForTurn = costForTurn + amt;
            if(costForTurn < 0)
                costForTurn = 0;
            if(cost != costForTurn)
                isCostModified = true;
            cost = costForTurn;
        } else
        if(cost >= 0)
        {
            cost = cost + amt;
            if(cost < 0)
                cost = 0;
            costForTurn = 0;
            if(cost != costForTurn)
                isCostModified = true;
        }
    }

    public void resetAttributes()
    {
        block = baseBlock;
        isBlockModified = false;
        damage = baseDamage;
        isDamageModified = false;
        magicNumber = baseMagicNumber;
        isMagicNumberModified = false;
        damageTypeForTurn = damageType;
        costForTurn = cost;
        isCostModifiedForTurn = false;
    }

    private String getCost()
    {
        if(cost == -1)
            return "X";
        if(freeToPlay())
            return "0";
        else
            return Integer.toString(costForTurn);
    }

    public boolean freeToPlay()
    {
        if(freeToPlayOnce)
            return true;
        return AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.hasPower("FreeAttackPower") && type == CardType.ATTACK;
    }

    private BitmapFont getEnergyFont()
    {
        FontHelper.cardEnergyFont_L.getData().setScale(drawScale);
        return FontHelper.cardEnergyFont_L;
    }

    public void hover()
    {
        if(!hovered)
        {
            hovered = true;
            drawScale = 1.0F;
            targetDrawScale = 1.0F;
        }
    }

    public void unhover()
    {
        if(hovered)
        {
            hovered = false;
            hoverDuration = 0.0F;
            renderTip = false;
            targetDrawScale = 0.75F;
        }
    }

    public void updateHoverLogic()
    {
        hb.update();
        if(hb.hovered)
        {
            hover();
            hoverDuration += Gdx.graphics.getDeltaTime();
            if(hoverDuration > 0.2F && !Settings.hideCards)
                renderTip = true;
        } else
        {
            unhover();
        }
    }

    public void untip()
    {
        hoverDuration = 0.0F;
        renderTip = false;
    }

    public void moveToDiscardPile()
    {
        target_x = CardGroup.DISCARD_PILE_X;
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            target_y = 0.0F;
        else
            target_y = 0.0F - OverlayMenu.HAND_HIDE_Y;
    }

    public void teleportToDiscardPile()
    {
        current_x = CardGroup.DISCARD_PILE_X;
        target_x = current_x;
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            current_y = 0.0F;
        else
            current_y = 0.0F - OverlayMenu.HAND_HIDE_Y;
        target_y = current_y;
        onMoveToDiscard();
    }

    public void onMoveToDiscard()
    {
    }

    public void renderCardTip(SpriteBatch sb)
    {
        if(!Settings.hideCards && renderTip)
        {
            if(AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard && !Settings.isTouchScreen)
                return;
            if(isLocked)
            {
                ArrayList locked = new ArrayList();
                locked.add(0, "locked");
                TipHelper.renderTipForCard(this, sb, locked);
                return;
            }
            if(!isSeen)
            {
                ArrayList unseen = new ArrayList();
                unseen.add(0, "unseen");
                TipHelper.renderTipForCard(this, sb, unseen);
                return;
            }
            if(SingleCardViewPopup.isViewingUpgrade && isSeen && !isLocked)
            {
                AbstractCard copy = makeCopy();
                copy.current_x = current_x;
                copy.current_y = current_y;
                copy.drawScale = drawScale;
                copy.upgrade();
                TipHelper.renderTipForCard(copy, sb, copy.keywords);
            } else
            {
                TipHelper.renderTipForCard(this, sb, keywords);
            }
            if(cardsToPreview != null)
                renderCardPreview(sb);
        }
    }

    public void renderCardPreviewInSingleView(SpriteBatch sb)
    {
        cardsToPreview.current_x = 1435F * Settings.scale;
        cardsToPreview.current_y = 795F * Settings.scale;
        cardsToPreview.drawScale = 0.8F;
        cardsToPreview.render(sb);
    }

    public void renderCardPreview(SpriteBatch sb)
    {
        if(AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard)
            return;
        float tmpScale = drawScale * 0.8F;
        if(current_x > (float)Settings.WIDTH * 0.75F)
            cardsToPreview.current_x = current_x + (IMG_WIDTH / 2.0F + (IMG_WIDTH / 2.0F) * 0.8F + 16F) * drawScale;
        else
            cardsToPreview.current_x = current_x - (IMG_WIDTH / 2.0F + (IMG_WIDTH / 2.0F) * 0.8F + 16F) * drawScale;
        cardsToPreview.current_y = current_y + (IMG_HEIGHT / 2.0F - (IMG_HEIGHT / 2.0F) * 0.8F) * drawScale;
        cardsToPreview.drawScale = tmpScale;
        cardsToPreview.render(sb);
    }

    public void triggerWhenDrawn()
    {
    }

    public void triggerWhenCopied()
    {
    }

    public void triggerOnEndOfPlayerTurn()
    {
        if(isEthereal)
            addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    public void triggerOnEndOfTurnForPlayingCard()
    {
    }

    public void triggerOnOtherCardPlayed(AbstractCard abstractcard)
    {
    }

    public void triggerOnGainEnergy(int i, boolean flag)
    {
    }

    public void triggerOnManualDiscard()
    {
    }

    public void triggerOnCardPlayed(AbstractCard abstractcard)
    {
    }

    public void triggerOnScry()
    {
    }

    public void triggerExhaustedCardsOnStanceChange(AbstractStance abstractstance)
    {
    }

    public void triggerAtStartOfTurn()
    {
    }

    public void onPlayCard(AbstractCard abstractcard, AbstractMonster abstractmonster)
    {
    }

    public void atTurnStart()
    {
    }

    public void atTurnStartPreDraw()
    {
    }

    public void onChoseThisOption()
    {
    }

    public void onRetained()
    {
    }

    public void triggerOnExhaust()
    {
    }

    public void applyPowers()
    {
        applyPowersToBlock();
        AbstractPlayer player = AbstractDungeon.player;
        isDamageModified = false;
        if(!isMultiDamage)
        {
            float tmp = baseDamage;
            Iterator iterator = player.relics.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator.next();
                tmp = r.atDamageModify(tmp, this);
                if(baseDamage != (int)tmp)
                    isDamageModified = true;
            } while(true);
            for(Iterator iterator1 = player.powers.iterator(); iterator1.hasNext();)
            {
                AbstractPower p = (AbstractPower)iterator1.next();
                tmp = p.atDamageGive(tmp, damageTypeForTurn, this);
            }

            tmp = player.stance.atDamageGive(tmp, damageTypeForTurn, this);
            if(baseDamage != (int)tmp)
                isDamageModified = true;
            for(Iterator iterator2 = player.powers.iterator(); iterator2.hasNext();)
            {
                AbstractPower p = (AbstractPower)iterator2.next();
                tmp = p.atDamageFinalGive(tmp, damageTypeForTurn, this);
            }

            if(tmp < 0.0F)
                tmp = 0.0F;
            if(baseDamage != MathUtils.floor(tmp))
                isDamageModified = true;
            damage = MathUtils.floor(tmp);
        } else
        {
            ArrayList m = AbstractDungeon.getCurrRoom().monsters.monsters;
            float tmp[] = new float[m.size()];
            for(int i = 0; i < tmp.length; i++)
                tmp[i] = baseDamage;

            for(int i = 0; i < tmp.length; i++)
            {
                Iterator iterator3 = player.relics.iterator();
                do
                {
                    if(!iterator3.hasNext())
                        break;
                    AbstractRelic r = (AbstractRelic)iterator3.next();
                    tmp[i] = r.atDamageModify(tmp[i], this);
                    if(baseDamage != (int)tmp[i])
                        isDamageModified = true;
                } while(true);
                for(Iterator iterator4 = player.powers.iterator(); iterator4.hasNext();)
                {
                    AbstractPower p = (AbstractPower)iterator4.next();
                    tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn, this);
                }

                tmp[i] = player.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                if(baseDamage != (int)tmp[i])
                    isDamageModified = true;
            }

            for(int i = 0; i < tmp.length; i++)
            {
                for(Iterator iterator5 = player.powers.iterator(); iterator5.hasNext();)
                {
                    AbstractPower p = (AbstractPower)iterator5.next();
                    tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn, this);
                }

            }

            for(int i = 0; i < tmp.length; i++)
                if(tmp[i] < 0.0F)
                    tmp[i] = 0.0F;

            multiDamage = new int[tmp.length];
            for(int i = 0; i < tmp.length; i++)
            {
                if(baseDamage != (int)tmp[i])
                    isDamageModified = true;
                multiDamage[i] = MathUtils.floor(tmp[i]);
            }

            damage = multiDamage[0];
        }
    }

    protected void applyPowersToBlock()
    {
        isBlockModified = false;
        float tmp = baseBlock;
        for(Iterator iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext();)
        {
            AbstractPower p = (AbstractPower)iterator.next();
            tmp = p.modifyBlock(tmp, this);
        }

        for(Iterator iterator1 = AbstractDungeon.player.powers.iterator(); iterator1.hasNext();)
        {
            AbstractPower p = (AbstractPower)iterator1.next();
            tmp = p.modifyBlockLast(tmp);
        }

        if(baseBlock != MathUtils.floor(tmp))
            isBlockModified = true;
        if(tmp < 0.0F)
            tmp = 0.0F;
        block = MathUtils.floor(tmp);
    }

    public void calculateDamageDisplay(AbstractMonster mo)
    {
        calculateCardDamage(mo);
    }

    public void calculateCardDamage(AbstractMonster mo)
    {
        applyPowersToBlock();
        AbstractPlayer player = AbstractDungeon.player;
        isDamageModified = false;
        if(!isMultiDamage && mo != null)
        {
            float tmp = baseDamage;
            Iterator iterator = player.relics.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator.next();
                tmp = r.atDamageModify(tmp, this);
                if(baseDamage != (int)tmp)
                    isDamageModified = true;
            } while(true);
            for(Iterator iterator1 = player.powers.iterator(); iterator1.hasNext();)
            {
                AbstractPower p = (AbstractPower)iterator1.next();
                tmp = p.atDamageGive(tmp, damageTypeForTurn, this);
            }

            tmp = player.stance.atDamageGive(tmp, damageTypeForTurn, this);
            if(baseDamage != (int)tmp)
                isDamageModified = true;
            for(Iterator iterator2 = mo.powers.iterator(); iterator2.hasNext();)
            {
                AbstractPower p = (AbstractPower)iterator2.next();
                tmp = p.atDamageReceive(tmp, damageTypeForTurn, this);
            }

            for(Iterator iterator3 = player.powers.iterator(); iterator3.hasNext();)
            {
                AbstractPower p = (AbstractPower)iterator3.next();
                tmp = p.atDamageFinalGive(tmp, damageTypeForTurn, this);
            }

            for(Iterator iterator4 = mo.powers.iterator(); iterator4.hasNext();)
            {
                AbstractPower p = (AbstractPower)iterator4.next();
                tmp = p.atDamageFinalReceive(tmp, damageTypeForTurn, this);
            }

            if(tmp < 0.0F)
                tmp = 0.0F;
            if(baseDamage != MathUtils.floor(tmp))
                isDamageModified = true;
            damage = MathUtils.floor(tmp);
        } else
        {
            ArrayList m = AbstractDungeon.getCurrRoom().monsters.monsters;
            float tmp[] = new float[m.size()];
            for(int i = 0; i < tmp.length; i++)
                tmp[i] = baseDamage;

            for(int i = 0; i < tmp.length; i++)
            {
                Iterator iterator5 = player.relics.iterator();
                do
                {
                    if(!iterator5.hasNext())
                        break;
                    AbstractRelic r = (AbstractRelic)iterator5.next();
                    tmp[i] = r.atDamageModify(tmp[i], this);
                    if(baseDamage != (int)tmp[i])
                        isDamageModified = true;
                } while(true);
                for(Iterator iterator6 = player.powers.iterator(); iterator6.hasNext();)
                {
                    AbstractPower p = (AbstractPower)iterator6.next();
                    tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn, this);
                }

                tmp[i] = player.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                if(baseDamage != (int)tmp[i])
                    isDamageModified = true;
            }

label0:
            for(int i = 0; i < tmp.length; i++)
            {
                Iterator iterator7 = ((AbstractMonster)m.get(i)).powers.iterator();
                do
                {
                    if(!iterator7.hasNext())
                        continue label0;
                    AbstractPower p = (AbstractPower)iterator7.next();
                    if(!((AbstractMonster)m.get(i)).isDying && !((AbstractMonster)m.get(i)).isEscaping)
                        tmp[i] = p.atDamageReceive(tmp[i], damageTypeForTurn, this);
                } while(true);
            }

            for(int i = 0; i < tmp.length; i++)
            {
                for(Iterator iterator8 = player.powers.iterator(); iterator8.hasNext();)
                {
                    AbstractPower p = (AbstractPower)iterator8.next();
                    tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn, this);
                }

            }

label1:
            for(int i = 0; i < tmp.length; i++)
            {
                Iterator iterator9 = ((AbstractMonster)m.get(i)).powers.iterator();
                do
                {
                    if(!iterator9.hasNext())
                        continue label1;
                    AbstractPower p = (AbstractPower)iterator9.next();
                    if(!((AbstractMonster)m.get(i)).isDying && !((AbstractMonster)m.get(i)).isEscaping)
                        tmp[i] = p.atDamageFinalReceive(tmp[i], damageTypeForTurn, this);
                } while(true);
            }

            for(int i = 0; i < tmp.length; i++)
                if(tmp[i] < 0.0F)
                    tmp[i] = 0.0F;

            multiDamage = new int[tmp.length];
            for(int i = 0; i < tmp.length; i++)
            {
                if(baseDamage != MathUtils.floor(tmp[i]))
                    isDamageModified = true;
                multiDamage[i] = MathUtils.floor(tmp[i]);
            }

            damage = multiDamage[0];
        }
    }

    public void setAngle(float degrees, boolean immediate)
    {
        targetAngle = degrees;
        if(immediate)
            angle = targetAngle;
    }

    public void shrink()
    {
        targetDrawScale = 0.12F;
    }

    public void shrink(boolean immediate)
    {
        targetDrawScale = 0.12F;
        drawScale = 0.12F;
    }

    public void darken(boolean immediate)
    {
        darken = true;
        darkTimer = 0.3F;
        if(immediate)
        {
            tintColor.a = 1.0F;
            darkTimer = 0.0F;
        }
    }

    public void lighten(boolean immediate)
    {
        darken = false;
        darkTimer = 0.3F;
        if(immediate)
        {
            tintColor.a = 0.0F;
            darkTimer = 0.0F;
        }
    }

    private void updateColor()
    {
        if(darkTimer != 0.0F)
        {
            darkTimer -= Gdx.graphics.getDeltaTime();
            if(darkTimer < 0.0F)
                darkTimer = 0.0F;
            if(darken)
                tintColor.a = 1.0F - (darkTimer * 1.0F) / 0.3F;
            else
                tintColor.a = (darkTimer * 1.0F) / 0.3F;
        }
    }

    public void superFlash(Color c)
    {
        flashVfx = new CardFlashVfx(this, c, true);
    }

    public void superFlash()
    {
        flashVfx = new CardFlashVfx(this, true);
    }

    public void flash()
    {
        flashVfx = new CardFlashVfx(this);
    }

    public void flash(Color c)
    {
        flashVfx = new CardFlashVfx(this, c);
    }

    public void unfadeOut()
    {
        fadingOut = false;
        transparency = 1.0F;
        targetTransparency = 1.0F;
        bannerColor.a = transparency;
        backColor.a = transparency;
        frameColor.a = transparency;
        bgColor.a = transparency;
        descBoxColor.a = transparency;
        imgFrameColor.a = transparency;
        frameShadowColor.a = transparency / 4F;
        renderColor.a = transparency;
        goldColor.a = transparency;
        if(frameOutlineColor != null)
            frameOutlineColor.a = transparency;
    }

    private void updateTransparency()
    {
        if(fadingOut && transparency != 0.0F)
        {
            transparency -= Gdx.graphics.getDeltaTime() * 2.0F;
            if(transparency < 0.0F)
                transparency = 0.0F;
        } else
        if(transparency != targetTransparency)
        {
            transparency += Gdx.graphics.getDeltaTime() * 2.0F;
            if(transparency > targetTransparency)
                transparency = targetTransparency;
        }
        bannerColor.a = transparency;
        backColor.a = transparency;
        frameColor.a = transparency;
        bgColor.a = transparency;
        descBoxColor.a = transparency;
        imgFrameColor.a = transparency;
        frameShadowColor.a = transparency / 4F;
        renderColor.a = transparency;
        textColor.a = transparency;
        goldColor.a = transparency;
        if(frameOutlineColor != null)
            frameOutlineColor.a = transparency;
    }

    public void setAngle(float degrees)
    {
        setAngle(degrees, false);
    }

    protected String getCantPlayMessage()
    {
        return TEXT[13];
    }

    public void clearPowers()
    {
        resetAttributes();
        isDamageModified = false;
    }

    public static void debugPrintDetailedCardDataHeader()
    {
        logger.info(gameDataUploadHeader());
    }

    public static String gameDataUploadHeader()
    {
        GameDataStringBuilder builder = new GameDataStringBuilder();
        builder.addFieldData("name");
        builder.addFieldData("cardID");
        builder.addFieldData("rawDescription");
        builder.addFieldData("assetURL");
        builder.addFieldData("keywords");
        builder.addFieldData("color");
        builder.addFieldData("type");
        builder.addFieldData("rarity");
        builder.addFieldData("cost");
        builder.addFieldData("target");
        builder.addFieldData("damageType");
        builder.addFieldData("baseDamage");
        builder.addFieldData("baseBlock");
        builder.addFieldData("baseHeal");
        builder.addFieldData("baseDraw");
        builder.addFieldData("baseDiscard");
        builder.addFieldData("baseMagicNumber");
        builder.addFieldData("isMultiDamage");
        return builder.toString();
    }

    public void debugPrintDetailedCardData()
    {
        logger.info(gameDataUploadData());
    }

    protected void addToBot(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public String gameDataUploadData()
    {
        GameDataStringBuilder builder = new GameDataStringBuilder();
        builder.addFieldData(name);
        builder.addFieldData(cardID);
        builder.addFieldData(rawDescription);
        builder.addFieldData(assetUrl);
        builder.addFieldData(Arrays.toString(keywords.toArray()));
        builder.addFieldData(color.name());
        builder.addFieldData(type.name());
        builder.addFieldData(rarity.name());
        builder.addFieldData(cost);
        builder.addFieldData(target.name());
        builder.addFieldData(damageType.name());
        builder.addFieldData(baseDamage);
        builder.addFieldData(baseBlock);
        builder.addFieldData(baseHeal);
        builder.addFieldData(baseDraw);
        builder.addFieldData(baseDiscard);
        builder.addFieldData(baseMagicNumber);
        builder.addFieldData(isMultiDamage);
        return builder.toString();
    }

    public String toString()
    {
        return name;
    }

    public int compareTo(AbstractCard other)
    {
        return cardID.compareTo(other.cardID);
    }

    public void setLocked()
    {
        isLocked = true;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[type.ordinal()])
        {
        case 1: // '\001'
            portraitImg = ImageMaster.CARD_LOCKED_ATTACK;
            break;

        case 5: // '\005'
            portraitImg = ImageMaster.CARD_LOCKED_POWER;
            break;

        default:
            portraitImg = ImageMaster.CARD_LOCKED_SKILL;
            break;
        }
        initializeDescription();
    }

    public void unlock()
    {
        isLocked = false;
        portrait = cardAtlas.findRegion(assetUrl);
        if(portrait == null)
            portrait = oldCardAtlas.findRegion(assetUrl);
    }

    public HashMap getLocStrings()
    {
        HashMap cardData = new HashMap();
        initializeDescription();
        cardData.put("name", name);
        cardData.put("description", rawDescription);
        return cardData;
    }

    public String getMetricID()
    {
        String id = cardID;
        if(upgraded)
        {
            id = (new StringBuilder()).append(id).append("+").toString();
            if(timesUpgraded > 0)
                id = (new StringBuilder()).append(id).append(timesUpgraded).toString();
        }
        return id;
    }

    public void triggerOnGlowCheck()
    {
    }

    public abstract AbstractCard makeCopy();

    public volatile int compareTo(Object obj)
    {
        return compareTo((AbstractCard)obj);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/cards/AbstractCard.getName());
    public CardType type;
    public int cost;
    public int costForTurn;
    public int price;
    public int chargeCost;
    public boolean isCostModified;
    public boolean isCostModifiedForTurn;
    public boolean retain;
    public boolean selfRetain;
    public boolean dontTriggerOnUseCard;
    public CardRarity rarity;
    public CardColor color;
    public boolean isInnate;
    public boolean isLocked;
    public boolean showEvokeValue;
    public int showEvokeOrbCount;
    public ArrayList keywords;
    private static final int COMMON_CARD_PRICE = 50;
    private static final int UNCOMMON_CARD_PRICE = 75;
    private static final int RARE_CARD_PRICE = 150;
    protected boolean isUsed;
    public boolean upgraded;
    public int timesUpgraded;
    public int misc;
    public int energyOnUse;
    public boolean ignoreEnergyOnUse;
    public boolean isSeen;
    public boolean upgradedCost;
    public boolean upgradedDamage;
    public boolean upgradedBlock;
    public boolean upgradedMagicNumber;
    public UUID uuid;
    public boolean isSelected;
    public boolean exhaust;
    public boolean returnToHand;
    public boolean shuffleBackIntoDrawPile;
    public boolean isEthereal;
    public ArrayList tags;
    public int multiDamage[];
    protected boolean isMultiDamage;
    public int baseDamage;
    public int baseBlock;
    public int baseMagicNumber;
    public int baseHeal;
    public int baseDraw;
    public int baseDiscard;
    public int damage;
    public int block;
    public int magicNumber;
    public int heal;
    public int draw;
    public int discard;
    public boolean isDamageModified;
    public boolean isBlockModified;
    public boolean isMagicNumberModified;
    protected DamageInfo.DamageType damageType;
    public DamageInfo.DamageType damageTypeForTurn;
    public CardTarget target;
    public boolean purgeOnUse;
    public boolean exhaustOnUseOnce;
    public boolean exhaustOnFire;
    public boolean freeToPlayOnce;
    public boolean isInAutoplay;
    private static TextureAtlas orbAtlas;
    private static TextureAtlas cardAtlas;
    private static TextureAtlas oldCardAtlas;
    public static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion orb_red;
    public static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion orb_green;
    public static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion orb_blue;
    public static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion orb_purple;
    public static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion orb_card;
    public static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion orb_potion;
    public static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion orb_relic;
    public static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion orb_special;
    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion portrait;
    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion jokePortrait;
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public static float typeWidthAttack;
    public static float typeWidthSkill;
    public static float typeWidthPower;
    public static float typeWidthCurse;
    public static float typeWidthStatus;
    public static float typeOffsetAttack;
    public static float typeOffsetSkill;
    public static float typeOffsetPower;
    public static float typeOffsetCurse;
    public static float typeOffsetStatus;
    public AbstractGameEffect flashVfx;
    public String assetUrl;
    public boolean fadingOut;
    public float transparency;
    public float targetTransparency;
    private Color goldColor;
    private Color renderColor;
    private Color textColor;
    private Color typeColor;
    public float targetAngle;
    private static final float NAME_OFFSET_Y = 175F;
    private static final float ENERGY_TEXT_OFFSET_X = -132F;
    private static final float ENERGY_TEXT_OFFSET_Y = 192F;
    private static final int W = 512;
    public float angle;
    private ArrayList glowList;
    private float glowTimer;
    public boolean isGlowing;
    public static final float SMALL_SCALE = 0.7F;
    public static final int RAW_W = 300;
    public static final int RAW_H = 420;
    public static final float IMG_WIDTH;
    public static final float IMG_HEIGHT;
    public static final float IMG_WIDTH_S;
    public static final float IMG_HEIGHT_S;
    private static final float SHADOW_OFFSET_X;
    private static final float SHADOW_OFFSET_Y;
    public float current_x;
    public float current_y;
    public float target_x;
    public float target_y;
    protected Texture portraitImg;
    private boolean useSmallTitleFont;
    public float drawScale;
    public float targetDrawScale;
    private static final int PORTRAIT_WIDTH = 250;
    private static final int PORTRAIT_HEIGHT = 190;
    private static final float PORTRAIT_OFFSET_Y = 72F;
    private static final float LINE_SPACING = 1.45F;
    public boolean isFlipped;
    private boolean darken;
    private float darkTimer;
    private static final float DARKEN_TIME = 0.3F;
    public Hitbox hb;
    private static final float HB_W;
    private static final float HB_H;
    public float hoverTimer;
    private boolean renderTip;
    private boolean hovered;
    private float hoverDuration;
    private static final float HOVER_TIP_TIME = 0.2F;
    private static final GlyphLayout gl = new GlyphLayout();
    private static final StringBuilder sbuilder = new StringBuilder();
    private static final StringBuilder sbuilder2 = new StringBuilder();
    public AbstractCard cardsToPreview;
    protected static final float CARD_TIP_PAD = 16F;
    public float newGlowTimer;
    public String originalName;
    public String name;
    public String rawDescription;
    public String cardID;
    public ArrayList description;
    public String cantUseMessage;
    private static final float TYPE_OFFSET_Y = -1F;
    private static final float DESC_OFFSET_Y;
    private static final float DESC_OFFSET_Y2 = -6F;
    private static final float DESC_BOX_WIDTH;
    private static final float CN_DESC_BOX_WIDTH;
    private static final float TITLE_BOX_WIDTH;
    private static final float TITLE_BOX_WIDTH_NO_COST;
    private static final float CARD_ENERGY_IMG_WIDTH;
    private static final float MAGIC_NUM_W;
    private static final UIStrings cardRenderStrings;
    public static final String LOCKED_STRING;
    public static final String UNKNOWN_STRING;
    private Color bgColor;
    private Color backColor;
    private Color frameColor;
    private Color frameOutlineColor;
    private Color frameShadowColor;
    private Color imgFrameColor;
    private Color descBoxColor;
    private Color bannerColor;
    private Color tintColor;
    private static final Color ENERGY_COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);
    private static final Color ENERGY_COST_MODIFIED_COLOR = new Color(0.4F, 1.0F, 0.4F, 1.0F);
    private static final Color FRAME_SHADOW_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.25F);
    private static final Color IMG_FRAME_COLOR_COMMON = CardHelper.getColor(53, 58, 64);
    private static final Color IMG_FRAME_COLOR_UNCOMMON = CardHelper.getColor(119, 152, 161);
    private static final Color IMG_FRAME_COLOR_RARE = new Color(0.855F, 0.557F, 0.32F, 1.0F);
    private static final Color HOVER_IMG_COLOR = new Color(1.0F, 0.815F, 0.314F, 0.8F);
    private static final Color SELECTED_CARD_COLOR = new Color(0.5F, 0.9F, 0.9F, 1.0F);
    private static final Color BANNER_COLOR_COMMON = CardHelper.getColor(131, 129, 121);
    private static final Color BANNER_COLOR_UNCOMMON = CardHelper.getColor(142, 196, 213);
    private static final Color BANNER_COLOR_RARE = new Color(1.0F, 0.796F, 0.251F, 1.0F);
    private static final Color CURSE_BG_COLOR = CardHelper.getColor(29, 29, 29);
    private static final Color CURSE_TYPE_BACK_COLOR = new Color(0.23F, 0.23F, 0.23F, 1.0F);
    private static final Color CURSE_FRAME_COLOR = CardHelper.getColor(21, 2, 21);
    private static final Color CURSE_DESC_BOX_COLOR = CardHelper.getColor(52, 58, 64);
    private static final Color COLORLESS_BG_COLOR = new Color(0.15F, 0.15F, 0.15F, 1.0F);
    private static final Color COLORLESS_TYPE_BACK_COLOR = new Color(0.23F, 0.23F, 0.23F, 1.0F);
    private static final Color COLORLESS_FRAME_COLOR = new Color(0.48F, 0.48F, 0.48F, 1.0F);
    private static final Color COLORLESS_DESC_BOX_COLOR = new Color(0.351F, 0.363F, 0.3745F, 1.0F);
    private static final Color RED_BG_COLOR = CardHelper.getColor(50, 26, 26);
    private static final Color RED_TYPE_BACK_COLOR = CardHelper.getColor(91, 43, 32);
    private static final Color RED_FRAME_COLOR = CardHelper.getColor(121, 12, 28);
    private static final Color RED_RARE_OUTLINE_COLOR = new Color(1.0F, 0.75F, 0.43F, 1.0F);
    private static final Color RED_DESC_BOX_COLOR = CardHelper.getColor(53, 58, 64);
    private static final Color GREEN_BG_COLOR = CardHelper.getColor(19, 45, 40);
    private static final Color GREEN_TYPE_BACK_COLOR = CardHelper.getColor(32, 91, 43);
    private static final Color GREEN_FRAME_COLOR = CardHelper.getColor(52, 123, 8);
    private static final Color GREEN_RARE_OUTLINE_COLOR = new Color(1.0F, 0.75F, 0.43F, 1.0F);
    private static final Color GREEN_DESC_BOX_COLOR = CardHelper.getColor(53, 58, 64);
    private static final Color BLUE_BG_COLOR = CardHelper.getColor(19, 45, 40);
    private static final Color BLUE_TYPE_BACK_COLOR = CardHelper.getColor(32, 91, 43);
    private static final Color BLUE_FRAME_COLOR = CardHelper.getColor(52, 123, 8);
    private static final Color BLUE_RARE_OUTLINE_COLOR = new Color(1.0F, 0.75F, 0.43F, 1.0F);
    private static final Color BLUE_DESC_BOX_COLOR = CardHelper.getColor(53, 58, 64);
    protected static final Color BLUE_BORDER_GLOW_COLOR = new Color(0.2F, 0.9F, 1.0F, 0.25F);
    protected static final Color GREEN_BORDER_GLOW_COLOR = new Color(0.0F, 1.0F, 0.0F, 0.25F);
    protected static final Color GOLD_BORDER_GLOW_COLOR;
    public boolean inBottleFlame;
    public boolean inBottleLightning;
    public boolean inBottleTornado;
    public Color glowColor;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("SingleCardViewPopup");
        TEXT = uiStrings.TEXT;
        IMG_WIDTH = 300F * Settings.scale;
        IMG_HEIGHT = 420F * Settings.scale;
        IMG_WIDTH_S = 300F * Settings.scale * 0.7F;
        IMG_HEIGHT_S = 420F * Settings.scale * 0.7F;
        SHADOW_OFFSET_X = 18F * Settings.scale;
        SHADOW_OFFSET_Y = 14F * Settings.scale;
        HB_W = 300F * Settings.scale;
        HB_H = 420F * Settings.scale;
        DESC_OFFSET_Y = Settings.BIG_TEXT_MODE ? IMG_HEIGHT * 0.24F : IMG_HEIGHT * 0.255F;
        DESC_BOX_WIDTH = Settings.BIG_TEXT_MODE ? IMG_WIDTH * 0.95F : IMG_WIDTH * 0.79F;
        CN_DESC_BOX_WIDTH = Settings.BIG_TEXT_MODE ? IMG_WIDTH * 0.87F : IMG_WIDTH * 0.72F;
        TITLE_BOX_WIDTH = IMG_WIDTH * 0.6F;
        TITLE_BOX_WIDTH_NO_COST = IMG_WIDTH * 0.7F;
        CARD_ENERGY_IMG_WIDTH = 24F * Settings.scale;
        MAGIC_NUM_W = 20F * Settings.scale;
        cardRenderStrings = CardCrawlGame.languagePack.getUIString("AbstractCard");
        LOCKED_STRING = cardRenderStrings.TEXT[0];
        UNKNOWN_STRING = cardRenderStrings.TEXT[1];
        GOLD_BORDER_GLOW_COLOR = Color.GOLD.cpy();
    }
}
