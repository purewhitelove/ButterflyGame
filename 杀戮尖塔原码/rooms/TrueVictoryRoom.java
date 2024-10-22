// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TrueVictoryRoom.java

package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            AbstractRoom

public class TrueVictoryRoom extends AbstractRoom
{

    public TrueVictoryRoom()
    {
        phase = AbstractRoom.RoomPhase.INCOMPLETE;
        cutscene = new Cutscene(AbstractDungeon.player.chosenClass);
        AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
    }

    public void onPlayerEntry()
    {
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        GameCursor.hidden = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NO_INTERACT;
    }

    public void update()
    {
        super.update();
        cutscene.update();
    }

    public void render(SpriteBatch sb)
    {
        super.render(sb);
        cutscene.render(sb);
    }

    public void renderAboveTopPanel(SpriteBatch sb)
    {
        super.renderAboveTopPanel(sb);
        cutscene.renderAbove(sb);
    }

    public void dispose()
    {
        super.dispose();
        cutscene.dispose();
    }

    public Cutscene cutscene;
}
