package ru.sherb.Snake.util;

import java.io.IOException;

/**
 * Created by sherb on 04.11.2016.
 */
@FunctionalInterface
public interface Task {
    void performTask() throws Exception;
}
