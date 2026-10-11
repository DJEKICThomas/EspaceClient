package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.Fmov;

import java.util.List;

public interface FmovDao {
    List<Fmov> findInvoicesByNcs(String ncs);
    List<Fmov> findUnpaidInvoicesByNcs (String ncs);
    List<Fmov> findDetailsByInvoiceNum (String num);
    List<Fmov> findInvoicesByNcsAndDateRange(String ncs, int from, int to);
}
