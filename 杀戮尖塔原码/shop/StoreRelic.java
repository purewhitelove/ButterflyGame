// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StoreRelic.java

package com.megacrit.cardcrawl.shop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.shop:
//            ShopScreen

public class StoreRelic
{

    public StoreRelic(AbstractRelic relic, int slot, ShopScreen screenRef)
    {
        isPurchased = false;
        this.relic = relic;
        price = relic.getPrice();
        this.slot = slot;
        shopScreen = screenRef;
    }

    public void update(float rugY)
    {
        if(relic != null)
        {
            if(!isPurchased)
            {
                relic.currentX = 1000F * Settings.xScale + 150F * (float)slot * Settings.xScale;
                relic.currentY = rugY + 400F * Settings.yScale;
                relic.hb.move(relic.currentX, relic.currentY);
                relic.hb.update();
                if(relic.hb.hovered)
                {
                    shopScreen.moveHand(relic.currentX - 190F * Settings.xScale, relic.currentY - 70F * Settings.yScale);
                    if(InputHelper.justClickedLeft)
                        relic.hb.clickStarted = true;
                    relic.scale = Settings.scale * 1.25F;
                } else
                {
                    relic.scale = MathHelper.scaleLerpSnap(relic.scale, Settings.scale);
                }
                if(relic.hb.hovered && InputHelper.justClickedRight)
                    CardCrawlGame.relicPopup.open(relic);
            }
            if(relic.hb.clicked || relic.hb.hovered && CInputActionSet.select.isJustPressed())
            {
                relic.hb.clicked = false;
                if(!Settings.isTouchScreen)
                    purchaseRelic();
                else
                if(AbstractDungeon.shopScreen.touchRelic == null)
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
                        AbstractDungeon.shopScreen.touchRelic = this;
                    }
            }
        }
    }

    public void purchaseRelic()
    {
        if(AbstractDungeon.player.gold >= price)
        {
            AbstractDungeon.player.loseGold(price);
            CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1F);
            CardCrawlGame.metricData.addShopPurchaseData(relic.relicId);
            AbstractDungeon.getCurrRoom().relics.add(relic);
            relic.instantObtain(AbstractDungeon.player, AbstractDungeon.player.relics.size(), true);
            relic.flash();
            if(relic.relicId.equals("Membership Card"))
                shopScreen.applyDiscount(0.5F, true);
            if(relic.relicId.equals("Smiling Mask"))
                ShopScreen.actualPurgeCost = 50;
            AbstractCard c;
            for(Iterator iterator = shopScreen.coloredCards.iterator(); iterator.hasNext(); relic.onPreviewObtainCard(c))
                c = (AbstractCard)iterator.next();

            AbstractCard c;
            for(Iterator iterator1 = shopScreen.colorlessCards.iterator(); iterator1.hasNext(); relic.onPreviewObtainCard(c))
                c = (AbstractCard)iterator1.next();

            shopScreen.playBuySfx();
            shopScreen.createSpeech(ShopScreen.getBuyMsg());
            if(relic.relicId.equals("The Courier") || AbstractDungeon.player.hasRelic("The Courier"))
            {
                AbstractRelic tempRelic;
                for(tempRelic = AbstractDungeon.returnRandomRelicEnd(ShopScreen.rollRelicTier()); (tempRelic instanceof OldCoin) || (tempRelic instanceof SmilingMask) || (tempRelic instanceof MawBank) || (tempRelic instanceof Courier); tempRelic = AbstractDungeon.returnRandomRelicEnd(ShopScreen.rollRelicTier()));
                relic = tempRelic;
                price = relic.getPrice();
                shopScreen.getNewPrice(this);
            } else
            {
                isPurchased = true;
            }
        } else
        {
            shopScreen.playCantBuySfx();
            shopScreen.createSpeech(ShopScreen.getCantBuyMsg());
        }
    }

    public void hide()
    {
        if(relic != null)
            relic.currentY = (float)Settings.HEIGHT + 200F * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        if(relic != null)
        {
            relic.renderWithoutAmount(sb, new Color(0.0F, 0.0F, 0.0F, 0.25F));
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.UI_GOLD, relic.currentX + RELIC_GOLD_OFFSET_X, relic.currentY + RELIC_GOLD_OFFSET_Y, GOLD_IMG_WIDTH, GOLD_IMG_WIDTH);
            Color color = Color.WHITE;
            if(price > AbstractDungeon.player.gold)
                color = Color.SALMON;
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, Integer.toString(price), relic.currentX + RELIC_PRICE_OFFSET_X, relic.currentY + RELIC_PRICE_OFFSET_Y, color);
        }
    }

    public AbstractRelic relic;
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
        RELIC_GOLD_OFFSET_X = -56F * Settings.scale;
        RELIC_GOLD_OFFSET_Y = -100F * Settings.scale;
        RELIC_PRICE_OFFSET_X = 14F * Settings.scale;
        RELIC_PRICE_OFFSET_Y = -62F * Settings.scale;
        GOLD_IMG_WIDTH = (float)ImageMaster.UI_GOLD.getWidth() * Settings.scale;
    }
}
