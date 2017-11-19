val words = Map("q" -> 1, "qw" -> 3, "wswq" -> 5)


val dataMap = Map("q" -> 3, "22" -> 3, "wswq" -> 1)



dataMap.foldLeft(words) { (mainMap, mapEntry) => mainMap + (mapEntry._1 -> (mainMap.getOrElse(mapEntry._1, 0) + mapEntry._2)) }