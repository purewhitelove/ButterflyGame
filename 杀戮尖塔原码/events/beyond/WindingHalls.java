// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WindingHalls.java

package com.megacrit.cardcrawl.events.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.List;

public class WindingHalls extends AbstractImageEvent
{

    public WindingHalls()
    {
        super(NAME, INTRO_BODY1, "images/events/winding.jpg");
        screenNum = 0;
        if(AbstractDungeon.ascensionLevel >= 15)
        {
            hpAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.18F);
            healAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.2F);
        } else
        {
            hpAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.125F);
            healAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.25F);
        }
        maxHPAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.05F);
        imageEventText.setDialogOption(OPTIONS[0]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_WINDING");
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            imageEventText.updateBodyText(INTRO_BODY2);
            screenNum = 1;
            imageEventText.updateDialogOption(0, (new StringBuilder()).append(OPTIONS[1]).append(hpAmt).append(OPTIONS[2]).toString(), CardLibrary.getCopy("Madness"));
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[3]).append(healAmt).append(OPTIONS[5]).toString(), CardLibrary.getCopy("Writhe"));
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[6]).append(maxHPAmt).append(OPTIONS[7]).toString());
            break;

        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                List cards = new ArrayList();
                cards.add("Madness");
                cards.add("Madness");
                logMetric("Winding Halls", "Embrace Madness", cards, null, null, null, null, null, null, hpAmt, 0, 0, 0, 0, 0);
                imageEventText.updateBodyText(CHOICE_1_TEXT);
                AbstractDungeon.player.damage(new DamageInfo(null, hpAmt));
                CardCrawlGame.sound.play("ATTACK_MAGIC_SLOW_1");
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Madness(), (float)Settings.WIDTH / 2.0F - 350F * Settings.xScale, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Madness(), (float)Settings.WIDTH / 2.0F + 350F * Settings.xScale, (float)Settings.HEIGHT / 2.0F));
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                imageEventText.clearRemainingOptions();
                break;

            case 1: // '\001'
                imageEventText.updateBodyText(CHOICE_2_TEXT);
                AbstractDungeon.player.heal(healAmt);
                com.megacrit.cardcrawl.cards.AbstractCard c = new Writhe();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F + 10F * Settings.xScale, (float)Settings.HEIGHT / 2.0F));
                logMetricObtainCardAndHeal("Winding Halls", "Writhe", c, healAmt);
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                imageEventText.clearRemainingOptions();
                break;

            case 2: // '\002'
                screenNum = 2;
                imageEventText.updateBodyText(DESCRIPTIONS[4]);
                logMetricMaxHPLoss("Winding Halls", "Max HP", maxHPAmt);
                AbstractDungeon.player.decreaseMaxHealth(maxHPAmt);
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.LOW, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, true);
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                imageEventText.clearRemainingOptions();
                break;
            }
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "Winding Halls";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final float HP_LOSS_PERCENT = 0.125F;
    private static final float HP_MAX_LOSS_PERCENT = 0.05F;
    private static final float A_2_HP_LOSS_PERCENT = 0.18F;
    private static final float HEAL_AMT = 0.25F;
    private static final float A_2_HEAL_AMT = 0.2F;
    private int hpAmt;
    private int healAmt;
    private int maxHPAmt;
    private static final String INTRO_BODY1;
    private static final String INTRO_BODY2;
    private static final String CHOICE_1_TEXT;
    private static final String CHOICE_2_TEXT;
    private int screenNum;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Winding Halls");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_BODY1 = DESCRIPTIONS[0];
        INTRO_BODY2 = DESCRIPTIONS[1];
        CHOICE_1_TEXT = DESCRIPTIONS[2];
        CHOICE_2_TEXT = DESCRIPTIONS[3];
    }
}
