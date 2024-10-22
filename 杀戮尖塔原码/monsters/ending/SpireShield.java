// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpireShield.java

package com.megacrit.cardcrawl.monsters.ending;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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

public class SpireShield extends AbstractMonster
{

    public SpireShield()
    {
        super(NAME, "SpireShield", 110, 0.0F, -20F, 380F, 290F, null, -1000F, 15F);
        moveCount = 0;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
        loadAnimation("images/monsters/theEnding/shield/skeleton.atlas", "images/monsters/theEnding/shield/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.1F);
        if(AbstractDungeon.ascensionLevel >= 8)
            setHp(125);
        else
            setHp(110);
        if(AbstractDungeon.ascensionLevel >= 3)
        {
            damage.add(new DamageInfo(this, 14));
            damage.add(new DamageInfo(this, 38));
        } else
        {
            damage.add(new DamageInfo(this, 12));
            damage.add(new DamageInfo(this, 34));
        }
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new SurroundedPower(AbstractDungeon.player)));
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
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.35F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            if(!AbstractDungeon.player.orbs.isEmpty() && AbstractDungeon.aiRng.randomBoolean())
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FocusPower(AbstractDungeon.player, -1), -1));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -1), -1));
            break;

        case 2: // '\002'
            AbstractMonster m;
            for(Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator(); iterator.hasNext(); AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, this, 30)))
                m = (AbstractMonster)iterator.next();

            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OLD_ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            if(AbstractDungeon.ascensionLevel >= 18)
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 99));
            else
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, ((DamageInfo)damage.get(1)).output));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        switch(moveCount % 3)
        {
        case 0: // '\0'
            if(AbstractDungeon.aiRng.randomBoolean())
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
            else
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
            break;

        case 1: // '\001'
            if(!lastMove((byte)1))
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
            else
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
            break;

        default:
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)damage.get(1)).base);
            break;
        }
        moveCount++;
    }

    public void changeState(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 334276480: 
            if(s.equals("OLD_ATTACK"))
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
            state.setAnimation(0, "old_attack", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;

        case 1: // '\001'
            state.setAnimation(0, "Attack", false);
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

    public static final String ID = "SpireShield";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private int moveCount;
    private static final byte BASH = 1;
    private static final byte FORTIFY = 2;
    private static final byte SMASH = 3;
    private static final int BASH_DEBUFF = -1;
    private static final int FORTIFY_BLOCK = 30;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpireShield");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
