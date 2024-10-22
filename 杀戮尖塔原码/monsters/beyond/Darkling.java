// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Darkling.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Darkling extends AbstractMonster
{

    public Darkling(float x, float y)
    {
        super(NAME, "Darkling", 56, 0.0F, -20F, 260F, 200F, null, x, y + 20F);
        firstMove = true;
        loadAnimation("images/monsters/theForest/darkling/skeleton.atlas", "images/monsters/theForest/darkling/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(MathUtils.random(0.75F, 1.0F));
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(50, 59);
        else
            setHp(48, 56);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            chompDmg = 9;
            nipDmg = AbstractDungeon.monsterHpRng.random(9, 13);
        } else
        {
            chompDmg = 8;
            nipDmg = AbstractDungeon.monsterHpRng.random(7, 11);
        }
        dialogX = -50F * Settings.scale;
        damage.add(new DamageInfo(this, chompDmg));
        damage.add(new DamageInfo(this, nipDmg));
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.getCurrRoom().cannotLose = true;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegrowPower(this)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 12));
            if(AbstractDungeon.ascensionLevel >= 17)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, DIALOG[0]));
            break;

        case 5: // '\005'
            if(MathUtils.randomBoolean())
                AbstractDungeon.actionManager.addToBottom(new SFXAction("DARKLING_REGROW_2", MathUtils.random(-0.1F, 0.1F)));
            else
                AbstractDungeon.actionManager.addToBottom(new SFXAction("DARKLING_REGROW_1", MathUtils.random(-0.1F, 0.1F)));
            AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, maxHealth / 2));
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REVIVE"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegrowPower(this), 1));
            AbstractRelic r;
            for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onSpawnMonster(this))
                r = (AbstractRelic)iterator.next();

            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(halfDead)
        {
            setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            return;
        }
        if(firstMove)
        {
            if(num < 50)
            {
                if(AbstractDungeon.ascensionLevel >= 17)
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
                else
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
            } else
            {
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            }
            firstMove = false;
            return;
        }
        if(num < 40)
        {
            if(!lastMove((byte)1) && AbstractDungeon.getMonsters().monsters.lastIndexOf(this) % 2 == 0)
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 2, true);
            else
                getMove(AbstractDungeon.aiRng.random(40, 99));
        } else
        if(num < 70)
        {
            if(!lastMove((byte)2))
            {
                if(AbstractDungeon.ascensionLevel >= 17)
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
                else
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
            } else
            {
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            }
        } else
        if(!lastTwoMoves((byte)3))
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
        else
            getMove(AbstractDungeon.aiRng.random(0, 99));
    }

    public void changeState(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 1941037640: 
            if(s.equals("ATTACK"))
                byte0 = 0;
            break;

        case -1881019051: 
            if(s.equals("REVIVE"))
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
            halfDead = false;
            break;
        }
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(currentHealth <= 0 && !halfDead)
        {
            halfDead = true;
            AbstractPower p;
            for(Iterator iterator = powers.iterator(); iterator.hasNext(); p.onDeath())
                p = (AbstractPower)iterator.next();

            AbstractRelic r;
            for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onMonsterDeath(this))
                r = (AbstractRelic)iterator1.next();

            powers.clear();
            logger.info("This monster is now half dead.");
            boolean allDead = true;
            Iterator iterator2 = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator2.next();
                if(m.id.equals("Darkling") && !m.halfDead)
                    allDead = false;
            } while(true);
            logger.info((new StringBuilder()).append("All dead: ").append(allDead).toString());
            if(!allDead)
            {
                if(nextMove != 4)
                {
                    setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
                    createIntent();
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN));
                }
            } else
            {
                AbstractDungeon.getCurrRoom().cannotLose = false;
                halfDead = false;
                AbstractMonster m;
                for(Iterator iterator3 = AbstractDungeon.getMonsters().monsters.iterator(); iterator3.hasNext(); m.die())
                    m = (AbstractMonster)iterator3.next();

            }
        } else
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    public void die()
    {
        if(!AbstractDungeon.getCurrRoom().cannotLose)
            super.die();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/monsters/beyond/Darkling.getName());
    public static final String ID = "Darkling";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP_MIN = 48;
    public static final int HP_MAX = 56;
    public static final int A_2_HP_MIN = 50;
    public static final int A_2_HP_MAX = 59;
    private static final float HB_X = 0F;
    private static final float HB_Y = -20F;
    private static final float HB_W = 260F;
    private static final float HB_H = 200F;
    private static final int BITE_DMG = 8;
    private static final int A_2_BITE_DMG = 9;
    private int chompDmg;
    private int nipDmg;
    private static final int BLOCK_AMT = 12;
    private static final int CHOMP_AMT = 2;
    private static final byte CHOMP = 1;
    private static final byte HARDEN = 2;
    private static final byte NIP = 3;
    private static final byte COUNT = 4;
    private static final byte REINCARNATE = 5;
    private boolean firstMove;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Darkling");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
