package com.redvas.app.items;

public class WetWipe extends Item {
    /** gives protection from profs
     *
     */
    @Override
    public void use() {
        logger.fine(this + " is being used...");
        owner().where().paralyzeProfessors();
        destroy();
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Wet Wipe";
    }
}
