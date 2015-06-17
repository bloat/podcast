(ns podcast.rss
  (require [podcast.xml :refer [write-rss]]
           [podcast.info :refer [info]]
           [podcast.props :refer [http-root]])
  (import [org.joda.time DateTime]))

(defn main- []
  (write-rss (info) (DateTime.) (str http-root "radio6.rss")))









