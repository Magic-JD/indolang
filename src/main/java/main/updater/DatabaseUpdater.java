package main.updater;

import main.updater.data.Definition;

public interface DatabaseUpdater {
    void updateDatabase(Definition definition, String language);
}
