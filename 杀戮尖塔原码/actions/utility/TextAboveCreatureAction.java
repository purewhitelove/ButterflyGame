// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextAboveCreatureAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import java.util.ArrayList;

public class TextAboveCreatureAction extends AbstractGameAction
{
    public static final class TextType extends Enum
    {

        public static TextType[] values()
        {
            return (TextType[])$VALUES.clone();
        }

        public static TextType valueOf(String name)
        {
            return (TextType)Enum.valueOf(com/megacrit/cardcrawl/actions/utility/TextAboveCreatureAction$TextType, name);
        }

        public static final TextType STUNNED;
        public static final TextType INTERRUPTED;
        private static final TextType $VALUES[];

        static 
        {
            STUNNED = new TextType("STUNNED", 0);
            INTERRUPTED = new TextType("INTERRUPTED", 1);
            $VALUES = (new TextType[] {
                STUNNED, INTERRUPTED
            });
        }

        private TextType(String s, int i)
        {
            super(s, i);
        }
    }


    public TextAboveCreatureAction(AbstractCreature source, TextType type)
    {
        used = false;
        if(type == TextType.STUNNED)
        {
            setValues(source, source);
            msg = AbstractCreature.TEXT[3];
            actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
            duration = Settings.ACTION_DUR_FASTER;
        } else
        if(type == TextType.INTERRUPTED)
        {
            setValues(source, source);
            msg = AbstractCreature.TEXT[4];
            actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
            duration = Settings.ACTION_DUR_FASTER;
        } else
        {
            isDone = true;
        }
    }

    public TextAboveCreatureAction(AbstractCreature source, String text)
    {
        used = false;
        setValues(source, source);
        msg = text;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
        duration = Settings.ACTION_DUR_FASTER;
    }

    public void update()
    {
        if(!used)
        {
            AbstractDungeon.effectList.add(new TextAboveCreatureEffect(source.hb.cX - source.animX, source.hb.cY + target.hb.height / 2.0F, msg, Color.WHITE.cpy()));
            used = true;
        }
        tickDuration();
    }

    private boolean used;
    private String msg;
}
