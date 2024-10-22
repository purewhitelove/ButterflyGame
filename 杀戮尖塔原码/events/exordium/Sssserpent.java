// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Sssserpent.java

package com.megacrit.cardcrawl.events.exordium;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class Sssserpent extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/exordium/Sssserpent$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN AGREE;
        public static final CUR_SCREEN DISAGREE;
        public static final CUR_SCREEN COMPLETE;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            AGREE = new CUR_SCREEN("AGREE", 1);
            DISAGREE = new CUR_SCREEN("DISAGREE", 2);
            COMPLETE = new CUR_SCREEN("COMPLETE", 3);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, AGREE, DISAGREE, COMPLETE
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_SERPENT");
    }

    public Sssserpent()
    {
        super(NAME, DIALOG_1, "images/events/liarsGame.jpg");
        screen = CUR_SCREEN.INTRO;
        if(AbstractDungeon.ascensionLevel >= 15)
            goldReward = 150;
        else
            goldReward = 175;
        curse = new Doubt();
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(goldReward).append(OPTIONS[1]).toString(), CardLibrary.getCopy(curse.cardID));
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$exordium$Sssserpent$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$exordium$Sssserpent$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$Sssserpent$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$Sssserpent$CUR_SCREEN[CUR_SCREEN.AGREE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.exordium.Sssserpent.CUR_SCREEN[screen.ordinal()])
        {
        case 1: // '\001'
            if(buttonPressed == 0)
            {
                imageEventText.updateBodyText(AGREE_DIALOG);
                imageEventText.removeDialogOption(1);
                imageEventText.updateDialogOption(0, OPTIONS[3]);
                screen = CUR_SCREEN.AGREE;
                AbstractEvent.logMetricGainGoldAndCard("Liars Game", "AGREE", curse, goldReward);
            } else
            {
                imageEventText.updateBodyText(DISAGREE_DIALOG);
                imageEventText.removeDialogOption(1);
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                screen = CUR_SCREEN.DISAGREE;
                AbstractEvent.logMetricIgnored("Liars Game");
            }
            break;

        case 2: // '\002'
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.effectList.add(new RainingGoldEffect(goldReward));
            AbstractDungeon.player.gainGold(goldReward);
            imageEventText.updateBodyText(GOLD_RAIN_MSG);
            imageEventText.updateDialogOption(0, OPTIONS[4]);
            screen = CUR_SCREEN.COMPLETE;
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "Liars Game";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String AGREE_DIALOG;
    private static final String DISAGREE_DIALOG;
    private static final String GOLD_RAIN_MSG;
    private CUR_SCREEN screen;
    private static final int GOLD_REWARD = 175;
    private static final int A_2_GOLD_REWARD = 150;
    private int goldReward;
    private AbstractCard curse;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Liars Game");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        AGREE_DIALOG = DESCRIPTIONS[1];
        DISAGREE_DIALOG = DESCRIPTIONS[2];
        GOLD_RAIN_MSG = DESCRIPTIONS[3];
    }
}
