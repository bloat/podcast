(ns podcast.info
  (import [java.io File]
          [org.joda.time.format DateTimeFormat]
          [org.joda.time DateTime]))

(defn info-lines []
  (into [] (.split (slurp "/Users/bloat/git/podcast/info.txt") "\n")))

(defn program-lines [info-lines]
  (partition 4 info-lines))

(defn description [line]
  (.substring line 16))

(defn url [line]
  (str "http://mexico/media/" (.substring line 30)))

(defn title [line]
  (.replace (.substring line 30 (- (count line) 21)) \_ \space))

(defn guid [line]
  (.substring line (- (count line) 20) (- (count line) 12)))

(def date-pattern (DateTimeFormat/forPattern "'lastbcast:      default: 'yyyy-MM-dd'T'HH:mm:ssZZ"))
(defn date [line] (.parseDateTime date-pattern line))

(defn program [program-lines]
  {:description (description (nth program-lines 1))
   :title (title (first program-lines))
   :url (url (first program-lines))
   :guid (guid (first program-lines))
   :length (.length (File. (first program-lines)))
   :date (date (nth program-lines 3))})

(defn info []
  (map program (program-lines (info-lines))))










