// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SmokePuffEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, FastSmoke

public class SmokePuffEffect extends AbstractGameEffect
{

    public SmokePuffEffect(float x, float y)
    {
        smoke = new ArrayList();
        duration = 0.8F;
        for(int i = 0; i < 30; i++)
            smoke.add(new FastSmoke(x, y));

    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < 0.7F)
            killSmoke();
        FastSmoke b;
        for(Iterator iterator = smoke.iterator(); iterator.hasNext(); b.update())
            b = (FastSmoke)iterator.next();

    }

    private void killSmoke()
    {
        FastSmoke s;
        for(Iterator iterator = smoke.iterator(); iterator.hasNext(); s.kill())
            s = (FastSmoke)iterator.next();

    }

    public void render(SpriteBatch sb)
    {
        FastSmoke b;
        for(Iterator iterator = smoke.iterator(); iterator.hasNext(); b.render(sb))
            b = (FastSmoke)iterator.next();

    }

    public void dispose()
    {
    }

    private static final float DEFAULT_DURATION = 0.8F;
    private ArrayList smoke;
}
