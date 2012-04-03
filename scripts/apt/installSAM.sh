#!/bin/bash

setupPaths(){
    ROOT="https://github.com/phaus/sam.git"
    GIT=`which git`
    SAM_INSTALL_DIR=/opt
    PLAY="$SAM_INSTALL_DIR/play/play"
}

showIps(){
  HN=`which hostname`
  echo ""
  echo "========================================================"
  echo "open one of these addresses (depending on you network)"
  echo "========================================================"
  for ip in `$HN -I`; do
        echo "    http://${ip}:9000"
  done
  echo "========================================================"
}


echo "========================================"
echo "  preparing system                  "
echo "========================================"
sudo apt-get -y install git-core ssh
wget https://raw.github.com/phaus/sam/master/scripts/common/setupSSH.sh -O ~/samt/scripts/setupSSH.sh && bash ~/samt/scripts/setupSSH.sh

setupPaths

cd $SAM_INSTALL_DIR
echo "========================================"
echo "  Checkout latest SAM              "
echo "========================================"
sudo $GIT clone $ROOT $SAM_INSTALL_DIR/sam
echo "========================================"
echo " Starting SAM                  "
echo "========================================"

sudo mkdir -p /var/log/sam
sudo ln -s /var/log/sam $SAM_INSTALL_DIR/sam/logs

echo "#!/bin/bash" > $SAM_INSTALL_DIR/sam/start-sam.sh
echo "$PLAY start $SAM_INSTALL_DIR/sam -Xmx64M" >> $SAM_INSTALL_DIR/sam/start-sam.sh
sudo chmod +x $SAM_INSTALL_DIR/sam/start-sam.sh

echo "#!/bin/bash" > $SAM_INSTALL_DIR/sam/stop-sam.sh
echo "$PLAY stop $SAM_INSTALL_DIR/sam" >> $SAM_INSTALL_DIR/sam/stop-sam.sh
sudo chmod +x $SAM_INSTALL_DIR/sam/stop-sam.sh

sudo $PLAY secret $SAM_INSTALL_DIR/sam
sudo bash $SAM_INSTALL_DIR/sam/start-sam.sh

showIps