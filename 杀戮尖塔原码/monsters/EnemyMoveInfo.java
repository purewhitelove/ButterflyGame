// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnemyMoveInfo.java

package com.megacrit.cardcrawl.monsters;


// Referenced classes of package com.megacrit.cardcrawl.monsters:
//            AbstractMonster

public class EnemyMoveInfo
{

    public EnemyMoveInfo(byte nextMove, AbstractMonster.Intent intent, int intentBaseDmg, int multiplier, boolean isMultiDamage)
    {
        this.nextMove = nextMove;
        this.intent = intent;
        baseDamage = intentBaseDmg;
        this.multiplier = multiplier;
        this.isMultiDamage = isMultiDamage;
    }

    public byte nextMove;
    public AbstractMonster.Intent intent;
    public int baseDamage;
    public int multiplier;
    public boolean isMultiDamage;
}
