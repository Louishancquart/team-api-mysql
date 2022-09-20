package com.football.team.nice.repository;

import com.football.team.nice.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teams", path = "teams")
public interface TeamRepository extends JpaRepository<Team, Long> {

//    List<Passenger> findByLastName(String lastName, Sort sort);



}
