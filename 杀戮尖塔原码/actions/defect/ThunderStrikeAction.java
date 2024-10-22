// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThunderStrikeAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import java.util.ArrayList;

public class ThunderStrikeAction extends AbstractGameAction
{

    public ThunderStrikeAction(AbstractCreature target, DamageInfo info, int numTimes)
    {
        this.info = info;
        this.target = target;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE;
        duration = 0.01F;
        this.numTimes = numTimes;
    }

    public void update()
    {
        if(target == null)
        {
            isDone = true;
            return;
        }
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
        {
            AbstractDungeon.actionManager.clearPostCombatActions();
            isDone = true;
            return;
        }
        if(target.currentHealth > 0)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
            AbstractDungeon.effectList.add(new LightningEffect(target.drawX, target.drawY));
            CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1F);
            info.applyPowers(info.owner, target);
            target.damage(info);
            if(numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            {
                numTimes--;
                addToTop(new ThunderStrikeAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info, numTimes));
            }
            addToTop(new WaitAction(0.2F));
        }
        isDone = true;
    }

    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int numTimes;
}
