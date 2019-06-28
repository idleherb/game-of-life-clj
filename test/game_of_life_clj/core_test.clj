(ns game-of-life-clj.core-test
  (:require [midje.sweet :refer [fact facts => =not=>]]
            [game-of-life-clj.core :as gol]))

(def empty-grid {:width 3
                 :height 3
                 :v [nil nil nil
                     nil nil nil
                     nil nil nil]})

(facts "About a step in the game of life"
  (fact "A grid of only dead cells doesn't change"
    (println "T001")
    (gol/step empty-grid) => empty-grid)
       
  (fact "An alive cell with no neighbours dies. Outside grid cells are dead by default."
    (println "T002.1")
    (let [grid {:width 3
                :height 3
                :v [nil nil    nil
                    nil :alive nil
                    nil nil    nil]}]
      (gol/step grid) => empty-grid)
    (println "T002.2")
    (let [grid {:width 3
                :height 3
                :v [:alive nil :alive
                    nil    nil nil
                    :alive nil :alive]}]
      (gol/step grid) => empty-grid))
       
  (fact "An alive cell with two or three neighbours lives on"
    (println "T003.1")
    (let [grid {:width 3
                :height 3
                :v [:alive :alive nil
                    :alive nil    nil
                    nil    nil    nil]}
          exp-grid {:width 3
                    :height 3
                    :v [:alive :alive nil
                        :alive :alive nil
                        nil    nil    nil]}]
      (gol/step grid) => exp-grid)
    (println "T003.2")
    (let [grid {:width 3
                :height 3
                :v [:alive nil    :alive
                    nil    :alive nil
                    :alive nil    nil]}
          exp-grid {:width 3
                    :height 3
                    :v [nil    :alive nil
                        :alive :alive nil
                        nil    nil    nil]}]
      (gol/step grid) => exp-grid))
       
  (fact "An alive cell with more than three neighbours dies"
    (println "T004")
    (let [grid {:width 3
                :height 3
                :v [:alive nil    :alive
                    nil    :alive nil
                    :alive nil    :alive]}
          exp-grid {:width 3
                    :height 3
                    :v [nil    :alive nil
                        :alive nil    :alive
                        nil    :alive nil]}]
      (gol/step grid) => exp-grid))
       
  (fact "An dead cell with exactly three neighbours comes to life"
    (println "T005")
    (let [grid {:width 3
                :height 3
                :v [:alive nil :alive
                    nil    nil nil
                    :alive nil nil]}
          exp-grid {:width 3
                    :height 3
                    :v [nil nil    nil
                        nil :alive nil
                        nil nil    nil]}]
      (gol/step grid) => exp-grid))
)
