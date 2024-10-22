// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATED_DarkCore.java

package com.megacrit.cardcrawl.relics.deprecated;

import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DEPRECATED_DarkCore extends AbstractRelic
{

    public DEPRECATED_DarkCore()
    {
        super("Dark Core", "vCore.png", com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.BOSS, com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new DEPRECATED_DarkCore();
    }

    public static final String ID = "Dark Core";
}
