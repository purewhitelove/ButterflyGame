// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CodexAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class CodexAction extends AbstractGameAction
{

    public CodexAction()
    {
        retrieveCard = false;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            isDone = true;
            return;
        }
        if(duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);
            tickDuration();
            return;
        }
        if(!retrieveCard)
        {
            if(AbstractDungeon.cardRewardScreen.discoveryCard != null)
            {
                AbstractCard codexCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                codexCard.current_x = -1000F * Settings.xScale;
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(codexCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, true));
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            retrieveCard = true;
        }
        tickDuration();
    }

    private ArrayList generateCardChoices()
    {
        ArrayList derp = new ArrayList();
        do
        {
            if(derp.size() == 3)
                break;
            boolean dupe = false;
            AbstractCard tmp = AbstractDungeon.returnTrulyRandomCardInCombat();
            Iterator iterator = derp.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(!c.cardID.equals(tmp.cardID))
                    continue;
                dupe = true;
                break;
            } while(true);
            if(!dupe)
                derp.add(tmp.makeCopy());
        } while(true);
        return derp;
    }

    public static int numPlaced;
    private boolean retrieveCard;
}
