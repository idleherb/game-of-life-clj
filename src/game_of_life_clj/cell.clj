(ns game-of-life-clj.cell)

(defn step
  [cell num-neighbours rule]
  (let [[birth survival] rule]
    (if cell
      (when (some #{num-neighbours} survival) :alive)
      (when (some #{num-neighbours} birth) :alive))))
