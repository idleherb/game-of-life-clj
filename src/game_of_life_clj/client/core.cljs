(ns ^:figwheel-always game-of-life-clj.client.core
  (:require [reagent.core :as r]
            [game-of-life-clj.client.components.app :refer [app]]))

(enable-console-print!)

(r/render-component [app]
                    (.getElementById js/document "app"))
