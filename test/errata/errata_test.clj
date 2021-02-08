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

(defn deviding-by-zero []
 (let [result (e/init)]
   (try
    (/ 1 0)
    (catch Exception e 
      (assoc result :error (str "caught exception: " (.getMessage e)))))))

(deftest deviding-by-zero-err-handling
  (testing "(/ 1 0)"
    (let [result (deviding-by-zero)
          _ (println result)]
      (is false (e/ok? result))
      (is true (e/err? result)))))