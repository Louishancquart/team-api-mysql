package com.football.team.nice;

import com.football.team.nice.repository.PlayerRepository;
import com.football.team.nice.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NiceTeamApi {

  public static void main(String[] args) {
    SpringApplication.run(NiceTeamApi.class, args);
  }
  
  @Autowired
  private TeamRepository teamRepository;
  
  @Autowired
  private PlayerRepository playerRepository;
  

//  @Bean
//  CommandLineRunner init() {
//    return args -> {
//
//      Stream.of("PSG", "OM", "LOSC", "LAMBERSART").forEach(acronym -> {
//        Team team = new Team(acronym + " team", acronym, new BigDecimal(acronym.length() * 10_000));
//        teamRepository.save(team);
//      });
//
//
//      List<Team> teamList = teamRepository.findAll();
//
//      Stream.of("Pogbah", "Mbape", "Messi", "Benzema").forEach(name -> {
//        Player player = new Player();
//        player.setName(name);
//        player.setPosition(Position.values()[name.length()%3].name());
//        int randomTeamNumber = (int) (Math.random() * (teamList.size()-1 ));
//        player.setTeam(teamList.get(randomTeamNumber));
//        playerRepository.save(player);
//      });
//      teamRepository.findAll().forEach(System.out::println);
//    };
//
//  }
}
