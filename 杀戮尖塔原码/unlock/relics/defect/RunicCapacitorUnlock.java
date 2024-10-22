// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RunicCapacitorUnlock.java

package com.megacrit.cardcrawl.unlock.relics.defect;

import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RunicCapacitor;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class RunicCapacitorUnlock extends AbstractUnlock
{

    public RunicCapacitorUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.RELIC;
        relic = RelicLibrary.getRelic("Runic Capacitor");
        key = relic.relicId;
        title = relic.name;
    }
}
