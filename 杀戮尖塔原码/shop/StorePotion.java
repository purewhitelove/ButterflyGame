// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StorePotion.java

package com.megacrit.cardcrawl.shop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

// Referenced classes of package com.megacrit.cardcrawl.shop:
//            ShopScreen

public class StorePotion
{

    public StorePotion(AbstractPotion potion, int slot, ShopScreen screenRef)
    {
        isPurchased = false;
        this.potion = potion;
        price = potion.getPrice();
        this.slot = slot;
        shopScreen = screenRef;
    }

    public void update(float rugY)
    {
        if(potion != null)
        {
            if(!isPurchased)
            {
                potion.posX = 1000F * Settings.xScale + 150F * (float)slot * Settings.xScale;
                potion.posY = rugY + 200F * Settings.yScale;
                potion.hb.move(potion.posX, potion.posY);
                potion.hb.update();
                if(potion.hb.hovered)
                {
                    shopScreen.moveHand(potion.posX - 190F * Settings.scale, potion.posY - 70F * Settings.scale);
                    if(InputHelper.justClickedLeft)
                        potion.hb.clickStarted = true;
                }
            }
            if(potion.hb.clicked || potion.hb.hovered && CInputActionSet.select.isJustPressed())
            {
                potion.hb.clicked = false;
                if(!Settings.isTouchScreen)
                    purchasePotion();
                else
                if(AbstractDungeon.shopScreen.touchPotion == null)
                    if(AbstractDungeon.player.gold < price)
                    {
                        shopScreen.playCantBuySfx();
                        shopScreen.createSpeech(ShopScreen.getCantBuyMsg());
                    } else
                    {
                        AbstractDungeon.shopScreen.confirmButton.hideInstantly();
                        AbstractDungeon.shopScreen.confirmButton.show();
                        AbstractDungeon.shopScreen.confirmButton.isDisabled = false;
                        AbstractDungeon.shopScreen.confirmButton.hb.clickStarted = false;
                        AbstractDungeon.shopScreen.touchPotion = this;
                    }
            }
        }
    }

    public void purchasePotion()
    {
        if(AbstractDungeon.player.hasRelic("Sozu"))
        {
            AbstractDungeon.player.getRelic("Sozu").flash();
            return;
        }
        if(AbstractDungeon.player.gold >= price)
        {
            if(AbstractDungeon.player.obtainPotion(potion))
            {
                AbstractDungeon.player.loseGold(price);
                CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1F);
                CardCrawlGame.metricData.addShopPurchaseData(potion.ID);
                shopScreen.playBuySfx();
                shopScreen.createSpeech(ShopScreen.getBuyMsg());
                if(AbstractDungeon.player.hasRelic("The Courier"))
                {
                    potion = AbstractDungeon.returnRandomPotion();
                    price = potion.getPrice();
                    shopScreen.getNewPrice(this);
                } else
                {
                    isPurchased = true;
                }
                return;
            }
            shopScreen.createSpeech(TEXT[0]);
            AbstractDungeon.topPanel.flashRed();
        } else
        {
            shopScreen.playCantBuySfx();
            shopScreen.createSpeech(ShopScreen.getCantBuyMsg());
        }
    }

    public void hide()
    {
        if(potion != null)
            potion.posY = (float)Settings.HEIGHT + 200F * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        if(potion != null)
        {
            potion.shopRender(sb);
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.UI_GOLD, potion.posX + RELIC_GOLD_OFFSET_X, potion.posY + RELIC_GOLD_OFFSET_Y, GOLD_IMG_WIDTH, GOLD_IMG_WIDTH);
            Color color = Color.WHITE;
            if(price > AbstractDungeon.player.gold)
                color = Color.SALMON;
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, Integer.toString(price), potion.posX + RELIC_PRICE_OFFSET_X, potion.posY + RELIC_PRICE_OFFSET_Y, color);
        }
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public AbstractPotion potion;
    private ShopScreen shopScreen;
    public int price;
    private int slot;
    public boolean isPurchased;
    private static final float RELIC_GOLD_OFFSET_X;
    private static final float RELIC_GOLD_OFFSET_Y;
    private static final float RELIC_PRICE_OFFSET_X;
    private static final float RELIC_PRICE_OFFSET_Y;
    private static final float GOLD_IMG_WIDTH;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("StorePotion");
        TEXT = uiStrings.TEXT;
        RELIC_GOLD_OFFSET_X = -56F * Settings.scale;
        RELIC_GOLD_OFFSET_Y = -100F * Settings.scale;
        RELIC_PRICE_OFFSET_X = 14F * Settings.scale;
        RELIC_PRICE_OFFSET_Y = -62F * Settings.scale;
        GOLD_IMG_WIDTH = (float)ImageMaster.UI_GOLD.getWidth() * Settings.scale;
    }
}
