// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApplyStasisAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StasisPower;

public class ApplyStasisAction extends AbstractGameAction
{

    public ApplyStasisAction(AbstractCreature owner)
    {
        card = null;
        this.owner = owner;
        duration = Settings.ACTION_DUR_LONG;
        startingDuration = Settings.ACTION_DUR_LONG;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
    }

    public void update()
    {
        if(AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.discardPile.isEmpty())
        {
            isDone = true;
            return;
        }
        if(duration == startingDuration)
        {
            if(AbstractDungeon.player.drawPile.isEmpty())
            {
                card = AbstractDungeon.player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE);
                if(card == null)
                {
                    card = AbstractDungeon.player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON);
                    if(card == null)
                    {
                        card = AbstractDungeon.player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON);
                        if(card == null)
                            card = AbstractDungeon.player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng);
                    }
                }
                AbstractDungeon.player.discardPile.removeCard(card);
            } else
            {
                card = AbstractDungeon.player.drawPile.getRandomCard(AbstractDungeon.cardRandomRng, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE);
                if(card == null)
                {
                    card = AbstractDungeon.player.drawPile.getRandomCard(AbstractDungeon.cardRandomRng, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON);
                    if(card == null)
                    {
                        card = AbstractDungeon.player.drawPile.getRandomCard(AbstractDungeon.cardRandomRng, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON);
                        if(card == null)
                            card = AbstractDungeon.player.drawPile.getRandomCard(AbstractDungeon.cardRandomRng);
                    }
                }
                AbstractDungeon.player.drawPile.removeCard(card);
            }
            AbstractDungeon.player.limbo.addToBottom(card);
            card.setAngle(0.0F);
            card.targetDrawScale = 0.75F;
            card.target_x = (float)Settings.WIDTH / 2.0F;
            card.target_y = (float)Settings.HEIGHT / 2.0F;
            card.lighten(false);
            card.unfadeOut();
            card.unhover();
            card.untip();
            card.stopGlowing();
        }
        tickDuration();
        if(isDone && card != null)
        {
            addToTop(new ApplyPowerAction(owner, owner, new StasisPower(owner, card)));
            addToTop(new ShowCardAction(card));
        }
    }

    private AbstractCreature owner;
    private float startingDuration;
    private AbstractCard card;
}
