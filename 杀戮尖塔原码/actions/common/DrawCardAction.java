// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DrawCardAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.actions.common:
//            EmptyDeckShuffleAction

public class DrawCardAction extends AbstractGameAction
{

    public DrawCardAction(AbstractCreature source, int amount, boolean endTurnDraw)
    {
        shuffleCheck = false;
        clearDrawHistory = true;
        followUpAction = null;
        if(endTurnDraw)
            AbstractDungeon.topLevelEffects.add(new PlayerTurnEffect());
        setValues(AbstractDungeon.player, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DRAW;
        if(Settings.FAST_MODE)
            duration = Settings.ACTION_DUR_XFAST;
        else
            duration = Settings.ACTION_DUR_FASTER;
    }

    public DrawCardAction(AbstractCreature source, int amount)
    {
        this(source, amount, false);
    }

    public DrawCardAction(int amount, boolean clearDrawHistory)
    {
        this(amount);
        this.clearDrawHistory = clearDrawHistory;
    }

    public DrawCardAction(int amount)
    {
        this(((AbstractCreature) (null)), amount, false);
    }

    public DrawCardAction(int amount, AbstractGameAction action)
    {
        this(amount, action, true);
    }

    public DrawCardAction(int amount, AbstractGameAction action, boolean clearDrawHistory)
    {
        this(amount, clearDrawHistory);
        followUpAction = action;
    }

    public void update()
    {
        if(clearDrawHistory)
        {
            clearDrawHistory = false;
            drawnCards.clear();
        }
        if(AbstractDungeon.player.hasPower("No Draw"))
        {
            AbstractDungeon.player.getPower("No Draw").flash();
            endActionWithFollowUp();
            return;
        }
        if(amount <= 0)
        {
            endActionWithFollowUp();
            return;
        }
        int deckSize = AbstractDungeon.player.drawPile.size();
        int discardSize = AbstractDungeon.player.discardPile.size();
        if(SoulGroup.isActive())
            return;
        if(deckSize + discardSize == 0)
        {
            endActionWithFollowUp();
            return;
        }
        if(AbstractDungeon.player.hand.size() == 10)
        {
            AbstractDungeon.player.createHandIsFullDialog();
            endActionWithFollowUp();
            return;
        }
        if(!shuffleCheck)
        {
            if(amount + AbstractDungeon.player.hand.size() > 10)
            {
                int handSizeAndDraw = 10 - (amount + AbstractDungeon.player.hand.size());
                amount += handSizeAndDraw;
                AbstractDungeon.player.createHandIsFullDialog();
            }
            if(amount > deckSize)
            {
                int tmp = amount - deckSize;
                addToTop(new DrawCardAction(tmp, followUpAction, false));
                addToTop(new EmptyDeckShuffleAction());
                if(deckSize != 0)
                    addToTop(new DrawCardAction(deckSize, false));
                amount = 0;
                isDone = true;
                return;
            }
            shuffleCheck = true;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(amount != 0 && duration < 0.0F)
        {
            if(Settings.FAST_MODE)
                duration = Settings.ACTION_DUR_XFAST;
            else
                duration = Settings.ACTION_DUR_FASTER;
            amount--;
            if(!AbstractDungeon.player.drawPile.isEmpty())
            {
                drawnCards.add(AbstractDungeon.player.drawPile.getTopCard());
                AbstractDungeon.player.draw();
                AbstractDungeon.player.hand.refreshHandLayout();
            } else
            {
                logger.warn((new StringBuilder()).append("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: ").append(AbstractDungeon.player.masterDeck.getCardNames()).toString());
                endActionWithFollowUp();
            }
            if(amount == 0)
                endActionWithFollowUp();
        }
    }

    private void endActionWithFollowUp()
    {
        isDone = true;
        if(followUpAction != null)
            addToTop(followUpAction);
    }

    private boolean shuffleCheck;
    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/actions/common/DrawCardAction.getName());
    public static ArrayList drawnCards = new ArrayList();
    private boolean clearDrawHistory;
    private AbstractGameAction followUpAction;

}
