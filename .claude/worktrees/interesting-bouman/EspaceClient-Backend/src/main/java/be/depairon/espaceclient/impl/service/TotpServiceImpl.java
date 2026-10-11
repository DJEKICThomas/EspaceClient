package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.service.TotpService;
import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import dev.samstevens.totp.util.Utils;
import org.springframework.stereotype.Service;

@Service
public class TotpServiceImpl implements TotpService {

    private final SecretGenerator secretGenerator = new DefaultSecretGenerator();

    @Override
    public String generateSecret() {
        return secretGenerator.generate();
    }

    @Override
    public String getQrCodeDataUri(String secret, String userid) throws QrGenerationException {
        QrData qrData = new QrData.Builder()
                .label(userid)
                .secret(secret)
                .issuer("Espace Client Depairon")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        QrGenerator qrGenerator = new ZxingPngQrGenerator();
        byte[] imageData = qrGenerator.generate(qrData);
        return Utils.getDataUriForImage(imageData, qrGenerator.getImageMimeType());
    }

    @Override
    public boolean verifyCode(String secret, String code) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        DefaultCodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        verifier.setAllowedTimePeriodDiscrepancy(1);
        return verifier.isValidCode(secret, code);
    }
}
