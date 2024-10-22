// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ResetFlagsAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResetFlagsAction extends AbstractGameAction
{

    public ResetFlagsAction(AbstractCard card)
    {
        duration = Settings.ACTION_DUR_FAST;
        this.card = card;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            logger.info("Resetting flags");
            card = card.makeStatEquivalentCopy();
            isDone = true;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/actions/utility/ResetFlagsAction.getName());
    private AbstractCard card;

}
