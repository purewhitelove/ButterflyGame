// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DuVuDoll.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.StrengthPower;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class DuVuDoll extends AbstractRelic
{

    public DuVuDoll()
    {
        super("Du-Vu Doll", "duvuDoll.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public void setCounter(int c)
    {
        counter = c;
        if(counter == 0)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).append(DESCRIPTIONS[2]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).append(DESCRIPTIONS[3]).append(counter).append(DESCRIPTIONS[4]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void onMasterDeckChange()
    {
        counter = 0;
        Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE)
                counter++;
        } while(true);
        if(counter == 0)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).append(DESCRIPTIONS[2]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).append(DESCRIPTIONS[3]).append(counter).append(DESCRIPTIONS[4]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void onEquip()
    {
        counter = 0;
        Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE)
                counter++;
        } while(true);
        if(counter == 0)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).append(DESCRIPTIONS[2]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).append(DESCRIPTIONS[3]).append(counter).append(DESCRIPTIONS[4]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void atBattleStart()
    {
        if(counter > 0)
        {
            flash();
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, counter), counter));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new DuVuDoll();
    }

    public static final String ID = "Du-Vu Doll";
    private static final int AMT = 1;
}
