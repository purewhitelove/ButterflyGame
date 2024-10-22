// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GrotesqueTrophy.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Pride;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class GrotesqueTrophy extends AbstractBlight
{

    public GrotesqueTrophy()
    {
        super("GrotesqueTrophy", NAME, (new StringBuilder()).append(DESC[0]).append(3).append(DESC[1]).toString(), "trophy.png", false);
        counter = 1;
    }

    public void onEquip()
    {
        CardGroup group = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        for(int i = 0; i < 3; i++)
        {
            AbstractCard bloatCard = new Pride();
            UnlockTracker.markCardAsSeen(bloatCard.cardID);
            group.addToBottom(bloatCard.makeCopy());
        }

        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, DESC[2]);
    }

    public void stack()
    {
        counter++;
        flash();
        CardGroup group = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        for(int i = 0; i < 3; i++)
        {
            AbstractCard bloatCard = new Pride();
            UnlockTracker.markCardAsSeen(bloatCard.cardID);
            group.addToBottom(bloatCard.makeCopy());
        }

        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, DESC[2]);
    }

    public static final String ID = "GrotesqueTrophy";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("GrotesqueTrophy");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
