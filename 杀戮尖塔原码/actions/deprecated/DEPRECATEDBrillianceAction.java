// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDBrillianceAction.java

package com.megacrit.cardcrawl.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class DEPRECATEDBrillianceAction extends AbstractGameAction
{

    public DEPRECATEDBrillianceAction()
    {
    }

    public void update()
    {
        ArrayList g = AbstractDungeon.player.hand.group;
        for(int i = 0; i < g.size(); i++)
            if(!((AbstractCard)g.get(i)).cardID.equals("Miracle"))
                addToBot(new TransformCardInHandAction(i, new Miracle()));

        isDone = true;
    }
}
