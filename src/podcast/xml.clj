(ns podcast.xml
  (require [clojure.data.xml :as xml]
           [clojure.java.io :as io]
           [podcast.props :refer :all])
  (import [org.joda.time.format DateTimeFormat]
          [org.joda.time DateTime]))

(def item-pattern (DateTimeFormat/forPattern "EEE, d MMM YYYY HH:mm:ss Z"))
(def year-pattern (DateTimeFormat/forPattern "YYYY"))

(defn make-item-sexp [{title :title guid :guid description :description url :url length :length date :date}]
  [:item
   [:title title]
   [:link home-page]
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
          [:link home-page]
          [:language "en-uk"]
          [:copyright (str "Copyright " (.print year-pattern (DateTime.)))]
          [:lastBuildDate (.print item-pattern date)]
          [:pubDate (.print item-pattern date)]
          [:docs home-page]
          [:webMaster email]
          (map make-item-sexp items))])

(defn write-rss [items date file]
  (with-open [out-file (io/writer file)]
    (xml/emit (xml/sexp-as-element (make-rss items date)) out-file)))









