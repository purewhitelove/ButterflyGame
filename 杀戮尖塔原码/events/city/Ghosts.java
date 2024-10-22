// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Ghosts.java

package com.megacrit.cardcrawl.events.city;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.List;

public class Ghosts extends AbstractImageEvent
{

    public Ghosts()
    {
        super(NAME, "test", "images/events/ghost.jpg");
        screenNum = 0;
        hpLoss = 0;
        body = INTRO_BODY_M;
        hpLoss = MathUtils.ceil((float)AbstractDungeon.player.maxHealth * 0.5F);
        if(hpLoss >= AbstractDungeon.player.maxHealth)
            hpLoss = AbstractDungeon.player.maxHealth - 1;
        if(AbstractDungeon.ascensionLevel >= 15)
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[3]).append(hpLoss).append(OPTIONS[1]).toString(), new Apparition());
        else
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(hpLoss).append(OPTIONS[1]).toString(), new Apparition());
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_GHOSTS");
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(ACCEPT_BODY);
                AbstractDungeon.player.decreaseMaxHealth(hpLoss);
                becomeGhost();
                screenNum = 1;
                imageEventText.updateDialogOption(0, OPTIONS[5]);
                imageEventText.clearRemainingOptions();
                break;

            default:
                logMetricIgnored("Ghosts");
                imageEventText.updateBodyText(EXIT_BODY);
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[5]);
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

    private void becomeGhost()
    {
        List cards = new ArrayList();
        int amount = 5;
        if(AbstractDungeon.ascensionLevel >= 15)
            amount -= 2;
        for(int i = 0; i < amount; i++)
        {
            AbstractCard c = new Apparition();
            cards.add(c.cardID);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }

        logMetricObtainCardsLoseMapHP("Ghosts", "Became a Ghost", cards, hpLoss);
    }

    public static final String ID = "Ghosts";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String INTRO_BODY_M;
    private static final String ACCEPT_BODY;
    private static final String EXIT_BODY;
    private static final float HP_DRAIN = 0.5F;
    private int screenNum;
    private int hpLoss;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Ghosts");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_BODY_M = DESCRIPTIONS[0];
        ACCEPT_BODY = DESCRIPTIONS[2];
        EXIT_BODY = DESCRIPTIONS[3];
    }
}
