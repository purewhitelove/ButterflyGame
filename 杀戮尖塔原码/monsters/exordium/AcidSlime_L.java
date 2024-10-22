// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AcidSlime_L.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.monsters.exordium:
//            AcidSlime_M

public class AcidSlime_L extends AbstractMonster
{

    public AcidSlime_L(float x, float y)
    {
        this(x, y, 0, 69);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(68, 72);
        else
            setHp(65, 69);
    }

    public AcidSlime_L(float x, float y, int poisonAmount, int newHealth)
    {
        super(NAME, "AcidSlime_L", newHealth, 0.0F, 0.0F, 300F, 180F, null, x, y, true);
        saveX = x;
        saveY = y;
        splitTriggered = false;
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            damage.add(new DamageInfo(this, 12));
            damage.add(new DamageInfo(this, 18));
        } else
        {
            damage.add(new DamageInfo(this, 11));
            damage.add(new DamageInfo(this, 16));
        }
        powers.add(new SplitPower(this));
        if(poisonAmount >= 1)
            powers.add(new PoisonPower(this, this, poisonAmount));
        loadAnimation("images/monsters/theBottom/slimeL/skeleton.atlas", "images/monsters/theBottom/slimeL/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        state.addListener(new SlimeAnimListener());
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_SLIME_ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 2));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
            AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(this, 1.0F, 0.1F));
            AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this));
            AbstractDungeon.actionManager.addToBottom(new SuicideAction(this, false));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new AcidSlime_M(saveX - 134F, saveY + MathUtils.random(-4F, 4F), 0, currentHealth), false));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new AcidSlime_M(saveX + 134F, saveY + MathUtils.random(-4F, 4F), 0, currentHealth), false));
            AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
            setMove(SPLIT_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            break;
        }
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(!isDying && (float)currentHealth <= (float)maxHealth / 2.0F && nextMove != 3 && !splitTriggered)
        {
            setMove(SPLIT_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            createIntent();
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType.INTERRUPTED));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, SPLIT_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN));
            splitTriggered = true;
        }
    }

    protected void getMove(int num)
    {
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            if(num < 40)
            {
                if(lastTwoMoves((byte)1))
                {
                    if(AbstractDungeon.aiRng.randomBoolean(0.6F))
                        setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                    else
                        setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
                } else
                {
                    setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
                }
            } else
            if(num < 70)
            {
                if(lastTwoMoves((byte)2))
                {
                    if(AbstractDungeon.aiRng.randomBoolean(0.6F))
                        setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
                    else
                        setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
                } else
                {
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                }
            } else
            if(lastMove((byte)4))
            {
                if(AbstractDungeon.aiRng.randomBoolean(0.4F))
                    setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
                else
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            } else
            {
                setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
            }
        } else
        if(num < 30)
        {
            if(lastTwoMoves((byte)1))
            {
                if(AbstractDungeon.aiRng.randomBoolean())
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                else
                    setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
            } else
            {
                setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
            }
        } else
        if(num < 70)
        {
            if(lastMove((byte)2))
            {
                if(AbstractDungeon.aiRng.randomBoolean(0.4F))
                    setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
                else
                    setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
            } else
            {
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            }
        } else
        if(lastTwoMoves((byte)4))
        {
            if(AbstractDungeon.aiRng.randomBoolean(0.4F))
                setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
            else
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
        } else
        {
            setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
        }
    }

    public void die()
    {
        super.die();
        for(Iterator iterator = AbstractDungeon.actionManager.actions.iterator(); iterator.hasNext();)
        {
            AbstractGameAction a = (AbstractGameAction)iterator.next();
            if(a instanceof SpawnMonsterAction)
                return;
        }

        if(AbstractDungeon.getMonsters().areMonstersBasicallyDead() && (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss))
        {
            onBossVictoryLogic();
            UnlockTracker.hardUnlockOverride("SLIME");
            UnlockTracker.unlockAchievement("SLIME_BOSS");
        }
    }

    public static final String ID = "AcidSlime_L";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    private static final String WOUND_NAME;
    private static final String SPLIT_NAME;
    private static final String WEAK_NAME;
    public static final int HP_MIN = 65;
    public static final int HP_MAX = 69;
    public static final int A_2_HP_MIN = 68;
    public static final int A_2_HP_MAX = 72;
    public static final int W_TACKLE_DMG = 11;
    public static final int N_TACKLE_DMG = 16;
    public static final int A_2_W_TACKLE_DMG = 12;
    public static final int A_2_N_TACKLE_DMG = 18;
    public static final int WEAK_TURNS = 2;
    public static final int WOUND_COUNT = 2;
    private static final byte SLIME_TACKLE = 1;
    private static final byte NORMAL_TACKLE = 2;
    private static final byte SPLIT = 3;
    private static final byte WEAK_LICK = 4;
    private float saveX;
    private float saveY;
    private boolean splitTriggered;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("AcidSlime_L");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        WOUND_NAME = MOVES[0];
        SPLIT_NAME = MOVES[1];
        WEAK_NAME = MOVES[2];
    }
}
