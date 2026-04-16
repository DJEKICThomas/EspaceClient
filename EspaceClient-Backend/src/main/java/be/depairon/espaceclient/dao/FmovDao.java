package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.Fmov;

import java.util.List;

public interface FmovDao {
    List<Fmov> findInvoicesByNcs(String ncs, String company);
    List<Fmov> findUnpaidInvoicesByNcs (String ncs);
    List<Fmov> findDetailsByInvoiceNum (String num);
    List<Fmov> findInvoicesByNcsAndDateRange(String ncs, int from, int to, String company);
}
