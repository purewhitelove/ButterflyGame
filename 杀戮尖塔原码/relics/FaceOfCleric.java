// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FaceOfCleric.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class FaceOfCleric extends AbstractRelic
{

    public FaceOfCleric()
    {
        super("FaceOfCleric", "clericFace.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onVictory()
    {
        flash();
        AbstractDungeon.player.increaseMaxHp(1, true);
    }

    public AbstractRelic makeCopy()
    {
        return new FaceOfCleric();
    }

    public static final String ID = "FaceOfCleric";
}
