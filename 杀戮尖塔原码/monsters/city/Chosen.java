// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Chosen.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import java.util.ArrayList;

public class Chosen extends AbstractMonster
{

    public Chosen()
    {
        this(0.0F, 0.0F);
    }

    public Chosen(float x, float y)
    {
        super(NAME, "Chosen", 99, 5F, -10F, 200F, 280F, null, x, -20F + y);
        firstTurn = true;
        usedHex = false;
        dialogX = -30F * Settings.scale;
        dialogY = 50F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(98, 103);
        else
            setHp(95, 99);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            zapDmg = 21;
            debilitateDmg = 12;
            pokeDmg = 6;
        } else
        {
            zapDmg = 18;
            debilitateDmg = 10;
            pokeDmg = 5;
        }
        damage.add(new DamageInfo(this, zapDmg));
        damage.add(new DamageInfo(this, debilitateDmg));
        damage.add(new DamageInfo(this, pokeDmg));
        loadAnimation("images/monsters/theCity/chosen/skeleton.atlas", "images/monsters/theCity/chosen/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.2F);
        stateData.setMix("Attack", "Idle", 0.2F);
        state.setTimeScale(0.8F);
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 5: // '\005'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(2), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(2), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new FastShakeAction(this, 0.3F, 0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 3, true), 3));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0]));
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new HexPower(AbstractDungeon.player, 1)));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
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
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            if(!usedHex)
            {
                usedHex = true;
                setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
                return;
            }
            if(!lastMove((byte)3) && !lastMove((byte)2))
                if(num < 50)
                {
                    setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                    return;
                } else
                {
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
                    return;
                }
            if(num < 40)
            {
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
                return;
            } else
            {
                setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(2)).base, 2, true);
                return;
            }
        }
        if(firstTurn)
        {
            firstTurn = false;
            setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(2)).base, 2, true);
            return;
        }
        if(!usedHex)
        {
            usedHex = true;
            setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            return;
        }
        if(!lastMove((byte)3) && !lastMove((byte)2))
            if(num < 50)
            {
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                return;
            } else
            {
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
                return;
            }
        if(num < 40)
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        } else
        {
            setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(2)).base, 2, true);
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
        super.die();
        CardCrawlGame.sound.play("CHOSEN_DEATH");
    }

    public static final String ID = "Chosen";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 95;
    private static final int HP_MAX = 99;
    private static final int A_2_HP_MIN = 98;
    private static final int A_2_HP_MAX = 103;
    private static final float HB_X = 5F;
    private static final float HB_Y = -10F;
    private static final float HB_W = 200F;
    private static final float HB_H = 280F;
    private static final int ZAP_DMG = 18;
    private static final int A_2_ZAP_DMG = 21;
    private static final int DEBILITATE_DMG = 10;
    private static final int A_2_DEBILITATE_DMG = 12;
    private static final int POKE_DMG = 5;
    private static final int A_2_POKE_DMG = 6;
    private int zapDmg;
    private int debilitateDmg;
    private int pokeDmg;
    private static final int DEBILITATE_VULN = 2;
    private static final int DRAIN_STR = 3;
    private static final int DRAIN_WEAK = 3;
    private static final byte ZAP = 1;
    private static final byte DRAIN = 2;
    private static final byte DEBILITATE = 3;
    private static final byte HEX = 4;
    private static final byte POKE = 5;
    private static final int HEX_AMT = 1;
    private boolean firstTurn;
    private boolean usedHex;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Chosen");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
