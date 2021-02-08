.PHONY: repl lint test build deploy install

repl: 
	clj -M:repl

lint:
	clj -M:clj-kondo --lint src

test:
	clojure -M:test:runner

build:
	clojure -X:jar

deploy:
	clojure -X:deploy

install:
	clojure -X:install
