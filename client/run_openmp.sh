#!/bin/bash
echo "Biological sequence comparison using MASA-OpenMP"

# Reference sequence and sequences path
seqBase="./sequences/NC_045512.2.fasta"
seqPath="./sequences/lineage_B.1.1.7"

# MASA-OpenMP work path and statistics file
workPath="./work.tmp"
statFileName="$workPath/statistics"

# Path for results files
resultsPath="./results"

# Check if a previous processing has stopped
checkpoint=false
currentSeq=""
if test -f "_current_file.txt"; then
	checkpoint=true
	currentSeq=`cat _current_file.txt`
fi

# Loop through all sequences
for f in $seqPath/*.fasta
do

	# Resume from checkpoint
	if $checkpoint; then
		if [ $currentSeq == $(basename $f) ]; then
			checkpoint=false
		fi
		continue
	fi

	echo "Processing sequence $f"
	echo "$(basename $f)" > _current_file.txt

	rm -rf $workPath
	~/masa-openmp-1.0.1.1024/masa-openmp --alignment-edges=++ $seqBase $f >> /dev/null
	cp $statFileName $resultsPath/statistics-$(basename $f)

	# Simulate an exception
	break

	rm _current_file.txt
done

echo "Execution completed."