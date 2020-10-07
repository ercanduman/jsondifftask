package ercanduman.jsondifftask.data.dao;

import ercanduman.jsondifftask.data.entity.JsonObject;
import ercanduman.jsondifftask.data.enums.Side;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Data access object fake implementation
 * <p>
 * This object overrides all {@link JsonObjectDao} interface pattern methods and handles invalid Side enum inputs
 */
@Repository
public class FakeJsonObjectDaoImpl implements JsonObjectDao {
    private HashMap<String, JsonObject> leftObjects = new HashMap<>();
    private HashMap<String, JsonObject> rightObjects = new HashMap<>();

    @Override
    public void insertLeft(JsonObject object) {
        if (isLeftObject(object)) leftObjects.put(object.getId(), object);
        else {
            throw new IllegalArgumentException("Invalid side found as: " + object.getSide() + ". Side can be only be " + Side.LEFT + " for /left endpoint.");
        }
    }

    @Override
    public void insertRight(JsonObject object) {
        if (isRightObject(object)) rightObjects.put(object.getId(), object);
        else {
            throw new IllegalArgumentException("Invalid side found as: " + object.getSide() + ". Side can be only be " + Side.RIGHT + " for /right endpoint.");
        }
    }

    @Override
    public void update(JsonObject object) {
        if (isRightObject(object)) {
            if (!rightObjects.containsKey(object.getId())) {
                throw new IllegalArgumentException(object.getId() + " is not found!");
            } else rightObjects.replace(object.getId(), object);
        } else if (isLeftObject(object)) {
            if (!leftObjects.containsKey(object.getId())) {
                throw new IllegalArgumentException(object.getId() + " is not found!");
            } else leftObjects.replace(object.getId(), object);
        } else {
            throw new IllegalArgumentException("Invalid side found as: " + object.getSide() + ". Side can be only one of " + Side.toStringList());
        }
    }

    @Override
    public List<JsonObject> getObjects(String id) {
        List<JsonObject> jsonObjects = new ArrayList<>();
        jsonObjects.add(rightObjects.get(id));
        jsonObjects.add(leftObjects.get(id));
        return jsonObjects;
    }

    @Override
    public JsonObject getLeftObject(String id) {
        return leftObjects.get(id);
    }

    @Override
    public JsonObject getRightObject(String id) {
        return rightObjects.get(id);
    }

    private boolean isRightObject(JsonObject object) {
        return Side.RIGHT.equals(object.getSide());
    }

    private boolean isLeftObject(JsonObject object) {
        return Side.LEFT.equals(object.getSide());
    }
}