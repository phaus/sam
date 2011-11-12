#!/bin/bash
DIST=""
ARCH=""
VERS=""
EXT=""
installForUbuntu (){
	DIST="Ubuntu"
	configs=`cat /etc/lsb-release`
	for config in $configs; do
        	K=`echo $config | cut -d '=' -f 1`
        	V=`echo $config | cut -d '=' -f 2`
		if [ $K = "DISTRIB_RELEASE" ] ; then
			VERS=$V
		fi
	done	
        aptSetup
}

installForDebian (){
	DIST="Debian"
	VERS=`cat /etc/debian_version`
	aptSetup
}

aptSetup (){
	echo "general apt setup"
	echo "for $DIST $ARCH $VERS"
	SUDO=`which sudo`
	if [ -f $SUDO ]; then
		sudo apt-get -y install wget
	else
		echo "Enter root mode"
		su
		apt-get -y install wget sudo
	fi
	EXT=deb
	commonSetup
	wget https://raw.github.com/phaus/sam/master/scripts/apt/playSetup.sh -O ~/samt/scripts/playSetup.sh && bash ~/samt/scripts/playSetup.sh
	wget https://raw.github.com/phaus/sam/master/scripts/apt/installSAM.sh -O ~/samt/scripts/installSAM.sh && bash ~/samt/scripts/installSAM.sh
}

commonSetup() {
	wget https://raw.github.com/phaus/sam/master/scripts/common/download.winexe.sh -O ~/samt/scripts/download.winexe.sh && bash ~/samt/scripts/download.winexe.sh $DIST $VERS $ARCH ~/samt/packages
	# needs to be called from another location
	if [ -f ~/samt/packages/winexe_1.00_$ARCH.$EXT ]; then
		sudo dpkg -i ~/samt/packages/winexe_1.00_$ARCH.$EXT
	fi
}

init (){
	# move folders to an array and check in a loop
	if [ ! -d ~/samt ]; then
		echo "creating folder for SAM Tools"
		mkdir ~/samt
		mkdir ~/samt/scripts
		mkdir ~/samt/packages
		mkdir ~/samt/logs
	fi
	
	if [ `uname -m` = "x86_64" ]; then
		ARCH="amd64"
	elif [ `uname -m` = "amd64" ]; then
		ARCH="amd64"
	elif [ `uname -m` = "i686" ]; then
		ARCH="i386"
	elif [ `uname -m` = "i386" ]; then
		ARCH="i386"
	else
		echo "this os/architecture is currently not supported!"
	fi
	
	if [ -f /etc/lsb-release ]; then 
		installForUbuntu
	elif [ -f /etc/debian_version ]; then 
		installForDebian
	else		
		echo "this os/architecture is currently not supported!"
	fi
}

init
