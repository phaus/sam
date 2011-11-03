#!/bin/bash
sudo apt-get update
sudo apt-get -y install openjdk-6-jre-headless unzip
cd /tmp
wget http://download.playframework.org/releases/play-1.2.3.zip
sudo unzip play-1.2.3.zip -d /usr/local/
cd /usr/local
sudo ln -s play-1.2.3 play
sudo chmod +x /usr/local/play/play
sudo ln -s /usr/local/play/play /usr/bin/play
