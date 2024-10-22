// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HoarderTrial.java

package com.megacrit.cardcrawl.trials;

import com.megacrit.cardcrawl.daily.mods.Hoarder;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.trials:
//            AbstractTrial

public class HoarderTrial extends AbstractTrial
{

    public HoarderTrial()
    {
    }

    public ArrayList dailyModIDs()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Hoarder");
        return retVal;
    }
}
