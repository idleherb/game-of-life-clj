(ns game-of-life-clj.domain.grid
  (:require [game-of-life-clj.domain.cell :as c]))

(defn- rand-cell
  []
  (if (< 0.5 (rand))
    nil
    :alive))

(defn rand-grid
  [width height]
  {:width width
   :height height
   :v (into [] (for [_ (range (* width height))] (rand-cell)))})

(defn- cell-coords
  [grid i]
  (let [width (:width grid)]
    [(mod i width)
     (int (/ i width))]))

(defn- cell-index
  [grid coords]
  (let [{:keys [width height]} grid
        [x y] coords]
    (if (and (<= 0 x (dec width))
             (<= 0 y (dec height)))
      (+ (* y width) x)
      (throw (Exception. (str "invalid coords: " coords
                              "(width: " width ", height: " height ")"))))))

(defn- cell-at
  [grid coords]
  (nth (:v grid) (cell-index grid coords)))

(defn- count-alive-neighbours
  [grid coords]
  (let [{:keys [width, height]} grid
        [x y] coords]
    (->> [(when (and (> x 0)           (> y 0))            (cell-at grid [(dec x) (dec y)]))
          (when                        (> y 0)             (cell-at grid [x       (dec y)]))
          (when (and (< x (dec width)) (> y 0))            (cell-at grid [(inc x) (dec y)]))
          (when      (> x 0)                               (cell-at grid [(dec x) y]))
          (when                        (< x (dec width))   (cell-at grid [(inc x) y]))
          (when (and (> x 0)           (< y (dec height))) (cell-at grid [(dec x) (inc y)]))
          (when                        (< y (dec height))  (cell-at grid [x       (inc y)]))
          (when (and (< x (dec width)) (< y (dec height))) (cell-at grid [(inc x) (inc y)]))]
         (filterv some?)
         (count))))

(defn step
  ([grid rule]
   (let [{:keys [width height]} grid]
     (reduce
      (fn [agg-grid i]
        (let [coords (cell-coords grid i)
              num-neighbours (count-alive-neighbours grid coords)
              cell (nth (:v grid) i)
              new-cell (c/step cell num-neighbours rule)]
          (assoc-in agg-grid [:v i] new-cell)))
      grid (range (* width height)))))
  ([grid]
   (step grid [[3] [2 3]])))
