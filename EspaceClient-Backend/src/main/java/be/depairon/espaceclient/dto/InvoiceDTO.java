package be.depairon.espaceclient.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceDTO {
    private long id;
    private String rec;
    private String type;
    private String ncs;
    private String ccyy;
    private BigDecimal per;
    private String impDate;
    private LocalDate docDate;
    private String jnl;
    private String num;
    private String relation;
    private BigDecimal valCurD;
    private BigDecimal valCurC;
    private String cur;
    private String des;
    private LocalDate dueDate;
    private String let;
    private boolean paid;

    public InvoiceDTO() {}

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

    public LocalDate getDocDate() { return docDate; }
    public void setDocDate(LocalDate docDate) { this.docDate = docDate; }

    public String getJnl() { return jnl; }
    public void setJnl(String jnl) { this.jnl = jnl; }

    public String getNum() { return num; }
    public void setNum(String num) { this.num = num; }

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

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getLet() { return let; }
    public void setLet(String let) { this.let = let; }

    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
}