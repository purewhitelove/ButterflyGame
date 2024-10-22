// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarterDeckTrial.java

package com.megacrit.cardcrawl.trials;

import com.megacrit.cardcrawl.daily.mods.Binary;
import com.megacrit.cardcrawl.relics.BustedCrown;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.trials:
//            AbstractTrial

public class StarterDeckTrial extends AbstractTrial
{

    public StarterDeckTrial()
    {
    }

    public List extraStartingRelicIDs()
    {
        return Collections.singletonList("Busted Crown");
    }

    public ArrayList dailyModIDs()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Binary");
        return retVal;
    }
}
