// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Sentry.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import java.util.ArrayList;

public class Sentry extends AbstractMonster
{

    public Sentry(float x, float y)
    {
        super(NAME, "Sentry", 42, 0.0F, -5F, 180F, 310F, null, x, y);
        firstMove = true;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
        if(AbstractDungeon.ascensionLevel >= 8)
            setHp(39, 45);
        else
            setHp(38, 42);
        if(AbstractDungeon.ascensionLevel >= 3)
            beamDmg = 10;
        else
            beamDmg = 9;
        if(AbstractDungeon.ascensionLevel >= 18)
            dazedAmt = 3;
        else
            dazedAmt = 2;
        damage.add(new DamageInfo(this, beamDmg));
        loadAnimation("images/monsters/theBottom/sentry/skeleton.atlas", "images/monsters/theBottom/sentry/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTimeScale(2.0F);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("idle", "attack", 0.1F);
        stateData.setMix("idle", "spaz1", 0.1F);
        stateData.setMix("idle", "hit", 0.1F);
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP"));
            if(!Settings.FAST_MODE)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(hb.cX, hb.cY, Color.ROYAL, com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5F));
                AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 0.6F, 0.2F));
            } else
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(hb.cX, hb.cY, Color.ROYAL, com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType.ADDITIVE), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 0.6F, 0.15F));
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), dazedAmt));
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.SKY)));
            if(Settings.FAST_MODE)
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, hb.cX, hb.cY), 0.1F));
            else
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, hb.cX, hb.cY), 0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE, Settings.FAST_MODE));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "hit", false);
            state.addAnimation(0, "idle", true, 0.0F);
        }
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
            state.setAnimation(0, "attack", false);
            state.addAnimation(0, "idle", true, 0.0F);
            break;
        }
    }

    protected void getMove(int num)
    {
        if(firstMove)
        {
            if(AbstractDungeon.getMonsters().monsters.lastIndexOf(this) % 2 == 0)
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
            else
                setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            firstMove = false;
            return;
        }
        if(lastMove((byte)4))
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
        else
            setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
    }

    public static final String ID = "Sentry";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final String ENC_NAME = "Sentries";
    private static final int HP_MIN = 38;
    private static final int HP_MAX = 42;
    private static final int A_2_HP_MIN = 39;
    private static final int A_2_HP_MAX = 45;
    private static final byte BOLT = 3;
    private static final byte BEAM = 4;
    private int beamDmg;
    private int dazedAmt;
    private static final int DAZED_AMT = 2;
    private static final int A_18_DAZED_AMT = 3;
    private boolean firstMove;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Sentry");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
