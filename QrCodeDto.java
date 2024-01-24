package at.ama.pdfgen.qr.dto;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.firstNonBlank;
import static org.apache.commons.lang3.StringUtils.firstNonEmpty;


/**
 * Eine Dto Klasse fuer QrCode information
 * Sieh auch:
 * <a href="https://zv.psa.at/de/download/qr-code.html">Dokumentaiton zum QR Code Standard</a>
 * <a href="https://zv.psa.at/de/qr-code-generator.html">Online Generator Tool um Payload zu verstehen</a>
 *
 * @author xasgarov
 */
public class QrCodeDto {
    private static final Logger log = LoggerFactory.getLogger(QrCodeDto.class);

    public QrCodeDto(String serviceTag, String version, int coding, String function, String bic, String receiver, String iban, String amount, String currency, String purpose, String reference, String text, String displayNote) {
        this.serviceTag = serviceTag;
        this.version = version;
        this.coding = coding;
        this.function = function;
        this.bic = bic;
        this.receiver = receiver;
        this.iban = iban;
        this.amount = amount;
        this.currency = currency;
        this.purpose = purpose;
        this.reference = reference;
        this.text = text;
        this.displayNote = displayNote;
    }

    public QrCodeDto(String[] payloadParts) {
        this.serviceTag = payloadParts[0];
        this.version = payloadParts[1];
        this.coding = Integer.parseInt(payloadParts[2]);
        this.function = payloadParts[3];
        this.bic = payloadParts[4];
        this.receiver = payloadParts[5];
        this.iban = payloadParts[6];
        // payload includes one line that holds both currency and amount - e.g. EUR25
        this.currency = payloadParts[7].substring(0, 3);
        this.amount = payloadParts[7].substring(3);
        this.purpose = payloadParts[8];
        this.reference = payloadParts[9];
        this.text = payloadParts[10];
        this.displayNote = payloadParts[11];
    }

    /**
     * 3 Character ServiceTag, z.B. `BCD`
     * Immer dabei, deswegen final
     */
    private final String serviceTag;

    /**
     * 3 Character Version, z.B. `001` oder `002`
     * Immer dabei, deswegen final
     */
    private final String version;

    /**
     * Encoding, moegliche Werte:
     * 1: UTF-8
     * 2: ISO-8859-1
     * 3: ISO-8859-2
     * 4: ISO-8859-4
     * 5: ISO-8859-5
     * 6: ISO-8859-7
     * 7: ISO-8859-10
     * 8: ISO-8859-15
     * <br/>
     * Immer dabei, deswegen final
     */
    private int coding = 1;

    /**
     * 3 Character Function, z.B. `SCT`, bedeutet `SEPA Credit Transfer`
     * Immer dabei, deswegen final
     */
    private final String function;

    /**
     * 8 oder 11 Character BIC, z.B. `RLNWATWW`
     */
    private String bic;

    /**
     * Empfaenger Name, immer dabei
     */
    private final String receiver;

    /**
     * IBAN, immer dabei
     */
    private final String iban;

    /**
     * Summe
     */
    private String amount;

    /**
     * Waehrung
     */
    private String currency;

    /**
     * Purpose
     */
    private String purpose;

    /**
     * Zahlungsreferenz, mutually exclusive with text
     */
    private String reference;

    /**
     * Text, mutually exclusive with reference
     */
    private String text;

    /**
     * User Notiz
     */
    private String displayNote;

    /**
     * Generiert den Payload (in String format) aus welchem ein QrCode Image erstellt wird
     *
     * @return Payload in Text Format
     */
    public String toPayload() {
        if (!StringUtils.isEmpty(reference) && !StringUtils.isEmpty(text)) {
            log.warn("Reference '{}' and text '{}' are mutually exclusive, ONLY reference will be put into QR Code",
                    reference, text);
        }

        return firstNonEmpty(serviceTag, "") + "\n" +
                ofNullable(version).orElse("") + "\n" +
                coding + "\n" +
                ofNullable(function).orElse("") + "\n" +
                ofNullable(bic).orElse("") + "\n" +
                ofNullable(receiver).orElse("") + "\n" +
                ofNullable(iban).orElse("") + "\n" +
                ofNullable(currency).orElse("") + ofNullable(amount).orElse("") + "\n" +
                ofNullable(purpose).orElse("") + "\n" +
                ofNullable(reference).orElse("") + "\n" +
                ofNullable(text).orElse("") + "\n" +
                ofNullable(displayNote).orElse("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QrCodeDto qrCodeDto = (QrCodeDto) o;
        return coding == qrCodeDto.coding && Objects.equals(serviceTag, qrCodeDto.serviceTag) && Objects.equals(version, qrCodeDto.version) && Objects.equals(function, qrCodeDto.function) && Objects.equals(bic, qrCodeDto.bic) && Objects.equals(receiver, qrCodeDto.receiver) && Objects.equals(iban, qrCodeDto.iban) && Objects.equals(amount, qrCodeDto.amount) && Objects.equals(currency, qrCodeDto.currency) && Objects.equals(purpose, qrCodeDto.purpose) && Objects.equals(reference, qrCodeDto.reference) && Objects.equals(text, qrCodeDto.text) && Objects.equals(displayNote, qrCodeDto.displayNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceTag, version, coding, function, bic, receiver, iban, amount, currency, purpose, reference, text, displayNote);
    }

    @Override
    public String toString() {
        return "QrCodeDto{" +
                "serviceTag='" + serviceTag + '\'' +
                ", version='" + version + '\'' +
                ", coding=" + coding +
                ", function='" + function + '\'' +
                ", bic='" + bic + '\'' +
                ", receiver='" + receiver + '\'' +
                ", iban='" + iban + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", purpose='" + purpose + '\'' +
                ", reference='" + reference + '\'' +
                ", text='" + text + '\'' +
                ", displayNote='" + displayNote + '\'' +
                '}';
    }
}
