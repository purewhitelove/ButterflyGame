// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmptyOrbSlot.java

package com.megacrit.cardcrawl.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.BobEffect;

// Referenced classes of package com.megacrit.cardcrawl.orbs:
//            AbstractOrb

public class EmptyOrbSlot extends AbstractOrb
{

    public EmptyOrbSlot(float x, float y)
    {
        angle = MathUtils.random(360F);
        ID = "Empty";
        name = orbString.NAME;
        evokeAmount = 0;
        cX = x;
        cY = y;
        updateDescription();
        channelAnimTimer = 0.5F;
    }

    public EmptyOrbSlot()
    {
        angle = MathUtils.random(360F);
        name = orbString.NAME;
        evokeAmount = 0;
        cX = AbstractDungeon.player.drawX + AbstractDungeon.player.hb_x;
        cY = AbstractDungeon.player.drawY + AbstractDungeon.player.hb_y + AbstractDungeon.player.hb_h / 2.0F;
        updateDescription();
    }

    public void updateDescription()
    {
        description = DESC[0];
    }

    public void onEvoke()
    {
    }

    public void updateAnimation()
    {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 10F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(c);
        sb.draw(ImageMaster.ORB_SLOT_2, cX - 48F - bobEffect.y / 8F, (cY - 48F) + bobEffect.y / 8F, 48F, 48F, 96F, 96F, scale, scale, 0.0F, 0, 0, 96, 96, false, false);
        sb.draw(ImageMaster.ORB_SLOT_1, (cX - 48F) + bobEffect.y / 8F, cY - 48F - bobEffect.y / 8F, 48F, 48F, 96F, 96F, scale, scale, angle, 0, 0, 96, 96, false, false);
        renderText(sb);
        hb.render(sb);
    }

    public AbstractOrb makeCopy()
    {
        return new EmptyOrbSlot();
    }

    public void playChannelSFX()
    {
    }

    public static final String ORB_ID = "Empty";
    private static final OrbStrings orbString;
    public static final String DESC[];

    static 
    {
        orbString = CardCrawlGame.languagePack.getOrbString("Empty");
        DESC = orbString.DESCRIPTION;
    }
}
