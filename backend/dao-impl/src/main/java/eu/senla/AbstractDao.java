package eu.senla;

import eu.senla.dto.PaginationDTO;
import eu.senla.dto.SortDto;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Log4j2
public abstract class AbstractDao<T, PK extends Serializable> implements GenericDao<T, PK> {
    private Class<T> clazz;
    public static String errorParameter;

    @PersistenceContext
    EntityManager entityManager;

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findOne(PK id) {
        T result = null;
        try {
            result = entityManager.find(clazz, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public List<T> findAll(PaginationDTO paginationDTO) {
        List<T> result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
            Root<T> root = query.from(clazz);
            query.select(root);
            result = sortByParametersAndPagination(query, paginationDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public List<T> findAllById(String column, PK id, PaginationDTO paginationDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get(column), id));
        return sortByParametersAndPagination(query, paginationDTO);
    }

    public T save(T entity) {
        T result = null;
        try {
            result = entityManager.merge(entity);
            entityManager.persist(result);
            entityManager.close();
            return result;
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return result;
    }

    public T update(T entity) {
        T result = null;
        try {
            result = (T) entityManager.merge(entity);
            entityManager.close();
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public void deleteById(PK entityId) {
        try {
            T entity = findOne(entityId);
            entityManager.remove(entity);
            entityManager.flush();
            entityManager.clear();
            entityManager.close();


        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(T entity) {
        try {
            entity = entityManager.merge(entity);
            entityManager.remove(entity);
            entityManager.flush();
            entityManager.clear();
            entityManager.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            String className = clazz.getName().split("\\.")[2].toLowerCase();
            entityManager.createQuery("delete " + className + " from " + clazz.getName() + " where " + 1)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public T findByParameter(String parameter, Attribute<?, ?> joinAttribute, Object value) {
        try {
            CriteriaQuery<T> criteriaQuery = searchByCriteria(parameter, joinAttribute, value);
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public List<T> findListByParameter(String parameter, Attribute<?, ?> joinAttribute, Object value, PaginationDTO paginationDTO) {
        try {
            CriteriaQuery<T> criteriaQuery = searchByCriteria(parameter, joinAttribute, value);
            return sortByParametersAndPagination(criteriaQuery, paginationDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<T> findEntryByParameter(String parameter, Object value) {
        TypedQuery<T> typedQuery = entityManager.createQuery(getQueryForEntryParameter(parameter, value));
        return typedQuery.getResultList();
    }

    public List<T> findEntryByParameter(String parameter, Object value, PaginationDTO paginationDTO) {
        return sortByParametersAndPagination(getQueryForEntryParameter(parameter, value), paginationDTO);
    }

    private CriteriaQuery<T> getQueryForEntryParameter(String parameter, Object value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        Predicate condition = criteriaBuilder.like(root.get(parameter), "%" + value + "%");
        query.where(condition);
        query.select(root);
        return query;
    }

    private CriteriaQuery<T> searchByCriteria(String parameter, Attribute<?, ?> attribute, Object value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        if (attribute != null) {
            Join<?, ?> join = root.join(attribute.getName());
            query.where(criteriaBuilder.equal(join.get(parameter), value));
        } else {
            query.where(criteriaBuilder.equal(root.get(parameter), value));
        }
        return query;
    }


    private List<T> sortByParametersAndPagination(CriteriaQuery<T> query, final PaginationDTO paginationDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        Root<T> root = (Root<T>) query.getRoots().iterator().next();
        try {
            List<SortDto> sortParameters = paginationDTO.getSortParameters();
            if (CollectionUtils.isNotEmpty(sortParameters)) {
                for (SortDto sortDto : sortParameters) {
                    errorParameter = sortDto.getSortParameter();
                    switch (sortDto.getSortDirection()) {
                        case ASC:
                            query.orderBy(criteriaBuilder.asc(root.get(sortDto.getSortParameter())));
                            break;
                        case DESC:
                            query.orderBy(criteriaBuilder.desc(root.get(sortDto.getSortParameter())));
                            break;
                        default:
                            query.orderBy(criteriaBuilder.asc(root.get("id")));
                    }
                }
            }
            query.select(root);
            TypedQuery<T> typedQuery = entityManager
                    .createQuery(query);
            if (paginationDTO.getPageSize() == -1) {
                return typedQuery.getResultList();
            }
            return typedQuery
                    .setFirstResult(paginationDTO.getElementNumber())
                    .setFirstResult(paginationDTO.getPageSize()).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
