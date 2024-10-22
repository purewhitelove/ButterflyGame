// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrikeDummyUnlock.java

package com.megacrit.cardcrawl.unlock.relics.watcher;

import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.StrikeDummy;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class StrikeDummyUnlock extends AbstractUnlock
{

    public StrikeDummyUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.RELIC;
        relic = RelicLibrary.getRelic("StrikeDummy");
        key = relic.relicId;
        title = relic.name;
    }
}
