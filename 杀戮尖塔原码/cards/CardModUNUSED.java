// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardModUNUSED.java

package com.megacrit.cardcrawl.cards;


// Referenced classes of package com.megacrit.cardcrawl.cards:
//            AbstractCard

public class CardModUNUSED
{
    public static final class DurationType extends Enum
    {

        public static DurationType[] values()
        {
            return (DurationType[])$VALUES.clone();
        }

        public static DurationType valueOf(String name)
        {
            return (DurationType)Enum.valueOf(com/megacrit/cardcrawl/cards/CardModUNUSED$DurationType, name);
        }

        public static final DurationType ONE_TURN;
        public static final DurationType COMBAT;
        public static final DurationType ATTACKS_PLAYED;
        public static final DurationType CARDS_PLAYED;
        private static final DurationType $VALUES[];

        static 
        {
            ONE_TURN = new DurationType("ONE_TURN", 0);
            COMBAT = new DurationType("COMBAT", 1);
            ATTACKS_PLAYED = new DurationType("ATTACKS_PLAYED", 2);
            CARDS_PLAYED = new DurationType("CARDS_PLAYED", 3);
            $VALUES = (new DurationType[] {
                ONE_TURN, COMBAT, ATTACKS_PLAYED, CARDS_PLAYED
            });
        }

        private DurationType(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class EffectType extends Enum
    {

        public static EffectType[] values()
        {
            return (EffectType[])$VALUES.clone();
        }

        public static EffectType valueOf(String name)
        {
            return (EffectType)Enum.valueOf(com/megacrit/cardcrawl/cards/CardModUNUSED$EffectType, name);
        }

        public static final EffectType DAMAGE;
        private static final EffectType $VALUES[];

        static 
        {
            DAMAGE = new EffectType("DAMAGE", 0);
            $VALUES = (new EffectType[] {
                DAMAGE
            });
        }

        private EffectType(String s, int i)
        {
            super(s, i);
        }
    }


    public CardModUNUSED(EffectType type, DurationType dur, int amount, String key)
    {
        applied = false;
        this.type = type;
        this.dur = dur;
        this.amount = amount;
        this.key = key;
    }

    public void apply(AbstractCard card)
    {
        if(!applied)
        {
            applied = true;
            static class _cls1
            {

                static final int $SwitchMap$com$megacrit$cardcrawl$cards$CardModUNUSED$EffectType[];

                static 
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$CardModUNUSED$EffectType = new int[EffectType.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$CardModUNUSED$EffectType[EffectType.DAMAGE.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                }
            }

            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.CardModUNUSED.EffectType[type.ordinal()])
            {
            case 1: // '\001'
                card.damage += amount;
                break;
            }
        }
    }

    public int applyDamageMod(int baseDamage)
    {
        return baseDamage + amount;
    }

    public void unapply(AbstractCard card)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.CardModUNUSED.EffectType[type.ordinal()])
        {
        case 1: // '\001'
            card.damage -= amount;
            break;
        }
    }

    public String key;
    private EffectType type;
    public DurationType dur;
    private int amount;
    private boolean applied;
}
