package com.dovhan.netflix.service;

import com.dovhan.netflix.domain.NetflixUser;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link NetflixUser}.
 */
public interface NetflixUserService {

    /**
     * Save a netflixUser.
     *
     * @param netflixUser the entity to save.
     * @return the persisted entity.
     */
    NetflixUser save(NetflixUser netflixUser);

    /**
     * Get all the netflixUsers.
     *
     * @return the list of entities.
     */
    List<NetflixUser> findAll();

    /**
     * Get the "id" netflixUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NetflixUser> findOne(Long id);

    /**
     * Delete the "id" netflixUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
