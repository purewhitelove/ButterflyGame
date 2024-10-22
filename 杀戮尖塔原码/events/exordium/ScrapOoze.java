// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScrapOoze.java

package com.megacrit.cardcrawl.events.exordium;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScrapOoze extends AbstractImageEvent
{

    public ScrapOoze()
    {
        super(NAME, DIALOG_1, "images/events/scrapOoze.jpg");
        relicObtainChance = 25;
        dmg = 3;
        totalDamageDealt = 0;
        screenNum = 0;
        if(AbstractDungeon.ascensionLevel >= 15)
            dmg = 5;
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(dmg).append(OPTIONS[1]).append(relicObtainChance).append(OPTIONS[2]).toString());
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_OOZE");
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                AbstractDungeon.player.damage(new DamageInfo(null, dmg));
                CardCrawlGame.sound.play("ATTACK_POISON");
                totalDamageDealt += dmg;
                int random = AbstractDungeon.miscRng.random(0, 99);
                if(random >= 99 - relicObtainChance)
                {
                    imageEventText.updateBodyText(SUCCESS_MSG);
                    AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                    AbstractEvent.logMetricObtainRelicAndDamage("Scrap Ooze", "Success", r, totalDamageDealt);
                    imageEventText.updateDialogOption(0, OPTIONS[3]);
                    imageEventText.removeDialogOption(1);
                    screenNum = 1;
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, r);
                } else
                {
                    imageEventText.updateBodyText(FAIL_MSG);
                    relicObtainChance += 10;
                    dmg++;
                    imageEventText.updateDialogOption(0, (new StringBuilder()).append(OPTIONS[4]).append(dmg).append(OPTIONS[1]).append(relicObtainChance).append(OPTIONS[2]).toString());
                    imageEventText.updateDialogOption(1, OPTIONS[3]);
                }
                break;

            case 1: // '\001'
                AbstractEvent.logMetricTakeDamage("Scrap Ooze", "Fled", totalDamageDealt);
                imageEventText.updateBodyText(ESCAPE_MSG);
                imageEventText.updateDialogOption(0, OPTIONS[3]);
                imageEventText.removeDialogOption(1);
                screenNum = 1;
                break;

            default:
                logger.info((new StringBuilder()).append("ERROR: case ").append(buttonPressed).append(" should never be called").toString());
                break;
            }
            break;

        case 1: // '\001'
            openMap();
            break;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/events/exordium/ScrapOoze.getName());
    public static final String ID = "Scrap Ooze";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private int relicObtainChance;
    private int dmg;
    private int totalDamageDealt;
    private int screenNum;
    private static final String DIALOG_1;
    private static final String FAIL_MSG;
    private static final String SUCCESS_MSG;
    private static final String ESCAPE_MSG;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Scrap Ooze");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        FAIL_MSG = DESCRIPTIONS[1];
        SUCCESS_MSG = DESCRIPTIONS[2];
        ESCAPE_MSG = DESCRIPTIONS[3];
    }
}
