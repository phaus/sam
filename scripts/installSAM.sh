#!/bin/bash
ROOT="https://github.com/phaus/sam.git"
sudo apt-get -y install git-core
GIT=`which git`
PLAY=`which play`
cd /usr/local
echo "Checkout SAM"
sudo $GIT clone $ROOT
echo "Starting SAM"
$PLAY start /usr/local/sam

