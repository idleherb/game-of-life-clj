(ns game-of-life-clj.core
  (:require [game-of-life-clj.grid :as g]
            [game-of-life-clj.render :as r])
  (:gen-class))

(defn- dead?
  [grid]
  (every? nil? (:v grid)))

(defn main
  [width height dead alive]
  (loop [grid (g/rand-grid width height)
         prev-grid nil
         gen 1]
    (cond
      (dead? grid)       (println (str "Dead in generation "   gen "."))
      (= grid prev-grid) (println (str "Stable in generation " gen "."))
      :else (do
              (println (str "-- Generation " gen "  --\n\n" (r/render-grid grid dead alive) "\n\n"))
              (recur (g/step grid)
                     grid
                     (inc gen))))))

(defn -main
  "Run a game of life simulation with optional given width and height"
  [& args]
  (if (= 2 (count args))
    (main (Integer/parseInt (first args))
          (Integer/parseInt (second args))
          " "
          "█")
    (main 150 22 " " "█")))
