#!/bin/bash

PLAY_INSTALL_DIR=/opt

sudo apt-get update
sudo apt-get -y install openjdk-6-jre-headless unzip python
wget http://download.playframework.org/releases/play-1.2.4.zip -O ~/samt/packages/play-1.2.4.zip
sudo unzip ~/samt/packages/play-1.2.4.zip -d $PLAY_INSTALL_DIR/
cd $PLAY_INSTALL_DIR
sudo ln -s play-1.2.4 play
sudo chmod +x $PLAY_INSTALL_DIR/play/play
sudo ln -s $PLAY_INSTALL_DIR/play/play /usr/bin/play
