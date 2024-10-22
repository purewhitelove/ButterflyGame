// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardSave.java

package com.megacrit.cardcrawl.cards;


public class CardSave
{

    public CardSave(String cardID, int timesUpgraded, int misc)
    {
        id = cardID;
        upgrades = timesUpgraded;
        this.misc = misc;
    }

    public int upgrades;
    public int misc;
    public String id;
}
