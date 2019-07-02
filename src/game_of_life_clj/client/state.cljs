(ns game-of-life-clj.client.state
  (:require [reagent.core :as r]))

(defonce state (r/atom {:app {:width 80
                              :height 40
                              :rule "B3/S23"
                              :exit-ch nil}
                        :grid nil}))
