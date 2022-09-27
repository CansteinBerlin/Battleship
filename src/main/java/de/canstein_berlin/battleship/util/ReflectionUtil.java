package main.java.de.canstein_berlin.battleship.util;

import org.reflections.Reflections;

import java.util.Set;

public class ReflectionUtil{

    public static <T> Set<Class<? extends T>> getAllClasses(String path, Class<T> type){
        Reflections reflections = new Reflections(path);
        return reflections.getSubTypesOf(type);
    }
}
