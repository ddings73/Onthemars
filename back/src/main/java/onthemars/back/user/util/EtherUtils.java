package onthemars.back.user.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.exception.IllegalSignatureException;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

@Slf4j
public class EtherUtils {
    private static final String ETHEREUM_SIGNED_MESSAGE = "\u0019Ethereum Signed Message:\n";
    private static final String MESSAGE_PREFIX = "I am signing my one-time nonce: ";
    public static String recoverSignature(String signature, String nonce) {
        String digest = hashMessage(MESSAGE_PREFIX + nonce);

        SignatureData signatureData = getSignatureData(signature);
        int header = 0;
        for (byte b : signatureData.getV()) {
            header = (header << 8) + (b & 0xFF);
        }
        if (header < 27 || header > 34) {
            return null;
        }
        int recId = header - 27;
        BigInteger key = Sign.recoverFromSignature(
            recId,
            new ECDSASignature(
                new BigInteger(1, signatureData.getR()),
                new BigInteger(1, signatureData.getS())
            ),
            Numeric.hexStringToByteArray(digest));
        if (key == null) {
            throw new IllegalSignatureException();
        }
        return ("0x" + Keys.getAddress(key)).trim();
    }

    private static String hashMessage(String message){
        return Hash.sha3(
            Numeric.toHexStringNoPrefix(
                (ETHEREUM_SIGNED_MESSAGE + message.length() + message).getBytes(StandardCharsets.UTF_8)
            )
        );
    }

    private static SignatureData getSignatureData(String signature){
        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }
        byte[] r = Arrays.copyOfRange(signatureBytes, 0, 32);
        byte[] s = Arrays.copyOfRange(signatureBytes, 32, 64);
        return new SignatureData(v, r, s);
    }
}
