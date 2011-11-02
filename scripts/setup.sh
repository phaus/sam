#!/bin/bash
DIST=""
ARCH=""
VERS=""

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
	sudo apt-get -y install wget
	wget https://raw.github.com/phaus/sam/master/scripts/apt/playSetup.sh -O ~/samt/scripts/playSetup.sh && bash ~/samt/scripts/playSetup.sh
	wget https://raw.github.com/phaus/sam/master/scripts/apt/installSAM.sh -O ~/samt/scripts/installSAM.sh && bash ~/samt/scripts/installSAM.sh	
}

init (){
	ran="false"
	if [ ! -d ~/samt ]; then
		echo "creating folder for SAM Tools"
		mkdir ~/samt
		mkdir ~/samt/scripts
	fi
	
	if [ `uname -m` = "x86_64" ]; then
		ARCH="amd64"
	else
		ARCH="i586"
	fi
	
	if [ -f /etc/lsb-release ]; then 
		installForUbuntu
		ran="true"
	fi

	if [ $ran = "false" && -f /etc/debian_version ]; then 
		installForDebian
		ran="true"
	fi
	
	if [ $ran = "false" ]; then
		echo "this os/architecture is currently not supported!"
	fi
}

init
