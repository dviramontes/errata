(ns errata.errata-test
  (:require [clojure.test :refer :all]
            [errata.errata :as e]))

(deftest errata-test
  (testing "empty result"
    (is (= true (some? (e/init)))))
  (testing "keys as functions of their maps and viceversa"
    (is (= :ok (:value (e/init :ok))))
    (is (= :ok ((e/init :ok) :value))))
  (testing
   " [error | result] boundary,
     if we say result has a value; it can't also contain an error"
    (let [ok-result (e/ok! (-> (e/init)) :ok)
          not-ok-result (e/err! (-> (e/init)) :not-ok)]
      (is (true? (e/ok? ok-result)))
      (is (= :ok (.value ok-result)))
      (is (false? (e/err? ok-result)))
      (is (true? (e/err? not-ok-result)))
      (is (= :not-ok (.error not-ok-result)))
      (is (false? (e/ok? not-ok-result)))
      (is (nil? (.value not-ok-result)))))
  (testing "ok / err getter methods"
    (is (= 123 (e/ok (e/init 123))))
    (is (= :new-error (e/err (e/err! (e/init) :new-error)))))
  (testing "predicate functions"
    (is (true? (e/ok? (e/init 123))))
    (is (false? (e/err? (e/init 123))))
    (is (false? (e/ok? (e/init))))
    (is (false? (e/err? (e/init))))
    (is (true? (e/err? (e/err! (e/init) :oh-no!))))))

(defn dividing-one-by [x]
 (let [result (e/init)]
   (try
    (-> result (e/ok! (/ 1 x)))
    (catch Exception e
      (-> result
          (e/err! (str "caught exception: " (.getMessage e))))))))

(deftest dividing-one-by-zero-err-handling
  (testing "(/ 1 0)"
    (let [result (dividing-one-by 0)
          error-message "caught exception: Divide by zero"]
      (is (= false (e/ok? result)))
      (is (= true (e/err? result)))
      (is (= error-message (:error result)))))
  (testing "(/ 1 1)"
    (let [result (dividing-one-by 1)]
      (is (= true (e/ok? result)))
      (is (= false (e/err? result)))
      (is (= 1 (:value result))))))
