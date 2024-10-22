// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Centurion.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.GainBlockRandomMonsterAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import java.util.ArrayList;
import java.util.Iterator;

public class Centurion extends AbstractMonster
{

    public Centurion(float x, float y)
    {
        super(NAME, "Centurion", 80, -14F, -20F, 250F, 330F, null, x, y);
        BLOCK_AMOUNT = 15;
        A_17_BLOCK_AMOUNT = 20;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(78, 83);
        else
            setHp(76, 80);
        if(AbstractDungeon.ascensionLevel >= 17)
            blockAmount = A_17_BLOCK_AMOUNT;
        else
            blockAmount = BLOCK_AMOUNT;
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            slashDmg = 14;
            furyDmg = 7;
            furyHits = 3;
        } else
        {
            slashDmg = 12;
            furyDmg = 6;
            furyHits = 3;
        }
        damage.add(new DamageInfo(this, slashDmg));
        damage.add(new DamageInfo(this, furyDmg));
        loadAnimation("images/monsters/theCity/tank/skeleton.atlas", "images/monsters/theCity/tank/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.2F);
        state.setTimeScale(0.8F);
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "MACE_HIT"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
            AbstractDungeon.actionManager.addToBottom(new GainBlockRandomMonsterAction(this, blockAmount));
            break;

        case 3: // '\003'
            for(int i = 0; i < furyHits; i++)
            {
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "MACE_HIT"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }

            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx()
    {
        int roll = MathUtils.random(1);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_TANK_1A"));
        else
        if(roll == 1)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_TANK_1B"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_TANK_1C"));
    }

    public void changeState(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 595384490: 
            if(s.equals("MACE_HIT"))
                byte0 = 0;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            state.setAnimation(0, "Attack", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;
        }
    }

    protected void getMove(int num)
    {
        int aliveCount;
        if(num >= 65 && !lastTwoMoves((byte)2) && !lastTwoMoves((byte)3))
        {
            aliveCount = 0;
            Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(!m.isDying && !m.isEscaping)
                    aliveCount++;
            } while(true);
            if(aliveCount > 1)
            {
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
                return;
            } else
            {
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base, furyHits, true);
                return;
            }
        }
        if(!lastTwoMoves((byte)1))
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
        aliveCount = 0;
        Iterator iterator1 = AbstractDungeon.getMonsters().monsters.iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator1.next();
            if(!m.isDying && !m.isEscaping)
                aliveCount++;
        } while(true);
        if(aliveCount > 1)
        {
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
            return;
        } else
        {
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base, furyHits, true);
            return;
        }
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.setTimeScale(0.8F);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    public void die()
    {
        state.setTimeScale(0.1F);
        useShakeAnimation(5F);
        super.die();
    }

    public static final String ID = "Centurion";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 76;
    private static final int HP_MAX = 80;
    private static final int A_2_HP_MIN = 78;
    private static final int A_2_HP_MAX = 83;
    private static final int SLASH_DMG = 12;
    private static final int FURY_DMG = 6;
    private static final int FURY_HITS = 3;
    private static final int A_2_SLASH_DMG = 14;
    private static final int A_2_FURY_DMG = 7;
    private int slashDmg;
    private int furyDmg;
    private int furyHits;
    private int blockAmount;
    private int BLOCK_AMOUNT;
    private int A_17_BLOCK_AMOUNT;
    private static final byte SLASH = 1;
    private static final byte PROTECT = 2;
    private static final byte FURY = 3;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Centurion");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
