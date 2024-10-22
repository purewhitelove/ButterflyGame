// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameActionManager.java

package com.megacrit.cardcrawl.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ShowMoveNameAction;
import com.megacrit.cardcrawl.actions.defect.TriggerEndOfTurnOrbsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.Careless;
import com.megacrit.cardcrawl.daily.mods.ControlledChaos;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.actions:
//            AbstractGameAction, IntentFlashAction

public class GameActionManager
{
    public static final class Phase extends Enum
    {

        public static Phase[] values()
        {
            return (Phase[])$VALUES.clone();
        }

        public static Phase valueOf(String name)
        {
            return (Phase)Enum.valueOf(com/megacrit/cardcrawl/actions/GameActionManager$Phase, name);
        }

        public static final Phase WAITING_ON_USER;
        public static final Phase EXECUTING_ACTIONS;
        private static final Phase $VALUES[];

        static 
        {
            WAITING_ON_USER = new Phase("WAITING_ON_USER", 0);
            EXECUTING_ACTIONS = new Phase("EXECUTING_ACTIONS", 1);
            $VALUES = (new Phase[] {
                WAITING_ON_USER, EXECUTING_ACTIONS
            });
        }

        private Phase(String s, int i)
        {
            super(s, i);
        }
    }


    public GameActionManager()
    {
        nextCombatActions = new ArrayList();
        actions = new ArrayList();
        preTurnActions = new ArrayList();
        cardQueue = new ArrayList();
        monsterQueue = new ArrayList();
        cardsPlayedThisTurn = new ArrayList();
        cardsPlayedThisCombat = new ArrayList();
        orbsChanneledThisCombat = new ArrayList();
        orbsChanneledThisTurn = new ArrayList();
        uniqueStancesThisCombat = new HashMap();
        mantraGained = 0;
        lastCard = null;
        phase = Phase.WAITING_ON_USER;
        hasControl = true;
        turnHasEnded = false;
        usingCard = false;
        monsterAttacksQueued = true;
    }

    public void addToNextCombat(AbstractGameAction action)
    {
        nextCombatActions.add(action);
    }

    public void useNextCombatActions()
    {
        AbstractGameAction a;
        for(Iterator iterator = nextCombatActions.iterator(); iterator.hasNext(); addToBottom(a))
            a = (AbstractGameAction)iterator.next();

        nextCombatActions.clear();
    }

    public void addToBottom(AbstractGameAction action)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            actions.add(action);
    }

    public void addCardQueueItem(CardQueueItem c, boolean inFrontOfQueue)
    {
        if(inFrontOfQueue)
        {
            if(!AbstractDungeon.actionManager.cardQueue.isEmpty())
                AbstractDungeon.actionManager.cardQueue.add(1, c);
            else
                AbstractDungeon.actionManager.cardQueue.add(c);
        } else
        {
            AbstractDungeon.actionManager.cardQueue.add(c);
        }
    }

    public void addCardQueueItem(CardQueueItem c)
    {
        addCardQueueItem(c, false);
    }

    public void removeFromQueue(AbstractCard c)
    {
        int index = -1;
        int i = 0;
        do
        {
            if(i >= cardQueue.size())
                break;
            if(((CardQueueItem)cardQueue.get(i)).card != null && ((CardQueueItem)cardQueue.get(i)).card.equals(c))
            {
                index = i;
                break;
            }
            i++;
        } while(true);
        if(index != -1)
            cardQueue.remove(index);
    }

    public void clearPostCombatActions()
    {
        Iterator i = actions.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameAction e = (AbstractGameAction)i.next();
            if(!(e instanceof HealAction) && !(e instanceof GainBlockAction) && !(e instanceof UseCardAction) && e.actionType != AbstractGameAction.ActionType.DAMAGE)
                i.remove();
        } while(true);
    }

    public void addToTop(AbstractGameAction action)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            actions.add(0, action);
    }

    public void addToTurnStart(AbstractGameAction action)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            preTurnActions.add(0, action);
    }

    public void update()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$actions$GameActionManager$Phase[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$actions$GameActionManager$Phase = new int[Phase.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$GameActionManager$Phase[Phase.WAITING_ON_USER.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$GameActionManager$Phase[Phase.EXECUTING_ACTIONS.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.actions.GameActionManager.Phase[phase.ordinal()])
        {
        case 1: // '\001'
            getNextAction();
            break;

        case 2: // '\002'
            if(currentAction != null && !currentAction.isDone)
            {
                currentAction.update();
                break;
            }
            previousAction = currentAction;
            currentAction = null;
            getNextAction();
            if(currentAction == null && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && !usingCard)
            {
                phase = Phase.WAITING_ON_USER;
                AbstractDungeon.player.hand.refreshHandLayout();
                hasControl = false;
            }
            usingCard = false;
            break;

        default:
            logger.info("This should never be called");
            break;
        }
    }

    public void endTurn()
    {
        AbstractDungeon.player.resetControllerValues();
        turnHasEnded = true;
        playerHpLastTurn = AbstractDungeon.player.currentHealth;
    }

    private void getNextAction()
    {
label0:
        {
label1:
            {
                AbstractCard c;
                boolean canPlayCard;
label2:
                {
                    Iterator i;
label3:
                    {
label4:
                        {
                            if(!actions.isEmpty())
                            {
                                currentAction = (AbstractGameAction)actions.remove(0);
                                phase = Phase.EXECUTING_ACTIONS;
                                hasControl = true;
                                break label0;
                            }
                            if(!preTurnActions.isEmpty())
                            {
                                currentAction = (AbstractGameAction)preTurnActions.remove(0);
                                phase = Phase.EXECUTING_ACTIONS;
                                hasControl = true;
                                break label0;
                            }
                            if(cardQueue.isEmpty())
                                break label1;
                            usingCard = true;
                            c = ((CardQueueItem)cardQueue.get(0)).card;
                            if(c == null)
                                callEndOfTurnActions();
                            else
                            if(c.equals(lastCard))
                            {
                                logger.info((new StringBuilder()).append("Last card! ").append(c.name).toString());
                                lastCard = null;
                            }
                            if(cardQueue.size() == 1 && ((CardQueueItem)cardQueue.get(0)).isEndTurnAutoPlay)
                            {
                                AbstractRelic top = AbstractDungeon.player.getRelic("Unceasing Top");
                                if(top != null)
                                    ((UnceasingTop)top).disableUntilTurnEnds();
                            }
                            canPlayCard = false;
                            if(c != null)
                                c.isInAutoplay = ((CardQueueItem)cardQueue.get(0)).autoplayCard;
                            if(c != null && ((CardQueueItem)cardQueue.get(0)).randomTarget)
                                ((CardQueueItem)cardQueue.get(0)).monster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                            if(((CardQueueItem)cardQueue.get(0)).card == null || !c.canUse(AbstractDungeon.player, ((CardQueueItem)cardQueue.get(0)).monster) && !((CardQueueItem)cardQueue.get(0)).card.dontTriggerOnUseCard)
                                break label3;
                            canPlayCard = true;
                            if(c.freeToPlay())
                                c.freeToPlayOnce = true;
                            ((CardQueueItem)cardQueue.get(0)).card.energyOnUse = ((CardQueueItem)cardQueue.get(0)).energyOnUse;
                            if(c.isInAutoplay)
                                ((CardQueueItem)cardQueue.get(0)).card.ignoreEnergyOnUse = true;
                            else
                                ((CardQueueItem)cardQueue.get(0)).card.ignoreEnergyOnUse = ((CardQueueItem)cardQueue.get(0)).ignoreEnergyTotal;
                            if(!((CardQueueItem)cardQueue.get(0)).card.dontTriggerOnUseCard)
                            {
                                AbstractPower p;
                                for(Iterator iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext(); p.onPlayCard(((CardQueueItem)cardQueue.get(0)).card, ((CardQueueItem)cardQueue.get(0)).monster))
                                    p = (AbstractPower)iterator.next();

                                for(Iterator iterator1 = AbstractDungeon.getMonsters().monsters.iterator(); iterator1.hasNext();)
                                {
                                    AbstractMonster m = (AbstractMonster)iterator1.next();
                                    Iterator iterator8 = m.powers.iterator();
                                    while(iterator8.hasNext()) 
                                    {
                                        AbstractPower p = (AbstractPower)iterator8.next();
                                        p.onPlayCard(((CardQueueItem)cardQueue.get(0)).card, ((CardQueueItem)cardQueue.get(0)).monster);
                                    }
                                }

                                AbstractRelic r;
                                for(Iterator iterator2 = AbstractDungeon.player.relics.iterator(); iterator2.hasNext(); r.onPlayCard(((CardQueueItem)cardQueue.get(0)).card, ((CardQueueItem)cardQueue.get(0)).monster))
                                    r = (AbstractRelic)iterator2.next();

                                AbstractDungeon.player.stance.onPlayCard(((CardQueueItem)cardQueue.get(0)).card);
                                AbstractBlight b;
                                for(Iterator iterator3 = AbstractDungeon.player.blights.iterator(); iterator3.hasNext(); b.onPlayCard(((CardQueueItem)cardQueue.get(0)).card, ((CardQueueItem)cardQueue.get(0)).monster))
                                    b = (AbstractBlight)iterator3.next();

                                AbstractCard card;
                                for(Iterator iterator4 = AbstractDungeon.player.hand.group.iterator(); iterator4.hasNext(); card.onPlayCard(((CardQueueItem)cardQueue.get(0)).card, ((CardQueueItem)cardQueue.get(0)).monster))
                                    card = (AbstractCard)iterator4.next();

                                AbstractCard card;
                                for(Iterator iterator5 = AbstractDungeon.player.discardPile.group.iterator(); iterator5.hasNext(); card.onPlayCard(((CardQueueItem)cardQueue.get(0)).card, ((CardQueueItem)cardQueue.get(0)).monster))
                                    card = (AbstractCard)iterator5.next();

                                AbstractCard card;
                                for(Iterator iterator6 = AbstractDungeon.player.drawPile.group.iterator(); iterator6.hasNext(); card.onPlayCard(((CardQueueItem)cardQueue.get(0)).card, ((CardQueueItem)cardQueue.get(0)).monster))
                                    card = (AbstractCard)iterator6.next();

                                AbstractDungeon.player.cardsPlayedThisTurn++;
                                cardsPlayedThisTurn.add(((CardQueueItem)cardQueue.get(0)).card);
                                cardsPlayedThisCombat.add(((CardQueueItem)cardQueue.get(0)).card);
                            }
                            if(cardsPlayedThisTurn.size() == 25)
                                UnlockTracker.unlockAchievement("INFINITY");
                            if(cardsPlayedThisTurn.size() >= 20 && !CardCrawlGame.combo)
                                CardCrawlGame.combo = true;
                            if(!(((CardQueueItem)cardQueue.get(0)).card instanceof Shiv))
                                break label4;
                            int shivCount = 0;
                            Iterator iterator7 = cardsPlayedThisTurn.iterator();
                            AbstractCard i;
                            do
                            {
                                if(!iterator7.hasNext())
                                    break label4;
                                i = (AbstractCard)iterator7.next();
                            } while(!(i instanceof Shiv) || ++shivCount != 10);
                            UnlockTracker.unlockAchievement("NINJA");
                        }
                        if(((CardQueueItem)cardQueue.get(0)).card != null)
                            if(((CardQueueItem)cardQueue.get(0)).card.target == com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY && (((CardQueueItem)cardQueue.get(0)).monster == null || ((CardQueueItem)cardQueue.get(0)).monster.isDeadOrEscaped()))
                            {
                                i = AbstractDungeon.player.limbo.group.iterator();
                                do
                                {
                                    if(!i.hasNext())
                                        break;
                                    AbstractCard e = (AbstractCard)i.next();
                                    if(e == ((CardQueueItem)cardQueue.get(0)).card)
                                    {
                                        ((CardQueueItem)cardQueue.get(0)).card.fadingOut = true;
                                        AbstractDungeon.effectList.add(new ExhaustCardEffect(((CardQueueItem)cardQueue.get(0)).card));
                                        i.remove();
                                    }
                                } while(true);
                                if(((CardQueueItem)cardQueue.get(0)).monster == null)
                                {
                                    ((CardQueueItem)cardQueue.get(0)).card.drawScale = ((CardQueueItem)cardQueue.get(0)).card.targetDrawScale;
                                    ((CardQueueItem)cardQueue.get(0)).card.angle = ((CardQueueItem)cardQueue.get(0)).card.targetAngle;
                                    ((CardQueueItem)cardQueue.get(0)).card.current_x = ((CardQueueItem)cardQueue.get(0)).card.target_x;
                                    ((CardQueueItem)cardQueue.get(0)).card.current_y = ((CardQueueItem)cardQueue.get(0)).card.target_y;
                                    AbstractDungeon.effectList.add(new ExhaustCardEffect(((CardQueueItem)cardQueue.get(0)).card));
                                }
                            } else
                            {
                                AbstractDungeon.player.useCard(((CardQueueItem)cardQueue.get(0)).card, ((CardQueueItem)cardQueue.get(0)).monster, ((CardQueueItem)cardQueue.get(0)).energyOnUse);
                            }
                        break label2;
                    }
                    i = AbstractDungeon.player.limbo.group.iterator();
                    do
                    {
                        if(!i.hasNext())
                            break;
                        AbstractCard e = (AbstractCard)i.next();
                        if(e == c)
                        {
                            c.fadingOut = true;
                            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                            i.remove();
                        }
                    } while(true);
                    if(c != null)
                        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3F, c.cantUseMessage, true));
                }
                cardQueue.remove(0);
                if(!canPlayCard && c != null && c.isInAutoplay)
                {
                    c.dontTriggerOnUseCard = true;
                    AbstractDungeon.actionManager.addToBottom(new UseCardAction(c));
                }
                break label0;
            }
            if(!monsterAttacksQueued)
            {
                monsterAttacksQueued = true;
                if(!AbstractDungeon.getCurrRoom().skipMonsterTurn)
                    AbstractDungeon.getCurrRoom().monsters.queueMonsters();
            } else
            if(!monsterQueue.isEmpty())
            {
                AbstractMonster m = ((MonsterQueueItem)monsterQueue.get(0)).monster;
                if(!m.isDeadOrEscaped() || m.halfDead)
                {
                    if(m.intent != com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.NONE)
                    {
                        addToBottom(new ShowMoveNameAction(m));
                        addToBottom(new IntentFlashAction(m));
                    }
                    if(!((Boolean)TipTracker.tips.get("INTENT_TIP")).booleanValue() && AbstractDungeon.player.currentBlock == 0 && (m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK || m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF || m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_BUFF || m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND))
                        if(AbstractDungeon.floorNum <= 5)
                            TipTracker.blockCounter++;
                        else
                            TipTracker.neverShowAgain("INTENT_TIP");
                    m.takeTurn();
                    m.applyTurnPowers();
                }
                monsterQueue.remove(0);
                if(monsterQueue.isEmpty())
                    addToBottom(new WaitAction(1.5F));
            } else
            if(turnHasEnded && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            {
                if(!AbstractDungeon.getCurrRoom().skipMonsterTurn)
                    AbstractDungeon.getCurrRoom().monsters.applyEndOfTurnPowers();
                AbstractDungeon.player.cardsPlayedThisTurn = 0;
                orbsChanneledThisTurn.clear();
                if(ModHelper.isModEnabled("Careless"))
                    Careless.modAction();
                if(ModHelper.isModEnabled("ControlledChaos"))
                {
                    ControlledChaos.modAction();
                    AbstractDungeon.player.hand.applyPowers();
                }
                AbstractDungeon.player.applyStartOfTurnRelics();
                AbstractDungeon.player.applyStartOfTurnPreDrawCards();
                AbstractDungeon.player.applyStartOfTurnCards();
                AbstractDungeon.player.applyStartOfTurnPowers();
                AbstractDungeon.player.applyStartOfTurnOrbs();
                turn++;
                AbstractDungeon.getCurrRoom().skipMonsterTurn = false;
                turnHasEnded = false;
                totalDiscardedThisTurn = 0;
                cardsPlayedThisTurn.clear();
                damageReceivedThisTurn = 0;
                if(!AbstractDungeon.player.hasPower("Barricade") && !AbstractDungeon.player.hasPower("Blur"))
                    if(!AbstractDungeon.player.hasRelic("Calipers"))
                        AbstractDungeon.player.loseBlock();
                    else
                        AbstractDungeon.player.loseBlock(15);
                if(!AbstractDungeon.getCurrRoom().isBattleOver)
                {
                    addToBottom(new DrawCardAction(null, AbstractDungeon.player.gameHandSize, true));
                    AbstractDungeon.player.applyStartOfTurnPostDrawRelics();
                    AbstractDungeon.player.applyStartOfTurnPostDrawPowers();
                    addToBottom(new EnableEndTurnButtonAction());
                }
            }
        }
    }

    private void callEndOfTurnActions()
    {
        AbstractDungeon.getCurrRoom().applyEndOfTurnRelics();
        AbstractDungeon.getCurrRoom().applyEndOfTurnPreCardPowers();
        addToBottom(new TriggerEndOfTurnOrbsAction());
        AbstractCard c;
        for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext(); c.triggerOnEndOfTurnForPlayingCard())
            c = (AbstractCard)iterator.next();

        AbstractDungeon.player.stance.onEndOfTurn();
    }

    public void callEndTurnEarlySequence()
    {
        Iterator iterator = AbstractDungeon.actionManager.cardQueue.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CardQueueItem i = (CardQueueItem)iterator.next();
            if(i.autoplayCard)
            {
                i.card.dontTriggerOnUseCard = true;
                AbstractDungeon.actionManager.addToBottom(new UseCardAction(i.card));
            }
        } while(true);
        AbstractDungeon.actionManager.cardQueue.clear();
        AbstractCard c;
        for(Iterator iterator1 = AbstractDungeon.player.limbo.group.iterator(); iterator1.hasNext(); AbstractDungeon.effectList.add(new ExhaustCardEffect(c)))
            c = (AbstractCard)iterator1.next();

        AbstractDungeon.player.limbo.group.clear();
        AbstractDungeon.player.releaseCard();
        AbstractDungeon.overlayMenu.endTurnButton.disable(true);
    }

    public void cleanCardQueue()
    {
        Iterator i = cardQueue.iterator();
        do
        {
            if(!i.hasNext())
                break;
            CardQueueItem e = (CardQueueItem)i.next();
            if(AbstractDungeon.player.hand.contains(e.card))
                i.remove();
        } while(true);
        for(i = AbstractDungeon.player.limbo.group.iterator(); i.hasNext();)
        {
            AbstractCard e = (AbstractCard)i.next();
            e.fadingOut = true;
        }

    }

    public boolean isEmpty()
    {
        return actions.isEmpty();
    }

    public void clearNextRoomCombatActions()
    {
        nextCombatActions.clear();
    }

    public void clear()
    {
        actions.clear();
        preTurnActions.clear();
        currentAction = null;
        previousAction = null;
        turnStartCurrentAction = null;
        cardsPlayedThisCombat.clear();
        cardsPlayedThisTurn.clear();
        orbsChanneledThisCombat.clear();
        orbsChanneledThisTurn.clear();
        uniqueStancesThisCombat.clear();
        cardQueue.clear();
        energyGainedThisCombat = 0;
        mantraGained = 0;
        damageReceivedThisCombat = 0;
        damageReceivedThisTurn = 0;
        hpLossThisCombat = 0;
        turnHasEnded = false;
        turn = 1;
        phase = Phase.WAITING_ON_USER;
        totalDiscardedThisTurn = 0;
    }

    public static void incrementDiscard(boolean endOfTurn)
    {
        totalDiscardedThisTurn++;
        if(!AbstractDungeon.actionManager.turnHasEnded && !endOfTurn)
        {
            AbstractDungeon.player.updateCardsOnDiscard();
            AbstractRelic r;
            for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onManualDiscard())
                r = (AbstractRelic)iterator.next();

        }
    }

    public void updateEnergyGain(int energyGain)
    {
        energyGainedThisCombat += energyGain;
    }

    public static void queueExtraCard(AbstractCard card, AbstractMonster m)
    {
        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        int extraCount = 0;
        Iterator iterator = AbstractDungeon.actionManager.cardQueue.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CardQueueItem c = (CardQueueItem)iterator.next();
            if(c.card.uuid.equals(card.uuid))
                extraCount++;
        } while(true);
        tmp.target_y = (float)Settings.HEIGHT / 2.0F;
        switch(extraCount)
        {
        case 0: // '\0'
            tmp.target_x = (float)Settings.WIDTH / 2.0F - 300F * Settings.xScale;
            break;

        case 1: // '\001'
            tmp.target_x = (float)Settings.WIDTH / 2.0F + 300F * Settings.xScale;
            break;

        case 2: // '\002'
            tmp.target_x = (float)Settings.WIDTH / 2.0F - 600F * Settings.xScale;
            break;

        case 3: // '\003'
            tmp.target_x = (float)Settings.WIDTH / 2.0F + 600F * Settings.xScale;
            break;

        default:
            tmp.target_x = MathUtils.random((float)Settings.WIDTH * 0.2F, (float)Settings.WIDTH * 0.8F);
            tmp.target_y = MathUtils.random((float)Settings.HEIGHT * 0.3F, (float)Settings.HEIGHT * 0.7F);
            break;
        }
        if(m != null)
            tmp.calculateCardDamage(m);
        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/actions/GameActionManager.getName());
    private ArrayList nextCombatActions;
    public ArrayList actions;
    public ArrayList preTurnActions;
    public ArrayList cardQueue;
    public ArrayList monsterQueue;
    public ArrayList cardsPlayedThisTurn;
    public ArrayList cardsPlayedThisCombat;
    public ArrayList orbsChanneledThisCombat;
    public ArrayList orbsChanneledThisTurn;
    public HashMap uniqueStancesThisCombat;
    public int mantraGained;
    public AbstractGameAction currentAction;
    public AbstractGameAction previousAction;
    public AbstractGameAction turnStartCurrentAction;
    public AbstractCard lastCard;
    public Phase phase;
    public boolean hasControl;
    public boolean turnHasEnded;
    public boolean usingCard;
    public boolean monsterAttacksQueued;
    public static int totalDiscardedThisTurn = 0;
    public static int damageReceivedThisTurn = 0;
    public static int damageReceivedThisCombat = 0;
    public static int hpLossThisCombat = 0;
    public static int playerHpLastTurn;
    public static int energyGainedThisCombat;
    public static int turn = 0;

}
