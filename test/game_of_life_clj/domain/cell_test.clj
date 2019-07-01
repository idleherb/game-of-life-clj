(ns game-of-life-clj.domain.cell-test
  (:require [midje.sweet :refer [fact facts =>]]
            [game-of-life-clj.domain.cell :as c]
            [game-of-life-clj.domain.util :as u]))

(facts "About cells"
  (fact "A cell step runs the given rule"
    (let [rule (u/parse-rule "B3/S23")]
      (c/step nil 0 rule) => nil
      (c/step nil 1 rule) => nil
      (c/step nil 2 rule) => nil
      (c/step nil 3 rule) => :alive
      (c/step nil 4 rule) => nil
      (c/step nil 5 rule) => nil
      (c/step nil 6 rule) => nil
      (c/step nil 7 rule) => nil
      (c/step nil 8 rule) => nil

      (c/step :alive 0 rule) => nil
      (c/step :alive 1 rule) => nil
      (c/step :alive 2 rule) => :alive
      (c/step :alive 3 rule) => :alive
      (c/step :alive 4 rule) => nil
      (c/step :alive 5 rule) => nil
      (c/step :alive 6 rule) => nil
      (c/step :alive 7 rule) => nil
      (c/step :alive 8 rule) => nil)))
