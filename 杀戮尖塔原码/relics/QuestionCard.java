// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QuestionCard.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class QuestionCard extends AbstractRelic
{

    public QuestionCard()
    {
        super("Question Card", "questionCard.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public int changeNumberOfCardsInReward(int numberOfCards)
    {
        return numberOfCards + 1;
    }

    public AbstractRelic makeCopy()
    {
        return new QuestionCard();
    }

    private static final int CARDS_ADDED = 1;
    public static final String ID = "Question Card";
}
