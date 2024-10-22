// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MaskedBandits.java

package com.megacrit.cardcrawl.events.city;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.RedMask;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import java.util.ArrayList;

public class MaskedBandits extends AbstractEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/city/MaskedBandits$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen PAID_1;
        public static final CurScreen PAID_2;
        public static final CurScreen PAID_3;
        public static final CurScreen END;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            PAID_1 = new CurScreen("PAID_1", 1);
            PAID_2 = new CurScreen("PAID_2", 2);
            PAID_3 = new CurScreen("PAID_3", 3);
            END = new CurScreen("END", 4);
            $VALUES = (new CurScreen[] {
                INTRO, PAID_1, PAID_2, PAID_3, END
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public MaskedBandits()
    {
        screen = CurScreen.INTRO;
        body = DESCRIPTIONS[4];
        roomEventText.addDialogOption(OPTIONS[0]);
        roomEventText.addDialogOption(OPTIONS[1]);
        hasDialog = true;
        hasFocus = true;
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Masked Bandits");
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

            static final int $SwitchMap$com$megacrit$cardcrawl$events$city$MaskedBandits$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$city$MaskedBandits$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$MaskedBandits$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$MaskedBandits$CurScreen[CurScreen.PAID_1.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$MaskedBandits$CurScreen[CurScreen.PAID_2.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$MaskedBandits$CurScreen[CurScreen.PAID_3.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$MaskedBandits$CurScreen[CurScreen.END.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
            }
        }

label0:
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.city.MaskedBandits.CurScreen[screen.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            switch(buttonPressed)
            {
            default:
                break label0;

            case 0: // '\0'
                stealGold();
                AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                roomEventText.updateBodyText(PAID_MSG_1);
                roomEventText.updateDialogOption(0, OPTIONS[2]);
                roomEventText.clearRemainingOptions();
                screen = CurScreen.PAID_1;
                return;

            case 1: // '\001'
                logMetric("Masked Bandits", "Fought Bandits");
                if(Settings.isDailyRun)
                    AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(30));
                else
                    AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25, 35));
                if(AbstractDungeon.player.hasRelic("Red Mask"))
                    AbstractDungeon.getCurrRoom().addRelicToRewards(new Circlet());
                else
                    AbstractDungeon.getCurrRoom().addRelicToRewards(new RedMask());
                enterCombat();
                AbstractDungeon.lastCombatMetricKey = "Masked Bandits";
                return;
            }

        case 2: // '\002'
            roomEventText.updateBodyText(PAID_MSG_2);
            screen = CurScreen.PAID_2;
            roomEventText.updateDialogOption(0, OPTIONS[2]);
            break;

        case 3: // '\003'
            roomEventText.updateBodyText(PAID_MSG_3);
            screen = CurScreen.PAID_3;
            roomEventText.updateDialogOption(0, OPTIONS[3]);
            break;

        case 4: // '\004'
            roomEventText.updateBodyText(PAID_MSG_4);
            roomEventText.updateDialogOption(0, OPTIONS[3]);
            screen = CurScreen.END;
            openMap();
            break;

        case 5: // '\005'
            openMap();
            break;
        }
    }

    private void stealGold()
    {
        AbstractCreature target = AbstractDungeon.player;
        if(target.gold == 0)
            return;
        logMetricLoseGold("Masked Bandits", "Paid Fearfully", target.gold);
        CardCrawlGame.sound.play("GOLD_JINGLE");
        for(int i = 0; i < target.gold; i++)
        {
            AbstractCreature source = AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
            AbstractDungeon.effectList.add(new GainPennyEffect(source, target.hb.cX, target.hb.cY, source.hb.cX, source.hb.cY, false));
        }

    }

    public static final String ID = "Masked Bandits";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String PAID_MSG_1;
    private static final String PAID_MSG_2;
    private static final String PAID_MSG_3;
    private static final String PAID_MSG_4;
    private CurScreen screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Masked Bandits");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        PAID_MSG_1 = DESCRIPTIONS[0];
        PAID_MSG_2 = DESCRIPTIONS[1];
        PAID_MSG_3 = DESCRIPTIONS[2];
        PAID_MSG_4 = DESCRIPTIONS[3];
    }
}
