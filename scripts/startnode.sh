#! /bin/bash

BASE=$(dirname $0)/../
CLASSPATH="${BASE}/lib/"

java -cp ${CLASSPATH} i5.las2peer.testing.L2pNodeLauncher windows_shell -s 9913 - uploadStartupDirectory startService('i5.las2peer.services.iStarMLVisualizerService.IStarMLVisualizerService') startWebConnector('9914') interactive
