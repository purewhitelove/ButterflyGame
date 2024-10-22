// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheJoust.java

package com.megacrit.cardcrawl.events.city;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;

public class TheJoust extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/city/TheJoust$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN HALT;
        public static final CUR_SCREEN EXPLANATION;
        public static final CUR_SCREEN PRE_JOUST;
        public static final CUR_SCREEN JOUST;
        public static final CUR_SCREEN COMPLETE;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            HALT = new CUR_SCREEN("HALT", 0);
            EXPLANATION = new CUR_SCREEN("EXPLANATION", 1);
            PRE_JOUST = new CUR_SCREEN("PRE_JOUST", 2);
            JOUST = new CUR_SCREEN("JOUST", 3);
            COMPLETE = new CUR_SCREEN("COMPLETE", 4);
            $VALUES = (new CUR_SCREEN[] {
                HALT, EXPLANATION, PRE_JOUST, JOUST, COMPLETE
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public TheJoust()
    {
        super(NAME, HALT_MSG, "images/events/joust.jpg");
        screen = CUR_SCREEN.HALT;
        joustTimer = 0.0F;
        clangCount = 0;
        imageEventText.setDialogOption(OPTIONS[0]);
    }

    public void update()
    {
        super.update();
        if(joustTimer != 0.0F)
        {
            joustTimer -= Gdx.graphics.getDeltaTime();
            if(joustTimer < 0.0F)
            {
                clangCount++;
                if(clangCount == 1)
                {
                    CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, false);
                    CardCrawlGame.sound.play("BLUNT_HEAVY");
                    joustTimer = 1.0F;
                } else
                if(clangCount == 2)
                {
                    CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
                    CardCrawlGame.sound.play("BLUNT_FAST");
                    joustTimer = 0.25F;
                } else
                if(clangCount == 3)
                {
                    CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.LONG, false);
                    CardCrawlGame.sound.play("BLUNT_HEAVY");
                    joustTimer = 0.0F;
                }
            }
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$city$TheJoust$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$city$TheJoust$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$TheJoust$CUR_SCREEN[CUR_SCREEN.HALT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$TheJoust$CUR_SCREEN[CUR_SCREEN.EXPLANATION.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$TheJoust$CUR_SCREEN[CUR_SCREEN.PRE_JOUST.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$TheJoust$CUR_SCREEN[CUR_SCREEN.JOUST.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$TheJoust$CUR_SCREEN[CUR_SCREEN.COMPLETE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.city.TheJoust.CUR_SCREEN[screen.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            imageEventText.updateBodyText(EXPL_MSG);
            imageEventText.updateDialogOption(0, (new StringBuilder()).append(OPTIONS[1]).append(50).append(OPTIONS[2]).append(100).append(OPTIONS[3]).toString());
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[4]).append(50).append(OPTIONS[5]).append(250).append(OPTIONS[3]).toString());
            screen = CUR_SCREEN.EXPLANATION;
            break;

        case 2: // '\002'
            if(buttonPressed == 0)
            {
                betFor = false;
                imageEventText.updateBodyText(BET_AGAINST);
            } else
            {
                betFor = true;
                imageEventText.updateBodyText(BET_FOR);
            }
            AbstractDungeon.player.loseGold(50);
            imageEventText.updateDialogOption(0, OPTIONS[6]);
            imageEventText.clearRemainingOptions();
            screen = CUR_SCREEN.PRE_JOUST;
            break;

        case 3: // '\003'
            imageEventText.updateBodyText(COMBAT_MSG);
            imageEventText.updateDialogOption(0, OPTIONS[6]);
            ownerWins = AbstractDungeon.miscRng.randomBoolean(0.3F);
            screen = CUR_SCREEN.JOUST;
            joustTimer = 0.01F;
            break;

        case 4: // '\004'
            imageEventText.updateDialogOption(0, OPTIONS[7]);
            String tmp;
            if(ownerWins)
            {
                tmp = NOODLES_WIN;
                if(betFor)
                {
                    tmp = (new StringBuilder()).append(tmp).append(BET_WON_MSG).toString();
                    AbstractDungeon.player.gainGold(250);
                    CardCrawlGame.sound.play("GOLD_GAIN");
                    logMetricGainAndLoseGold("The Joust", "Bet on Owner", 250, 50);
                } else
                {
                    tmp = (new StringBuilder()).append(tmp).append(BET_LOSE_MSG).toString();
                    logMetricLoseGold("The Joust", "Bet on Owner", 50);
                }
            } else
            {
                tmp = NOODLES_LOSE;
                if(betFor)
                {
                    tmp = (new StringBuilder()).append(tmp).append(BET_LOSE_MSG).toString();
                    logMetricLoseGold("The Joust", "Bet on Murderer", 50);
                } else
                {
                    tmp = (new StringBuilder()).append(tmp).append(BET_WON_MSG).toString();
                    AbstractDungeon.player.gainGold(100);
                    CardCrawlGame.sound.play("GOLD_GAIN");
                    logMetricGainAndLoseGold("The Joust", "Bet on Murderer", 100, 50);
                }
            }
            imageEventText.updateBodyText(tmp);
            screen = CUR_SCREEN.COMPLETE;
            break;

        case 5: // '\005'
            openMap();
            break;
        }
    }

    public static final String ID = "The Joust";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String HALT_MSG;
    private static final String EXPL_MSG;
    private static final String BET_AGAINST;
    private static final String BET_FOR;
    private static final String COMBAT_MSG;
    private static final String NOODLES_WIN;
    private static final String NOODLES_LOSE;
    private static final String BET_WON_MSG;
    private static final String BET_LOSE_MSG;
    private boolean betFor;
    private boolean ownerWins;
    private static final int WIN_OWNER = 250;
    private static final int WIN_MURDERER = 100;
    private static final int BET_AMT = 50;
    private CUR_SCREEN screen;
    private float joustTimer;
    private int clangCount;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("The Joust");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        HALT_MSG = DESCRIPTIONS[0];
        EXPL_MSG = DESCRIPTIONS[1];
        BET_AGAINST = DESCRIPTIONS[2];
        BET_FOR = DESCRIPTIONS[3];
        COMBAT_MSG = DESCRIPTIONS[4];
        NOODLES_WIN = DESCRIPTIONS[5];
        NOODLES_LOSE = DESCRIPTIONS[6];
        BET_WON_MSG = DESCRIPTIONS[7];
        BET_LOSE_MSG = DESCRIPTIONS[8];
    }
}
