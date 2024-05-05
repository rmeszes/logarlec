package com.redvas.app.ui.players;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Professor;
import com.redvas.app.ui.players.listeners.PlayerChangeListener;
import com.redvas.app.ui.players.listeners.ProfessorChangeListener;

public class ProfessorView implements PlayerChangeListener, ProfessorChangeListener {
    private final Professor professor;
    public ProfessorView(Professor professor) {
        this.professor = professor;
    }

    @Override
    public void positionChanged(Room from, Room to) {

    }

    @Override
    public void faintedChanged(boolean isFainted) {

    }

    @Override
    public void paralyzedChanged(boolean isParalyzed) {

    }
}
