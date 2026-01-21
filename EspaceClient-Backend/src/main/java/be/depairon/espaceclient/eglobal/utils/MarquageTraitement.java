package be.depairon.espaceclient.eglobal.utils;


public class MarquageTraitement {


    private Long id;
    private String com = "";
    private String dep = "";
    private String ncs = "";
    private int nda = 0;
    private int bcb = 0;
    private int iin = 0;
    private String porteur = "";
    private int armoire = 0;
    private int cass = 0;
    private long barcode = 0;
    private int iinFacMarquage = 0;
    private int iinFacEntretient = 0;
    private int iinFacPersCB = 0;
    private int iinFacDepers = 0;
    private int iinFacLongeur = 0;
    private int dtc = 0;
    private int tmc = 0;
    private int statut = 0;
    private String usc = "";
    private String type = "";
    private int couleurCout = 0;
    private int longeur = 0;
    private Long porteurID;


    public static String getBarcodeDigit(String sBarcode) {
        int z = 0;
        try {
            z = Integer.parseInt(sBarcode.substring(0, 1))
                    + Integer.parseInt(sBarcode.substring(2, 3))
                    + Integer.parseInt(sBarcode.substring(4, 5))
                    + Integer.parseInt(sBarcode.substring(6, 7));
        } catch (NumberFormatException ignored) {
        }
        int y = 0;
        try {
            y = Integer.parseInt(sBarcode.substring(1, 2))
                    + Integer.parseInt(sBarcode.substring(3, 4))
                    + Integer.parseInt(sBarcode.substring(5, 6));
        } catch (NumberFormatException ignored) {
        }
        z = (z * 3) + y;
        String temp = String.valueOf(z);
        return String.valueOf(z).substring(temp.length() - 1, temp.length());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getNcs() {
        return ncs;
    }

    public void setNcs(String ncs) {
        this.ncs = ncs;
    }

    public int getNda() {
        return nda;
    }

    public void setNda(int nda) {
        this.nda = nda;
    }

    public int getBcb() {
        return bcb;
    }

    public void setBcb(int bcb) {
        this.bcb = bcb;
    }

    public int getIin() {
        return iin;
    }

    public void setIin(int iin) {
        this.iin = iin;
    }

    public String getPorteur() {
        return porteur;
    }

    public void setPorteur(String porteur) {
        this.porteur = porteur;
    }

    public int getArmoire() {
        return armoire;
    }

    public void setArmoire(int armoire) {
        this.armoire = armoire;
    }

    public int getCass() {
        return cass;
    }

    public void setCass(int cass) {
        this.cass = cass;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public int getIinFacMarquage() {
        return iinFacMarquage;
    }

    public void setIinFacMarquage(int iinFacMarquage) {
        this.iinFacMarquage = iinFacMarquage;
    }

    public int getIinFacEntretient() {
        return iinFacEntretient;
    }

    public void setIinFacEntretient(int iinFacEntretient) {
        this.iinFacEntretient = iinFacEntretient;
    }

    public int getIinFacPersCB() {
        return iinFacPersCB;
    }

    public void setIinFacPersCB(int iinFacPersCB) {
        this.iinFacPersCB = iinFacPersCB;
    }

    public int getIinFacDepers() {
        return iinFacDepers;
    }

    public void setIinFacDepers(int iinFacDepers) {
        this.iinFacDepers = iinFacDepers;
    }

    public int getIinFacLongeur() {
        return iinFacLongeur;
    }

    public void setIinFacLongeur(int iinFacLongeur) {
        this.iinFacLongeur = iinFacLongeur;
    }

    public int getDtc() {
        return dtc;
    }

    public void setDtc(int dtc) {
        this.dtc = dtc;
    }

    public int getTmc() {
        return tmc;
    }

    public void setTmc(int tmc) {
        this.tmc = tmc;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public String getUsc() {
        return usc;
    }

    public void setUsc(String usc) {
        this.usc = usc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCouleurCout() {
        return couleurCout;
    }

    public void setCouleurCout(int couleurCout) {
        this.couleurCout = couleurCout;
    }

    public int getLongeur() {
        return longeur;
    }

    public void setLongeur(int longeur) {
        this.longeur = longeur;
    }

    public Long getPorteurID() {
        return porteurID;
    }

    public void setPorteurID(Long porteurID) {
        this.porteurID = porteurID;
    }
}
