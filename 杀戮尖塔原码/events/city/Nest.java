// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Nest.java

package com.megacrit.cardcrawl.events.city;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class Nest extends AbstractImageEvent
{

    public Nest()
    {
        super(NAME, INTRO_BODY_M, "images/events/theNest.jpg");
        screenNum = 0;
        imageEventText.setDialogOption(OPTIONS[5]);
        if(AbstractDungeon.ascensionLevel >= 15)
            goldGain = 50;
        else
            goldGain = 99;
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            imageEventText.updateBodyText(INTRO_BODY_M_2);
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(6).append(OPTIONS[1]).toString(), new RitualDagger());
            UnlockTracker.markCardAsSeen("RitualDagger");
            imageEventText.updateDialogOption(0, (new StringBuilder()).append(OPTIONS[2]).append(goldGain).append(OPTIONS[3]).toString());
            screenNum = 1;
            break;

        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                logMetricGainGold("Nest", "Stole From Cult", goldGain);
                imageEventText.updateBodyText(EXIT_BODY);
                screenNum = 2;
                AbstractDungeon.effectList.add(new RainingGoldEffect(goldGain));
                AbstractDungeon.player.gainGold(goldGain);
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                imageEventText.clearRemainingOptions();
                break;

            case 1: // '\001'
                com.megacrit.cardcrawl.cards.AbstractCard c = new RitualDagger();
                logMetricObtainCardAndDamage("Nest", "Joined the Cult", c, 6);
                imageEventText.updateBodyText(ACCEPT_BODY);
                AbstractDungeon.player.damage(new DamageInfo(null, 6));
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                imageEventText.clearRemainingOptions();
                break;
            }
            break;

        case 2: // '\002'
            openMap();
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "Nest";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String INTRO_BODY_M;
    private static final String INTRO_BODY_M_2;
    private static final String ACCEPT_BODY;
    private static final String EXIT_BODY;
    private static final int HP_LOSS = 6;
    private int goldGain;
    private int screenNum;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Nest");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_BODY_M = DESCRIPTIONS[0];
        INTRO_BODY_M_2 = DESCRIPTIONS[1];
        ACCEPT_BODY = DESCRIPTIONS[2];
        EXIT_BODY = DESCRIPTIONS[3];
    }
}
