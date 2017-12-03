package com.louis.lottogenerator.generator

import scala.collection.mutable.ListBuffer

/**
  * Created by lth09 on 29/10/2017.
  */
trait NationalLotteryGenerator {

   private def generate(amountOfNumbers: Int, upperLimit: Int): Either[String,List[Int]] = {
      val lottoList = new ListBuffer[Int]()
      if (amountOfNumbers > upperLimit) {
         Left("cannot generate numbers with provided values")
      }
      else Right(helper(amountOfNumbers, upperLimit, lottoList))
   }

   private def helper(amountOfNumbers: Int, upperLimit: Int, lottoList: ListBuffer[Int]): List[Int] = {
      while ((lottoList.distinct.length + 1) <= amountOfNumbers) {
         scala.util.Random.nextInt(upperLimit) match {
            case (randomNumber) => if (randomNumber > 0) {
               lottoList += randomNumber
            }
         }
      }
      lottoList.distinct.toList
   }
}

object NationalLotteryGenerator extends NationalLotteryGenerator {
   def generate(amountOfNumbers: Int, upperLimit: Int) = {
      super.generate(amountOfNumbers,upperLimit)
   }
}
