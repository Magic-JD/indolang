package main.rest.updater;

import main.rest.updater.data.Definition;

public interface DatabaseUpdater {
    void updateDatabase(Definition definition, String language);
}
