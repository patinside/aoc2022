(ns aoc-2022.day2
  (:require [clojure.string :as str]))

(def ex1 "A Y\nB X\nC Z")

(def data (slurp "resources/day2.txt"))

(def parse-fn-1
  (comp
   (fn [coll] (map (fn [[p1 p2]] (case p2
                                   "X" [p1 "A"]
                                   "Y" [p1 "B"]
                                   "Z" [p1 "C"])) coll))
   (fn [coll] (map #(re-seq #"\w" %) coll))
   #(str/split-lines %)))

(defn loose [p1]
  (case p1
    "A" "C"
    "B" "A"
    "C" "B"))

(defn win [p1]
  (case p1
    "A" "B"
    "B" "C"
    "C" "A"))

(defn round-score [[p1 p2]]
  (let [shape-score (case p2
                      "A" 1
                      "B" 2
                      "C" 3)
        outcome (case [p1 p2]
                  ["A" "A"] 3
                  ["A" "B"] 6
                  ["A" "C"] 0
                  ["B" "A"] 0
                  ["B" "B"] 3
                  ["B" "C"] 6
                  ["C" "A"] 6
                  ["C" "B"] 0
                  ["C" "C"] 3)]
    (+ shape-score outcome)))

(def parse-fn-2
  (comp
   (fn [coll] (map (fn [[p1 p2]] (case p2
                                   "X" [p1 (loose p1)]
                                   "Y" [p1 p1]
                                   "Z" [p1 (win p1)])) coll))
   (fn [coll] (map #(re-seq #"\w" %) coll))
   #(str/split-lines %)))



(def solve-fn
  (comp
   (map round-score)
   parse-fn-1))

(comment
  (->> ex1
       parse-fn-1
       (map round-score)
       (reduce +))

  (->> data
       parse-fn-2
       (map round-score))
  ,)