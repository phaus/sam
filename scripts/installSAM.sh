#!/bin/bash
ROOT="git clone git@github.com:phaus/sam.git"
sudo apt-get -y install git
GIT=`which git`
cd /usr/local
echo "Checkout SAM"
sudo $GIT $ROOT
echo "Starting SAM"
/usr/local/play/play start /usr/local/sam

