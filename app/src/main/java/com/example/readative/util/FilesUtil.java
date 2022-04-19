package com.example.readative.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;

public class FilesUtil {
    public static String getFileChecksumHash(MessageDigest digest, File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        byte[] buffer = new byte[1024];
        int bytesCount = 0;

        while ((bytesCount = fis.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesCount);
        }

        fis.close();
        byte[] bytes = digest.digest();

        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
