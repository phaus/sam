#!/bin/bash

SSH_DIR="~/.ssh" 
# TODO ugly fix need to find a better way :-)
if [ ! -d $SSH_DIR ]; then
	echo "========================================"
	echo "  SSH Setup - user needs access as root "
	echo "  USE empty Passphrase for this key     "
	echo "========================================"
	ssh-keygen -q -t rsa -b 2048 -f ~/.ssh/id_rsa
	cat  ~/.ssh/id_rsa.pub >>  ~/.ssh/authorized_keys
fi
wget https://raw.github.com/phaus/sam/master/scripts/common/setupRootSSH.sh -O ~/samt/scripts/setupRootSSH.sh && sudo bash ~/samt/scripts/setupRootSSH.sh
