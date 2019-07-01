(ns game-of-life-clj.server.core
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [immutant.web :as web]
            [immutant.web.middleware :as web-middleware]
            [ring.util.response :refer [redirect]]))

(defroutes routes
  (GET "/" {c :context} (redirect (str c "/index.html")))
  (route/resources "/"))

(defn run
  [host port]
  (web/run (-> routes
               (web-middleware/wrap-session {:timeout 20}))
           {"host" host, "port" port}))
