// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractTrial.java

package com.megacrit.cardcrawl.trials;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import java.util.ArrayList;
import java.util.List;

public class AbstractTrial
{

    public AbstractTrial()
    {
        relics = new ArrayList();
    }

    public AbstractPlayer setupPlayer(AbstractPlayer player)
    {
        return player;
    }

    public boolean keepStarterRelic()
    {
        return true;
    }

    public List extraStartingRelicIDs()
    {
        return new ArrayList();
    }

    public boolean keepsStarterCards()
    {
        return true;
    }

    public List extraStartingCardIDs()
    {
        return new ArrayList();
    }

    public boolean useRandomDailyMods()
    {
        return false;
    }

    public ArrayList dailyModIDs()
    {
        return new ArrayList();
    }

    public String name;
    public com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c;
    public int energy;
    public CardGroup deck;
    public ArrayList relics;
}
