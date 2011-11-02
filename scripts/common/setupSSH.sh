#!/bin/bash
echo "========================================"
echo "  SSH Setup - user needs access as root "
echo "  USE empty Passphrase for this key     "
echo "========================================"
ssh-keygen -q -t rsa -b 2048 -f ~/.ssh/id_rsa
cat  ~/.ssh/id_rsa.pub >>  ~/.ssh/authorized_keys

ROOT_SSH="/root/.ssh" 
# TODO ugly fix need to find a better way :-)
if [ ! -d $ROOT_SSH ]; then
    cp -R  ~/.ssh /root/
    chown -R root:root /root/
else
    cat $ROOT_SSH/id_rsa.pub >> $ROOT_SSH/authorized_keys       
fi

