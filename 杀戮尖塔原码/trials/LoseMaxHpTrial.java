// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoseMaxHpTrial.java

package com.megacrit.cardcrawl.trials;

import com.megacrit.cardcrawl.daily.mods.NightTerrors;
import com.megacrit.cardcrawl.daily.mods.Terminal;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.trials:
//            AbstractTrial

public class LoseMaxHpTrial extends AbstractTrial
{

    public LoseMaxHpTrial()
    {
    }

    public ArrayList dailyModIDs()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Night Terrors");
        retVal.add("Terminal");
        return retVal;
    }
}
