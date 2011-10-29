#!/bin/bash
echo "========================================"
echo "  preparing system	              "
echo "========================================"
sudo apt-get -y install git-core ssh
ROOT="https://github.com/phaus/sam.git"
GIT=`which git`
PLAY="/usr/local/play/play"
echo "========================================"
echo "  SSH Setup - user needs access as root "
echo "  USE empty Passphrase for this key     "
echo "========================================"
ssh-keygen -q -t rsa -b 2048 -f ~/.ssh/id_rsa
cat  ~/.ssh/id_rsa.pub >>  ~/.ssh/authorized_keys
wget https://raw.github.com/phaus/sam/master/scripts/setupSSH.sh && sudo bash setupSSH.sh
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

