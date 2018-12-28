#!/bin/bash

#those tools may to be installed before configuration
#git
sudo apt update
sudo apt install git
#unzip
sudo apt-get install unzip

#in case you want to obtain most popular human genomes try this with
# personalised paths
mkdir $1genomes/ #path to resources
cd $1genomes/

#downloading reference genome hg38
wget http://hgdownload.soe.ucsc.edu/goldenPath/hg38/bigZips/hg38.fa.gz
gunzip hg38.fa.gz

#downloading reference genome hg19
wget hgdownload.cse.ucsc.edu/goldenPath/hg19/bigZips/chromFa.tar.gz
tar -zxvf chromFa.tar.gz
cat chr*.fa > hg19.fa

#in case you want to obtain some ovarian cancer wes samples from SRA
#WARNING! Those file are very large. Choose wisely which you want to download
mkdir $1samples/ #path to resources
cd $1samples/
sudo apt install sra-toolkit

./fastq-dump -I --split-files SRR7500805
./fastq-dump -I --split-files SRR7500804
./fastq-dump -I --split-files SRR7501665
./fastq-dump -I --split-files SRR7501664
./fastq-dump -I --split-files SRR7501663
./fastq-dump -I --split-files SRR7501661
./fastq-dump -I --split-files SRR7501660
./fastq-dump -I --split-files SRR7501659
./fastq-dump -I --split-files SRR7501658
./fastq-dump -I --split-files SRR7501656
./fastq-dump -I --split-files SRR7501655
./fastq-dump -I --split-files SRR7501654
./fastq-dump -I --split-files SRR7501653
./fastq-dump -I --split-files SRR7501652
./fastq-dump -I --split-files SRR5141046
./fastq-dump -I --split-files SRR5141045
./fastq-dump -I --split-files SRR5141044
./fastq-dump -I --split-files SRR5141042
./fastq-dump -I --split-files SRR5141041
./fastq-dump -I --split-files SRR5141040