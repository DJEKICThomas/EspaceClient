package be.depairon.espaceclient.eglobal.utils;


import java.util.Vector;

public class Sec_Alfa_Num {
    private int rtn = 0;
    private String Msg = "Nomsg ";
    private StringBuffer Num;
    private int nTbl = 0;
    private String aTbl = " ";
    private String Alf = "NoAlf";
    private int Randnum = 0;

    public void Sec_Alfa() {
        int lngt = 0;
        Msg = " ";
        Num = new StringBuffer();
        rtn = 0;
        String[] cNum;
        cNum = new String[10];
        if (Alf == null) {
            rtn = 1;
            Msg = "Null ";
            return;
        }

        Alf = STR.Complete(Alf, 10);
        if (Alf.substring(0, 1).trim().equals("")) {
            rtn = 2;
            Msg = ("Invalid " + Alf);
            return;
        }
        Randnum = (int) (Math.random() * 10);

        Sec_Alfa_Tabels Tbl = new Sec_Alfa_Tabels();
        Tbl.Set_Tabelnum(Randnum);
        Tbl.Set_Tables();

        String Alfchar = Tbl.GetAlfa();
        nTbl = Randnum;


        StringBuffer Alf1 = new StringBuffer(Alfchar.length());
        Alf1.append(Alfchar);
        lngt = Alf1.length();

        String b = "";

        // ArrayList<String> Allcharlist = new ArrayList<String>();
        Vector<String> Allcharlist = new Vector<String>();
        for (int i = 0; i < lngt; i++) {
            b = Alfchar.substring(i, i + 1);
            Allcharlist.add(b);
        }
        for (int i = 0; i < 10; i++) {
            int idx = Allcharlist.indexOf(Alf.substring(i, i + 1));
            if (idx < 0) {
                rtn = 3;
                Msg = ("Invalid character " + Alf.substring(i, i + 1));
                return;
            }
            int nMuw = Tbl.GetMuw(i);
            int nNuw = Tbl.GetNuw(i);
            int Znum = idx + nMuw + nTbl;
            int q = nNuw - 1;
            cNum[q] = STR.getFormattedNumber(Znum, 2);

        } // end For

        for (int i = 0; i < 10; i++) {
            Num.append(cNum[i]);
            aTbl = STR.getFormattedNumber(nTbl, 1);
        }


    }

    public void SetAlf(String zn10A) {
        Alf = zn10A;
    }

    public int GetRtn() {
        return rtn;
    }

    public String GetMsg() {
        return Msg;
    }

    public String GetNum() {
        return Num.toString();
    }

    public String GetnTbl() {
        return aTbl;
    }
}
	

