(ns errata.errata-test
  (:require [clojure.test :refer :all]
            [errata.errata :as errata]))

(deftest a-test
  (testing "keys as functions to maps and viceversa"
    (is (= :ok (:value (errata/init :ok))))
    (is (= :ok ((errata/init :ok) :value)))))
