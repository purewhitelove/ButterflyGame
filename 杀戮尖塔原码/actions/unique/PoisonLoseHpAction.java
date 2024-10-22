// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PoisonLoseHpAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.TintEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class PoisonLoseHpAction extends AbstractGameAction
{

    public PoisonLoseHpAction(AbstractCreature target, AbstractCreature source, int amount, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = effect;
        duration = 0.33F;
    }

    public void update()
    {
        if(AbstractDungeon.getCurrRoom().phase != com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            isDone = true;
            return;
        }
        if(duration == 0.33F && target.currentHealth > 0)
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
        tickDuration();
        if(isDone)
        {
            if(target.currentHealth > 0)
            {
                target.tint.color = Color.CHARTREUSE.cpy();
                target.tint.changeColor(Color.WHITE.cpy());
                target.damage(new DamageInfo(source, amount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
                if(target.isDying)
                {
                    AbstractPlayer.poisonKillCount++;
                    if(AbstractPlayer.poisonKillCount == 3 && AbstractDungeon.player.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT)
                        UnlockTracker.unlockAchievement("PLAGUE");
                }
            }
            AbstractPower p = target.getPower("Poison");
            if(p != null)
            {
                p.amount--;
                if(p.amount == 0)
                    target.powers.remove(p);
                else
                    p.updateDescription();
            }
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            addToTop(new WaitAction(0.1F));
        }
    }

    private static final float DURATION = 0.33F;
}
