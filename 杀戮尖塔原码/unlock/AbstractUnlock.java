// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractUnlock.java

package com.megacrit.cardcrawl.unlock;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class AbstractUnlock
    implements Comparable
{
    public static final class UnlockType extends Enum
    {

        public static UnlockType[] values()
        {
            return (UnlockType[])$VALUES.clone();
        }

        public static UnlockType valueOf(String name)
        {
            return (UnlockType)Enum.valueOf(com/megacrit/cardcrawl/unlock/AbstractUnlock$UnlockType, name);
        }

        public static final UnlockType CARD;
        public static final UnlockType RELIC;
        public static final UnlockType LOADOUT;
        public static final UnlockType CHARACTER;
        public static final UnlockType MISC;
        private static final UnlockType $VALUES[];

        static 
        {
            CARD = new UnlockType("CARD", 0);
            RELIC = new UnlockType("RELIC", 1);
            LOADOUT = new UnlockType("LOADOUT", 2);
            CHARACTER = new UnlockType("CHARACTER", 3);
            MISC = new UnlockType("MISC", 4);
            $VALUES = (new UnlockType[] {
                CARD, RELIC, LOADOUT, CHARACTER, MISC
            });
        }

        private UnlockType(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractUnlock()
    {
        player = null;
        card = null;
        relic = null;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public int compareTo(AbstractUnlock u)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$unlock$AbstractUnlock$UnlockType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$unlock$AbstractUnlock$UnlockType = new int[UnlockType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$unlock$AbstractUnlock$UnlockType[UnlockType.CARD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$unlock$AbstractUnlock$UnlockType[UnlockType.RELIC.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType[type.ordinal()])
        {
        case 1: // '\001'
            return card.cardID.compareTo(u.card.cardID);

        case 2: // '\002'
            return relic.relicId.compareTo(u.relic.relicId);
        }
        return title.compareTo(u.title);
    }

    public void onUnlockScreenOpen()
    {
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((AbstractUnlock)obj);
    }

    public String title;
    public String key;
    public UnlockType type;
    public AbstractPlayer player;
    public AbstractCard card;
    public AbstractRelic relic;
}
