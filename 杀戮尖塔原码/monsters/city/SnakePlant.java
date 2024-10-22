// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SnakePlant.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import java.util.ArrayList;

public class SnakePlant extends AbstractMonster
{

    public SnakePlant(float x, float y)
    {
        super(NAME, "SnakePlant", 79, 0.0F, -44F, 350F, 360F, null, x, y + 50F);
        loadAnimation("images/monsters/theCity/snakePlant/skeleton.atlas", "images/monsters/theCity/snakePlant/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(78, 82);
        else
            setHp(75, 79);
        if(AbstractDungeon.ascensionLevel >= 2)
            rainBlowsDmg = 8;
        else
            rainBlowsDmg = 7;
        damage.add(new DamageInfo(this, rainBlowsDmg));
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MalleablePower(this)));
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
        }
        switch(byte0)
        {
        case 0: // '\0'
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

    public void takeTurn()
    {
        AbstractCreature p = AbstractDungeon.player;
        switch(nextMove)
        {
        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            int numBlows = 3;
            for(int i = 0; i < numBlows; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50F, 50F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50F, 50F) * Settings.scale, Color.CHARTREUSE.cpy()), 0.2F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(p, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE, true));
            }

            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            if(num < 65)
            {
                if(lastTwoMoves((byte)1))
                    setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
                else
                    setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 3, true);
            } else
            if(lastMove((byte)2) || lastMoveBefore((byte)2))
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 3, true);
            else
                setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
        } else
        if(num < 65)
        {
            if(lastTwoMoves((byte)1))
                setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            else
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 3, true);
        } else
        if(lastMove((byte)2))
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 3, true);
        else
            setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
    }

    public static final String ID = "SnakePlant";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 75;
    private static final int HP_MAX = 79;
    private static final int A_2_HP_MIN = 78;
    private static final int A_2_HP_MAX = 82;
    private static final byte CHOMPY_CHOMPS = 1;
    private static final byte SPORES = 2;
    private static final int CHOMPY_AMT = 3;
    private static final int CHOMPY_DMG = 7;
    private static final int A_2_CHOMPY_DMG = 8;
    private int rainBlowsDmg;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SnakePlant");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
