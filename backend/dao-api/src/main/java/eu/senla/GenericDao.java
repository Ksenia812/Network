package eu.senla;

import eu.senla.dto.PaginationDTO;
import eu.senla.dto.SortDto;

import javax.persistence.TypedQuery;
import javax.persistence.metamodel.Attribute;
import java.util.List;

public interface GenericDao<T, ID> {
    T findOne(ID id);

    T findByParameter(String parameter, Attribute<?, ?> joinAttribute, Object value);

    List<T> findListByParameter(String parameter, Attribute<?, ?> attribute, Object value,PaginationDTO paginationDTO);

    List<T> findAll(PaginationDTO paginationDTO);

    List<T> findAllById(String column, ID id,PaginationDTO paginationDTO);

    T save(T entity);

    void deleteById(ID id);

    void  delete(T entity);

    void deleteAll();

    T update(T entity);

    List<T> findEntryByParameter(String parameter, Object value);

    List<T> findEntryByParameter(String parameter, Object value, PaginationDTO paginationDTO);
}
