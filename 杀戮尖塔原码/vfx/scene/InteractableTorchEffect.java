// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InteractableTorchEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.scene:
//            TorchParticleSEffect, LightFlareSEffect, TorchParticleMEffect, LightFlareMEffect, 
//            TorchParticleLEffect, LightFlareLEffect

public class InteractableTorchEffect extends AbstractGameEffect
{
    public static final class TorchSize extends Enum
    {

        public static TorchSize[] values()
        {
            return (TorchSize[])$VALUES.clone();
        }

        public static TorchSize valueOf(String name)
        {
            return (TorchSize)Enum.valueOf(com/megacrit/cardcrawl/vfx/scene/InteractableTorchEffect$TorchSize, name);
        }

        public static final TorchSize S;
        public static final TorchSize M;
        public static final TorchSize L;
        private static final TorchSize $VALUES[];

        static 
        {
            S = new TorchSize("S", 0);
            M = new TorchSize("M", 1);
            L = new TorchSize("L", 2);
            $VALUES = (new TorchSize[] {
                S, M, L
            });
        }

        private TorchSize(String s, int i)
        {
            super(s, i);
        }
    }


    public InteractableTorchEffect(float x, float y, TorchSize size)
    {
        activated = true;
        particleTimer1 = 0.0F;
        this.size = TorchSize.M;
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("env/torch");
        this.size = size;
        this.x = x;
        this.y = y;
        if(Settings.isLetterbox)
            this.y += Settings.LETTERBOX_OFFSET_Y;
        hb = new Hitbox(50F * Settings.scale, 60F * Settings.scale);
        hb.move(x, this.y);
        color = new Color(1.0F, 1.0F, 1.0F, 0.4F);
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$vfx$scene$InteractableTorchEffect$TorchSize[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$vfx$scene$InteractableTorchEffect$TorchSize = new int[TorchSize.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$scene$InteractableTorchEffect$TorchSize[TorchSize.S.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$scene$InteractableTorchEffect$TorchSize[TorchSize.M.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$vfx$scene$InteractableTorchEffect$TorchSize[TorchSize.L.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect.TorchSize[size.ordinal()])
        {
        case 1: // '\001'
            scale = Settings.scale * 0.6F;
            break;

        case 2: // '\002'
            scale = Settings.scale;
            break;

        case 3: // '\003'
            scale = Settings.scale * 1.4F;
            break;
        }
    }

    public InteractableTorchEffect(float x, float y)
    {
        this(x, y, TorchSize.M);
    }

    public void update()
    {
        hb.update();
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            activated = !activated;
            if(activated)
                CardCrawlGame.sound.playA("ATTACK_FIRE", 0.4F);
            else
                CardCrawlGame.sound.play("SCENE_TORCH_EXTINGUISH");
        }
        if(activated && !Settings.DISABLE_EFFECTS)
        {
            particleTimer1 -= Gdx.graphics.getDeltaTime();
            if(particleTimer1 < 0.0F)
            {
                particleTimer1 = 0.1F;
                switch(_cls1..SwitchMap.com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect.TorchSize[size.ordinal()])
                {
                case 1: // '\001'
                    AbstractDungeon.effectsQueue.add(new TorchParticleSEffect(x, y - 10F * Settings.scale));
                    AbstractDungeon.effectsQueue.add(new LightFlareSEffect(x, y - 10F * Settings.scale));
                    break;

                case 2: // '\002'
                    AbstractDungeon.effectsQueue.add(new TorchParticleMEffect(x, y));
                    AbstractDungeon.effectsQueue.add(new LightFlareMEffect(x, y));
                    break;

                case 3: // '\003'
                    AbstractDungeon.effectsQueue.add(new TorchParticleLEffect(x, y + 14F * Settings.scale));
                    AbstractDungeon.effectsQueue.add(new LightFlareLEffect(x, y + 14F * Settings.scale));
                    break;
                }
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        if(Settings.DISABLE_EFFECTS)
        {
            return;
        } else
        {
            sb.setColor(color);
            sb.draw(img, x - (float)(img.packedWidth / 2), y - (float)(img.packedHeight / 2) - 24F * Settings.yScale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
            hb.render(sb);
            return;
        }
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private Hitbox hb;
    private boolean activated;
    private float particleTimer1;
    private static final float PARTICLE_EMIT_INTERVAL = 0.1F;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private TorchSize size;
}
