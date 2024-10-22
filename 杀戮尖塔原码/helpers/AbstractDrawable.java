// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractDrawable.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractDrawable
    implements Comparable
{

    public AbstractDrawable()
    {
    }

    public abstract void render(SpriteBatch spritebatch);

    public int compareTo(AbstractDrawable other)
    {
        return Integer.compare(z, other.z);
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((AbstractDrawable)obj);
    }

    public int z;
}
