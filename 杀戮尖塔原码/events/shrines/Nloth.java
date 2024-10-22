// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Nloth.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.NlothsGift;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Nloth extends AbstractImageEvent
{

    public Nloth()
    {
        super(NAME, DIALOG_1, "images/events/nloth.jpg");
        screenNum = 0;
        ArrayList relics = new ArrayList();
        relics.addAll(AbstractDungeon.player.relics);
        Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
        choice1 = (AbstractRelic)relics.get(0);
        choice2 = (AbstractRelic)relics.get(1);
        gift = new NlothsGift();
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(choice1.name).append(OPTIONS[1]).toString(), new NlothsGift());
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(choice2.name).append(OPTIONS[1]).toString(), new NlothsGift());
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_SERPENT");
    }

    protected void buttonEffect(int buttonPressed)
    {
label0:
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(DIALOG_2);
                if(AbstractDungeon.player.hasRelic("Nloth's Gift"))
                {
                    gift = new Circlet();
                    AbstractEvent.logMetricRelicSwap("N'loth", "Traded Relic", gift, choice1);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, gift);
                } else
                {
                    AbstractEvent.logMetricRelicSwap("N'loth", "Traded Relic", gift, choice1);
                    AbstractDungeon.player.loseRelic(choice1.relicId);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, gift);
                }
                screenNum = 1;
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                imageEventText.clearRemainingOptions();
                break label0;

            case 1: // '\001'
                imageEventText.updateBodyText(DIALOG_2);
                if(AbstractDungeon.player.hasRelic("Nloth's Gift"))
                {
                    gift = new Circlet();
                    AbstractEvent.logMetricRelicSwap("N'loth", "Traded Relic", gift, choice2);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, gift);
                } else
                {
                    AbstractEvent.logMetricRelicSwap("N'loth", "Traded Relic", gift, choice2);
                    AbstractDungeon.player.loseRelic(choice2.relicId);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, gift);
                }
                screenNum = 1;
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                imageEventText.clearRemainingOptions();
                break;

            case 2: // '\002'
                AbstractEvent.logMetricIgnored("N'loth");
                imageEventText.updateBodyText(DIALOG_3);
                screenNum = 1;
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                imageEventText.clearRemainingOptions();
                break;

            default:
                imageEventText.updateBodyText(DIALOG_3);
                screenNum = 1;
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                imageEventText.clearRemainingOptions();
                break;
            }
            break;

        case 1: // '\001'
            openMap();
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "N'loth";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private int screenNum;
    private AbstractRelic choice1;
    private AbstractRelic choice2;
    private AbstractRelic gift;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("N'loth");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
    }
}
