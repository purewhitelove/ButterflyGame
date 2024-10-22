// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NoteForYourself.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class NoteForYourself extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/NoteForYourself$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN CHOOSE;
        public static final CUR_SCREEN COMPLETE;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            CHOOSE = new CUR_SCREEN("CHOOSE", 1);
            COMPLETE = new CUR_SCREEN("COMPLETE", 2);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, CHOOSE, COMPLETE
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public NoteForYourself()
    {
        super(NAME, DIALOG_1, "images/events/selfNote.jpg");
        obtainCard = null;
        saveCard = null;
        cardSelect = false;
        screen = CUR_SCREEN.INTRO;
        imageEventText.setDialogOption(OPTIONS[0]);
        initializeObtainCard();
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$NoteForYourself$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$shrines$NoteForYourself$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$NoteForYourself$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$NoteForYourself$CUR_SCREEN[CUR_SCREEN.CHOOSE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$NoteForYourself$CUR_SCREEN[CUR_SCREEN.COMPLETE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.NoteForYourself.CUR_SCREEN[screen.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            imageEventText.updateBodyText(DESCRIPTIONS[1]);
            screen = CUR_SCREEN.CHOOSE;
            imageEventText.updateDialogOption(0, (new StringBuilder()).append(OPTIONS[1]).append(obtainCard.name).append(OPTIONS[2]).toString(), obtainCard);
            imageEventText.setDialogOption(OPTIONS[3]);
            break;

        case 2: // '\002'
            screen = CUR_SCREEN.COMPLETE;
            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
            switch(buttonPressed)
            {
            case 0: // '\0'
                AbstractRelic r;
                for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onObtainCard(obtainCard))
                    r = (AbstractRelic)iterator.next();

                AbstractDungeon.player.masterDeck.addToTop(obtainCard);
                AbstractRelic r;
                for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onMasterDeckChange())
                    r = (AbstractRelic)iterator1.next();

                cardSelect = true;
                AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, DESCRIPTIONS[2], false, false, false, false);
                break;

            default:
                logMetricIgnored("NoteForYourself");
                break;
            }
            imageEventText.updateBodyText(DESCRIPTIONS[3]);
            imageEventText.updateDialogOption(0, OPTIONS[4]);
            imageEventText.clearRemainingOptions();
            screen = CUR_SCREEN.COMPLETE;
            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
            break;

        case 3: // '\003'
            openMap();
            break;
        }
    }

    public void update()
    {
        super.update();
        if(cardSelect && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard storeCard = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.remove(0);
            logMetricObtainCardAndLoseCard("NoteForYourself", "Took Card", obtainCard, storeCard);
            AbstractDungeon.player.masterDeck.removeCard(storeCard);
            saveCard = storeCard;
            cardSelect = false;
        }
    }

    private void initializeObtainCard()
    {
        obtainCard = CardLibrary.getCard(CardCrawlGame.playerPref.getString("NOTE_CARD", "Iron Wave"));
        if(obtainCard == null)
            obtainCard = new IronWave();
        obtainCard = obtainCard.makeCopy();
        for(int i = 0; i < CardCrawlGame.playerPref.getInteger("NOTE_UPGRADE", 0); i++)
            obtainCard.upgrade();

    }

    public static final String ID = "NoteForYourself";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private AbstractCard obtainCard;
    public AbstractCard saveCard;
    private boolean cardSelect;
    private static final String DIALOG_1;
    private CUR_SCREEN screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("NoteForYourself");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
    }
}
