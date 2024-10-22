// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MysteriousSphere.java

package com.megacrit.cardcrawl.events.beyond;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class MysteriousSphere extends AbstractEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/beyond/MysteriousSphere$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen PRE_COMBAT;
        public static final CurScreen END;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            PRE_COMBAT = new CurScreen("PRE_COMBAT", 1);
            END = new CurScreen("END", 2);
            $VALUES = (new CurScreen[] {
                INTRO, PRE_COMBAT, END
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public MysteriousSphere()
    {
        screen = CurScreen.INTRO;
        initializeImage("images/events/sphereClosed.png", 1120F * Settings.xScale, AbstractDungeon.floorY - 50F * Settings.scale);
        body = INTRO_MSG;
        roomEventText.addDialogOption(OPTIONS[0]);
        roomEventText.addDialogOption(OPTIONS[1]);
        hasDialog = true;
        hasFocus = true;
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("2 Orb Walkers");
    }

    public void update()
    {
        super.update();
        if(!RoomEventDialog.waitForInput)
            buttonEffect(roomEventText.getSelectedOption());
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$beyond$MysteriousSphere$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$beyond$MysteriousSphere$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$MysteriousSphere$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$MysteriousSphere$CurScreen[CurScreen.PRE_COMBAT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$MysteriousSphere$CurScreen[CurScreen.END.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.beyond.MysteriousSphere.CurScreen[screen.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                screen = CurScreen.PRE_COMBAT;
                roomEventText.updateBodyText(DESCRIPTIONS[1]);
                roomEventText.updateDialogOption(0, OPTIONS[2]);
                roomEventText.clearRemainingOptions();
                logMetric("Mysterious Sphere", "Fight");
                return;

            case 1: // '\001'
                screen = CurScreen.END;
                roomEventText.updateBodyText(DESCRIPTIONS[2]);
                roomEventText.updateDialogOption(0, OPTIONS[1]);
                roomEventText.clearRemainingOptions();
                logMetricIgnored("Mysterious Sphere");
                return;
            }
            break;

        case 2: // '\002'
            if(Settings.isDailyRun)
                AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(50));
            else
                AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(45, 55));
            AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomScreenlessRelic(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE));
            if(img != null)
            {
                img.dispose();
                img = null;
            }
            img = ImageMaster.loadImage("images/events/sphereOpen.png");
            enterCombat();
            AbstractDungeon.lastCombatMetricKey = "2 Orb Walkers";
            break;

        case 3: // '\003'
            openMap();
            break;
        }
    }

    public static final String ID = "Mysterious Sphere";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String INTRO_MSG;
    private CurScreen screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Mysterious Sphere");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_MSG = DESCRIPTIONS[0];
    }
}
