package be.depairon.espaceclient.eglobal.utils;

public class Sec_Usr_Psw_Dcript {
    private int rtn = 0;
    private String Iusrpsw = null;
    private String Msg = null;
    private String Usr = null;
    private String Psw = null;
    private String Ctime = " ";

    public void Sec_Dcript() {
        int lngt = 0;
        int Tim = 0;
        Sec_Num_Alfa Snum1 = new Sec_Num_Alfa();
        Sec_Num_Alfa Snum2 = new Sec_Num_Alfa();
        Sec_Num_Alfa Snum3 = new Sec_Num_Alfa();
        if (Iusrpsw == null) {
            rtn = 1;
            Msg = STR.Complete("ERR00015", 80);
            return;
        }
        Iusrpsw = Iusrpsw.trim();
        if (Iusrpsw == null) {
            rtn = 1;
            Msg = STR.Complete("ERR00015", 80);
            return;
        }
        StringBuffer Alf1 = new StringBuffer(Iusrpsw.length());
        Alf1.append(Iusrpsw);
        lngt = Alf1.length();
        if (lngt != 63) {
            rtn = 2;
            Msg = STR.Complete("ERR00015", 80);
            return;

        }

        // decript User
        String Zn20a = Iusrpsw.substring(0, 20);
        String Zn1a = Iusrpsw.substring(20, 21);
        Snum1.SetNum(Zn20a);
        Snum1.SetTbl(Zn1a);
        Snum1.Sec_Num();
        if (Snum1.AlfGetRtn() > 0) {
            rtn = Snum1.AlfGetRtn();
            Msg = Snum1.AlfGetMsg();
            return;
        }
        Usr = Snum1.AlfGet();
        // decript Pasw
        Zn20a = Iusrpsw.substring(21, 41);
        Zn1a = Iusrpsw.substring(41, 42);
        Snum2.SetNum(Zn20a);
        Snum2.SetTbl(Zn1a);
        Snum2.Sec_Num();
        if (Snum2.AlfGetRtn() > 0) {
            rtn = Snum2.AlfGetRtn();
            Msg = Snum2.AlfGetMsg();
            return;
        }
        Psw = Snum2.AlfGet();
        // decript Tim
        Zn20a = Iusrpsw.substring(42, 62);
        Zn1a = Iusrpsw.substring(62, 63);
        Snum3.SetNum(Zn20a);
        Snum3.SetTbl(Zn1a);
        Snum3.Sec_Num();
        if (Snum3.AlfGetRtn() > 0) {
            rtn = Snum3.AlfGetRtn();
            Msg = Snum3.AlfGetMsg();
            return;
        }
        if (Ctime.equals("n")) {
            return;
        }

        String a = Snum3.AlfGet();
        try {
            Tim = Integer.parseInt(a.substring(0, 6));
        } catch (NumberFormatException ex) {
            rtn = 1;
            Msg = STR.Complete("ERR00015", 80);
            return;
        }
        Serv_CCYYMMDD_HMS Ntime = new Serv_CCYYMMDD_HMS();
        Ntime.Calc_CCYYMMDD_HMS();
        int z = 0;
        int wTim = 0;
        String wHMS = STR.getNumber(Ntime.Get_HMS(), 6);
        for (int i = 0; i < 6 && rtn == 0; ) {
            a = wHMS.substring(i, i + 2);
            try {
                lngt = Integer.parseInt(a);
            } catch (NumberFormatException ex) {
                rtn = 1;
                Msg = STR.Complete("ERR00015", 80);
                return;
            }
            if (i < 4) {
                z += lngt;
                z *= 60;
            } else {

                wTim = z + lngt;
            }
            i += 2;
        }
        int Utim = wTim + 600;
        int Ltim = wTim - 600;
        if (Tim > Utim || Tim < Ltim) {
            rtn = 1;
            Msg = STR.Complete("ERR00015", 80);
            return;
        }
    }

    public void Set_iUsrPswTim(String Pusrpswtim) {
        Iusrpsw = Pusrpswtim;
    }

    public void Set_iUsrPswTimChk(String Pusrpswtimchk) {
        Ctime = Pusrpswtimchk;
    }

    public int GetRtn() {
        return rtn;
    }

    public String GetMsg() {
        return Msg;
    }

    public String GetUsr() {
        return Usr;
    }

    public String GetPsw() {
        return Psw;
    }

}
