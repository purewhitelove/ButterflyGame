// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrikeUpPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class StrikeUpPower extends AbstractPower
{

    public StrikeUpPower(AbstractCreature owner, int amt)
    {
        name = powerStrings.NAME;
        ID = "StrikeUp";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("accuracy");
        updateExistingStrikes();
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        amount += stackAmount;
        updateExistingStrikes();
    }

    private void updateExistingStrikes()
    {
        Iterator iterator = AbstractDungeon.player.hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STRIKE))
                c.baseDamage = CardLibrary.getCard(c.cardID).baseDamage + amount;
        } while(true);
        iterator = AbstractDungeon.player.drawPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STRIKE))
                c.baseDamage = CardLibrary.getCard(c.cardID).baseDamage + amount;
        } while(true);
        iterator = AbstractDungeon.player.discardPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STRIKE))
                c.baseDamage = CardLibrary.getCard(c.cardID).baseDamage + amount;
        } while(true);
        iterator = AbstractDungeon.player.exhaustPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STRIKE))
                c.baseDamage = CardLibrary.getCard(c.cardID).baseDamage + amount;
        } while(true);
    }

    public void onDrawOrDiscard()
    {
        Iterator iterator = AbstractDungeon.player.hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STRIKE))
                c.baseDamage = CardLibrary.getCard(c.cardID).baseDamage + amount;
        } while(true);
    }

    public static final String POWER_ID = "StrikeUp";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("StrikeUp");
    }
}
