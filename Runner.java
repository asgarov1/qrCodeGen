package com.asgarov.qr;

import at.ama.pdfgen.qr.dto.QrCodeDto;
import at.ama.pdfgen.qr.util.QrCodeGeneratorUtil;
import com.google.zxing.WriterException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws WriterException, IOException {
        QrCodeDto qrCodeDto = new QrCodeDto(
                "BCD",
                "001",
                1,
                "SCT",
                "OPSKATWW",
                "Max Mustermann",
                "AT026000000001349870",
                "25",
                "EUR",
                "",
                "test reference",
                "",
                ""
        );

        int size = 95;
        BufferedImage qrImage = QrCodeGeneratorUtil.createQrImage(qrCodeDto.toPayload(), size);

        // write to file system
        String filePath = "myQrCode.png";
        ImageIO.write(qrImage,  "png", new File(filePath));
    }
}
