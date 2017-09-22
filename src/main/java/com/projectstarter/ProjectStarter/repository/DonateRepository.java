package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Donate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DonateRepository extends JpaRepository<Donate, Long> {

    @Query(value = "SELECT * FROM donates ORDER BY amount DESC LIMIT :limit", nativeQuery = true)
    List<Donate> findAllOrderByAmountDescLimitN(@Param("limit") int limit);


    int countAllByProjectId(long projectId);
    int countAllByUserId(long userId);
}
