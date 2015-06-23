#!/bin/bash

LOG=$HOME/Downloads/iplayer/log

# Download new episodes
/usr/local/bin/get_iplayer -o $HOME/Downloads/iplayer --pvr >> $LOG 2>&1

printf "............................\n. Generating info.txt\n............................\n" >> $LOG

for i in `ls $HOME/Downloads/iplayer/*.m4a`; do printf %"s\n" $i; printf $i | awk -F _ '{print $(NF-1)}' | xargs /usr/local/bin/get_iplayer --info --type=radio --pid | egrep "(lastbcast:|filename:|desclong:)" | grep -v original; done  > $HOME/Downloads/iplayer/info.txt 2>&1

printf "............................\n. Removing old files\n............................\n" >> $LOG

find /usr/share/nginx/html/media -name "*.m4a" -mtime 14 -exec rm -v {} \; >> $LOG
find $HOME/Downloads/iplayer -name "*.m4a" -mtime 14 -exec rm -v {} \; >> $LOG

printf "............................\n. Copying new files to web server\n............................\n" >> $LOG

cp -vn $HOME/Downloads/iplayer/*.m4a /usr/share/nginx/html/media >> $LOG

printf "............................\n" >> $LOG

export PATH=$HOME/bin:$PATH

cd $HOME/installs/podcast
lein run -m podcast.rss >> $LOG 2>&1

