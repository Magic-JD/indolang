package main.rest.updater;

import main.rest.model.Definition;

public interface DatabaseUpdater {
    void updateDatabase(Definition definition, String language);

    void removeFromDatabase(Definition definition, String language);
}