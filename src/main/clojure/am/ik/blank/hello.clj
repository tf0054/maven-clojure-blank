(ns am.ik.blank.hello
	(:gen-class
:methods [#^{:static true} [foo [int] void]]))

(defn -foo [i] (println "Hello from Clojure. My input was " i))
