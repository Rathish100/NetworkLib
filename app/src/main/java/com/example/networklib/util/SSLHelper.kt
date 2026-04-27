package com.example.networklib.util

import android.annotation.SuppressLint
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

object SSLHelper {

    @SuppressLint("CustomX509TrustManager")
    fun getUnsafeTrustManager(): X509TrustManager {
        return object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    }

    fun getSSLSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(trustManager), SecureRandom())
        return sslContext.socketFactory
    }

    fun getUnsafeSSLSocketFactory(): SSLSocketFactory {
        return getSSLSocketFactory(getUnsafeTrustManager())
    }

    @SuppressLint("BadHostnameVerifier")
    fun getUnsafeHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { _, _ -> true }
    }
}