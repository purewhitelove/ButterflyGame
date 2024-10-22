// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpireSpear.java

package com.megacrit.cardcrawl.monsters.ending;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

public class SpireSpear extends AbstractMonster
{

    public SpireSpear()
    {
        super(NAME, "SpireSpear", 160, 0.0F, -15F, 380F, 290F, null, 70F, 10F);
        moveCount = 0;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
        loadAnimation("images/monsters/theEnding/spear/skeleton.atlas", "images/monsters/theEnding/spear/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.7F);
        if(AbstractDungeon.ascensionLevel >= 8)
            setHp(180);
        else
            setHp(160);
        if(AbstractDungeon.ascensionLevel >= 3)
        {
            skewerCount = 4;
            damage.add(new DamageInfo(this, 6));
            damage.add(new DamageInfo(this, 10));
        } else
        {
            skewerCount = 3;
            damage.add(new DamageInfo(this, 5));
            damage.add(new DamageInfo(this, 10));
        }
    }

    public void usePreBattleAction()
    {
        if(AbstractDungeon.ascensionLevel >= 18)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2)));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            for(int i = 0; i < 2; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.15F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            }

            if(AbstractDungeon.ascensionLevel >= 18)
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Burn(), 2, false, true));
            else
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Burn(), 2));
            break;

        case 2: // '\002'
            AbstractMonster m;
            for(Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator(); iterator.hasNext(); AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 2), 2)))
                m = (AbstractMonster)iterator.next();

            break;

        case 3: // '\003'
            for(int i = 0; i < skewerCount; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.05F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
            }

            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        switch(moveCount % 3)
        {
        case 0: // '\0'
            if(!lastMove((byte)1))
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base, 2, true);
            else
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            break;

        case 1: // '\001'
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base, skewerCount, true);
            break;

        default:
            if(AbstractDungeon.aiRng.randomBoolean())
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            else
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base, 2, true);
            break;
        }
        moveCount++;
    }

    public void changeState(String key)
    {
        com.esotericsoftware.spine.AnimationState.TrackEntry e = null;
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -273028538: 
            if(s.equals("SLOW_ATTACK"))
                byte0 = 0;
            break;

        case 1941037640: 
            if(s.equals("ATTACK"))
                byte0 = 1;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            state.setAnimation(0, "Attack_1", false);
            e = state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.5F);
            break;

        case 1: // '\001'
            state.setAnimation(0, "Attack_2", false);
            e = state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.7F);
            break;
        }
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            com.esotericsoftware.spine.AnimationState.TrackEntry e = state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.7F);
        }
    }

    public void die()
    {
        super.die();
        Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDead && !m.isDying)
            {
                if(AbstractDungeon.player.hasPower("Surrounded"))
                {
                    AbstractDungeon.player.flipHorizontal = m.drawX < AbstractDungeon.player.drawX;
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Surrounded"));
                }
                if(m.hasPower("BackAttack"))
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, "BackAttack"));
            }
        } while(true);
    }

    public static final String ID = "SpireSpear";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private int moveCount;
    private static final byte BURN_STRIKE = 1;
    private static final byte PIERCER = 2;
    private static final byte SKEWER = 3;
    private static final int BURN_STRIKE_COUNT = 2;
    private int skewerCount;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpireSpear");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
