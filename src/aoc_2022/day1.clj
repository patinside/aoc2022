(ns aoc-2022.day1
  (:require [clojure.string :as str]))

(def ex1
  "1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000")

(def real-data1 (slurp "resources/day1-1.txt"))

(defn aoc1 [data n]
  (let [elves-str (-> data
                      (str/split #"\n\n"))]
    (->> (map (fn [x] (map parse-long
                       (str/split-lines x))) elves-str)
         (map #(apply + %))
         (sort >)
         (take n)
         (reduce +))))

(comment
  (aoc1 ex1),
  (aoc1 real-data1 1)
  (aoc1 real-data1 3))