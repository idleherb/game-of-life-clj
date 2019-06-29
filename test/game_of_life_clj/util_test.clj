(ns game-of-life-clj.util-test
  (:require [midje.sweet :refer [fact facts => tabular]]
            [game-of-life-clj.util :as u]))

(facts "About utilities"
  (tabular
   (fact "RLE-formatted rules can be parsed"
      (u/parse-rule ?rule) => ?parsed-rule)
      ?rule           ?parsed-rule
      "B1357/S1357"   [[1 3 5 7] [1 3 5 7]]
      "B2/S"          [[2] []]
      "B25/S4"        [[2 5] [4]]
      "B3/S012345678" [[3] [0 1 2 3 4 5 6 7 8]]
      "B3/S23"        [[3] [2 3]]
      "B34/S34"       [[3 4] [3 4]]
      "B35678/S5678"  [[3 5 6 7 8] [5 6 7 8]]
      "B36/S125"      [[3 6] [1 2 5]]
      "B36/S23"       [[3 6] [2 3]]
      "B3678/S34678"  [[3 6 7 8] [3 4 6 7 8]]
      "B368/S245"     [[3 6 8] [2 4 5]]
      "B4678/S35678"  [[4 6 7 8] [3 5 6 7 8]]))
