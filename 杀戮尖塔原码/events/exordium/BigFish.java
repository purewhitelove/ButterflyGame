// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BigFish.java

package com.megacrit.cardcrawl.events.exordium;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class BigFish extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/exordium/BigFish$CurScreen, name);
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


    public BigFish()
    {
        super(NAME, DIALOG_1, "images/events/fishing.jpg");
        healAmt = 0;
        screen = CurScreen.INTRO;
        healAmt = AbstractDungeon.player.maxHealth / 3;
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(healAmt).append(OPTIONS[1]).toString());
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(5).append(OPTIONS[3]).toString());
        imageEventText.setDialogOption(OPTIONS[4], CardLibrary.getCopy("Regret"));
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$exordium$BigFish$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$exordium$BigFish$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$BigFish$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.exordium.BigFish.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                AbstractDungeon.player.heal(healAmt, true);
                imageEventText.updateBodyText(BANANA_RESULT);
                AbstractEvent.logMetricHeal("Big Fish", "Banana", healAmt);
                break;

            case 1: // '\001'
                AbstractDungeon.player.increaseMaxHp(5, true);
                imageEventText.updateBodyText(DONUT_RESULT);
                AbstractEvent.logMetricMaxHPGain("Big Fish", "Donut", 5);
                break;

            default:
                imageEventText.updateBodyText((new StringBuilder()).append(BOX_RESULT).append(BOX_BAD).toString());
                AbstractCard c = new Regret();
                AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                AbstractEvent.logMetricObtainCardAndRelic("Big Fish", "Box", c, r);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCopy(c.cardID), Settings.WIDTH / 2, Settings.HEIGHT / 2));
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, r);
                break;
            }
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPTIONS[5]);
            screen = CurScreen.RESULT;
            break;

        default:
            openMap();
            break;
        }
    }

    public void logMetric(String actionTaken)
    {
        AbstractEvent.logMetric("Big Fish", actionTaken);
    }

    public static final String ID = "Big Fish";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String BANANA_RESULT;
    private static final String DONUT_RESULT;
    private static final String BOX_RESULT;
    private static final String BOX_BAD;
    private int healAmt;
    private static final int MAX_HP_AMT = 5;
    private CurScreen screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Big Fish");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        BANANA_RESULT = DESCRIPTIONS[1];
        DONUT_RESULT = DESCRIPTIONS[2];
        BOX_RESULT = DESCRIPTIONS[4];
        BOX_BAD = DESCRIPTIONS[5];
    }
}
