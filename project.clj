(defproject game-of-life-clj "0.1.0-SNAPSHOT"
  :description "A basic version of Conway's Game of Life"
  :url "https://github.com/idleherb/game-of-life-clj"
  :license {:name "The Unlicense"
            :url "https://unlicense.org/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.520"]
                 [org.clojure/core.async "0.4.490"]
                 [compojure "1.6.1"]
                 [org.immutant/web "2.1.10"]
                 [reagent "0.8.1"]
                 [ring/ring-core "1.7.1"]
                 [com.cognitect/transit-clj "0.8.313"]]
  :managed-dependencies [[org.clojure/core.rrb-vector "0.0.13"]
                         [org.flatland/ordered "1.5.7"]]
  :main ^:skip-aot game-of-life-clj.core
  :min-lein-version "2.0.0"
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-environ "1.1.0"]
            [lein-figwheel "0.5.18"]]
  :profiles {:dev {:dependencies [[midje "1.9.7" :exclusions [org.clojure/clojure]]]
                   :plugins [[lein-cloverage "1.1.1"]
                             [lein-midje "3.2.1"]]
                   :env {:dev? "true"}
                   :cljsbuild {:builds
                               [{:id "dev"
                                 :source-paths ["src" "dev"]
                                 :figwheel {}
                                 :compiler {:main game_of_life_clj.client.core
                                            :asset-path "js/compiled/out"
                                            :output-to "resources/public/js/compiled/app.js"
                                            :output-dir "resources/public/js/compiled/out"
                                            :source-map-timestamp true}}]}}
             :uberjar {:aot :all
                       :hooks [leiningen.cljsbuild]
                       :cljsbuild {:builds
                                   [{:id "min"
                                     :source-paths ["src" "prod"]
                                     :compiler {:main game_of_life_clj.client.core
                                                :output-to "resources/public/js/compiled/app.js"
                                                :optimizations :advanced
                                                :pretty-print false}}]}}}
  :uberjar-name "game-of-life-clj-standalone.jar"
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :figwheel {:css-dirs ["resources/public/css"]}
  :source-paths ["src"])
