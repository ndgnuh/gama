#!/bin/bash

##
# Copyright 2019 Arthur Brugiere <contact@arthurbrugiere.fr>
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
##
#
# This script aim to be a helper to use the GAMA's headless
# 
##

#
#	Set variables
#

# External var
outputFile=""
inputFile=""

# Internal var
PARAM=$@
memory=4096m
console=false
tunneling=false
xml=false

# Letter w/o ":" is for flags
# Letter w/  ":" is w/ arguments (option) -> eg m:
# Set every flag's first letter 
while getopts 'phvstfxcm:' option; do
	case "${option}" in

		# flags
	    p) tunneling=true ;;
		c) console=true ;;
		x) xml=true;; #only the first letter will match the -xml flag

		# options
		m) memory=${OPTARG};;

		# escape other
		*) shift;;

		#failed) echo "Failed is an undefined parameter";;
		#check) echo "check is an undefined parameter";;
	
	esac
done

# if !$xml => running headless experiment
# 	So we shoudl remove input/output files from parameters
# else => Generating XML from gaml file
# 	So we should leave experimentName/modelFile.gaml/xmlOutputFile.xml as parameters
if [ $xml = false ]; then

	# Set input / output relative path
	inputFile="${@: -2: 1}"
	outputFile="${@: -1: 1}"

	# Remove input/output from param
	#	expr will calculate following elements
	#	-2 stand for spaces between elements (args_input_output)
	#	${#_var_} count char from char
	#	SUM => total char to remove starting by the end of PARAM string
	PARAM="${PARAM::$(expr -2 - ${#inputFile} - ${#outputFile})}"

	# Set input / output absolute path
	inputFile="$(readlink -f $inputFile)"
	outputFile="$(readlink -f $outputFile)"
fi

# Beautiful header	=> Can be removed if already set in the .jar
echo "******************************************************************"
echo "* GAMA version 1.8                                               *"
echo "* http://gama-platform.org                                       *"
echo "* (c) 2007-2019 UMI 209 UMMISCO IRD/SU & Partners                *"
echo "******************************************************************"

# Verification input file / output directory
if [ ! -f "$inputFile" ] && [ $console = false ] && [ $tunneling = false ] ;  then
	echo "The input or output file are not specied. Please check the path of your files and output file."
	echo "Use the help for more information: ./gama-headless -help"
	exit 1
fi
if   [ -d "$inputFile" ] && [ $console = false ] && [ $tunneling = false ] ; then
    echo "The defined input is not an XML parameter file" 
    echo "Use the help for more information: ./gama-headless -help"
    exit 1
fi
if [ $tunneling = false ] && [ -d "$outputFile" ]   ; then
	echo "The output directory already exist. Please check the path of your output directory" 
	echo "Use the help for more information: ./gama-headless -help"
	exit 1
fi

# assuming this file is within the gama deployment
GAMAHOME=$(cd $(dirname $0)/.. && pwd -P)
gamaDirectory=$(cd $GAMAHOME/plugins && pwd)
DUMPLIST=$(ls  $gamaDirectory/*.jar )
for fic in $DUMPLIST; do
GAMA=$GAMA:$fic
done
passWork=/tmp/.work
if [ $console = 'no' ] && [ $tunneling = 'no' ] ; then
mP=$( cd $(dirname $inputFile) && pwd -P )
mF=$(basename $inputFile)
mfull=$mP/$mF
passWork=$outputFile/.work
fi
echo "GAMA is starting..."
#exec
#GAMA=Gamaq
exec java -cp $GAMA -Xms512m -Xmx$memory  -Djava.awt.headless=true org.eclipse.core.launcher.Main  -application msi.gama.headless.id4 -data $passWork $PARAM $mfull $outputFile
