(ns aoc-2022.day4
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def ex1 "2-4,6-8\n2-3,4-5\n5-7,7-9\n2-8,3-7\n6-6,4-6\n2-6,4-8")

(def data (slurp "resources/day4.txt"))

(defn format-elmt [[[a b] [c d]]]
  [(set (range (Integer/parseInt a) (+ 1 (Integer/parseInt b))))
   (set (range (Integer/parseInt c) (+ 1 (Integer/parseInt d))))])

(defn one-subset-of-other? [[a b]]
  (or (set/subset? a b)
      (set/subset? b a)))

(defn one-ovelap-other? [[a b]]
  (seq (set/intersection (set a) b)))

(defn solve-fn [relation-fn]
  (comp
   count
   (fn [x] (remove #(or (false? %) (nil? %)) x))
   #(map relation-fn %)
   #(map format-elmt %)
   (fn [x] (map #(partition 2 %) x))
   (fn [x] (map #(str/split % #"[,-]") x))
   #(str/split-lines %)))

(comment
  ;solution 1
  ((solve-fn one-subset-of-other?) data)
  ;solution 2
  ((solve-fn one-ovelap-other?) data))