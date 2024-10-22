// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RoomEventDialog.java

package com.megacrit.cardcrawl.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import java.util.*;

public class RoomEventDialog
{

    public RoomEventDialog()
    {
        color = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        targetColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        isMoving = false;
        animateTimer = 0.0F;
        show = false;
        curLineWidth = 0.0F;
        curLine = 0;
        gl = new GlyphLayout();
        words = new ArrayList();
        textDone = true;
        wordTimer = 0.0F;
    }

    public void update()
    {
        if(isMoving)
        {
            animateTimer -= Gdx.graphics.getDeltaTime();
            if(animateTimer < 0.0F)
            {
                animateTimer = 0.0F;
                isMoving = false;
            }
        }
        color = color.lerp(targetColor, Gdx.graphics.getDeltaTime() * 8F);
        if(show)
        {
            for(int i = 0; i < optionList.size(); i++)
            {
                ((LargeDialogOptionButton)optionList.get(i)).update(optionList.size());
                if(((LargeDialogOptionButton)optionList.get(i)).pressed && waitForInput)
                {
                    selectedOption = i;
                    ((LargeDialogOptionButton)optionList.get(i)).pressed = false;
                    waitForInput = false;
                }
            }

        }
        if(Settings.lineBreakViaCharacter)
            bodyTextEffectCN();
        else
            bodyTextEffect();
        DialogWord w;
        for(Iterator iterator = words.iterator(); iterator.hasNext(); w.update())
            w = (DialogWord)iterator.next();

    }

    public int getSelectedOption()
    {
        waitForInput = true;
        return selectedOption;
    }

    public void clear()
    {
        optionList.clear();
        words.clear();
        waitForInput = true;
    }

    public void show()
    {
        targetColor = PANEL_COLOR;
        if(Settings.FAST_MODE)
            animateTimer = 0.125F;
        else
            animateTimer = 0.5F;
        show = true;
        isMoving = true;
    }

    public void show(String text)
    {
        updateBodyText(text);
        show();
    }

    public void hide()
    {
        targetColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        if(Settings.FAST_MODE)
            animateTimer = 0.125F;
        else
            animateTimer = 0.5F;
        show = false;
        isMoving = true;
        DialogWord w;
        for(Iterator iterator = words.iterator(); iterator.hasNext(); w.dialogFadeOut())
            w = (DialogWord)iterator.next();

        optionList.clear();
    }

    public void removeDialogOption(int slot)
    {
        optionList.remove(slot);
    }

    public void addDialogOption(String text)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text));
    }

    public void addDialogOption(String text, AbstractCard previewCard)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, previewCard));
    }

    public void addDialogOption(String text, AbstractRelic previewRelic)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, previewRelic));
    }

    public void addDialogOption(String text, AbstractCard previewCard, AbstractRelic previewRelic)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, previewCard, previewRelic));
    }

    public void addDialogOption(String text, boolean isDisabled)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, isDisabled));
    }

    public void addDialogOption(String text, boolean isDisabled, AbstractCard previewCard)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, isDisabled, previewCard));
    }

    public void addDialogOption(String text, boolean isDisabled, AbstractRelic previewRelic)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, isDisabled, previewRelic));
    }

    public void addDialogOption(String text, boolean isDisabled, AbstractCard previewCard, AbstractRelic previewRelic)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, isDisabled, previewCard, previewRelic));
    }

    public void updateDialogOption(int slot, String text)
    {
        optionList.set(slot, new LargeDialogOptionButton(slot, text));
    }

    public void updateBodyText(String text)
    {
        updateBodyText(text, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.BUMP_IN);
    }

    public void updateBodyText(String text, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect ae)
    {
        s = new Scanner(text);
        words.clear();
        textDone = false;
        a_effect = ae;
        curLineWidth = 0.0F;
        curLine = 0;
    }

    public void clearRemainingOptions()
    {
        for(int i = optionList.size() - 1; i > 0; i--)
            optionList.remove(i);

    }

    private void bodyTextEffectCN()
    {
        wordTimer -= Gdx.graphics.getDeltaTime();
        if(wordTimer < 0.0F && !textDone)
        {
            if(Settings.FAST_MODE)
                wordTimer = 0.005F;
            else
                wordTimer = 0.02F;
            if(s.hasNext())
            {
                String word = s.next();
                if(word.equals("NL"))
                {
                    curLine++;
                    curLineWidth = 0.0F;
                    return;
                }
                com.megacrit.cardcrawl.ui.DialogWord.WordColor color = DialogWord.identifyWordColor(word);
                if(color != com.megacrit.cardcrawl.ui.DialogWord.WordColor.DEFAULT)
                    word = word.substring(2, word.length());
                com.megacrit.cardcrawl.ui.DialogWord.WordEffect effect = DialogWord.identifyWordEffect(word);
                if(effect != com.megacrit.cardcrawl.ui.DialogWord.WordEffect.NONE)
                    word = word.substring(1, word.length() - 1);
                for(int i = 0; i < word.length(); i++)
                {
                    String tmp = Character.toString(word.charAt(i));
                    gl.setText(FontHelper.charDescFont, tmp);
                    if(curLineWidth + gl.width > DIALOG_MSG_W)
                    {
                        curLine++;
                        curLineWidth = gl.width;
                    } else
                    {
                        curLineWidth += gl.width;
                    }
                    words.add(new DialogWord(FontHelper.charDescFont, tmp, a_effect, effect, color, (DIALOG_MSG_X + curLineWidth) - gl.width, DIALOG_MSG_Y - LINE_SPACING * (float)curLine, curLine));
                    if(!show)
                        ((DialogWord)words.get(words.size() - 1)).dialogFadeOut();
                }

            } else
            {
                textDone = true;
                s.close();
            }
        }
    }

    private void bodyTextEffect()
    {
        wordTimer -= Gdx.graphics.getDeltaTime();
        if(wordTimer < 0.0F && !textDone)
        {
            if(Settings.FAST_MODE)
                wordTimer = 0.005F;
            else
                wordTimer = 0.02F;
            if(s.hasNext())
            {
                String word = s.next();
                if(word.equals("NL"))
                {
                    curLine++;
                    curLineWidth = 0.0F;
                    return;
                }
                com.megacrit.cardcrawl.ui.DialogWord.WordColor color = DialogWord.identifyWordColor(word);
                if(color != com.megacrit.cardcrawl.ui.DialogWord.WordColor.DEFAULT)
                    word = word.substring(2, word.length());
                com.megacrit.cardcrawl.ui.DialogWord.WordEffect effect = DialogWord.identifyWordEffect(word);
                if(effect != com.megacrit.cardcrawl.ui.DialogWord.WordEffect.NONE)
                    word = word.substring(1, word.length() - 1);
                gl.setText(FontHelper.charDescFont, word);
                if(curLineWidth + gl.width > DIALOG_MSG_W)
                {
                    curLine++;
                    curLineWidth = gl.width + CHAR_SPACING;
                } else
                {
                    curLineWidth += gl.width + CHAR_SPACING;
                }
                words.add(new DialogWord(FontHelper.charDescFont, word, a_effect, effect, color, (DIALOG_MSG_X + curLineWidth) - gl.width, DIALOG_MSG_Y - LINE_SPACING * (float)curLine, curLine));
                if(!show)
                    ((DialogWord)words.get(words.size() - 1)).dialogFadeOut();
            } else
            {
                textDone = true;
                s.close();
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        if(!AbstractDungeon.player.isDead)
        {
            DialogWord w;
            for(Iterator iterator = words.iterator(); iterator.hasNext(); w.render(sb, (float)Settings.HEIGHT - 525F * Settings.scale))
                w = (DialogWord)iterator.next();

            LargeDialogOptionButton b;
            for(Iterator iterator1 = optionList.iterator(); iterator1.hasNext(); b.render(sb))
                b = (LargeDialogOptionButton)iterator1.next();

            LargeDialogOptionButton b;
            for(Iterator iterator2 = optionList.iterator(); iterator2.hasNext(); b.renderCardPreview(sb))
                b = (LargeDialogOptionButton)iterator2.next();

            LargeDialogOptionButton b;
            for(Iterator iterator3 = optionList.iterator(); iterator3.hasNext(); b.renderRelicPreview(sb))
                b = (LargeDialogOptionButton)iterator3.next();

        }
    }

    private Color color;
    private Color targetColor;
    private static final Color PANEL_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.5F);
    private static final float COLOR_FADE_SPEED = 8F;
    private boolean isMoving;
    private float animateTimer;
    private static final float ANIM_SPEED = 0.5F;
    private boolean show;
    private float curLineWidth;
    private int curLine;
    private com.megacrit.cardcrawl.ui.DialogWord.AppearEffect a_effect;
    private Scanner s;
    private GlyphLayout gl;
    private ArrayList words;
    private boolean textDone;
    private float wordTimer;
    private static final float WORD_TIME = 0.02F;
    private static final float CHAR_SPACING;
    private static final float LINE_SPACING;
    private static final float DIALOG_MSG_X;
    private static final float DIALOG_MSG_Y;
    private static final float DIALOG_MSG_W;
    public static ArrayList optionList = new ArrayList();
    public static int selectedOption = -1;
    public static boolean waitForInput = true;

    static 
    {
        CHAR_SPACING = 8F * Settings.scale;
        LINE_SPACING = 34F * Settings.scale;
        DIALOG_MSG_X = (float)Settings.WIDTH * 0.1F;
        DIALOG_MSG_Y = 250F * Settings.scale;
        DIALOG_MSG_W = (float)Settings.WIDTH * 0.8F;
    }
}
