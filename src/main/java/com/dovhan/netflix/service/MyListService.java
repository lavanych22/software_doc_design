package com.dovhan.netflix.service;

import com.dovhan.netflix.domain.MyList;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MyList}.
 */
public interface MyListService {

    /**
     * Save a myList.
     *
     * @param myList the entity to save.
     * @return the persisted entity.
     */
    MyList save(MyList myList);

    /**
     * Get all the myLists.
     *
     * @return the list of entities.
     */
    List<MyList> findAll();
    /**
     * Get all the MyListDTO where NetflixUser is {@code null}.
     *
     * @return the list of entities.
     */
    List<MyList> findAllWhereNetflixUserIsNull();

    /**
     * Get the "id" myList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MyList> findOne(Long id);

    /**
     * Delete the "id" myList.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
