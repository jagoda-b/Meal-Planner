package mealplanner.DAO;

import java.util.List;

public interface DAO<T> {

    T get(int id);
    List<T> getAll();
    int insert(T t);
    int update(T t);
    int delete(T t);
}