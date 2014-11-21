(ns am.ik.blank.hello
   (:gen-class
	:methods [
		#^{:static true} [foo [int] void]
		[echo [String] String]
	]))

; http://d.hatena.ne.jp/Kazuhira/20121027/1351328382

(defn -foo [s]
  (println "Hello from Clojure. I am static, My input int was " s ))

(defn -echo [this s] (println "passwd first arg for non-static func: " (type this))
					  (str "Echoed from Clojure. The input was " s))
