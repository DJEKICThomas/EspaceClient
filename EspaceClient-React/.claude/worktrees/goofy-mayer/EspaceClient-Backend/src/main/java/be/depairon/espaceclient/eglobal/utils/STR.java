package be.depairon.espaceclient.eglobal.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class STR {

    private static final Log log = LogFactory.getLog(STR.class);

    public static String Complete(final String str, final int Len) {
        final StringBuilder wrk1 = new StringBuilder(Math.max(str.length(), Len));
        wrk1.append(str);
        while (wrk1.length() < Len) {
            wrk1.append(" ");
        }
        wrk1.setLength(Len);
        return wrk1.toString();
    }

    public static String CompleteBefore(final String str, final int Len) {
        final StringBuilder wrk1 = new StringBuilder(Math.max(str.length(), Len));
        final int Len2 = (Len - str.length());
        while (wrk1.length() < Len2) {
            wrk1.append(" ");
        }
        wrk1.append(str);
        wrk1.setLength(Len);
        return wrk1.toString();
    }

    public static String Complete(final String str, final int Len, final String fill) {
        final StringBuilder wrk1 = new StringBuilder(Math.max(str.length(), Len));
        wrk1.append(str);
        while (wrk1.length() < Len) {
            wrk1.append(fill);
        }
        wrk1.setLength(Len);
        return wrk1.toString();
    }

    public static String CompleteBefore(String str, final int Len, final String fill) {
        str = str.trim();
        final StringBuilder wrk1 = new StringBuilder(Math.max(str.length(), Len));
        final int Len2 = (Len - str.length());
        while (wrk1.length() < Len2) {
            wrk1.append(fill);
        }
        wrk1.append(str);
        wrk1.setLength(Len);
        return wrk1.toString();
    }

    public static String Repete(final String str, final int Len) {
        final StringBuilder wrk1 = new StringBuilder(str.length() * Len);
        for (int i = 0; i < Len; i++) {
            wrk1.append(str);
        }
        return wrk1.toString();
    }

    public static String removeEnter(final String param) {
        final String patternStr = "(?m)$^|[\\r\\n]+\\z+\\n+\\r+\\u0085+\\u2028+\\u2029";
        final String replaceStr = " ";
        final Pattern pattern = Pattern.compile(patternStr);
        final Matcher matcher = pattern.matcher(param);
        return matcher.replaceAll(replaceStr);
    }

    public static String Crypte(String str, String offset) {
        YGEncryption ygin = null;
        try {
            ygin = new YGEncryption("DESede", offset);
            str = ygin.encrypt(str);
        } catch (YGEncryption.EncryptionException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String DeCrypte(String str, String offset) {
        YGEncryption ygin = null;
        try {
            ygin = new YGEncryption("DESede", offset);
            str = ygin.decrypt(str);
        } catch (YGEncryption.EncryptionException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static boolean isInTable(final String str, final String[] tstr) {
        return isInTable(str, tstr, true);
    }

    public static boolean isInTable(final String str, final String[] tstr, final boolean equ) {
        for (int i = 0; i < tstr.length; i++) {
            if (equ && str.equals(tstr[i])) return true;
            if (!equ && str.startsWith(tstr[i])) return true;
        }
        return false;
    }

    public static String Replace(final String str, final String ss1, final String ss2) {
        if (str == null) return null;
        try {
            //return str.replace(ss1,ss2);
            return StringUtils.replace(str, ss1, ss2);
        } catch (Exception e) {
            return str;
        }
    }

    public static String ReplaceAll(String str, final String ss1, final String ss2) {
        int idx = str.indexOf(ss1);
        if (idx != -1) {
            str = StringUtils.replace(str, ss1, ss2);
            while ((idx = str.indexOf(ss1)) != -1) {
                str = StringUtils.replace(str, ss1, ss2);
            }
        }
        return str;
    }

    public static String ReplaceFirst(final String str, final String ss1, final String ss2) {
        if (str == null) return null;
        try {
            return str.replaceFirst(ss1, ss2);
        } catch (Exception e) {
            return str;
        }
    }

    public static String Escape(final String str) {
        return Escape(str, "%");
    }

    public static String Escape(final String str, final String ppref) {
        return Escape(str, ppref, false);
    }

    public static String Escape(String str, final String ppref, final boolean pblanks) {
        final StringBuilder res = new StringBuilder(1000);

        final String strNocode = "*+-./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
        if (str.length() > 0) {
            if (pblanks) str = STR.Replace(str, " ", "+");
            for (int i = 0; i < str.length(); i++) {
                final char c1 = str.charAt(i);
                if (strNocode.indexOf(c1) != -1) {
                    res.append(c1);
                } else {
                    res.append(ppref + Integer.toHexString(c1));
                }
            }
        }

        return res.toString();
    }

    public static String UnEscape(final String str) {
        return UnEscape(str, "%");
    }

    public static String UnEscape(final String str, final String ppref) {
        final StringBuilder res = new StringBuilder(1000);

        if (str.length() > 0) {
            int i = 0;
            while (i < str.length()) {
                if (str.substring(i, i + 1).equals(ppref)) {
                    final String cod = str.substring(i + 1, i + 3).trim();
                    if (cod.length() == 2) {
                        final int valint = Integer.parseInt(cod, 16);
                        final char ch = (char) valint;
                        res.append(ch);
                        i += 3;
                    } else {
                        res.append(str.substring(i, i + 1));
                        i++;
                    }
                } else {
                    res.append(str.substring(i, i + 1));
                    i++;
                }
            }
        }

        return res.toString();
    }

    public static String[] split(final String str, final char x) {
        final ArrayList<String> v = new ArrayList<String>();
        StringBuilder str1 = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == x) {
                v.add(str1.toString());
                str1 = new StringBuilder();
            } else {
                str1.append(str.charAt(i));
            }
        }
        v.add(str1.toString());
        String array[];
        array = new String[v.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = (String) v.get(i);
        }
        return array;
    }

    public static String[] split(final String str, final String x) {
        final ArrayList<String> v = new ArrayList<String>();
        StringBuilder str1 = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (i + x.length() > str.length()) str1.append(str.charAt(i));
            else {
                if (str.substring(i, i + x.length()).equals(x)) {
                    v.add(str1.toString());
                    str1 = new StringBuilder();
                    i += x.length() - 1;
                } else {
                    str1.append(str.charAt(i));
                }
            }
        }
        v.add(str1.toString());
        String array[];
        array = new String[v.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = (String) v.get(i);
        }
        return array;
    }

    public static List<String> splitToList(final String str, final char x) {
        final ArrayList<String> v = new ArrayList<String>();
        StringBuilder str1 = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == x) {
                v.add(str1.toString());
                str1 = new StringBuilder();
            } else {
                str1.append(str.charAt(i));
            }
        }
        v.add(str1.toString());
        return v;
    }

    public static List<String> splitToList(final String str, final String x) {
        final ArrayList<String> v = new ArrayList<String>();
        StringBuilder str1 = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (i + x.length() > str.length()) str1.append(str.charAt(i));
            else {
                if (str.substring(i, i + x.length()).equals(x)) {
                    v.add(str1.toString());
                    str1 = new StringBuilder();
                    i += x.length() - 1;
                } else {
                    str1.append(str.charAt(i));
                }
            }
        }
        return v;
    }

    /**
     * @param data
     * @param pos
     * @return
     * @throws NumberFormatException
     */
    public static String getFormattedNumber(final String data, final int pos) throws NumberFormatException {
        long iData = 0;
        try {
            iData = Long.parseLong(data.trim());
        } catch (Exception e) {
            log.debug("getFormattedNumber for data " + data, e);
        }
        return getFormattedNumber(iData, pos);
    }

    public static String getFormattedNumber(final long data, final int pos) {
        final NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(pos);
        nf.setMaximumIntegerDigits(pos);
        nf.setGroupingUsed(false);
        return nf.format(data);
    }

    public static String getFormattedNumber(final int data, final int pos) {
        final NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(pos);
        nf.setMaximumIntegerDigits(pos);
        nf.setGroupingUsed(false);
        return nf.format(data);
    }

    public static String getFormattedNumberDec(final double data, final int dec) {
        return getFormattedNumberDec(data, dec, '.');
    }

    //public static String getFormattedNumberDecVir(float data, int dec) {
//	return getFormattedNumberDec(data, dec, ',');
//}
    public static String getFormattedNumberDec(final double data, final int dec, final char psep) {
        final DecimalFormat df = new DecimalFormat("0.00");
        df.setMinimumFractionDigits(dec);
        df.setMaximumFractionDigits(dec);
        final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(psep);
        df.setDecimalFormatSymbols(dfs);
        return df.format(data);
    }

    public static String getNumber(final int data, final int len) {
        final StringBuilder sb1 = new StringBuilder(Integer.toString(data));
        final StringBuilder sb2 = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb2.append("0");
        final int lsb1 = sb1.length();
        for (int i = 0; i < lsb1; i++) {
            sb2.setCharAt(i + len - lsb1, sb1.charAt(i));
        }
        return sb2.toString();
    }

    public static String getRemoveSpacestoUpper(String text) {
        text = text.trim();
        String test = "";
        final StringBuilder rettext = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            test = text.substring(i, i + 1);
            if (!test.trim().equals(""))
                rettext.append(text.substring(i, i + 1));
        }
        return rettext.toString().toUpperCase();
    }

    public static String getRemoveSpacestoLower(String text) {
        text = text.trim();
        String test = "";
        String rettext = "";
        for (int i = 0; i < text.length(); i++) {
            test = text.substring(i, i + 1);
            if (!test.trim().equals(""))
                rettext += text.substring(i, i + 1);
        }
        rettext = rettext.toLowerCase();
        return rettext;
    }

    public static String getRemoveSpaces(String text) {
        text = text.trim();
        String test = "";
        String rettext = "";
        for (int i = 0; i < text.length(); i++) {
            test = text.substring(i, i + 1);
            if (!test.trim().equals(""))
                rettext += text.substring(i, i + 1);
        }
        return rettext;
    }

    public static double getFormattedNumberDecDouble(final double data, final int dec, final String round) {
        return 0;
    }

    public static double getFormattedNumberDecDouble(final double data, final int dec) {
        return getFormattedNumberDecDouble(data, dec, "");
    }

    /* remove leading whitespace */
    public static String ltrim(final String source) {
        return source.replaceAll("^\\s+", "");
    }

    /* remove trailing whitespace */
    public static String rtrim(final String source) {
        return source.replaceAll("\\s+$", "");
    }

    /* replace multiple whitespaces between words with single blank */
    public static String itrim(final String source) {
        return source.replaceAll("\\b\\s{2,}\\b", " ");
    }

    /* remove all superfluous whitespaces in source string */
    public static String trim(final String source) {
        return itrim(ltrim(rtrim(source)));
    }

    public static String lrtrim(final String source) {
        return ltrim(rtrim(source));
    }

    public static String htmlEnter(final String param) {
        final String patternStr = "(?m)$^|[\\r\\n]+\\z+\\n+\\r+\\u0085+\\u2028+\\u2029";
        final String replaceStr = "<br>";
        final Pattern pattern = Pattern.compile(patternStr);
        final Matcher matcher = pattern.matcher(param);
        return matcher.replaceAll(replaceStr);
    }

    public static String DoubleFormat(final double param) {
        final NumberFormat formatter = new DecimalFormat("#0.00####");
        return formatter.format(param);
    }

    public static String DoubleFormatString(String param) {
        param = STR.Replace(param, ",", ".");
        double par = 0;
        try {
            par = Double.parseDouble(param.trim());
        } catch (Exception e) {
        }
        NumberFormat formatter = new DecimalFormat("#0.00####");
        return formatter.format(par);
    }

    public static String shaencryption(String text, String encryption) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance(encryption);
            crypt.reset();
            crypt.update(text.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    public static String sha1(String text) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(text.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
