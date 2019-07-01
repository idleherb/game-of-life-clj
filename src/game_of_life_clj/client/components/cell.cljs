(ns game-of-life-clj.client.components.cell)

(defn cell [cell-state]
  [:div {:class ["cell" (if cell-state :alive :dead)]}])
