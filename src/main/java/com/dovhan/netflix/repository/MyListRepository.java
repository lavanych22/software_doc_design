package com.dovhan.netflix.repository;

import com.dovhan.netflix.domain.MyList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MyList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyListRepository extends JpaRepository<MyList, Long> {
}
