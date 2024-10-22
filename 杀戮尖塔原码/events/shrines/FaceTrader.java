// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FaceTrader.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.CultistMask;
import com.megacrit.cardcrawl.relics.FaceOfCleric;
import com.megacrit.cardcrawl.relics.GremlinMask;
import com.megacrit.cardcrawl.relics.NlothsMask;
import com.megacrit.cardcrawl.relics.SsserpentHead;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FaceTrader extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/FaceTrader$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen MAIN;
        public static final CurScreen RESULT;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            MAIN = new CurScreen("MAIN", 1);
            RESULT = new CurScreen("RESULT", 2);
            $VALUES = (new CurScreen[] {
                INTRO, MAIN, RESULT
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public FaceTrader()
    {
        super(NAME, DESCRIPTIONS[0], "images/events/facelessTrader.jpg");
        screen = CurScreen.INTRO;
        if(AbstractDungeon.ascensionLevel >= 15)
            goldReward = 50;
        else
            goldReward = 75;
        damage = AbstractDungeon.player.maxHealth / 10;
        if(damage == 0)
            damage = 1;
        imageEventText.setDialogOption(OPTIONS[4]);
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$FaceTrader$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$shrines$FaceTrader$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$FaceTrader$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$FaceTrader$CurScreen[CurScreen.MAIN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.FaceTrader.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(DESCRIPTIONS[1]);
                imageEventText.updateDialogOption(0, (new StringBuilder()).append(OPTIONS[0]).append(damage).append(OPTIONS[5]).append(goldReward).append(OPTIONS[1]).toString());
                imageEventText.setDialogOption(OPTIONS[2]);
                imageEventText.setDialogOption(OPTIONS[3]);
                screen = CurScreen.MAIN;
                break;
            }
            break;

        case 2: // '\002'
            switch(buttonPressed)
            {
            case 0: // '\0'
                logMetricGainGoldAndDamage("FaceTrader", "Touch", goldReward, damage);
                imageEventText.updateBodyText(DESCRIPTIONS[2]);
                AbstractDungeon.effectList.add(new RainingGoldEffect(goldReward));
                AbstractDungeon.player.gainGold(goldReward);
                AbstractDungeon.player.damage(new DamageInfo(null, damage));
                CardCrawlGame.sound.play("ATTACK_POISON");
                break;

            case 1: // '\001'
                AbstractRelic r = getRandomFace();
                logMetricObtainRelic("FaceTrader", "Trade", r);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, r);
                imageEventText.updateBodyText(DESCRIPTIONS[3]);
                break;

            case 2: // '\002'
                logMetric("Leave");
                imageEventText.updateBodyText(DESCRIPTIONS[4]);
                break;
            }
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPTIONS[3]);
            screen = CurScreen.RESULT;
            break;

        default:
            openMap();
            break;
        }
    }

    private AbstractRelic getRandomFace()
    {
        ArrayList ids = new ArrayList();
        if(!AbstractDungeon.player.hasRelic("CultistMask"))
            ids.add("CultistMask");
        if(!AbstractDungeon.player.hasRelic("FaceOfCleric"))
            ids.add("FaceOfCleric");
        if(!AbstractDungeon.player.hasRelic("GremlinMask"))
            ids.add("GremlinMask");
        if(!AbstractDungeon.player.hasRelic("NlothsMask"))
            ids.add("NlothsMask");
        if(!AbstractDungeon.player.hasRelic("SsserpentHead"))
            ids.add("SsserpentHead");
        if(ids.size() <= 0)
            ids.add("Circlet");
        Collections.shuffle(ids, new Random(AbstractDungeon.miscRng.randomLong()));
        return RelicLibrary.getRelic((String)ids.get(0)).makeCopy();
    }

    public void logMetric(String actionTaken)
    {
        AbstractEvent.logMetric("FaceTrader", actionTaken);
    }

    public static final String ID = "FaceTrader";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static int goldReward;
    private static int damage;
    private CurScreen screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("FaceTrader");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
}
