package com.louis.lottogenerator.email

import java.io.File

import com.typesafe.config.ConfigFactory
import org.apache.commons.mail.{DefaultAuthenticator, SimpleEmail}
import org.slf4j.LoggerFactory

/**
  * Created by lth09 on 03/12/2017.
  */

object EmailClient {

  val config = ConfigFactory.parseFile(new File("/Users/lth09/Desktop/LotteryGen/service/src/main/resources/application.conf"))
  val sender = config.getString("email.sender")
  val mailList = config.getStringList("email.mail-list")
  val user = config.getString("email.default-authenticator.user")
  val password = config.getString("email.default-authenticator.password")
  val hostname = config.getString("email.hostname")
  val port = config.getInt("email.port")
  val sslOnConnect = config.getBoolean("email.ssl-on-connect")
  val subject = config.getString("email.subject")

  val logger = LoggerFactory.getLogger("lotteryGeneratorService")

  def sendEmail(content: String): Unit = {
//    val email = new SimpleEmail()
//    email.setHostName(hostname)
//    email.setSmtpPort(port)
//    email.setAuthenticator(new DefaultAuthenticator(user, password))
//    email.setSSLOnConnect(sslOnConnect)
//    email.setFrom(sender)
//    email.setSubject(subject)
//    email.setMsg(content)
//    mailList.forEach(recipient => email.addTo(recipient))
//    email.send()
    mailList.forEach(recipient => logger.info(s"mail sent to $recipient from $sender"))
  }
}
