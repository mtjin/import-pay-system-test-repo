package com.mtjin.payproject

class PaymentScheme {
    companion object {
        const val ISP = "ispmobile"
        const val BANKPAY = "kftc-bankpay"
        const val HYUNDAI_APPCARD =
            "hdcardappcardansimclick" //intent:hdcardappcardansimclick://appcard?acctid=201605092050048514902797477441#Intent;package=com.hyundaicard.appcard;end;

        const val KB_APPCARD =
            "kb-acp" //intent://pay?srCode=5681318&kb-acp://#Intent;scheme=kb-acp;package=com.kbcard.cxh.appcard;end;


        const val PACKAGE_ISP = "kvp.jjy.MispAndroid320"
        const val PACKAGE_BANKPAY = "com.kftc.bankpay.android"
    }
}