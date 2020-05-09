package com.dovhan.netflix.web.rest;

import com.dovhan.netflix.domain.MyList;
import com.dovhan.netflix.service.MyListService;
import com.dovhan.netflix.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.dovhan.netflix.domain.MyList}.
 */
@RestController
@RequestMapping("/api")
public class MyListResource {

    private final Logger log = LoggerFactory.getLogger(MyListResource.class);

    private static final String ENTITY_NAME = "myList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyListService myListService;

    public MyListResource(MyListService myListService) {
        this.myListService = myListService;
    }

    /**
     * {@code POST  /my-lists} : Create a new myList.
     *
     * @param myList the myList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myList, or with status {@code 400 (Bad Request)} if the myList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/my-lists")
    public ResponseEntity<MyList> createMyList(@RequestBody MyList myList) throws URISyntaxException {
        log.debug("REST request to save MyList : {}", myList);
        if (myList.getId() != null) {
            throw new BadRequestAlertException("A new myList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyList result = myListService.save(myList);
        return ResponseEntity.created(new URI("/api/my-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /my-lists} : Updates an existing myList.
     *
     * @param myList the myList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myList,
     * or with status {@code 400 (Bad Request)} if the myList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/my-lists")
    public ResponseEntity<MyList> updateMyList(@RequestBody MyList myList) throws URISyntaxException {
        log.debug("REST request to update MyList : {}", myList);
        if (myList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MyList result = myListService.save(myList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, myList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /my-lists} : get all the myLists.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myLists in body.
     */
    @GetMapping("/my-lists")
    public List<MyList> getAllMyLists(@RequestParam(required = false) String filter) {
        if ("netflixuser-is-null".equals(filter)) {
            log.debug("REST request to get all MyLists where netflixUser is null");
            return myListService.findAllWhereNetflixUserIsNull();
        }
        log.debug("REST request to get all MyLists");
        return myListService.findAll();
    }

    /**
     * {@code GET  /my-lists/:id} : get the "id" myList.
     *
     * @param id the id of the myList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/my-lists/{id}")
    public ResponseEntity<MyList> getMyList(@PathVariable Long id) {
        log.debug("REST request to get MyList : {}", id);
        Optional<MyList> myList = myListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(myList);
    }

    /**
     * {@code DELETE  /my-lists/:id} : delete the "id" myList.
     *
     * @param id the id of the myList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/my-lists/{id}")
    public ResponseEntity<Void> deleteMyList(@PathVariable Long id) {
        log.debug("REST request to delete MyList : {}", id);
        myListService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
