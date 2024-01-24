package com.asgarov.qr.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;

public class QrCodeGeneratorUtil {

    private static final Logger log = LoggerFactory.getLogger(QrCodeGeneratorUtil.class);

    private QrCodeGeneratorUtil(){
      // Util Klasse
    }

    public static BufferedImage createQrImage(String qrCodeText, int size) throws WriterException {
        log.info("Creating the bitmatrix for the QR-Code that encodes the given String");
        BitMatrix bitMatrix = createBitMatrix(qrCodeText, size);

        log.info("Creating the BufferedImage that contains the QrCode");
        return createBufferedImage(bitMatrix);
    }

    private static BufferedImage createBufferedImage(BitMatrix bitMatrix) {
        int matrixWidth = bitMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);

        // Create white rectangle for the QrCode
        image.createGraphics();
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);

        // Paint and save the image using the BitMatrix
        paintQrCode(bitMatrix, graphics, matrixWidth);
        return image;
    }

    private static void paintQrCode(BitMatrix bitMatrix, Graphics graphics, int matrixWidth) {
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
    }

    private static BitMatrix createBitMatrix(String qrCodeText, int size) throws WriterException {
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new EnumMap<>(EncodeHintType.class);
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        return qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
    }
}
