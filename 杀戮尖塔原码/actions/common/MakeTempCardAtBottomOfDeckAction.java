// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MakeTempCardAtBottomOfDeckAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class MakeTempCardAtBottomOfDeckAction extends AbstractGameAction
{

    public MakeTempCardAtBottomOfDeckAction(int amount)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        duration = startDuration;
    }

    public void update()
    {
        if(duration == startDuration)
        {
            for(int i = 0; i < amount; i++)
            {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat().makeStatEquivalentCopy();
                UnlockTracker.markCardAsSeen(c.cardID);
                if(c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower"))
                    c.upgrade();
                AbstractDungeon.player.drawPile.addToBottom(c);
            }

            duration -= Gdx.graphics.getDeltaTime();
        }
        tickDuration();
    }
}
