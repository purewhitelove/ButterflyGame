// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GainPennyEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, ShineLinesEffect, GainGoldTextEffect

public class GainPennyEffect extends AbstractGameEffect
{

    public GainPennyEffect(AbstractCreature owner, float x, float y, float targetX, float targetY, boolean showGainEffect)
    {
        alpha = 0.0F;
        suctionTimer = 0.7F;
        if(MathUtils.randomBoolean())
            img = ImageMaster.COPPER_COIN_1;
        else
            img = ImageMaster.COPPER_COIN_2;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        this.targetX = targetX + MathUtils.random(-TARGET_JITTER, TARGET_JITTER);
        this.targetY = targetY + MathUtils.random(-TARGET_JITTER, TARGET_JITTER * 2.0F);
        this.showGainEffect = showGainEffect;
        this.owner = owner;
        staggerTimer = MathUtils.random(0.0F, 0.5F);
        vX = MathUtils.random(START_VX - 50F * Settings.scale, START_VX_JITTER);
        rotationSpeed = MathUtils.random(500F, 2000F);
        if(MathUtils.randomBoolean())
        {
            vX = -vX;
            rotationSpeed = -rotationSpeed;
        }
        vY = MathUtils.random(START_VY, START_VY_JITTER);
        scale = Settings.scale;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    }

    public GainPennyEffect(float x, float y)
    {
        this(((AbstractCreature) (AbstractDungeon.player)), x, y, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true);
    }

    public void update()
    {
        if(staggerTimer > 0.0F)
        {
            staggerTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        if(alpha != 1.0F)
        {
            alpha += Gdx.graphics.getDeltaTime() * 2.0F;
            if(alpha > 1.0F)
                alpha = 1.0F;
            color.a = alpha;
        }
        rotation += Gdx.graphics.getDeltaTime() * rotationSpeed;
        x += Gdx.graphics.getDeltaTime() * vX;
        y += Gdx.graphics.getDeltaTime() * vY;
        vY -= Gdx.graphics.getDeltaTime() * GRAVITY;
        if(suctionTimer > 0.0F)
        {
            suctionTimer -= Gdx.graphics.getDeltaTime();
        } else
        {
            vY = MathUtils.lerp(vY, 0.0F, Gdx.graphics.getDeltaTime() * 5F);
            vX = MathUtils.lerp(vX, 0.0F, Gdx.graphics.getDeltaTime() * 5F);
            x = MathUtils.lerp(x, targetX, Gdx.graphics.getDeltaTime() * 4F);
            y = MathUtils.lerp(y, targetY, Gdx.graphics.getDeltaTime() * 4F);
            if(Math.abs(x - targetX) < 20F)
            {
                isDone = true;
                if(MathUtils.randomBoolean())
                    CardCrawlGame.sound.play("GOLD_GAIN", 0.1F);
                if(!owner.isPlayer)
                    owner.gainGold(1);
                AbstractDungeon.effectsQueue.add(new ShineLinesEffect(x, y));
                boolean textEffectFound = false;
                Iterator iterator = AbstractDungeon.effectList.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    AbstractGameEffect e = (AbstractGameEffect)iterator.next();
                    if(!(e instanceof GainGoldTextEffect) || !((GainGoldTextEffect)e).ping(1))
                        continue;
                    textEffectFound = true;
                    break;
                } while(true);
                if(!textEffectFound)
                {
                    Iterator iterator1 = AbstractDungeon.effectsQueue.iterator();
                    do
                    {
                        if(!iterator1.hasNext())
                            break;
                        AbstractGameEffect e = (AbstractGameEffect)iterator1.next();
                        if((e instanceof GainGoldTextEffect) && ((GainGoldTextEffect)e).ping(1))
                            textEffectFound = true;
                    } while(true);
                }
                if(!textEffectFound && showGainEffect)
                    AbstractDungeon.effectsQueue.add(new GainGoldTextEffect(1));
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        if(staggerTimer > 0.0F)
        {
            return;
        } else
        {
            sb.setColor(color);
            sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
            return;
        }
    }

    public void dispose()
    {
    }

    private static final float GRAVITY;
    private static final float START_VY;
    private static final float START_VY_JITTER;
    private static final float START_VX;
    private static final float START_VX_JITTER;
    private static final float TARGET_JITTER;
    private float rotationSpeed;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float targetX;
    private float targetY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float alpha;
    private float suctionTimer;
    private float staggerTimer;
    private boolean showGainEffect;
    private AbstractCreature owner;

    static 
    {
        GRAVITY = 2000F * Settings.scale;
        START_VY = 800F * Settings.scale;
        START_VY_JITTER = 400F * Settings.scale;
        START_VX = 200F * Settings.scale;
        START_VX_JITTER = 300F * Settings.scale;
        TARGET_JITTER = 50F * Settings.scale;
    }
}
