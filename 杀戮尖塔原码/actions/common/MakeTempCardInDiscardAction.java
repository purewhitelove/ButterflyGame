// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MakeTempCardInDiscardAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import java.util.ArrayList;

public class MakeTempCardInDiscardAction extends AbstractGameAction
{

    public MakeTempCardInDiscardAction(AbstractCard card, int amount)
    {
        UnlockTracker.markCardAsSeen(card.cardID);
        numCards = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        duration = startDuration;
        c = card;
        sameUUID = false;
    }

    public MakeTempCardInDiscardAction(AbstractCard card, boolean sameUUID)
    {
        this(card, 1);
        this.sameUUID = sameUUID;
        if(!sameUUID && c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower"))
            c.upgrade();
    }

    public void update()
    {
        if(duration == startDuration)
        {
            if(numCards < 6)
            {
                for(int i = 0; i < numCards; i++)
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard()));

            }
            duration -= Gdx.graphics.getDeltaTime();
        }
        tickDuration();
    }

    private AbstractCard makeNewCard()
    {
        if(sameUUID)
            return c.makeSameInstanceOf();
        else
            return c.makeStatEquivalentCopy();
    }

    private AbstractCard c;
    private int numCards;
    private boolean sameUUID;
}
