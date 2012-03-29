#!/bin/bash
echo "========================================"
echo "  SSH Setup - for root access           "
echo "========================================"

SSH_DIR="/root/.ssh" 
# TODO ugly fix need to find a better way :-)
if [ ! -d $SSH_DIR ]; then
    sudo cp -R  $HOME/.ssh /root/
    sudo chown -R root:root /root/
else
    sudo cat $SSH_DIR/id_rsa.pub >> $SSH_DIR/authorized_keys       
fi

