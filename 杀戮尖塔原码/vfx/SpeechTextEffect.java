// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpeechTextEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.ui.SpeechWord;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class SpeechTextEffect extends AbstractGameEffect
{

    public SpeechTextEffect(float x, float y, float duration, String msg, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect a_effect)
    {
        wordTimer = 0.0F;
        textDone = false;
        words = new ArrayList();
        curLine = 0;
        curLineWidth = 0.0F;
        if(gl == null)
            gl = new GlyphLayout();
        this.duration = duration;
        this.x = x;
        this.y = y;
        font = FontHelper.turnNumFont;
        this.a_effect = a_effect;
        s = new Scanner(msg);
    }

    public void update()
    {
        wordTimer -= Gdx.graphics.getDeltaTime();
        if(wordTimer < 0.0F && !textDone)
            if(Settings.lineBreakViaCharacter)
                addWordCN();
            else
                addWord();
        SpeechWord w;
        for(Iterator iterator = words.iterator(); iterator.hasNext(); w.update())
            w = (SpeechWord)iterator.next();

        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            words.clear();
            isDone = true;
        }
        if(duration < 0.3F)
        {
            SpeechWord w;
            for(Iterator iterator1 = words.iterator(); iterator1.hasNext(); w.fadeOut())
                w = (SpeechWord)iterator1.next();

        }
    }

    private void addWord()
    {
        wordTimer = 0.03F;
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
            com.megacrit.cardcrawl.ui.DialogWord.WordColor color = SpeechWord.identifyWordColor(word);
            if(color != com.megacrit.cardcrawl.ui.DialogWord.WordColor.DEFAULT)
                word = word.substring(2, word.length());
            com.megacrit.cardcrawl.ui.DialogWord.WordEffect effect = SpeechWord.identifyWordEffect(word);
            if(effect != com.megacrit.cardcrawl.ui.DialogWord.WordEffect.NONE)
                word = word.substring(1, word.length() - 1);
            gl.setText(font, word);
            float temp = 0.0F;
            if(curLineWidth + gl.width > DEFAULT_WIDTH)
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
            words.add(new SpeechWord(font, word, a_effect, effect, color, x + temp, y - LINE_SPACING * (float)curLine, curLine));
        } else
        {
            textDone = true;
            s.close();
        }
    }

    private void addWordCN()
    {
        wordTimer = 0.03F;
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
            com.megacrit.cardcrawl.ui.DialogWord.WordColor color = SpeechWord.identifyWordColor(word);
            if(color != com.megacrit.cardcrawl.ui.DialogWord.WordColor.DEFAULT)
                word = word.substring(2, word.length());
            com.megacrit.cardcrawl.ui.DialogWord.WordEffect effect = SpeechWord.identifyWordEffect(word);
            if(effect != com.megacrit.cardcrawl.ui.DialogWord.WordEffect.NONE)
                word = word.substring(1, word.length() - 1);
            for(int i = 0; i < word.length(); i++)
            {
                String tmp = Character.toString(word.charAt(i));
                gl.setText(font, tmp);
                float temp = 0.0F;
                if(curLineWidth + gl.width > DEFAULT_WIDTH)
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
                words.add(new SpeechWord(font, tmp, a_effect, effect, color, x + temp, y - LINE_SPACING * (float)curLine, curLine));
            }

        } else
        {
            textDone = true;
            s.close();
        }
    }

    public void render(SpriteBatch sb)
    {
        SpeechWord w;
        for(Iterator iterator = words.iterator(); iterator.hasNext(); w.render(sb))
            w = (SpeechWord)iterator.next();

    }

    public void dispose()
    {
    }

    private static GlyphLayout gl;
    private BitmapFont font;
    private com.megacrit.cardcrawl.ui.DialogWord.AppearEffect a_effect;
    private static final float DEFAULT_WIDTH;
    private static final float LINE_SPACING;
    private static final float CHAR_SPACING;
    private static final float WORD_TIME = 0.03F;
    private float wordTimer;
    private boolean textDone;
    private float x;
    private float y;
    private ArrayList words;
    private int curLine;
    private Scanner s;
    private float curLineWidth;
    private static final float FADE_TIME = 0.3F;

    static 
    {
        DEFAULT_WIDTH = 280F * Settings.scale;
        LINE_SPACING = Settings.isMobile ? 18F * Settings.scale : 15F * Settings.scale;
        CHAR_SPACING = 8F * Settings.scale;
    }
}
