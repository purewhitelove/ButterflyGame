// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameCursor.java

package com.megacrit.cardcrawl.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

// Referenced classes of package com.megacrit.cardcrawl.core:
//            Settings

public class GameCursor
{
    public static final class CursorType extends Enum
    {

        public static CursorType[] values()
        {
            return (CursorType[])$VALUES.clone();
        }

        public static CursorType valueOf(String name)
        {
            return (CursorType)Enum.valueOf(com/megacrit/cardcrawl/core/GameCursor$CursorType, name);
        }

        public static final CursorType NORMAL;
        public static final CursorType INSPECT;
        private static final CursorType $VALUES[];

        static 
        {
            NORMAL = new CursorType("NORMAL", 0);
            INSPECT = new CursorType("INSPECT", 1);
            $VALUES = (new CursorType[] {
                NORMAL, INSPECT
            });
        }

        private CursorType(String s, int i)
        {
            super(s, i);
        }
    }


    public GameCursor()
    {
        type = CursorType.NORMAL;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        img = ImageMaster.loadImage("images/ui/cursors/gold2.png");
        mImg = ImageMaster.loadImage("images/ui/cursors/magGlass2.png");
    }

    public void update()
    {
        if(InputHelper.isMouseDown)
            rotation = 6F;
        else
            rotation = 0.0F;
    }

    public void changeType(CursorType type)
    {
        this.type = type;
    }

    public void render(SpriteBatch sb)
    {
        if(hidden || Settings.isControllerMode)
            return;
        if(!Settings.isTouchScreen || Settings.isDev)
        {
            static class _cls1
            {

                static final int $SwitchMap$com$megacrit$cardcrawl$core$GameCursor$CursorType[];

                static 
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$GameCursor$CursorType = new int[CursorType.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$GameCursor$CursorType[CursorType.NORMAL.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$GameCursor$CursorType[CursorType.INSPECT.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror1) { }
                }
            }

            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.core.GameCursor.CursorType[type.ordinal()])
            {
            case 1: // '\001'
                sb.setColor(SHADOW_COLOR);
                sb.draw(img, ((float)InputHelper.mX - 32F - SHADOW_OFFSET_X) + OFFSET_X, ((float)InputHelper.mY - 32F - SHADOW_OFFSET_Y) + OFFSET_Y, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, rotation, 0, 0, 64, 64, false, false);
                sb.setColor(Color.WHITE);
                sb.draw(img, ((float)InputHelper.mX - 32F) + OFFSET_X, ((float)InputHelper.mY - 32F) + OFFSET_Y, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, rotation, 0, 0, 64, 64, false, false);
                break;

            case 2: // '\002'
                sb.setColor(SHADOW_COLOR);
                sb.draw(mImg, ((float)InputHelper.mX - 32F - SHADOW_OFFSET_X) + OFFSET_X, ((float)InputHelper.mY - 32F - SHADOW_OFFSET_Y) + OFFSET_Y, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, rotation, 0, 0, 64, 64, false, false);
                sb.setColor(Color.WHITE);
                sb.draw(mImg, ((float)InputHelper.mX - 32F) + OFFSET_X, ((float)InputHelper.mY - 32F) + OFFSET_Y, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, rotation, 0, 0, 64, 64, false, false);
                break;
            }
            changeType(CursorType.NORMAL);
        } else
        {
            color.a = MathHelper.slowColorLerpSnap(color.a, 0.0F);
            sb.setColor(color);
            sb.draw(ImageMaster.WOBBLY_ORB_VFX, (((float)InputHelper.mX - 16F) + OFFSET_X) - 24F * Settings.scale, ((float)InputHelper.mY - 16F) + OFFSET_Y + 24F * Settings.scale, 16F, 16F, 32F, 32F, Settings.scale, Settings.scale, rotation, 0, 0, 32, 32, false, false);
        }
    }

    private Texture img;
    private Texture mImg;
    public static final int W = 64;
    public static boolean hidden = false;
    private float rotation;
    private static final float OFFSET_X;
    private static final float OFFSET_Y;
    private static final float SHADOW_OFFSET_X;
    private static final float SHADOW_OFFSET_Y;
    private static final Color SHADOW_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.15F);
    private static final float TILT_ANGLE = 6F;
    private CursorType type;
    public Color color;

    static 
    {
        OFFSET_X = 24F * Settings.scale;
        OFFSET_Y = -24F * Settings.scale;
        SHADOW_OFFSET_X = -10F * Settings.scale;
        SHADOW_OFFSET_Y = 8F * Settings.scale;
    }
}
