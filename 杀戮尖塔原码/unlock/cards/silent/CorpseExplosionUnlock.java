// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CorpseExplosionUnlock.java

package com.megacrit.cardcrawl.unlock.cards.silent;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.CorpseExplosion;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class CorpseExplosionUnlock extends AbstractUnlock
{

    public CorpseExplosionUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.CARD;
        card = CardLibrary.getCard("Corpse Explosion");
        key = card.cardID;
        title = card.name;
    }
}
