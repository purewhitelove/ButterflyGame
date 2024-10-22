// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DarkEmbracePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class DarkEmbracePower extends AbstractPower
{

    public DarkEmbracePower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Dark Embrace";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("darkembrace");
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public void onExhaust(AbstractCard card)
    {
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            addToBot(new DrawCardAction(owner, amount));
        }
    }

    public static final String POWER_ID = "Dark Embrace";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Dark Embrace");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
