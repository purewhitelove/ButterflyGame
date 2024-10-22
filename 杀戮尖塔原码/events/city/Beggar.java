// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Beggar.java

package com.megacrit.cardcrawl.events.city;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import java.util.ArrayList;

public class Beggar extends AbstractImageEvent
{
    public static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/city/Beggar$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen LEAVE;
        public static final CurScreen GAVE_MONEY;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            LEAVE = new CurScreen("LEAVE", 1);
            GAVE_MONEY = new CurScreen("GAVE_MONEY", 2);
            $VALUES = (new CurScreen[] {
                INTRO, LEAVE, GAVE_MONEY
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public Beggar()
    {
        super(NAME, DIALOG_1, "images/events/beggar.jpg");
        if(AbstractDungeon.player.gold >= 75)
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(75).append(OPTIONS[1]).toString(), AbstractDungeon.player.gold < 75);
        else
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(75).append(OPTIONS[3]).toString(), AbstractDungeon.player.gold < 75);
        imageEventText.setDialogOption(OPTIONS[5]);
        hasDialog = true;
        hasFocus = true;
        screen = CurScreen.INTRO;
    }

    public void update()
    {
        super.update();
        if(!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            CardCrawlGame.sound.play("CARD_EXHAUST");
            logMetricCardRemovalAtCost("Beggar", "Gave Gold", (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), 75);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), Settings.WIDTH / 2, Settings.HEIGHT / 2));
            AbstractDungeon.player.masterDeck.removeCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            openMap();
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$city$Beggar$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$city$Beggar$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$Beggar$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$Beggar$CurScreen[CurScreen.GAVE_MONEY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$Beggar$CurScreen[CurScreen.LEAVE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.city.Beggar.CurScreen[screen.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            if(buttonPressed == 0)
            {
                imageEventText.loadImage("images/events/cleric.jpg");
                imageEventText.updateBodyText(PURGE_DIALOG);
                AbstractDungeon.player.loseGold(75);
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPTIONS[4]);
                screen = CurScreen.GAVE_MONEY;
            } else
            {
                imageEventText.updateBodyText(CANCEL_DIALOG);
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPTIONS[5]);
                screen = CurScreen.LEAVE;
                logMetricIgnored("Beggar");
            }
            break;

        case 2: // '\002'
            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[6], false, false, false, true);
            imageEventText.updateBodyText(POST_PURGE_DIALOG);
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPTIONS[5]);
            screen = CurScreen.LEAVE;
            break;

        case 3: // '\003'
            imageEventText.updateDialogOption(0, OPTIONS[5]);
            imageEventText.clearRemainingOptions();
            openMap();
            break;
        }
    }

    public static final String ID = "Beggar";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private CurScreen screen;
    public static final int GOLD_COST = 75;
    private static final String DIALOG_1;
    private static final String CANCEL_DIALOG;
    private static final String PURGE_DIALOG;
    private static final String POST_PURGE_DIALOG;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Beggar");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        CANCEL_DIALOG = DESCRIPTIONS[1];
        PURGE_DIALOG = DESCRIPTIONS[2];
        POST_PURGE_DIALOG = DESCRIPTIONS[3];
    }
}
