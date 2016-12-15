package ru.sherb.Snake.model;

/**
 * Created by sherb on 07.11.2016.
 */


import java.util.Map;
import java.util.HashMap;

import static ru.sherb.Snake.model.Controllable.*;

public class MovementController {
    /**
     * Массив содержит код кнопок управления, каждая под индексом согласно константам {@link Controllable}
     */
    private HashMap<Integer, Integer> keyControl = new HashMap<>(4);
    private Controllable object;

    public MovementController(Controllable object, Map<Integer, Integer> controlKey) {
        this(object, controlKey.get(UP), controlKey.get(DOWN), controlKey.get(LEFT), controlKey.get(RIGHT));
    }

    public MovementController(Controllable object, int keyUp, int keyDown, int keyLeft, int keyRight) {
        this.object = object;
        keyControl.put(keyUp, UP);
        keyControl.put(keyDown, DOWN);
        keyControl.put(keyLeft, LEFT);
        keyControl.put(keyRight, RIGHT);
    }

    public boolean changeDirection(int key) {
        object.setDirect(keyControl.get(key));
        return true;
    }

    public boolean containsKey(int key) {
        return keyControl.containsKey(key);
    }
}
