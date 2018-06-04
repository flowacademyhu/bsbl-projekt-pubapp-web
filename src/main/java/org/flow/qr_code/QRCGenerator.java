package org.flow.qr_code;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.apache.commons.lang.RandomStringUtils;
import org.flow.models.Ordering;
import org.flow.repositories.OrderingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class QRCGenerator {
    @Autowired
    private OrderingRepository orderingRepository;

    public static void main(String[] args) throws IOException, WriterException {
    }

    public void generateQRCode(Long id)
            throws WriterException, IOException {
        int size = 300;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        String rndchars = RandomStringUtils.randomAlphanumeric(4);
        String uniquefilename = new SimpleDateFormat("'QR_'yyyyMMdd-HHmm'_'").format(new Date());
        Path path = FileSystems.getDefault().getPath("myQRcodes/", uniquefilename + rndchars + ".png");
        BitMatrix bitMatrix = qrCodeWriter.encode(path.toString(), BarcodeFormat.QR_CODE, size, size, hints);
        //BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size, size, hints);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        Ordering generated = orderingRepository.findById(id).get();
        generated.setQrCodePath(path.toString());
        orderingRepository.save(generated);

    }
}

