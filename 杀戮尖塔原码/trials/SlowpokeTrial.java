// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SlowpokeTrial.java

package com.megacrit.cardcrawl.trials;

import com.megacrit.cardcrawl.daily.mods.TimeDilation;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.trials:
//            AbstractTrial

public class SlowpokeTrial extends AbstractTrial
{

    public SlowpokeTrial()
    {
    }

    public ArrayList dailyModIDs()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Time Dilation");
        return retVal;
    }
}
