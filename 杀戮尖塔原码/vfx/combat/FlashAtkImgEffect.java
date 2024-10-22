// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlashAtkImgEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FlashAtkImgEffect extends AbstractGameEffect
{

    public FlashAtkImgEffect(float x, float y, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect, boolean mute)
    {
        triggered = false;
        duration = 0.6F;
        startingDuration = 0.6F;
        this.effect = effect;
        img = loadImage();
        if(img != null)
        {
            this.x = x - (float)img.packedWidth / 2.0F;
            y -= (float)img.packedHeight / 2.0F;
        }
        color = Color.WHITE.cpy();
        scale = Settings.scale;
        if(!mute)
            playSound(effect);
        this.y = y;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect = new int[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SHIELD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_VERTICAL.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$actions$AbstractGameAction$AttackEffect[com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[effect.ordinal()])
        {
        case 1: // '\001'
            this.y = y + 80F * Settings.scale;
            sY = this.y;
            tY = y;
            break;

        default:
            this.y = y;
            sY = y;
            tY = y;
            break;
        }
    }

    public FlashAtkImgEffect(float x, float y, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        this(x, y, effect, false);
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion loadImage()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[effect.ordinal()])
        {
        case 2: // '\002'
            return ImageMaster.ATK_SLASH_D;

        case 3: // '\003'
            return ImageMaster.ATK_SLASH_HEAVY;

        case 4: // '\004'
            return ImageMaster.ATK_SLASH_H;

        case 5: // '\005'
            return ImageMaster.ATK_SLASH_V;

        case 6: // '\006'
            return ImageMaster.ATK_BLUNT_LIGHT;

        case 7: // '\007'
            rotation = MathUtils.random(360F);
            return ImageMaster.ATK_BLUNT_HEAVY;

        case 8: // '\b'
            return ImageMaster.ATK_FIRE;

        case 9: // '\t'
            return ImageMaster.ATK_POISON;

        case 1: // '\001'
            return ImageMaster.ATK_SHIELD;

        case 10: // '\n'
            return null;
        }
        return ImageMaster.ATK_SLASH_D;
    }

    private void playSound(com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[effect.ordinal()])
        {
        case 3: // '\003'
            CardCrawlGame.sound.play("ATTACK_HEAVY");
            break;

        case 6: // '\006'
            CardCrawlGame.sound.play("BLUNT_FAST");
            break;

        case 7: // '\007'
            CardCrawlGame.sound.play("BLUNT_HEAVY");
            break;

        case 8: // '\b'
            CardCrawlGame.sound.play("ATTACK_FIRE");
            break;

        case 9: // '\t'
            CardCrawlGame.sound.play("ATTACK_POISON");
            break;

        case 1: // '\001'
            playBlockSound();
            break;

        case 2: // '\002'
        case 4: // '\004'
        case 5: // '\005'
        default:
            CardCrawlGame.sound.play("ATTACK_FAST");
            break;

        case 10: // '\n'
            break;
        }
    }

    private void playBlockSound()
    {
        if(blockSound == 0)
            CardCrawlGame.sound.play("BLOCK_GAIN_1");
        else
        if(blockSound == 1)
            CardCrawlGame.sound.play("BLOCK_GAIN_2");
        else
            CardCrawlGame.sound.play("BLOCK_GAIN_3");
        blockSound++;
        if(blockSound > 2)
            blockSound = 0;
    }

    public void update()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[effect.ordinal()])
        {
        case 1: // '\001'
            duration -= Gdx.graphics.getDeltaTime();
            if(duration < 0.0F)
            {
                isDone = true;
                color.a = 0.0F;
            } else
            if(duration < 0.2F)
                color.a = duration * 5F;
            else
                color.a = Interpolation.fade.apply(1.0F, 0.0F, (duration * 0.75F) / 0.6F);
            y = Interpolation.exp10In.apply(tY, sY, duration / 0.6F);
            if(duration < 0.4F && !triggered)
                triggered = true;
            break;

        default:
            super.update();
            break;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(img != null)
        {
            sb.setColor(color);
            sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        }
    }

    public void dispose()
    {
    }

    private static int blockSound = 0;
    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float sY;
    private float tY;
    private static final float DURATION = 0.6F;
    private com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect;
    private boolean triggered;

}
