#!/bin/bash
echo "========================================"
echo "  preparing system	              "
echo "========================================"
sudo apt-get -y install git-core ssh
ROOT="https://github.com/phaus/sam.git"
GIT=`which git`
PLAY="/usr/local/play/play"
wget https://raw.github.com/phaus/sam/master/scripts/common/setupSSH.sh -O ~/samt/scripts/setupSSH.sh && bash ~/samt/scripts/setupSSH.sh
cd /usr/local
echo "========================================"
echo "  Checkout latest SAM		      "
echo "========================================"
sudo $GIT clone $ROOT /usr/local/sam
echo "========================================"
echo " Starting SAM			      "
echo "========================================"
sudo mkdir -p /var/log/sam
sudo ln -s /var/log/sam logs
sudo $PLAY start /usr/local/sam

