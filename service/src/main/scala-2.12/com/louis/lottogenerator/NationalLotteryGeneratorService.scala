package com.louis.lottogenerator

import com.louis.lottogenerator.email.EmailClient
import com.louis.lottogenerator.euromillions.EuroMillions
import com.louis.lottogenerator.lotto.Lotto

/**
  * Created by lth09 on 03/12/2017.
  */
object NationalLotteryGeneratorService extends App {
  EmailClient.sendEmail(EuroMillions.message +"\n" + Lotto.message)
}
