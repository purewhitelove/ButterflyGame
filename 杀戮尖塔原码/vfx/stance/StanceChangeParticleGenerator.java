// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StanceChangeParticleGenerator.java

package com.megacrit.cardcrawl.vfx.stance;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.stance:
//            DivinityStanceChangeParticle, WrathStanceChangeParticle

public class StanceChangeParticleGenerator extends AbstractGameEffect
{

    public StanceChangeParticleGenerator(float x, float y, String stanceId)
    {
        this.x = x;
        this.y = y;
        this.stanceId = stanceId;
    }

    public void update()
    {
        if(!stanceId.equals("Calm"))
            if(stanceId.equals("Divinity"))
            {
                for(int i = 0; i < 20; i++)
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.PINK, x, y));

            } else
            if(stanceId.equals("Wrath"))
            {
                for(int i = 0; i < 10; i++)
                    AbstractDungeon.effectsQueue.add(new WrathStanceChangeParticle(x));

            } else
            if(stanceId.equals("Neutral"))
            {
                for(int i = 0; i < 20; i++)
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.WHITE, x, y));

            } else
            {
                for(int i = 0; i < 20; i++)
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.WHITE, x, y));

            }
        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private String stanceId;
}
