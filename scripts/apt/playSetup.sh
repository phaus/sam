#!/bin/bash
sudo apt-get update
sudo apt-get -y install openjdk-6-jre-headless unzip python
wget http://download.playframework.org/releases/play-1.2.4.zip -O ~/samt/packages/play-1.2.4.zip
sudo unzip ~/samt/packages/play-1.2.2.zip -d /usr/local/
cd /usr/local
sudo ln -s play-1.2.4 play
sudo chmod +x /usr/local/play/play
sudo ln -s /usr/local/play/play /usr/bin/play
