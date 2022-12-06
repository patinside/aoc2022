(ns aoc-2022.day5
  (:require [clojure.string :as str]))

(def ex1 "    [D]    \n[N] [C]    \n[Z] [M] [P]\n 1   2   3 \n\nmove 1 from 2 to 1\nmove 3 from 1 to 3\nmove 2 from 2 to 1\nmove 1 from 1 to 2")
(def data (slurp "resources/day5.txt"))


(defn clean-line [line]
  (map (fn [x] (remove #(#{\space \[ \]} %) x)) line))

(defn empty-stack [stack nb]
  (let [nil-part (take-while nil? stack)
        non-nil-part (drop-while nil? stack)]
    (concat nil-part (repeat nb nil) (drop nb non-nil-part))))

(defn grant-stack [stack to-move]
  (let [non-nil-part (drop-while nil? stack)
        new-non-nil-part (concat to-move non-nil-part)]
    (concat (repeat (- (count stack)
                       (count new-non-nil-part)) nil)
            new-non-nil-part)))

(defn move [stacks [nb from to]]
  (let [nb (Integer/parseInt nb)
        from (- (Integer/parseInt from) 1)
        to (- (Integer/parseInt to) 1)
        stack-to-empty (get stacks from)
        stack-to-grant (get stacks to)
        emptied-stack (empty-stack stack-to-empty nb)
        ;solution1
        ;to-move (take nb (reverse (remove nil? stack-to-empty)))
        ;solution2
        to-move (take nb (remove nil? stack-to-empty))
        granted-stack (grant-stack stack-to-grant to-move)
        _ (prn stacks  from emptied-stack to granted-stack)]
    (assoc stacks from emptied-stack to granted-stack)))

(def mk-clean-stacks-xf
  (comp

   (fn [x] (map #(map first %) x))))

(defn mk-clean-stacks [stacks]
  (->> (butlast stacks)
       (map #(partition-all 4  %))
       (map clean-line)
       (apply map vector)
       (mapv #(map first %))))

(defn mk-clean-inst [inst]
  (->> (str/split-lines inst)
       (mapv #(str/split % #" "))
       (mapv #(remove #{"move" "from" "to"} %))))

(comment
  (let [[stacks inst] (str/split data #"\n\n")
        stacks (str/split stacks #"\n")
        nb-stacks (->> stacks last (re-seq #"\d") count)
        clean-stacks (mk-clean-stacks stacks)
        clean-inst (mk-clean-inst inst)]
    (->> (reduce move clean-stacks clean-inst)
        (map #(->> (remove nil? %)
                   (first)))
         (apply str)))
  ,)
