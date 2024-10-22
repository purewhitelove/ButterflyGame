// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LessonLearnedAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class LessonLearnedAction extends AbstractGameAction
{

    public LessonLearnedAction(AbstractCreature target, DamageInfo info)
    {
        theCard = null;
        this.info = info;
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        duration = Settings.ACTION_DUR_MED;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_MED && target != null)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            target.damage(info);
            if((((AbstractMonster)target).isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion"))
            {
                ArrayList possibleCards = new ArrayList();
                Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    AbstractCard c = (AbstractCard)iterator.next();
                    if(c.canUpgrade())
                        possibleCards.add(c);
                } while(true);
                if(!possibleCards.isEmpty())
                {
                    theCard = (AbstractCard)possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
                    theCard.upgrade();
                    AbstractDungeon.player.bottledCardUpgradeCheck(theCard);
                }
            }
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
        if(isDone && theCard != null)
        {
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(theCard.makeStatEquivalentCopy()));
            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }
    }

    private DamageInfo info;
    private AbstractCard theCard;
}
