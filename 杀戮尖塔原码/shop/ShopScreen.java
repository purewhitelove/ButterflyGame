// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShopScreen.java

package com.megacrit.cardcrawl.shop;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.Hoarder;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.shop:
//            OnSaleTag, StoreRelic, StorePotion, Merchant

public class ShopScreen
{
    private static final class StoreSelectionType extends Enum
    {

        public static StoreSelectionType[] values()
        {
            return (StoreSelectionType[])$VALUES.clone();
        }

        public static StoreSelectionType valueOf(String name)
        {
            return (StoreSelectionType)Enum.valueOf(com/megacrit/cardcrawl/shop/ShopScreen$StoreSelectionType, name);
        }

        public static final StoreSelectionType RELIC;
        public static final StoreSelectionType COLOR_CARD;
        public static final StoreSelectionType COLORLESS_CARD;
        public static final StoreSelectionType POTION;
        public static final StoreSelectionType PURGE;
        private static final StoreSelectionType $VALUES[];

        static 
        {
            RELIC = new StoreSelectionType("RELIC", 0);
            COLOR_CARD = new StoreSelectionType("COLOR_CARD", 1);
            COLORLESS_CARD = new StoreSelectionType("COLORLESS_CARD", 2);
            POTION = new StoreSelectionType("POTION", 3);
            PURGE = new StoreSelectionType("PURGE", 4);
            $VALUES = (new StoreSelectionType[] {
                RELIC, COLOR_CARD, COLORLESS_CARD, POTION, PURGE
            });
        }

        private StoreSelectionType(String s, int i)
        {
            super(s, i);
        }
    }


    public ShopScreen()
    {
        isActive = true;
        rugY = (float)Settings.HEIGHT / 2.0F + 540F * Settings.yScale;
        coloredCards = new ArrayList();
        colorlessCards = new ArrayList();
        speechTimer = 0.0F;
        speechBubble = null;
        dialogTextEffect = null;
        idleMessages = new ArrayList();
        saidWelcome = false;
        somethingHovered = false;
        relics = new ArrayList();
        potions = new ArrayList();
        purgeAvailable = false;
        purgeHovered = false;
        purgeCardScale = 1.0F;
        f_effect = new FloatyEffect(20F, 0.1F);
        handTimer = 1.0F;
        handX = (float)Settings.WIDTH / 2.0F;
        handY = Settings.HEIGHT;
        handTargetX = 0.0F;
        handTargetY = Settings.HEIGHT;
        notHoveredTimer = 0.0F;
        confirmButton = new ConfirmButton();
        touchRelic = null;
        touchPotion = null;
        touchCard = null;
        touchPurge = false;
    }

    public void init(ArrayList coloredCards, ArrayList colorlessCards)
    {
        idleMessages.clear();
        if(AbstractDungeon.id.equals("TheEnding"))
            Collections.addAll(idleMessages, Merchant.ENDING_TEXT);
        else
            Collections.addAll(idleMessages, TEXT);
        if(rugImg == null)
        {
            static class _cls1
            {

                static final int $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[];
                static final int $SwitchMap$com$megacrit$cardcrawl$shop$ShopScreen$StoreSelectionType[];

                static 
                {
                    $SwitchMap$com$megacrit$cardcrawl$shop$ShopScreen$StoreSelectionType = new int[StoreSelectionType.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$shop$ShopScreen$StoreSelectionType[StoreSelectionType.COLOR_CARD.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$shop$ShopScreen$StoreSelectionType[StoreSelectionType.COLORLESS_CARD.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror1) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$shop$ShopScreen$StoreSelectionType[StoreSelectionType.RELIC.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror2) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$shop$ShopScreen$StoreSelectionType[StoreSelectionType.POTION.ordinal()] = 4;
                    }
                    catch(NoSuchFieldError nosuchfielderror3) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$shop$ShopScreen$StoreSelectionType[StoreSelectionType.PURGE.ordinal()] = 5;
                    }
                    catch(NoSuchFieldError nosuchfielderror4) { }
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage = new int[com.megacrit.cardcrawl.core.Settings.GameLanguage.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.DEU.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror5) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.EPO.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror6) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.FIN.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror7) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.FRA.ordinal()] = 4;
                    }
                    catch(NoSuchFieldError nosuchfielderror8) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.ITA.ordinal()] = 5;
                    }
                    catch(NoSuchFieldError nosuchfielderror9) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.JPN.ordinal()] = 6;
                    }
                    catch(NoSuchFieldError nosuchfielderror10) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.KOR.ordinal()] = 7;
                    }
                    catch(NoSuchFieldError nosuchfielderror11) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.RUS.ordinal()] = 8;
                    }
                    catch(NoSuchFieldError nosuchfielderror12) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.THA.ordinal()] = 9;
                    }
                    catch(NoSuchFieldError nosuchfielderror13) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.UKR.ordinal()] = 10;
                    }
                    catch(NoSuchFieldError nosuchfielderror14) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS.ordinal()] = 11;
                    }
                    catch(NoSuchFieldError nosuchfielderror15) { }
                }
            }

            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.core.Settings.GameLanguage[Settings.language.ordinal()])
            {
            case 1: // '\001'
                rugImg = ImageMaster.loadImage("images/npcs/rug/deu.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/deu.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/deu.png");
                break;

            case 2: // '\002'
                rugImg = ImageMaster.loadImage("images/npcs/rug/epo.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/epo.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/epo.png");
                break;

            case 3: // '\003'
                rugImg = ImageMaster.loadImage("images/npcs/rug/fin.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/fin.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/fin.png");
                break;

            case 4: // '\004'
                rugImg = ImageMaster.loadImage("images/npcs/rug/fra.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/fra.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/fra.png");
                break;

            case 5: // '\005'
                rugImg = ImageMaster.loadImage("images/npcs/rug/ita.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/ita.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/ita.png");
                break;

            case 6: // '\006'
                rugImg = ImageMaster.loadImage("images/npcs/rug/jpn.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/jpn.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/jpn.png");
                break;

            case 7: // '\007'
                rugImg = ImageMaster.loadImage("images/npcs/rug/kor.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/kor.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/kor.png");
                break;

            case 8: // '\b'
                rugImg = ImageMaster.loadImage("images/npcs/rug/rus.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/rus.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/rus.png");
                break;

            case 9: // '\t'
                rugImg = ImageMaster.loadImage("images/npcs/rug/tha.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/tha.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/tha.png");
                break;

            case 10: // '\n'
                rugImg = ImageMaster.loadImage("images/npcs/rug/ukr.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/ukr.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/ukr.png");
                break;

            case 11: // '\013'
                rugImg = ImageMaster.loadImage("images/npcs/rug/zhs.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/zhs.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/zhs.png");
                break;

            default:
                rugImg = ImageMaster.loadImage("images/npcs/rug/eng.png");
                removeServiceImg = ImageMaster.loadImage("images/npcs/purge/eng.png");
                soldOutImg = ImageMaster.loadImage("images/npcs/sold_out/eng.png");
                break;
            }
            handImg = ImageMaster.loadImage("images/npcs/merchantHand.png");
        }
        HAND_W = (float)handImg.getWidth() * Settings.scale;
        HAND_H = (float)handImg.getHeight() * Settings.scale;
        this.coloredCards.clear();
        this.colorlessCards.clear();
        this.coloredCards = coloredCards;
        this.colorlessCards = colorlessCards;
        initCards();
        initRelics();
        initPotions();
        purgeAvailable = true;
        purgeCardY = -1000F;
        purgeCardX = (float)Settings.WIDTH * 0.73F * Settings.scale;
        purgeCardScale = 0.7F;
        actualPurgeCost = purgeCost;
        if(AbstractDungeon.ascensionLevel >= 16)
            applyDiscount(1.1F, false);
        if(AbstractDungeon.player.hasRelic("The Courier"))
            applyDiscount(0.8F, true);
        if(AbstractDungeon.player.hasRelic("Membership Card"))
            applyDiscount(0.5F, true);
        if(AbstractDungeon.player.hasRelic("Smiling Mask"))
            actualPurgeCost = 50;
    }

    public static void resetPurgeCost()
    {
        purgeCost = 75;
        actualPurgeCost = 75;
    }

    private void initCards()
    {
        int tmp = (int)((float)Settings.WIDTH - DRAW_START_X * 2.0F - AbstractCard.IMG_WIDTH_S * 5F) / 4;
        float padX = (int)((float)tmp + AbstractCard.IMG_WIDTH_S);
        for(int i = 0; i < coloredCards.size(); i++)
        {
            float tmpPrice = (float)AbstractCard.getPrice(((AbstractCard)coloredCards.get(i)).rarity) * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
            AbstractCard c = (AbstractCard)coloredCards.get(i);
            c.price = (int)tmpPrice;
            c.current_x = Settings.WIDTH / 2;
            c.target_x = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * (float)i;
            AbstractRelic r;
            for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onPreviewObtainCard(c))
                r = (AbstractRelic)iterator.next();

        }

        for(int i = 0; i < colorlessCards.size(); i++)
        {
            float tmpPrice = (float)AbstractCard.getPrice(((AbstractCard)colorlessCards.get(i)).rarity) * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
            tmpPrice *= 1.2F;
            AbstractCard c = (AbstractCard)colorlessCards.get(i);
            c.price = (int)tmpPrice;
            c.current_x = Settings.WIDTH / 2;
            c.target_x = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * (float)i;
            AbstractRelic r;
            for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onPreviewObtainCard(c))
                r = (AbstractRelic)iterator1.next();

        }

        AbstractCard saleCard = (AbstractCard)coloredCards.get(AbstractDungeon.merchantRng.random(0, 4));
        saleCard.price /= 2;
        saleTag = new OnSaleTag(saleCard);
        setStartingCardPositions();
    }

    public static void purgeCard()
    {
        AbstractDungeon.player.loseGold(actualPurgeCost);
        CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1F);
        purgeCost += 25;
        actualPurgeCost = purgeCost;
        if(AbstractDungeon.player.hasRelic("Smiling Mask"))
        {
            actualPurgeCost = 50;
            AbstractDungeon.player.getRelic("Smiling Mask").stopPulse();
        } else
        if(AbstractDungeon.player.hasRelic("The Courier") && AbstractDungeon.player.hasRelic("Membership Card"))
            actualPurgeCost = MathUtils.round((float)purgeCost * 0.8F * 0.5F);
        else
        if(AbstractDungeon.player.hasRelic("The Courier"))
            actualPurgeCost = MathUtils.round((float)purgeCost * 0.8F);
        else
        if(AbstractDungeon.player.hasRelic("Membership Card"))
            actualPurgeCost = MathUtils.round((float)purgeCost * 0.5F);
    }

    public void updatePurge()
    {
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            purgeCard();
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

    public static String getCantBuyMsg()
    {
        ArrayList list = new ArrayList();
        list.add(NAMES[1]);
        list.add(NAMES[2]);
        list.add(NAMES[3]);
        list.add(NAMES[4]);
        list.add(NAMES[5]);
        list.add(NAMES[6]);
        return (String)list.get(MathUtils.random(list.size() - 1));
    }

    public static String getBuyMsg()
    {
        ArrayList list = new ArrayList();
        list.add(NAMES[7]);
        list.add(NAMES[8]);
        list.add(NAMES[9]);
        list.add(NAMES[10]);
        list.add(NAMES[11]);
        return (String)list.get(MathUtils.random(list.size() - 1));
    }

    /**
     * @deprecated Method applyUpgrades is deprecated
     */

    public void applyUpgrades(com.megacrit.cardcrawl.cards.AbstractCard.CardType type)
    {
        Iterator iterator = coloredCards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == type)
                c.upgrade();
        } while(true);
        iterator = colorlessCards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == type)
                c.upgrade();
        } while(true);
    }

    public void applyDiscount(float multiplier, boolean affectPurge)
    {
        for(Iterator iterator = relics.iterator(); iterator.hasNext();)
        {
            StoreRelic r = (StoreRelic)iterator.next();
            r.price = MathUtils.round((float)r.price * multiplier);
        }

        for(Iterator iterator1 = potions.iterator(); iterator1.hasNext();)
        {
            StorePotion p = (StorePotion)iterator1.next();
            p.price = MathUtils.round((float)p.price * multiplier);
        }

        for(Iterator iterator2 = coloredCards.iterator(); iterator2.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator2.next();
            c.price = MathUtils.round((float)c.price * multiplier);
        }

        for(Iterator iterator3 = colorlessCards.iterator(); iterator3.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator3.next();
            c.price = MathUtils.round((float)c.price * multiplier);
        }

        if(AbstractDungeon.player.hasRelic("Smiling Mask"))
            actualPurgeCost = 50;
        else
        if(affectPurge)
            actualPurgeCost = MathUtils.round((float)purgeCost * multiplier);
    }

    private void initRelics()
    {
        relics.clear();
        relics = new ArrayList();
        for(int i = 0; i < 3; i++)
        {
            AbstractRelic tempRelic = null;
            if(i != 2)
                tempRelic = AbstractDungeon.returnRandomRelicEnd(rollRelicTier());
            else
                tempRelic = AbstractDungeon.returnRandomRelicEnd(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SHOP);
            StoreRelic relic = new StoreRelic(tempRelic, i, this);
            if(!Settings.isDailyRun)
                relic.price = MathUtils.round((float)relic.price * AbstractDungeon.merchantRng.random(0.95F, 1.05F));
            relics.add(relic);
        }

    }

    private void initPotions()
    {
        potions.clear();
        potions = new ArrayList();
        for(int i = 0; i < 3; i++)
        {
            StorePotion potion = new StorePotion(AbstractDungeon.returnRandomPotion(), i, this);
            if(!Settings.isDailyRun)
                potion.price = MathUtils.round((float)potion.price * AbstractDungeon.merchantRng.random(0.95F, 1.05F));
            potions.add(potion);
        }

    }

    public void getNewPrice(StoreRelic r)
    {
        int retVal = r.price;
        if(!Settings.isDailyRun)
            retVal = MathUtils.round((float)retVal * AbstractDungeon.merchantRng.random(0.95F, 1.05F));
        if(AbstractDungeon.player.hasRelic("The Courier"))
            retVal = applyDiscountToRelic(retVal, 0.8F);
        if(AbstractDungeon.player.hasRelic("Membership Card"))
            retVal = applyDiscountToRelic(retVal, 0.5F);
        r.price = retVal;
    }

    public void getNewPrice(StorePotion r)
    {
        int retVal = r.price;
        if(!Settings.isDailyRun)
            retVal = MathUtils.round((float)retVal * AbstractDungeon.merchantRng.random(0.95F, 1.05F));
        if(AbstractDungeon.player.hasRelic("The Courier"))
            retVal = applyDiscountToRelic(retVal, 0.8F);
        if(AbstractDungeon.player.hasRelic("Membership Card"))
            retVal = applyDiscountToRelic(retVal, 0.5F);
        r.price = retVal;
    }

    private int applyDiscountToRelic(int price, float multiplier)
    {
        return MathUtils.round((float)price * multiplier);
    }

    public static com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier rollRelicTier()
    {
        int roll = AbstractDungeon.merchantRng.random(99);
        logger.info((new StringBuilder()).append("ROLL ").append(roll).toString());
        if(roll < 48)
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON;
        if(roll < 82)
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON;
        else
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE;
    }

    private void setStartingCardPositions()
    {
        int tmp = (int)((float)Settings.WIDTH - DRAW_START_X * 2.0F - AbstractCard.IMG_WIDTH_S * 5F) / 4;
        float padX = (float)(int)((float)tmp + AbstractCard.IMG_WIDTH_S) + 10F * Settings.scale;
        for(int i = 0; i < coloredCards.size(); i++)
        {
            ((AbstractCard)coloredCards.get(i)).updateHoverLogic();
            ((AbstractCard)coloredCards.get(i)).targetDrawScale = 0.75F;
            ((AbstractCard)coloredCards.get(i)).current_x = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * (float)i;
            ((AbstractCard)coloredCards.get(i)).target_x = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * (float)i;
            ((AbstractCard)coloredCards.get(i)).target_y = 9999F * Settings.scale;
            ((AbstractCard)coloredCards.get(i)).current_y = 9999F * Settings.scale;
        }

        for(int i = 0; i < colorlessCards.size(); i++)
        {
            ((AbstractCard)colorlessCards.get(i)).updateHoverLogic();
            ((AbstractCard)colorlessCards.get(i)).targetDrawScale = 0.75F;
            ((AbstractCard)colorlessCards.get(i)).current_x = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * (float)i;
            ((AbstractCard)colorlessCards.get(i)).target_x = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * (float)i;
            ((AbstractCard)colorlessCards.get(i)).target_y = 9999F * Settings.scale;
            ((AbstractCard)colorlessCards.get(i)).current_y = 9999F * Settings.scale;
        }

    }

    public void open()
    {
        resetTouchscreenVars();
        CardCrawlGame.sound.play("SHOP_OPEN");
        setStartingCardPositions();
        purgeCardY = -1000F;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.SHOP;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.cancelButton.show(NAMES[12]);
        StoreRelic r;
        for(Iterator iterator = relics.iterator(); iterator.hasNext(); r.hide())
            r = (StoreRelic)iterator.next();

        StorePotion p;
        for(Iterator iterator1 = potions.iterator(); iterator1.hasNext(); p.hide())
            p = (StorePotion)iterator1.next();

        rugY = Settings.HEIGHT;
        handX = (float)Settings.WIDTH / 2.0F;
        handY = Settings.HEIGHT;
        handTargetX = handX;
        handTargetY = handY;
        handTimer = 1.0F;
        speechTimer = 1.5F;
        speechBubble = null;
        dialogTextEffect = null;
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractCard c;
        for(Iterator iterator2 = coloredCards.iterator(); iterator2.hasNext(); UnlockTracker.markCardAsSeen(c.cardID))
            c = (AbstractCard)iterator2.next();

        AbstractCard c;
        for(Iterator iterator3 = colorlessCards.iterator(); iterator3.hasNext(); UnlockTracker.markCardAsSeen(c.cardID))
            c = (AbstractCard)iterator3.next();

        Iterator iterator4 = relics.iterator();
        do
        {
            if(!iterator4.hasNext())
                break;
            StoreRelic r = (StoreRelic)iterator4.next();
            if(r.relic != null)
                UnlockTracker.markRelicAsSeen(r.relic.relicId);
        } while(true);
        if(ModHelper.isModEnabled("Hoarder"))
            purgeAvailable = false;
    }

    private void resetTouchscreenVars()
    {
        if(Settings.isTouchScreen)
        {
            confirmButton.hide();
            confirmButton.isDisabled = false;
            touchRelic = null;
            touchCard = null;
            touchPotion = null;
            touchPurge = false;
        }
    }

    public void update()
    {
        if(Settings.isTouchScreen)
        {
            confirmButton.update();
            if(InputHelper.justClickedLeft && confirmButton.hb.hovered)
                confirmButton.hb.clickStarted = true;
            if(InputHelper.justReleasedClickLeft && !confirmButton.hb.hovered)
                resetTouchscreenVars();
            else
            if(confirmButton.hb.clicked)
            {
                confirmButton.hb.clicked = false;
                if(touchRelic != null)
                    touchRelic.purchaseRelic();
                else
                if(touchCard != null)
                    purchaseCard(touchCard);
                else
                if(touchPotion != null)
                    touchPotion.purchasePotion();
                else
                if(touchPurge)
                    purchasePurge();
                resetTouchscreenVars();
            }
        }
        if(handTimer != 0.0F)
        {
            handTimer -= Gdx.graphics.getDeltaTime();
            if(handTimer < 0.0F)
                handTimer = 0.0F;
        }
        f_effect.update();
        somethingHovered = false;
        updateControllerInput();
        updatePurgeCard();
        updatePurge();
        updateRelics();
        updatePotions();
        updateRug();
        updateSpeech();
        updateCards();
        updateHand();
        AbstractCard hoveredCard = null;
        Iterator iterator = coloredCards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.hb.hovered)
                continue;
            hoveredCard = c;
            somethingHovered = true;
            moveHand(c.current_x - AbstractCard.IMG_WIDTH / 2.0F, c.current_y);
            break;
        } while(true);
        iterator = colorlessCards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.hb.hovered)
                continue;
            hoveredCard = c;
            somethingHovered = true;
            moveHand(c.current_x - AbstractCard.IMG_WIDTH / 2.0F, c.current_y);
            break;
        } while(true);
        if(!somethingHovered)
        {
            notHoveredTimer += Gdx.graphics.getDeltaTime();
            if(notHoveredTimer > 1.0F)
                handTargetY = Settings.HEIGHT;
        } else
        {
            notHoveredTimer = 0.0F;
        }
        if(hoveredCard != null && InputHelper.justClickedLeft)
            hoveredCard.hb.clickStarted = true;
        if(hoveredCard != null && (InputHelper.justClickedRight || CInputActionSet.proceed.isJustPressed()))
        {
            InputHelper.justClickedRight = false;
            CardCrawlGame.cardPopup.open(hoveredCard);
        }
        if(hoveredCard != null && (hoveredCard.hb.clicked || CInputActionSet.select.isJustPressed()))
        {
            hoveredCard.hb.clicked = false;
            if(!Settings.isTouchScreen)
                purchaseCard(hoveredCard);
            else
            if(touchCard == null)
                if(AbstractDungeon.player.gold < hoveredCard.price)
                {
                    speechTimer = MathUtils.random(40F, 60F);
                    playCantBuySfx();
                    createSpeech(getCantBuyMsg());
                } else
                {
                    confirmButton.hideInstantly();
                    confirmButton.show();
                    confirmButton.isDisabled = false;
                    confirmButton.hb.clickStarted = false;
                    touchCard = hoveredCard;
                }
        }
    }

    private void purchaseCard(AbstractCard hoveredCard)
    {
        if(AbstractDungeon.player.gold >= hoveredCard.price)
        {
            CardCrawlGame.metricData.addShopPurchaseData(hoveredCard.getMetricID());
            AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(hoveredCard, hoveredCard.current_x, hoveredCard.current_y));
            AbstractDungeon.player.loseGold(hoveredCard.price);
            CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1F);
            if(AbstractDungeon.player.hasRelic("The Courier"))
            {
                if(hoveredCard.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS)
                {
                    com.megacrit.cardcrawl.cards.AbstractCard.CardRarity tempRarity = com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON;
                    if(AbstractDungeon.merchantRng.random() < AbstractDungeon.colorlessRareChance)
                        tempRarity = com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE;
                    AbstractCard c = AbstractDungeon.getColorlessCardFromPool(tempRarity).makeCopy();
                    AbstractRelic r;
                    for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onPreviewObtainCard(c))
                        r = (AbstractRelic)iterator1.next();

                    c.current_x = hoveredCard.current_x;
                    c.current_y = hoveredCard.current_y;
                    c.target_x = c.current_x;
                    c.target_y = c.current_y;
                    setPrice(c);
                    colorlessCards.set(colorlessCards.indexOf(hoveredCard), c);
                } else
                {
                    AbstractCard c;
                    for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), hoveredCard.type, false).makeCopy(); c.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), hoveredCard.type, false).makeCopy());
                    AbstractRelic r;
                    for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onPreviewObtainCard(c))
                        r = (AbstractRelic)iterator.next();

                    c.current_x = hoveredCard.current_x;
                    c.current_y = hoveredCard.current_y;
                    c.target_x = c.current_x;
                    c.target_y = c.current_y;
                    setPrice(c);
                    coloredCards.set(coloredCards.indexOf(hoveredCard), c);
                }
            } else
            {
                coloredCards.remove(hoveredCard);
                colorlessCards.remove(hoveredCard);
            }
            hoveredCard = null;
            InputHelper.justClickedLeft = false;
            notHoveredTimer = 1.0F;
            speechTimer = MathUtils.random(40F, 60F);
            playBuySfx();
            createSpeech(getBuyMsg());
        } else
        {
            speechTimer = MathUtils.random(40F, 60F);
            playCantBuySfx();
            createSpeech(getCantBuyMsg());
        }
    }

    private void updateCards()
    {
        for(int i = 0; i < coloredCards.size(); i++)
        {
            ((AbstractCard)coloredCards.get(i)).update();
            ((AbstractCard)coloredCards.get(i)).updateHoverLogic();
            ((AbstractCard)coloredCards.get(i)).current_y = rugY + TOP_ROW_Y;
            ((AbstractCard)coloredCards.get(i)).target_y = ((AbstractCard)coloredCards.get(i)).current_y;
        }

        for(int i = 0; i < colorlessCards.size(); i++)
        {
            ((AbstractCard)colorlessCards.get(i)).update();
            ((AbstractCard)colorlessCards.get(i)).updateHoverLogic();
            ((AbstractCard)colorlessCards.get(i)).current_y = rugY + BOTTOM_ROW_Y;
            ((AbstractCard)colorlessCards.get(i)).target_y = ((AbstractCard)colorlessCards.get(i)).current_y;
        }

    }

    private void setPrice(AbstractCard card)
    {
        float tmpPrice = (float)AbstractCard.getPrice(card.rarity) * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
        if(card.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS)
            tmpPrice *= 1.2F;
        if(AbstractDungeon.player.hasRelic("The Courier"))
            tmpPrice *= 0.8F;
        if(AbstractDungeon.player.hasRelic("Membership Card"))
            tmpPrice *= 0.5F;
        card.price = (int)tmpPrice;
    }

    public void moveHand(float x, float y)
    {
        handTargetX = x - 50F * Settings.xScale;
        handTargetY = y + 90F * Settings.yScale;
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || AbstractDungeon.topPanel.selectPotionMode || !AbstractDungeon.topPanel.potionUi.isHidden || AbstractDungeon.player.viewingRelics)
            return;
        StoreSelectionType type = null;
        int index = 0;
        Iterator iterator = coloredCards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.hb.hovered)
            {
                type = StoreSelectionType.COLOR_CARD;
                break;
            }
            index++;
        } while(true);
        if(type == null)
        {
            index = 0;
            Iterator iterator1 = relics.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                StoreRelic r = (StoreRelic)iterator1.next();
                if(r.relic.hb.hovered)
                {
                    type = StoreSelectionType.RELIC;
                    break;
                }
                index++;
            } while(true);
        }
        if(type == null)
        {
            index = 0;
            Iterator iterator2 = colorlessCards.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator2.next();
                if(c.hb.hovered)
                {
                    type = StoreSelectionType.COLORLESS_CARD;
                    break;
                }
                index++;
            } while(true);
        }
        if(type == null)
        {
            index = 0;
            Iterator iterator3 = potions.iterator();
            do
            {
                if(!iterator3.hasNext())
                    break;
                StorePotion p = (StorePotion)iterator3.next();
                if(p.potion.hb.hovered)
                {
                    type = StoreSelectionType.POTION;
                    break;
                }
                index++;
            } while(true);
        }
        if(type == null && purgeHovered)
            type = StoreSelectionType.PURGE;
        if(type == null)
        {
            if(!coloredCards.isEmpty())
                Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(0)).hb.cY);
            else
            if(!colorlessCards.isEmpty())
                Gdx.input.setCursorPosition((int)((AbstractCard)colorlessCards.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)colorlessCards.get(0)).hb.cY);
            else
            if(!relics.isEmpty())
                Gdx.input.setCursorPosition((int)((StoreRelic)relics.get(0)).relic.hb.cX, Settings.HEIGHT - (int)((StoreRelic)relics.get(0)).relic.hb.cY);
            else
            if(!potions.isEmpty())
                Gdx.input.setCursorPosition((int)((StorePotion)potions.get(0)).potion.hb.cX, Settings.HEIGHT - (int)((StorePotion)potions.get(0)).potion.hb.cY);
            else
            if(purgeAvailable)
                Gdx.input.setCursorPosition((int)purgeCardX, Settings.HEIGHT - (int)purgeCardY);
        } else
        {
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.shop.ShopScreen.StoreSelectionType[type.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = 0;
                    Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(index)).hb.cY);
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    if(++index > coloredCards.size() - 1)
                        index--;
                    Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(index)).hb.cY);
                    break;
                }
                if(!CInputActionSet.down.isJustPressed() && !CInputActionSet.altDown.isJustPressed())
                    break;
                if(((AbstractCard)coloredCards.get(index)).hb.cX < 550F * Settings.scale && !colorlessCards.isEmpty())
                {
                    Gdx.input.setCursorPosition((int)((AbstractCard)colorlessCards.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)colorlessCards.get(0)).hb.cY);
                    return;
                }
                if(((AbstractCard)coloredCards.get(index)).hb.cX < 850F * Settings.scale)
                {
                    if(!colorlessCards.isEmpty())
                    {
                        if(colorlessCards.size() > 1)
                            Gdx.input.setCursorPosition((int)((AbstractCard)colorlessCards.get(1)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)colorlessCards.get(1)).hb.cY);
                        else
                            Gdx.input.setCursorPosition((int)((AbstractCard)colorlessCards.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)colorlessCards.get(0)).hb.cY);
                        return;
                    }
                    if(!relics.isEmpty())
                    {
                        Gdx.input.setCursorPosition((int)((StoreRelic)relics.get(0)).relic.hb.cX, Settings.HEIGHT - (int)((StoreRelic)relics.get(0)).relic.hb.cY);
                        return;
                    }
                    if(!potions.isEmpty())
                        Gdx.input.setCursorPosition((int)((StorePotion)potions.get(0)).potion.hb.cX, Settings.HEIGHT - (int)((StorePotion)potions.get(0)).potion.hb.cY);
                    else
                    if(purgeAvailable)
                    {
                        Gdx.input.setCursorPosition((int)purgeCardX, Settings.HEIGHT - (int)purgeCardY);
                        return;
                    }
                }
                if(((AbstractCard)coloredCards.get(index)).hb.cX < 1400F * Settings.scale)
                {
                    if(!relics.isEmpty())
                    {
                        Gdx.input.setCursorPosition((int)((StoreRelic)relics.get(0)).relic.hb.cX, Settings.HEIGHT - (int)((StoreRelic)relics.get(0)).relic.hb.cY);
                        return;
                    }
                    if(!potions.isEmpty())
                        Gdx.input.setCursorPosition((int)((StorePotion)potions.get(0)).potion.hb.cX, Settings.HEIGHT - (int)((StorePotion)potions.get(0)).potion.hb.cY);
                }
                Gdx.input.setCursorPosition((int)purgeCardX, Settings.HEIGHT - (int)purgeCardY);
                break;

            case 2: // '\002'
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = 0;
                    Gdx.input.setCursorPosition((int)((AbstractCard)colorlessCards.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)colorlessCards.get(index)).hb.cY);
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    if(++index > colorlessCards.size() - 1)
                    {
                        if(!relics.isEmpty())
                        {
                            Gdx.input.setCursorPosition((int)((StoreRelic)relics.get(0)).relic.hb.cX, Settings.HEIGHT - (int)((StoreRelic)relics.get(0)).relic.hb.cY);
                            break;
                        }
                        if(!potions.isEmpty())
                        {
                            Gdx.input.setCursorPosition((int)((StorePotion)potions.get(0)).potion.hb.cX, Settings.HEIGHT - (int)((StorePotion)potions.get(0)).potion.hb.cY);
                            break;
                        }
                        if(purgeAvailable)
                            Gdx.input.setCursorPosition((int)purgeCardX, Settings.HEIGHT - (int)purgeCardY);
                        else
                            index = 0;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractCard)colorlessCards.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)colorlessCards.get(index)).hb.cY);
                    }
                    break;
                }
                if(!CInputActionSet.up.isJustPressed() && !CInputActionSet.altUp.isJustPressed() || coloredCards.isEmpty())
                    break;
                if(((AbstractCard)colorlessCards.get(index)).hb.cX < 550F * Settings.scale)
                {
                    Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(0)).hb.cY);
                    break;
                }
                if(coloredCards.size() > 1)
                    Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(1)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(1)).hb.cY);
                else
                    Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(0)).hb.cY);
                break;

            case 3: // '\003'
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0 && !colorlessCards.isEmpty())
                    {
                        Gdx.input.setCursorPosition((int)((AbstractCard)colorlessCards.get(colorlessCards.size() - 1)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)colorlessCards.get(colorlessCards.size() - 1)).hb.cY);
                        break;
                    }
                    if(index < 0)
                        index = 0;
                    Gdx.input.setCursorPosition((int)((StoreRelic)relics.get(index)).relic.hb.cX, Settings.HEIGHT - (int)((StoreRelic)relics.get(index)).relic.hb.cY);
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    if(++index > relics.size() - 1 && purgeAvailable)
                    {
                        Gdx.input.setCursorPosition((int)purgeCardX, Settings.HEIGHT - (int)purgeCardY);
                        break;
                    }
                    if(index <= relics.size() - 1)
                        Gdx.input.setCursorPosition((int)((StoreRelic)relics.get(index)).relic.hb.cX, Settings.HEIGHT - (int)((StoreRelic)relics.get(index)).relic.hb.cY);
                    break;
                }
                if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && !potions.isEmpty())
                {
                    if(potions.size() - 1 >= index)
                        Gdx.input.setCursorPosition((int)((StorePotion)potions.get(index)).potion.hb.cX, Settings.HEIGHT - (int)((StorePotion)potions.get(index)).potion.hb.cY);
                    else
                        Gdx.input.setCursorPosition((int)((StorePotion)potions.get(0)).potion.hb.cX, Settings.HEIGHT - (int)((StorePotion)potions.get(0)).potion.hb.cY);
                    break;
                }
                if(!CInputActionSet.up.isJustPressed() && !CInputActionSet.altUp.isJustPressed() || coloredCards.isEmpty())
                    break;
                if(coloredCards.size() > 3)
                    Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(2)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(2)).hb.cY);
                else
                    Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(0)).hb.cY);
                break;

            case 4: // '\004'
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0 && !colorlessCards.isEmpty())
                    {
                        Gdx.input.setCursorPosition((int)((AbstractCard)colorlessCards.get(colorlessCards.size() - 1)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)colorlessCards.get(colorlessCards.size() - 1)).hb.cY);
                        break;
                    }
                    if(index < 0)
                        index = 0;
                    Gdx.input.setCursorPosition((int)((StorePotion)potions.get(index)).potion.hb.cX, Settings.HEIGHT - (int)((StorePotion)potions.get(index)).potion.hb.cY);
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    if(++index > potions.size() - 1 && purgeAvailable)
                    {
                        Gdx.input.setCursorPosition((int)purgeCardX, Settings.HEIGHT - (int)purgeCardY);
                        break;
                    }
                    if(index <= potions.size() - 1)
                        Gdx.input.setCursorPosition((int)((StorePotion)potions.get(index)).potion.hb.cX, Settings.HEIGHT - (int)((StorePotion)potions.get(index)).potion.hb.cY);
                    break;
                }
                if(!CInputActionSet.up.isJustPressed() && !CInputActionSet.altUp.isJustPressed())
                    break;
                if(!relics.isEmpty())
                {
                    if(relics.size() - 1 >= index)
                        Gdx.input.setCursorPosition((int)((StoreRelic)relics.get(index)).relic.hb.cX, Settings.HEIGHT - (int)((StoreRelic)relics.get(index)).relic.hb.cY);
                    else
                        Gdx.input.setCursorPosition((int)((StoreRelic)relics.get(0)).relic.hb.cX, Settings.HEIGHT - (int)((StoreRelic)relics.get(0)).relic.hb.cY);
                    break;
                }
                if(coloredCards.isEmpty())
                    break;
                if(coloredCards.size() > 3)
                    Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(2)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(2)).hb.cY);
                else
                    Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(0)).hb.cY);
                break;

            case 5: // '\005'
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(!relics.isEmpty())
                    {
                        Gdx.input.setCursorPosition((int)((StoreRelic)relics.get(relics.size() - 1)).relic.hb.cX, Settings.HEIGHT - (int)((StoreRelic)relics.get(relics.size() - 1)).relic.hb.cY);
                        break;
                    }
                    if(!potions.isEmpty())
                    {
                        Gdx.input.setCursorPosition((int)((StorePotion)potions.get(potions.size() - 1)).potion.hb.cX, Settings.HEIGHT - (int)((StorePotion)potions.get(potions.size() - 1)).potion.hb.cY);
                        break;
                    }
                    if(colorlessCards.isEmpty())
                        Gdx.input.setCursorPosition((int)((AbstractCard)colorlessCards.get(colorlessCards.size() - 1)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)colorlessCards.get(colorlessCards.size() - 1)).hb.cY);
                    break;
                }
                if((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && !coloredCards.isEmpty())
                    Gdx.input.setCursorPosition((int)((AbstractCard)coloredCards.get(coloredCards.size() - 1)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)coloredCards.get(coloredCards.size() - 1)).hb.cY);
                break;
            }
        }
    }

    private void updatePurgeCard()
    {
        purgeCardX = 1554F * Settings.xScale;
        purgeCardY = rugY + BOTTOM_ROW_Y;
        if(purgeAvailable)
        {
            float CARD_W = 110F * Settings.scale;
            float CARD_H = 150F * Settings.scale;
            if((float)InputHelper.mX > purgeCardX - CARD_W && (float)InputHelper.mX < purgeCardX + CARD_W && (float)InputHelper.mY > purgeCardY - CARD_H && (float)InputHelper.mY < purgeCardY + CARD_H)
            {
                purgeHovered = true;
                moveHand(purgeCardX - AbstractCard.IMG_WIDTH / 2.0F, purgeCardY);
                somethingHovered = true;
                purgeCardScale = Settings.scale;
            } else
            {
                purgeHovered = false;
            }
            if(!purgeHovered)
            {
                purgeCardScale = MathHelper.cardScaleLerpSnap(purgeCardScale, 0.75F * Settings.scale);
            } else
            {
                if(InputHelper.justReleasedClickLeft || CInputActionSet.select.isJustPressed())
                    if(!Settings.isTouchScreen)
                    {
                        CInputActionSet.select.unpress();
                        purchasePurge();
                    } else
                    if(!touchPurge)
                        if(AbstractDungeon.player.gold < actualPurgeCost)
                        {
                            playCantBuySfx();
                            createSpeech(getCantBuyMsg());
                        } else
                        {
                            confirmButton.hideInstantly();
                            confirmButton.show();
                            confirmButton.hb.clickStarted = false;
                            confirmButton.isDisabled = false;
                            touchPurge = true;
                        }
                TipHelper.renderGenericTip((float)InputHelper.mX - 360F * Settings.scale, (float)InputHelper.mY - 70F * Settings.scale, LABEL[0], (new StringBuilder()).append(MSG[0]).append(25).append(MSG[1]).toString());
            }
        } else
        {
            purgeCardScale = MathHelper.cardScaleLerpSnap(purgeCardScale, 0.75F * Settings.scale);
        }
    }

    private void purchasePurge()
    {
        purgeHovered = false;
        if(AbstractDungeon.player.gold >= actualPurgeCost)
        {
            AbstractDungeon.previousScreen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.SHOP;
            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, NAMES[13], false, false, true, true);
        } else
        {
            playCantBuySfx();
            createSpeech(getCantBuyMsg());
        }
    }

    private void updateRelics()
    {
        Iterator i = relics.iterator();
        do
        {
            if(!i.hasNext())
                break;
            StoreRelic r = (StoreRelic)i.next();
            if(Settings.isFourByThree)
                r.update(rugY + 50F * Settings.yScale);
            else
                r.update(rugY);
            if(!r.isPurchased)
                continue;
            i.remove();
            break;
        } while(true);
    }

    private void updatePotions()
    {
        Iterator i = potions.iterator();
        do
        {
            if(!i.hasNext())
                break;
            StorePotion p = (StorePotion)i.next();
            if(Settings.isFourByThree)
                p.update(rugY + 50F * Settings.scale);
            else
                p.update(rugY);
            if(!p.isPurchased)
                continue;
            i.remove();
            break;
        } while(true);
    }

    public void createSpeech(String msg)
    {
        boolean isRight = MathUtils.randomBoolean();
        float x = MathUtils.random(660F, 1260F) * Settings.scale;
        float y = (float)Settings.HEIGHT - 380F * Settings.scale;
        speechBubble = new ShopSpeechBubble(x, y, 4F, msg, isRight);
        float offset_x = 0.0F;
        if(isRight)
            offset_x = SPEECH_TEXT_R_X;
        else
            offset_x = SPEECH_TEXT_L_X;
        dialogTextEffect = new SpeechTextEffect(x + offset_x, y + SPEECH_TEXT_Y, 4F, msg, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.BUMP_IN);
    }

    private void updateSpeech()
    {
        if(speechBubble != null)
        {
            speechBubble.update();
            if(speechBubble.hb.hovered && speechBubble.duration > 0.3F)
            {
                speechBubble.duration = 0.3F;
                dialogTextEffect.duration = 0.3F;
            }
            if(speechBubble.isDone)
                speechBubble = null;
        }
        if(dialogTextEffect != null)
        {
            dialogTextEffect.update();
            if(dialogTextEffect.isDone)
                dialogTextEffect = null;
        }
        speechTimer -= Gdx.graphics.getDeltaTime();
        if(speechBubble == null && dialogTextEffect == null && speechTimer <= 0.0F)
        {
            speechTimer = MathUtils.random(40F, 60F);
            if(!saidWelcome)
            {
                createSpeech(WELCOME_MSG);
                saidWelcome = true;
                welcomeSfx();
            } else
            {
                playMiscSfx();
                createSpeech(getIdleMsg());
            }
        }
    }

    private void welcomeSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_MERCHANT_3A");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_MERCHANT_3B");
        else
            CardCrawlGame.sound.play("VO_MERCHANT_3C");
    }

    private void playMiscSfx()
    {
        int roll = MathUtils.random(5);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_MERCHANT_MA");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_MERCHANT_MB");
        else
        if(roll == 2)
            CardCrawlGame.sound.play("VO_MERCHANT_MC");
        else
        if(roll == 3)
            CardCrawlGame.sound.play("VO_MERCHANT_3A");
        else
        if(roll == 4)
            CardCrawlGame.sound.play("VO_MERCHANT_3B");
        else
            CardCrawlGame.sound.play("VO_MERCHANT_3C");
    }

    public void playBuySfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_MERCHANT_KA");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_MERCHANT_KB");
        else
            CardCrawlGame.sound.play("VO_MERCHANT_KC");
    }

    public void playCantBuySfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_MERCHANT_2A");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_MERCHANT_2B");
        else
            CardCrawlGame.sound.play("VO_MERCHANT_2C");
    }

    private String getIdleMsg()
    {
        return (String)idleMessages.get(MathUtils.random(idleMessages.size() - 1));
    }

    private void updateRug()
    {
        if(rugY != 0.0F)
        {
            rugY = MathUtils.lerp(rugY, (float)Settings.HEIGHT / 2.0F - 540F * Settings.yScale, Gdx.graphics.getDeltaTime() * 5F);
            if(Math.abs(rugY - 0.0F) < 0.5F)
                rugY = 0.0F;
        }
    }

    private void updateHand()
    {
        if(handTimer == 0.0F)
        {
            if(handX != handTargetX)
                handX = MathUtils.lerp(handX, handTargetX, Gdx.graphics.getDeltaTime() * 6F);
            if(handY != handTargetY)
                if(handY > handTargetY)
                    handY = MathUtils.lerp(handY, handTargetY, Gdx.graphics.getDeltaTime() * 6F);
                else
                    handY = MathUtils.lerp(handY, handTargetY, (Gdx.graphics.getDeltaTime() * 6F) / 4F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(rugImg, 0.0F, rugY, Settings.WIDTH, Settings.HEIGHT);
        renderCardsAndPrices(sb);
        renderRelics(sb);
        renderPotions(sb);
        renderPurge(sb);
        sb.draw(handImg, handX + f_effect.x, handY + f_effect.y, HAND_W, HAND_H);
        if(speechBubble != null)
            speechBubble.render(sb);
        if(dialogTextEffect != null)
            dialogTextEffect.render(sb);
        if(Settings.isTouchScreen)
            confirmButton.render(sb);
    }

    private void renderRelics(SpriteBatch sb)
    {
        StoreRelic r;
        for(Iterator iterator = relics.iterator(); iterator.hasNext(); r.render(sb))
            r = (StoreRelic)iterator.next();

    }

    private void renderPotions(SpriteBatch sb)
    {
        StorePotion p;
        for(Iterator iterator = potions.iterator(); iterator.hasNext(); p.render(sb))
            p = (StorePotion)iterator.next();

    }

    private void renderCardsAndPrices(SpriteBatch sb)
    {
        AbstractCard c;
        Color color;
        for(Iterator iterator = coloredCards.iterator(); iterator.hasNext(); FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, Integer.toString(c.price), c.current_x + PRICE_TEXT_OFFSET_X, (c.current_y + PRICE_TEXT_OFFSET_Y) - (c.drawScale - 0.75F) * 200F * Settings.scale, color))
        {
            c = (AbstractCard)iterator.next();
            c.render(sb);
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.UI_GOLD, c.current_x + GOLD_IMG_OFFSET_X, (c.current_y + GOLD_IMG_OFFSET_Y) - (c.drawScale - 0.75F) * 200F * Settings.scale, GOLD_IMG_WIDTH, GOLD_IMG_WIDTH);
            color = Color.WHITE.cpy();
            if(c.price > AbstractDungeon.player.gold)
            {
                color = Color.SALMON.cpy();
                continue;
            }
            if(c.equals(saleTag.card))
                color = Color.SKY.cpy();
        }

        AbstractCard c;
        Color color;
        for(Iterator iterator1 = colorlessCards.iterator(); iterator1.hasNext(); FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, Integer.toString(c.price), c.current_x + PRICE_TEXT_OFFSET_X, (c.current_y + PRICE_TEXT_OFFSET_Y) - (c.drawScale - 0.75F) * 200F * Settings.scale, color))
        {
            c = (AbstractCard)iterator1.next();
            c.render(sb);
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.UI_GOLD, c.current_x + GOLD_IMG_OFFSET_X, (c.current_y + GOLD_IMG_OFFSET_Y) - (c.drawScale - 0.75F) * 200F * Settings.scale, GOLD_IMG_WIDTH, GOLD_IMG_WIDTH);
            color = Color.WHITE.cpy();
            if(c.price > AbstractDungeon.player.gold)
            {
                color = Color.SALMON.cpy();
                continue;
            }
            if(c.equals(saleTag.card))
                color = Color.SKY.cpy();
        }

        if(coloredCards.contains(saleTag.card))
            saleTag.render(sb);
        if(colorlessCards.contains(saleTag.card))
            saleTag.render(sb);
        AbstractCard c;
        for(Iterator iterator2 = coloredCards.iterator(); iterator2.hasNext(); c.renderCardTip(sb))
            c = (AbstractCard)iterator2.next();

        AbstractCard c;
        for(Iterator iterator3 = colorlessCards.iterator(); iterator3.hasNext(); c.renderCardTip(sb))
            c = (AbstractCard)iterator3.next();

    }

    private void renderPurge(SpriteBatch sb)
    {
        sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
        com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion tmpImg = ImageMaster.CARD_SKILL_BG_SILHOUETTE;
        sb.draw(tmpImg, (purgeCardX + 18F * Settings.scale + tmpImg.offsetX) - (float)tmpImg.originalWidth / 2.0F, ((purgeCardY - 14F * Settings.scale) + tmpImg.offsetY) - (float)tmpImg.originalHeight / 2.0F, (float)tmpImg.originalWidth / 2.0F - tmpImg.offsetX, (float)tmpImg.originalHeight / 2.0F - tmpImg.offsetY, tmpImg.packedWidth, tmpImg.packedHeight, purgeCardScale, purgeCardScale, 0.0F);
        sb.setColor(Color.WHITE);
        if(purgeAvailable)
        {
            sb.draw(removeServiceImg, purgeCardX - 256F, purgeCardY - 256F, 256F, 256F, 512F, 512F, purgeCardScale, purgeCardScale, 0.0F, 0, 0, 512, 512, false, false);
            sb.draw(ImageMaster.UI_GOLD, purgeCardX + GOLD_IMG_OFFSET_X, (purgeCardY + GOLD_IMG_OFFSET_Y) - (purgeCardScale / Settings.scale - 0.75F) * 200F * Settings.scale, GOLD_IMG_WIDTH, GOLD_IMG_WIDTH);
            Color color = Color.WHITE;
            if(actualPurgeCost > AbstractDungeon.player.gold)
                color = Color.SALMON;
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, Integer.toString(actualPurgeCost), purgeCardX + PRICE_TEXT_OFFSET_X, (purgeCardY + PRICE_TEXT_OFFSET_Y) - (purgeCardScale / Settings.scale - 0.75F) * 200F * Settings.scale, color);
        } else
        {
            sb.draw(soldOutImg, purgeCardX - 256F, purgeCardY - 256F, 256F, 256F, 512F, 512F, purgeCardScale, purgeCardScale, 0.0F, 0, 0, 512, 512, false, false);
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/shop/ShopScreen.getName());
    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    private static final CharacterStrings characterStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    public boolean isActive;
    private static Texture rugImg = null;
    private static Texture removeServiceImg = null;
    private static Texture soldOutImg = null;
    private static Texture handImg = null;
    private float rugY;
    private static final float RUG_SPEED = 5F;
    public ArrayList coloredCards;
    public ArrayList colorlessCards;
    private static final float DRAW_START_X;
    private static final int NUM_CARDS_PER_LINE = 5;
    private static final float CARD_PRICE_JITTER = 0.1F;
    private static final float TOP_ROW_Y;
    private static final float BOTTOM_ROW_Y;
    private float speechTimer;
    private static final float MIN_IDLE_MSG_TIME = 40F;
    private static final float MAX_IDLE_MSG_TIME = 60F;
    private static final float SPEECH_DURATION = 4F;
    private static final float SPEECH_TEXT_R_X;
    private static final float SPEECH_TEXT_L_X;
    private static final float SPEECH_TEXT_Y;
    private ShopSpeechBubble speechBubble;
    private SpeechTextEffect dialogTextEffect;
    private static final String WELCOME_MSG;
    private ArrayList idleMessages;
    private boolean saidWelcome;
    private boolean somethingHovered;
    private ArrayList relics;
    private static final float RELIC_PRICE_JITTER = 0.05F;
    private ArrayList potions;
    private static final float POTION_PRICE_JITTER = 0.05F;
    public boolean purgeAvailable;
    public static int purgeCost = 75;
    public static int actualPurgeCost = 75;
    private static final int PURGE_COST_RAMP = 25;
    private boolean purgeHovered;
    private float purgeCardX;
    private float purgeCardY;
    private float purgeCardScale;
    private FloatyEffect f_effect;
    private float handTimer;
    private float handX;
    private float handY;
    private float handTargetX;
    private float handTargetY;
    private static final float HAND_SPEED = 6F;
    private static float HAND_W;
    private static float HAND_H;
    private float notHoveredTimer;
    private static final float GOLD_IMG_WIDTH;
    private static final float COLORLESS_PRICE_BUMP = 1.2F;
    private OnSaleTag saleTag;
    private static final float GOLD_IMG_OFFSET_X;
    private static final float GOLD_IMG_OFFSET_Y;
    private static final float PRICE_TEXT_OFFSET_X;
    private static final float PRICE_TEXT_OFFSET_Y;
    public ConfirmButton confirmButton;
    public StoreRelic touchRelic;
    public StorePotion touchPotion;
    private AbstractCard touchCard;
    private boolean touchPurge;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Shop Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Shop Screen");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
        DRAW_START_X = (float)Settings.WIDTH * 0.16F;
        TOP_ROW_Y = 760F * Settings.yScale;
        BOTTOM_ROW_Y = 337F * Settings.yScale;
        SPEECH_TEXT_R_X = 164F * Settings.scale;
        SPEECH_TEXT_L_X = -166F * Settings.scale;
        SPEECH_TEXT_Y = 126F * Settings.scale;
        WELCOME_MSG = NAMES[0];
        GOLD_IMG_WIDTH = (float)ImageMaster.UI_GOLD.getWidth() * Settings.scale;
        GOLD_IMG_OFFSET_X = -50F * Settings.scale;
        GOLD_IMG_OFFSET_Y = -215F * Settings.scale;
        PRICE_TEXT_OFFSET_X = 16F * Settings.scale;
        PRICE_TEXT_OFFSET_Y = -180F * Settings.scale;
    }
}
