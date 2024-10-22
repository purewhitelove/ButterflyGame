// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BronzeAutomaton.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.monsters.city:
//            BronzeOrb

public class BronzeAutomaton extends AbstractMonster
{

    public BronzeAutomaton()
    {
        super(NAME, "BronzeAutomaton", 300, 0.0F, -30F, 270F, 400F, null, -50F, 20F);
        numTurns = 0;
        firstTurn = true;
        loadAnimation("images/monsters/theCity/automaton/skeleton.atlas", "images/monsters/theCity/automaton/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
        dialogX = -100F * Settings.scale;
        dialogY = 10F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 9)
        {
            setHp(320);
            blockAmt = 12;
        } else
        {
            setHp(300);
            blockAmt = 9;
        }
        if(AbstractDungeon.ascensionLevel >= 4)
        {
            flailDmg = 8;
            beamDmg = 50;
            strAmt = 4;
        } else
        {
            flailDmg = 7;
            beamDmg = 45;
            strAmt = 3;
        }
        damage.add(new DamageInfo(this, flailDmg));
        damage.add(new DamageInfo(this, beamDmg));
    }

    public void usePreBattleAction()
    {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 3)));
        UnlockTracker.markBossAsSeen("AUTOMATON");
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 4: // '\004'
            if(MathUtils.randomBoolean())
                AbstractDungeon.actionManager.addToBottom(new SFXAction("AUTOMATON_ORB_SPAWN", MathUtils.random(-0.1F, 0.1F)));
            else
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_AUTOMATON_SUMMON", MathUtils.random(-0.1F, 0.1F)));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrb(-300F, 200F, 0), true));
            if(MathUtils.randomBoolean())
                AbstractDungeon.actionManager.addToBottom(new SFXAction("AUTOMATON_ORB_SPAWN", MathUtils.random(-0.1F, 0.1F)));
            else
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_AUTOMATON_SUMMON", MathUtils.random(-0.1F, 0.1F)));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrb(200F, 130F, 1), true));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            break;

        case 5: // '\005'
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blockAmt));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, strAmt), strAmt));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new LaserBeamEffect(hb.cX, hb.cY + 60F * Settings.scale), 1.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType.STUNNED));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(firstTurn)
        {
            setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            firstTurn = false;
            return;
        }
        if(numTurns == 4)
        {
            setMove(BEAM_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            numTurns = 0;
            return;
        }
        if(lastMove((byte)2))
            if(AbstractDungeon.ascensionLevel >= 19)
            {
                setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
                return;
            } else
            {
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STUN);
                return;
            }
        if(lastMove((byte)3) || lastMove((byte)5) || lastMove((byte)4))
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 2, true);
        else
            setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
        numTurns++;
    }

    public void die()
    {
        useFastShakeAnimation(5F);
        CardCrawlGame.screenShake.rumble(4F);
        super.die();
        onBossVictoryLogic();
        Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDead && !m.isDying)
            {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
            }
        } while(true);
        UnlockTracker.hardUnlockOverride("AUTOMATON");
        UnlockTracker.unlockAchievement("AUTOMATON");
    }

    public static final String ID = "BronzeAutomaton";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    private static final String MOVES[];
    private static final int HP = 300;
    private static final int A_2_HP = 320;
    private static final byte FLAIL = 1;
    private static final byte HYPER_BEAM = 2;
    private static final byte STUNNED = 3;
    private static final byte SPAWN_ORBS = 4;
    private static final byte BOOST = 5;
    private static final String BEAM_NAME;
    private static final int FLAIL_DMG = 7;
    private static final int BEAM_DMG = 45;
    private static final int A_2_FLAIL_DMG = 8;
    private static final int A_2_BEAM_DMG = 50;
    private int flailDmg;
    private int beamDmg;
    private static final int BLOCK_AMT = 9;
    private static final int STR_AMT = 3;
    private static final int A_2_BLOCK_AMT = 12;
    private static final int A_2_STR_AMT = 4;
    private int strAmt;
    private int blockAmt;
    private int numTurns;
    private boolean firstTurn;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BronzeAutomaton");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        BEAM_NAME = MOVES[0];
    }
}
