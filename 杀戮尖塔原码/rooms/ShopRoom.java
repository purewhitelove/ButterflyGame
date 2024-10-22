// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShopRoom.java

package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            AbstractRoom

public class ShopRoom extends AbstractRoom
{

    public ShopRoom()
    {
        shopRarityBonus = 6;
        phase = AbstractRoom.RoomPhase.COMPLETE;
        merchant = null;
        mapSymbol = "$";
        mapImg = ImageMaster.MAP_NODE_MERCHANT;
        mapImgOutline = ImageMaster.MAP_NODE_MERCHANT_OUTLINE;
        baseRareCardChance = 9;
        baseUncommonCardChance = 37;
    }

    public void setMerchant(Merchant merc)
    {
        merchant = merc;
    }

    public void onPlayerEntry()
    {
        if(!AbstractDungeon.id.equals("TheEnding"))
            playBGM("SHOP");
        AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
        setMerchant(new Merchant());
    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardRarity getCardRarity(int roll)
    {
        return getCardRarity(roll, false);
    }

    public void update()
    {
        super.update();
        if(merchant != null)
            merchant.update();
        updatePurge();
    }

    private void updatePurge()
    {
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            ShopScreen.purgeCard();
            AbstractCard card;
            for(Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext(); AbstractDungeon.player.masterDeck.removeCard(card))
            {
                card = (AbstractCard)iterator.next();
                CardCrawlGame.metricData.addPurgedItem(card.getMetricID());
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.shopScreen.purgeAvailable = false;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(merchant != null)
            merchant.render(sb);
        super.render(sb);
        renderTips(sb);
    }

    public void dispose()
    {
        super.dispose();
        if(merchant != null)
        {
            merchant.dispose();
            merchant = null;
        }
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public int shopRarityBonus;
    public Merchant merchant;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ShopRoom");
        TEXT = uiStrings.TEXT;
    }
}
