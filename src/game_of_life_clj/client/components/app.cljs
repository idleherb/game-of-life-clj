(ns game-of-life-clj.client.components.app
  (:require [game-of-life-clj.client.components.grid :refer [grid]
                                                     :rename {grid el-grid}]
            [game-of-life-clj.client.components.main-form :refer [main-form]
                                                          :rename {main-form el-main-form}]
            [game-of-life-clj.client.state :as s]))

(defn app []
  (let [{:keys [app grid]} @s/state]
    [:div {:class "app col"}
     [el-main-form app]
     [el-grid grid]]))
