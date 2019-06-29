(ns game-of-life-clj.render)

(defn- render-row
  [row dead alive]
  (clojure.string/join "" (mapv #(if % alive dead) row)))

(defn- row
  [grid i]
  (let [{:keys [width v]} grid]
    (subvec v (* i width) (* (inc i) width))))

(defn render-grid
  [grid dead alive]
  (let [height (:height grid)]
    (clojure.string/join
     "\n"
     (mapv #(render-row (row grid %) dead alive)
           (range height)))))
