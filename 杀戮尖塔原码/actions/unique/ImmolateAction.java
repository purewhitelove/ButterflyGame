// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImmolateAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import com.megacrit.cardcrawl.vfx.TintEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class ImmolateAction extends AbstractGameAction
{

    public ImmolateAction(AbstractCreature source, int amount[], com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        setValues(null, source, amount[0]);
        damage = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        damageType = type;
        attackEffect = com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(AbstractDungeon.player.hand.size() == 0)
            {
                isDone = true;
                return;
            }
            if(AbstractDungeon.player.hand.size() == 1)
            {
                AbstractCard card = AbstractDungeon.player.hand.getBottomCard();
                if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE || card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS)
                    dealDamage();
                addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                isDone = true;
                return;
            } else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                tickDuration();
                return;
            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator.hasNext(); addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.handCardSelectScreen.selectedCards)))
            {
                c = (AbstractCard)iterator.next();
                if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE || c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS)
                    dealDamage();
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    public void dealDamage()
    {
        boolean playedMusic = false;
        int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        for(int i = 0; i < temp; i++)
        {
            if(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDying || ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isEscaping)
                continue;
            if(playedMusic)
            {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, attackEffect, true));
            } else
            {
                playedMusic = true;
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, attackEffect));
            }
        }

        AbstractPower p;
        for(Iterator iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext(); p.onDamageAllEnemies(damage))
            p = (AbstractPower)iterator.next();

        int temp2 = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        for(int i = 0; i < temp2; i++)
            if(!((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDying && !((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isEscaping)
            {
                ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.color = Color.RED.cpy();
                ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).damage(new DamageInfo(source, damage[i], damageType));
            }

        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            AbstractDungeon.actionManager.clearPostCombatActions();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public int damage[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ImmolateAction");
        TEXT = uiStrings.TEXT;
    }
}
