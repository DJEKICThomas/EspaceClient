package be.depairon.espaceclient.service;

import dev.samstevens.totp.exceptions.QrGenerationException;

public interface TotpService {

    String generateSecret();

    String getQrCodeDataUri(String secret, String userid, String issuer) throws QrGenerationException;

    boolean verifyCode(String secret, String code);
}
