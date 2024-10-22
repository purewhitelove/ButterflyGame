// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MoaiHead.java

package com.megacrit.cardcrawl.events.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.GoldenIdol;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import java.util.ArrayList;

public class MoaiHead extends AbstractImageEvent
{

    public MoaiHead()
    {
        super(NAME, INTRO_BODY, "images/events/moaiHead.jpg");
        hpAmt = 0;
        screenNum = 0;
        if(AbstractDungeon.ascensionLevel >= 15)
            hpAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.18F);
        else
            hpAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.125F);
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(hpAmt).append(OPTIONS[1]).toString());
        if(AbstractDungeon.player.hasRelic("Golden Idol"))
            imageEventText.setDialogOption(OPTIONS[2], !AbstractDungeon.player.hasRelic("Golden Idol"));
        else
            imageEventText.setDialogOption(OPTIONS[3], !AbstractDungeon.player.hasRelic("Golden Idol"));
        imageEventText.setDialogOption(OPTIONS[4]);
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(DESCRIPTIONS[1]);
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, true);
                CardCrawlGame.sound.play("BLUNT_HEAVY");
                AbstractDungeon.player.maxHealth = AbstractDungeon.player.maxHealth - hpAmt;
                if(AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth)
                    AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                if(AbstractDungeon.player.maxHealth < 1)
                    AbstractDungeon.player.maxHealth = 1;
                AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                logMetricHealAndLoseMaxHP("The Moai Head", "Heal", AbstractDungeon.player.maxHealth, hpAmt);
                screenNum = 1;
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                imageEventText.clearRemainingOptions();
                break;

            case 1: // '\001'
                logMetricGainGoldAndLoseRelic("The Moai Head", "Gave Idol", new GoldenIdol(), 333);
                imageEventText.updateBodyText(DESCRIPTIONS[2]);
                screenNum = 1;
                AbstractDungeon.player.loseRelic("Golden Idol");
                AbstractDungeon.effectList.add(new RainingGoldEffect(333));
                AbstractDungeon.player.gainGold(333);
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                imageEventText.clearRemainingOptions();
                break;

            default:
                logMetricIgnored("The Moai Head");
                imageEventText.updateBodyText(DESCRIPTIONS[3]);
                screenNum = 1;
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

    public static final String ID = "The Moai Head";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final float HP_LOSS_PERCENT = 0.125F;
    private static final float A_2_HP_LOSS_PERCENT = 0.18F;
    private int hpAmt;
    private static final int goldAmount = 333;
    private static final String INTRO_BODY;
    private int screenNum;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("The Moai Head");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_BODY = DESCRIPTIONS[0];
    }
}
