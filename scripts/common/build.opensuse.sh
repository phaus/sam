#!/bin/bash
APP=$1
USR=$2
DIST=$3
VERS=$4
ARCH=$5
DEST=$6
EXT=""
FVER=""
EXPECTED_ARGS=6
AWK=`which awk`

start(){
    comp=`echo $DIST | $AWK '{print tolower($0)}'`
    if [ $comp = "debian" ]; then
        VERS=`echo $VERS | cut -d '.' -f 1`
        VERS="${VERS}.0"
        EXT="deb"
        FVER="_1.00_"
    fi
    if [ $comp = "ubuntu" ]; then
        EXT="deb"
        FVER="_1.00_"
        DIST="x${DIST}"
    fi    
    if [ $comp = "opensuse" ]; then
        if [ $ARCH = "amd64" ]; then
            ARCH="x86_64"
        fi
        if [ $ARCH = "i386" ]; then
            ARCH="i586"
        fi
        DIST="openSUSE"
        EXT="rpm"
        FVER="-1.00-2.1."
    fi
    echo "downloading a $EXT for $DIST $VERS $ARCH"
    download
}


#http://download.opensuse.org/repositories/home:/ahajda:/winexe/Debian_6.0/i386/winexe_1.00_i386.deb
#http://download.opensuse.org/repositories/home:/ahajda:/winexe/Debian_6.0/amd64/winexe_1.00_amd64.deb
#http://download.opensuse.org/repositories/home:/ahajda:/winexe/xUbuntu_11.04/amd64/winexe_1.00_amd64.deb
#http://download.opensuse.org/repositories/home:/ahajda:/winexe/openSUSE_11.4/x86_64/winexe-1.00-2.1.x86_64.rpm
#http://download.opensuse.org/repositories/home:/ahajda:/winexe/openSUSE_11.4/i586/winexe-1.00-2.1.i586.rpm

download(){
    link="http://download.opensuse.org/repositories/home:/${USR}:/${APP}/${DIST}_$VERS/${ARCH}/${APP}${FVER}${ARCH}.${EXT}"
    echo "downloading $link"
        wget $link -O ${DEST}/${APP}${FVER}${ARCH}.${EXT}
}

if [ $# -ne $EXPECTED_ARGS ]
then
    echo "Usage: download.build.opensuse.sh DIST VERS ARCH DEST"
    exit 1
fi
start