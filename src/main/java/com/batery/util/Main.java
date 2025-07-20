package com.batery.util;

interface GenericEntity {
    default String collectionName() {
        return getClass().getSimpleName();
    }
}

interface GenericRequest<T extends GenericEntity> extends GenericEntity {
    T toEntity();
}

class Entity1 implements GenericEntity {
    private int id;

    public Entity1(int id) {
        this.id = id;
    }
}
class Request1 implements GenericRequest<Entity1> {

    @Override
    public Entity1 toEntity() {
        return null;
    }
}


public class TestEntity {
}
