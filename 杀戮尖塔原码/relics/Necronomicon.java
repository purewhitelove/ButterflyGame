// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Necronomicon.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Necronomicon extends AbstractRelic
{

    public Necronomicon()
    {
        super("Necronomicon", "necronomicon.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.FLAT);
        activated = true;
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(2).append(DESCRIPTIONS[1]).toString();
    }

    public void onEquip()
    {
        CardCrawlGame.sound.play("NECRONOMICON");
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(2).append(DESCRIPTIONS[2]).toString();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Necronomicurse(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        UnlockTracker.markCardAsSeen("Necronomicurse");
    }

    public void onUnequip()
    {
        AbstractCard cardToRemove = null;
        Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!(c instanceof Necronomicurse))
                continue;
            cardToRemove = c;
            break;
        } while(true);
        if(cardToRemove != null)
            AbstractDungeon.player.masterDeck.group.remove(cardToRemove);
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK && (card.costForTurn >= 2 && !card.freeToPlayOnce || card.cost == -1 && card.energyOnUse >= 2) && activated)
        {
            activated = false;
            flash();
            AbstractMonster m = null;
            if(action.target != null)
                m = (AbstractMonster)action.target;
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractCard tmp = card.makeSameInstanceOf();
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float)Settings.WIDTH / 2.0F - 300F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            tmp.applyPowers();
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            pulse = false;
        }
    }

    public void atTurnStart()
    {
        activated = true;
    }

    public boolean checkTrigger()
    {
        return activated;
    }

    public AbstractRelic makeCopy()
    {
        return new Necronomicon();
    }

    public static final String ID = "Necronomicon";
    private static final int COST_THRESHOLD = 2;
    private boolean activated;
}
