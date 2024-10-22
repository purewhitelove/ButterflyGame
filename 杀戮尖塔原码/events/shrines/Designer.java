// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Designer.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Designer extends AbstractImageEvent
{
    private static final class OptionChosen extends Enum
    {

        public static OptionChosen[] values()
        {
            return (OptionChosen[])$VALUES.clone();
        }

        public static OptionChosen valueOf(String name)
        {
            return (OptionChosen)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/Designer$OptionChosen, name);
        }

        public static final OptionChosen UPGRADE;
        public static final OptionChosen REMOVE;
        public static final OptionChosen REMOVE_AND_UPGRADE;
        public static final OptionChosen TRANSFORM;
        public static final OptionChosen NONE;
        private static final OptionChosen $VALUES[];

        static 
        {
            UPGRADE = new OptionChosen("UPGRADE", 0);
            REMOVE = new OptionChosen("REMOVE", 1);
            REMOVE_AND_UPGRADE = new OptionChosen("REMOVE_AND_UPGRADE", 2);
            TRANSFORM = new OptionChosen("TRANSFORM", 3);
            NONE = new OptionChosen("NONE", 4);
            $VALUES = (new OptionChosen[] {
                UPGRADE, REMOVE, REMOVE_AND_UPGRADE, TRANSFORM, NONE
            });
        }

        private OptionChosen(String s, int i)
        {
            super(s, i);
        }
    }

    private static final class CurrentScreen extends Enum
    {

        public static CurrentScreen[] values()
        {
            return (CurrentScreen[])$VALUES.clone();
        }

        public static CurrentScreen valueOf(String name)
        {
            return (CurrentScreen)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/Designer$CurrentScreen, name);
        }

        public static final CurrentScreen INTRO;
        public static final CurrentScreen MAIN;
        public static final CurrentScreen DONE;
        private static final CurrentScreen $VALUES[];

        static 
        {
            INTRO = new CurrentScreen("INTRO", 0);
            MAIN = new CurrentScreen("MAIN", 1);
            DONE = new CurrentScreen("DONE", 2);
            $VALUES = (new CurrentScreen[] {
                INTRO, MAIN, DONE
            });
        }

        private CurrentScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public Designer()
    {
        super(NAME, DESC[0], "images/events/designer2.jpg");
        curScreen = CurrentScreen.INTRO;
        option = null;
        imageEventText.setDialogOption(OPTIONS[0]);
        option = OptionChosen.NONE;
        adjustmentUpgradesOne = AbstractDungeon.miscRng.randomBoolean();
        cleanUpRemovesCards = AbstractDungeon.miscRng.randomBoolean();
        if(AbstractDungeon.ascensionLevel >= 15)
        {
            adjustCost = 50;
            cleanUpCost = 75;
            fullServiceCost = 110;
            hpLoss = 5;
        } else
        {
            adjustCost = 40;
            cleanUpCost = 60;
            fullServiceCost = 90;
            hpLoss = 3;
        }
    }

    public void update()
    {
        super.update();
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$OptionChosen[];
            static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$CurrentScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$CurrentScreen = new int[CurrentScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$CurrentScreen[CurrentScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$CurrentScreen[CurrentScreen.MAIN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$CurrentScreen[CurrentScreen.DONE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$OptionChosen = new int[OptionChosen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$OptionChosen[OptionChosen.REMOVE.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$OptionChosen[OptionChosen.REMOVE_AND_UPGRADE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$OptionChosen[OptionChosen.TRANSFORM.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$Designer$OptionChosen[OptionChosen.UPGRADE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
            }
        }

        if(option != OptionChosen.NONE)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.Designer.OptionChosen[option.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                if(!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
                {
                    CardCrawlGame.sound.play("CARD_EXHAUST");
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    logMetricCardRemovalAtCost("Designer", "Single Remove", c, cleanUpCost);
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    option = OptionChosen.NONE;
                }
                break;

            case 2: // '\002'
                if(AbstractDungeon.isScreenUp || AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
                    break;
                AbstractCard removeCard = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                CardCrawlGame.sound.play("CARD_EXHAUST");
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(removeCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH - 20F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.masterDeck.removeCard(removeCard);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                ArrayList upgradableCards = new ArrayList();
                Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    AbstractCard c = (AbstractCard)iterator.next();
                    if(c.canUpgrade())
                        upgradableCards.add(c);
                } while(true);
                Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
                if(!upgradableCards.isEmpty())
                {
                    AbstractCard upgradeCard = (AbstractCard)upgradableCards.get(0);
                    upgradeCard.upgrade();
                    AbstractDungeon.player.bottledCardUpgradeCheck(upgradeCard);
                    AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradeCard.makeStatEquivalentCopy()));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    logMetricCardUpgradeAndRemovalAtCost("Designer", "Upgrade and Remove", upgradeCard, removeCard, fullServiceCost);
                } else
                {
                    logMetricCardRemovalAtCost("Designer", "Removal", removeCard, fullServiceCost);
                }
                option = OptionChosen.NONE;
                break;

            case 3: // '\003'
                if(AbstractDungeon.isScreenUp || AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
                    break;
                List transCards = new ArrayList();
                List obtainedCards = new ArrayList();
                if(AbstractDungeon.gridSelectScreen.selectedCards.size() == 2)
                {
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    transCards.add(c.cardID);
                    AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
                    AbstractCard newCard1 = AbstractDungeon.getTransformedCard();
                    obtainedCards.add(newCard1.cardID);
                    AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(newCard1, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                    c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    transCards.add(c.cardID);
                    AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
                    AbstractCard newCard2 = AbstractDungeon.getTransformedCard();
                    obtainedCards.add(newCard2.cardID);
                    AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(newCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    logMetricTransformCardsAtCost("Designer", "Transformed Cards", transCards, obtainedCards, cleanUpCost);
                } else
                {
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
                    AbstractCard transCard = AbstractDungeon.getTransformedCard();
                    AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(transCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    logMetricTransformCardAtCost("Designer", "Transform", transCard, c, cleanUpCost);
                }
                option = OptionChosen.NONE;
                break;

            case 4: // '\004'
                if(!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
                {
                    logMetricCardUpgradeAtCost("Designer", "Upgrade", (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), adjustCost);
                    ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).upgrade();
                    AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                    AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeStatEquivalentCopy()));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    option = OptionChosen.NONE;
                }
                break;
            }
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.Designer.CurrentScreen[curScreen.ordinal()])
        {
        case 1: // '\001'
            imageEventText.updateBodyText(DESC[1]);
            imageEventText.removeDialogOption(0);
            if(adjustmentUpgradesOne)
                imageEventText.updateDialogOption(0, (new StringBuilder()).append(OPTIONS[1]).append(adjustCost).append(OPTIONS[6]).append(OPTIONS[9]).toString(), AbstractDungeon.player.gold < adjustCost || !AbstractDungeon.player.masterDeck.hasUpgradableCards().booleanValue());
            else
                imageEventText.updateDialogOption(0, (new StringBuilder()).append(OPTIONS[1]).append(adjustCost).append(OPTIONS[6]).append(OPTIONS[7]).append(2).append(OPTIONS[8]).toString(), AbstractDungeon.player.gold < adjustCost || !AbstractDungeon.player.masterDeck.hasUpgradableCards().booleanValue());
            if(cleanUpRemovesCards)
                imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(cleanUpCost).append(OPTIONS[6]).append(OPTIONS[10]).toString(), AbstractDungeon.player.gold < cleanUpCost || CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).size() == 0);
            else
                imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(cleanUpCost).append(OPTIONS[6]).append(OPTIONS[11]).append(2).append(OPTIONS[12]).toString(), AbstractDungeon.player.gold < cleanUpCost || CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).size() < 2);
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[3]).append(fullServiceCost).append(OPTIONS[6]).append(OPTIONS[13]).toString(), AbstractDungeon.player.gold < fullServiceCost || CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).size() == 0);
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[4]).append(hpLoss).append(OPTIONS[5]).toString());
            curScreen = CurrentScreen.MAIN;
            break;

        case 2: // '\002'
            switch(buttonPressed)
            {
            default:
                break;

            case 0: // '\0'
                imageEventText.updateBodyText(DESC[2]);
                AbstractDungeon.player.loseGold(adjustCost);
                if(adjustmentUpgradesOne)
                {
                    option = OptionChosen.UPGRADE;
                    AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[15], true, false, false, false);
                } else
                {
                    upgradeTwoRandomCards();
                }
                break;

            case 1: // '\001'
                imageEventText.updateBodyText(DESC[2]);
                AbstractDungeon.player.loseGold(cleanUpCost);
                if(cleanUpRemovesCards)
                {
                    option = OptionChosen.REMOVE;
                    AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[17], false, false, false, true);
                } else
                {
                    option = OptionChosen.TRANSFORM;
                    AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 2, OPTIONS[16], false, false, false, false);
                }
                break;

            case 2: // '\002'
                imageEventText.updateBodyText(DESC[2]);
                AbstractDungeon.player.loseGold(fullServiceCost);
                option = OptionChosen.REMOVE_AND_UPGRADE;
                AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[17], false, false, false, true);
                break;

            case 3: // '\003'
                imageEventText.loadImage("images/events/designerPunched2.jpg");
                imageEventText.updateBodyText(DESC[3]);
                logMetricTakeDamage("Designer", "Punched", hpLoss);
                CardCrawlGame.sound.play("BLUNT_FAST");
                AbstractDungeon.player.damage(new DamageInfo(null, hpLoss, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
                break;
            }
            imageEventText.updateDialogOption(0, OPTIONS[14]);
            imageEventText.clearRemainingOptions();
            curScreen = CurrentScreen.DONE;
            break;

        case 3: // '\003'
        default:
            openMap();
            break;
        }
    }

    private void upgradeTwoRandomCards()
    {
        ArrayList upgradableCards = new ArrayList();
        Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.canUpgrade())
                upgradableCards.add(c);
        } while(true);
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if(upgradableCards.isEmpty())
            logMetricLoseGold("Designer", "Tried to Upgrade", adjustCost);
        else
        if(upgradableCards.size() == 1)
        {
            ((AbstractCard)upgradableCards.get(0)).upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            logMetricCardUpgradeAtCost("Designer", "Tried to Upgrade", (AbstractCard)upgradableCards.get(0), adjustCost);
        } else
        {
            List cards = new ArrayList();
            cards.add(((AbstractCard)upgradableCards.get(0)).cardID);
            cards.add(((AbstractCard)upgradableCards.get(1)).cardID);
            logMetricUpgradeCardsAtCost("Designer", "Upgraded Two", cards, adjustCost);
            ((AbstractCard)upgradableCards.get(0)).upgrade();
            ((AbstractCard)upgradableCards.get(1)).upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(1));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(1)).makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
    }

    public static final String ID = "Designer";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESC[];
    public static final String OPTIONS[];
    private CurrentScreen curScreen;
    private OptionChosen option;
    public static final int GOLD_REQ = 75;
    public static final int UPG_AMT = 2;
    public static final int REMOVE_AMT = 2;
    private boolean adjustmentUpgradesOne;
    private boolean cleanUpRemovesCards;
    private int adjustCost;
    private int cleanUpCost;
    private int fullServiceCost;
    private int hpLoss;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Designer");
        NAME = eventStrings.NAME;
        DESC = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
}
