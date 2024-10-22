// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntanglePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class EntanglePower extends AbstractPower
{

    public EntanglePower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "Entangled";
        this.owner = owner;
        amount = 1;
        updateDescription();
        loadRegion("entangle");
        isTurnBased = true;
        type = AbstractPower.PowerType.DEBUFF;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_ENTANGLED", 0.05F);
    }

    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(isPlayer)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Entangled"));
    }

    public static final String POWER_ID = "Entangled";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Entangled");
    }
}
