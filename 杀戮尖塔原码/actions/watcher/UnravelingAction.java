// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnravelingAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class UnravelingAction extends AbstractGameAction
{

    public UnravelingAction()
    {
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            Iterator iterator = AbstractDungeon.player.hand.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                for(Iterator iterator1 = AbstractDungeon.actionManager.cardQueue.iterator(); iterator1.hasNext();)
                {
                    CardQueueItem q = (CardQueueItem)iterator1.next();
                    if(q.card != c);
                }

                c.freeToPlayOnce = true;
                static class _cls1
                {

                    static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[];

                    static 
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.values().length];
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF_AND_ENEMY.ordinal()] = 1;
                        }
                        catch(NoSuchFieldError nosuchfielderror) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY.ordinal()] = 2;
                        }
                        catch(NoSuchFieldError nosuchfielderror1) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF.ordinal()] = 3;
                        }
                        catch(NoSuchFieldError nosuchfielderror2) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL.ordinal()] = 4;
                        }
                        catch(NoSuchFieldError nosuchfielderror3) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY.ordinal()] = 5;
                        }
                        catch(NoSuchFieldError nosuchfielderror4) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE.ordinal()] = 6;
                        }
                        catch(NoSuchFieldError nosuchfielderror5) { }
                    }
                }

                switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardTarget[c.target.ordinal()])
                {
                case 1: // '\001'
                case 2: // '\002'
                    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(c, AbstractDungeon.getRandomMonster()));
                    break;

                case 3: // '\003'
                case 4: // '\004'
                case 5: // '\005'
                case 6: // '\006'
                default:
                    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(c, null));
                    break;
                }
            } while(true);
        }
        tickDuration();
    }
}
