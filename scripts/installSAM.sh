#!/bin/bash
echo "========================================"
echo "  preparing system	              "
echo "========================================"
sudo apt-get -y install git-core ssh-keygen
ROOT="https://github.com/phaus/sam.git"
GIT=`which git`
PLAY="/usr/local/play/play"
cd /usr/local
echo "========================================"
echo "  Checkout latest SAM		      "
echo "========================================"
sudo $GIT clone $ROOT
echo "========================================"
echo "  SSH Setup - user needs access as root "
echo "  USE empty Passphrase for this key     "
echo "========================================"
ssh-keygen -q -t rsa -b 2048 -f ~/.ssh/id_rsa
sudo mkdir -p /root/.ssh/
sudo cat ~/.ssh/id_rsa.pub >> /root/.ssh/authorized_keys
sudo chmod 0600 /root/.ssh/authorized_keys
echo "========================================"
echo " Starting SAM			      "
echo "========================================"
sudo mkdir -p /var/log/sam
sudo ln -s /var/log/sam logs
sudo $PLAY start /usr/local/sam

