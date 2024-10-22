// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnemyTurnEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class EnemyTurnEffect extends AbstractGameEffect
{

    public EnemyTurnEffect()
    {
        currentHeight = 0.0F;
        duration = 2.0F;
        startingDuration = 2.0F;
        bgColor = new Color(AbstractDungeon.fadeColor.r / 2.0F, AbstractDungeon.fadeColor.g / 2.0F, AbstractDungeon.fadeColor.b / 2.0F, 0.0F);
        color = Settings.GOLD_COLOR.cpy();
        color.a = 0.0F;
        CardCrawlGame.sound.play("ENEMY_TURN");
        scale = 1.0F;
    }

    public void update()
    {
        if(duration > 1.5F)
            currentHeight = MathUtils.lerp(currentHeight, TARGET_HEIGHT, Gdx.graphics.getDeltaTime() * 3F);
        else
        if(duration < 0.5F)
            currentHeight = MathUtils.lerp(currentHeight, 0.0F, Gdx.graphics.getDeltaTime() * 3F);
        if(duration > 1.5F)
        {
            scale = Interpolation.exp10In.apply(1.0F, 3F, (duration - 1.5F) * 2.0F);
            color.a = Interpolation.exp10In.apply(1.0F, 0.0F, (duration - 1.5F) * 2.0F);
        } else
        if(duration < 0.5F)
        {
            scale = Interpolation.pow3In.apply(0.9F, 1.0F, duration * 2.0F);
            color.a = Interpolation.pow3In.apply(0.0F, 1.0F, duration * 2.0F);
        }
        bgColor.a = color.a * 0.8F;
        if(Settings.FAST_MODE)
            duration -= Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
        {
            sb.setColor(bgColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, HEIGHT_DIV_2 - currentHeight / 2.0F, Settings.WIDTH, currentHeight);
            sb.setBlendFunction(770, 1);
            FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, BattleStartEffect.ENEMY_TURN_MSG, WIDTH_DIV_2, HEIGHT_DIV_2, color, scale);
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose()
    {
    }

    private static final float DUR = 2F;
    private static final float HEIGHT_DIV_2;
    private static final float WIDTH_DIV_2;
    private Color bgColor;
    private static final float TARGET_HEIGHT;
    private static final float BG_RECT_EXPAND_SPEED = 3F;
    private float currentHeight;

    static 
    {
        HEIGHT_DIV_2 = (float)Settings.HEIGHT / 2.0F;
        WIDTH_DIV_2 = (float)Settings.WIDTH / 2.0F;
        TARGET_HEIGHT = 150F * Settings.scale;
    }
}
