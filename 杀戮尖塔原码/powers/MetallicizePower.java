// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetallicizePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class MetallicizePower extends AbstractPower
{

    public MetallicizePower(AbstractCreature owner, int armorAmt)
    {
        name = NAME;
        ID = "Metallicize";
        this.owner = owner;
        amount = armorAmt;
        updateDescription();
        loadRegion("armor");
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_METALLICIZE", 0.05F);
    }

    public void updateDescription()
    {
        if(owner.isPlayer)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[2]).append(amount).append(DESCRIPTIONS[3]).toString();
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer)
    {
        flash();
        addToBot(new GainBlockAction(owner, owner, amount));
    }

    public static final String POWER_ID = "Metallicize";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Metallicize");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
