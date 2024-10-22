// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimeMazePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class TimeMazePower extends AbstractPower
{

    public TimeMazePower(AbstractCreature owner, int maxAmount)
    {
        name = NAME;
        ID = "TimeMazePower";
        this.owner = owner;
        amount = maxAmount;
        this.maxAmount = maxAmount;
        updateDescription();
        loadRegion("time");
        type = AbstractPower.PowerType.BUFF;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESC[0]).append(maxAmount).append(DESC[1]).toString();
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        flashWithoutSound();
        amount--;
        if(amount == 0)
        {
            amount = maxAmount;
            AbstractDungeon.actionManager.cardQueue.clear();
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.player.limbo.group.iterator(); iterator.hasNext(); AbstractDungeon.effectList.add(new ExhaustCardEffect(c)))
                c = (AbstractCard)iterator.next();

            AbstractDungeon.player.limbo.group.clear();
            AbstractDungeon.player.releaseCard();
            AbstractDungeon.overlayMenu.endTurnButton.disable(true);
        }
        updateDescription();
    }

    public void atStartOfTurn()
    {
        amount = 15;
    }

    public static final String POWER_ID = "TimeMazePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESC[];
    private int maxAmount;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("TimeMazePower");
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
