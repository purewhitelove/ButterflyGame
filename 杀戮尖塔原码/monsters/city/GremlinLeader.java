// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GremlinLeader.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.SummonGremlinAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

public class GremlinLeader extends AbstractMonster
{

    public GremlinLeader()
    {
        super(NAME, "GremlinLeader", 148, 0.0F, -15F, 200F, 310F, null, 35F, 0.0F);
        gremlins = new AbstractMonster[3];
        STAB_DMG = 6;
        STAB_AMT = 3;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
        loadAnimation("images/monsters/theCity/gremlinleader/skeleton.atlas", "images/monsters/theCity/gremlinleader/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        if(AbstractDungeon.ascensionLevel >= 8)
            setHp(145, 155);
        else
            setHp(140, 148);
        if(AbstractDungeon.ascensionLevel >= 18)
        {
            strAmt = 5;
            blockAmt = 10;
        } else
        if(AbstractDungeon.ascensionLevel >= 3)
        {
            strAmt = 4;
            blockAmt = 6;
        } else
        {
            strAmt = 3;
            blockAmt = 6;
        }
        dialogX = -80F * Settings.scale;
        dialogY = 50F * Settings.scale;
        damage.add(new DamageInfo(this, STAB_DMG));
    }

    public void usePreBattleAction()
    {
        gremlins[0] = (AbstractMonster)AbstractDungeon.getMonsters().monsters.get(0);
        gremlins[1] = (AbstractMonster)AbstractDungeon.getMonsters().monsters.get(1);
        gremlins[2] = null;
        AbstractMonster aabstractmonster[] = gremlins;
        int i = aabstractmonster.length;
        for(int j = 0; j < i; j++)
        {
            AbstractMonster m = aabstractmonster[j];
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MinionPower(this)));
        }

    }

    public void takeTurn()
    {
label0:
        switch(nextMove)
        {
        default:
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "CALL"));
            AbstractDungeon.actionManager.addToBottom(new SummonGremlinAction(gremlins));
            AbstractDungeon.actionManager.addToBottom(new SummonGremlinAction(gremlins));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, getEncourageQuote()));
            Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break label0;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(m == this)
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, strAmt), strAmt));
                else
                if(!m.isDying)
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, strAmt), strAmt));
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, this, blockAmt));
                }
            } while(true);

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private String getEncourageQuote()
    {
        ArrayList list = new ArrayList();
        list.add(DIALOG[0]);
        list.add(DIALOG[1]);
        list.add(DIALOG[2]);
        return (String)list.get(AbstractDungeon.aiRng.random(0, list.size() - 1));
    }

    protected void getMove(int num)
    {
        if(numAliveGremlins() == 0)
        {
            if(num < 75)
            {
                if(!lastMove((byte)2))
                    setMove(RALLY_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
                else
                    setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, STAB_DMG, STAB_AMT, true);
            } else
            if(!lastMove((byte)4))
                setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, STAB_DMG, STAB_AMT, true);
            else
                setMove(RALLY_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
        } else
        if(numAliveGremlins() < 2)
        {
            if(num < 50)
            {
                if(!lastMove((byte)2))
                    setMove(RALLY_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
                else
                    getMove(AbstractDungeon.aiRng.random(50, 99));
            } else
            if(num < 80)
            {
                if(!lastMove((byte)3))
                    setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
                else
                    setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, STAB_DMG, STAB_AMT, true);
            } else
            if(!lastMove((byte)4))
                setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, STAB_DMG, STAB_AMT, true);
            else
                getMove(AbstractDungeon.aiRng.random(0, 80));
        } else
        if(numAliveGremlins() > 1)
            if(num < 66)
            {
                if(!lastMove((byte)3))
                    setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
                else
                    setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, STAB_DMG, STAB_AMT, true);
            } else
            if(!lastMove((byte)4))
                setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, STAB_DMG, STAB_AMT, true);
            else
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
    }

    private int numAliveGremlins()
    {
        int count = 0;
        Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(m != null && m != this && !m.isDying)
                count++;
        } while(true);
        return count;
    }

    public void changeState(String stateName)
    {
        String s = stateName;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 1941037640: 
            if(s.equals("ATTACK"))
                byte0 = 0;
            break;

        case 2060894: 
            if(s.equals("CALL"))
                byte0 = 1;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            state.setAnimation(0, "Attack", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;

        case 1: // '\001'
            state.setAnimation(0, "Call", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;
        }
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    public void die()
    {
        super.die();
        boolean first = true;
        Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDying)
                if(first)
                {
                    AbstractDungeon.actionManager.addToBottom(new ShoutAction(m, DIALOG[3], 0.5F, 1.2F));
                    first = false;
                } else
                {
                    AbstractDungeon.actionManager.addToBottom(new ShoutAction(m, DIALOG[4], 0.5F, 1.2F));
                }
        } while(true);
        iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDying)
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(m));
        } while(true);
    }

    public static final String ID = "GremlinLeader";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 140;
    private static final int HP_MAX = 148;
    private static final int A_2_HP_MIN = 145;
    private static final int A_2_HP_MAX = 155;
    public static final String ENC_NAME = "Gremlin Leader Combat";
    public AbstractMonster gremlins[];
    public static final float POSX[] = {
        -366F, -170F, -532F
    };
    public static final float POSY[] = {
        -4F, 6F, 0.0F
    };
    private static final byte RALLY = 2;
    private static final String RALLY_NAME;
    private static final byte ENCOURAGE = 3;
    private static final int STR_AMT = 3;
    private static final int BLOCK_AMT = 6;
    private static final int A_2_STR_AMT = 4;
    private static final int A_18_STR_AMT = 5;
    private static final int A_18_BLOCK_AMT = 10;
    private int strAmt;
    private int blockAmt;
    private static final byte STAB = 4;
    private int STAB_DMG;
    private int STAB_AMT;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinLeader");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        RALLY_NAME = MOVES[0];
    }
}
