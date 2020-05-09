package com.dovhan.netflix.web.rest;

import com.dovhan.netflix.domain.NetflixUser;
import com.dovhan.netflix.service.NetflixUserService;
import com.dovhan.netflix.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dovhan.netflix.domain.NetflixUser}.
 */
@RestController
@RequestMapping("/api")
public class NetflixUserResource {

    private final Logger log = LoggerFactory.getLogger(NetflixUserResource.class);

    private static final String ENTITY_NAME = "netflixUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NetflixUserService netflixUserService;

    public NetflixUserResource(NetflixUserService netflixUserService) {
        this.netflixUserService = netflixUserService;
    }

    /**
     * {@code POST  /netflix-users} : Create a new netflixUser.
     *
     * @param netflixUser the netflixUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new netflixUser, or with status {@code 400 (Bad Request)} if the netflixUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/netflix-users")
    public ResponseEntity<NetflixUser> createNetflixUser(@Valid @RequestBody NetflixUser netflixUser) throws URISyntaxException {
        log.debug("REST request to save NetflixUser : {}", netflixUser);
        if (netflixUser.getId() != null) {
            throw new BadRequestAlertException("A new netflixUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetflixUser result = netflixUserService.save(netflixUser);
        return ResponseEntity.created(new URI("/api/netflix-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /netflix-users} : Updates an existing netflixUser.
     *
     * @param netflixUser the netflixUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated netflixUser,
     * or with status {@code 400 (Bad Request)} if the netflixUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the netflixUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/netflix-users")
    public ResponseEntity<NetflixUser> updateNetflixUser(@Valid @RequestBody NetflixUser netflixUser) throws URISyntaxException {
        log.debug("REST request to update NetflixUser : {}", netflixUser);
        if (netflixUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NetflixUser result = netflixUserService.save(netflixUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, netflixUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /netflix-users} : get all the netflixUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of netflixUsers in body.
     */
    @GetMapping("/netflix-users")
    public List<NetflixUser> getAllNetflixUsers() {
        log.debug("REST request to get all NetflixUsers");
        return netflixUserService.findAll();
    }

    /**
     * {@code GET  /netflix-users/:id} : get the "id" netflixUser.
     *
     * @param id the id of the netflixUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the netflixUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/netflix-users/{id}")
    public ResponseEntity<NetflixUser> getNetflixUser(@PathVariable Long id) {
        log.debug("REST request to get NetflixUser : {}", id);
        Optional<NetflixUser> netflixUser = netflixUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(netflixUser);
    }

    /**
     * {@code DELETE  /netflix-users/:id} : delete the "id" netflixUser.
     *
     * @param id the id of the netflixUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/netflix-users/{id}")
    public ResponseEntity<Void> deleteNetflixUser(@PathVariable Long id) {
        log.debug("REST request to delete NetflixUser : {}", id);
        netflixUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
