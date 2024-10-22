// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SporeCloudPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, VulnerablePower

public class SporeCloudPower extends AbstractPower
{

    public SporeCloudPower(AbstractCreature owner, int vulnAmt)
    {
        name = NAME;
        ID = "Spore Cloud";
        this.owner = owner;
        amount = vulnAmt;
        updateDescription();
        loadRegion("sporeCloud");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void onDeath()
    {
        if(AbstractDungeon.getCurrRoom().isBattleEnding())
        {
            return;
        } else
        {
            CardCrawlGame.sound.play("SPORE_CLOUD_RELEASE");
            flashWithoutSound();
            addToTop(new ApplyPowerAction(AbstractDungeon.player, null, new VulnerablePower(AbstractDungeon.player, amount, true), amount));
            return;
        }
    }

    public static final String POWER_ID = "Spore Cloud";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Spore Cloud");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
