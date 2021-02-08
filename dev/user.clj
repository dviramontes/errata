(ns user
  (:require [errata.errata :as errata]))

(comment 
  (errata/init {:a :b})
  (let [result (errata/init :ok)]
    (println (:value result))
    (println (:error result))))