package com.hat.hereandthere.tourservice.domains.majorregion.entity;

import com.hat.hereandthere.tourservice.domains.sigungu.entity.Sigungu;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Entity(name = "major_region")
@Getter
@ToString
public class MajorRegion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column(columnDefinition = "TEXT")
  private String imageUrl;

  @OneToMany(mappedBy = "majorRegion")
  private List<Sigungu> sigungus;

}
