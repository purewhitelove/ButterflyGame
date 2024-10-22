// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheMausoleum.java

package com.megacrit.cardcrawl.events.city;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class TheMausoleum extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/city/TheMausoleum$CurScreen, name);
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


    public TheMausoleum()
    {
        super(NAME, DIALOG_1, "images/events/mausoleum.jpg");
        screen = CurScreen.INTRO;
        if(AbstractDungeon.ascensionLevel >= 15)
            percent = 100;
        else
            percent = 50;
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(percent).append(OPTIONS[1]).toString(), CardLibrary.getCopy("Writhe"));
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_GHOSTS");
    }

    public void update()
    {
        super.update();
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$city$TheMausoleum$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$city$TheMausoleum$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$TheMausoleum$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.city.TheMausoleum.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                boolean result = AbstractDungeon.miscRng.randomBoolean();
                if(AbstractDungeon.ascensionLevel >= 15)
                    result = true;
                if(result)
                {
                    imageEventText.updateBodyText(CURSED_RESULT);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Writhe(), Settings.WIDTH / 2, Settings.HEIGHT / 2));
                } else
                {
                    imageEventText.updateBodyText(NORMAL_RESULT);
                }
                CardCrawlGame.sound.play("BLUNT_HEAVY");
                CardCrawlGame.screenShake.rumble(2.0F);
                AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, r);
                if(result)
                    logMetricObtainCardAndRelic("The Mausoleum", "Opened", new Writhe(), r);
                else
                    logMetricObtainRelic("The Mausoleum", "Opened", r);
                break;

            default:
                imageEventText.updateBodyText(NOPE_RESULT);
                logMetricIgnored("The Mausoleum");
                break;
            }
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPTIONS[2]);
            screen = CurScreen.RESULT;
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "The Mausoleum";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String CURSED_RESULT;
    private static final String NORMAL_RESULT;
    private static final String NOPE_RESULT;
    private CurScreen screen;
    private static final int PERCENT = 50;
    private static final int A_2_PERCENT = 100;
    private int percent;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("The Mausoleum");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        CURSED_RESULT = DESCRIPTIONS[1];
        NORMAL_RESULT = DESCRIPTIONS[2];
        NOPE_RESULT = DESCRIPTIONS[3];
    }
}
