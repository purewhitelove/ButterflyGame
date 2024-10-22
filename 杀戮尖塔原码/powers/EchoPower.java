// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EchoPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class EchoPower extends AbstractPower
{

    public EchoPower(AbstractCreature owner, int amount)
    {
        cardsDoubledThisTurn = 0;
        name = NAME;
        ID = "Echo Form";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("echo");
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public void atStartOfTurn()
    {
        cardsDoubledThisTurn = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(!card.purgeOnUse && amount > 0 && AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - cardsDoubledThisTurn <= amount)
        {
            cardsDoubledThisTurn++;
            flash();
            AbstractMonster m = null;
            if(action.target != null)
                m = (AbstractMonster)action.target;
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float)Settings.WIDTH / 2.0F - 300F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            if(m != null)
                tmp.calculateCardDamage(m);
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
        }
    }

    public static final String POWER_ID = "Echo Form";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private int cardsDoubledThisTurn;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Echo Form");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
