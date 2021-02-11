(ns errata.errata)

(defprotocol Result
  "Implement <ok> & <err> as value types"
  (ok [result] "get value from ->Result")
  (err [result] "get error from ->Result") 
  (ok! [result value] "sets the value property of ->Result")
  (err! [result error] "set the error property of  ->Result")
  (ok? [result] "returns true when value is present")
  (err? [result] "returns true when error is present"))
  ;; (check [] ...) returns {result value}
  ;; (is? [result error] ...) error comparison
  ;; (ok-n [result])
  ;; (err-n [result]))

(defrecord Errata [value error]
  clojure.lang.IFn
  (invoke [this kw] (get this kw))
  Result
  (ok [this] (get this :value))
  (err [this] (get this :error))
  (ok! [this value] (assoc this :value value))
  (err! [this error] (assoc this :error error))
  (ok? ^Boolean [this] (some? (:value this)))
  (err? ^Boolean [this] (some? (:error this))))

(defn init
  "Initialize ->Errata with optional value"
  ([]
   (->Errata nil nil))
  ([value]
   (->Errata value nil)))

(comment
  (.error (init :ok))
  (.error (ok! (init) :ok))
  (.value (ok! (init) :some-value))
  (.error (err! (init) :some-error)))