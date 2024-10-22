// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SunderUnlock.java

package com.megacrit.cardcrawl.unlock.cards.defect;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Sunder;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class SunderUnlock extends AbstractUnlock
{

    public SunderUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.CARD;
        card = CardLibrary.getCard("Sunder");
        key = card.cardID;
        title = card.name;
    }
}
