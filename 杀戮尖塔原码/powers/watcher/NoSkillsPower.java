// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NoSkillsPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NoSkillsPower extends AbstractPower
{

    public NoSkillsPower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "NoSkills";
        this.owner = owner;
        amount = 1;
        updateDescription();
        loadRegion("entangle");
        isTurnBased = true;
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_ENTANGLED", 0.05F);
    }

    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0];
    }

    public boolean canPlayCard(AbstractCard card)
    {
        return card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(isPlayer)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "NoSkills"));
    }

    public static final String POWER_ID = "NoSkills";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("NoSkills");
    }
}
