package com.louis.lottogenerator.euromillions

import com.louis.lottogenerator.generator.{NationalLottery, NationalLotteryGenerator}
import org.slf4j.LoggerFactory

/**
  * Created by lth09 on 02/12/2017.
  */
case class EuroMillions(standardNumbers:Either[String,List[Int]], luckyStars: Either[String,List[Int]])

object EuroMillions extends NationalLottery {

  val logger = LoggerFactory.getLogger("lotteryGeneratorService")
  private val standardNumbers = NationalLotteryGenerator.generate(5,50)
  private val luckyStars = NationalLotteryGenerator.generate(2,12)

  def generate: EuroMillions = {
    EuroMillions(standardNumbers,luckyStars)
  }

  def message: String = {

    val result = generate
    var standardNumbers = ""
    result.standardNumbers match {
      case Left(msg) => logger.info(msg)
      case Right(numbers) => standardNumbers = numbers.mkString(", ")
    }

    var luckyStars = ""
    result.luckyStars match {
      case Left(msg) => logger.info(msg)
      case Right(numbers) => luckyStars = numbers.mkString(", ")
    }
    s""" EuroMillions
       | Standard Numbers: $standardNumbers
       | LuckyStars: $luckyStars
        """.stripMargin
  }
}