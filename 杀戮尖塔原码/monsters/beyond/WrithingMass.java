// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WrithingMass.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;

public class WrithingMass extends AbstractMonster
{

    public WrithingMass()
    {
        super(NAME, "WrithingMass", 160, 5F, -26F, 450F, 310F, null, 0.0F, 15F);
        firstMove = true;
        usedMegaDebuff = false;
        loadAnimation("images/monsters/theForest/spaghetti/skeleton.atlas", "images/monsters/theForest/spaghetti/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.1F);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(175);
        else
            setHp(160);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            damage.add(new DamageInfo(this, 38));
            damage.add(new DamageInfo(this, 9));
            damage.add(new DamageInfo(this, 16));
            damage.add(new DamageInfo(this, 12));
            normalDebuffAmt = 2;
        } else
        {
            damage.add(new DamageInfo(this, 32));
            damage.add(new DamageInfo(this, 7));
            damage.add(new DamageInfo(this, 15));
            damage.add(new DamageInfo(this, 10));
            normalDebuffAmt = 2;
        }
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ReactivePower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MalleablePower(this)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 0: // '\0'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            for(int i = 0; i < 3; i++)
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));

            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(2), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, ((DamageInfo)damage.get(2)).base));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(3), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, normalDebuffAmt, true), normalDebuffAmt));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, normalDebuffAmt, true), normalDebuffAmt));
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
            break;

        case 4: // '\004'
            usedMegaDebuff = true;
            AbstractDungeon.actionManager.addToBottom(new FastShakeAction(this, 0.5F, 0.2F));
            AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(CardLibrary.getCard("Parasite").makeCopy()));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void damage(DamageInfo info)
    {
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
        super.damage(info);
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
        if(firstMove)
        {
            firstMove = false;
            if(num < 33)
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base, 3, true);
            else
            if(num < 66)
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)damage.get(2)).base);
            else
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(3)).base);
            return;
        }
        if(num < 10)
        {
            if(!lastMove((byte)0))
                setMove((byte)0, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            else
                getMove(AbstractDungeon.aiRng.random(10, 99));
        } else
        if(num < 20)
        {
            if(!usedMegaDebuff && !lastMove((byte)4))
                setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            else
            if(AbstractDungeon.aiRng.randomBoolean(0.1F))
                setMove((byte)0, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            else
                getMove(AbstractDungeon.aiRng.random(20, 99));
        } else
        if(num < 40)
        {
            if(!lastMove((byte)3))
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(3)).base);
            else
            if(AbstractDungeon.aiRng.randomBoolean(0.4F))
                getMove(AbstractDungeon.aiRng.random(19));
            else
                getMove(AbstractDungeon.aiRng.random(40, 99));
        } else
        if(num < 70)
        {
            if(!lastMove((byte)1))
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base, 3, true);
            else
            if(AbstractDungeon.aiRng.randomBoolean(0.3F))
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)damage.get(2)).base);
            else
                getMove(AbstractDungeon.aiRng.random(39));
        } else
        if(!lastMove((byte)2))
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)damage.get(2)).base);
        else
            getMove(AbstractDungeon.aiRng.random(69));
        createIntent();
    }

    public static final String ID = "WrithingMass";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    private static final int HP = 160;
    private static final int A_2_HP = 175;
    private boolean firstMove;
    private boolean usedMegaDebuff;
    private static final int HIT_COUNT = 3;
    private int normalDebuffAmt;
    private static final byte BIG_HIT = 0;
    private static final byte MULTI_HIT = 1;
    private static final byte ATTACK_BLOCK = 2;
    private static final byte ATTACK_DEBUFF = 3;
    private static final byte MEGA_DEBUFF = 4;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("WrithingMass");
        NAME = monsterStrings.NAME;
    }
}
