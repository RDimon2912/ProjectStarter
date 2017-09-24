package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findById(Long id);
    List<Project> findAllByUserId(Long userId);
    int countProjectByUser(User user);
    int countAllByUserId(long userId);
    List<Project> findAllByUserIdOrderByStartDateDesc(Long userId);

    @Query(value = "SELECT * FROM projects ORDER BY start_date DESC " +
            "LIMIT :offset, :limit", nativeQuery = true)
    List<Project> findAllOrderByStartDateDescLimitN(
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    @Query(value = "SELECT * FROM projects p WHERE p.status = :status ORDER BY end_date DESC LIMIT :limit",
            nativeQuery = true)
    List<Project> findAllByStatusNameOrderByEndDateDescLimitN(@Param("status") String statusName,
                                                              @Param("limit") int limit);
}
