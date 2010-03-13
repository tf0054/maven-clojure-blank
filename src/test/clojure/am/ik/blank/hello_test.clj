(ns am.ik.blank.hello-test
  (:use [clojure.test]
        [am.ik.blank.hello]
        ))

(deftest foo-test []
  (is (= "foobarhoge" (foo "bar" "hoge"))))

(run-tests)