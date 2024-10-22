// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HitboxListener.java

package com.megacrit.cardcrawl.helpers;


// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            Hitbox

public interface HitboxListener
{

    public abstract void hoverStarted(Hitbox hitbox);

    public abstract void startClicking(Hitbox hitbox);

    public abstract void clicked(Hitbox hitbox);
}
