package com.football.team.nice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_generator")
  private long id;


  @NotNull(message = "Name cannot be null")
  private String name;

  @NotNull(message = "acronym cannot be null")
  private String acronym;

  @NotNull(message = "Please specify a budget for the team")
  private BigDecimal budget; // currency storage => bigDecimal for precision

  @OneToMany( targetEntity=Player.class, mappedBy="team" , cascade = CascadeType.ALL)
  private List<Player> players = new ArrayList<>();


  public Team() {

  }

  public Team(String name, String acronym, BigDecimal budget) {
    this.name = name;
    this.acronym = acronym;
    this.budget = budget;
  }

  public Team(String name, String acronym, BigDecimal budget, List<Player> players) {
    this.name = name;
    this.acronym = acronym;
    this.budget = budget;
    this.players = players;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAcronym() {
    return acronym;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }



  public List<Player> getPlayers() {
    return players;
  }

  @Override
  public String toString() {
    return "Team{" +
            "name='" + name + '\'' +
            ", acronym='" + acronym + '\'' +
            ", budget=" + budget +
            '}';
  }
}
