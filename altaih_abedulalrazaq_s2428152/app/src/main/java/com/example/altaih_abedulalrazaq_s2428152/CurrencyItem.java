
// Name                 Abedulalrazaq Altaih
// Student ID           S2428152
// Programme of Study   BSc (Hons) Software Development


package com.example.altaih_abedulalrazaq_s2428152;

public class CurrencyItem implements java.io.Serializable {

    public String title;
    public String description;
    public String pubDate;
    public double rate;
    public String code;

    // -----------------------------
    // Currency Symbol Map
    // -----------------------------
    public static String getSymbol(String code) {
        if (code == null) return "";

        switch (code.toUpperCase()) {

            case "EUR": return "€";
            case "GBP": return "£";
            case "JPY": return "¥";
            case "AUD": return "A$";
            case "NZD": return "NZ$";
            case "CAD": return "C$";
            case "CHF": return "CHF";
            case "CNY": return "¥";
            case "HKD": return "HK$";
            case "SGD": return "S$";
            case "SEK": return "kr";
            case "NOK": return "kr";
            case "DKK": return "kr";

            // Middle East (ENGLISH SYMBOLS)
            case "AED": return "AED";
            case "SAR": return "SAR";
            case "QAR": return "QAR";
            case "KWD": return "KWD";
            case "BHD": return "BHD";
            case "OMR": return "OMR";
            case "JOD": return "JOD";
            case "LYD": return "LYD";
            case "LBP": return "LBP";
            case "IQD": return "IQD";
            case "IRR": return "IRR";
            case "YER": return "YER";
            case "SYP": return "SYP";
            case "TRY": return "₺";

            // Africa
            case "ZAR": return "R";
            case "EGP": return "E£";
            case "NGN": return "₦";
            case "KES": return "KES";
            case "GHS": return "GHS";
            case "MAD": return "MAD";
            case "TZS": return "TZS";
            case "UGX": return "UGX";
            case "DZD": return "DZD";
            case "TND": return "TND";
            case "MUR": return "MUR";
            case "SZL": return "SZL";
            case "LSL": return "LSL";
            case "NAD": return "NAD";
            case "BWP": return "BWP";
            case "MWK": return "MWK";
            case "RWF": return "RWF";
            case "SDG": return "SDG";
            case "SOS": return "SOS";
            case "MGA": return "MGA";
            case "KMF": return "KMF";
            case "DJF": return "DJF";
            case "ZMW": return "ZMW";
            case "ZMK": return "ZMK";
            case "CDF": return "CDF";
            case "AOA": return "AOA";

            // Asia
            case "INR": return "₹";
            case "PKR": return "₨";
            case "BDT": return "৳";
            case "LKR": return "Rs";
            case "IDR": return "Rp";
            case "MYR": return "RM";
            case "THB": return "฿";
            case "KRW": return "₩";
            case "PHP": return "₱";
            case "VND": return "₫";
            case "MMK": return "MMK";
            case "LAK": return "LAK";
            case "KHR": return "KHR";
            case "MVR": return "MVR";
            case "BTN": return "BTN";
            case "NPR": return "NPR";
            case "KZT": return "KZT";
            case "TJS": return "TJS";
            case "TMT": return "TMT";
            case "AZN": return "AZN";
            case "AMD": return "AMD";
            case "GEL": return "GEL";
            case "KGS": return "KGS";
            case "UZS": return "UZS";
            case "AFN": return "AFN";
            case "MNT": return "MNT";

            // Europe (non-Euro)
            case "PLN": return "zł";
            case "CZK": return "Kč";
            case "HUF": return "Ft";
            case "RON": return "lei";
            case "ISK": return "kr";
            case "BGN": return "лв";
            case "HRK": return "kn";
            case "RSD": return "RSD";
            case "MDL": return "MDL";
            case "MKD": return "MKD";
            case "ALL": return "Lek";
            case "BAM": return "KM";
            case "BYN": return "Br";
            case "BYR": return "BYR";
            case "XPF": return "₣";
            case "XOF": return "CFA";
            case "XAF": return "CFA";

            // North America / Caribbean
            case "MXN": return "Mex$";
            case "USD": return "$";
            case "BBD": return "Bds$";
            case "BSD": return "B$";
            case "BZD": return "BZ$";
            case "GYD": return "G$";
            case "JMD": return "J$";
            case "TTD": return "TT$";
            case "HTG": return "HTG";
            case "CUP": return "₱"; // Cuba
            case "XCD": return "EC$";
            case "FKP": return "£";
            case "BMD": return "$";

            // Central / South America
            case "ARS": return "$";
            case "BRL": return "R$";
            case "CLP": return "CLP$";
            case "COP": return "COP$";
            case "PEN": return "S/";
            case "PYG": return "₲";
            case "UYU": return "$U";
            case "BOB": return "Bs";
            case "CRC": return "₡";
            case "HNL": return "L";
            case "NIO": return "C$";
            case "GTQ": return "Q";
            case "SVC": return "₡";
            case "DOP": return "RD$";

            // Oceania / Pacific
            case "FJD": return "FJ$";
            case "PGK": return "PGK";
            case "SBD": return "SI$";
            case "WST": return "WS$";
            case "TOP": return "TOP";
            case "VUV": return "VT";

            // Crypto
            case "BTC": return "₿";       // Icelandic Krona
        }
        return "";
    }

}
