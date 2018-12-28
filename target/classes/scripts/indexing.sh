#!/bin/bash

#1 path to genome, 2 genome.index, 3 genome.ext

cd $1

#indexing reference genome in BWA
bwa index $2.$3

#add also for Bowtie2
#bowtie2-build IDX.EXT bowtie2/IDX
bowtie2-build $2.$3 $2
