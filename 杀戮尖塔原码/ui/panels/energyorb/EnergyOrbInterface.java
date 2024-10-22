// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnergyOrbInterface.java

package com.megacrit.cardcrawl.ui.panels.energyorb;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface EnergyOrbInterface
{

    public abstract void renderOrb(SpriteBatch spritebatch, boolean flag, float f, float f1);

    public abstract void updateOrb(int i);
}
