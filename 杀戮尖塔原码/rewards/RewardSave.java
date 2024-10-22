// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RewardSave.java

package com.megacrit.cardcrawl.rewards;


public class RewardSave
{

    public RewardSave(String type, String id, int amount, int bonusGold)
    {
        this.type = type;
        this.id = id;
        this.amount = amount;
    }

    public RewardSave(String type, String id)
    {
        this(type, id, 0, 0);
    }

    public String type;
    public String id;
    public int amount;
    public int bonusGold;
}
