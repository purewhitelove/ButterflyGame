// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HexaghostOrb.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;
import java.util.ArrayList;

public class HexaghostOrb
{

    public HexaghostOrb(float x, float y, int index)
    {
        effect = new BobEffect(2.0F);
        activated = false;
        hidden = false;
        playedSfx = false;
        particleTimer = 0.0F;
        this.x = x * Settings.scale + MathUtils.random(-10F, 10F) * Settings.scale;
        this.y = y * Settings.scale + MathUtils.random(-10F, 10F) * Settings.scale;
        activateTimer = (float)index * 0.3F;
        color = Color.CHARTREUSE.cpy();
        color.a = 0.0F;
        hidden = true;
    }

    public void activate(float oX, float oY)
    {
        playedSfx = false;
        activated = true;
        hidden = false;
    }

    public void deactivate()
    {
        activated = false;
    }

    public void hide()
    {
        hidden = true;
    }

    public void update(float oX, float oY)
    {
        if(!hidden)
        {
            if(activated)
            {
                activateTimer -= Gdx.graphics.getDeltaTime();
                if(activateTimer < 0.0F)
                {
                    if(!playedSfx)
                    {
                        playedSfx = true;
                        AbstractDungeon.effectsQueue.add(new GhostIgniteEffect(x + oX, y + oY));
                        if(MathUtils.randomBoolean())
                            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.3F);
                        else
                            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_2", 0.3F);
                    }
                    color.a = MathHelper.fadeLerpSnap(color.a, 1.0F);
                    effect.update();
                    effect.update();
                    particleTimer -= Gdx.graphics.getDeltaTime();
                    if(particleTimer < 0.0F)
                    {
                        AbstractDungeon.effectList.add(new GhostlyFireEffect(x + oX + effect.y * 2.0F, y + oY + effect.y * 2.0F));
                        particleTimer = 0.06F;
                    }
                }
            } else
            {
                effect.update();
                particleTimer -= Gdx.graphics.getDeltaTime();
                if(particleTimer < 0.0F)
                {
                    AbstractDungeon.effectList.add(new GhostlyWeakFireEffect(x + oX + effect.y * 2.0F, y + oY + effect.y * 2.0F));
                    particleTimer = 0.06F;
                }
            }
        } else
        {
            color.a = MathHelper.fadeLerpSnap(color.a, 0.0F);
        }
    }

    public static final String ID = "HexaghostOrb";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private BobEffect effect;
    private float activateTimer;
    public boolean activated;
    public boolean hidden;
    public boolean playedSfx;
    private Color color;
    private float x;
    private float y;
    private float particleTimer;
    private static final float PARTICLE_INTERVAL = 0.06F;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("HexaghostOrb");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
