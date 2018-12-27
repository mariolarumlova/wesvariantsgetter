#!/bin/bash

#installing atom
wget -O atom-amd64.deb http://atom.io/download/deb
sudo apt install gdebi-core
sudo gdebi atom-amd64.deb

#installing git
sudo apt update
sudo apt install git

#installing unzip
sudo apt-get install unzip

#installing Miniconda3
wget https://repo.continuum.io/miniconda/Miniconda3-latest-Linux-x86_64.sh
bash Miniconda-3-latest-Linux-x86_64.sh
# delete the installer after successful run
rm Miniconda3-latest-Linux-x86_64.sh
echo 'export PATH="/home/USER/miniconda3/bin:$PATH"' >> ~/.bashrc
echo 'export PATH="/home/USER/miniconda3/bin:$PATH"' >> ~/.zshrc
echo ". /home/USER/miniconda3/etc/profile.d/conda.sh" >> ~/.bashrc

#installing Snakemake
conda install -c bioconda -c conda-forge snakemake

conda update conda
conda env create --name ngs --file /PATHTOENVFILE/environment.yaml
# activate the environment
conda activate ngs

mkdir /home/USER/PATHTOGENOMES/genomes/
cd /home/USER/PATHTOGENOMES/genomes/

#downloading reference genome hg38
wget http://hgdownload.soe.ucsc.edu/goldenPath/hg38/bigZips/hg38.fa.gz
gunzip hg38.fa.gz

#downloading reference genome hg19
wget hgdownload.cse.ucsc.edu/goldenPath/hg19/bigZips/chromFa.tar.gz
tar -zxvf chromFa.tar.gz
cat chr*.fa > hg19.fa

mkdir /home/USER/PATHTOPROGRAMS/programs/
cd /home/USER/PATHTOPROGRAMS/programs/

wget https://sourceforge.net/projects/snvsniffer/files/latest/download/SNVSniffer-v2.0.4_bin_x86_64.tar.gz
tar xvzf SNVSniffer-v2.0.4_bin_x86_64.tar.gz
#gunzip SNVSniffer-v2.0.4_bin_x86_64.tar.gz

wget http://147.8.193.36:8080/hkubioinfo/FaSD-somatic/FaSD-somatic.zip
unzip FaSD-somatic.zip
