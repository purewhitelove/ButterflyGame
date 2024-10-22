// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DarkSmokePuffEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, FastDarkSmoke

public class DarkSmokePuffEffect extends AbstractGameEffect
{

    public DarkSmokePuffEffect(float x, float y)
    {
        smoke = new ArrayList();
        duration = 0.8F;
        for(int i = 0; i < 20; i++)
            smoke.add(new FastDarkSmoke(x, y));

    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < 0.7F)
            killSmoke();
        FastDarkSmoke b;
        for(Iterator iterator = smoke.iterator(); iterator.hasNext(); b.update())
            b = (FastDarkSmoke)iterator.next();

    }

    private void killSmoke()
    {
        FastDarkSmoke s;
        for(Iterator iterator = smoke.iterator(); iterator.hasNext(); s.kill())
            s = (FastDarkSmoke)iterator.next();

    }

    public void render(SpriteBatch sb)
    {
        FastDarkSmoke b;
        for(Iterator iterator = smoke.iterator(); iterator.hasNext(); b.render(sb))
            b = (FastDarkSmoke)iterator.next();

    }

    public void dispose()
    {
    }

    private static final float DEFAULT_DURATION = 0.8F;
    private ArrayList smoke;
}
