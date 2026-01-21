package be.depairon.espaceclient.eglobal.utils;

import java.util.Calendar;
import java.util.Date;

public class Serv_CCYYMMDD_HMS {
	private int CCYYMMDD = 0;
	private int CCYY = 0;
	private int CCYYMM = 0;
	private int MM = 0;
	private int DD = 0;
	private int HMS = 0;
	private int HM = 0;
	private int H = 0;
	private int M = 0;
	private int S = 0;
	private int MIL = 0;
	private Date today = null;

	public void Calc_CCYYMMDD_HMS() {
		Calendar c = Calendar.getInstance();

		CCYYMMDD = (((c.get(Calendar.YEAR) * 100) + c.get(Calendar.MONTH) + 1) * 100) + c.get(Calendar.DAY_OF_MONTH);
		CCYYMM = (((c.get(Calendar.YEAR) * 100) + c.get(Calendar.MONTH) + 1) * 100);
		CCYY = c.get(Calendar.YEAR);
		MM = c.get(Calendar.MONTH);
		DD = c.get(Calendar.DAY_OF_MONTH);
		HMS = (((c.get(Calendar.HOUR_OF_DAY) * 100) + c.get(Calendar.MINUTE)) * 100) + c.get(Calendar.SECOND);
		HM = (((c.get(Calendar.HOUR_OF_DAY) * 100) + c.get(Calendar.MINUTE)));
		H = c.get(Calendar.HOUR_OF_DAY);
		M = c.get(Calendar.MINUTE);
		S = c.get(Calendar.SECOND);
		MIL = c.get(Calendar.MILLISECOND);
		today = c.getTime();
	}

	public int Get_CCYYMMDD() {
		return CCYYMMDD;
	}

	public int Get_CCYY() {
		return CCYY;
	}

	public int Get_MM() {
		return MM;
	}

	public int Get_DD() {
		return DD;
	}

	public int Get_HMS() {
		return HMS;
	}

	public int Get_HM() {
		return HM;
	}

	public Date getToday() {
		return today;
	}

	public int getH() {
		return H;
	}

	public int getS() {
		return S;
	}

	public int getM() {
		return M;
	}

	public int getMIL() {
		return MIL;
	}

	public int getCCYYMM() {
		return CCYYMM;
	}

}
