// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ForgottenAltar.java

package com.megacrit.cardcrawl.events.city;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class ForgottenAltar extends AbstractImageEvent
{

    public ForgottenAltar()
    {
        super(NAME, DIALOG_1, "images/events/forgottenAltar.jpg");
        if(AbstractDungeon.player.hasRelic("Golden Idol"))
            imageEventText.setDialogOption(OPTIONS[0], !AbstractDungeon.player.hasRelic("Golden Idol"), new BloodyIdol());
        else
            imageEventText.setDialogOption(OPTIONS[1], !AbstractDungeon.player.hasRelic("Golden Idol"), new BloodyIdol());
        if(AbstractDungeon.ascensionLevel >= 15)
            hpLoss = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.35F);
        else
            hpLoss = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.25F);
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(5).append(OPTIONS[3]).append(hpLoss).append(OPTIONS[4]).toString());
        imageEventText.setDialogOption(OPTIONS[6], CardLibrary.getCopy("Decay"));
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_FORGOTTEN");
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                gainChalice();
                showProceedScreen(DIALOG_2);
                CardCrawlGame.sound.play("HEAL_1");
                break;

            case 1: // '\001'
                AbstractDungeon.player.increaseMaxHp(5, false);
                AbstractDungeon.player.damage(new DamageInfo(null, hpLoss));
                CardCrawlGame.sound.play("HEAL_3");
                showProceedScreen(DIALOG_3);
                logMetricDamageAndMaxHPGain("Forgotten Altar", "Shed Blood", hpLoss, 5);
                break;

            case 2: // '\002'
                CardCrawlGame.sound.play("BLUNT_HEAVY");
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, true);
                com.megacrit.cardcrawl.cards.AbstractCard curse = new Decay();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, Settings.WIDTH / 2, Settings.HEIGHT / 2));
                showProceedScreen(DIALOG_4);
                logMetricObtainCard("Forgotten Altar", "Smashed Altar", curse);
                break;
            }
            break;

        default:
            openMap();
            break;
        }
    }

    public void gainChalice()
    {
        int relicAtIndex = 0;
        int i = 0;
        do
        {
            if(i >= AbstractDungeon.player.relics.size())
                break;
            if(((AbstractRelic)AbstractDungeon.player.relics.get(i)).relicId.equals("Golden Idol"))
            {
                relicAtIndex = i;
                break;
            }
            i++;
        } while(true);
        if(AbstractDungeon.player.hasRelic("Bloody Idol"))
        {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, RelicLibrary.getRelic("Circlet").makeCopy());
            logMetricRelicSwap("Forgotten Altar", "Gave Idol", new Circlet(), new GoldenIdol());
        } else
        {
            ((AbstractRelic)AbstractDungeon.player.relics.get(relicAtIndex)).onUnequip();
            AbstractRelic bloodyIdol = RelicLibrary.getRelic("Bloody Idol").makeCopy();
            bloodyIdol.instantObtain(AbstractDungeon.player, relicAtIndex, false);
            logMetricRelicSwap("Forgotten Altar", "Gave Idol", new BloodyIdol(), new GoldenIdol());
        }
    }

    public static final String ID = "Forgotten Altar";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private static final String DIALOG_4;
    private static final float HP_LOSS_PERCENT = 0.25F;
    private static final float A_2_HP_LOSS_PERCENT = 0.35F;
    private int hpLoss;
    private static final int MAX_HP_GAIN = 5;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Forgotten Altar");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
        DIALOG_4 = DESCRIPTIONS[3];
    }
}
