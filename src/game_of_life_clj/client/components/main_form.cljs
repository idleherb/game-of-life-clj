(ns game-of-life-clj.client.components.main-form
  (:require [clojure.string :as str]
            [reagent.core :as r]
            [game-of-life-clj.client.actions :as a]
            [game-of-life-clj.client.state :as s]))

(defn- sanitize-min-max
  [val min max]
  (cond
    (< val min) min
    (> val max) max
    :else val))

(defn- sanitize-dimension
  [dim min max]
  (let [dim (sanitize-min-max dim min max)]
    (if (odd? dim)
      dim
      (dec dim))))

(defn- on-click-run-game
  [width height rule]
  (let [width (sanitize-dimension width 10 200)
        height (sanitize-dimension height 10 200)
        rule (str/trim rule)
        main-form-state (-> (:app @s/state)
                            (assoc :width width
                                   :height height
                                   :rule rule))]
    (swap! s/state assoc :app main-form-state)
    (a/run-game! width height rule)))

(defn- on-key-down-input
  [code width height rule]
  (condp = code
    13 (on-click-run-game width height rule)
    nil))

(defn main-form
  [main-form-state]
  (let [{:keys [width height rule]} main-form-state
        !ref (atom nil)
        width-input (r/atom width)
        height-input (r/atom height)
        rule-input (r/atom rule)]
    (r/create-class
     {:component-did-mount #(some-> @!ref .focus)
      :reagent-render
      (fn []
         [:div {:class "main-form"}
          [:label {:for "in-width"} "Width"
           [:input {:name "in-width"
                    :value @width-input
                    :type "number"
                    :min 10
                    :max 200
                    :required true
                    :on-change #(let [num (-> % .-target .-value (js/parseInt))]
                                  (when-not (js/Number.isNaN num)
                                    (reset! width-input num)))
                    :on-key-down #(on-key-down-input (.-keyCode %)
                                                     @width-input
                                                     @height-input
                                                     @rule-input)}]]
          [:label {:for "in-height"} "Height"
           [:input {:name "in-height"
                    :value @height-input
                    :type "number"
                    :min 10
                    :max 200
                    :required true
                    :on-change #(let [num (-> % .-target .-value (js/parseInt))]
                                  (when-not (js/Number.isNaN num)
                                    (reset! height-input num)))
                    :on-key-down #(on-key-down-input (.-keyCode %)
                                                     @width-input
                                                     @height-input
                                                     @rule-input)}]]
          [:label {:for "in-rule"} "RLE Rule"
           [:input {:ref #(reset! !ref %)
                    :name "in-rule"
                    :value @rule-input
                    :type "text"
                    :required true
                    :on-change #(reset! rule-input (-> % .-target .-value))
                    :on-key-down #(on-key-down-input (.-keyCode %)
                                                     @width-input
                                                     @height-input
                                                     @rule-input)}]]
          [:div {:class "buttons"}
           [:button {:class "primary"
                     :on-click #(on-click-run-game @width-input
                                                   @height-input
                                                   @rule-input)} "Run Game"]]])})))
