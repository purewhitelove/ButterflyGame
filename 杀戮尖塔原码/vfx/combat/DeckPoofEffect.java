// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeckPoofEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            DeckPoofParticle

public class DeckPoofEffect extends AbstractGameEffect
{

    public DeckPoofEffect(float x, float y, boolean isDeck)
    {
        for(int i = 0; i < 70; i++)
            AbstractDungeon.effectsQueue.add(new DeckPoofParticle(x, y, isDeck));

    }

    public void update()
    {
        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }
}
