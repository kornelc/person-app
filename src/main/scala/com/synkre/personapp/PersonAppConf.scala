package com.synkre.personapp

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}

case class PersonAppConf(personStoreConf: PersonStoreConf)
object PersonAppConf{
  def loadConf(path: String): PersonAppConf = {
    val mainConfig = ConfigFactory.parseFile(new File(path))
    PersonAppConf(
      loadPersonStoreConf(mainConfig.getConfig("personApp.personStore"))
    )
  }
  private def loadPersonStoreConf(parentConf: Config): PersonStoreConf ={
    PersonStoreConf(parentConf.getString("writeLocation"))
  }
}
