package main.rest.update;

import main.rest.model.Definition;

public interface DatabaseUpdater {
    void updateDatabase(String language, Definition definition);

    void removeFromDatabase(String language, Definition definition);
}
