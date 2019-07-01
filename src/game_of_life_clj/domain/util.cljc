(ns game-of-life-clj.domain.util
  (:require [clojure.string :as str]))

(defn parse-int [s]
  #?(:clj  (java.lang.Integer/parseInt s)
     :cljs (js/parseInt s)))

(defn parse-rule
  "Parse rules in RLE syntax, e.g. B3/S23"
  [rule]
  (let [[birth survival] (str/split rule #"/")
        birth (->> (str/split (subs birth 1) #"")
                   (filterv not-empty)
                   (mapv #(parse-int %)))
        survival (->> (str/split (subs survival 1) #"")
                      (filterv not-empty)
                      (mapv #(parse-int %)))]
    [birth survival]))
