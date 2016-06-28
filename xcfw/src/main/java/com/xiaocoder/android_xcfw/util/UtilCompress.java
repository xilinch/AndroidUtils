package com.xiaocoder.android_xcfw.util;

import com.xiaocoder.android_xcfw.encryption.rsa.UtilBase64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class UtilCompress {


    public static String compress(String src) {

        String result = null;

        try {

            if (src != null) {

                ByteArrayOutputStream bos = null;
                GZIPOutputStream gos = null;
                try {
                    bos = new ByteArrayOutputStream();
                    gos = new GZIPOutputStream(bos);

                    gos.write(src.getBytes());
                    gos.finish();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (gos != null)
                            gos.close();
                        byte[] bytes = bos.toByteArray();
                        result = UtilBase64.encode(bytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (bos != null)
                            bos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {

        }

        return result;
    }


    public static String deCompress(String compressSrc) {

        String result = null;

        try {

            if (compressSrc != null) {

                byte[] bytes = UtilBase64.decode(compressSrc);

                result = deCompress(bytes);

            }

        } catch (Exception e) {

        }

        return result;
    }


    public static String deCompress(byte[] compressBytes) {
        return deCompress(compressBytes, "utf-8");
    }


    public static String deCompress(byte[] compressBytes, String encode) {

        String result = null;

        try {
            ByteArrayInputStream bis = null;
            GZIPInputStream gis = null;
            ByteArrayOutputStream bos = null;

            try {
                bis = new ByteArrayInputStream(compressBytes);
                gis = new GZIPInputStream(bis);
                bos = new ByteArrayOutputStream();

                byte[] buff = new byte[4096];
                int len;
                while ((len = gis.read(buff)) > 0) {
                    bos.write(buff, 0, len);
                }
                result = new String(bos.toByteArray(), encode);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null)
                        bos.close();
                } catch (Exception e) {
                }
                try {
                    if (gis != null)
                        gis.close();
                } catch (Exception e) {
                }
                try {
                    if (bis != null)
                        bis.close();
                } catch (Exception e) {
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}