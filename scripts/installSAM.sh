#!/bin/bash
ROOT="https://concordia.consolving.de/svn/sandbox/philipp/playframework/sam/"
sudo apt-get -y install subversion
SVN=`which svn`
cd /usr/local
echo "Checkout SAM"
sudo $SVN $ROOT
echo "Starting SAM"
/usr/local/play/play start /usr/local/sam

