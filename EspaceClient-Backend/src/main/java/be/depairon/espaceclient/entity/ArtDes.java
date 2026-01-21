package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ART_DES", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class ArtDes implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private double artDesIin;
  private String artDesLng;
  private String artDesDes;
  private String artDesLde;
  private String artDesDcol;
  private String com;
  private String dep;
  private String artDesSta;
  private String artDesWeb;

}
