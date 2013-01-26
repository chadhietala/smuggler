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

; Creates a new doll-structure
(def dolls (vec (map #(apply struct doll %) (partition 3 drug-dolls))))

; Create a reference to cached bag but no bindings attached to it
(declare memoized-bag)

(defn fill-bag
  "Fill the handbag. Pass in info from generate-shipment, count of dolls and size of handbag"
  [dolls-available index size-of-handbag]
  (cond
    ; Return 0 if the size of the handbag is 0 or index < 0
    (< index 0) [0 []]
    (= size-of-handbag 0) [0 []]
    :else

    (let [{doll-weight :weight doll-value :value} (get dolls-available index)]
      (if (> doll-weight size-of-handbag)
        ; Doll doesn't fit call again
        (memoized-bag dolls-available (dec index) size-of-handbag)
        ; else
        (let [ new-handbag-size (- size-of-handbag doll-weight) ; Calculate the size of the bag with items in it
              [vn sn :as no] (memoized-bag dolls-available (dec index) size-of-handbag)
              [vy sy :as yes] (memoized-bag dolls-available (dec index) new-handbag-size)]

          (if (> (+ vy doll-value) vn)
            ; keep
            [(+ vy doll-value) (conj sy index)]
            ; skip / shortcut
            no))))))

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
        [total-value selected-dolls] (fill-bag dolls (- (count dolls) 1) max-weight)
        doll-names (map (comp :name dolls) selected-dolls)
        doll-values (map (comp :value dolls) selected-dolls)
        doll-weight (map (comp :weight dolls) selected-dolls)
    ]
    (println "Street Value:" total-value)
    (println "Selected Dolls:" (reverse doll-names) (reverse doll-values) (reverse doll-weight))
    ))