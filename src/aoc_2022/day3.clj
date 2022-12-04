(ns aoc-2022.day3
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def ex1
  "vJrwpWtwJgWrhcsFMMfFFhFp\njqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\nPmmdzqPrVvPwwTWBwg\nwMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\nttgJtRGJQctTZtZT\nCrZsJsPPZsGzwwsLwLmpwMDw")

(def data (slurp "resources/day3.txt"))


(defn split-middle [string]
  (let [nb-char (count string)]
    [(set (take (/ nb-char 2) string))
     (set (drop (/ nb-char 2) string))]))

(defn partition-by-3 [lines]
  (->> (map set lines)
       (partition 3)))

(defn solve1-fn [split-fn]
  (comp
   #(reduce + %)
   (fn [x] (map #(if (> (int %) 96)
                   (- (int %) 96)
                   (- (int %) 38)) x))
   #(map first %)
   (fn [x] (map #(apply set/intersection %) x))
   split-fn
   #(str/split-lines %)))

(comment
  ; solution 1
  ((solve1-fn split-middle) data)
  ; solution 2
  ((solve1-fn partition-by-3) data)
  ,)
