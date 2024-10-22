// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NoxiousFumesPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, PoisonPower

public class NoxiousFumesPower extends AbstractPower
{

    public NoxiousFumesPower(AbstractCreature owner, int poisonAmount)
    {
        name = NAME;
        ID = "Noxious Fumes";
        this.owner = owner;
        amount = poisonAmount;
        updateDescription();
        loadRegion("fumes");
    }

    public void atStartOfTurnPostDraw()
    {
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(!m.isDead && !m.isDying)
                    addToBot(new ApplyPowerAction(m, owner, new PoisonPower(m, owner, amount), amount));
            } while(true);
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

    public static final String POWER_ID = "Noxious Fumes";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Noxious Fumes");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
