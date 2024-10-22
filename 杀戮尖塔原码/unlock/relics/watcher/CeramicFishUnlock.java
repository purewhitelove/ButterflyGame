// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CeramicFishUnlock.java

package com.megacrit.cardcrawl.unlock.relics.watcher;

import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CeramicFish;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class CeramicFishUnlock extends AbstractUnlock
{

    public CeramicFishUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.RELIC;
        relic = RelicLibrary.getRelic("CeramicFish");
        key = relic.relicId;
        title = relic.name;
    }
}
