package com.currency.rateman.ui.components

import androidx.annotation.DrawableRes
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.R

@DrawableRes
fun getCurrencyIconRes(currency: CurrencyCode): Int {
    return when (currency) {
        CurrencyCode.USD -> R.drawable.usd
        CurrencyCode.EUR -> R.drawable.eur
        CurrencyCode.GBP -> R.drawable.gbp
        CurrencyCode.JPY -> R.drawable.jpy
        CurrencyCode.AUD -> R.drawable.aud
        CurrencyCode.CAD -> R.drawable.cad
        CurrencyCode.CHF -> R.drawable.chf
        CurrencyCode.CNY -> R.drawable.cny
        CurrencyCode.INR -> R.drawable.inr
        CurrencyCode.NZD -> R.drawable.nzd
        CurrencyCode.SGD -> R.drawable.sgd
        CurrencyCode.HKD -> R.drawable.hkd
        CurrencyCode.KRW -> R.drawable.krw
        CurrencyCode.TRY -> R.drawable.tryy
        CurrencyCode.RUB -> R.drawable.rub
        CurrencyCode.BRL -> R.drawable.brl
        CurrencyCode.ZAR -> R.drawable.zar
        CurrencyCode.MXN -> R.drawable.mxn
        CurrencyCode.AED -> R.drawable.aed
        CurrencyCode.CZK -> R.drawable.czk
        CurrencyCode.DKK -> R.drawable.dkk
        CurrencyCode.NOK -> R.drawable.nok
        CurrencyCode.SEK -> R.drawable.sek
        CurrencyCode.PLN -> R.drawable.pln
        CurrencyCode.HUF -> R.drawable.huf
        CurrencyCode.RON -> R.drawable.ron
        CurrencyCode.BGN -> R.drawable.bgn
        CurrencyCode.UAH -> R.drawable.uah
        CurrencyCode.ISK -> R.drawable.isk
        CurrencyCode.THB -> R.drawable.thb
        CurrencyCode.MYR -> R.drawable.myr
        CurrencyCode.IDR -> R.drawable.idr
        CurrencyCode.PHP -> R.drawable.php
        CurrencyCode.VND -> R.drawable.vnd
        CurrencyCode.ILS -> R.drawable.ils
        CurrencyCode.TND -> R.drawable.tnd
        CurrencyCode.XDR -> R.drawable.xdr
        CurrencyCode.BAM -> R.drawable.bam
    }
}