#!/bin/bash
ROOT_SSH="/root/.ssh/" 
# TODO ugly fix need to find a better way :-)
touch /tmp/1
if [ -d $ROOT_SSH ]; then
    cp -R  ~/.ssh /root/
    chown -R root:root /root/
else
    cat $ROOT_SSH/id_rsa.pub >> $ROOT_SSH/authorized_keys	
fi
