.PHONY: repl lint test build

repl: 
	clj -M:repl

lint:
	clj -M:clj-kondo --lint src

test:
	clojure -M:test:runner

build:
	clojure -X:jar