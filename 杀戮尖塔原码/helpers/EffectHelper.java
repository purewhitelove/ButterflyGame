// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EffectHelper.java

package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            Hitbox

public class EffectHelper
{

    public EffectHelper()
    {
    }

    public static void gainGold(AbstractCreature target, float srcX, float srcY, int amount)
    {
        for(int i = 0; i < amount; i++)
            AbstractDungeon.effectList.add(new GainPennyEffect(target, srcX, srcY, target.hb.cX, target.hb.cY, true));

    }
}
