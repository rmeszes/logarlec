package com.redvas.app.ui.players;

import com.redvas.app.players.Professor;
import com.redvas.app.ui.players.listeners.PlayerChangeListener;
import com.redvas.app.ui.players.listeners.ProfessorChangeListener;

public class ProfessorView extends PlayerView implements PlayerChangeListener, ProfessorChangeListener {
    private final Professor professor;

    public ProfessorView(Professor professor) {
        super(professor);
        this.professor = professor;
        this.myImage = professorImage;
        professor.setListener((ProfessorChangeListener) this);
    }

    @Override
    public void paralyzedChanged(boolean isParalyzed) {
        if (isParalyzed) myImage = professorParalyzedImage;
        else myImage = professorImage;
        repaint();
    }

    @Override
    public void faintedChanged(boolean isFainted) {
        if (isFainted)
            myImage = professorParalyzedImage;
        else
            myImage = professorImage;
        repaint();
    }
}
