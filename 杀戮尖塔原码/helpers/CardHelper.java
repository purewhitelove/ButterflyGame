// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardHelper.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CardHelper
{
    public static class CardInfo
    {

        String id;
        String name;
        com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity;
        com.megacrit.cardcrawl.cards.AbstractCard.CardColor color;

        public CardInfo(String id, String name, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity, com.megacrit.cardcrawl.cards.AbstractCard.CardColor color)
        {
            this.id = id;
            this.name = name;
            this.rarity = rarity;
            this.color = color;
        }
    }


    public CardHelper()
    {
    }

    public static void obtain(String key, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity, com.megacrit.cardcrawl.cards.AbstractCard.CardColor color)
    {
        if(rarity == com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL || rarity == com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC || rarity == com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE)
        {
            logger.info((new StringBuilder()).append("No need to track rarity type: ").append(rarity.name()).toString());
            return;
        }
        if(obtainedCards.containsKey(key))
        {
            int tmp = ((Integer)obtainedCards.get(key)).intValue() + 1;
            obtainedCards.put(key, Integer.valueOf(tmp));
            logger.info((new StringBuilder()).append("Obtained ").append(key).append(" (").append(rarity.name()).append("). You have ").append(tmp).append(" now").toString());
        } else
        {
            obtainedCards.put(key, Integer.valueOf(1));
            logger.info((new StringBuilder()).append("Obtained ").append(key).append(" (").append(rarity.name()).append("). Creating new map entry.").toString());
        }
        UnlockTracker.markCardAsSeen(key);
    }

    public static void clear()
    {
        logger.info("Clearing CardHelper (obtained cards)");
        removedCards.clear();
        obtainedCards.clear();
    }

    public static Color getColor(int r, int g, int b)
    {
        return new Color((float)r / 255F, (float)g / 255F, (float)b / 255F, 1.0F);
    }

    public static Color getColor(float r, float g, float b)
    {
        return new Color(r / 255F, g / 255F, b / 255F, 1.0F);
    }

    public static boolean hasCardWithXDamage(int damage)
    {
        for(Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK && c.baseDamage >= 10)
            {
                logger.info((new StringBuilder()).append(c.name).append(" is 10 Attack!").toString());
                return true;
            }
        }

        return false;
    }

    public static boolean hasCardWithID(String targetID)
    {
        for(Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.cardID.equals(targetID))
                return true;
        }

        return false;
    }

    public static boolean hasCardType(com.megacrit.cardcrawl.cards.AbstractCard.CardType hasType)
    {
        for(Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == hasType)
                return true;
        }

        return false;
    }

    public static boolean hasCardWithType(com.megacrit.cardcrawl.cards.AbstractCard.CardType type)
    {
        for(Iterator iterator = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == type)
                return true;
        }

        return false;
    }

    public static AbstractCard returnCardOfType(com.megacrit.cardcrawl.cards.AbstractCard.CardType type, Random rng)
    {
        ArrayList cards = new ArrayList();
        Iterator iterator = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == type)
                cards.add(c);
        } while(true);
        return (AbstractCard)cards.remove(rng.random(cards.size() - 1));
    }

    public static boolean hasUpgradedCard()
    {
        for(Iterator iterator = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.upgraded)
                return true;
        }

        return false;
    }

    public static AbstractCard returnUpgradedCard(Random rng)
    {
        ArrayList cards = new ArrayList();
        Iterator iterator = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.upgraded)
                cards.add(c);
        } while(true);
        return (AbstractCard)cards.remove(rng.random(cards.size() - 1));
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/CardHelper.getName());
    public static final int COMMON_CARD_LIMIT = 3;
    public static final int UNCOMMON_CARD_LIMIT = 2;
    public static HashMap obtainedCards = new HashMap();
    public static ArrayList removedCards = new ArrayList();

}
