package be.depairon.espaceclient.dto;

import java.math.BigDecimal;

public class InvoiceDTO {
    // Champs de base identifiés dans le code du collègue
    private long id;
    private String rec;
    private String type;
    private String ncs;
    private String ccyy; // Année
    private BigDecimal per; // Période
    private String impDate;
    private String docDate;
    private String jnl; // Journal
    private BigDecimal num; // Numéro de pièce
    private String relation;
    private BigDecimal valCurD; // Débit
    private BigDecimal valCurC; // Crédit
    private String cur; // Devise (Currency)
    private String des; // Description

    public InvoiceDTO() {}

    // Getters et Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getRec() { return rec; }
    public void setRec(String rec) { this.rec = rec; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNcs() { return ncs; }
    public void setNcs(String ncs) { this.ncs = ncs; }

    public String getCcyy() { return ccyy; }
    public void setCcyy(String ccyy) { this.ccyy = ccyy; }

    public BigDecimal getPer() { return per; }
    public void setPer(BigDecimal per) { this.per = per; }

    public String getImpDate() { return impDate; }
    public void setImpDate(String impDate) { this.impDate = impDate; }

    public String getDocDate() { return docDate; }
    public void setDocDate(String docDate) { this.docDate = docDate; }

    public String getJnl() { return jnl; }
    public void setJnl(String jnl) { this.jnl = jnl; }

    public BigDecimal getNum() { return num; }
    public void setNum(BigDecimal num) { this.num = num; }

    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }

    public BigDecimal getValCurD() { return valCurD; }
    public void setValCurD(BigDecimal valCurD) { this.valCurD = valCurD; }

    public BigDecimal getValCurC() { return valCurC; }
    public void setValCurC(BigDecimal valCurC) { this.valCurC = valCurC; }

    public String getCur() { return cur; }
    public void setCur(String cur) { this.cur = cur; }

    public String getDes() { return des; }
    public void setDes(String des) { this.des = des; }
}