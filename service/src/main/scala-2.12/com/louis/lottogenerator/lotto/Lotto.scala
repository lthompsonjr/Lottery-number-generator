package com.louis.lottogenerator.lotto

import com.louis.lottogenerator.generator.{NationalLottery, NationalLotteryGenerator}
import org.slf4j.LoggerFactory

object Lotto extends NationalLottery {

  val logger = LoggerFactory.getLogger("lotteryGeneratorService")
  def generate = {
    NationalLotteryGenerator.generate(6, 59)
  }

  def message: String = {

    var lottoNumbers = ""
    val result = generate
    result match {
      case Left(msg) => logger.info(msg)
      case Right(numbers) =>  lottoNumbers = numbers.mkString(", ")
    }

    s""" Lotto
       | Numbers: $lottoNumbers
        """.stripMargin
  }

}






