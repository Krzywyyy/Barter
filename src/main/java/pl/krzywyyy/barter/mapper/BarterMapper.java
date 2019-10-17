package pl.krzywyyy.barter.mapper;

import java.lang.reflect.Type;

public interface BarterMapper {
    <V> V map(Object object, Type clazz);
}
