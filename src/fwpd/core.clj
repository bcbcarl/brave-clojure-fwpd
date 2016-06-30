(ns fwpd.core
  (:gen-class))

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def map->name (partial map :name))

(defn glitter-filter-new
  [minimum-glitter records]
  (map->name (glitter-filter minimum-glitter records)))

(defn validate
  [x y]
  (= (set (keys x)) (set (keys y))))

(defn append
  "Append new suspect to the suspect list."
  [coll x]
  (if (validate conversions x)
    (concat coll (seq [x]))
    coll))

(defn- suspect->str
  [record]
  (clojure.string/join "," [(:name record) (:glitter-index record)]))

(defn suspects->str
  [records]
  (clojure.string/join "\n" (map suspect->str records)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [filename "suspects.csv"
        records (mapify (parse (slurp filename)))]
    (glitter-filter-new 3 records)
    (append records {:name "Carl Su" :glitter-index 2})
    (validate conversions {:name "Carl Su" :glitter-index 2})
    (suspects->str records)))
