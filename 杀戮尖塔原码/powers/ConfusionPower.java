// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfusionPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.random.Random;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class ConfusionPower extends AbstractPower
{

    public ConfusionPower(AbstractCreature owner)
    {
        name = NAME;
        ID = "Confusion";
        this.owner = owner;
        updateDescription();
        loadRegion("confusion");
        type = AbstractPower.PowerType.DEBUFF;
        priority = 0;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_CONFUSION", 0.05F);
    }

    public void onCardDraw(AbstractCard card)
    {
        if(card.cost >= 0)
        {
            int newCost = AbstractDungeon.cardRandomRng.random(3);
            if(card.cost != newCost)
            {
                card.cost = newCost;
                card.costForTurn = card.cost;
                card.isCostModified = true;
            }
            card.freeToPlayOnce = false;
        }
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    public static final String POWER_ID = "Confusion";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Confusion");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
