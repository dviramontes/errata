(ns errata.errata
  (:require [clojure.pprint :as pprint]))

(defprotocol Result
  "Implement <ok> & <err> as values"
  (ok [result value] "set value")
  (err [result error] "set err")
  (ok? [result] "if value")
  (err? [result] "if error"))
  ;; (is? [result error] ...) error comparison
  ;; (ok-n [result])
  ;; (err-n [result]))

(defrecord Errata [value error]
  clojure.lang.IFn
  (invoke [this kw]
    (get this kw))
  Result
  (ok [this value] (assoc this :value value))
  (err [this error] (assoc this :error error))
  (ok? ^Boolean [this] (some? (:value this)))
  (err? ^Boolean [this] (some? (:error this))))

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
