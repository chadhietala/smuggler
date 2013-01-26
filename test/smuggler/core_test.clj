(ns smuggler.core-test
  (:use clojure.test
        smuggler.core))

(def sally (fill-bag dolls (- (count dolls) 1) 4))

(defstruct doll-struct :name :weight :value)

(def correct-map [
  (struct doll-struct "sally"       4    50)
  (struct doll-struct "eddie"       7    20)
  (struct doll-struct "grumpy"     22    80)
  (struct doll-struct "dusty"      43    75)
  (struct doll-struct "grumpkin"   42    70)
  (struct doll-struct "marc"       11    70)
  (struct doll-struct "randal"     27    60)
  (struct doll-struct "puppy"      15    60)
  (struct doll-struct "dorothy"    50   160)
  (struct doll-struct "candice"   153   200)
  (struct doll-struct "anthony"    13    35)
  (struct doll-struct  "luke"       9   150)
])


(deftest smuggler-test
  (testing "Dolls is a vactor"
    (is (vector? dolls)))

  (testing "Return a value of 0 and empty bag if Bag Size is 0"
    (is (= (fill-bag dolls (- (count dolls) 1) 0) [0 []])))

  (testing "Return a value of 0 and empty bag if the index is -1"
    (is (= (fill-bag dolls -1 20) [0 []])))

  (testing "Should return only [20]sally if Bag Size is 4"
      (is (= sally [50 [20]]) ))

  (testing "Should return all the dolls"
    (is (= (fill-bag dolls (- (count dolls) 1) 999999999) [1272 [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21]])))

  (testing "Cast String to Integer"
    (is (= (parse-int "123") 123)))

  (testing "Returns a list of maps of selected dolls"
    (is (every? map? (let [[total-value selected-dolls] (fill-bag dolls (- (count dolls) 1) 400)
                  dolls-in-bag (map dolls selected-dolls)] dolls-in-bag))))

  (testing "Returns the correct list of maps"
    (is (= (let [[total-value selected-dolls] (fill-bag dolls (- (count dolls) 1) 400)
                  dolls-in-bag (map dolls selected-dolls)] (reverse dolls-in-bag))  correct-map)))

  (testing "Returns the correct value"
    (is (= (let [[total-value selected-dolls] (fill-bag dolls (- (count dolls) 1) 400)]
     total-value)  1030)))
)
