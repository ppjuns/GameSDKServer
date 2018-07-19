package com.ppjun.game.util

import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.Key
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException

/**
 * Created by ppjun on 12/13/17.
 */

class RsaUtil {


    companion object {

        private var sTransform = "RSA/ECB/PKCS1Padding"

        //进行Base64转码时的flag设置，默认为Base64.DEFAULT
        private var sBase64Mode = Base64.DEFAULT

        //初始化方法，设置参数
        fun init(transform: String, base64Mode: Int) {
            sTransform = transform
            sBase64Mode = base64Mode
        }
        /*
        产生密钥对
        @param keyLength
        密钥长度，小于1024长度的密钥已经被证实是不安全的，通常设置为1024或者2048，建议2048
     */
        fun generateRSAKeyPair(keyLength: Int): KeyPair? {
            var keyPair: KeyPair? = null
            try {

                val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
                //设置密钥长度
                keyPairGenerator.initialize(keyLength)
                //产生密钥对
                keyPair = keyPairGenerator.generateKeyPair()

            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return keyPair
        }

        /*
        加密或解密数据的通用方法
        @param srcData
        待处理的数据
        @param key
        公钥或者私钥
        @param mode
        指定是加密还是解密，值为Cipher.ENCRYPT_MODE或者Cipher.DECRYPT_MODE

     */
        private fun processData(srcData: ByteArray, key: Key, mode: Int): ByteArray? {

            //用来保存处理结果
            var resultBytes: ByteArray? = null

            try {

                //获取Cipher实例
                val cipher = Cipher.getInstance(sTransform)
                //初始化Cipher，mode指定是加密还是解密，key为公钥或私钥
                cipher.init(mode, key)
                //处理数据
                resultBytes = cipher.doFinal(srcData)

            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: NoSuchPaddingException) {
                e.printStackTrace()
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            }

            return resultBytes
        }


        /*
        使用公钥加密数据，结果用Base64转码
     */
        fun encryptDataByPublicKey(srcData: ByteArray, publicKey: PublicKey): String {

            try {
                val resultBytes = processData(srcData, publicKey, Cipher.ENCRYPT_MODE)
                return Base64.encodeToString(resultBytes, sBase64Mode)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        /*
        使用私钥解密，返回解码数据
     */
        fun decryptDataByPrivate(encryptedData: String, privateKey: PrivateKey): ByteArray? {

            val bytes = Base64.decode(encryptedData, sBase64Mode)

            return processData(bytes, privateKey, Cipher.DECRYPT_MODE)
        }

        /*
        使用私钥进行解密，解密数据转换为字符串，使用utf-8编码格式
     */
        fun decryptedToStrByPrivate(encryptedData: String, privateKey: PrivateKey): String {
            return String(decryptDataByPrivate(encryptedData, privateKey)!!)
        }

        /*
        使用私钥解密，解密数据转换为字符串，并指定字符集
     */
        fun decryptedToStrByPrivate(encryptedData: String, privateKey: PrivateKey, charset: String): String? {
            try {

                return String(decryptDataByPrivate(encryptedData, privateKey)!!, Charset.forName(charset))

            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return null
        }


        /*
        使用私钥加密，结果用Base64转码
     */

        fun encryptDataByPrivateKey(srcData: ByteArray, privateKey: PrivateKey): String {

            val resultBytes = processData(srcData, privateKey, Cipher.ENCRYPT_MODE)

            return Base64.encodeToString(resultBytes, sBase64Mode)
        }

        /*
        使用公钥解密，返回解密数据
     */

        fun decryptDataByPublicKey(encryptedData: String, publicKey: PublicKey): ByteArray? {

            val bytes = Base64.decode(encryptedData, sBase64Mode)

            return processData(bytes, publicKey, Cipher.DECRYPT_MODE)

        }

        /*
        使用公钥解密，结果转换为字符串，使用默认字符集utf-8
     */
        fun decryptedToStrByPublicKey(encryptedData: String, publicKey: PublicKey): String {
            return String(decryptDataByPublicKey(encryptedData, publicKey)!!)
        }


        /*
        使用公钥解密，结果转换为字符串，使用指定字符集
     */

        fun decryptedToStrByPublicKey(encryptedData: String, publicKey: PublicKey, charset: String): String? {
            try {

                return String(decryptDataByPublicKey(encryptedData, publicKey)!!, Charset.forName(charset))

            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return null
        }


        /*
        将字符串形式的公钥转换为公钥对象
     */

        fun keyStrToPublicKey(publicKeyStr: String): PublicKey? {

            var publicKey: PublicKey? = null

            val keyBytes = Base64.decode(publicKeyStr, sBase64Mode)

            val keySpec = X509EncodedKeySpec(keyBytes)

            try {

                val keyFactory = KeyFactory.getInstance("RSA")

                publicKey = keyFactory.generatePublic(keySpec)

            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: InvalidKeySpecException) {
                e.printStackTrace()
            }

            return publicKey

        }

        /*
        将字符串形式的私钥，转换为私钥对象
     */

        fun keyStrToPrivate(privateKeyStr: String): PrivateKey? {

            var privateKey: PrivateKey? = null

            val keyBytes = Base64.decode(privateKeyStr, sBase64Mode)

            val keySpec = PKCS8EncodedKeySpec(keyBytes)

            try {

                val keyFactory = KeyFactory.getInstance("RSA")

                privateKey = keyFactory.generatePrivate(keySpec)

            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: InvalidKeySpecException) {
                e.printStackTrace()
            }

            return privateKey

        }


    }
}