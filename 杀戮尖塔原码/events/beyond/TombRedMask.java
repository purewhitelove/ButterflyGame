// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TombRedMask.java

package com.megacrit.cardcrawl.events.beyond;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.RedMask;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import java.util.ArrayList;

public class TombRedMask extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/beyond/TombRedMask$CurScreen, name);
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


    public TombRedMask()
    {
        super(NAME, DIALOG_1, "images/events/redMaskTomb.jpg");
        screen = CurScreen.INTRO;
        if(AbstractDungeon.player.hasRelic("Red Mask"))
        {
            imageEventText.setDialogOption(OPTIONS[0]);
        } else
        {
            imageEventText.setDialogOption(OPTIONS[1], true);
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(AbstractDungeon.player.gold).append(OPTIONS[3]).toString(), new RedMask());
        }
        imageEventText.setDialogOption(OPTIONS[4]);
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$beyond$TombRedMask$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$beyond$TombRedMask$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$TombRedMask$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.beyond.TombRedMask.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            if(buttonPressed == 0)
            {
                AbstractDungeon.effectList.add(new RainingGoldEffect(222));
                AbstractDungeon.player.gainGold(222);
                imageEventText.updateBodyText(MASK_RESULT);
                logMetricGainGold("Tomb of Lord Red Mask", "Wore Mask", 222);
            } else
            if(buttonPressed == 1 && !AbstractDungeon.player.hasRelic("Red Mask"))
            {
                com.megacrit.cardcrawl.relics.AbstractRelic r = new RedMask();
                logMetricObtainRelicAtCost("Tomb of Lord Red Mask", "Paid", r, AbstractDungeon.player.gold);
                AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, r);
                imageEventText.updateBodyText(RELIC_RESULT);
            } else
            {
                openMap();
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPTIONS[4]);
                logMetricIgnored("Tomb of Lord Red Mask");
            }
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPTIONS[4]);
            screen = CurScreen.RESULT;
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "Tomb of Lord Red Mask";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final int GOLD_AMT = 222;
    private static final String DIALOG_1;
    private static final String MASK_RESULT;
    private static final String RELIC_RESULT;
    private CurScreen screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Tomb of Lord Red Mask");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        MASK_RESULT = DESCRIPTIONS[1];
        RELIC_RESULT = DESCRIPTIONS[2];
    }
}
