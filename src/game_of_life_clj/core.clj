(ns game-of-life-clj.core
  (:require [game-of-life-clj.grid :as g]
            [game-of-life-clj.render :as r]
            [game-of-life-clj.util :as u])
  (:gen-class))

(defn- dead?
  [grid]
  (every? nil? (:v grid)))

(defn main
  [width height rule dead alive]
  (loop [grid (g/rand-grid width height)
         prev-grid nil
         gen 1]
    (cond
      (dead? grid)       (println (str "Dead in generation "   gen "."))
      (= grid prev-grid) (println (str "Stable in generation " gen "."))
      :else (do
              (println (str "-- Generation " gen "  --\n\n" (r/render-grid grid dead alive) "\n\n"))
              (recur (g/step grid rule)
                     grid
                     (inc gen))))))

(defn -main
  "Run a game of life simulation with optional given width and height"
  [& args]
  (if (= 3 (count args))
    (main (Integer/parseInt (first args))
          (Integer/parseInt (second args))
          (u/parse-rule (nth args 2))
          " "
          "█")
    (main 150 22 (u/parse-rule "B3/S23") " " "█")))
