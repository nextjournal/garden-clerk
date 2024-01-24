(ns nextjournal.garden-clerk
  (:require [babashka.fs :as fs]
            [nextjournal.clerk :as clerk]
            [nextjournal.simple-http-server :as http]))

(defn assoc-some [m k v]
  (if (some? v)
    (assoc m k v)
    m))

(defn serve! [opts]
  (when-let [persistent-storage (System/getenv "GARDEN_STORAGE")]
    (System/setProperty "clerk.cache_dir" (str (fs/path persistent-storage ".clerk" "cache")) ))
  (clerk/serve! (-> opts
                    (assoc :bind "0.0.0.0")
                    (assoc-some :port (System/getenv "GARDEN_PORT")))))

(defn serve-static! [opts]
  (def out-path (str (fs/create-temp-dir)))
  (when-let [persistent-storage (System/getenv "GARDEN_STORAGE")]
    (System/setProperty "clerk.cache_dir" (str (fs/path persistent-storage ".clerk" "cache")) ))
  (clerk/build! (-> opts
                    (assoc :out-path out-path)))
  (http/serve! (-> opts
                   (assoc :path out-path)
                   (assoc-some :port (System/getenv "GARDEN_PORT")))))
