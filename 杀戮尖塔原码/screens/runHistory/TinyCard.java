// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TinyCard.java

package com.megacrit.cardcrawl.screens.runHistory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class TinyCard
{

    public TinyCard(AbstractCard card, int count)
    {
        col = -1;
        row = -1;
        this.card = card;
        this.count = count;
        hb = new Hitbox(approximateWidth(), ImageMaster.TINY_CARD_ATTACK.getHeight());
    }

    public float approximateWidth()
    {
        String text = getText();
        return TEXT_LEADING_SPACE + FontHelper.getSmartWidth(FontHelper.charDescFont, text, 9999F, LINE_SPACING);
    }

    private String getText()
    {
        String text = count != 1 ? (new StringBuilder()).append(count).append(" x ").append(card.name).toString() : card.name;
        if(text.length() > 18)
            text = (new StringBuilder()).append(text.substring(0, 15)).append("...").toString();
        return text;
    }

    public void render(SpriteBatch sb)
    {
        float x = hb.x;
        float y = hb.y;
        float width = scaled(46F);
        float height = scaled(46F);
        renderTinyCardIcon(sb, card, x, y, width, height);
        String text = getText();
        float textOffset = -(height / 2.0F) + scaled(7F);
        Color basicColor = card.upgraded ? Settings.GREEN_TEXT_COLOR : Settings.CREAM_COLOR;
        Color textColor = hb.hovered ? Settings.GOLD_COLOR : basicColor;
        if(hb.hovered)
            FontHelper.renderSmartText(sb, FontHelper.charDescFont, text, x + TEXT_LEADING_SPACE + 3F * Settings.scale, y + height + textOffset, 9999F, LINE_SPACING, textColor);
        else
            FontHelper.renderSmartText(sb, FontHelper.charDescFont, text, x + TEXT_LEADING_SPACE, y + height + textOffset, 9999F, LINE_SPACING, textColor);
        hb.render(sb);
    }

    public boolean updateDidClick()
    {
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
        if(hb.hovered)
        {
            CardCrawlGame.cursor.changeType(com.megacrit.cardcrawl.core.GameCursor.CursorType.INSPECT);
            if(InputHelper.justClickedLeft)
            {
                CardCrawlGame.sound.play("UI_CLICK_1");
                hb.clickStarted = true;
            }
        }
        if(hb.clicked)
        {
            hb.clicked = false;
            return true;
        } else
        {
            return false;
        }
    }

    private Color getIconBackgroundColor(AbstractCard card)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[];
            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[];
            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror13) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror14) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror15) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror16) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
        {
        case 1: // '\001'
            return RED_BACKGROUND_COLOR;

        case 2: // '\002'
            return GREEN_BACKGROUND_COLOR;

        case 3: // '\003'
            return BLUE_BACKGROUND_COLOR;

        case 4: // '\004'
            return PURPLE_BACKGROUND_COLOR;

        case 5: // '\005'
            return COLORLESS_BACKGROUND_COLOR;

        case 6: // '\006'
            return CURSE_BACKGROUND_COLOR;
        }
        return new Color(0xff69b4ff);
    }

    private Color getIconDescriptionColor(AbstractCard card)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
        {
        case 1: // '\001'
            return RED_DESCRIPTION_COLOR;

        case 2: // '\002'
            return GREEN_DESCRIPTION_COLOR;

        case 3: // '\003'
            return BLUE_DESCRIPTION_COLOR;

        case 4: // '\004'
            return PURPLE_DESCRIPTION_COLOR;

        case 5: // '\005'
            return COLORLESS_DESCRIPTION_COLOR;

        case 6: // '\006'
            return CURSE_DESCRIPTION_COLOR;
        }
        return new Color(0xb2497dff);
    }

    private Color getIconBannerColor(AbstractCard card)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
        {
        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
            return COMMON_BANNER_COLOR;

        case 5: // '\005'
            return UNCOMMON_BANNER_COLOR;

        case 6: // '\006'
            return RARE_BANNER_COLOR;
        }
        return COMMON_BANNER_COLOR;
    }

    private Texture getIconPortrait(AbstractCard card)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[card.type.ordinal()])
        {
        case 1: // '\001'
            return ImageMaster.TINY_CARD_ATTACK;

        case 2: // '\002'
            return ImageMaster.TINY_CARD_POWER;

        case 3: // '\003'
        case 4: // '\004'
        case 5: // '\005'
        default:
            return ImageMaster.TINY_CARD_SKILL;
        }
    }

    private void renderTinyCardIcon(SpriteBatch sb, AbstractCard card, float x, float y, float width, float height)
    {
        sb.setColor(getIconBackgroundColor(card));
        sb.draw(ImageMaster.TINY_CARD_BACKGROUND, x, y, width, height);
        sb.setColor(getIconDescriptionColor(card));
        sb.draw(ImageMaster.TINY_CARD_DESCRIPTION, x, y, width, height);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.TINY_CARD_PORTRAIT_SHADOW, x, y, width, height);
        sb.draw(getIconPortrait(card), x, y, width, height);
        sb.draw(ImageMaster.TINY_CARD_BANNER_SHADOW, x, y, width, height);
        sb.setColor(getIconBannerColor(card));
        sb.draw(ImageMaster.TINY_CARD_BANNER, x, y, width, height);
    }

    private static float scaled(float val)
    {
        return Settings.scale * val;
    }

    private static final int MAX_CARD_TEXT_LENGTH = 18;
    private static final Color RED_BACKGROUND_COLOR = new Color(0xd52323ff);
    private static final Color RED_DESCRIPTION_COLOR = new Color(0x603232ff);
    private static final Color GREEN_BACKGROUND_COLOR = new Color(0x6ad45bff);
    private static final Color GREEN_DESCRIPTION_COLOR = new Color(0x355739ff);
    private static final Color BLUE_BACKGROUND_COLOR = new Color(0x69c0ffff);
    private static final Color BLUE_DESCRIPTION_COLOR = new Color(0x547dadff);
    private static final Color PURPLE_BACKGROUND_COLOR = new Color(0x9d39e3ff);
    private static final Color PURPLE_DESCRIPTION_COLOR = new Color(0x6012b0ff);
    private static final Color COLORLESS_BACKGROUND_COLOR = new Color(0x7a7a7aff);
    private static final Color COLORLESS_DESCRIPTION_COLOR = new Color(0x404040ff);
    private static final Color CURSE_BACKGROUND_COLOR = new Color(0x3b383cff);
    private static final Color CURSE_DESCRIPTION_COLOR = new Color(0x1b1b1bff);
    private static final Color COMMON_BANNER_COLOR = new Color(0xaeaeaeff);
    private static final Color UNCOMMON_BANNER_COLOR = new Color(0x8cf0f6ff);
    private static final Color RARE_BANNER_COLOR = new Color(0xf9d567ff);
    public static final float LINE_SPACING;
    public static final float LINE_WIDTH = 9999F;
    public static final float TEXT_LEADING_SPACE = scaled(60F);
    public static int desiredColumns;
    public AbstractCard card;
    public int count;
    public Hitbox hb;
    public int col;
    public int row;

    static 
    {
        LINE_SPACING = 36F * Settings.scale;
    }
}
