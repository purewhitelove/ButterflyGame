// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TinyChestUnlock.java

package com.megacrit.cardcrawl.unlock.relics.silent;

import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.TinyChest;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class TinyChestUnlock extends AbstractUnlock
{

    public TinyChestUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.RELIC;
        relic = RelicLibrary.getRelic("Tiny Chest");
        key = relic.relicId;
        title = relic.name;
    }
}
