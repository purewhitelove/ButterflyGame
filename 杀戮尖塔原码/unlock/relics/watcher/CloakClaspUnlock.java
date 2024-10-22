// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CloakClaspUnlock.java

package com.megacrit.cardcrawl.unlock.relics.watcher;

import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CloakClasp;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class CloakClaspUnlock extends AbstractUnlock
{

    public CloakClaspUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.RELIC;
        relic = RelicLibrary.getRelic("CloakClasp");
        key = relic.relicId;
        title = relic.name;
    }
}
