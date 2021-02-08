(ns errata.errata 
  (:require [clojure.pprint :as pprint]))

(defrecord Result [value error]
  clojure.lang.IFn
  (invoke [this kw]
    (get this kw)))

(defn init
  "Initialize Errata with optional value, error, config arguments"
  ([value]
   (->Result value nil))
  ([value error]
   (->Result value error))
  ([value error config]
   (pprint/pprint config)
   (->Result value error)))