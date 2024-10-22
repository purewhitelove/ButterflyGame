// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CursedTome.java

package com.megacrit.cardcrawl.events.city;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import java.util.ArrayList;

public class CursedTome extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/city/CursedTome$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen PAGE_1;
        public static final CurScreen PAGE_2;
        public static final CurScreen PAGE_3;
        public static final CurScreen LAST_PAGE;
        public static final CurScreen END;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            PAGE_1 = new CurScreen("PAGE_1", 1);
            PAGE_2 = new CurScreen("PAGE_2", 2);
            PAGE_3 = new CurScreen("PAGE_3", 3);
            LAST_PAGE = new CurScreen("LAST_PAGE", 4);
            END = new CurScreen("END", 5);
            $VALUES = (new CurScreen[] {
                INTRO, PAGE_1, PAGE_2, PAGE_3, LAST_PAGE, END
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public CursedTome()
    {
        super(NAME, INTRO_MSG, "images/events/cursedTome.jpg");
        screen = CurScreen.INTRO;
        noCardsInRewards = true;
        damageTaken = 0;
        if(AbstractDungeon.ascensionLevel >= 15)
            finalDmg = 15;
        else
            finalDmg = 10;
        imageEventText.setDialogOption(OPT_READ);
        imageEventText.setDialogOption(OPT_LEAVE);
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$city$CursedTome$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$city$CursedTome$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$CursedTome$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$CursedTome$CurScreen[CurScreen.PAGE_1.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$CursedTome$CurScreen[CurScreen.PAGE_2.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$CursedTome$CurScreen[CurScreen.PAGE_3.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$CursedTome$CurScreen[CurScreen.LAST_PAGE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$CursedTome$CurScreen[CurScreen.END.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.city.CursedTome.CurScreen[screen.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            imageEventText.clearAllDialogs();
            if(buttonPressed == 0)
            {
                CardCrawlGame.sound.play("EVENT_TOME");
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPT_CONTINUE_1);
                imageEventText.updateBodyText(READ_1);
                screen = CurScreen.PAGE_1;
            } else
            {
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPT_LEAVE);
                imageEventText.updateBodyText(IGNORE_MSG);
                screen = CurScreen.END;
                logMetricIgnored("Cursed Tome");
            }
            break;

        case 2: // '\002'
            CardCrawlGame.sound.play("EVENT_TOME");
            AbstractDungeon.player.damage(new DamageInfo(null, 1, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
            damageTaken++;
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPT_CONTINUE_2);
            imageEventText.updateBodyText(READ_2);
            screen = CurScreen.PAGE_2;
            break;

        case 3: // '\003'
            CardCrawlGame.sound.play("EVENT_TOME");
            AbstractDungeon.player.damage(new DamageInfo(null, 2, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
            damageTaken += 2;
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPT_CONTINUE_3);
            imageEventText.updateBodyText(READ_3);
            screen = CurScreen.PAGE_3;
            break;

        case 4: // '\004'
            CardCrawlGame.sound.play("EVENT_TOME");
            AbstractDungeon.player.damage(new DamageInfo(null, 3, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
            damageTaken += 3;
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[5]).append(finalDmg).append(OPTIONS[6]).toString());
            imageEventText.setDialogOption(OPT_STOP);
            imageEventText.updateBodyText(READ_4);
            screen = CurScreen.LAST_PAGE;
            break;

        case 5: // '\005'
            if(buttonPressed == 0)
            {
                AbstractDungeon.player.damage(new DamageInfo(null, finalDmg, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
                damageTaken += finalDmg;
                imageEventText.updateBodyText(OBTAIN_MSG);
                randomBook();
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPT_LEAVE);
            } else
            {
                AbstractDungeon.player.damage(new DamageInfo(null, 3, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
                damageTaken += 3;
                imageEventText.updateBodyText(STOP_MSG);
                logMetricTakeDamage("Cursed Tome", "Stopped", damageTaken);
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPT_LEAVE);
                screen = CurScreen.END;
            }
            break;

        case 6: // '\006'
            imageEventText.updateDialogOption(0, OPT_LEAVE);
            imageEventText.clearRemainingOptions();
            openMap();
            break;
        }
    }

    private void randomBook()
    {
        ArrayList possibleBooks = new ArrayList();
        if(!AbstractDungeon.player.hasRelic("Necronomicon"))
            possibleBooks.add(RelicLibrary.getRelic("Necronomicon").makeCopy());
        if(!AbstractDungeon.player.hasRelic("Enchiridion"))
            possibleBooks.add(RelicLibrary.getRelic("Enchiridion").makeCopy());
        if(!AbstractDungeon.player.hasRelic("Nilry's Codex"))
            possibleBooks.add(RelicLibrary.getRelic("Nilry's Codex").makeCopy());
        if(possibleBooks.size() == 0)
            possibleBooks.add(RelicLibrary.getRelic("Circlet").makeCopy());
        AbstractRelic r = (AbstractRelic)possibleBooks.get(AbstractDungeon.miscRng.random(possibleBooks.size() - 1));
        logMetricTakeDamage("Cursed Tome", "Obtained Book", damageTaken);
        AbstractDungeon.getCurrRoom().rewards.clear();
        AbstractDungeon.getCurrRoom().addRelicToRewards(r);
        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
        screen = CurScreen.END;
    }

    public static final String ID = "Cursed Tome";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String INTRO_MSG;
    private static final String READ_1;
    private static final String READ_2;
    private static final String READ_3;
    private static final String READ_4;
    private static final String OBTAIN_MSG;
    private static final String IGNORE_MSG;
    private static final String STOP_MSG;
    private static final String OPT_READ;
    private static final String OPT_CONTINUE_1;
    private static final String OPT_CONTINUE_2;
    private static final String OPT_CONTINUE_3;
    private static final String OPT_STOP;
    private static final String OPT_LEAVE;
    private static final int DMG_BOOK_OPEN = 1;
    private static final int DMG_SECOND_PAGE = 2;
    private static final int DMG_THIRD_PAGE = 3;
    private static final int DMG_STOP_READING = 3;
    private static final int DMG_OBTAIN_BOOK = 10;
    private static final int A_2_DMG_OBTAIN_BOOK = 15;
    private int finalDmg;
    private int damageTaken;
    private CurScreen screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Cursed Tome");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_MSG = DESCRIPTIONS[0];
        READ_1 = DESCRIPTIONS[1];
        READ_2 = DESCRIPTIONS[2];
        READ_3 = DESCRIPTIONS[3];
        READ_4 = DESCRIPTIONS[4];
        OBTAIN_MSG = DESCRIPTIONS[5];
        IGNORE_MSG = DESCRIPTIONS[6];
        STOP_MSG = DESCRIPTIONS[7];
        OPT_READ = OPTIONS[0];
        OPT_CONTINUE_1 = OPTIONS[1];
        OPT_CONTINUE_2 = OPTIONS[2];
        OPT_CONTINUE_3 = OPTIONS[3];
        OPT_STOP = OPTIONS[4];
        OPT_LEAVE = OPTIONS[7];
    }
}
