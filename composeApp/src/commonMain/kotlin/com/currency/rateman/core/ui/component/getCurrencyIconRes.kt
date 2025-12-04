package com.currency.rateman.core.ui.component

import com.currency.rateman.core.domain.model.CurrencyCode
import org.jetbrains.compose.resources.DrawableResource
import rateman.composeapp.generated.resources.Res
import rateman.composeapp.generated.resources.aed
import rateman.composeapp.generated.resources.aud
import rateman.composeapp.generated.resources.bam
import rateman.composeapp.generated.resources.bgn
import rateman.composeapp.generated.resources.brl
import rateman.composeapp.generated.resources.cad
import rateman.composeapp.generated.resources.chf
import rateman.composeapp.generated.resources.cny
import rateman.composeapp.generated.resources.czk
import rateman.composeapp.generated.resources.dkk
import rateman.composeapp.generated.resources.egp
import rateman.composeapp.generated.resources.eur
import rateman.composeapp.generated.resources.gbp
import rateman.composeapp.generated.resources.hkd
import rateman.composeapp.generated.resources.hrk
import rateman.composeapp.generated.resources.huf
import rateman.composeapp.generated.resources.idr
import rateman.composeapp.generated.resources.ils
import rateman.composeapp.generated.resources.inr
import rateman.composeapp.generated.resources.isk
import rateman.composeapp.generated.resources.jpy
import rateman.composeapp.generated.resources.krw
import rateman.composeapp.generated.resources.mxn
import rateman.composeapp.generated.resources.myr
import rateman.composeapp.generated.resources.nok
import rateman.composeapp.generated.resources.nzd
import rateman.composeapp.generated.resources.php
import rateman.composeapp.generated.resources.pln
import rateman.composeapp.generated.resources.ron
import rateman.composeapp.generated.resources.rsd
import rateman.composeapp.generated.resources.rub
import rateman.composeapp.generated.resources.sar
import rateman.composeapp.generated.resources.sek
import rateman.composeapp.generated.resources.sgd
import rateman.composeapp.generated.resources.thb
import rateman.composeapp.generated.resources.tnd
import rateman.composeapp.generated.resources.tryy
import rateman.composeapp.generated.resources.twd
import rateman.composeapp.generated.resources.uah
import rateman.composeapp.generated.resources.usd
import rateman.composeapp.generated.resources.usdt
import rateman.composeapp.generated.resources.vnd
import rateman.composeapp.generated.resources.xdr
import rateman.composeapp.generated.resources.zar

fun getCurrencyIcon(currency: CurrencyCode): DrawableResource {
    return when (currency) {
        CurrencyCode.USD -> Res.drawable.usd
        CurrencyCode.EUR -> Res.drawable.eur
        CurrencyCode.JPY -> Res.drawable.jpy
        CurrencyCode.GBP -> Res.drawable.gbp
        CurrencyCode.CHF -> Res.drawable.chf
        CurrencyCode.CAD -> Res.drawable.cad
        CurrencyCode.AUD -> Res.drawable.aud
        CurrencyCode.CNY -> Res.drawable.cny
        CurrencyCode.AED -> Res.drawable.aed
        CurrencyCode.BAM -> Res.drawable.bam
        CurrencyCode.BGN -> Res.drawable.bgn
        CurrencyCode.BRL -> Res.drawable.brl
        CurrencyCode.CZK -> Res.drawable.czk
        CurrencyCode.DKK -> Res.drawable.dkk
        CurrencyCode.HKD -> Res.drawable.hkd
        CurrencyCode.HUF -> Res.drawable.huf
        CurrencyCode.IDR -> Res.drawable.idr
        CurrencyCode.ILS -> Res.drawable.ils
        CurrencyCode.INR -> Res.drawable.inr
        CurrencyCode.ISK -> Res.drawable.isk
        CurrencyCode.KRW -> Res.drawable.krw
        CurrencyCode.MXN -> Res.drawable.mxn
        CurrencyCode.MYR -> Res.drawable.myr
        CurrencyCode.NOK -> Res.drawable.nok
        CurrencyCode.NZD -> Res.drawable.nzd
        CurrencyCode.PHP -> Res.drawable.php
        CurrencyCode.PLN -> Res.drawable.pln
        CurrencyCode.RON -> Res.drawable.ron
        CurrencyCode.RUB -> Res.drawable.rub
        CurrencyCode.SEK -> Res.drawable.sek
        CurrencyCode.SGD -> Res.drawable.sgd
        CurrencyCode.THB -> Res.drawable.thb
        CurrencyCode.TND -> Res.drawable.tnd
        CurrencyCode.TRY -> Res.drawable.tryy
        CurrencyCode.UAH -> Res.drawable.uah
        CurrencyCode.VND -> Res.drawable.vnd
        CurrencyCode.XDR -> Res.drawable.xdr
        CurrencyCode.ZAR -> Res.drawable.zar
        CurrencyCode.EGP -> Res.drawable.egp
        CurrencyCode.SAR -> Res.drawable.sar
        CurrencyCode.HRK -> Res.drawable.hrk
        CurrencyCode.RSD -> Res.drawable.rsd
        CurrencyCode.TWD -> Res.drawable.twd
        CurrencyCode.USDT -> Res.drawable.usdt
    }
}