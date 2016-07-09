(defproject fwpd "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"
            :year 2016
            :key "mit"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot fwpd.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
