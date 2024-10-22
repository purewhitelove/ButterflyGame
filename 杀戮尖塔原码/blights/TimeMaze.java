// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimeMaze.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class TimeMaze extends AbstractBlight
{

    public TimeMaze()
    {
        super("TimeMaze", NAME, (new StringBuilder()).append(DESC[0]).append(15).append(DESC[1]).toString(), "maze.png", true);
        counter = 15;
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        if(counter < 15 && card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE)
        {
            counter++;
            if(counter >= 15)
                flash();
        }
    }

    public boolean canPlay(AbstractCard card)
    {
        if(counter >= 15 && card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE)
        {
            card.cantUseMessage = (new StringBuilder()).append(DESC[2]).append(15).append(DESC[1]).toString();
            return false;
        } else
        {
            return true;
        }
    }

    public void onVictory()
    {
        counter = -1;
    }

    public void atBattleStart()
    {
        counter = 0;
    }

    public void atTurnStart()
    {
        counter = 0;
    }

    public static final String ID = "TimeMaze";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];
    private static final int CARD_AMT = 15;

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("TimeMaze");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
