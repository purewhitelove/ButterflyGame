// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDDamagePerCardAction.java

package com.megacrit.cardcrawl.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DEPRECATEDDamagePerCardAction extends AbstractGameAction
{

    public DEPRECATEDDamagePerCardAction(AbstractCreature target, DamageInfo info, String cardName, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        this.info = info;
        this.cardName = cardName;
        attackEffect = effect;
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
    }

    public void update()
    {
        if(!isDone)
        {
            isDone = true;
            Iterator iterator = AbstractDungeon.player.hand.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.originalName.equals(cardName))
                {
                    logger.info("QUEUED DAMAGE...");
                    AbstractDungeon.actionManager.addToTop(new DamageAction(target, info, attackEffect));
                }
            } while(true);
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/actions/deprecated/DEPRECATEDDamagePerCardAction.getName());
    private DamageInfo info;
    private String cardName;

}
