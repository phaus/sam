#!/bin/bash
sudo apt-get -y install wget
wget https://raw.github.com/phaus/sam/master/scripts/playSetup.sh && bash playSetup.sh
wget https://raw.github.com/phaus/sam/master/scripts/installSAM.sh && bash installSAM.sh
