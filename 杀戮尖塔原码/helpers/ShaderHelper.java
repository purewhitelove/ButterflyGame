// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShaderHelper.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderHelper
{
    public static final class Shader extends Enum
    {

        public static Shader[] values()
        {
            return (Shader[])$VALUES.clone();
        }

        public static Shader valueOf(String name)
        {
            return (Shader)Enum.valueOf(com/megacrit/cardcrawl/helpers/ShaderHelper$Shader, name);
        }

        public static final Shader BLUR;
        public static final Shader DEFAULT;
        public static final Shader GRAYSCALE;
        public static final Shader RED_SILHOUETTE;
        public static final Shader WHITE_SILHOUETTE;
        public static final Shader OUTLINE;
        public static final Shader WATER;
        private static final Shader $VALUES[];

        static 
        {
            BLUR = new Shader("BLUR", 0);
            DEFAULT = new Shader("DEFAULT", 1);
            GRAYSCALE = new Shader("GRAYSCALE", 2);
            RED_SILHOUETTE = new Shader("RED_SILHOUETTE", 3);
            WHITE_SILHOUETTE = new Shader("WHITE_SILHOUETTE", 4);
            OUTLINE = new Shader("OUTLINE", 5);
            WATER = new Shader("WATER", 6);
            $VALUES = (new Shader[] {
                BLUR, DEFAULT, GRAYSCALE, RED_SILHOUETTE, WHITE_SILHOUETTE, OUTLINE, WATER
            });
        }

        private Shader(String s, int i)
        {
            super(s, i);
        }
    }


    public ShaderHelper()
    {
    }

    public static void initializeShaders()
    {
        ShaderProgram.pedantic = false;
        gsShader = new ShaderProgram(Gdx.files.internal("shaders/grayscale/vertexShader.vs").readString(), Gdx.files.internal("shaders/grayscale/fragShader.fs").readString());
    }

    public static void setShader(SpriteBatch sb, Shader shader)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$helpers$ShaderHelper$Shader[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$helpers$ShaderHelper$Shader = new int[Shader.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ShaderHelper$Shader[Shader.BLUR.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ShaderHelper$Shader[Shader.DEFAULT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ShaderHelper$Shader[Shader.GRAYSCALE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ShaderHelper$Shader[Shader.OUTLINE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ShaderHelper$Shader[Shader.RED_SILHOUETTE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ShaderHelper$Shader[Shader.WATER.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$ShaderHelper$Shader[Shader.WHITE_SILHOUETTE.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.helpers.ShaderHelper.Shader[shader.ordinal()])
        {
        case 1: // '\001'
            sb.end();
            sb.setShader(blurShader);
            sb.begin();
            break;

        case 2: // '\002'
            sb.end();
            sb.setShader(null);
            sb.begin();
            break;

        case 3: // '\003'
            sb.end();
            sb.setShader(gsShader);
            sb.begin();
            break;

        case 4: // '\004'
            sb.end();
            sb.setShader(outlineShader);
            sb.begin();
            break;

        case 5: // '\005'
            sb.end();
            sb.setShader(rsShader);
            sb.begin();
            break;

        case 6: // '\006'
            sb.end();
            sb.setShader(waterShader);
            sb.begin();
            break;

        case 7: // '\007'
            sb.end();
            sb.setShader(wsShader);
            sb.begin();
            break;

        default:
            sb.end();
            sb.setShader(null);
            sb.begin();
            break;
        }
    }

    public static void setShader(PolygonSpriteBatch sb, Shader shader)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.helpers.ShaderHelper.Shader[shader.ordinal()])
        {
        case 1: // '\001'
            sb.setShader(blurShader);
            break;

        case 2: // '\002'
            sb.setShader(null);
            break;

        case 3: // '\003'
            sb.setShader(gsShader);
            break;

        default:
            sb.setShader(null);
            break;
        }
    }

    private static ShaderProgram gsShader;
    private static ShaderProgram rsShader;
    private static ShaderProgram wsShader;
    private static ShaderProgram blurShader;
    private static ShaderProgram waterShader;
    private static ShaderProgram outlineShader;
}
