// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractOrb.java

package com.megacrit.cardcrawl.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.BobEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.orbs:
//            Dark, Frost, Lightning, Plasma, 
//            EmptyOrbSlot

public abstract class AbstractOrb
{

    public AbstractOrb()
    {
        tips = new ArrayList();
        evokeAmount = 0;
        passiveAmount = 0;
        baseEvokeAmount = 0;
        basePassiveAmount = 0;
        cX = 0.0F;
        cY = 0.0F;
        c = Settings.CREAM_COLOR.cpy();
        shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        hb = new Hitbox(96F * Settings.scale, 96F * Settings.scale);
        img = null;
        bobEffect = new BobEffect(3F * Settings.scale, 3F);
        fontScale = 0.7F;
        showEvokeValue = false;
        channelAnimTimer = 0.5F;
    }

    public abstract void updateDescription();

    public abstract void onEvoke();

    public static AbstractOrb getRandomOrb(boolean useCardRng)
    {
        ArrayList orbs = new ArrayList();
        orbs.add(new Dark());
        orbs.add(new Frost());
        orbs.add(new Lightning());
        orbs.add(new Plasma());
        if(useCardRng)
            return (AbstractOrb)orbs.get(AbstractDungeon.cardRandomRng.random(orbs.size() - 1));
        else
            return (AbstractOrb)orbs.get(MathUtils.random(orbs.size() - 1));
    }

    public void onStartOfTurn()
    {
    }

    public void onEndOfTurn()
    {
    }

    public void applyFocus()
    {
        AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if(power != null && !ID.equals("Plasma"))
        {
            passiveAmount = Math.max(0, basePassiveAmount + power.amount);
            evokeAmount = Math.max(0, baseEvokeAmount + power.amount);
        } else
        {
            passiveAmount = basePassiveAmount;
            evokeAmount = baseEvokeAmount;
        }
    }

    public static int applyLockOn(AbstractCreature target, int dmg)
    {
        int retVal = dmg;
        if(target.hasPower("Lockon"))
            retVal = (int)((float)retVal * 1.5F);
        return retVal;
    }

    public abstract AbstractOrb makeCopy();

    public void update()
    {
        hb.update();
        if(hb.hovered)
            TipHelper.renderGenericTip(tX + 96F * Settings.scale, tY + 64F * Settings.scale, name, description);
        fontScale = MathHelper.scaleLerpSnap(fontScale, 0.7F);
    }

    public void updateAnimation()
    {
        bobEffect.update();
        cX = MathHelper.orbLerpSnap(cX, AbstractDungeon.player.animX + tX);
        cY = MathHelper.orbLerpSnap(cY, AbstractDungeon.player.animY + tY);
        if(channelAnimTimer != 0.0F)
        {
            channelAnimTimer -= Gdx.graphics.getDeltaTime();
            if(channelAnimTimer < 0.0F)
                channelAnimTimer = 0.0F;
        }
        c.a = Interpolation.pow2In.apply(1.0F, 0.01F, channelAnimTimer / 0.5F);
        scale = Interpolation.swingIn.apply(Settings.scale, 0.01F, channelAnimTimer / 0.5F);
    }

    public void setSlot(int slotNum, int maxOrbs)
    {
        float dist = 160F * Settings.scale + (float)maxOrbs * 10F * Settings.scale;
        float angle = 100F + (float)maxOrbs * 12F;
        float offsetAngle = angle / 2.0F;
        angle *= (float)slotNum / ((float)maxOrbs - 1.0F);
        angle += 90F - offsetAngle;
        tX = dist * MathUtils.cosDeg(angle) + AbstractDungeon.player.drawX;
        tY = dist * MathUtils.sinDeg(angle) + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
        if(maxOrbs == 1)
        {
            tX = AbstractDungeon.player.drawX;
            tY = 160F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
        }
        hb.move(tX, tY);
    }

    public abstract void render(SpriteBatch spritebatch);

    protected void renderText(SpriteBatch sb)
    {
        if(!(this instanceof EmptyOrbSlot))
            if(showEvokeValue)
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(evokeAmount), cX + NUM_X_OFFSET, cY + bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, c.a), fontScale);
            else
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(passiveAmount), cX + NUM_X_OFFSET, cY + bobEffect.y / 2.0F + NUM_Y_OFFSET, c, fontScale);
    }

    public void triggerEvokeAnimation()
    {
    }

    public void showEvokeValue()
    {
        showEvokeValue = true;
        fontScale = 1.5F;
    }

    public void hideEvokeValues()
    {
        showEvokeValue = false;
    }

    public abstract void playChannelSFX();

    public String name;
    public String description;
    public String ID;
    protected ArrayList tips;
    public int evokeAmount;
    public int passiveAmount;
    protected int baseEvokeAmount;
    protected int basePassiveAmount;
    public float cX;
    public float cY;
    public float tX;
    public float tY;
    protected Color c;
    protected Color shineColor;
    protected static final int W = 96;
    public Hitbox hb;
    protected Texture img;
    protected BobEffect bobEffect;
    protected static final float NUM_X_OFFSET;
    protected static final float NUM_Y_OFFSET;
    protected float angle;
    protected float scale;
    protected float fontScale;
    protected boolean showEvokeValue;
    protected static final float CHANNEL_IN_TIME = 0.5F;
    protected float channelAnimTimer;

    static 
    {
        NUM_X_OFFSET = 20F * Settings.scale;
        NUM_Y_OFFSET = -12F * Settings.scale;
    }
}
