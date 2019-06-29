(ns game-of-life-clj.util
  (:require [clojure.string :as str]))

(defn parse-rule
  "Parse rules in Golly syntax, e.g. B3/S23"
  [rule]
  (let [[birth survival] (str/split rule #"/")
        birth (->> (str/split (subs birth 1) #"")
                   (filterv not-empty)
                   (mapv #(Integer/parseInt %)))
        survival (->> (str/split (subs survival 1) #"")
                      (filterv not-empty)
                      (mapv #(Integer/parseInt %)))]
    [birth survival]))
