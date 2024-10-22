// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MyTrueFormTrial.java

package com.megacrit.cardcrawl.trials;

import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.purple.DevaForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.trials:
//            AbstractTrial

public class MyTrueFormTrial extends AbstractTrial
{

    public MyTrueFormTrial()
    {
    }

    public ArrayList dailyModIDs()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Demon Form");
        retVal.add("Wraith Form v2");
        retVal.add("Echo Form");
        retVal.add("DevaForm");
        return retVal;
    }
}
