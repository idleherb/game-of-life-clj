(ns game-of-life-clj.client.actions
  (:require [cljs.core.async :as a
                             :include-macros true]
            [game-of-life-clj.client.state :as s]
            [game-of-life-clj.domain.grid :as g]
            [game-of-life-clj.domain.util :as u]))

(defn- dead?
  [grid]
  (every? nil? (:v grid)))

(defn run-game
  [width height rule]
  (let [rule (u/parse-rule rule)]
    (a/go-loop [grid (g/rand-grid width height)
                prev-grid nil
                gen 1]
      (a/<! (a/timeout 5))
      (cond
        (dead? grid)       (println (str "Dead in generation "   gen "."))
        (= grid prev-grid) (println (str "Stable in generation " gen "."))
        :else (do
                (swap! s/state assoc :grid grid)
                (recur (g/step grid rule)
                       grid
                       (inc gen)))))))
