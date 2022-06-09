package clqwq.press.qycopenfiles;

import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

public class Md5 implements Hash{

    @Override
    public String out(InputStream in) {
        return "MD5: " + calc(in);
    }

    private String calc(InputStream in) {
        try {
            return DigestUtils.md5Hex(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
