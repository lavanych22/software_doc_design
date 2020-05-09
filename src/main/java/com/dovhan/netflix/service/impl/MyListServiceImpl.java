package com.dovhan.netflix.service.impl;

import com.dovhan.netflix.service.MyListService;
import com.dovhan.netflix.domain.MyList;
import com.dovhan.netflix.repository.MyListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link MyList}.
 */
@Service
@Transactional
public class MyListServiceImpl implements MyListService {

    private final Logger log = LoggerFactory.getLogger(MyListServiceImpl.class);

    private final MyListRepository myListRepository;

    public MyListServiceImpl(MyListRepository myListRepository) {
        this.myListRepository = myListRepository;
    }

    /**
     * Save a myList.
     *
     * @param myList the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MyList save(MyList myList) {
        log.debug("Request to save MyList : {}", myList);
        return myListRepository.save(myList);
    }

    /**
     * Get all the myLists.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MyList> findAll() {
        log.debug("Request to get all MyLists");
        return myListRepository.findAll();
    }


    /**
     *  Get all the myLists where NetflixUser is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<MyList> findAllWhereNetflixUserIsNull() {
        log.debug("Request to get all myLists where NetflixUser is null");
        return StreamSupport
            .stream(myListRepository.findAll().spliterator(), false)
            .filter(myList -> myList.getNetflixUser() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one myList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MyList> findOne(Long id) {
        log.debug("Request to get MyList : {}", id);
        return myListRepository.findById(id);
    }

    /**
     * Delete the myList by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyList : {}", id);
        myListRepository.deleteById(id);
    }
}
