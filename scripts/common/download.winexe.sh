#!/bin/bash
DIST=$1
VERS=$2
ARCH=$3
DEST=$4
APP="winexe"
USR="ahajda"
EXPECTED_ARGS=4
if [ $# -ne $EXPECTED_ARGS ]
then
	echo "Usage: download.build.opensuse.sh DIST VERS ARCH DEST"
	exit 1
fi

wget https://raw.github.com/phaus/sam/master/scripts/common/build.opensuse.sh -O ~/samt/scripts/build.opensuse.sh && bash ~/samt/scripts/build.opensuse.sh $APP $USR $DIST $VERS $ARCH $DEST