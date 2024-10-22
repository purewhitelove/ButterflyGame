// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlphaUnlock.java

package com.megacrit.cardcrawl.unlock.cards.watcher;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Alpha;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class AlphaUnlock extends AbstractUnlock
{

    public AlphaUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.CARD;
        card = CardLibrary.getCard("Alpha");
        key = card.cardID;
        title = card.name;
    }
}
