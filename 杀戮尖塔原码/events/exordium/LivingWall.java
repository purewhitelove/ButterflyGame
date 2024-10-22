// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LivingWall.java

package com.megacrit.cardcrawl.events.exordium;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class LivingWall extends AbstractImageEvent
{
    private static final class Choice extends Enum
    {

        public static Choice[] values()
        {
            return (Choice[])$VALUES.clone();
        }

        public static Choice valueOf(String name)
        {
            return (Choice)Enum.valueOf(com/megacrit/cardcrawl/events/exordium/LivingWall$Choice, name);
        }

        public static final Choice FORGET;
        public static final Choice CHANGE;
        public static final Choice GROW;
        private static final Choice $VALUES[];

        static 
        {
            FORGET = new Choice("FORGET", 0);
            CHANGE = new Choice("CHANGE", 1);
            GROW = new Choice("GROW", 2);
            $VALUES = (new Choice[] {
                FORGET, CHANGE, GROW
            });
        }

        private Choice(String s, int i)
        {
            super(s, i);
        }
    }

    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/exordium/LivingWall$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen RESULT;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            RESULT = new CurScreen("RESULT", 1);
            $VALUES = (new CurScreen[] {
                INTRO, RESULT
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public LivingWall()
    {
        super(NAME, DIALOG_1, "images/events/livingWall.jpg");
        screen = CurScreen.INTRO;
        pickCard = false;
        choice = null;
        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        if(AbstractDungeon.player.masterDeck.hasUpgradableCards().booleanValue())
            imageEventText.setDialogOption(OPTIONS[2]);
        else
            imageEventText.setDialogOption(OPTIONS[7], true);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_LIVING_WALL");
    }

    public void update()
    {
        super.update();
        if(pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            static class _cls1
            {

                static final int $SwitchMap$com$megacrit$cardcrawl$events$exordium$LivingWall$Choice[];
                static final int $SwitchMap$com$megacrit$cardcrawl$events$exordium$LivingWall$CurScreen[];

                static 
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$LivingWall$CurScreen = new int[CurScreen.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$events$exordium$LivingWall$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$LivingWall$Choice = new int[Choice.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$events$exordium$LivingWall$Choice[Choice.FORGET.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror1) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$events$exordium$LivingWall$Choice[Choice.CHANGE.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror2) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$events$exordium$LivingWall$Choice[Choice.GROW.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror3) { }
                }
            }

            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.exordium.LivingWall.Choice[choice.ordinal()])
            {
            case 1: // '\001'
                CardCrawlGame.sound.play("CARD_EXHAUST");
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), Settings.WIDTH / 2, Settings.HEIGHT / 2));
                AbstractEvent.logMetricCardRemoval("Living Wall", "Forget", (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                AbstractDungeon.player.masterDeck.removeCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                break;

            case 2: // '\002'
                AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractDungeon.player.masterDeck.removeCard(c);
                AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
                AbstractCard transCard = AbstractDungeon.getTransformedCard();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(transCard, c.current_x, c.current_y));
                AbstractEvent.logMetricTransformCard("Living Wall", "Change", c, transCard);
                break;

            case 3: // '\003'
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).upgrade();
                AbstractCard upgCard = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractEvent.logMetricCardUpgrade("Living Wall", "Grow", upgCard);
                AbstractDungeon.player.bottledCardUpgradeCheck(upgCard);
                break;
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            pickCard = false;
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.exordium.LivingWall.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                choice = Choice.FORGET;
                if(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0)
                    AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[3], false, false, false, true);
                break;

            case 1: // '\001'
                choice = Choice.CHANGE;
                if(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0)
                    AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[4], false, true, false, false);
                break;

            default:
                choice = Choice.GROW;
                if(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0)
                    AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[5], true, false, false, false);
                break;
            }
            pickCard = true;
            imageEventText.updateBodyText(RESULT_DIALOG);
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPTIONS[6]);
            screen = CurScreen.RESULT;
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "Living Wall";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String RESULT_DIALOG;
    private CurScreen screen;
    private boolean pickCard;
    private Choice choice;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Living Wall");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        RESULT_DIALOG = DESCRIPTIONS[1];
    }
}
