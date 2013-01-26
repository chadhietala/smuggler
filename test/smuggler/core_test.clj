(ns smuggler.core-test
  (:use clojure.test
        smuggler.core))

(def sally (fill-bag dolls (- (count dolls) 1) 4) )



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
)
