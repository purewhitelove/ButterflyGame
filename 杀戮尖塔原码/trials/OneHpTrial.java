// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OneHpTrial.java

package com.megacrit.cardcrawl.trials;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

// Referenced classes of package com.megacrit.cardcrawl.trials:
//            AbstractTrial

public class OneHpTrial extends AbstractTrial
{

    public OneHpTrial()
    {
    }

    public AbstractPlayer setupPlayer(AbstractPlayer player)
    {
        player.currentHealth = 1;
        player.maxHealth = 1;
        return player;
    }
}
