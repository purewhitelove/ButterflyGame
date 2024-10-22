// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GoopPuddle.java

package com.megacrit.cardcrawl.events.exordium;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class GoopPuddle extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/exordium/GoopPuddle$CurScreen, name);
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


    public GoopPuddle()
    {
        super(NAME, DIALOG_1, "images/events/goopPuddle.jpg");
        screen = CurScreen.INTRO;
        damage = 11;
        gold = 75;
        if(AbstractDungeon.ascensionLevel >= 15)
            goldLoss = AbstractDungeon.miscRng.random(35, 75);
        else
            goldLoss = AbstractDungeon.miscRng.random(20, 50);
        if(goldLoss > AbstractDungeon.player.gold)
            goldLoss = AbstractDungeon.player.gold;
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(gold).append(OPTIONS[1]).append(damage).append(OPTIONS[2]).toString());
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[3]).append(goldLoss).append(OPTIONS[4]).toString());
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_SPIRITS");
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$exordium$GoopPuddle$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$exordium$GoopPuddle$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$GoopPuddle$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.exordium.GoopPuddle.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(GOLD_DIALOG);
                imageEventText.clearAllDialogs();
                AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, damage));
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.effectList.add(new RainingGoldEffect(gold));
                AbstractDungeon.player.gainGold(gold);
                imageEventText.setDialogOption(OPTIONS[5]);
                screen = CurScreen.RESULT;
                AbstractEvent.logMetricGainGoldAndDamage("World of Goop", "Gather Gold", gold, damage);
                break;

            case 1: // '\001'
                imageEventText.updateBodyText(LEAVE_DIALOG);
                AbstractDungeon.player.loseGold(goldLoss);
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPTIONS[5]);
                screen = CurScreen.RESULT;
                logMetricLoseGold("World of Goop", "Left Gold", goldLoss);
                break;
            }
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "World of Goop";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String GOLD_DIALOG;
    private static final String LEAVE_DIALOG;
    private CurScreen screen;
    private int damage;
    private int gold;
    private int goldLoss;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("World of Goop");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        GOLD_DIALOG = DESCRIPTIONS[1];
        LEAVE_DIALOG = DESCRIPTIONS[2];
    }
}
