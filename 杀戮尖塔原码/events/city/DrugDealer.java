// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DrugDealer.java

package com.megacrit.cardcrawl.events.city;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.JAX;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DrugDealer extends AbstractImageEvent
{

    public DrugDealer()
    {
        super(NAME, DESCRIPTIONS[0], "images/events/drugDealer.jpg");
        screenNum = 0;
        cardsSelected = false;
        imageEventText.setDialogOption(OPTIONS[0], CardLibrary.getCopy("J.A.X."));
        if(AbstractDungeon.player.masterDeck.getPurgeableCards().size() >= 2)
            imageEventText.setDialogOption(OPTIONS[1]);
        else
            imageEventText.setDialogOption(OPTIONS[4], true);
        imageEventText.setDialogOption(OPTIONS[2], new MutagenicStrength());
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                AbstractCard jax = new JAX();
                logMetricObtainCard("Drug Dealer", "Obtain J.A.X.", jax);
                imageEventText.updateBodyText(DESCRIPTIONS[1]);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(jax, Settings.WIDTH / 2, Settings.HEIGHT / 2));
                imageEventText.updateDialogOption(0, OPTIONS[3]);
                imageEventText.clearRemainingOptions();
                break;

            case 1: // '\001'
                imageEventText.updateBodyText(DESCRIPTIONS[2]);
                transform();
                imageEventText.updateDialogOption(0, OPTIONS[3]);
                imageEventText.clearRemainingOptions();
                break;

            case 2: // '\002'
                imageEventText.updateBodyText(DESCRIPTIONS[3]);
                AbstractRelic r;
                if(!AbstractDungeon.player.hasRelic("MutagenicStrength"))
                {
                    r = new MutagenicStrength();
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(drawX, drawY, r);
                } else
                {
                    r = new Circlet();
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(drawX, drawY, r);
                }
                logMetricObtainRelic("Drug Dealer", "Inject Mutagens", r);
                imageEventText.updateDialogOption(0, OPTIONS[3]);
                imageEventText.clearRemainingOptions();
                break;

            default:
                logger.info((new StringBuilder()).append("ERROR: Unhandled case ").append(buttonPressed).toString());
                break;
            }
            screenNum = 1;
            break;

        case 1: // '\001'
            openMap();
            break;
        }
    }

    public void update()
    {
        super.update();
        if(!cardsSelected)
        {
            List transformedCards = new ArrayList();
            List obtainedCards = new ArrayList();
            if(AbstractDungeon.gridSelectScreen.selectedCards.size() == 2)
            {
                cardsSelected = true;
                float displayCount = 0.0F;
                Iterator i = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                do
                {
                    if(!i.hasNext())
                        break;
                    AbstractCard card = (AbstractCard)i.next();
                    card.untip();
                    card.unhover();
                    transformedCards.add(card.cardID);
                    AbstractDungeon.player.masterDeck.removeCard(card);
                    AbstractDungeon.transformCard(card, false, AbstractDungeon.miscRng);
                    AbstractCard c = AbstractDungeon.getTransformedCard();
                    obtainedCards.add(c.cardID);
                    if(AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.TRANSFORM && c != null)
                    {
                        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(c.makeCopy(), (float)Settings.WIDTH / 3F + displayCount, (float)Settings.HEIGHT / 2.0F, false));
                        displayCount += (float)Settings.WIDTH / 6F;
                    }
                } while(true);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                logMetricTransformCards("Drug Dealer", "Became Test Subject", transformedCards, obtainedCards);
                AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
            }
        }
    }

    private void transform()
    {
        if(!AbstractDungeon.isScreenUp)
        {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, OPTIONS[5], false, false, false, false);
        } else
        {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, OPTIONS[5], false, false, false, false);
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/events/city/DrugDealer.getName());
    public static final String ID = "Drug Dealer";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private int screenNum;
    private boolean cardsSelected;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Drug Dealer");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
}
