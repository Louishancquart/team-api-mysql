package com.football.team.nice.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.football.team.nice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
  List<Player> findByTeamId(Long postId);
  
  @Transactional
  void deleteByTeamId(long teamId);
}
