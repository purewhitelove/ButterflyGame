// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GoldenIdolEvent.java

package com.megacrit.cardcrawl.events.exordium;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class GoldenIdolEvent extends AbstractImageEvent
{

    public GoldenIdolEvent()
    {
        super(NAME, DIALOG_START, "images/events/goldenIdol.jpg");
        screenNum = 0;
        relicMetric = null;
        imageEventText.setDialogOption(OPTIONS[0], new GoldenIdol());
        imageEventText.setDialogOption(OPTIONS[1]);
        if(AbstractDungeon.ascensionLevel >= 15)
        {
            damage = (int)((float)AbstractDungeon.player.maxHealth * 0.35F);
            maxHpLoss = (int)((float)AbstractDungeon.player.maxHealth * 0.1F);
        } else
        {
            damage = (int)((float)AbstractDungeon.player.maxHealth * 0.25F);
            maxHpLoss = (int)((float)AbstractDungeon.player.maxHealth * 0.08F);
        }
        if(maxHpLoss < 1)
            maxHpLoss = 1;
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_GOLDEN");
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(DIALOG_BOULDER);
                if(AbstractDungeon.player.hasRelic("Golden Idol"))
                    relicMetric = RelicLibrary.getRelic("Circlet").makeCopy();
                else
                    relicMetric = RelicLibrary.getRelic("Golden Idol").makeCopy();
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, relicMetric);
                CardCrawlGame.screenShake.mildRumble(5F);
                CardCrawlGame.sound.play("BLUNT_HEAVY");
                screenNum = 1;
                imageEventText.updateDialogOption(0, OPTIONS[2], CardLibrary.getCopy("Injury"));
                imageEventText.updateDialogOption(1, (new StringBuilder()).append(OPTIONS[3]).append(damage).append(OPTIONS[4]).toString());
                imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[5]).append(maxHpLoss).append(OPTIONS[6]).toString());
                break;

            default:
                imageEventText.updateBodyText(DIALOG_IGNORE);
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                imageEventText.clearRemainingOptions();
                AbstractEvent.logMetricIgnored("Golden Idol");
                break;
            }
            break;

        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, false);
                imageEventText.updateBodyText(DIALOG_CHOSE_RUN);
                com.megacrit.cardcrawl.cards.AbstractCard curse = new Injury();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                AbstractEvent.logMetricObtainCardAndRelic("Golden Idol", "Take Wound", curse, relicMetric);
                imageEventText.clearRemainingOptions();
                break;

            case 1: // '\001'
                imageEventText.updateBodyText(DIALOG_CHOSE_FIGHT);
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, false);
                CardCrawlGame.sound.play("BLUNT_FAST");
                AbstractDungeon.player.damage(new DamageInfo(null, damage));
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                AbstractEvent.logMetricObtainRelicAndDamage("Golden Idol", "Take Damage", relicMetric, damage);
                imageEventText.clearRemainingOptions();
                break;

            case 2: // '\002'
                imageEventText.updateBodyText(DIALOG_CHOSE_FLAT);
                AbstractDungeon.player.decreaseMaxHealth(maxHpLoss);
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, false);
                CardCrawlGame.sound.play("BLUNT_FAST");
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                AbstractEvent.logMetricObtainRelicAndLoseMaxHP("Golden Idol", "Lose Max HP", relicMetric, maxHpLoss);
                imageEventText.clearRemainingOptions();
                break;

            default:
                openMap();
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

    public static final String ID = "Golden Idol";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_START;
    private static final String DIALOG_BOULDER;
    private static final String DIALOG_CHOSE_RUN;
    private static final String DIALOG_CHOSE_FIGHT;
    private static final String DIALOG_CHOSE_FLAT;
    private static final String DIALOG_IGNORE;
    private int screenNum;
    private static final float HP_LOSS_PERCENT = 0.25F;
    private static final float MAX_HP_LOSS_PERCENT = 0.08F;
    private static final float A_2_HP_LOSS_PERCENT = 0.35F;
    private static final float A_2_MAX_HP_LOSS_PERCENT = 0.1F;
    private int damage;
    private int maxHpLoss;
    private AbstractRelic relicMetric;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Golden Idol");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_START = DESCRIPTIONS[0];
        DIALOG_BOULDER = DESCRIPTIONS[1];
        DIALOG_CHOSE_RUN = DESCRIPTIONS[2];
        DIALOG_CHOSE_FIGHT = DESCRIPTIONS[3];
        DIALOG_CHOSE_FLAT = DESCRIPTIONS[4];
        DIALOG_IGNORE = DESCRIPTIONS[5];
    }
}
