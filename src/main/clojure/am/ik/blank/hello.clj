(ns am.ik.blank.hello
   (:gen-class
	:methods [
		#^{:static true} [foo [int] void]
		[echo [String] String]
	]))

; http://d.hatena.ne.jp/Kazuhira/20121027/1351328382

(defn -foo [i] (println "Hello from Clojure. My input was " i))
(defn -echo [this s] (str "Echoed from Clojure. The input was " s))
