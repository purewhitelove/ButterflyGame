// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LetterOpener.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class LetterOpener extends AbstractRelic
{

    public LetterOpener()
    {
        super("Letter Opener", "letterOpener.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(5).append(DESCRIPTIONS[1]).toString();
    }

    public void atTurnStart()
    {
        counter = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
        {
            counter++;
            if(counter % 3 == 0)
            {
                flash();
                counter = 0;
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(5, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
            }
        }
    }

    public void onVictory()
    {
        counter = -1;
    }

    public AbstractRelic makeCopy()
    {
        return new LetterOpener();
    }

    public static final String ID = "Letter Opener";
    private static final int DAMAGE = 5;
}
