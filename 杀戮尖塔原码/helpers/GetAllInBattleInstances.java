// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetAllInBattleInstances.java

package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.*;

public class GetAllInBattleInstances
{

    public GetAllInBattleInstances()
    {
    }

    public static HashSet get(UUID uuid)
    {
        HashSet cards = new HashSet();
        if(AbstractDungeon.player.cardInUse.uuid.equals(uuid))
            cards.add(AbstractDungeon.player.cardInUse);
        Iterator iterator = AbstractDungeon.player.drawPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.uuid.equals(uuid))
                cards.add(c);
        } while(true);
        iterator = AbstractDungeon.player.discardPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.uuid.equals(uuid))
                cards.add(c);
        } while(true);
        iterator = AbstractDungeon.player.exhaustPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.uuid.equals(uuid))
                cards.add(c);
        } while(true);
        iterator = AbstractDungeon.player.limbo.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.uuid.equals(uuid))
                cards.add(c);
        } while(true);
        iterator = AbstractDungeon.player.hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.uuid.equals(uuid))
                cards.add(c);
        } while(true);
        return cards;
    }
}
