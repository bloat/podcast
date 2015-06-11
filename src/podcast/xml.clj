(ns podcast.xml
  (require [clojure.data.xml :as xml])
  (import [org.joda.time.format DateTimeFormat]
          [org.joda.time DateTime])

  (def item-pattern (DateTimeFormat/forPattern "EEE, d MMM YYYY HH:mm:ss Z")))
(def year-pattern (DateTimeFormat/forPattern "YYYY"))

(defn make-item-sexp [{title :title link :link guid :guid description :description url :url length :length date :date}]
  [:item
   [:title title]
   [:link link]
   [:guid guid]
   [:description description]
   [:enclosure {:url url :length (str length) :type "audio/m4a"}]
   [:category "Podcasts"]
   [:pubDate (.print item-pattern date)]])

(defn make-rss [items date]
  [:rss {:version "2.0"}
   (apply vector
          :channel
          [:title "Iplayer Pod"]
          [:description "Programmes downloaded from IPlayer"]
          [:link "http://mexico"]
          [:language "en-uk"]
          [:copyright (str "Copyright " (.print year-pattern (DateTime.)))]
          [:lastBuildDate (.print item-pattern date)]
          [:pubDate (.print item-pattern date)]
          [:docs "http://mexico"]
          [:webMaster "andrew.cowper@slothrop.net"]
          (map make-item-sexp items))])











