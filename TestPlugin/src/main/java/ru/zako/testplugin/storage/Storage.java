package ru.zako.testplugin.storage;

import ru.zako.testplugin.User;

public interface Storage {
    User getUser(String name);
    void setUserAge(String name, int age);
    void unload();
}
