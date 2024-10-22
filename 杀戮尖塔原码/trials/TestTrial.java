// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TestTrial.java

package com.megacrit.cardcrawl.trials;

import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.daily.mods.*;
import com.megacrit.cardcrawl.relics.UnceasingTop;
import com.megacrit.cardcrawl.relics.deprecated.DerpRock;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.trials:
//            AbstractTrial

public class TestTrial extends AbstractTrial
{

    public TestTrial()
    {
    }

    public AbstractPlayer setupPlayer(AbstractPlayer player)
    {
        player.maxHealth = 20;
        player.currentHealth = 10;
        player.gold = 777;
        return player;
    }

    public boolean keepStarterRelic()
    {
        return false;
    }

    public List extraStartingRelicIDs()
    {
        return Arrays.asList(new String[] {
            "Derp Rock", "Unceasing Top"
        });
    }

    public boolean keepsStarterCards()
    {
        return true;
    }

    public List extraStartingCardIDs()
    {
        return Arrays.asList(new String[] {
            "Demon Form", "Wraith Form v2", "Echo Form"
        });
    }

    public boolean useRandomDailyMods()
    {
        return false;
    }

    public ArrayList dailyModIDs()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Diverse");
        retVal.add("Lethality");
        retVal.add("Time Dilation");
        retVal.add("Cursed Run");
        retVal.add("Elite Swarm");
        return retVal;
    }
}
