// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoopPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class LoopPower extends AbstractPower
{

    public LoopPower(AbstractCreature owner, int amt)
    {
        name = NAME;
        ID = "Loop";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("loop");
    }

    public void atStartOfTurn()
    {
        if(!AbstractDungeon.player.orbs.isEmpty())
        {
            flash();
            for(int i = 0; i < amount; i++)
            {
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onStartOfTurn();
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onEndOfTurn();
            }

        }
    }

    public void updateDescription()
    {
        if(amount <= 1)
            description = DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public static final String POWER_ID = "Loop";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Loop");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
