package ru.sherb.Snake.model;


import java.util.HashMap;
import java.util.Map;

import static ru.sherb.Snake.model.Controllable.DOWN;
import static ru.sherb.Snake.model.Controllable.LEFT;
import static ru.sherb.Snake.model.Controllable.RIGHT;
import static ru.sherb.Snake.model.Controllable.UP;

/**
 * Created by sherb on 07.11.2016.
 */
public class MovementController {
    /**
     * Массив содержит код кнопок управления, каждая под индексом согласно константам {@link Controllable}
     */
    private final HashMap<Integer, Integer> keyControl = new HashMap<>(4);
    private final Controllable object;

    public MovementController(Controllable object, Map<Integer, Integer> controlKey) {
        this(object, controlKey.get(UP), controlKey.get(DOWN), controlKey.get(LEFT), controlKey.get(RIGHT));
    }

    private MovementController(Controllable object, int keyUp, int keyDown, int keyLeft, int keyRight) {
        this.object = object;
        keyControl.put(keyUp, UP);
        keyControl.put(keyDown, DOWN);
        keyControl.put(keyLeft, LEFT);
        keyControl.put(keyRight, RIGHT);
    }

    public void changeDirection(int key) {
        object.setDirect(keyControl.get(key));
    }

    public boolean containsKey(int key) {
        return keyControl.containsKey(key);
    }
}
