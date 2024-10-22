// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BookOfStabbing.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PainfulStabsPower;
import java.util.ArrayList;

public class BookOfStabbing extends AbstractMonster
{

    public BookOfStabbing()
    {
        super(NAME, "BookOfStabbing", 164, 0.0F, -10F, 320F, 420F, null, 0.0F, 5F);
        stabCount = 1;
        loadAnimation("images/monsters/theCity/stabBook/skeleton.atlas", "images/monsters/theCity/stabBook/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.2F);
        e.setTimeScale(0.8F);
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
        dialogX = -70F * Settings.scale;
        dialogY = 50F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 8)
            setHp(168, 172);
        else
            setHp(160, 164);
        if(AbstractDungeon.ascensionLevel >= 3)
        {
            stabDmg = 7;
            bigStabDmg = 24;
        } else
        {
            stabDmg = 6;
            bigStabDmg = 21;
        }
        damage.add(new DamageInfo(this, stabDmg));
        damage.add(new DamageInfo(this, bigStabDmg));
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PainfulStabsPower(this)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            for(int i = 0; i < stabCount; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new SFXAction((new StringBuilder()).append("MONSTER_BOOK_STAB_").append(MathUtils.random(0, 3)).toString()));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_VERTICAL, false, true));
            }

            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_2"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
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

        case 1321368571: 
            if(s.equals("ATTACK_2"))
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
            state.setAnimation(0, "Attack_2", false);
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

    protected void getMove(int num)
    {
        if(num < 15)
        {
            if(lastMove((byte)2))
            {
                stabCount++;
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, stabCount, true);
            } else
            {
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                if(AbstractDungeon.ascensionLevel >= 18)
                    stabCount++;
            }
        } else
        if(lastTwoMoves((byte)1))
        {
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            if(AbstractDungeon.ascensionLevel >= 18)
                stabCount++;
        } else
        {
            stabCount++;
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, stabCount, true);
        }
    }

    public void die()
    {
        super.die();
        CardCrawlGame.sound.play("STAB_BOOK_DEATH");
    }

    public static final String ID = "BookOfStabbing";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 160;
    private static final int HP_MAX = 164;
    private static final int A_2_HP_MIN = 168;
    private static final int A_2_HP_MAX = 172;
    private static final int STAB_DAMAGE = 6;
    private static final int BIG_STAB_DAMAGE = 21;
    private static final int A_2_STAB_DAMAGE = 7;
    private static final int A_2_BIG_STAB_DAMAGE = 24;
    private int stabDmg;
    private int bigStabDmg;
    private static final byte STAB = 1;
    private static final byte BIG_STAB = 2;
    private int stabCount;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BookOfStabbing");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
