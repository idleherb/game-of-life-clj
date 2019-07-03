(ns game-of-life-clj.client.components.grid
  (:require [reagent.core :as r]))

(defn- render-canvas
  [canvas grid]
  (let [{:keys [width height v]} grid
        _ (set! (.-width  canvas) width)
        _ (set! (.-height canvas) height)
        ctx (.getContext canvas "2d")]
    (let [image-data (.createImageData ctx width height)]
       (doseq [[cell-idx cell] (map-indexed vector v)]
               (let [image-data-idx (* cell-idx 4)]
                 (when cell
                   (do
                     (aset (.-data image-data) image-data-idx       236)
                     (aset (.-data image-data) (+ image-data-idx 1)  64)
                     (aset (.-data image-data) (+ image-data-idx 2) 122)
                     (aset (.-data image-data) (+ image-data-idx 3) 255)))))
      (.putImageData ctx image-data 0 0))))

(defn grid
  [grid-state]
  (r/create-class
   {:component-did-update #(render-canvas (.-firstChild (r/dom-node %))
                                          (first (rest (r/argv %))))
    :reagent-render
    (fn [grid-state]
      (let [{:keys [width height]} grid-state]
        [:div {:style {:width  (str (* 5 width)  "px")
                       :height (str (* 5 height) "px")}}
         [:canvas {:class "grid"}]]))}))
