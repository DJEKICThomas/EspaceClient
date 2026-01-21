package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BARCODESTOPENC", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class BarcodeStopEnc {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String ncs;
  private String nda;
  private String dot;
  private long porteurid;
  private int arm;
  private int cass;
  private String stop;
  private String rem;
  private String perm;
  private int dtb;
  private int dtf;
  private int dtr;
  private String ce;
  private String prod;
  private String nat;
  private String usc;

}
