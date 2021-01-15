#!/bin/bash
echo "Biological sequence comparison using MASA-OpenMP"

# Server-side configuration and parameters
serverUrlBase="http://192.168.56.1:8080"
serverParamKey="test123"

# Source and target sequences paths
sourcePath="./sequences/reference"
targetPath="./sequences/lineage_B.1.1.7"

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
rm _wget_tmp_file.txt

# Verify previous processing and set checkpoint flag
if test -z $lastSequence; then

	# There is not a previous processing
	checkpoint=false

	# Start key for current processing
	serverUrlStart="$serverUrlBase/checkpoint/rest/service?c=start&key=$serverParamKey"
	wget -O _wget_tmp_file.txt "$serverUrlStart"
	rm _wget_tmp_file.txt

else

	# There is a previous processing
	checkpoint=true
fi

# Loop through all source sequences
for sourceSeq in $sourcePath/*.fasta
do

	# Loop through all target sequences
	for targetSeq in $targetPath/*.fasta
	do

		# Parse current sequence name
		sourceSeqName="$(basename $sourceSeq)" # file name without path
		sourceSeqName="${sourceSeqName%.*}"    # file name without extension
		targetSeqName="$(basename $targetSeq)" # file name without path
		targetSeqName="${targetSeqName%.*}"    # file name without extension
		currentSequence="${sourceSeqName}_${targetSeqName}"

		# Resume from checkpoint/last sequence
		if $checkpoint; then
			if [ $lastSequence == $currentSequence ]; then
				checkpoint=false
			fi
			continue
		fi

		# Start processing current sequence
		echo "Processing sequence $currentSequence"

		# Call MASA-OpenMP and save results
		~/masa-openmp-1.0.1.1024/masa-openmp --alignment-edges=++ $sourceSeq $targetSeq >> /dev/null
		cp $ompStatsFileName $seqResultsPath/statistics-$currentSequence
		#cp $ompAlignFileName $seqResultsPath/alignment-$currentSequence

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
		#serverParamDescription="alignment"
		#curl -X POST -H "Content-Type: multipart/form-data" \
		#	-F "key=$serverParamKey" \
		#	-F "sequence=$currentSequence" \
		#	-F "description=$serverParamDescription" \
		#	-F "content=@$seqResultsPath/$serverParamDescription-$currentSequence" \
		#	"$serverUrlUpload"

		# Save checkpoint
		serverUrlSave="$serverUrlBase/checkpoint/rest/service?c=save&key=$serverParamKey&sequence=$currentSequence"
		wget -O _wget_tmp_file.txt "$serverUrlSave"
		rm _wget_tmp_file.txt

		# Clear procedures
		rm -rf $ompWorkPath

	done

done

# Finish key for current processing
serverUrlFinish="$serverUrlBase/checkpoint/rest/service?c=finish&key=$serverParamKey"
wget -O _wget_tmp_file.txt "$serverUrlFinish"
rm _wget_tmp_file.txt

echo "Execution completed."
