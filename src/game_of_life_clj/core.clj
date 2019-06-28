(ns game-of-life-clj.core
  (:gen-class))

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

(defn- cell-step
  [grid coords]
  (let [cell (cell-at grid coords)
        num-neighbours (count-alive-neighbours grid coords)
        new-cell (if cell
                   (cond
                     (< num-neighbours 2) nil
                     (<= 2 num-neighbours 3) :alive
                     :else nil)
                   (when (= num-neighbours 3) :alive))]
      (assoc-in grid [:v (cell-index grid coords)] new-cell)))

(defn step
  [grid]
  (let [{:keys [width height]} grid]
    (reduce
     (fn [agg-grid i]
       (let [coords (cell-coords grid i)
             cell-step-grid (cell-step grid coords)]
         (assoc-in agg-grid [:v i]
            (get-in cell-step-grid [:v i]))))
     grid (range (* width height)))))

(defn- style-row
  [row]
  (clojure.string/join "" (mapv #(if % "█" " ") row)))

(defn- print-grid
  [grid]
  (let [{:keys [width height v]} grid]
    (println (str (clojure.string/join "\n"
     (mapv (fn [i]
            (style-row (subvec v (* i width) (* (inc i) width)))) (range height))) "\n\n"))))

(defn- rand-cell
  []
  (if (< 0.5 (rand))
    nil
    :alive))

(defn- dead?
  [grid]
  (every? nil? (:v grid)))

(defn- simulate
  [width height]
  (let [grid {:width width
              :height height
              :v (into [] (for [_ (range (* width height))] (rand-cell)))}]
    (loop [grid grid
           prev-grid nil
           gen 1]
      (cond
        (dead? grid)       (println (str "Dead in generation "   gen "."))
        (= grid prev-grid) (println (str "Stable in generation " gen "."))
        :else (do
                (Thread/sleep 15)
                (print-grid grid)
                (recur (step grid)
                       grid
                       (inc gen)))))))

(defn -main
  "Run a game of life simulation with optional given width and height"
  [& args]
  (if (= 2 (count args))
    (simulate (Integer/parseInt (first args))
              (Integer/parseInt (second args)))
    (simulate 150 22)))
