package clqwq.press.qycopenfiles;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

public class SHA1 implements Hash{

    @Override
    public String out(InputStream in) {
        return "SHA1: " + calc(in);
    }

    private String calc(InputStream in) {
        try {
            return DigestUtils.sha1Hex(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
