(ns aoc-2022.day6)

(def ex1 "mjqjpqmgbljsphdztnvjfqwrcgsmlb")

(def ex2 "bvwbjplbgvbhsrlpgdmjqwftvncz")

(def data (slurp "resources/day6.txt"))


(defn index-start-marker-detection [datastream marker-size]
  (->> (partition marker-size 1 datastream)
       (take-while #(not= (count (set %))
                          marker-size))
       count
       (+ marker-size)))

(comment
  ;solution1
  (index-start-marker-detection data 4),
  ;solution2
  (index-start-marker-detection data 14))