(ns game-of-life-clj.client.actions
  (:require [cljs.core.async :as a
                             :include-macros true]
            [game-of-life-clj.client.state :as s]
            [game-of-life-clj.domain.grid :as g]
            [game-of-life-clj.domain.util :as u]))

(defn- dead?
  [grid]
  (every? nil? (:v grid)))

(defn- reset-game!
  []
  (let [new-exit-ch (a/chan)]
    (when-let [exit-ch (get-in @s/state [:app :exit-ch])]
      (a/go (a/>! exit-ch true)))
    (swap! s/state assoc-in [:app :exit-ch] new-exit-ch)
    new-exit-ch))

(defn run-game!
  [width height rule]
  (let [rule (u/parse-rule rule)
        exit-ch (reset-game!)]
    (a/go-loop [grid (g/rand-grid width height)
                prev-grid nil
                gen 1]
      (a/alt!
        exit-ch nil
        (a/timeout 5) (cond
          (dead? grid)       (println (str "Dead in generation "   gen "."))
          (= grid prev-grid) (println (str "Stable in generation " gen "."))
          :else (do
                  (swap! s/state assoc :grid grid)
                  (recur (g/step grid rule)
                         grid
                         (inc gen))))))))
