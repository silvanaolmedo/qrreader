package ar.edu.ub.qrcodereader.laboratoryschedule.util;

import android.content.Context;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class FileReaderUtil {

    public static String readFileAsString(Context context, int id){
        try {
            return IOUtils.toString(context.getResources().openRawResource(id), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
