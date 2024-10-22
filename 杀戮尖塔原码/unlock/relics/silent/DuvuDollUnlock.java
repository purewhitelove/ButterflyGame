// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DuvuDollUnlock.java

package com.megacrit.cardcrawl.unlock.relics.silent;

import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class DuvuDollUnlock extends AbstractUnlock
{

    public DuvuDollUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.RELIC;
        relic = RelicLibrary.getRelic("Du-Vu Doll");
        key = relic.relicId;
        title = relic.name;
    }
}
