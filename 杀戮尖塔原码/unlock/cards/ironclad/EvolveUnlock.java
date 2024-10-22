// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EvolveUnlock.java

package com.megacrit.cardcrawl.unlock.cards.ironclad;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Evolve;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class EvolveUnlock extends AbstractUnlock
{

    public EvolveUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.CARD;
        card = CardLibrary.getCard("Evolve");
        key = card.cardID;
        title = card.name;
    }
}
