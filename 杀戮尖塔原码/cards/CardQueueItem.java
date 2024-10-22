// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardQueueItem.java

package com.megacrit.cardcrawl.cards;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

// Referenced classes of package com.megacrit.cardcrawl.cards:
//            AbstractCard

public class CardQueueItem
{

    public CardQueueItem()
    {
        energyOnUse = 0;
        ignoreEnergyTotal = false;
        autoplayCard = false;
        randomTarget = false;
        isEndTurnAutoPlay = false;
        card = null;
        monster = null;
    }

    public CardQueueItem(AbstractCard card, boolean isEndTurnAutoPlay)
    {
        this(card, ((AbstractMonster) (null)));
        this.isEndTurnAutoPlay = isEndTurnAutoPlay;
    }

    public CardQueueItem(AbstractCard card, AbstractMonster monster)
    {
        this(card, monster, EnergyPanel.getCurrentEnergy(), false);
    }

    public CardQueueItem(AbstractCard card, AbstractMonster monster, int setEnergyOnUse)
    {
        this(card, monster, setEnergyOnUse, false);
    }

    public CardQueueItem(AbstractCard card, AbstractMonster monster, int setEnergyOnUse, boolean ignoreEnergyTotal)
    {
        this(card, monster, setEnergyOnUse, ignoreEnergyTotal, false);
    }

    public CardQueueItem(AbstractCard card, AbstractMonster monster, int setEnergyOnUse, boolean ignoreEnergyTotal, boolean autoplayCard)
    {
        energyOnUse = 0;
        this.ignoreEnergyTotal = false;
        this.autoplayCard = false;
        randomTarget = false;
        isEndTurnAutoPlay = false;
        this.card = card;
        this.monster = monster;
        energyOnUse = setEnergyOnUse;
        this.ignoreEnergyTotal = ignoreEnergyTotal;
        this.autoplayCard = autoplayCard;
    }

    public CardQueueItem(AbstractCard card, boolean randomTarget, int setEnergyOnUse, boolean ignoreEnergyTotal, boolean autoplayCard)
    {
        this(card, ((AbstractMonster) (null)), setEnergyOnUse, ignoreEnergyTotal, autoplayCard);
        this.randomTarget = randomTarget;
    }

    public AbstractCard card;
    public AbstractMonster monster;
    public int energyOnUse;
    public boolean ignoreEnergyTotal;
    public boolean autoplayCard;
    public boolean randomTarget;
    public boolean isEndTurnAutoPlay;
}
