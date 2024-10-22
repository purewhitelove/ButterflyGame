// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CablesUnlock.java

package com.megacrit.cardcrawl.unlock.relics.defect;

import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.GoldPlatedCables;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class CablesUnlock extends AbstractUnlock
{

    public CablesUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.RELIC;
        relic = RelicLibrary.getRelic("Cables");
        key = relic.relicId;
        title = relic.name;
    }
}
