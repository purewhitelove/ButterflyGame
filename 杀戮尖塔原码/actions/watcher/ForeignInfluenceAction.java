// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ForeignInfluenceAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class ForeignInfluenceAction extends AbstractGameAction
{

    public ForeignInfluenceAction(boolean upgraded)
    {
        retrieveCard = false;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);
            tickDuration();
            return;
        }
        if(!retrieveCard)
        {
            if(AbstractDungeon.cardRewardScreen.discoveryCard != null)
            {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                if(upgraded)
                    disCard.setCostForTurn(0);
                disCard.current_x = -1000F * Settings.xScale;
                if(AbstractDungeon.player.hand.size() < 10)
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                else
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            retrieveCard = true;
        }
        tickDuration();
    }

    private ArrayList generateCardChoices()
    {
        ArrayList derp = new ArrayList();
        do
        {
            if(derp.size() == 3)
                break;
            boolean dupe = false;
            int roll = AbstractDungeon.cardRandomRng.random(99);
            com.megacrit.cardcrawl.cards.AbstractCard.CardRarity cardRarity;
            if(roll < 55)
                cardRarity = com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON;
            else
            if(roll < 85)
                cardRarity = com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON;
            else
                cardRarity = com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE;
            AbstractCard tmp = CardLibrary.getAnyColorCard(com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, cardRarity);
            Iterator iterator = derp.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(!c.cardID.equals(tmp.cardID))
                    continue;
                dupe = true;
                break;
            } while(true);
            if(!dupe)
                derp.add(tmp.makeCopy());
        } while(true);
        return derp;
    }

    private boolean retrieveCard;
    private boolean upgraded;
}
