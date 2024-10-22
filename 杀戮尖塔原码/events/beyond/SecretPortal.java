// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SecretPortal.java

package com.megacrit.cardcrawl.events.beyond;

import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.FadeWipeParticle;
import java.util.ArrayList;

public class SecretPortal extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/beyond/SecretPortal$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen ACCEPT;
        public static final CurScreen LEAVE;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            ACCEPT = new CurScreen("ACCEPT", 1);
            LEAVE = new CurScreen("LEAVE", 2);
            $VALUES = (new CurScreen[] {
                INTRO, ACCEPT, LEAVE
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public SecretPortal()
    {
        super(NAME, DIALOG_1, "images/events/secretPortal.jpg");
        screen = CurScreen.INTRO;
        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_PORTAL");
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$beyond$SecretPortal$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$beyond$SecretPortal$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$SecretPortal$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$SecretPortal$CurScreen[CurScreen.ACCEPT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.beyond.SecretPortal.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(DIALOG_2);
                screen = CurScreen.ACCEPT;
                logMetric("SecretPortal", "Took Portal");
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                CardCrawlGame.screenShake.mildRumble(5F);
                CardCrawlGame.sound.play("ATTACK_MAGIC_SLOW_2");
                break;

            case 1: // '\001'
                imageEventText.updateBodyText(DIALOG_3);
                screen = CurScreen.LEAVE;
                logMetricIgnored("SecretPortal");
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                break;
            }
            imageEventText.clearRemainingOptions();
            break;

        case 2: // '\002'
            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
            MapRoomNode node = new MapRoomNode(-1, 15);
            node.room = new MonsterRoomBoss();
            AbstractDungeon.nextRoom = node;
            CardCrawlGame.music.fadeOutTempBGM();
            AbstractDungeon.pathX.add(Integer.valueOf(1));
            AbstractDungeon.pathY.add(Integer.valueOf(15));
            AbstractDungeon.topLevelEffects.add(new FadeWipeParticle());
            AbstractDungeon.nextRoomTransitionStart();
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "SecretPortal";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    public static final String EVENT_CHOICE_TOOK_PORTAL = "Took Portal";
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private CurScreen screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("SecretPortal");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
    }
}
