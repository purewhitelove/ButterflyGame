// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CalmStance.java

package com.megacrit.cardcrawl.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.stances:
//            AbstractStance

public class CalmStance extends AbstractStance
{

    public CalmStance()
    {
        ID = "Calm";
        name = stanceString.NAME;
        updateDescription();
    }

    public void updateDescription()
    {
        description = stanceString.DESCRIPTION[0];
    }

    public void updateAnimation()
    {
        if(!Settings.DISABLE_EFFECTS)
        {
            particleTimer -= Gdx.graphics.getDeltaTime();
            if(particleTimer < 0.0F)
            {
                particleTimer = 0.04F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }
        particleTimer2 -= Gdx.graphics.getDeltaTime();
        if(particleTimer2 < 0.0F)
        {
            particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
        }
    }

    public void onEnterStance()
    {
        if(sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SKY, true));
    }

    public void onExitStance()
    {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
        stopIdleSfx();
    }

    public void stopIdleSfx()
    {
        if(sfxId != -1L)
        {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", sfxId);
            sfxId = -1L;
        }
    }

    public static final String STANCE_ID = "Calm";
    private static final StanceStrings stanceString;
    private static long sfxId = -1L;

    static 
    {
        stanceString = CardCrawlGame.languagePack.getStanceString("Calm");
    }
}
