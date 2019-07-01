(defproject game-of-life-clj "0.1.0-SNAPSHOT"
  :description "A basic version of Conway's Game of Life"
  :url "https://github.com/idleherb/game-of-life-clj"
  :license {:name "The Unlicense"
            :url "https://unlicense.org/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot game-of-life-clj.core
  :min-lein-version "2.0.0"
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[midje "1.9.7" :exclusions [org.clojure/clojure]]]
                   :plugins [[lein-cloverage "1.1.1"]
                             [lein-midje "3.2.1"]]}
             :uberjar {:aot :all}})
