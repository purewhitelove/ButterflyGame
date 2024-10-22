// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Healer.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import java.util.ArrayList;
import java.util.Iterator;

public class Healer extends AbstractMonster
{

    public Healer(float x, float y)
    {
        super(NAME, "Healer", 56, 0.0F, -20F, 230F, 250F, null, x, y);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(50, 58);
        else
            setHp(48, 56);
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            magicDmg = 9;
            strAmt = 4;
            healAmt = 20;
        } else
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            magicDmg = 9;
            strAmt = 3;
            healAmt = 16;
        } else
        {
            magicDmg = 8;
            strAmt = 2;
            healAmt = 16;
        }
        damage.add(new DamageInfo(this, magicDmg));
        loadAnimation("images/monsters/theCity/healer/skeleton.atlas", "images/monsters/theCity/healer/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        stateData.setMix("Hit", "Idle", 0.2F);
        e.setTime(e.getEndTime() * MathUtils.random());
        state.setTimeScale(0.8F);
    }

    public void takeTurn()
    {
label0:
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
            break;

        case 2: // '\002'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAFF_RAISE"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
            Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                AbstractMonster m;
                do
                {
                    if(!iterator.hasNext())
                        break label0;
                    m = (AbstractMonster)iterator.next();
                } while(m.isDying || m.isEscaping);
                AbstractDungeon.actionManager.addToBottom(new HealAction(m, this, healAmt));
            } while(true);

        case 3: // '\003'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAFF_RAISE"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
            Iterator iterator1 = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                AbstractMonster m;
                do
                {
                    if(!iterator1.hasNext())
                        break label0;
                    m = (AbstractMonster)iterator1.next();
                } while(m.isDying || m.isEscaping);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, strAmt), strAmt));
            } while(true);
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx()
    {
        if(MathUtils.randomBoolean())
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_HEALER_1A"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_HEALER_1B"));
    }

    private void playDeathSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_HEALER_2A");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_HEALER_2B");
        else
            CardCrawlGame.sound.play("VO_HEALER_2C");
    }

    public void changeState(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -1729868403: 
            if(s.equals("STAFF_RAISE"))
                byte0 = 0;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            state.setAnimation(0, "Attack", false);
            state.setTimeScale(0.8F);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;
        }
    }

    protected void getMove(int num)
    {
        int needToHeal = 0;
        Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDying && !m.isEscaping)
                needToHeal += m.maxHealth - m.currentHealth;
        } while(true);
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            if(needToHeal > 20 && !lastTwoMoves((byte)2))
            {
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
                return;
            }
        } else
        if(needToHeal > 15 && !lastTwoMoves((byte)2))
        {
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            return;
        }
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            if(num >= 40 && !lastMove((byte)1))
            {
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
                return;
            }
        } else
        if(num >= 40 && !lastTwoMoves((byte)1))
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
            return;
        }
        if(!lastTwoMoves((byte)3))
        {
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            return;
        } else
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
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
        playDeathSfx();
        state.setTimeScale(0.1F);
        useShakeAnimation(5F);
        super.die();
    }

    public static final String ID = "Healer";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final float IDLE_TIMESCALE = 0.8F;
    public static final String ENC_NAME = "HealerTank";
    private static final int HP_MIN = 48;
    private static final int HP_MAX = 56;
    private static final int A_2_HP_MIN = 50;
    private static final int A_2_HP_MAX = 58;
    private static final int MAGIC_DMG = 8;
    private static final int HEAL_AMT = 16;
    private static final int STR_AMOUNT = 2;
    private static final int A_2_MAGIC_DMG = 9;
    private static final int A_2_STR_AMOUNT = 3;
    private int magicDmg;
    private int strAmt;
    private int healAmt;
    private static final byte ATTACK = 1;
    private static final byte HEAL = 2;
    private static final byte BUFF = 3;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Healer");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
