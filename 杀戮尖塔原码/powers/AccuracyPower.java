// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccuracyPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class AccuracyPower extends AbstractPower
{

    public AccuracyPower(AbstractCreature owner, int amt)
    {
        name = powerStrings.NAME;
        ID = "Accuracy";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("accuracy");
        updateExistingShivs();
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        amount += stackAmount;
        updateExistingShivs();
    }

    private void updateExistingShivs()
    {
        Iterator iterator = AbstractDungeon.player.hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c instanceof Shiv)
                if(!c.upgraded)
                    c.baseDamage = 4 + amount;
                else
                    c.baseDamage = 6 + amount;
        } while(true);
        iterator = AbstractDungeon.player.drawPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c instanceof Shiv)
                if(!c.upgraded)
                    c.baseDamage = 4 + amount;
                else
                    c.baseDamage = 6 + amount;
        } while(true);
        iterator = AbstractDungeon.player.discardPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c instanceof Shiv)
                if(!c.upgraded)
                    c.baseDamage = 4 + amount;
                else
                    c.baseDamage = 6 + amount;
        } while(true);
        iterator = AbstractDungeon.player.exhaustPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c instanceof Shiv)
                if(!c.upgraded)
                    c.baseDamage = 4 + amount;
                else
                    c.baseDamage = 6 + amount;
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
            if(c instanceof Shiv)
                if(!c.upgraded)
                    c.baseDamage = 4 + amount;
                else
                    c.baseDamage = 6 + amount;
        } while(true);
    }

    public static final String POWER_ID = "Accuracy";
    private static final PowerStrings powerStrings;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Accuracy");
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
