// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Falling.java

package com.megacrit.cardcrawl.events.beyond;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import java.util.ArrayList;

public class Falling extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/beyond/Falling$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen CHOICE;
        public static final CurScreen RESULT;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            CHOICE = new CurScreen("CHOICE", 1);
            RESULT = new CurScreen("RESULT", 2);
            $VALUES = (new CurScreen[] {
                INTRO, CHOICE, RESULT
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public Falling()
    {
        super(NAME, DIALOG_1, "images/events/falling.jpg");
        screen = CurScreen.INTRO;
        setCards();
        imageEventText.setDialogOption(OPTIONS[0]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_FALLING");
    }

    private void setCards()
    {
        attack = CardHelper.hasCardWithType(com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK);
        skill = CardHelper.hasCardWithType(com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL);
        power = CardHelper.hasCardWithType(com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER);
        if(attack)
            attackCard = CardHelper.returnCardOfType(com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, AbstractDungeon.miscRng);
        if(skill)
            skillCard = CardHelper.returnCardOfType(com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, AbstractDungeon.miscRng);
        if(power)
            powerCard = CardHelper.returnCardOfType(com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER, AbstractDungeon.miscRng);
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$beyond$Falling$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$beyond$Falling$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$Falling$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$Falling$CurScreen[CurScreen.CHOICE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.beyond.Falling.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            screen = CurScreen.CHOICE;
            imageEventText.updateBodyText(DIALOG_2);
            imageEventText.clearAllDialogs();
            if(!skill && !power && !attack)
            {
                imageEventText.setDialogOption(OPTIONS[8]);
            } else
            {
                if(skill)
                    imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[1]).append(FontHelper.colorString(skillCard.name, "r")).toString(), skillCard.makeStatEquivalentCopy());
                else
                    imageEventText.setDialogOption(OPTIONS[2], true);
                if(power)
                    imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[3]).append(FontHelper.colorString(powerCard.name, "r")).toString(), powerCard.makeStatEquivalentCopy());
                else
                    imageEventText.setDialogOption(OPTIONS[4], true);
                if(attack)
                    imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[5]).append(FontHelper.colorString(attackCard.name, "r")).toString(), attackCard.makeStatEquivalentCopy());
                else
                    imageEventText.setDialogOption(OPTIONS[6], true);
            }
            break;

        case 2: // '\002'
            screen = CurScreen.RESULT;
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPTIONS[7]);
            switch(buttonPressed)
            {
            default:
                break;

            case 0: // '\0'
                if(!skill && !power && !attack)
                {
                    imageEventText.updateBodyText(DESCRIPTIONS[5]);
                    logMetricIgnored("Falling");
                } else
                {
                    imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    AbstractDungeon.effectList.add(new PurgeCardEffect(skillCard));
                    AbstractDungeon.player.masterDeck.removeCard(skillCard);
                    logMetricCardRemoval("Falling", "Removed Skill", skillCard);
                }
                break;

            case 1: // '\001'
                imageEventText.updateBodyText(DESCRIPTIONS[3]);
                AbstractDungeon.effectList.add(new PurgeCardEffect(powerCard));
                AbstractDungeon.player.masterDeck.removeCard(powerCard);
                logMetricCardRemoval("Falling", "Removed Power", powerCard);
                break;

            case 2: // '\002'
                imageEventText.updateBodyText(DESCRIPTIONS[4]);
                AbstractDungeon.effectList.add(new PurgeCardEffect(attackCard));
                logMetricCardRemoval("Falling", "Removed Attack", attackCard);
                AbstractDungeon.player.masterDeck.removeCard(attackCard);
                break;
            }
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "Falling";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private CurScreen screen;
    private boolean attack;
    private boolean skill;
    private boolean power;
    private AbstractCard attackCard;
    private AbstractCard skillCard;
    private AbstractCard powerCard;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Falling");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
    }
}
