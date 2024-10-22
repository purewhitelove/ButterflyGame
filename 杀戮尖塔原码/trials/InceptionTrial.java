// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InceptionTrial.java

package com.megacrit.cardcrawl.trials;

import com.megacrit.cardcrawl.relics.UnceasingTop;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.trials:
//            AbstractTrial

public class InceptionTrial extends AbstractTrial
{

    public InceptionTrial()
    {
    }

    public boolean keepStarterRelic()
    {
        return false;
    }

    public ArrayList dailyModIDs()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Unceasing Top");
        return retVal;
    }
}
