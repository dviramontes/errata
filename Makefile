.PHONY: repl lint test

repl: 
	clj -M:repl

lint:
	clj -M:clj-kondo --lint src

test:
	clojure -M:test:runner