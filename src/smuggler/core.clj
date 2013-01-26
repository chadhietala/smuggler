(ns smuggler.core)

; Our drug dolls to choose from
(def drug-dolls
  [ "luke"        9   150
    "anthony"    13    35
    "candice"   153   200
    "dorothy"    50   160
    "puppy"      15    60
    "thomas"     68    45
    "randal"     27    60
    "april"      39    40
    "nancy"      23    30
    "bonnie"     52    10
    "marc"       11    70
    "kate"       32    30
    "tbone"      24    15
    "tranny"     48    10
    "uma"        73    40
    "grumpkin"   42    70
    "dusty"      43    75
    "grumpy"     22    80
    "eddie"       7    20
    "tory"       18    12
    "sally"       4    50
    "babe"       30    10])

; Generate a single doll data structure with the correct keys
(defstruct doll :name :weight :value)

; Creates a new doll vector
(def dolls (vec (map #(apply struct doll %) (partition 3 drug-dolls))))

; Create a reference to cached bag but no bindings attached to it
(declare memoized-bag)


(defn fill-bag [dolls-available index size-of-handbag]
  (cond
    ; Return 0 if the size of the handbag is 0 or index < 0
    (< index 0) [0 []]
    (= size-of-handbag 0) [0 []]
    :else

    ; Assign doll-weight and doll-value based on dolls index lookup
    (let [{doll-weight :weight doll-value :value} (get dolls-available index)]
      (if (> doll-weight size-of-handbag)
        ; The passed in doll doesn't fit... recurse
        (memoized-bag dolls-available (dec index) size-of-handbag)

        ; Otherwise calculate the size of the bag with the item in it
        (let [ new-handbag-size (- size-of-handbag doll-weight)
              ; Create alias to the skip/keep vectors and the value of that matrix
              [value-if-skip no-vec :as no] (memoized-bag dolls-available (dec index) size-of-handbag)
              [value-if-keep yes-vec :as yes] (memoized-bag dolls-available (dec index) new-handbag-size)]

          ; if the value of keeping is greater than the value of skipping
          (if (> (+ value-if-keep doll-value) value-if-skip)
            ; keep the doll
            ; increase the value if keept and use conj to push the index into the yes vector
            [(+ value-if-keep doll-value) (conj yes-vec index)]
            ; skip the doll 
            no))))))

; memoize each fill-bag function call
(def memoized-bag (memoize fill-bag))

; make sure the input is an Int
(defn parse-int [string]
  (Integer. (re-find #"[0-9]*" string)))

; Setup a prompt to ask the weight
(defn prompt-weight [prompt]
  (print (format "%s: " prompt))
  (flush)
  (parse-int (read-line)))

(defn -main []
  (let [max-weight (prompt-weight "How much can the bag hold?")
        ; Destructured assignment to reference first and second item in fill-bag result
        [total-value selected-dolls] (fill-bag dolls (- (count dolls) 1) max-weight)
        ; return selected dolls as a map
        dolls-in-bag (map dolls selected-dolls)
    ]
    (println "Value:" total-value)
    (println "Dolls:" (reverse dolls-in-bag))))