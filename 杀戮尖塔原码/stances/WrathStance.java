// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WrathStance.java

package com.megacrit.cardcrawl.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.stances:
//            AbstractStance

public class WrathStance extends AbstractStance
{

    public WrathStance()
    {
        ID = "Wrath";
        name = stanceString.NAME;
        updateDescription();
    }

    public float atDamageGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        if(type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL)
            return damage * 2.0F;
        else
            return damage;
    }

    public float atDamageReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        if(type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL)
            return damage * 2.0F;
        else
            return damage;
    }

    public void updateAnimation()
    {
        if(!Settings.DISABLE_EFFECTS)
        {
            particleTimer -= Gdx.graphics.getDeltaTime();
            if(particleTimer < 0.0F)
            {
                particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
            }
        }
        particleTimer2 -= Gdx.graphics.getDeltaTime();
        if(particleTimer2 < 0.0F)
        {
            particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        }
    }

    public void updateDescription()
    {
        description = stanceString.DESCRIPTION[0];
    }

    public void onEnterStance()
    {
        if(sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));
    }

    public void onExitStance()
    {
        stopIdleSfx();
    }

    public void stopIdleSfx()
    {
        if(sfxId != -1L)
        {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }
    }

    public static final String STANCE_ID = "Wrath";
    private static final StanceStrings stanceString;
    private static long sfxId = -1L;

    static 
    {
        stanceString = CardCrawlGame.languagePack.getStanceString("Wrath");
    }
}
