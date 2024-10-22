// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NeutralStance.java

package com.megacrit.cardcrawl.stances;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.StanceStrings;

// Referenced classes of package com.megacrit.cardcrawl.stances:
//            AbstractStance

public class NeutralStance extends AbstractStance
{

    public NeutralStance()
    {
        ID = "Neutral";
        img = null;
        name = null;
        updateDescription();
    }

    public void updateDescription()
    {
        description = stanceString.DESCRIPTION[0];
    }

    public void onEnterStance()
    {
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public static final String STANCE_ID = "Neutral";
    private static final StanceStrings stanceString;

    static 
    {
        stanceString = CardCrawlGame.languagePack.getStanceString("Neutral");
    }
}
