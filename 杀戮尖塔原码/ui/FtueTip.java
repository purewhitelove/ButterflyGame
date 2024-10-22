// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FtueTip.java

package com.megacrit.cardcrawl.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.ui.buttons.GotItButton;
import com.megacrit.cardcrawl.ui.panels.DiscardPilePanel;
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class FtueTip
{
    public static final class TipType extends Enum
    {

        public static TipType[] values()
        {
            return (TipType[])$VALUES.clone();
        }

        public static TipType valueOf(String name)
        {
            return (TipType)Enum.valueOf(com/megacrit/cardcrawl/ui/FtueTip$TipType, name);
        }

        public static final TipType ENERGY;
        public static final TipType CREATURE;
        public static final TipType CARD;
        public static final TipType POTION;
        public static final TipType CARD_REWARD;
        public static final TipType INTENT;
        public static final TipType SHUFFLE;
        public static final TipType NO_FTUE;
        public static final TipType COMBAT;
        public static final TipType RELIC;
        public static final TipType MULTI;
        public static final TipType POWER;
        private static final TipType $VALUES[];

        static 
        {
            ENERGY = new TipType("ENERGY", 0);
            CREATURE = new TipType("CREATURE", 1);
            CARD = new TipType("CARD", 2);
            POTION = new TipType("POTION", 3);
            CARD_REWARD = new TipType("CARD_REWARD", 4);
            INTENT = new TipType("INTENT", 5);
            SHUFFLE = new TipType("SHUFFLE", 6);
            NO_FTUE = new TipType("NO_FTUE", 7);
            COMBAT = new TipType("COMBAT", 8);
            RELIC = new TipType("RELIC", 9);
            MULTI = new TipType("MULTI", 10);
            POWER = new TipType("POWER", 11);
            $VALUES = (new TipType[] {
                ENERGY, CREATURE, CARD, POTION, CARD_REWARD, INTENT, SHUFFLE, NO_FTUE, COMBAT, RELIC, 
                MULTI, POWER
            });
        }

        private TipType(String s, int i)
        {
            super(s, i);
        }
    }


    public FtueTip()
    {
        type = null;
    }

    public FtueTip(String header, String body, float x, float y, AbstractPotion potion)
    {
        type = null;
        openScreen(header, body, x, y);
        type = TipType.POTION;
        p = potion;
    }

    public FtueTip(String header, String body, float x, float y, TipType type)
    {
        this.type = null;
        openScreen(header, body, x, y);
        this.type = type;
    }

    public FtueTip(String header, String body, float x, float y, AbstractCard c)
    {
        type = null;
        openScreen(header, body, x, y);
        this.c = c;
        type = TipType.CARD;
    }

    public void openScreen(String header, String body, float x, float y)
    {
        this.header = header;
        this.body = body;
        this.x = x;
        this.y = y;
        c = null;
        m = null;
        p = null;
        button = new GotItButton(x, y);
        AbstractDungeon.player.releaseCard();
        if(AbstractDungeon.isScreenUp)
        {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
    }

    public void update()
    {
        button.update();
        if(button.hb.clicked || CInputActionSet.proceed.isJustPressed())
        {
            CInputActionSet.proceed.unpress();
            CardCrawlGame.sound.play("DECK_OPEN");
            if(type == TipType.POWER)
                AbstractDungeon.cardRewardScreen.reopen();
            else
                AbstractDungeon.closeCurrentScreen();
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.FTUE, x - 311F, y - 142F, 311F, 142F, 622F, 284F, Settings.scale, Settings.scale, 0.0F, 0, 0, 622, 284, false, false);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.7F + (MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L) + 1.25F) / 5F));
        button.render(sb);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, (new StringBuilder()).append(LABEL[0]).append(header).toString(), x - 190F * Settings.scale, y + 80F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, body, x - 250F * Settings.scale, y + 20F * Settings.scale, 450F * Settings.scale, 26F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, LABEL[1], x + 194F * Settings.scale, y - 150F * Settings.scale, Settings.GOLD_COLOR);
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$ui$FtueTip$TipType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$ui$FtueTip$TipType = new int[TipType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$FtueTip$TipType[TipType.CARD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$FtueTip$TipType[TipType.POWER.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$FtueTip$TipType[TipType.CARD_REWARD.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$FtueTip$TipType[TipType.CREATURE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$FtueTip$TipType[TipType.ENERGY.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$FtueTip$TipType[TipType.POTION.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$FtueTip$TipType[TipType.COMBAT.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$ui$FtueTip$TipType[TipType.SHUFFLE.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.ui.FtueTip.TipType[type.ordinal()])
        {
        case 1: // '\001'
            c.render(sb);
            break;

        case 2: // '\002'
            float pScale = c.drawScale;
            c.drawScale = 1.0F;
            c.render(sb);
            c.drawScale = pScale;
            break;

        case 4: // '\004'
            if(m.isPlayer)
                m.render(sb);
            else
                m.render(sb);
            if(m.hb.hovered)
                m.renderPowerTips(sb);
            break;

        case 5: // '\005'
            AbstractDungeon.overlayMenu.energyPanel.render(sb);
            break;

        case 6: // '\006'
            p.render(sb);
            break;

        case 8: // '\b'
            AbstractDungeon.overlayMenu.combatDeckPanel.render(sb);
            AbstractDungeon.overlayMenu.discardPilePanel.render(sb);
            break;
        }
        if(Settings.isControllerMode)
        {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.proceed.getKeyImg(), (button.hb.cX - 32F) + 130F * Settings.scale, (button.hb.cY - 32F) + 2.0F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
    }

    private static final TutorialStrings tutorialStrings;
    public static final String LABEL[];
    private GotItButton button;
    private float x;
    private float y;
    private static final int W = 622;
    private static final int H = 284;
    private String header;
    private String body;
    private AbstractCard c;
    private AbstractCreature m;
    private AbstractPotion p;
    public TipType type;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("FTUE Tips");
        LABEL = tutorialStrings.LABEL;
    }
}
