// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WinterPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class WinterPower extends AbstractPower
{

    public WinterPower(AbstractCreature owner, int orbAmt)
    {
        name = NAME;
        ID = "Winter";
        this.owner = owner;
        amount = orbAmt;
        updateDescription();
        loadRegion("winter");
    }

    public void atStartOfTurn()
    {
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            Iterator iterator = AbstractDungeon.player.orbs.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractOrb o = (AbstractOrb)iterator.next();
                if(!(o instanceof EmptyOrbSlot))
                    continue;
                flash();
                break;
            } while(true);
            for(int i = 0; i < amount; i++)
                addToBot(new ChannelAction(new Frost(), false));

        }
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        amount += stackAmount;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "Winter";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Winter");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
