// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConstrictedPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class ConstrictedPower extends AbstractPower
{

    public ConstrictedPower(AbstractCreature target, AbstractCreature source, int fadeAmt)
    {
        name = NAME;
        ID = "Constricted";
        owner = target;
        this.source = source;
        amount = fadeAmt;
        updateDescription();
        loadRegion("constricted");
        type = AbstractPower.PowerType.DEBUFF;
        priority = 105;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_CONSTRICTED", 0.05F);
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        flashWithoutSound();
        playApplyPowerSfx();
        addToBot(new DamageAction(owner, new DamageInfo(source, amount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS)));
    }

    public static final String POWER_ID = "Constricted";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public AbstractCreature source;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Constricted");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
