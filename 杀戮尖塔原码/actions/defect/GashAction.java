// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GashAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class GashAction extends AbstractGameAction
{

    public GashAction(AbstractCard card, int amount)
    {
        this.card = card;
        this.amount = amount;
    }

    public void update()
    {
        card.baseDamage += amount;
        card.applyPowers();
        Iterator iterator = AbstractDungeon.player.discardPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c instanceof Claw)
            {
                c.baseDamage += amount;
                c.applyPowers();
            }
        } while(true);
        iterator = AbstractDungeon.player.drawPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c instanceof Claw)
            {
                c.baseDamage += amount;
                c.applyPowers();
            }
        } while(true);
        iterator = AbstractDungeon.player.hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c instanceof Claw)
            {
                c.baseDamage += amount;
                c.applyPowers();
            }
        } while(true);
        isDone = true;
    }

    private AbstractCard card;
}
