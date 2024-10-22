// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UseCardAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.StrangeSpoon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.actions.utility:
//            ShowCardAndPoofAction, ShowCardAction, WaitAction, HandCheckAction

public class UseCardAction extends AbstractGameAction
{

    public UseCardAction(AbstractCard card, AbstractCreature target)
    {
        this.target = null;
        reboundCard = false;
        targetCard = card;
        this.target = target;
        if(card.exhaustOnUseOnce || card.exhaust)
            exhaustCard = true;
        setValues(AbstractDungeon.player, null, 1);
        duration = 0.15F;
        Iterator iterator = AbstractDungeon.player.powers.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractPower p = (AbstractPower)iterator.next();
            if(!card.dontTriggerOnUseCard)
                p.onUseCard(card, this);
        } while(true);
        iterator = AbstractDungeon.player.relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(!card.dontTriggerOnUseCard)
                r.onUseCard(card, this);
        } while(true);
        iterator = AbstractDungeon.player.hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!card.dontTriggerOnUseCard)
                c.triggerOnCardPlayed(card);
        } while(true);
        iterator = AbstractDungeon.player.discardPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!card.dontTriggerOnUseCard)
                c.triggerOnCardPlayed(card);
        } while(true);
        iterator = AbstractDungeon.player.drawPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!card.dontTriggerOnUseCard)
                c.triggerOnCardPlayed(card);
        } while(true);
        for(Iterator iterator1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); iterator1.hasNext();)
        {
            AbstractMonster m = (AbstractMonster)iterator1.next();
            Iterator iterator2 = m.powers.iterator();
            while(iterator2.hasNext()) 
            {
                AbstractPower p = (AbstractPower)iterator2.next();
                if(!card.dontTriggerOnUseCard)
                    p.onUseCard(card, this);
            }
        }

        if(exhaustCard)
            actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.EXHAUST;
        else
            actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.USE;
    }

    public UseCardAction(AbstractCard targetCard)
    {
        this(targetCard, null);
    }

    public void update()
    {
        if(duration == 0.15F)
        {
            Iterator iterator = AbstractDungeon.player.powers.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractPower p = (AbstractPower)iterator.next();
                if(!targetCard.dontTriggerOnUseCard)
                    p.onAfterUseCard(targetCard, this);
            } while(true);
            for(Iterator iterator1 = AbstractDungeon.getMonsters().monsters.iterator(); iterator1.hasNext();)
            {
                AbstractMonster m = (AbstractMonster)iterator1.next();
                Iterator iterator2 = m.powers.iterator();
                while(iterator2.hasNext()) 
                {
                    AbstractPower p = (AbstractPower)iterator2.next();
                    if(!targetCard.dontTriggerOnUseCard)
                        p.onAfterUseCard(targetCard, this);
                }
            }

            targetCard.freeToPlayOnce = false;
            targetCard.isInAutoplay = false;
            if(targetCard.purgeOnUse)
            {
                addToTop(new ShowCardAndPoofAction(targetCard));
                isDone = true;
                AbstractDungeon.player.cardInUse = null;
                return;
            }
            if(targetCard.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER)
            {
                addToTop(new ShowCardAction(targetCard));
                if(Settings.FAST_MODE)
                    addToTop(new WaitAction(0.1F));
                else
                    addToTop(new WaitAction(0.7F));
                AbstractDungeon.player.hand.empower(targetCard);
                isDone = true;
                AbstractDungeon.player.hand.applyPowers();
                AbstractDungeon.player.hand.glowCheck();
                AbstractDungeon.player.cardInUse = null;
                return;
            }
            AbstractDungeon.player.cardInUse = null;
            boolean spoonProc = false;
            if(exhaustCard && AbstractDungeon.player.hasRelic("Strange Spoon") && targetCard.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER)
                spoonProc = AbstractDungeon.cardRandomRng.randomBoolean();
            if(!exhaustCard || spoonProc)
            {
                if(spoonProc)
                    AbstractDungeon.player.getRelic("Strange Spoon").flash();
                if(reboundCard)
                    AbstractDungeon.player.hand.moveToDeck(targetCard, false);
                else
                if(targetCard.shuffleBackIntoDrawPile)
                    AbstractDungeon.player.hand.moveToDeck(targetCard, true);
                else
                if(targetCard.returnToHand)
                {
                    AbstractDungeon.player.hand.moveToHand(targetCard);
                    AbstractDungeon.player.onCardDrawOrDiscard();
                } else
                {
                    AbstractDungeon.player.hand.moveToDiscardPile(targetCard);
                }
            } else
            {
                AbstractDungeon.player.hand.moveToExhaustPile(targetCard);
                CardCrawlGame.dungeon.checkForPactAchievement();
            }
            targetCard.exhaustOnUseOnce = false;
            targetCard.dontTriggerOnUseCard = false;
            addToBot(new HandCheckAction());
        }
        tickDuration();
    }

    private AbstractCard targetCard;
    public AbstractCreature target;
    public boolean exhaustCard;
    public boolean returnToHand;
    public boolean reboundCard;
    private static final float DUR = 0.15F;
}
