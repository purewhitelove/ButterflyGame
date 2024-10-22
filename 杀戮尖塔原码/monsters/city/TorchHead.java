// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TorchHead.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.TorchHeadFireEffect;
import java.util.ArrayList;

public class TorchHead extends AbstractMonster
{

    public TorchHead(float x, float y)
    {
        super(NAME, "TorchHead", AbstractDungeon.monsterHpRng.random(38, 40), -5F, -20F, 145F, 240F, null, x, y);
        fireTimer = 0.0F;
        setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 7);
        damage.add(new DamageInfo(this, 7));
        loadAnimation("images/monsters/theCity/torchHead/skeleton.atlas", "images/monsters/theCity/torchHead/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if(AbstractDungeon.ascensionLevel >= 9)
            setHp(40, 45);
        else
            setHp(38, 40);
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 7));
            break;
        }
    }

    public void update()
    {
        super.update();
        if(!isDying)
        {
            fireTimer -= Gdx.graphics.getDeltaTime();
            if(fireTimer < 0.0F)
            {
                fireTimer = 0.04F;
                AbstractDungeon.effectList.add(new TorchHeadFireEffect(skeleton.getX() + skeleton.findBone("fireslot").getX() + 10F * Settings.scale, skeleton.getY() + skeleton.findBone("fireslot").getY() + 110F * Settings.scale));
            }
        }
    }

    protected void getMove(int num)
    {
        setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 7);
    }

    public static final String ID = "TorchHead";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP_MIN = 38;
    public static final int HP_MAX = 40;
    public static final int A_2_HP_MIN = 40;
    public static final int A_2_HP_MAX = 45;
    public static final int ATTACK_DMG = 7;
    private static final byte TACKLE = 1;
    private float fireTimer;
    private static final float FIRE_TIME = 0.04F;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TorchHead");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
