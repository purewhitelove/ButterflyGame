// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NeowNarrationScreen.java

package com.megacrit.cardcrawl.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.ui.SpeechWord;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.cutscenes:
//            NeowEye

public class NeowNarrationScreen
{

    public NeowNarrationScreen()
    {
        currentDialog = 0;
        clickCount = 0;
        font = FontHelper.panelNameFont;
        x = (float)Settings.WIDTH / 2.0F;
        y = (float)Settings.HEIGHT / 2.0F;
        wordTimer = 1.0F;
        textDone = false;
        fadingOut = false;
        fadeOutTimer = 3F;
    }

    public void open()
    {
        fadingOut = false;
        fadeOutTimer = 3F;
        playSfx();
        s = new Scanner(charStrings.TEXT[0]);
        textDone = false;
        wordTimer = 1.0F;
        words.clear();
        curLineWidth = 0.0F;
        curLine = 0;
        currentDialog = 0;
        clickCount = 0;
        eye1 = new NeowEye(0);
        eye2 = new NeowEye(1);
        eye3 = new NeowEye(2);
        bgColor = new Color(0x13151800);
        eyeColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    }

    public void update()
    {
        bgColor.a = MathHelper.slowColorLerpSnap(bgColor.a, 1.0F);
        eyeColor.a = bgColor.a;
        eye1.update();
        eye2.update();
        eye3.update();
        wordTimer -= Gdx.graphics.getDeltaTime();
        if(wordTimer < 0.0F && !textDone)
        {
            if(clickCount > 4)
                wordTimer = 0.1F;
            else
                wordTimer += 0.4F;
            if(Settings.lineBreakViaCharacter)
                addWordCN();
            else
                addWord();
        }
        SpeechWord w;
        for(Iterator iterator = words.iterator(); iterator.hasNext(); w.update())
            w = (SpeechWord)iterator.next();

        if(InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())
            clickCount++;
        if(fadingOut)
        {
            if(clickCount > 4)
                fadeOutTimer -= Gdx.graphics.getDeltaTime() * 3F;
            else
                fadeOutTimer -= Gdx.graphics.getDeltaTime();
            if(fadeOutTimer < 0.0F)
            {
                fadeOutTimer = 0.0F;
                exit();
                return;
            }
        } else
        if((InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed() || clickCount > 3) && textDone)
        {
            currentDialog++;
            if(currentDialog > 2)
            {
                fadingOut = true;
                return;
            }
            playSfx();
            s = new Scanner(charStrings.TEXT[currentDialog]);
            textDone = false;
            if(clickCount > 4)
                wordTimer = 0.1F;
            else
                wordTimer = 0.3F;
            words.clear();
            curLineWidth = 0.0F;
            curLine = 0;
        }
    }

    private void playSfx()
    {
        int roll = MathUtils.random(3);
        if(roll == 0)
        {
            CardCrawlGame.sound.playA("VO_NEOW_1A", -0.2F);
            CardCrawlGame.sound.playA("VO_NEOW_1A", -0.4F);
        } else
        if(roll == 1)
        {
            CardCrawlGame.sound.playA("VO_NEOW_1B", -0.2F);
            CardCrawlGame.sound.playA("VO_NEOW_1B", -0.4F);
        } else
        if(roll == 2)
        {
            CardCrawlGame.sound.playA("VO_NEOW_2A", -0.2F);
            CardCrawlGame.sound.playA("VO_NEOW_2A", -0.4F);
        } else
        {
            CardCrawlGame.sound.playA("VO_NEOW_2B", -0.2F);
            CardCrawlGame.sound.playA("VO_NEOW_2B", -0.4F);
        }
    }

    private void addWord()
    {
        if(s.hasNext())
        {
            String word = s.next();
            if(word.equals("NL"))
            {
                curLine++;
                SpeechWord w;
                for(Iterator iterator = words.iterator(); iterator.hasNext(); w.shiftY(LINE_SPACING))
                    w = (SpeechWord)iterator.next();

                curLineWidth = 0.0F;
                return;
            }
            gl.setText(font, word);
            float temp = 0.0F;
            if(curLineWidth + gl.width > 9999F)
            {
                curLine++;
                SpeechWord w;
                for(Iterator iterator1 = words.iterator(); iterator1.hasNext(); w.shiftY(LINE_SPACING))
                    w = (SpeechWord)iterator1.next();

                curLineWidth = gl.width + CHAR_SPACING;
                temp = -curLineWidth / 2.0F;
            } else
            {
                curLineWidth += gl.width;
                temp = -curLineWidth / 2.0F;
                Iterator iterator2 = words.iterator();
                do
                {
                    if(!iterator2.hasNext())
                        break;
                    SpeechWord w = (SpeechWord)iterator2.next();
                    if(w.line == curLine)
                    {
                        w.setX(x + temp);
                        gl.setText(font, w.word);
                        temp += gl.width + CHAR_SPACING;
                    }
                } while(true);
                curLineWidth += CHAR_SPACING;
                gl.setText(font, (new StringBuilder()).append(word).append(" ").toString());
            }
            words.add(new SpeechWord(font, word, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.FADE_IN, com.megacrit.cardcrawl.ui.DialogWord.WordEffect.SLOW_WAVY, com.megacrit.cardcrawl.ui.DialogWord.WordColor.WHITE, x + temp, y - LINE_SPACING * (float)curLine, curLine));
        } else
        {
            textDone = true;
            s.close();
        }
    }

    private void addWordCN()
    {
        if(s.hasNext())
        {
            String word = s.next();
            if(word.equals("NL"))
            {
                curLine++;
                SpeechWord w;
                for(Iterator iterator = words.iterator(); iterator.hasNext(); w.shiftY(LINE_SPACING))
                    w = (SpeechWord)iterator.next();

                curLineWidth = 0.0F;
                return;
            }
            for(int i = 0; i < word.length(); i++)
            {
                String tmp = Character.toString(word.charAt(i));
                gl.setText(font, tmp);
                float temp = 0.0F;
                if(curLineWidth + gl.width > 9999F)
                {
                    curLine++;
                    SpeechWord w;
                    for(Iterator iterator1 = words.iterator(); iterator1.hasNext(); w.shiftY(LINE_SPACING))
                        w = (SpeechWord)iterator1.next();

                    curLineWidth = gl.width;
                    temp = -curLineWidth / 2.0F;
                } else
                {
                    curLineWidth += gl.width;
                    temp = -curLineWidth / 2.0F;
                    Iterator iterator2 = words.iterator();
                    do
                    {
                        if(!iterator2.hasNext())
                            break;
                        SpeechWord w = (SpeechWord)iterator2.next();
                        if(w.line == curLine)
                        {
                            w.setX(x + temp);
                            gl.setText(font, w.word);
                            temp += gl.width;
                        }
                    } while(true);
                    gl.setText(font, (new StringBuilder()).append(tmp).append(" ").toString());
                }
                words.add(new SpeechWord(font, tmp, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.FADE_IN, com.megacrit.cardcrawl.ui.DialogWord.WordEffect.SLOW_WAVY, com.megacrit.cardcrawl.ui.DialogWord.WordColor.WHITE, x + temp, y - LINE_SPACING * (float)curLine, curLine));
            }

        } else
        {
            textDone = true;
            s.close();
        }
    }

    private void exit()
    {
        GameCursor.hidden = false;
        CardCrawlGame.mainMenuScreen.lighten();
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.MAIN_MENU;
        CardCrawlGame.music.changeBGM("MENU");
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(bgColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(eyeColor);
        eye1.renderRight(sb);
        eye1.renderLeft(sb);
        eye2.renderRight(sb);
        eye2.renderLeft(sb);
        eye3.renderRight(sb);
        eye3.renderLeft(sb);
        SpeechWord w;
        for(Iterator iterator = words.iterator(); iterator.hasNext(); w.render(sb))
            w = (SpeechWord)iterator.next();

        if(fadingOut)
        {
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, Interpolation.fade.apply(1.0F, 0.0F, fadeOutTimer / 3F)));
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    private Color bgColor;
    private Color eyeColor;
    private NeowEye eye1;
    private NeowEye eye2;
    private NeowEye eye3;
    private int currentDialog;
    private int clickCount;
    private static final CharacterStrings charStrings;
    private static float curLineWidth = 0.0F;
    private static int curLine = 0;
    private static Scanner s;
    private static GlyphLayout gl = new GlyphLayout();
    private static ArrayList words = new ArrayList();
    private static final float CHAR_SPACING;
    private static final float LINE_SPACING;
    private BitmapFont font;
    private float x;
    private float y;
    private float wordTimer;
    private boolean textDone;
    private boolean fadingOut;
    private float fadeOutTimer;

    static 
    {
        charStrings = CardCrawlGame.languagePack.getCharacterString("PostCreditsNeow");
        CHAR_SPACING = 8F * Settings.scale;
        LINE_SPACING = 38F * Settings.scale;
    }
}
