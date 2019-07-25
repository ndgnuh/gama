#!/bin/sh

##
#  gama-headless.sh
#  Author: Arthur Brugiere <RoiArthurB>
#  Description: This script aim to be a helper to use the GAMA's headless
#  Last editing: 07/19
##

#
#	Set variables
#

# Internal var
PARAM=$@
memory=4096m

# Letter w/o ":" is for flags
# Letter w/  ":" is w/ arguments (option) -> eg m:
# Set every flag's first letter 
while getopts 'phvstfxcm:' option; do
	case "${option}" in

		# options
		m) memory=${OPTARG};;

		# escape other
		*) shift;;

		#failed) echo "Failed is an undefined parameter";;
		#check) echo "check is an undefined parameter";;
	esac
done

# Beautiful header	=> Can be removed if already set in the .jar
echo "*******************************************************************"
echo "* Headless helper (OSX)                                           *"
echo "*   for GAMA version 1.8                                          *"
echo "* http://gama-platform.org                                        *"
echo "* (c) 2007-2019 UMI 209 UMMISCO IRD/SU & Partners                 *"
echo "*******************************************************************"

#
#	Preparing ressources for headless run
#

# Get jar files for java command
GAMAHOME=$(cd $(dirname $0)/.. && pwd -P) # Path assuming this script is within the gama release tree
gamaDirectory=$(cd $GAMAHOME/Eclipse/plugins && pwd)
DUMPLIST=$(ls $gamaDirectory/*.jar)
# Concat jar files into a string
for fic in $DUMPLIST; do
	GAMA=$GAMA:$fic
done

# Create Headless workspace
# Use TimeStamp to have a unique folder name
workingDir=/tmp/work$(date +%s) 

#
#	Launching it in GAMA
#

echo "GAMA is starting..."

exec java -cp $GAMA -Xms512m -Xmx4096m -Djava.awt.headless=true org.eclipse.core.launcher.Main -application msi.gama.headless.id4 -data $workingDir $PARAM
