#!/bin/bash
echo "========================================"
echo "  preparing system	              "
echo "========================================"
sudo apt-get -y install git-core ssh
ROOT="https://github.com/phaus/sam.git"
GIT=`which git`
PLAY="/usr/local/play/play"
cd /usr/local
echo "========================================"
echo "  Checkout latest SAM		      "
echo "========================================"
sudo $GIT clone $ROOT /usr/local/sam
echo "========================================"
echo "  SSH Setup - user needs access as root "
echo "  USE empty Passphrase for this key     "
echo "========================================"
ssh-keygen -q -t rsa -b 2048 -f ~/.ssh/id_rsa
sudo mkdir -p /root/.ssh/
# TODO ugly fix need to find a better way :-)
touch /tmp/1
sudo cp /root/.ssh/authorized_keys /tmp/1
sudo cat ~/.ssh/id_rsa.pub >> /tmp/1
sudo cp /tmp/1 /root/.ssh/authorized_keys
sudo rm /tmp/1
sudo chmod 0600 /root/.ssh
echo "========================================"
echo " Starting SAM			      "
echo "========================================"
sudo mkdir -p /var/log/sam
sudo ln -s /var/log/sam logs
sudo $PLAY start /usr/local/sam

