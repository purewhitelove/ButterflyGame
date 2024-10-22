// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InflameEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            FlameParticleEffect

public class InflameEffect extends AbstractGameEffect
{

    public InflameEffect(AbstractCreature creature)
    {
        x = creature.hb.cX;
        y = creature.hb.cY;
    }

    public void update()
    {
        CardCrawlGame.sound.play("ATTACK_FIRE");
        for(int i = 0; i < 75; i++)
            AbstractDungeon.effectsQueue.add(new FlameParticleEffect(x, y));

        for(int i = 0; i < 20; i++)
            AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(x, y));

        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    float x;
    float y;
}
