package be.depairon.espaceclient.eglobal.utils;

import java.util.ArrayList;

public class Sec_Num_Alfa {
    private int rtn = 0;
    private String Num = " ";
    private StringBuffer Alf;
    private String Msg = " ";
    private String nTbl = " ";

    public void Sec_Num() {
        Msg = " ";
        Alf = new StringBuffer();
        rtn = 0;
        int Numtbl = 0;
        int lngt = 0;
        int[] iNum;
        int[] iMuw;
        int[] iNuw;
        String[] aAlf;
        int NumNum = 0;
        ArrayList<String> Allcharlist = new ArrayList<String>();
        iNum = new int[10];
        iMuw = new int[10];
        iNuw = new int[10];
        aAlf = new String[10];
        if (Num == null) {
            rtn = 1;
            Msg = "Null ";
            return;
        }
        StringBuffer Alf1 = new StringBuffer(Num.length());
        Alf1.append(Num);
        lngt = Alf1.length();

        if (lngt != 20) {
            rtn = 2;
            Msg = STR.Complete("ERR00009", 80);
            return;
        }

        int z = 0;
        for (int i = 0; i < 20 && rtn == 0; ) {
            String a = Num.substring(i, i + 2);
            try {
                NumNum = Integer.parseInt(a);
            } catch (NumberFormatException ex) {
                rtn = 3;
                Msg = STR.Complete("ERR00009", 80);
                return;
            }
            iNum[z] = NumNum;
            z++;
            i = i + 2;
        }


        try {
            Numtbl = Integer.parseInt(nTbl);
        } catch (NumberFormatException ex) {
            rtn = 4;
            Msg = STR.Complete("ERR00009", 80);
            return;
        }

        if (Numtbl > 9) {
            rtn = 5;
            Msg = STR.Complete("ERR00009", 80);
            return;
        }

        Sec_Alfa_Tabels Tbl = new Sec_Alfa_Tabels();
        Tbl.Set_Tabelnum(Numtbl);
        Tbl.Set_Tables();

        String Alfchar = Tbl.GetAlfa();
        StringBuffer Alf2 = new StringBuffer(Alfchar.length());
        Alf2.append(Alfchar);
        lngt = Alf2.length();

        String b = "";


        for (int i = 0; i < lngt; i++) {
            b = Alfchar.substring(i, i + 1);
            Allcharlist.add(b);
        }
        for (int i = 0; i < 10; i++) {

            int nMuw = Tbl.GetMuw(i);
            iMuw[i] = nMuw;
            int nNuw = Tbl.GetNuw(i);
            iNuw[i] = nNuw - 1;
        }


        for (int i = 0; i < 10 && rtn == 0; i++) {
            int q = iNuw[i];
            int wNum = iNum[q];
            int idx = wNum - iMuw[i] - Numtbl;

            if (idx < 0 || idx > lngt - 1) {
                rtn = 6;
                Msg = STR.Complete("ERR00009", 80);
                return;
            }

            String a = (String) Allcharlist.get(idx);
            aAlf[i] = a;
        }

        for (int i = 0; i < 10; i++) {
            Alf.append(aAlf[i]);
        }
    }

    public void SetNum(String zn20A) {
        Num = zn20A;
    }

    public void SetTbl(String aTbl) {
        nTbl = aTbl;
    }

    public int AlfGetRtn() {
        return rtn;
    }

    public String AlfGetMsg() {
        return Msg;
    }

    public String AlfGet() {
        return Alf.toString();
    }

}
	
