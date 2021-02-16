package ua.hospital.servletapp.model.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
	T create (T entity);
    Optional<T> findById(int id);
    List<T> findAll();
    boolean update(T entity);
    boolean delete(int id);
    void close();
}
