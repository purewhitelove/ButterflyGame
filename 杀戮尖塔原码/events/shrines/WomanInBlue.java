// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WomanInBlue.java

package com.megacrit.cardcrawl.events.shrines;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import java.util.ArrayList;

public class WomanInBlue extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/WomanInBlue$CurScreen, name);
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


    public WomanInBlue()
    {
        super(NAME, DIALOG_1, "images/events/ladyInBlue.jpg");
        screen = CurScreen.INTRO;
        noCardsInRewards = true;
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(20).append(OPTIONS[3]).toString());
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[1]).append(30).append(OPTIONS[3]).toString());
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(40).append(OPTIONS[3]).toString());
        if(AbstractDungeon.ascensionLevel >= 15)
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[5]).append(MathUtils.ceil((float)AbstractDungeon.player.maxHealth * 0.05F)).append(OPTIONS[6]).toString());
        else
            imageEventText.setDialogOption(OPTIONS[4]);
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$WomanInBlue$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$shrines$WomanInBlue$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$WomanInBlue$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.WomanInBlue.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            default:
                break;

            case 0: // '\0'
                AbstractDungeon.player.loseGold(20);
                imageEventText.updateBodyText(DESCRIPTIONS[1]);
                logMetric("Bought 1 Potion");
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();
                break;

            case 1: // '\001'
                AbstractDungeon.player.loseGold(30);
                imageEventText.updateBodyText(DESCRIPTIONS[1]);
                logMetric("Bought 2 Potions");
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();
                break;

            case 2: // '\002'
                AbstractDungeon.player.loseGold(40);
                imageEventText.updateBodyText(DESCRIPTIONS[1]);
                logMetric("Bought 3 Potions");
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();
                break;

            case 3: // '\003'
                imageEventText.updateBodyText(DESCRIPTIONS[2]);
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, false);
                CardCrawlGame.sound.play("BLUNT_FAST");
                if(AbstractDungeon.ascensionLevel >= 15)
                    AbstractDungeon.player.damage(new DamageInfo(null, MathUtils.ceil((float)AbstractDungeon.player.maxHealth * 0.05F), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
                logMetric("Bought 0 Potions");
                break;
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

    public void logMetric(String actionTaken)
    {
        AbstractEvent.logMetric("The Woman in Blue", actionTaken);
    }

    public static final String ID = "The Woman in Blue";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final int cost1 = 20;
    private static final int cost2 = 30;
    private static final int cost3 = 40;
    private static final float PUNCH_DMG_PERCENT = 0.05F;
    private CurScreen screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("The Woman in Blue");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
    }
}
