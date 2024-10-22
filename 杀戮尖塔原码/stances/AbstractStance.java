// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractStance.java

package com.megacrit.cardcrawl.stances;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.stances:
//            CalmStance, WrathStance, DivinityStance, NeutralStance

public abstract class AbstractStance
{

    public AbstractStance()
    {
        tips = new ArrayList();
        c = Color.WHITE.cpy();
        img = null;
        particleTimer = 0.0F;
        particleTimer2 = 0.0F;
    }

    public abstract void updateDescription();

    public void atStartOfTurn()
    {
    }

    public void onEndOfTurn()
    {
    }

    public void onEnterStance()
    {
    }

    public void onExitStance()
    {
    }

    public float atDamageGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        return damage;
    }

    public float atDamageGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, AbstractCard card)
    {
        return atDamageGive(damage, type);
    }

    public float atDamageReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType damageType)
    {
        return damage;
    }

    public void onPlayCard(AbstractCard abstractcard)
    {
    }

    public void update()
    {
        updateAnimation();
    }

    public void updateAnimation()
    {
    }

    public void render(SpriteBatch sb)
    {
        if(img == null)
        {
            return;
        } else
        {
            sb.setColor(c);
            sb.setBlendFunction(770, 1);
            sb.draw(img, (AbstractDungeon.player.drawX - 256F) + AbstractDungeon.player.animX, (AbstractDungeon.player.drawY - 256F) + AbstractDungeon.player.animY + AbstractDungeon.player.hb_h / 2.0F, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, -angle, 0, 0, 512, 512, false, false);
            sb.setBlendFunction(770, 771);
            return;
        }
    }

    public void stopIdleSfx()
    {
    }

    public static AbstractStance getStanceFromName(String name)
    {
        if(name.equals("Calm"))
            return new CalmStance();
        if(name.equals("Wrath"))
            return new WrathStance();
        if(name.equals("Divinity"))
            return new DivinityStance();
        if(name.equals("Neutral"))
        {
            return new NeutralStance();
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] Unknown stance: ").append(name).append(" called for in getStanceFromName()").toString());
            return null;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/stances/AbstractStance.getName());
    public String name;
    public String description;
    public String ID;
    protected ArrayList tips;
    protected Color c;
    protected static final int W = 512;
    protected Texture img;
    protected float angle;
    protected float particleTimer;
    protected float particleTimer2;

}
