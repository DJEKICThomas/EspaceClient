package be.depairon.espaceclient.eglobal.utils;


public class Sec_Alfa_Tabels {
    private String Alfastring = "";
    private int nTbl = 0;
    private int[] Muw;
    private int[] Nuw;

    public void Set_Tables() {
        String[] Atab;
        Atab = new String[10];

        Atab[0] = "NM12D5678O0 Zjkl#-TRpqrUIAmno$EghiYdef9PQS3FG4stuHXxKvwWLJyCabczVB";
        Atab[1] = "Amno$EghiYdef9PQS3FG4stuHXxKvwWLJyCabczVBNM12D5678O0 Zjkl#-TRpqrUI";
        Atab[2] = "mnoUzIEY9PQSstuA$3yFG4abcHXKxWLJghiCvwVBNM12pqrD5678O0 Zjkl#-TRdef";
        Atab[3] = "9PUvwzIEstuYQSA$3pqrF8OG4HXKWghiLJCVBNM12Djklx50 Z#-TabcRdef67mnoy";
        Atab[4] = "F89PUvwIEYQSA$ Z#-TRstudzef67pqr3abcOxG4yHghiXKWLJCVjklBNM12Dmno50";
        Atab[5] = "abc$EmnoYTzRUjkxlI9PQyvwS3FG4stuHXpqrKWLJdefCVBNM12D5678Oghi0A Z#-";
        Atab[6] = "HxpqrXKWvwLJCVBjsztuklNabcM12A$EY9PQS3FdefG4mnoD5678O0 Z#-TRghiUyI";
        Atab[7] = "CVBNpqrM12DvwUIEY9yPQajklbcSA$3FG4HXKWLJ56mnxstuo78O0 Z#-TdzghiefR";
        Atab[8] = "G49PUIEabcYQzSA$3Fgvxwhpqri8OyHjklXKWLJCVBNM12Dstu50 Zmno#-TdefR67";
        Atab[9] = "LJCVBjklNv8wzxyF9PdefUstuIEYQSabcApqr$ Z#-TR673OG4HXKWM12D5gmnohi0";
        Alfastring = Atab[nTbl];
        Muw = new int[10];
        if (nTbl < 5) {
            Muw[0] = 16;
            Muw[1] = 12;
            Muw[2] = 8;
            Muw[3] = 13;
            Muw[4] = 10;
            Muw[5] = 11;
            Muw[6] = 2;
            Muw[7] = 4;
            Muw[8] = 14;
            Muw[9] = 9;
        } else {
            Muw[0] = 10;
            Muw[1] = 11;
            Muw[2] = 3;
            Muw[3] = 12;
            Muw[4] = 7;
            Muw[5] = 5;
            Muw[6] = 2;
            Muw[7] = 9;
            Muw[8] = 19;
            Muw[9] = 16;
        }
        Nuw = new int[10];
        if (nTbl == 0) {
            Nuw[0] = 1;
            Nuw[1] = 2;
            Nuw[2] = 3;
            Nuw[3] = 4;
            Nuw[4] = 5;
            Nuw[5] = 6;
            Nuw[6] = 7;
            Nuw[7] = 8;
            Nuw[8] = 9;
            Nuw[9] = 10;
        } else {
            if (nTbl == 1) {
                Nuw[0] = 3;
                Nuw[1] = 4;
                Nuw[2] = 1;
                Nuw[3] = 2;
                Nuw[4] = 7;
                Nuw[5] = 10;
                Nuw[6] = 5;
                Nuw[7] = 9;
                Nuw[8] = 8;
                Nuw[9] = 6;
            } else {
                if (nTbl == 2) {
                    Nuw[0] = 4;
                    Nuw[1] = 3;
                    Nuw[2] = 2;
                    Nuw[3] = 1;
                    Nuw[4] = 10;
                    Nuw[5] = 7;
                    Nuw[6] = 9;
                    Nuw[7] = 5;
                    Nuw[8] = 6;
                    Nuw[9] = 8;
                } else {
                    if (nTbl == 3) {
                        Nuw[0] = 4;
                        Nuw[1] = 3;
                        Nuw[2] = 2;
                        Nuw[3] = 1;
                        Nuw[4] = 10;
                        Nuw[5] = 7;
                        Nuw[6] = 9;
                        Nuw[7] = 5;
                        Nuw[8] = 6;
                        Nuw[9] = 8;

                    } else {
                        if (nTbl == 4) {
                            Nuw[0] = 10;
                            Nuw[1] = 3;
                            Nuw[2] = 2;
                            Nuw[3] = 1;
                            Nuw[4] = 4;
                            Nuw[5] = 7;
                            Nuw[6] = 9;
                            Nuw[7] = 8;
                            Nuw[8] = 6;
                            Nuw[9] = 5;
                        } else {
                            if (nTbl == 5) {
                                Nuw[0] = 10;
                                Nuw[1] = 6;
                                Nuw[2] = 2;
                                Nuw[3] = 1;
                                Nuw[4] = 4;
                                Nuw[5] = 8;
                                Nuw[6] = 9;
                                Nuw[7] = 5;
                                Nuw[8] = 3;
                                Nuw[9] = 7;
                            } else {
                                if (nTbl == 6) {
                                    Nuw[0] = 7;
                                    Nuw[1] = 10;
                                    Nuw[2] = 2;
                                    Nuw[3] = 1;
                                    Nuw[4] = 3;
                                    Nuw[5] = 4;
                                    Nuw[6] = 9;
                                    Nuw[7] = 8;
                                    Nuw[8] = 6;
                                    Nuw[9] = 5;
                                } else {
                                    if (nTbl == 7) {
                                        Nuw[0] = 6;
                                        Nuw[1] = 3;
                                        Nuw[2] = 2;
                                        Nuw[3] = 8;
                                        Nuw[4] = 9;
                                        Nuw[5] = 7;
                                        Nuw[6] = 10;
                                        Nuw[7] = 5;
                                        Nuw[8] = 4;
                                        Nuw[9] = 1;
                                    } else {
                                        if (nTbl == 8) {
                                            Nuw[0] = 2;
                                            Nuw[1] = 3;
                                            Nuw[2] = 4;
                                            Nuw[3] = 6;
                                            Nuw[4] = 8;
                                            Nuw[5] = 7;
                                            Nuw[6] = 9;
                                            Nuw[7] = 5;
                                            Nuw[8] = 1;
                                            Nuw[9] = 10;
                                        } else {
                                            if (nTbl == 9) {
                                                Nuw[0] = 6;
                                                Nuw[1] = 1;
                                                Nuw[2] = 2;
                                                Nuw[3] = 3;
                                                Nuw[4] = 10;
                                                Nuw[5] = 8;
                                                Nuw[6] = 9;
                                                Nuw[7] = 5;
                                                Nuw[8] = 4;
                                                Nuw[9] = 7;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public void Set_Tabelnum(int Rnum) {
        nTbl = Rnum;
    }

    public String GetAlfa() {
        return Alfastring;
    }

    public int GetMuw(int c) {
        return Muw[c];
    }

    public int GetNuw(int p) {
        return Nuw[p];
    }
}

