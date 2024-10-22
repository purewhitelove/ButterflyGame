// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpikeSlime_S.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import java.util.ArrayList;

public class SpikeSlime_S extends AbstractMonster
{

    public SpikeSlime_S(float x, float y, int poisonAmount)
    {
        super(NAME, "SpikeSlime_S", 14, 0.0F, -24F, 130F, 100F, null, x, y);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(11, 15);
        else
            setHp(10, 14);
        if(AbstractDungeon.ascensionLevel >= 2)
            damage.add(new DamageInfo(this, 6));
        else
            damage.add(new DamageInfo(this, 5));
        if(poisonAmount >= 1)
            powers.add(new PoisonPower(this, this, poisonAmount));
        loadAnimation("images/monsters/theBottom/slimeAltS/skeleton.atlas", "images/monsters/theBottom/slimeAltS/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        state.addListener(new SlimeAnimListener());
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;
        }
    }

    protected void getMove(int num)
    {
        setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
    }

    public static final String ID = "SpikeSlime_S";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP_MIN = 10;
    public static final int HP_MAX = 14;
    public static final int A_2_HP_MIN = 11;
    public static final int A_2_HP_MAX = 15;
    public static final int TACKLE_DAMAGE = 5;
    public static final int A_2_TACKLE_DAMAGE = 6;
    private static final byte TACKLE = 1;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpikeSlime_S");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
