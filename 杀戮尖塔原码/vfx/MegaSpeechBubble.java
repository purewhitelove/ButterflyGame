// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MegaSpeechBubble.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.DialogWord;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, MegaDialogTextEffect

public class MegaSpeechBubble extends AbstractGameEffect
{

    public MegaSpeechBubble(float x, float y, String msg, boolean isPlayer)
    {
        this(x, y, 2.0F, msg, isPlayer);
    }

    public MegaSpeechBubble(float x, float y, float duration, String msg, boolean isPlayer)
    {
        shadow_offset = 0.0F;
        scaleTimer = 0.3F;
        float effect_x = -170F * Settings.scale;
        if(isPlayer)
            effect_x = 170F * Settings.scale;
        AbstractDungeon.effectsQueue.add(new MegaDialogTextEffect(x + effect_x, y + 124F * Settings.scale, duration, msg, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.BUMP_IN));
        if(isPlayer)
            this.x = x + ADJUST_X;
        else
            this.x = x - ADJUST_X;
        this.y = y + ADJUST_Y;
        scale_x = Settings.scale * 0.7F;
        scale_y = Settings.scale * 0.7F;
        scaleTimer = 0.3F;
        color = new Color(0.8F, 0.9F, 0.9F, 0.0F);
        this.duration = duration;
        facingRight = !isPlayer;
    }

    public void update()
    {
        updateScale();
        wavyHelper += Gdx.graphics.getDeltaTime() * 5F;
        wavy_y = MathUtils.sin(wavyHelper) * WAVY_DISTANCE;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        if(duration > 0.3F)
            color.a = MathUtils.lerp(color.a, 1.0F, Gdx.graphics.getDeltaTime() * 12F);
        else
            color.a = MathUtils.lerp(color.a, 0.0F, Gdx.graphics.getDeltaTime() * 12F);
        shadow_offset = MathUtils.lerp(shadow_offset, SHADOW_OFFSET, Gdx.graphics.getDeltaTime() * 4F);
    }

    private void updateScale()
    {
        scaleTimer -= Gdx.graphics.getDeltaTime();
        if(scaleTimer < 0.0F)
            scaleTimer = 0.0F;
        if(Settings.isMobile)
        {
            scale_x = Interpolation.swingIn.apply(scale_x * 1.15F, Settings.scale, scaleTimer / 0.3F);
            scale_y = Interpolation.swingIn.apply(scale_y * 1.15F, Settings.scale, scaleTimer / 0.3F);
        } else
        {
            scale_x = Interpolation.swingIn.apply(scale_x, Settings.scale, scaleTimer / 0.3F);
            scale_y = Interpolation.swingIn.apply(scale_y, Settings.scale, scaleTimer / 0.3F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, color.a / 4F));
        sb.draw(ImageMaster.SPEECH_BUBBLE_IMG, (x - 256F) + shadow_offset, (y - 256F - shadow_offset) + wavy_y, 256F, 256F, 512F, 512F, scale_x, scale_y, rotation, 0, 0, 512, 512, facingRight, false);
        sb.setColor(color);
        sb.draw(ImageMaster.SPEECH_BUBBLE_IMG, x - 256F, (y - 256F) + wavy_y, 256F, 256F, 512F, 512F, scale_x, scale_y, rotation, 0, 0, 512, 512, facingRight, false);
    }

    public void dispose()
    {
    }

    private static final int RAW_W = 512;
    private float shadow_offset;
    private static final float SHADOW_OFFSET;
    private float x;
    private float y;
    private float scale_x;
    private float scale_y;
    private float wavy_y;
    private float wavyHelper;
    private static final float WAVY_DISTANCE;
    private static final float SCALE_TIME = 0.3F;
    private float scaleTimer;
    private static final float ADJUST_X;
    private static final float ADJUST_Y;
    private boolean facingRight;
    private static final float DEFAULT_DURATION = 2F;
    private static final float FADE_TIME = 0.3F;

    static 
    {
        SHADOW_OFFSET = 16F * Settings.scale;
        WAVY_DISTANCE = 2.0F * Settings.scale;
        ADJUST_X = 170F * Settings.scale;
        ADJUST_Y = 116F * Settings.scale;
    }
}
