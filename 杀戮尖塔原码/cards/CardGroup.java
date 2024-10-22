// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardGroup.java

package com.megacrit.cardcrawl.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.BottledTornado;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.cards:
//            AbstractCard, CardSave, CardQueueItem, SoulGroup

public class CardGroup
{
    private class CardCostComparator
        implements Comparator
    {

        public int compare(AbstractCard c1, AbstractCard c2)
        {
            return c1.cost - c2.cost;
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((AbstractCard)obj, (AbstractCard)obj1);
        }

        final CardGroup this$0;

        private CardCostComparator()
        {
            this$0 = CardGroup.this;
            super();
        }

    }

    private class CardNameComparator
        implements Comparator
    {

        public int compare(AbstractCard c1, AbstractCard c2)
        {
            return c1.name.compareTo(c2.name);
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((AbstractCard)obj, (AbstractCard)obj1);
        }

        final CardGroup this$0;

        private CardNameComparator()
        {
            this$0 = CardGroup.this;
            super();
        }

    }

    private class CardLockednessComparator
        implements Comparator
    {

        public int compare(AbstractCard c1, AbstractCard c2)
        {
            int c1Rank = 0;
            if(UnlockTracker.isCardLocked(c1.cardID))
                c1Rank = 2;
            else
            if(!UnlockTracker.isCardSeen(c1.cardID))
                c1Rank = 1;
            int c2Rank = 0;
            if(UnlockTracker.isCardLocked(c2.cardID))
                c2Rank = 2;
            else
            if(!UnlockTracker.isCardSeen(c2.cardID))
                c2Rank = 1;
            return c1Rank - c2Rank;
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((AbstractCard)obj, (AbstractCard)obj1);
        }

        final CardGroup this$0;

        private CardLockednessComparator()
        {
            this$0 = CardGroup.this;
            super();
        }

    }

    private class CardTypeComparator
        implements Comparator
    {

        public int compare(AbstractCard c1, AbstractCard c2)
        {
            return c1.type.compareTo(c2.type);
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((AbstractCard)obj, (AbstractCard)obj1);
        }

        final CardGroup this$0;

        private CardTypeComparator()
        {
            this$0 = CardGroup.this;
            super();
        }

    }

    private class StatusCardsLastComparator
        implements Comparator
    {

        public int compare(AbstractCard c1, AbstractCard c2)
        {
            if(c1.type == AbstractCard.CardType.STATUS && c2.type != AbstractCard.CardType.STATUS)
                return 1;
            return c1.type == AbstractCard.CardType.STATUS || c2.type != AbstractCard.CardType.STATUS ? 0 : -1;
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((AbstractCard)obj, (AbstractCard)obj1);
        }

        final CardGroup this$0;

        private StatusCardsLastComparator()
        {
            this$0 = CardGroup.this;
            super();
        }

    }

    private class CardRarityComparator
        implements Comparator
    {

        public int compare(AbstractCard c1, AbstractCard c2)
        {
            return c1.rarity.compareTo(c2.rarity);
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((AbstractCard)obj, (AbstractCard)obj1);
        }

        final CardGroup this$0;

        private CardRarityComparator()
        {
            this$0 = CardGroup.this;
            super();
        }

    }

    public static final class CardGroupType extends Enum
    {

        public static CardGroupType[] values()
        {
            return (CardGroupType[])$VALUES.clone();
        }

        public static CardGroupType valueOf(String name)
        {
            return (CardGroupType)Enum.valueOf(com/megacrit/cardcrawl/cards/CardGroup$CardGroupType, name);
        }

        public static final CardGroupType DRAW_PILE;
        public static final CardGroupType MASTER_DECK;
        public static final CardGroupType HAND;
        public static final CardGroupType DISCARD_PILE;
        public static final CardGroupType EXHAUST_PILE;
        public static final CardGroupType CARD_POOL;
        public static final CardGroupType UNSPECIFIED;
        private static final CardGroupType $VALUES[];

        static 
        {
            DRAW_PILE = new CardGroupType("DRAW_PILE", 0);
            MASTER_DECK = new CardGroupType("MASTER_DECK", 1);
            HAND = new CardGroupType("HAND", 2);
            DISCARD_PILE = new CardGroupType("DISCARD_PILE", 3);
            EXHAUST_PILE = new CardGroupType("EXHAUST_PILE", 4);
            CARD_POOL = new CardGroupType("CARD_POOL", 5);
            UNSPECIFIED = new CardGroupType("UNSPECIFIED", 6);
            $VALUES = (new CardGroupType[] {
                DRAW_PILE, MASTER_DECK, HAND, DISCARD_PILE, EXHAUST_PILE, CARD_POOL, UNSPECIFIED
            });
        }

        private CardGroupType(String s, int i)
        {
            super(s, i);
        }
    }


    public CardGroup(CardGroupType type)
    {
        group = new ArrayList();
        HAND_START_X = (float)Settings.WIDTH * 0.36F;
        HAND_OFFSET_X = AbstractCard.IMG_WIDTH * 0.35F;
        handPositioningMap = new HashMap();
        queued = new ArrayList();
        inHand = new ArrayList();
        this.type = type;
    }

    public CardGroup(CardGroup g, CardGroupType type)
    {
        group = new ArrayList();
        HAND_START_X = (float)Settings.WIDTH * 0.36F;
        HAND_OFFSET_X = AbstractCard.IMG_WIDTH * 0.35F;
        handPositioningMap = new HashMap();
        queued = new ArrayList();
        inHand = new ArrayList();
        this.type = type;
        AbstractCard c;
        for(Iterator iterator = g.group.iterator(); iterator.hasNext(); group.add(c.makeSameInstanceOf()))
            c = (AbstractCard)iterator.next();

    }

    public ArrayList getCardDeck()
    {
        ArrayList retVal = new ArrayList();
        AbstractCard card;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); retVal.add(new CardSave(card.cardID, card.timesUpgraded, card.misc)))
            card = (AbstractCard)iterator.next();

        return retVal;
    }

    public ArrayList getCardNames()
    {
        ArrayList retVal = new ArrayList();
        AbstractCard card;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); retVal.add(card.cardID))
            card = (AbstractCard)iterator.next();

        return retVal;
    }

    public ArrayList getCardIdsForMetrics()
    {
        ArrayList retVal = new ArrayList();
        AbstractCard card;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); retVal.add(card.getMetricID()))
            card = (AbstractCard)iterator.next();

        return retVal;
    }

    public void clear()
    {
        group.clear();
    }

    public boolean contains(AbstractCard c)
    {
        return group.contains(c);
    }

    public boolean canUseAnyCard()
    {
        for(Iterator iterator = group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.hasEnoughEnergy())
                return true;
        }

        return false;
    }

    public int fullSetCheck()
    {
        int times = 0;
        ArrayList cardIDS = new ArrayList();
        HashMap cardCount = group.iterator();
        do
        {
            if(!cardCount.hasNext())
                break;
            AbstractCard c = (AbstractCard)cardCount.next();
            if(c.rarity != AbstractCard.CardRarity.BASIC)
                cardIDS.add(c.cardID);
        } while(true);
        cardCount = new HashMap();
        for(Iterator iterator = cardIDS.iterator(); iterator.hasNext();)
        {
            String cardID = (String)iterator.next();
            if(cardCount.containsKey(cardID))
                cardCount.put(cardID, Integer.valueOf(((Integer)cardCount.get(cardID)).intValue() + 1));
            else
                cardCount.put(cardID, Integer.valueOf(1));
        }

        Iterator iterator1 = cardCount.entrySet().iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            java.util.Map.Entry card = (java.util.Map.Entry)iterator1.next();
            if(((Integer)card.getValue()).intValue() >= 4)
                times++;
        } while(true);
        return times;
    }

    public boolean pauperCheck()
    {
        for(Iterator iterator = group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.rarity == AbstractCard.CardRarity.RARE)
                return false;
        }

        return true;
    }

    public boolean cursedCheck()
    {
        int count = 0;
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == AbstractCard.CardType.CURSE)
                count++;
        } while(true);
        return count >= 5;
    }

    public boolean highlanderCheck()
    {
        ArrayList cardIDS = new ArrayList();
        Set set = group.iterator();
        do
        {
            if(!set.hasNext())
                break;
            AbstractCard c = (AbstractCard)set.next();
            if(c.rarity != AbstractCard.CardRarity.BASIC)
                cardIDS.add(c.cardID);
        } while(true);
        set = new HashSet(cardIDS);
        return set.size() >= cardIDS.size();
    }

    public void applyPowers()
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); c.applyPowers())
            c = (AbstractCard)iterator.next();

    }

    public void removeCard(AbstractCard c)
    {
        group.remove(c);
        if(type == CardGroupType.MASTER_DECK)
        {
            c.onRemoveFromMasterDeck();
            AbstractRelic r;
            for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onMasterDeckChange())
                r = (AbstractRelic)iterator.next();

        }
    }

    public boolean removeCard(String targetID)
    {
        for(Iterator i = group.iterator(); i.hasNext();)
        {
            AbstractCard e = (AbstractCard)i.next();
            if(e.cardID.equals(targetID))
            {
                i.remove();
                return true;
            }
        }

        return false;
    }

    public void addToHand(AbstractCard c)
    {
        c.untip();
        group.add(c);
    }

    public void refreshHandLayout()
    {
        if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            return;
        if(AbstractDungeon.player.hasPower("Surrounded") && AbstractDungeon.getCurrRoom().monsters != null)
        {
            Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(AbstractDungeon.player.flipHorizontal)
                {
                    if(AbstractDungeon.player.drawX < m.drawX)
                    {
                        m.applyPowers();
                    } else
                    {
                        m.applyPowers();
                        m.removeSurroundedPower();
                    }
                } else
                if(!AbstractDungeon.player.flipHorizontal)
                    if(AbstractDungeon.player.drawX > m.drawX)
                    {
                        m.applyPowers();
                    } else
                    {
                        m.applyPowers();
                        m.removeSurroundedPower();
                    }
            } while(true);
        }
        AbstractOrb o;
        for(Iterator iterator1 = AbstractDungeon.player.orbs.iterator(); iterator1.hasNext(); o.hideEvokeValues())
            o = (AbstractOrb)iterator1.next();

        if(AbstractDungeon.player.hand.size() + AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() <= 3 && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.getCurrRoom().monsters != null && !AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead() && AbstractDungeon.floorNum > 3)
            UnlockTracker.unlockAchievement("PURITY");
        AbstractRelic r;
        for(Iterator iterator2 = AbstractDungeon.player.relics.iterator(); iterator2.hasNext(); r.onRefreshHand())
            r = (AbstractRelic)iterator2.next();

        float angleRange = 50F - (float)(10 - group.size()) * 5F;
        float incrementAngle = angleRange / (float)group.size();
        float sinkStart = 80F * Settings.scale;
        float sinkRange = 300F * Settings.scale;
        float incrementSink = sinkRange / (float)group.size() / 2.0F;
        int middle = group.size() / 2;
        for(int i = 0; i < group.size(); i++)
        {
            ((AbstractCard)group.get(i)).setAngle(angleRange / 2.0F - incrementAngle * (float)i - incrementAngle / 2.0F);
            int t = i - middle;
            if(t >= 0)
                if(group.size() % 2 == 0)
                    t = -++t;
                else
                    t = -t;
            if(group.size() % 2 == 0)
                t++;
            t = (int)((float)t * 1.7F);
            ((AbstractCard)group.get(i)).target_y = sinkStart + incrementSink * (float)t;
        }

        for(Iterator iterator3 = group.iterator(); iterator3.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator3.next();
            c.targetDrawScale = 0.75F;
        }

        switch(group.size())
        {
        case 0: // '\0'
            return;

        case 1: // '\001'
            ((AbstractCard)group.get(0)).target_x = (float)Settings.WIDTH / 2.0F;
            break;

        case 2: // '\002'
            ((AbstractCard)group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 0.47F;
            ((AbstractCard)group.get(1)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 0.53F;
            break;

        case 3: // '\003'
            ((AbstractCard)group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 0.9F;
            ((AbstractCard)group.get(1)).target_x = (float)Settings.WIDTH / 2.0F;
            ((AbstractCard)group.get(2)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 0.9F;
            ((AbstractCard)group.get(0)).target_y += 20F * Settings.scale;
            ((AbstractCard)group.get(2)).target_y += 20F * Settings.scale;
            break;

        case 4: // '\004'
            ((AbstractCard)group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 1.36F;
            ((AbstractCard)group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 0.46F;
            ((AbstractCard)group.get(2)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 0.46F;
            ((AbstractCard)group.get(3)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 1.36F;
            ((AbstractCard)group.get(1)).target_y -= 10F * Settings.scale;
            ((AbstractCard)group.get(2)).target_y -= 10F * Settings.scale;
            break;

        case 5: // '\005'
            ((AbstractCard)group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 1.7F;
            ((AbstractCard)group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 0.9F;
            ((AbstractCard)group.get(2)).target_x = (float)Settings.WIDTH / 2.0F;
            ((AbstractCard)group.get(3)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 0.9F;
            ((AbstractCard)group.get(4)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 1.7F;
            ((AbstractCard)group.get(0)).target_y += 25F * Settings.scale;
            ((AbstractCard)group.get(2)).target_y -= 10F * Settings.scale;
            ((AbstractCard)group.get(4)).target_y += 25F * Settings.scale;
            break;

        case 6: // '\006'
            ((AbstractCard)group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 2.1F;
            ((AbstractCard)group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 1.3F;
            ((AbstractCard)group.get(2)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 0.43F;
            ((AbstractCard)group.get(3)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 0.43F;
            ((AbstractCard)group.get(4)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 1.3F;
            ((AbstractCard)group.get(5)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 2.1F;
            ((AbstractCard)group.get(0)).target_y += 10F * Settings.scale;
            ((AbstractCard)group.get(5)).target_y += 10F * Settings.scale;
            break;

        case 7: // '\007'
            ((AbstractCard)group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 2.4F;
            ((AbstractCard)group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 1.7F;
            ((AbstractCard)group.get(2)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 0.9F;
            ((AbstractCard)group.get(3)).target_x = (float)Settings.WIDTH / 2.0F;
            ((AbstractCard)group.get(4)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 0.9F;
            ((AbstractCard)group.get(5)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 1.7F;
            ((AbstractCard)group.get(6)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 2.4F;
            ((AbstractCard)group.get(0)).target_y += 25F * Settings.scale;
            ((AbstractCard)group.get(1)).target_y += 18F * Settings.scale;
            ((AbstractCard)group.get(3)).target_y -= 6F * Settings.scale;
            ((AbstractCard)group.get(5)).target_y += 18F * Settings.scale;
            ((AbstractCard)group.get(6)).target_y += 25F * Settings.scale;
            break;

        case 8: // '\b'
            ((AbstractCard)group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 2.5F;
            ((AbstractCard)group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 1.82F;
            ((AbstractCard)group.get(2)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 1.1F;
            ((AbstractCard)group.get(3)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 0.38F;
            ((AbstractCard)group.get(4)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 0.38F;
            ((AbstractCard)group.get(5)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 1.1F;
            ((AbstractCard)group.get(6)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 1.77F;
            ((AbstractCard)group.get(7)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 2.5F;
            ((AbstractCard)group.get(1)).target_y += 10F * Settings.scale;
            ((AbstractCard)group.get(6)).target_y += 10F * Settings.scale;
            for(Iterator iterator4 = group.iterator(); iterator4.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator4.next();
                c.targetDrawScale = 0.7125F;
            }

            break;

        case 9: // '\t'
            ((AbstractCard)group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 2.8F;
            ((AbstractCard)group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 2.2F;
            ((AbstractCard)group.get(2)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 1.53F;
            ((AbstractCard)group.get(3)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 0.8F;
            ((AbstractCard)group.get(4)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 0.0F;
            ((AbstractCard)group.get(5)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 0.8F;
            ((AbstractCard)group.get(6)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 1.53F;
            ((AbstractCard)group.get(7)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 2.2F;
            ((AbstractCard)group.get(8)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 2.8F;
            ((AbstractCard)group.get(1)).target_y += 22F * Settings.scale;
            ((AbstractCard)group.get(2)).target_y += 18F * Settings.scale;
            ((AbstractCard)group.get(3)).target_y += 12F * Settings.scale;
            ((AbstractCard)group.get(5)).target_y += 12F * Settings.scale;
            ((AbstractCard)group.get(6)).target_y += 18F * Settings.scale;
            ((AbstractCard)group.get(7)).target_y += 22F * Settings.scale;
            for(Iterator iterator5 = group.iterator(); iterator5.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator5.next();
                c.targetDrawScale = 0.675F;
            }

            break;

        case 10: // '\n'
            ((AbstractCard)group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 2.9F;
            ((AbstractCard)group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 2.4F;
            ((AbstractCard)group.get(2)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 1.8F;
            ((AbstractCard)group.get(3)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 1.1F;
            ((AbstractCard)group.get(4)).target_x = (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH_S * 0.4F;
            ((AbstractCard)group.get(5)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 0.4F;
            ((AbstractCard)group.get(6)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 1.1F;
            ((AbstractCard)group.get(7)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 1.8F;
            ((AbstractCard)group.get(8)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 2.4F;
            ((AbstractCard)group.get(9)).target_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH_S * 2.9F;
            ((AbstractCard)group.get(1)).target_y += 20F * Settings.scale;
            ((AbstractCard)group.get(2)).target_y += 17F * Settings.scale;
            ((AbstractCard)group.get(3)).target_y += 12F * Settings.scale;
            ((AbstractCard)group.get(4)).target_y += 5F * Settings.scale;
            ((AbstractCard)group.get(5)).target_y += 5F * Settings.scale;
            ((AbstractCard)group.get(6)).target_y += 12F * Settings.scale;
            ((AbstractCard)group.get(7)).target_y += 17F * Settings.scale;
            ((AbstractCard)group.get(8)).target_y += 20F * Settings.scale;
            for(Iterator iterator6 = group.iterator(); iterator6.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator6.next();
                c.targetDrawScale = 0.6375F;
            }

            break;

        default:
            logger.info("WTF MATE, why so many cards");
            break;
        }
        AbstractCard card = AbstractDungeon.player.hoveredCard;
        if(card != null)
        {
            card.setAngle(0.0F);
            card.target_x = (card.current_x + card.target_x) / 2.0F;
            card.target_y = card.current_y;
        }
        Iterator iterator7 = AbstractDungeon.actionManager.cardQueue.iterator();
        do
        {
            if(!iterator7.hasNext())
                break;
            CardQueueItem q = (CardQueueItem)iterator7.next();
            if(q.card != null)
            {
                q.card.setAngle(0.0F);
                q.card.target_x = q.card.current_x;
                q.card.target_y = q.card.current_y;
            }
        } while(true);
        glowCheck();
    }

    public void glowCheck()
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); c.triggerOnGlowCheck())
        {
            c = (AbstractCard)iterator.next();
            if(c.canUse(AbstractDungeon.player, null) && AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.HAND_SELECT)
                c.beginGlowing();
            else
                c.stopGlowing();
        }

    }

    public void stopGlowing()
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); c.stopGlowing())
            c = (AbstractCard)iterator.next();

    }

    public void hoverCardPush(AbstractCard c)
    {
        if(group.size() > 1)
        {
            int cardNum = -1;
            int i = 0;
            do
            {
                if(i >= group.size())
                    break;
                if(c.equals(group.get(i)))
                {
                    cardNum = i;
                    break;
                }
                i++;
            } while(true);
            float pushAmt = 0.4F;
            if(group.size() == 2)
                pushAmt = 0.2F;
            else
            if(group.size() == 3 || group.size() == 4)
                pushAmt = 0.27F;
            for(int currentSlot = cardNum + 1; currentSlot < group.size(); currentSlot++)
            {
                ((AbstractCard)group.get(currentSlot)).target_x += AbstractCard.IMG_WIDTH_S * pushAmt;
                pushAmt *= 0.25F;
            }

            pushAmt = 0.4F;
            if(group.size() == 2)
                pushAmt = 0.2F;
            else
            if(group.size() == 3 || group.size() == 4)
                pushAmt = 0.27F;
            for(int currentSlot = cardNum - 1; currentSlot > -1 && currentSlot < group.size(); currentSlot--)
            {
                ((AbstractCard)group.get(currentSlot)).target_x -= AbstractCard.IMG_WIDTH_S * pushAmt;
                pushAmt *= 0.25F;
            }

        }
    }

    public void addToTop(AbstractCard c)
    {
        group.add(c);
    }

    public void addToBottom(AbstractCard c)
    {
        group.add(0, c);
    }

    public void addToRandomSpot(AbstractCard c)
    {
        if(group.size() == 0)
            group.add(c);
        else
            group.add(AbstractDungeon.cardRandomRng.random(group.size() - 1), c);
    }

    public AbstractCard getTopCard()
    {
        return (AbstractCard)group.get(group.size() - 1);
    }

    public AbstractCard getNCardFromTop(int num)
    {
        return (AbstractCard)group.get(group.size() - 1 - num);
    }

    public AbstractCard getBottomCard()
    {
        return (AbstractCard)group.get(0);
    }

    public AbstractCard getHoveredCard()
    {
label0:
        {
            Iterator iterator = group.iterator();
            AbstractCard c;
            boolean success;
label1:
            do
            {
                do
                {
                    if(!iterator.hasNext())
                        break label0;
                    c = (AbstractCard)iterator.next();
                } while(!c.isHoveredInHand(0.7F));
                success = true;
                Iterator iterator1 = AbstractDungeon.actionManager.cardQueue.iterator();
                CardQueueItem q;
                do
                {
                    if(!iterator1.hasNext())
                        continue label1;
                    q = (CardQueueItem)iterator1.next();
                } while(q.card != c);
                success = false;
            } while(!success);
            return c;
        }
        return null;
    }

    public AbstractCard getRandomCard(com.megacrit.cardcrawl.random.Random rng)
    {
        return (AbstractCard)group.get(rng.random(group.size() - 1));
    }

    public AbstractCard getRandomCard(boolean useRng)
    {
        if(useRng)
            return (AbstractCard)group.get(AbstractDungeon.cardRng.random(group.size() - 1));
        else
            return (AbstractCard)group.get(MathUtils.random(group.size() - 1));
    }

    public AbstractCard getRandomCard(boolean useRng, AbstractCard.CardRarity rarity)
    {
        ArrayList tmp = new ArrayList();
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.rarity == rarity)
                tmp.add(c);
        } while(true);
        if(tmp.isEmpty())
        {
            logger.info((new StringBuilder()).append("ERROR: No cards left for type: ").append(type.name()).toString());
            return null;
        }
        Collections.sort(tmp);
        if(useRng)
            return (AbstractCard)tmp.get(AbstractDungeon.cardRng.random(tmp.size() - 1));
        else
            return (AbstractCard)tmp.get(MathUtils.random(tmp.size() - 1));
    }

    public AbstractCard getRandomCard(com.megacrit.cardcrawl.random.Random rng, AbstractCard.CardRarity rarity)
    {
        ArrayList tmp = new ArrayList();
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.rarity == rarity)
                tmp.add(c);
        } while(true);
        if(tmp.isEmpty())
        {
            logger.info((new StringBuilder()).append("ERROR: No cards left for type: ").append(type.name()).toString());
            return null;
        } else
        {
            Collections.sort(tmp);
            return (AbstractCard)tmp.get(rng.random(tmp.size() - 1));
        }
    }

    public AbstractCard getRandomCard(AbstractCard.CardType type, boolean useRng)
    {
        ArrayList tmp = new ArrayList();
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == type)
                tmp.add(c);
        } while(true);
        if(tmp.isEmpty())
        {
            logger.info((new StringBuilder()).append("ERROR: No cards left for type: ").append(type.name()).toString());
            return null;
        }
        Collections.sort(tmp);
        if(useRng)
            return (AbstractCard)tmp.get(AbstractDungeon.cardRng.random(tmp.size() - 1));
        else
            return (AbstractCard)tmp.get(MathUtils.random(tmp.size() - 1));
    }

    public void removeTopCard()
    {
        group.remove(group.size() - 1);
    }

    public void shuffle()
    {
        Collections.shuffle(group, new Random(AbstractDungeon.shuffleRng.randomLong()));
    }

    public void shuffle(com.megacrit.cardcrawl.random.Random rng)
    {
        Collections.shuffle(group, new Random(rng.randomLong()));
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("");
        for(Iterator iterator = group.iterator(); iterator.hasNext(); sb.append("\n"))
        {
            AbstractCard c = (AbstractCard)iterator.next();
            sb.append(c.cardID);
        }

        return sb.toString();
    }

    public void update()
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); c.update())
            c = (AbstractCard)iterator.next();

    }

    public void updateHoverLogic()
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); c.updateHoverLogic())
            c = (AbstractCard)iterator.next();

    }

    public void render(SpriteBatch sb)
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); c.render(sb))
            c = (AbstractCard)iterator.next();

    }

    public void renderShowBottled(SpriteBatch sb)
    {
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            c.render(sb);
            if(c.inBottleFlame)
            {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Flame");
                float prevX = tmp.currentX;
                float prevY = tmp.currentY;
                tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                tmp.scale = c.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            } else
            if(c.inBottleLightning)
            {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Lightning");
                float prevX = tmp.currentX;
                float prevY = tmp.currentY;
                tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                tmp.scale = c.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            } else
            if(c.inBottleTornado)
            {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Tornado");
                float prevX = tmp.currentX;
                float prevY = tmp.currentY;
                tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                tmp.scale = c.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            }
        } while(true);
    }

    public void renderMasterDeck(SpriteBatch sb)
    {
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            c.render(sb);
            if(c.inBottleFlame)
            {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Flame");
                float prevX = tmp.currentX;
                float prevY = tmp.currentY;
                tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                tmp.scale = c.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            } else
            if(c.inBottleLightning)
            {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Lightning");
                float prevX = tmp.currentX;
                float prevY = tmp.currentY;
                tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                tmp.scale = c.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            } else
            if(c.inBottleTornado)
            {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Tornado");
                float prevX = tmp.currentX;
                float prevY = tmp.currentY;
                tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                tmp.scale = c.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            }
        } while(true);
    }

    public void renderExceptOneCard(SpriteBatch sb, AbstractCard card)
    {
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.equals(card))
                c.render(sb);
        } while(true);
    }

    public void renderExceptOneCardShowBottled(SpriteBatch sb, AbstractCard card)
    {
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.equals(card))
            {
                c.render(sb);
                if(c.inBottleFlame)
                {
                    AbstractRelic tmp = RelicLibrary.getRelic("Bottled Flame");
                    float prevX = tmp.currentX;
                    float prevY = tmp.currentY;
                    tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                    tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                    tmp.scale = c.drawScale * Settings.scale * 1.5F;
                    tmp.render(sb);
                    tmp.currentX = prevX;
                    tmp.currentY = prevY;
                    tmp = null;
                } else
                if(c.inBottleLightning)
                {
                    AbstractRelic tmp = RelicLibrary.getRelic("Bottled Lightning");
                    float prevX = tmp.currentX;
                    float prevY = tmp.currentY;
                    tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                    tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                    tmp.scale = c.drawScale * Settings.scale * 1.5F;
                    tmp.render(sb);
                    tmp.currentX = prevX;
                    tmp.currentY = prevY;
                    tmp = null;
                } else
                if(c.inBottleTornado)
                {
                    AbstractRelic tmp = RelicLibrary.getRelic("Bottled Tornado");
                    float prevX = tmp.currentX;
                    float prevY = tmp.currentY;
                    tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                    tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                    tmp.scale = c.drawScale * Settings.scale * 1.5F;
                    tmp.render(sb);
                    tmp.currentX = prevX;
                    tmp.currentY = prevY;
                    tmp = null;
                }
            }
        } while(true);
    }

    public void renderMasterDeckExceptOneCard(SpriteBatch sb, AbstractCard card)
    {
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.equals(card))
            {
                c.render(sb);
                if(c.inBottleFlame)
                {
                    AbstractRelic tmp = RelicLibrary.getRelic("Bottled Flame");
                    float prevX = tmp.currentX;
                    float prevY = tmp.currentY;
                    tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                    tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                    tmp.scale = c.drawScale * Settings.scale * 1.5F;
                    tmp.render(sb);
                    tmp.currentX = prevX;
                    tmp.currentY = prevY;
                    tmp = null;
                } else
                if(c.inBottleLightning)
                {
                    AbstractRelic tmp = RelicLibrary.getRelic("Bottled Lightning");
                    float prevX = tmp.currentX;
                    float prevY = tmp.currentY;
                    tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                    tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                    tmp.scale = c.drawScale * Settings.scale * 1.5F;
                    tmp.render(sb);
                    tmp.currentX = prevX;
                    tmp.currentY = prevY;
                    tmp = null;
                } else
                if(c.inBottleTornado)
                {
                    AbstractRelic tmp = RelicLibrary.getRelic("Bottled Tornado");
                    float prevX = tmp.currentX;
                    float prevY = tmp.currentY;
                    tmp.currentX = c.current_x + ((390F * c.drawScale) / 3F) * Settings.scale;
                    tmp.currentY = c.current_y + ((546F * c.drawScale) / 3F) * Settings.scale;
                    tmp.scale = c.drawScale * Settings.scale * 1.5F;
                    tmp.render(sb);
                    tmp.currentX = prevX;
                    tmp.currentY = prevY;
                    tmp = null;
                }
            }
        } while(true);
    }

    public void renderHand(SpriteBatch sb, AbstractCard exceptThis)
    {
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c == exceptThis)
                continue;
            boolean inQueue = false;
            Iterator iterator3 = AbstractDungeon.actionManager.cardQueue.iterator();
            do
            {
                if(!iterator3.hasNext())
                    break;
                CardQueueItem i = (CardQueueItem)iterator3.next();
                if(i.card == null || !i.card.equals(c))
                    continue;
                queued.add(c);
                inQueue = true;
                break;
            } while(true);
            if(!inQueue)
                inHand.add(c);
        } while(true);
        AbstractCard c;
        for(Iterator iterator1 = inHand.iterator(); iterator1.hasNext(); c.render(sb))
            c = (AbstractCard)iterator1.next();

        AbstractCard c;
        for(Iterator iterator2 = queued.iterator(); iterator2.hasNext(); c.render(sb))
            c = (AbstractCard)iterator2.next();

        inHand.clear();
        queued.clear();
    }

    public void renderInLibrary(SpriteBatch sb)
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); c.renderInLibrary(sb))
            c = (AbstractCard)iterator.next();

    }

    public void renderTip(SpriteBatch sb)
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); c.renderCardTip(sb))
            c = (AbstractCard)iterator.next();

    }

    public void renderWithSelections(SpriteBatch sb)
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); c.renderWithSelections(sb))
            c = (AbstractCard)iterator.next();

    }

    public void renderDiscardPile(SpriteBatch sb)
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); c.render(sb))
            c = (AbstractCard)iterator.next();

    }

    public void moveToDiscardPile(AbstractCard c)
    {
        resetCardBeforeMoving(c);
        c.shrink();
        c.darken(false);
        AbstractDungeon.getCurrRoom().souls.discard(c);
        AbstractDungeon.player.onCardDrawOrDiscard();
    }

    public void empower(AbstractCard c)
    {
        resetCardBeforeMoving(c);
        c.shrink();
        AbstractDungeon.getCurrRoom().souls.empower(c);
    }

    public void moveToExhaustPile(AbstractCard c)
    {
        AbstractRelic r;
        for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onExhaust(c))
            r = (AbstractRelic)iterator.next();

        AbstractPower p;
        for(Iterator iterator1 = AbstractDungeon.player.powers.iterator(); iterator1.hasNext(); p.onExhaust(c))
            p = (AbstractPower)iterator1.next();

        c.triggerOnExhaust();
        resetCardBeforeMoving(c);
        AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        AbstractDungeon.player.exhaustPile.addToTop(c);
        AbstractDungeon.player.onCardDrawOrDiscard();
    }

    public void moveToHand(AbstractCard c, CardGroup group)
    {
        c.unhover();
        c.lighten(true);
        c.setAngle(0.0F);
        c.drawScale = 0.12F;
        c.targetDrawScale = 0.75F;
        c.current_x = DRAW_PILE_X;
        c.current_y = DRAW_PILE_Y;
        group.removeCard(c);
        AbstractDungeon.player.hand.addToTop(c);
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.applyPowers();
    }

    public void moveToHand(AbstractCard c)
    {
        resetCardBeforeMoving(c);
        c.unhover();
        c.lighten(true);
        c.setAngle(0.0F);
        c.drawScale = 0.12F;
        c.targetDrawScale = 0.75F;
        c.current_x = DRAW_PILE_X;
        c.current_y = DRAW_PILE_Y;
        AbstractDungeon.player.hand.addToTop(c);
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.applyPowers();
    }

    public void moveToDeck(AbstractCard c, boolean randomSpot)
    {
        resetCardBeforeMoving(c);
        c.shrink();
        AbstractDungeon.getCurrRoom().souls.onToDeck(c, randomSpot);
    }

    public void moveToBottomOfDeck(AbstractCard c)
    {
        resetCardBeforeMoving(c);
        c.shrink();
        AbstractDungeon.getCurrRoom().souls.onToBottomOfDeck(c);
    }

    private void resetCardBeforeMoving(AbstractCard c)
    {
        if(AbstractDungeon.player.hoveredCard == c)
            AbstractDungeon.player.releaseCard();
        AbstractDungeon.actionManager.removeFromQueue(c);
        c.unhover();
        c.untip();
        c.stopGlowing();
        group.remove(c);
    }

    public boolean isEmpty()
    {
        return group.isEmpty();
    }

    private void discardAll(CardGroup discardPile)
    {
        AbstractCard c;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); discardPile.addToTop(c))
        {
            c = (AbstractCard)iterator.next();
            c.target_x = DISCARD_PILE_X;
            c.target_y = 0.0F;
        }

        group.clear();
    }

    public void initializeDeck(CardGroup masterDeck)
    {
        clear();
        CardGroup copy = new CardGroup(masterDeck, CardGroupType.DRAW_PILE);
        copy.shuffle(AbstractDungeon.shuffleRng);
        ArrayList placeOnTop = new ArrayList();
        for(Iterator iterator = copy.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.isInnate)
                placeOnTop.add(c);
            else
            if(c.inBottleFlame || c.inBottleLightning || c.inBottleTornado)
            {
                placeOnTop.add(c);
            } else
            {
                c.target_x = DRAW_PILE_X;
                c.target_y = DRAW_PILE_Y;
                c.current_x = DRAW_PILE_X;
                c.current_y = DRAW_PILE_Y;
                addToTop(c);
            }
        }

        AbstractCard c;
        for(Iterator iterator1 = placeOnTop.iterator(); iterator1.hasNext(); addToTop(c))
            c = (AbstractCard)iterator1.next();

        if(placeOnTop.size() > AbstractDungeon.player.masterHandSize)
            AbstractDungeon.actionManager.addToTurnStart(new DrawCardAction(AbstractDungeon.player, placeOnTop.size() - AbstractDungeon.player.masterHandSize));
        placeOnTop.clear();
    }

    public int size()
    {
        return group.size();
    }

    public CardGroup getUpgradableCards()
    {
        CardGroup retVal = new CardGroup(CardGroupType.UNSPECIFIED);
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.canUpgrade())
                retVal.group.add(c);
        } while(true);
        return retVal;
    }

    public Boolean hasUpgradableCards()
    {
        for(Iterator iterator = group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.canUpgrade())
                return Boolean.valueOf(true);
        }

        return Boolean.valueOf(false);
    }

    public CardGroup getPurgeableCards()
    {
        CardGroup retVal = new CardGroup(CardGroupType.UNSPECIFIED);
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.cardID.equals("Necronomicurse") && !c.cardID.equals("CurseOfTheBell") && !c.cardID.equals("AscendersBane"))
                retVal.group.add(c);
        } while(true);
        return retVal;
    }

    public AbstractCard getSpecificCard(AbstractCard targetCard)
    {
        if(group.contains(targetCard))
            return targetCard;
        else
            return null;
    }

    public void triggerOnOtherCardPlayed(AbstractCard usedCard)
    {
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c != usedCard)
                c.triggerOnOtherCardPlayed(usedCard);
        } while(true);
        AbstractPower p;
        for(Iterator iterator1 = AbstractDungeon.player.powers.iterator(); iterator1.hasNext(); p.onAfterCardPlayed(usedCard))
            p = (AbstractPower)iterator1.next();

    }

    private void sortWithComparator(Comparator comp, boolean ascending)
    {
        if(ascending)
            group.sort(comp);
        else
            group.sort(Collections.reverseOrder(comp));
    }

    public void sortByRarity(boolean ascending)
    {
        sortWithComparator(new CardRarityComparator(), ascending);
    }

    public void sortByRarityPlusStatusCardType(boolean ascending)
    {
        sortWithComparator(new CardRarityComparator(), ascending);
        sortWithComparator(new StatusCardsLastComparator(), true);
    }

    public void sortByType(boolean ascending)
    {
        sortWithComparator(new CardTypeComparator(), ascending);
    }

    public void sortByAcquisition()
    {
    }

    public void sortByStatus(boolean ascending)
    {
        sortWithComparator(new CardLockednessComparator(), ascending);
    }

    public void sortAlphabetically(boolean ascending)
    {
        sortWithComparator(new CardNameComparator(), ascending);
    }

    public void sortByCost(boolean ascending)
    {
        sortWithComparator(new CardCostComparator(), ascending);
    }

    public CardGroup getSkills()
    {
        return getCardsOfType(AbstractCard.CardType.SKILL);
    }

    public CardGroup getAttacks()
    {
        return getCardsOfType(AbstractCard.CardType.ATTACK);
    }

    public CardGroup getPowers()
    {
        return getCardsOfType(AbstractCard.CardType.POWER);
    }

    public CardGroup getCardsOfType(AbstractCard.CardType cardType)
    {
        CardGroup retVal = new CardGroup(CardGroupType.UNSPECIFIED);
        Iterator iterator = group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard card = (AbstractCard)iterator.next();
            if(card.type == cardType)
                retVal.addToBottom(card);
        } while(true);
        return retVal;
    }

    public CardGroup getGroupedByColor()
    {
        ArrayList colorGroups = new ArrayList();
        AbstractCard.CardColor acardcolor[] = AbstractCard.CardColor.values();
        int i = acardcolor.length;
        for(int j = 0; j < i; j++)
        {
            AbstractCard.CardColor color = acardcolor[j];
            colorGroups.add(new CardGroup(CardGroupType.UNSPECIFIED));
        }

        AbstractCard card;
        for(Iterator iterator = this.group.iterator(); iterator.hasNext(); ((CardGroup)colorGroups.get(card.color.ordinal())).addToTop(card))
            card = (AbstractCard)iterator.next();

        CardGroup retVal = new CardGroup(CardGroupType.UNSPECIFIED);
        CardGroup group;
        for(Iterator iterator1 = colorGroups.iterator(); iterator1.hasNext(); retVal.group.addAll(group.group))
            group = (CardGroup)iterator1.next();

        return retVal;
    }

    public AbstractCard findCardById(String id)
    {
        for(Iterator iterator = group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.cardID.equals(id))
                return c;
        }

        return null;
    }

    public static CardGroup getGroupWithoutBottledCards(CardGroup group)
    {
        CardGroup retVal = new CardGroup(CardGroupType.UNSPECIFIED);
        Iterator iterator = group.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.inBottleFlame && !c.inBottleLightning && !c.inBottleTornado)
                retVal.addToTop(c);
        } while(true);
        return retVal;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/cards/CardGroup.getName());
    public ArrayList group;
    public float HAND_START_X;
    public float HAND_OFFSET_X;
    private static final float HAND_HOVER_PUSH_AMT = 0.4F;
    private static final float PUSH_TAPER = 0.25F;
    private static final float TWO_CARD_PUSH_AMT = 0.2F;
    private static final float THREE_FOUR_CARD_PUSH_AMT = 0.27F;
    public static final float DRAW_PILE_X;
    public static final float DRAW_PILE_Y;
    public static final int DISCARD_PILE_X;
    public static final int DISCARD_PILE_Y = 0;
    public CardGroupType type;
    public HashMap handPositioningMap;
    private ArrayList queued;
    private ArrayList inHand;

    static 
    {
        DRAW_PILE_X = (float)Settings.WIDTH * 0.04F;
        DRAW_PILE_Y = 50F * Settings.scale;
        DISCARD_PILE_X = (int)((float)Settings.WIDTH + AbstractCard.IMG_WIDTH_S / 2.0F + 100F * Settings.scale);
    }
}
