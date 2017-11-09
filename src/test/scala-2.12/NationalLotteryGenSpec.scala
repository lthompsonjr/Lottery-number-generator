//import org.scalatest.{FlatSpec, Matchers}
//
///**
//  * Created by lth09 on 29/10/2017.
//  */
//class NationalLotteryGenSpec extends FlatSpec with Matchers {
//
//
//  "The National Lottery Generator" should "generate a provided set of numbers" in {
//    val expectedLotteryNumberCount = Right(6)
//    val numbers: Either[String, Int] = NationalLotteryGenerator.generate(6,7).right.map(_.length)
//
//
//    numbers.isRight shouldBe true
//    numbers shouldEqual expectedLotteryNumberCount
//  }
//
//  it should "print a message if the limit is less than the amount of distinct numbers to be generated" in {
//    val message = NationalLotteryGenerator.generate(10,7)
//    message shouldBe Left("cannot generate numbers with provided values")
//
//  }
//
//  it should "generate numbers ranging from 1 to 59" in {
//
//  }
//
//
//
//  "The Euro Millions Generator" should "generate a total of 7 numbers" in {
//    val x = EuroMillions.generate
//    println(x.standardNumbers)
//    println(x.luckyStars)
//  }
//
//  it should "Generate 5 numbers ranging from 1 to 50" in {
//
//  }
//
//  it should "Generate 2 numbers ranging from 1 to 12" in {
//
//  }
//}
