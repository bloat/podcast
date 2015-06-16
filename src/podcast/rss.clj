(ns podcast.rss
  (require [podcast.xml :refer [write-rss]]
           [podcast.info :refer [info]])
  (import [org.joda.time DateTime]))

(write-rss (info) (DateTime.) "/Users/bloat/git/podcast/feed.rss")









