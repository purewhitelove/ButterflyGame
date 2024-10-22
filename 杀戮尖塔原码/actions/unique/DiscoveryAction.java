// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscoveryAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class DiscoveryAction extends AbstractGameAction
{

    public DiscoveryAction()
    {
        retrieveCard = false;
        returnColorless = false;
        cardType = null;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        amount = 1;
    }

    public DiscoveryAction(com.megacrit.cardcrawl.cards.AbstractCard.CardType type, int amount)
    {
        retrieveCard = false;
        returnColorless = false;
        cardType = null;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        cardType = type;
    }

    public DiscoveryAction(boolean colorless, int amount)
    {
        retrieveCard = false;
        returnColorless = false;
        cardType = null;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        returnColorless = colorless;
    }

    public void update()
    {
        ArrayList generatedCards;
        if(returnColorless)
            generatedCards = generateColorlessCardChoices();
        else
            generatedCards = generateCardChoices(cardType);
        if(duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, CardRewardScreen.TEXT[1], cardType != null);
            tickDuration();
            return;
        }
        if(!retrieveCard)
        {
            if(AbstractDungeon.cardRewardScreen.discoveryCard != null)
            {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                AbstractCard disCard2 = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                if(AbstractDungeon.player.hasPower("MasterRealityPower"))
                {
                    disCard.upgrade();
                    disCard2.upgrade();
                }
                disCard.setCostForTurn(0);
                disCard2.setCostForTurn(0);
                disCard.current_x = -1000F * Settings.xScale;
                disCard2.current_x = -1000F * Settings.xScale + AbstractCard.IMG_HEIGHT_S;
                if(amount == 1)
                {
                    if(AbstractDungeon.player.hand.size() < 10)
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    else
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    disCard2 = null;
                } else
                if(AbstractDungeon.player.hand.size() + amount <= 10)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                } else
                if(AbstractDungeon.player.hand.size() == 9)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                } else
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            retrieveCard = true;
        }
        tickDuration();
    }

    private ArrayList generateColorlessCardChoices()
    {
        ArrayList derp = new ArrayList();
        do
        {
            if(derp.size() == 3)
                break;
            boolean dupe = false;
            AbstractCard tmp = AbstractDungeon.returnTrulyRandomColorlessCardInCombat();
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

    private ArrayList generateCardChoices(com.megacrit.cardcrawl.cards.AbstractCard.CardType type)
    {
        ArrayList derp = new ArrayList();
        do
        {
            if(derp.size() == 3)
                break;
            boolean dupe = false;
            AbstractCard tmp = null;
            if(type == null)
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat();
            else
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat(type);
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
    private boolean returnColorless;
    private com.megacrit.cardcrawl.cards.AbstractCard.CardType cardType;
}
