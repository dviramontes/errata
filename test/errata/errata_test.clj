(ns errata.errata-test
  (:require [clojure.test :refer :all]
            [errata.errata :as e]))

(deftest errata-test
  (testing "empty result"
    (is (= true (some? (e/init)))))
  (testing "keys as functions of their maps and viceversa"
    (is (= :ok (:value (e/init :ok))))
    (is (= :ok ((e/init :ok) :value))))
  (testing "predicate functions"
    (is (= true (e/ok? (->(e/init 123)))))
    (is (= false (e/err? (->(e/init 123)))))
    (is (= false (e/ok? (->(e/init nil :err)))))
    (is (= true (e/err? (->(e/init nil :err)))))))

(defn dividing-one-by [x]
 (let [result (e/init)]
   (try
    (assoc result :value (/ 1 x))
    (catch Exception e 
      (assoc result :error (str "caught exception: " (.getMessage e)))))))

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