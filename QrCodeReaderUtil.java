package com.asgarov.qr.util;

import com.asgarov.qr.dto.QrCodeDto;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;

public class QrCodeReaderUtil {

    private QrCodeReaderUtil(){
      // Util Klasse
    }

    public static QrCodeDto readQrDto(BufferedImage image) throws NotFoundException {
        String payload = readQrPayload(image);

        // -1 keeps trailing white spaces of the empty values
        String[] payloadLines = payload.split(System.lineSeparator(), -1);
        return new QrCodeDto(payloadLines);
    }

    public static String readQrPayload(BufferedImage image) throws NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }

}
