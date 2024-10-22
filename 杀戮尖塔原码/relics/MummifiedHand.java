// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MummifiedHand.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class MummifiedHand extends AbstractRelic
{

    public MummifiedHand()
    {
        super("Mummified Hand", "mummifiedHand.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER)
        {
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            ArrayList groupCopy = new ArrayList();
            for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce)
                    groupCopy.add(c);
                else
                    logger.info((new StringBuilder()).append("COST IS 0: ").append(c.name).toString());
            }

            AbstractCard c = AbstractDungeon.actionManager.cardQueue.iterator();
            do
            {
                if(!c.hasNext())
                    break;
                CardQueueItem i = (CardQueueItem)c.next();
                if(i.card != null)
                {
                    logger.info((new StringBuilder()).append("INVALID: ").append(i.card.name).toString());
                    groupCopy.remove(i.card);
                }
            } while(true);
            c = null;
            if(!groupCopy.isEmpty())
            {
                logger.info("VALID CARDS: ");
                AbstractCard cc;
                for(Iterator iterator1 = groupCopy.iterator(); iterator1.hasNext(); logger.info(cc.name))
                    cc = (AbstractCard)iterator1.next();

                c = (AbstractCard)groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            } else
            {
                logger.info("NO VALID CARDS");
            }
            if(c != null)
            {
                logger.info((new StringBuilder()).append("Mummified hand: ").append(c.name).toString());
                c.setCostForTurn(0);
            } else
            {
                logger.info("ERROR: MUMMIFIED HAND NOT WORKING");
            }
        }
    }

    public AbstractRelic makeCopy()
    {
        return new MummifiedHand();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/relics/MummifiedHand.getName());
    public static final String ID = "Mummified Hand";

}
