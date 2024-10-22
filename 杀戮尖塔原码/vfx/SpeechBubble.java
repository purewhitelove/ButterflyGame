// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpeechBubble.java

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
//            AbstractGameEffect, SpeechTextEffect

public class SpeechBubble extends AbstractGameEffect
{

    public SpeechBubble(float x, float y, String msg, boolean isPlayer)
    {
        this(x, y, 2.0F, msg, isPlayer);
    }

    public SpeechBubble(float x, float y, float duration, String msg, boolean isPlayer)
    {
        shadow_offset = 0.0F;
        scaleTimer = 0.3F;
        shadowColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        float effect_x = -170F * Settings.scale;
        if(isPlayer)
            effect_x = 170F * Settings.scale;
        AbstractDungeon.effectsQueue.add(new SpeechTextEffect(x + effect_x, y + 124F * Settings.scale, duration, msg, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.BUMP_IN));
        if(isPlayer)
            this.x = x + ADJUST_X;
        else
            this.x = x - ADJUST_X;
        this.y = y + ADJUST_Y;
        color = new Color(0.8F, 0.9F, 0.9F, 0.0F);
        this.duration = duration;
        facingRight = !isPlayer;
    }

    public void update()
    {
        updateScale();
        wavyHelper += Gdx.graphics.getDeltaTime() * 4F;
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
            scale = Interpolation.swingIn.apply(Settings.scale * 1.15F, Settings.scale / 2.0F, scaleTimer / 0.3F);
        else
            scale = Interpolation.swingIn.apply(Settings.scale, Settings.scale / 2.0F, scaleTimer / 0.3F);
    }

    public void render(SpriteBatch sb)
    {
        shadowColor.a = color.a / 4F;
        sb.setColor(shadowColor);
        sb.draw(ImageMaster.SPEECH_BUBBLE_IMG, (x - 256F) + shadow_offset, ((y - 256F) + wavy_y) - shadow_offset, 256F, 256F, 512F, 512F, scale, scale, rotation, 0, 0, 512, 512, facingRight, false);
        sb.setColor(color);
        sb.draw(ImageMaster.SPEECH_BUBBLE_IMG, x - 256F, (y - 256F) + wavy_y, 256F, 256F, 512F, 512F, scale, scale, rotation, 0, 0, 512, 512, facingRight, false);
    }

    public void dispose()
    {
    }

    private static final int RAW_W = 512;
    private static final float SHADOW_OFFSET;
    private static final float WAVY_DISTANCE;
    private static final float ADJUST_X;
    private static final float ADJUST_Y;
    private static final float FADE_TIME = 0.3F;
    private float shadow_offset;
    private float x;
    private float y;
    private float wavy_y;
    private float wavyHelper;
    private float scaleTimer;
    private boolean facingRight;
    private Color shadowColor;

    static 
    {
        SHADOW_OFFSET = 16F * Settings.scale;
        WAVY_DISTANCE = 2.0F * Settings.scale;
        ADJUST_X = 170F * Settings.scale;
        ADJUST_Y = 116F * Settings.scale;
    }
}
