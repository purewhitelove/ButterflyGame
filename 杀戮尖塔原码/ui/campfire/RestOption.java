// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RestOption.java

package com.megacrit.cardcrawl.ui.campfire;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.blights.Muzzle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.NightTerrors;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RegalPillow;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui.campfire:
//            AbstractCampfireOption

public class RestOption extends AbstractCampfireOption
{

    public RestOption(boolean active)
    {
        int healAmt;
        label = TEXT[0];
        usable = active;
        if(ModHelper.isModEnabled("Night Terrors"))
            healAmt = (int)((float)AbstractDungeon.player.maxHealth * 1.0F);
        else
            healAmt = (int)((float)AbstractDungeon.player.maxHealth * 0.3F);
        if(Settings.isEndless && AbstractDungeon.player.hasBlight("FullBelly"))
            healAmt /= 2;
        if(!ModHelper.isModEnabled("Night Terrors")) goto _L2; else goto _L1
_L1:
        description = (new StringBuilder()).append(TEXT[1]).append(healAmt).append(")").append(LocalizedStrings.PERIOD).toString();
        if(!AbstractDungeon.player.hasRelic("Regal Pillow")) goto _L4; else goto _L3
_L3:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        description;
        append();
        "\n+15";
        append();
        TEXT[2];
        append();
        AbstractDungeon.player.getRelic("Regal Pillow").name;
        append();
        LocalizedStrings.PERIOD;
        append();
        toString();
        description;
          goto _L4
_L2:
        description = (new StringBuilder()).append(TEXT[3]).append(healAmt).append(")").append(LocalizedStrings.PERIOD).toString();
        if(!AbstractDungeon.player.hasRelic("Regal Pillow")) goto _L4; else goto _L5
_L5:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        description;
        append();
        "\n+15";
        append();
        TEXT[2];
        append();
        AbstractDungeon.player.getRelic("Regal Pillow").name;
        append();
        LocalizedStrings.PERIOD;
        append();
        toString();
        description;
_L4:
        updateUsability(active);
        return;
    }

    public void updateUsability(boolean canUse)
    {
        if(!canUse)
            description = TEXT[4];
        img = ImageMaster.CAMPFIRE_REST_BUTTON;
    }

    public void useOption()
    {
        CardCrawlGame.sound.play("SLEEP_BLANKET");
        AbstractDungeon.effectList.add(new CampfireSleepEffect());
        for(int i = 0; i < 30; i++)
            AbstractDungeon.topLevelEffects.add(new CampfireSleepScreenCoverEffect());

        CardCrawlGame.metricData.campfire_rested++;
        CardCrawlGame.metricData.addCampfireChoiceData("REST");
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Rest Option");
        TEXT = uiStrings.TEXT;
    }
}
