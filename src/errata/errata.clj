(ns errata.errata
  (:require [clojure.pprint :as pprint]))

(defprotocol Result
  "Implement <ok> & <err> as value types"
  (ok [result value] "sets the value property of <Result>")
  (err [result error] "set the error property of  <Restult>")
  (ok? [result] "returns true when value is present")
  (err? [result] "returns true when error is present"))
  ;; (is? [result error] ...) error comparison
  ;; (ok-n [result])
  ;; (err-n [result]))

(defrecord Errata [value error]
  clojure.lang.IFn
  (invoke [this kw] (get this kw))
  Result
  (ok [this value] (assoc this :value value))
  (err [this error] (assoc this :error error))
  (ok? ^Boolean [this] (some? (:value this)))
  (err? ^Boolean [this] (some? (:error this))))

(defn init
  "Initialize ->Errata with optional value"
  ([]
   (->Errata nil nil))
  ([value]
   (->Errata value nil)))

;; TODO: explore creating a wrapper for annonating errors with metadata
