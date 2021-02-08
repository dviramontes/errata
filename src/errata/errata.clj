(ns errata.errata
  (:require [clojure.pprint :as pprint]))

(defprotocol Result
  "Implement ok/err as value"
  (ok? [result] "if value")
  (err? [result] "if error"))

(defrecord Errata [value error]
  clojure.lang.IFn
  (invoke [this kw]
    (get this kw))
  Result
  (ok? [this] (some? (:value this)))
  (err? [this] (some? (:error this))))

(defn init
  "Initialize ->Errata with optional value, error, config arguments"
  ([]
   (->Errata nil nil))
  ([value]
   (->Errata value nil))
  ([value error]
   (->Errata value error))
  ([value error config]
   (pprint/pprint config)
   (->Errata value error)))

;; TODO: explore creating a wrapper for annonating errors with metadata
