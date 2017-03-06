package com.example.grzegorzkokoszka.shopproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class CardValidator {

    private CardValidator() {}

    private static int[] array;
    private static int doubleNumber;
    private static int length;
    private static String number;
    private static int sum;

    private static final String MAESTRO = "(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)\\d{8}|(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)\\d{9}|(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)\\d{10}|(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)\\d{11}|(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)\\d{12}|(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)\\d{13}|(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)\\d{14}|(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)\\d{15}$";
    private static final String MASTERCARD = "^(?!.*(?:(?:5018|5020|5038\\d{12})))[5][0-5].{14}$";
    private static final String SOLO = "(6334|6767)\\d{12}|(6334|6767)\\d{14}|(6334|6767)\\d{15}";
    private static final String SWITCH = "(4903|4905|4911|4936|6333|6759)\\d{12}|(4903|4905|4911|4936|6333|6759)\\d{14}|(4903|4905|4911|4936|6333|6759)\\d{15}|(564182|633110)\\d{10}|(564182|633110)\\d{12}|(564182|633110)\\d{13}$";
    private static final String VISA = "^(?!.*(?:(?:4026|4405|4508|4844|4913|4917)\\d{12}|417500\\d{10}))4\\d{15}$";
    private static final String VISA_ELECTRON = "(4026|4405|4508|4844|4913|4917)\\d{12}|417500\\d{10}$";

    public static String getCardNumber(String number) {
        return number + Integer.toString(getCheckDigit(number));
    }

    public static String getCardVendor(String cardNumber) {
        if (isVisa(cardNumber)) { return "Visa"; }
        else if (isVisaElectron(cardNumber)) { return "Visa Electron"; }
        else if (isMasterCard(cardNumber)) { return "MasterCard"; }
        else if (isMaestro(cardNumber)) { return "Maestro"; }
        else if (isSolo(cardNumber)) { return "Solo"; }
        else if (isSwitch(cardNumber)) { return "Switch"; }
        else return "";
    }

    public static int getCheckDigit(String number) {
        getStringLength(number);
        toIntArray(number);
        sum = 0;

        for (int i=length-1;i>=0;i-=2) {
            doubleNumber = array[i]*2;
            if (doubleNumber == 10) { sum += 1; }
            else if (doubleNumber > 10) { sum += 1 + (doubleNumber % 10); }
            else { sum += doubleNumber; }
        }
        for (int i=length-2;i>=1;i-=2) {
            sum += array[i];
        }
        if (sum % 10 != 0) {
            return 10 - (sum % 10);
        }
        else { return 0; }
    }

    private static void getStringLength(String number) {
        length = number.length();
    }

    public static String removeNonDigits(String str) {
        return str.replaceAll("\\D", "");
    }

    public static boolean validate(String cardNumber) {
        number = removeNonDigits(cardNumber);
        if (number == null || number == "") {
            return false;
        }
        getStringLength(number);
        array = toIntArray(cardNumber);

        sum = 0;
        for (int i=length-2;i>=0;i-=2) {
            doubleNumber = array[i]*2;
            if (doubleNumber == 10) { sum += 1; }
            else if (doubleNumber > 10) { sum += 1 + (doubleNumber % 10); }
            else { sum += doubleNumber; }
        }
        for (int i=length-1;i>=1;i-=2) {
            sum += array[i];
        }
        return sum % 10 == 0;
    }

    private static int[] toIntArray(String number) {
        int length = number.length();
        int[] intArray = new int[length];
        for (int i = 0; i < length; i++) {
            intArray[i] = Integer.parseInt(number.substring(i, i+1));
        }
        return intArray;
    }

    private static boolean isMaestro(String number) {
        Pattern p = Pattern.compile(MAESTRO);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    private static boolean isMasterCard(String number) {
        Pattern p = Pattern.compile(MASTERCARD);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    private static boolean isSolo(String number) {
        Pattern p = Pattern.compile(SOLO);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    private static boolean isSwitch(String number) {
        Pattern p = Pattern.compile(SWITCH);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    private static boolean isVisa(String number) {
        Pattern p = Pattern.compile(VISA);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    private static boolean isVisaElectron(String number) {
        Pattern p = Pattern.compile(VISA_ELECTRON);
        Matcher m = p.matcher(number);
        return m.matches();
    }
}