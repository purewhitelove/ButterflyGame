// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MentalFortressUnlock.java

package com.megacrit.cardcrawl.unlock.cards.watcher;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.MentalFortress;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class MentalFortressUnlock extends AbstractUnlock
{

    public MentalFortressUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.CARD;
        card = CardLibrary.getCard("MentalFortress");
        key = card.cardID;
        title = card.name;
    }
}
