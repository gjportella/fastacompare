#!/bin/bash
echo "Biological sequence comparison using MASA-OpenMP"

# Server-side configuration and parameters
serverUrlBase="http://192.168.56.1:8080"
serverParamKey="test123"

# Reference sequence and sequences path
seqBase="./sequences/NC_045512.2.fasta"
seqPath="./sequences/lineage_B.1.1.7"

# MASA-OpenMP work path and files
ompWorkPath="./work.tmp"
ompStatsFileName="$ompWorkPath/statistics"
ompAlignFileName="$ompWorkPath/alignment.00.txt"

# Path for result files
seqResultsPath="./results"

# Check if exists a previous processing with informed key
serverUrlDetailLastSequence="$serverUrlBase/checkpoint/rest/service?c=detailLastSequence&key=$serverParamKey"
wget -O _wget_tmp_file.txt "$serverUrlDetailLastSequence"
lastSequence=`cat _wget_tmp_file.txt`

# Set checkpoint flag
checkpoint=false
if test ! -z $lastSequence; then
	checkpoint=true
fi

# Loop through all sequences
for f in $seqPath/*.fasta
do

	# Parse current sequence name
	currentSequence="$(basename $f)"

	# Resume from checkpoint/last sequence
	if $checkpoint; then
		if [ $lastSequence == $currentSequence ]; then
			checkpoint=false
		fi
		continue
	fi

	# Start processing current sequence
	echo "Processing sequence $currentSequence"

	# Clear working directory
	rm -rf $ompWorkPath

	# Call MASA-OpenMP and save results
	~/masa-openmp-1.0.1.1024/masa-openmp --alignment-edges=++ $seqBase $f >> /dev/null
	cp $ompStatsFileName $seqResultsPath/statistics-$currentSequence
	cp $ompAlignFileName $seqResultsPath/alignment-$currentSequence

	# Upload statistics file
	serverUrlUpload="$serverUrlBase/checkpoint/rest/upload"
	serverParamDescription="statistics"
	curl -X POST -H "Content-Type: multipart/form-data" \
		-F "key=$serverParamKey" \
		-F "sequence=$currentSequence" \
		-F "description=$serverParamDescription" \
		-F "content=@$seqResultsPath/$serverParamDescription-$currentSequence" \
		"$serverUrlUpload"

	# Upload alignment file
	serverParamDescription="alignment"
	curl -X POST -H "Content-Type: multipart/form-data" \
		-F "key=$serverParamKey" \
		-F "sequence=$currentSequence" \
		-F "description=$serverParamDescription" \
		-F "content=@$seqResultsPath/$serverParamDescription-$currentSequence" \
		"$serverUrlUpload"

	# Save checkpoint
	serverUrlSave="$serverUrlBase/checkpoint/rest/service?c=save&key=$serverParamKey&sequence=$currentSequence"
	wget -O _wget_tmp_file.txt "$serverUrlSave"

	# Simulate an exception
	#break

done

# Clear procedures
rm -rf $ompWorkPath
rm _wget_tmp_file.txt

echo "Execution completed."