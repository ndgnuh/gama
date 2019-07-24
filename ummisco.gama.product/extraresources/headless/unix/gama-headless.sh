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

outputFile=""
inputFile=""

memory=4096m
console=false
tunneling=false

for ((i=1;i<=$#;i=$i+1))
do
if test ${!i} = "-help"
then
help="yes"
i=$i+1000
fi
done

for ((i=1;i<=$#;i=$i+1))
do
if test ${!i} = "-hpc"
then
i=$i+1
export PARAM=$PARAM\ -hpc\ ${!i}
hpc="yes"
i=$i+1000
fi
done

for ((i=1;i<=$#;i=$i+1))
do
if test ${!i} = "-p"
then
i=$i+1
export PARAM=$PARAM\ -p
tunneling="yes"
i=$i+1000
fi
done

for ((i=1;i<=$#;i=$i+1))
do
if test ${!i} = "-v"
then
i=$i+1
export PARAM=$PARAM\ -v
verbose="yes"
i=$i+100
fi
done

if [ $console = 'no' ] && [ $tunneling = 'no' ] ; then
i=$#
i=$i-1
inputFile=${!i}
fi

if [ $tunneling = 'no' ] ; then
i=$#
i=$i
outputFile=${!i}
fi



echo "******************************************************************"
echo "* GAMA version 1.8                                               *"
echo "* http://gama-platform.org                                       *"
echo "* (c) 2007-2019 UMI 209 UMMISCO IRD/SU & Partners                *"
echo "******************************************************************"
if [ $help = "yes" ]  ;  then
echo ""
echo ""
echo ""
echo "sh ./gama-headless.sh [Options] [XML Input] [output directory]"
echo "	"
echo "List of available options:"
echo " -help     				-- get the help of the command line"
echo " -version     				-- get the the version of gama"
echo " -m [mem]    				-- allocate memory, eg. 2048m"
echo " -c        				-- start the console to write xml parameter file"
echo " -v 					-- verbose mode"
echo " -hpc [core] 				-- set the number of core available for experimentation"
echo " -socket [socketPort] 			-- start socket pipeline to interact with another framework"  
echo " -p                                     -- start pipeline to interact with another framework" 
echo " -validate [directory]                  -- invokes GAMA to validate the models present in the directory passed as argument"
echo " -test [directory]		   	-- invokes GAMA to execute the tests present in the directory and display their results"
echo " -failed		      	        -- only display the failed and aborted test results"
echo " -xml	[experimentName] [modelFile.gaml] [xmlOutputFile.xml]"	
echo "				        --  build an xml parameter file from a model"
echo ""
echo ""
exit 1
fi
if [ ! -f "$inputFile" ] && [ $console = "no" ] && [ $tunneling = "no" ] ;  then
echo "The input or output file are not specied. Please check the path of your files and output file."
echo "Use the help for more information: ./gama-headless -help"
exit 1
fi
if   [ -d "$inputFile" ]  && [ $console = "no" ] && [ $tunneling = "no" ] ; then
    echo "The defined input is not an XML parameter file" 
    echo "Use the help for more information: ./gama-headless -help"
    exit 1
fi
if [ $tunneling = "no" ] && [ -d "$outputFile" ]   ; then
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
