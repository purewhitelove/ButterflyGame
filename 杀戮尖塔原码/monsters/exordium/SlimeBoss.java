// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SlimeBoss.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SplitPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.monsters.exordium:
//            SpikeSlime_L, AcidSlime_L

public class SlimeBoss extends AbstractMonster
{

    public SlimeBoss()
    {
        super(NAME, "SlimeBoss", 140, 0.0F, -30F, 400F, 350F, null, 0.0F, 28F);
        firstTurn = true;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
        dialogX = -150F * Settings.scale;
        dialogY = -70F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 9)
            setHp(150);
        else
            setHp(140);
        if(AbstractDungeon.ascensionLevel >= 4)
        {
            tackleDmg = 10;
            slamDmg = 38;
        } else
        {
            tackleDmg = 9;
            slamDmg = 35;
        }
        damage.add(new DamageInfo(this, tackleDmg));
        damage.add(new DamageInfo(this, slamDmg));
        powers.add(new SplitPower(this));
        loadAnimation("images/monsters/theBottom/boss/slime/skeleton.atlas", "images/monsters/theBottom/boss/slime/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction()
    {
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)
        {
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");
        }
        UnlockTracker.markBossAsSeen("SLIME");
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_SLIME_ATTACK"));
            if(AbstractDungeon.ascensionLevel >= 19)
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 5));
            else
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 3));
            setMove(PREP_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            break;

        case 2: // '\002'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0], 1.0F, 2.0F));
            AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.3F, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.LONG, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.LOW));
            setMove(SLAM_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateJumpAction(this));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(0.1F, 1.0F, 0.1F, 0.0F))));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON));
            setMove(STICKY_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
            AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(this, 1.0F, 0.1F));
            AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this));
            AbstractDungeon.actionManager.addToBottom(new SuicideAction(this, false));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new SpikeSlime_L(-385F, 20F, 0, currentHealth), false));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new AcidSlime_L(120F, -8F, 0, currentHealth), false));
            AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
            setMove(SPLIT_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            break;
        }
    }

    private void playSfx()
    {
        int roll = MathUtils.random(1);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLIMEBOSS_1A"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLIMEBOSS_1B"));
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(!isDying && (float)currentHealth <= (float)maxHealth / 2.0F && nextMove != 3)
        {
            logger.info("SPLIT");
            setMove(SPLIT_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            createIntent();
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType.INTERRUPTED));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, SPLIT_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN));
        }
    }

    protected void getMove(int num)
    {
        if(firstTurn)
        {
            firstTurn = false;
            setMove(STICKY_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            return;
        } else
        {
            return;
        }
    }

    public void die()
    {
        super.die();
        CardCrawlGame.sound.play("VO_SLIMEBOSS_2A");
        for(Iterator iterator = AbstractDungeon.actionManager.actions.iterator(); iterator.hasNext();)
        {
            AbstractGameAction a = (AbstractGameAction)iterator.next();
            if(a instanceof SpawnMonsterAction)
                return;
        }

        if(currentHealth <= 0)
        {
            useFastShakeAnimation(5F);
            CardCrawlGame.screenShake.rumble(4F);
            onBossVictoryLogic();
            UnlockTracker.hardUnlockOverride("SLIME");
            UnlockTracker.unlockAchievement("SLIME_BOSS");
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/monsters/exordium/SlimeBoss.getName());
    public static final String ID = "SlimeBoss";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP = 140;
    public static final int A_2_HP = 150;
    public static final int TACKLE_DAMAGE = 9;
    public static final int SLAM_DAMAGE = 35;
    public static final int A_2_TACKLE_DAMAGE = 10;
    public static final int A_2_SLAM_DAMAGE = 38;
    private int tackleDmg;
    private int slamDmg;
    public static final int STICKY_TURNS = 3;
    private static final byte SLAM = 1;
    private static final byte PREP_SLAM = 2;
    private static final byte SPLIT = 3;
    private static final byte STICKY = 4;
    private static final String SLAM_NAME;
    private static final String PREP_NAME;
    private static final String SPLIT_NAME;
    private static final String STICKY_NAME;
    private boolean firstTurn;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SlimeBoss");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        SLAM_NAME = MOVES[0];
        PREP_NAME = MOVES[1];
        SPLIT_NAME = MOVES[2];
        STICKY_NAME = MOVES[3];
    }
}
