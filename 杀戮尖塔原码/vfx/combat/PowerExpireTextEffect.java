// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PowerExpireTextEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            FlyingSpikeEffect

public class PowerExpireTextEffect extends AbstractGameEffect
{

    public PowerExpireTextEffect(float x, float y, String msg, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region)
    {
        spikeEffectTriggered = false;
        duration = 2.0F;
        startingDuration = 2.0F;
        this.msg = msg;
        this.x = x - 64F * Settings.scale;
        this.y = y;
        color = Color.WHITE.cpy();
        offsetY = STARTING_OFFSET_Y;
        this.region = region;
        scale = Settings.scale * 0.7F;
    }

    public void update()
    {
        if(duration < startingDuration * 0.8F && !spikeEffectTriggered && !Settings.DISABLE_EFFECTS)
        {
            spikeEffectTriggered = true;
            for(int i = 0; i < 10; i++)
                AbstractDungeon.effectsQueue.add(new FlyingSpikeEffect((x - MathUtils.random(20F) * Settings.scale) + 70F * Settings.scale, y + MathUtils.random(STARTING_OFFSET_Y, TARGET_OFFSET_Y) * Settings.scale, 0.0F, MathUtils.random(50F, 400F) * Settings.scale, 0.0F, Settings.BLUE_TEXT_COLOR));

            for(int i = 0; i < 10; i++)
                AbstractDungeon.effectsQueue.add(new FlyingSpikeEffect(x + MathUtils.random(20F) * Settings.scale, y + MathUtils.random(STARTING_OFFSET_Y, TARGET_OFFSET_Y) * Settings.scale, 0.0F, MathUtils.random(-400F, -50F) * Settings.scale, 0.0F, Settings.BLUE_TEXT_COLOR));

        }
        offsetY = Interpolation.exp10In.apply(TARGET_OFFSET_Y, STARTING_OFFSET_Y, duration / 2.0F);
        color.a = Interpolation.exp10Out.apply(0.0F, 1.0F, duration / 2.0F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            duration = 0.0F;
        }
    }

    public void render(SpriteBatch sb)
    {
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.losePowerFont, msg, x, y + offsetY, color);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.losePowerFont, TEXT[0], x, (y + offsetY) - 40F * Settings.scale, color);
        if(region != null)
        {
            sb.setColor(color);
            sb.setBlendFunction(770, 1);
            sb.draw(region, x - (float)(region.packedWidth / 2) - 64F * Settings.scale, (y + h + offsetY) - (float)(region.packedHeight / 2) - 30F * Settings.scale, (float)region.packedWidth / 2.0F, (float)region.packedHeight / 2.0F, region.packedWidth, region.packedHeight, scale, scale, rotation);
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose()
    {
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final float TEXT_DURATION = 2F;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private float x;
    private float y;
    private float offsetY;
    private float h;
    private String msg;
    private boolean spikeEffectTriggered;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("PowerExpireTextEffect");
        TEXT = uiStrings.TEXT;
        STARTING_OFFSET_Y = 80F * Settings.scale;
        TARGET_OFFSET_Y = 160F * Settings.scale;
    }
}
